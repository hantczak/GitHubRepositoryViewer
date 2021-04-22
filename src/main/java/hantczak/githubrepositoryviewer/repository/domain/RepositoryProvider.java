package hantczak.githubrepositoryviewer.repository.domain;

import java.util.List;
import java.util.Optional;

public interface RepositoryProvider {
    List<Repository> getAllUserRepositories(String userName);

    Optional<Repository> getRepositoryByName(String userName, String repoName);
}
