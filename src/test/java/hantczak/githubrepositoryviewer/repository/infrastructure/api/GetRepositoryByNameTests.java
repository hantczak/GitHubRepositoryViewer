package hantczak.githubrepositoryviewer.repository.infrastructure.api;

import hantczak.githubrepositoryviewer.GitHubRepositoryViewerApplicationTests;
import hantczak.githubrepositoryviewer.repository.domain.RepositoryResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Tag("integration")
class GetRepositoryByNameTests extends GitHubRepositoryViewerApplicationTests {
    String username;
    String repositoryName;

    @Test
    @DisplayName("Should return particular user repository")
    void shouldReturnParticularUserRepository() {
        //given
        username = "userName";
        repositoryName = "StudentDataStorage";
        stubGithubRepositoriesProviderForSingleRepo(username, repositoryName, 200);
        String url = buildUrl(username, repositoryName);

        //when
        ResponseEntity<RepositoryDto> responseEntity = restTemplate.getForEntity(url, RepositoryDto.class);

        //then
        Assertions.assertEquals(new RepositoryDto("StudentDataStorage", 1), responseEntity.getBody());
    }

    @Test
    @DisplayName("Should return status code 500")
    void shouldReturn500ForRepositoryProviderException() {
        //given
        username = "userName";
        repositoryName = "StudentDataStorage";
        String url = buildUrl(username, repositoryName);

        //when
        stubGithubRepositoriesProviderForSingleRepo(username, repositoryName, 500);

        //then
        HttpServerErrorException exception = Assertions.assertThrows(HttpServerErrorException.class, () -> {
            restTemplate.getForEntity(url, RepositoryResponse.class);
        });
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
    }

    @Test
    @DisplayName("Should return status code 404")
    void shouldReturn404ForNonExistentRepository() {
        //given
        username = "userName";
        repositoryName = "StudentDataStorage";
        stubGithubRepositoriesProviderForSingleRepo(username, repositoryName, 404);
        String url = buildUrl(username, repositoryName);
        //when
        HttpClientErrorException exception = Assertions.assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.getForEntity(url, RepositoryDto.class);
        });

        //then
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    @DisplayName("Should return status code 422")
    void shouldReturnUnprocessableEntityExceptionForInvalidUserName() {
        //given
        username = "-invalid--username-";
        repositoryName = "StudentDataStorage";
        String url = buildUrl(username, repositoryName);

        //when
        HttpClientErrorException exception = Assertions.assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.getForEntity(url, RepositoryResponse.class);
        });

        //then
        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, exception.getStatusCode());
    }

    private String buildUrl(String username, String repositoryName) {
        String urlBase = "http://localhost:" + port;
        String wholeUrl = urlBase + "/repos/" + username + "/" + repositoryName;
        return wholeUrl;
    }
}

