package hantczak.githubrepositoryviewer.repository.infrastructure.provider;

import hantczak.githubrepositoryviewer.GithubrepositoryviewerApplicationTests;
import hantczak.githubrepositoryviewer.repository.domain.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

class GithubRepositoriesProviderTest extends GithubrepositoryviewerApplicationTests {
    private String username;

    @Autowired
    protected RepositoryProvider repositoryProvider;

    @BeforeEach
    void setUserName() {
        username = "userName";
    }

    @Nested
    @DisplayName("Get all user repositories tests:")
    class getAllUserRepositoriesTests {

        @Test
        @DisplayName("Should return list of user repositories")
        void shouldReturnUserRepositories() {
            //given
            List<Repository> expectedRepositoryList = constructExpectedRepositoryList();
            stubGithubRepositoriesProviderForRepoList(username, 200);

            //when
            List<Repository> repositoryResponse = repositoryProvider.getAllUserRepositories(username);

            //then
            Assertions.assertEquals(expectedRepositoryList, repositoryResponse);
        }

        @Test
        @DisplayName("Should return status code 404")
        void shouldReturn404ForNonExistentUser() {
            //given
            stubGithubRepositoriesProviderForRepoList(username, 404);

            //when
            //then
            Assertions.assertThrows(UserDoesNotExistException.class, () -> repositoryProvider.getAllUserRepositories(username));
        }

        @Test
        @DisplayName("Should throw RepositoryProviderException")
        void shouldReturnRepositoryProviderException() {
            //given
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

    @Nested
    @DisplayName("Get repository by name tests:")
    class getRepositoryByNameTests {
        private final String repositoryName = "StudentDataStorage";

        @Test
        @DisplayName("Should return single repository object")
        void shouldReturnParticularUserRepository() {
            //given
            stubGithubRepositoriesProviderForSingleRepo(username, repositoryName, 200);

            //when
            Optional<Repository> repository = repositoryProvider.getRepositoryByName(username, repositoryName);

            //then
            Assertions.assertEquals(Optional.of(new Repository("StudentDataStorage", 1)), repository);
        }

        @Test
        @DisplayName("Should return status code 404")
        void shouldReturn404ForNonExistentRepo() {
            //given
            stubGithubRepositoriesProviderForSingleRepo(username, repositoryName, 404);

            //when
            //then
            Assertions.assertThrows(RepositoryDoesNotExistException.class, () -> repositoryProvider.getRepositoryByName(username, repositoryName));

        }

        @Test
        @DisplayName("Should throw RepositoryProviderException")
        void shouldReturnRepositoryProviderException() {
            //given
            stubGithubRepositoriesProviderForSingleRepo(username, repositoryName, 500);

            //when
            //then
            Assertions.assertThrows(RepositoryProviderException.class, () -> repositoryProvider.getRepositoryByName(username, repositoryName));
        }
    }
}