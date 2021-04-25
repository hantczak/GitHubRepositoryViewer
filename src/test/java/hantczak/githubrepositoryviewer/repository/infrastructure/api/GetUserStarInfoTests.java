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
class GetUserStarInfoTests extends GitHubRepositoryViewerApplicationTests {
    String username;

    @Test
    @DisplayName("Should return StarSumDto object")
    void shouldReturnStarSumDto() {
        //given
        username = "userName";
        String url = buildUrl(username);
        stubGithubRepositoriesProviderForRepoList(username, 200);
        stubGithubRepositoriesProviderForRepoListEmptySecondPage(username, 200);

        //when
        ResponseEntity<UserStarInfoDto> responseEntity = restTemplate.getForEntity(url, UserStarInfoDto.class);

        //then
        Assertions.assertEquals(new UserStarInfoDto(3L), responseEntity.getBody());
    }

    @Test
    @DisplayName("Should return status code 500")
    void shouldReturn500ForRepositoryProviderException() {
        //given
        username = "userName";
        String url = buildUrl(username);

        //when
        stubGithubRepositoriesProviderForRepoList(username, 500);

        //then
        HttpServerErrorException exception = Assertions.assertThrows(HttpServerErrorException.class, () -> {
            restTemplate.getForEntity(url, RepositoryResponse.class);
        });
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
    }

    @Test
    @DisplayName("Should return status code 404")
    void shouldReturn404ForNonExistentUserRepoList() {
        //given
        username = "userName";
        String url = buildUrl(username);

        //when
        stubGithubRepositoriesProviderForRepoList(username, 404);

        //then
        HttpClientErrorException exception = Assertions.assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.getForEntity(url, RepositoryResponse.class);
        });
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    @DisplayName("Should return status code 422")
    void shouldReturnUnprocessableEntityExceptionForInvalidUserName() {
        //given
        username = "-invalid--username-";
        String url = buildUrl(username);

        //when
        //then
        HttpClientErrorException exception = Assertions.assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.getForEntity(url, RepositoryResponse.class);
        });
        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, exception.getStatusCode());
    }

    private String buildUrl(String username) {
        String urlBase = "http://localhost:" + port;
        String wholeUrl = urlBase + "/users/" + username + "/starSum";
        return wholeUrl;
    }
}

