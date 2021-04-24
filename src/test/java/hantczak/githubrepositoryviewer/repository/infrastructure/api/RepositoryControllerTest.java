package hantczak.githubrepositoryviewer.repository.infrastructure.api;

import hantczak.githubrepositoryviewer.GithubrepositoryviewerApplicationTests;
import hantczak.githubrepositoryviewer.repository.domain.RepositoryResponse;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

class RepositoryControllerTest extends GithubrepositoryviewerApplicationTests {
    String username;
    String url;

    @Nested
    @DisplayName("Get all user repositories tests:")
    class GetAllUserRepositories {

        @BeforeEach
        void setUsername() {
            username = "userName";
        }

        @Test
        @DisplayName("Should return user repositories")
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
        @DisplayName("Should return status code 500")
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
        @DisplayName("Should return status code 404")
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
        @DisplayName("Should return unprocessable entity Exception")
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
            return wholeUrl;
        }

        private RepositoryResponse constructExpectedResponse() {
            RepositoryDto expectedRepositoryDto1 = new RepositoryDto("StudentDataStorage", 1);
            RepositoryDto expectedRepositoryDto2 = new RepositoryDto("Advent-of-Code-2020", 2);
            List<RepositoryDto> expectedList = List.of(expectedRepositoryDto1, expectedRepositoryDto2);
            RepositoryResponse expectedResponse = new RepositoryResponse(expectedList);
            return expectedResponse;
        }
    }

    @Nested
    @DisplayName("Get repository by name tests:")
    class GetRepositoryByName {
        String repositoryName;

        @BeforeEach
        void setTestVariables() {
            username = "userName";
            repositoryName = "StudentDataStorage";
        }

        @Test
        @DisplayName("Should return particular user repository")
        void shouldReturnParticularUserRepository() {
            //given
            stubGithubRepositoriesProviderForSingleRepo(username, repositoryName, 200);
            url = buildUrl(username, repositoryName);

            //when


            ResponseEntity<RepositoryDto> responseEntity = restTemplate.getForEntity(url, RepositoryDto.class);

            Assertions.assertEquals(new RepositoryDto("StudentDataStorage", 1), responseEntity.getBody());
        }

        @Test
        @DisplayName("Should return status code 500")
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
        @DisplayName("Should return status code 404")
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
        @DisplayName("Should return status code 422")
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
            return wholeUrl;
        }
    }

    @Nested
    @DisplayName("Get star sum tests:")
    class getStarSumTests {

        @BeforeEach
        void setUsername() {
            username = "userName";
        }

        @Test
        @DisplayName("Should return StarSumDto object")
        void shouldReturnStarSumDto() {
            //given
            url = buildUrl(username);
            stubGithubRepositoriesProviderForRepoList(username, 200);

            //when
            ResponseEntity<StarSumDto> responseEntity = restTemplate.getForEntity(url, StarSumDto.class);

            //then
            Assertions.assertEquals(new StarSumDto(3L), responseEntity.getBody());
        }

        @Test
        @DisplayName("Should return status code 500")
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
        @DisplayName("Should return status code 404")
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
        @DisplayName("Should return status code 422")
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
            String wholeUrl = urlBase + "/users/" + username + "/starSum";
            return wholeUrl;
        }

    }
}