package hantczak.githubrepositoryviewer.repository.infrastructure.api;

import hantczak.githubrepositoryviewer.repository.domain.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class GithubRepositoriesProvider implements RepositoryProvider {
    private final RestTemplate restTemplate = new RestTemplate();
    private String urlBase;

    public GithubRepositoriesProvider(@Value("${spring.urlBase}") String urlBase) {
        this.urlBase = urlBase;
    }

    public List<Repository> getAllUserRepositories(String userName) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(urlBase);
        urlBuilder.append("users/");
        urlBuilder.append(userName);
        urlBuilder.append("/repos");

        RepositoryDto[] repositoryArray;
        try {
            ResponseEntity<RepositoryDto[]> responseEntity = restTemplate.getForEntity(urlBuilder.toString(), RepositoryDto[].class);
            repositoryArray = responseEntity.getBody();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new UserDoesNotExistException(userName);
        }
        return mapToRepositories(repositoryArray);
    }

    public Optional<Repository> getRepositoryByName(String userName, String repoName) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(urlBase);
        urlBuilder.append("repos/");
        urlBuilder.append(userName);
        urlBuilder.append("/");
        urlBuilder.append(repoName);

        RepositoryDto repositoryDto;
        try {
            ResponseEntity<RepositoryDto> responseEntity = restTemplate.getForEntity(urlBuilder.toString(), RepositoryDto.class);
            repositoryDto = responseEntity.getBody();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new RepositoryDoesNotExistException(userName, repoName);
        } catch (HttpServerErrorException exception) {
            throw new RepositoryProviderException(exception);
        }
        return Optional.of(RepositoryMapper.fromDto(repositoryDto));
    }

    private List<Repository> mapToRepositories(RepositoryDto[] repositoryArray) {
        List<RepositoryDto> repositoryList = new ArrayList<>();
        Arrays.stream(repositoryArray)
                .forEach(repository -> repositoryList.add(repository));
        return RepositoryMapper.repositoryDtoListToRepositoryList(repositoryList);
    }
}

