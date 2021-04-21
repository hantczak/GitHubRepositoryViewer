package hantczak.githubrepositoryviewer.repository.domain;

import java.util.List;
import java.util.Optional;

public interface RepositoryInfoAccessInterface {
    List<Repository> getAllUserRepositories(String userName);
    Optional<Repository> getRepositoryByName(String userName, String repoName);
}
