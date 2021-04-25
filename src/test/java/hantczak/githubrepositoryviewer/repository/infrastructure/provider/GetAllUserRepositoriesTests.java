package hantczak.githubrepositoryviewer.repository.infrastructure.provider;

import hantczak.githubrepositoryviewer.GitHubRepositoryViewerApplicationTests;
import hantczak.githubrepositoryviewer.repository.domain.Repository;
import hantczak.githubrepositoryviewer.repository.domain.RepositoryProvider;
import hantczak.githubrepositoryviewer.repository.domain.RepositoryProviderException;
import hantczak.githubrepositoryviewer.repository.domain.UserDoesNotExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Tag("integration")
class GetAllUserRepositoriesTests extends GitHubRepositoryViewerApplicationTests {
    private String username;

    @Autowired
    protected RepositoryProvider repositoryProvider;

    @Test
    @DisplayName("Should return list of user repositories")
    void shouldReturnUserRepositories() {
        //given
        username = "userName";
        List<Repository> expectedRepositoryList = constructExpectedRepositoryList();
        stubGithubRepositoriesProviderForRepoList(username, 200);
        stubGithubRepositoriesProviderForRepoListEmptySecondPage(username, 200);

        //when
        List<Repository> repositoryResponse = repositoryProvider.getAllUserRepositories(username);

        //then
        Assertions.assertEquals(expectedRepositoryList, repositoryResponse);
    }

    @Test
    @DisplayName("Should return status code 404")
    void shouldReturn404ForNonExistentUser() {
        //given
        username = "userName";
        stubGithubRepositoriesProviderForRepoList(username, 404);

        //when
        //then
        Assertions.assertThrows(UserDoesNotExistException.class, () -> repositoryProvider.getAllUserRepositories(username));
    }

    @Test
    @DisplayName("Should throw RepositoryProviderException")
    void shouldReturnRepositoryProviderException() {
        //given
        username = "userName";
        stubGithubRepositoriesProviderForRepoList(username, 500);

        //when
        //then
        Assertions.assertThrows(RepositoryProviderException.class, () -> repositoryProvider.getAllUserRepositories(username));
    }

    private List<Repository> constructExpectedRepositoryList() {
        Repository expectedRepository1 = new Repository("StudentDataStorage", 1);
        Repository expectedRepository2 = new Repository("Advent-of-Code-2020", 2);
        return List.of(expectedRepository1, expectedRepository2);
    }
}
