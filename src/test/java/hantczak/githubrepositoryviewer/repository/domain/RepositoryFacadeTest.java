package hantczak.githubrepositoryviewer.repository.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

class RepositoryFacadeTest {
    private RepositoryFacade repositoryFacade;
    private RepositoryProvider repositoryProviderMock;
    private String username = "userName";


    @BeforeEach
    void init() {
        repositoryProviderMock = Mockito.mock(RepositoryProvider.class);
        repositoryFacade = new RepositoryFacadeConfiguration().repositoryFacade(repositoryProviderMock);
    }

    @Nested
    class getAllUserRepositoriesTests {

        @BeforeEach
        void setupMock() {
            Repository exampleRepository1 = new Repository("StudentDataStorage", 1);
            Repository exampleRepository2 = new Repository("Advent-of-Code-2020", 2);
            Mockito.when(repositoryProviderMock.getAllUserRepositories(username)).thenReturn(List.of(exampleRepository1, exampleRepository2));
        }

        @Test
        void shouldReturnListOfRepositories() {
            //given
            //when
            Repository expectedRepository1 = new Repository("StudentDataStorage", 1);
            Repository expectedRepository2 = new Repository("Advent-of-Code-2020", 2);
            List<Repository> expectedRepositories = List.of(expectedRepository1, expectedRepository2);

            //then
            Assertions.assertEquals(repositoryFacade.getAllUserRepositories(username), expectedRepositories);
        }
    }

    @Nested
    class getRepositoryByNameTest {
        private final String repoName = "Advent-of-Code-2020";

        @BeforeEach
        void setupMock() {
            Repository exampleRepository = new Repository("Advent-of-Code-2020", 2);
            Optional<Repository> exampleRepositoryOptional = Optional.of(exampleRepository);
            Mockito.when(repositoryProviderMock.getRepositoryByName(username, repoName)).thenReturn(exampleRepositoryOptional);
        }

        @Test
        void shouldReturnSingleRepository() {
            //given
            //when
            Repository expectedRepository1 = new Repository("Advent-of-Code-2020", 2);

            //then
            Assertions.assertEquals(Optional.of(expectedRepository1), repositoryFacade.getRepositoryByName(username, repoName));

        }
    }

    @Nested
    class getSumOfStarsForAllUserRepositoriesTests {

        @BeforeEach
        void setupMock() {
            Repository exampleRepository1 = new Repository("StudentDataStorage", 1);
            Repository exampleRepository2 = new Repository("Advent-of-Code-2020", 2);
            Mockito.when(repositoryProviderMock.getAllUserRepositories(username)).thenReturn(List.of(exampleRepository1, exampleRepository2));
        }

        @Test
        void shouldReturnSumOfStarsForAllUserRepositories() {
            //given
            //when
            Optional<StarSum> expectedStarSumOptional = Optional.of(new StarSum(3L));

            //then
            Assertions.assertEquals(expectedStarSumOptional, repositoryFacade.getSumOfStarsForAllUserRepositories(username));

        }
    }

    @Nested
    class InvalidUsernameExceptionTest {
        String invalidUsername;

        @Test
        void shouldThrowExceptionWhenUsernameHasMultipleAdjacentHyphens() {
            //given
            invalidUsername = "user--name";
            //when
            //then
            Assertions.assertThrows(InvalidUserNameException.class, () -> repositoryFacade.getSumOfStarsForAllUserRepositories(invalidUsername));
        }

        @Test
        void shouldThrowExceptionWhenUsernameStartsWithHyphenSign() {
            //given
            invalidUsername = "-username";
            //when
            //then
            Assertions.assertThrows(InvalidUserNameException.class, () -> repositoryFacade.getSumOfStarsForAllUserRepositories(invalidUsername));
        }

        @Test
        void shouldThrowExceptionWhenUsernameEndsWithHyphenSign() {
            //given
            invalidUsername = "username-";
            //when
            //then
            Assertions.assertThrows(InvalidUserNameException.class, () -> repositoryFacade.getSumOfStarsForAllUserRepositories(invalidUsername));
        }

        @Test
        void shouldThrowExceptionWhenUsernameContainsSpaces() {
            //given
            invalidUsername = "user name";
            //when
            //then
            Assertions.assertThrows(InvalidUserNameException.class, () -> repositoryFacade.getSumOfStarsForAllUserRepositories(invalidUsername));
        }

        @Test
        void shouldThrowExceptionWhenUsernameContainsSpecialCharacters() {
            //given
            invalidUsername = "user!name";
            //when
            //then
            Assertions.assertThrows(InvalidUserNameException.class, () -> repositoryFacade.getSumOfStarsForAllUserRepositories(invalidUsername));
        }

        @Test
        void shouldThrowExceptionWhenUsernameIsLongerThan39Characters() {
            //given
            StringBuilder stringBuilder = new StringBuilder();
            int i = 1;
            while (i <= 40) {
                stringBuilder.append("a");
                i++;
            }
            invalidUsername = stringBuilder.toString();
            //when
            //then
            Assertions.assertThrows(InvalidUserNameException.class, () -> repositoryFacade.getSumOfStarsForAllUserRepositories(invalidUsername));
        }
    }
}
