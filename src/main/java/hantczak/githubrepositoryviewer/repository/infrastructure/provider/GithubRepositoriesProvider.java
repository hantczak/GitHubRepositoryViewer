package hantczak.githubrepositoryviewer.repository.infrastructure.provider;

import hantczak.githubrepositoryviewer.repository.domain.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

        GithubRepositoryDto[] repositoryArray;
        try {
            ResponseEntity<GithubRepositoryDto[]> responseEntity = restTemplate.getForEntity(urlBuilder.toString(), GithubRepositoryDto[].class);
            repositoryArray = responseEntity.getBody();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new UserDoesNotExistException(userName);
        }catch (HttpServerErrorException exception){
            throw new RepositoryProviderException(exception);
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

        GithubRepositoryDto githubRepositoryDto;
        try {
            ResponseEntity<GithubRepositoryDto> responseEntity = restTemplate.getForEntity(urlBuilder.toString(), GithubRepositoryDto.class);
            githubRepositoryDto = responseEntity.getBody();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new RepositoryDoesNotExistException(userName, repoName);
        } catch (HttpServerErrorException exception) {
            throw new RepositoryProviderException(exception);
        }
        return Optional.of(GithubRepositoryMapper.fromDto(githubRepositoryDto));
    }

    private List<Repository> mapToRepositories(GithubRepositoryDto[] repositoryArray) {
        List<GithubRepositoryDto> repositoryList = new ArrayList<>();
        Arrays.stream(repositoryArray)
                .forEach(repository -> repositoryList.add(repository));
        return GithubRepositoryMapper.GithubRepositoryDtoListToRepositoryList(repositoryList);
    }
}

