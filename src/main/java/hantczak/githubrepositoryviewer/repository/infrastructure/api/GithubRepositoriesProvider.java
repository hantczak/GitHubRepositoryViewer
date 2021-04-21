package hantczak.githubrepositoryviewer.repository.infrastructure.api;

import hantczak.githubrepositoryviewer.repository.domain.Repository;
import hantczak.githubrepositoryviewer.repository.domain.RepositoryInfoAccessInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class GithubRepositoriesProvider implements RepositoryInfoAccessInterface {
    private final RestTemplate restTemplate = new RestTemplate();

    public List<Repository> getAllUserRepositories(String userName) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("https://api.github.com/users/");
        urlBuilder.append(userName);
        urlBuilder.append("/repos");

        try{
            ResponseEntity<RepositoryDto[]> responseEntity = restTemplate.getForEntity(urlBuilder.toString(), RepositoryDto[].class);
            RepositoryDto[] repositoryArray = responseEntity.getBody();
            List<RepositoryDto> repositoryList = new ArrayList<>();
            Arrays.stream(repositoryArray)
                    .forEach(repository -> repositoryList.add(repository));
            return RepositoryMapper.repositoryDtoListToRepositoryList(repositoryList);
        }catch (Exception e){
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }

    public Optional<Repository> getRepositoryByName(String userName, String repoName) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("https://api.github.com/repos/");
        urlBuilder.append(userName);
        urlBuilder.append("/");
        urlBuilder.append(repoName);

        try {
            ResponseEntity<RepositoryDto> responseEntity = restTemplate.getForEntity(urlBuilder.toString(), RepositoryDto.class);
            return Optional.of(RepositoryMapper.fromDto(responseEntity.getBody()));
        }catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
