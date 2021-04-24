package hantczak.githubrepositoryviewer.repository.infrastructure.api;

import hantczak.githubrepositoryviewer.GithubrepositoryviewerApplicationTests;
import hantczak.githubrepositoryviewer.repository.domain.RepositoryResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

class RepositoryControllerTest extends GithubrepositoryviewerApplicationTests {
    String username;
    String url;

    @Nested
    class GetUserRepositories {

        @BeforeEach
        void setUsername(){
            username = "userName";
        }

        @Test
        void shouldReturnRepositories() {
            //given
            username = "userName";
            url = buildUrl(username);
            stubGithubRepositoriesProviderForRepoList(username, 200);

            RepositoryResponse expectedResponse = constructExpectedResponse();

            //when
            ResponseEntity<RepositoryResponse> responseEntity = restTemplate.getForEntity(url, RepositoryResponse.class);
            //then
            Assertions.assertEquals(expectedResponse, responseEntity.getBody());
        }

        @Test
        void shouldReturn500ForRepositoryProviderException() {
            //given
            url = buildUrl(username);

            //when
            stubGithubRepositoriesProviderForRepoList(username, 500);

            //then
            HttpServerErrorException exception = Assertions.assertThrows(HttpServerErrorException.class, () -> {
                restTemplate.getForEntity(url, RepositoryResponse.class);
            });
            Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        }

        @Test
        void shouldReturn404ForNonExistentUserRepoList() {
            //given
            url = buildUrl(username);

            //when
            stubGithubRepositoriesProviderForRepoList(username, 404);

            //then
            HttpClientErrorException exception = Assertions.assertThrows(HttpClientErrorException.class, () -> {
                restTemplate.getForEntity(url, RepositoryResponse.class);
            });
            Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        }

        @Test
        void shouldReturnUnprocessableEntityExceptionForInvalidUserName() {
            //given
            username = "-invalid--username-";
            url = buildUrl(username);

            //when
            //then
            HttpClientErrorException exception = Assertions.assertThrows(HttpClientErrorException.class, () -> {
                restTemplate.getForEntity(url, RepositoryResponse.class);
            });
            Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, exception.getStatusCode());
        }

        private String buildUrl(String username) {
            String urlBase = "http://localhost:" + port;
            String wholeUrl = urlBase + "/users/" + username + "/repositories";
            System.out.println("Requesting: " + wholeUrl);
            return wholeUrl;
        }

        private RepositoryResponse constructExpectedResponse() {
            RepositoryDto expectedRepositoryDto1 = new RepositoryDto("StudentDataStorage", 1);
            RepositoryDto expectedRepositoryDto2 = new RepositoryDto("Advent-of-Code-2020", 2);
            List<RepositoryDto> expectedList = List.of(expectedRepositoryDto1,expectedRepositoryDto2);
            RepositoryResponse expectedResponse = new RepositoryResponse(expectedList);
            return expectedResponse;
        }
    }

    @Nested
    class GetRepositoryByName {
        String repositoryName;

        @BeforeEach
        void setTestVariables(){
            username="userName";
            repositoryName = "StudentDataStorage";
        }

        @Test
        void shouldReturnParticularUserRepository() {
            //given
            stubGithubRepositoriesProviderForSingleRepo(username, repositoryName, 200);
            url = buildUrl(username, repositoryName);

            //when


            ResponseEntity<RepositoryDto> responseEntity = restTemplate.getForEntity(url, RepositoryDto.class);

            Assertions.assertEquals(new RepositoryDto("StudentDataStorage", 1), responseEntity.getBody());
        }

        @Test
        void shouldReturn500ForRepositoryProviderException() {
            //given
            url = buildUrl(username, repositoryName);

            //when
            stubGithubRepositoriesProviderForSingleRepo(username, repositoryName, 500);

            //then
            HttpServerErrorException exception = Assertions.assertThrows(HttpServerErrorException.class, () -> {
                restTemplate.getForEntity(url, RepositoryResponse.class);
            });
            Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        }

        @Test
        void shouldReturn404ForNonExistentRepository() {
            //given
            stubGithubRepositoriesProviderForSingleRepo(username, repositoryName, 404);
            url = buildUrl(username, repositoryName);
            //when
            HttpClientErrorException exception = Assertions.assertThrows(HttpClientErrorException.class, () -> {
                restTemplate.getForEntity(url, RepositoryDto.class);
            });

            //then
            Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        }

        @Test
        void shouldReturnUnprocessableEntityExceptionForInvalidUserName() {
            //given
            String username = "-invalid--username-";
            url = buildUrl(username, repositoryName);

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
            System.out.println("Requesting: " + wholeUrl);
            return wholeUrl;
        }
    }
}