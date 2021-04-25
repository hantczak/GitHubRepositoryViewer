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
    private final String urlBase;

    public GithubRepositoriesProvider(@Value("${github.urlBase}") String urlBase) {
        this.urlBase = urlBase;
    }

    public List<Repository> getAllUserRepositories(String userName) {

        List<Repository> repositories = new ArrayList<>();
        GithubRepositoryDto[] repositoryArray;
        ResponseEntity<GithubRepositoryDto[]> responseEntity;

        int i = 1;
        do {
            try {
                responseEntity = restTemplate.getForEntity(buildListOfRepositoriesUrl(userName, i), GithubRepositoryDto[].class);
                repositoryArray = responseEntity.getBody();
            } catch (HttpClientErrorException.NotFound exception) {
                throw new UserDoesNotExistException(userName);
            } catch (HttpServerErrorException exception) {
                throw new RepositoryProviderException(exception);
            }

            repositories.addAll(mapToRepositories(repositoryArray));
            i++;
        } while (repositoryArray.length > 0);

        return repositories;
    }

    private String buildListOfRepositoriesUrl(String userName, int pageNumber) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(urlBase)
                .append("users/")
                .append(userName)
                .append("/repos")
                .append("?page=")
                .append(pageNumber);
        return urlBuilder.toString();
    }

    public Optional<Repository> getRepositoryByName(String userName, String repoName) {

        GithubRepositoryDto githubRepositoryDto;
        try {
            ResponseEntity<GithubRepositoryDto> responseEntity = restTemplate.getForEntity(buildSingleRepositoryUrl(userName, repoName), GithubRepositoryDto.class);
            githubRepositoryDto = responseEntity.getBody();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new RepositoryDoesNotExistException(userName, repoName);
        } catch (HttpServerErrorException exception) {
            throw new RepositoryProviderException(exception);
        }
        return Optional.of(GithubRepositoryMapper.fromDto(githubRepositoryDto));
    }

    private String buildSingleRepositoryUrl(String userName, String repoName) {
        StringBuilder urlBuilder = new StringBuilder()
                .append(urlBase)
                .append("repos/")
                .append(userName)
                .append("/")
                .append(repoName);
        return urlBuilder.toString();
    }

    private List<Repository> mapToRepositories(GithubRepositoryDto[] repositoryArray) {
        List<GithubRepositoryDto> repositoryList = new ArrayList<>();
        Arrays.stream(repositoryArray)
                .forEach(repository -> repositoryList.add(repository));
        return GithubRepositoryMapper.GithubRepositoryDtoListToRepositoryList(repositoryList);
    }
}

