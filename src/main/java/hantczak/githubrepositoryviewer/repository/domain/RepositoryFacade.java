package hantczak.githubrepositoryviewer.repository.domain;

import java.util.List;
import java.util.Optional;

public class RepositoryFacade {
    private final RepositoryService repositoryService;

    public RepositoryFacade(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    public List<Repository> getAllUserRepositories(String userName) {
        return repositoryService.getAllUserRepositories(userName);
    }

    public Optional<Repository> getRepositoryByName(String userName, String repoName) {
        return repositoryService.getRepositoryByName(userName, repoName);
    }

    public Optional<StarSum> getSumOfStarsForAllUserRepositories(String userName) {
        return repositoryService.getSumOfStarsForAllUserRepositories(userName);
    }
}
