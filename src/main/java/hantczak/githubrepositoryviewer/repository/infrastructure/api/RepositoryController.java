package hantczak.githubrepositoryviewer.repository.infrastructure.api;

import hantczak.githubrepositoryviewer.repository.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class RepositoryController {

    private final RepositoryFacade repositoryFacade;

    public RepositoryController(RepositoryFacade repositoryFacade) {
        this.repositoryFacade = repositoryFacade;
    }

    @GetMapping("users/{userName}/repositories")
    public ResponseEntity<RepositoryResponse> getAllUserRepositories(@PathVariable(value = "userName") String username) {
        List<Repository> repositories = null;

        repositories = repositoryFacade.getAllUserRepositories(username);
        if (repositories.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new RepositoryResponse(RepositoryMapper.repositoryListToRepositoryDtoList(repositories)));
        }
    }

    @GetMapping("repos/{userName}/{repositoryName}")
    public ResponseEntity<RepositoryDto> getRepositoryByName(@PathVariable(value = "userName") String userName, @PathVariable(value = "repositoryName") String repositoryName) {
        Optional<Repository> repositoryOptional = repositoryFacade.getRepositoryByName(userName, repositoryName);
        if (repositoryOptional.isPresent()) {
            return ResponseEntity.ok(RepositoryMapper.toDto(repositoryOptional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("users/{userName}/starSum")
    public ResponseEntity<UserStarInfoDto> getSumOfStarsForAllUserRepositories(@PathVariable(value = "userName") String userName) {
        Optional<UserStarInfo> starSumOptional = repositoryFacade.getSumOfStarsForAllUserRepositories(userName);
        if (starSumOptional.isPresent()) {
            return ResponseEntity.ok(UserStarInfoMapper.toDto(starSumOptional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(InvalidUserNameException.class)
    ResponseEntity<String> handleInvalidUserNameException(InvalidUserNameException exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception.getMessage());
    }

    @ExceptionHandler(UserDoesNotExistException.class)
    ResponseEntity<String> handleUserDoesNotExistException(UserDoesNotExistException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(RepositoryDoesNotExistException.class)
    ResponseEntity<String> handleRepositoryDoesNotExistException(RepositoryDoesNotExistException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(RepositoryProviderException.class)
    ResponseEntity<String> handleRepositoryProviderException(RepositoryProviderException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
    }
}
