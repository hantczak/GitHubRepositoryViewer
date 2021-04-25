package hantczak.githubrepositoryviewer.repository.infrastructure.provider;

import hantczak.githubrepositoryviewer.GitHubRepositoryViewerApplicationTests;
import hantczak.githubrepositoryviewer.repository.domain.Repository;
import hantczak.githubrepositoryviewer.repository.domain.RepositoryDoesNotExistException;
import hantczak.githubrepositoryviewer.repository.domain.RepositoryProvider;
import hantczak.githubrepositoryviewer.repository.domain.RepositoryProviderException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Tag("integration")
class GetRepositoryByNameTests extends GitHubRepositoryViewerApplicationTests {
    private String username;
    private final String repositoryName = "StudentDataStorage";

    @Autowired
    protected RepositoryProvider repositoryProvider;


    @Test
    @DisplayName("Should return single repository object")
    void shouldReturnParticularUserRepository() {
        //given
        username = "userName";
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
        username = "userName";
        stubGithubRepositoriesProviderForSingleRepo(username, repositoryName, 404);

        //when
        //then
        Assertions.assertThrows(RepositoryDoesNotExistException.class, () -> repositoryProvider.getRepositoryByName(username, repositoryName));

    }

    @Test
    @DisplayName("Should throw RepositoryProviderException")
    void shouldReturnRepositoryProviderException() {
        //given
        username = "userName";
        stubGithubRepositoriesProviderForSingleRepo(username, repositoryName, 500);

        //when
        //then
        Assertions.assertThrows(RepositoryProviderException.class, () -> repositoryProvider.getRepositoryByName(username, repositoryName));
    }
}

