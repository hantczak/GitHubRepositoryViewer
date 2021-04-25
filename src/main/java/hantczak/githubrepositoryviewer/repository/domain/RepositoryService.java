package hantczak.githubrepositoryviewer.repository.domain;

import java.util.List;
import java.util.Optional;

public class RepositoryService {
    private final RepositoryProvider repositoryProvider;
    private final UserNameValidator userNameValidator;

    public RepositoryService(RepositoryProvider repositoryInfoAccess, UserNameValidator userNameValidator) {
        this.repositoryProvider = repositoryInfoAccess;
        this.userNameValidator = userNameValidator;
    }

    public List<Repository> getAllUserRepositories(String userName) {
        userNameValidator.validateUserName(userName);
        return repositoryProvider.getAllUserRepositories(userName);
    }

    public Optional<Repository> getRepositoryByName(String userName, String repoName) {
        userNameValidator.validateUserName(userName);
        return repositoryProvider.getRepositoryByName(userName, repoName);
    }

    public Optional<UserStarInfo> getSumOfStarsForAllUserRepositories(String userName) {
        userNameValidator.validateUserName(userName);
        List<Repository> repositories = getAllUserRepositories(userName);
        if (repositories.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(listOfRepositoriesToStarSum(repositories));
    }

    private UserStarInfo listOfRepositoriesToStarSum(List<Repository> repositories) {
        Long starSumAsLong = repositories.stream()
                .mapToLong(Repository::getStarsCount)
                .sum();
        return new UserStarInfo(starSumAsLong);
    }
}