package hantczak.githubrepositoryviewer.repository.infrastructure.api;

import hantczak.githubrepositoryviewer.repository.domain.Repository;
import hantczak.githubrepositoryviewer.repository.domain.RepositoryFacade;
import hantczak.githubrepositoryviewer.repository.domain.RepositoryResponse;
import hantczak.githubrepositoryviewer.repository.domain.RepositoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/users")
public class RepositoryController {

    private final RepositoryFacade repositoryFacade;

    public RepositoryController(RepositoryFacade repositoryFacade) {
        this.repositoryFacade = repositoryFacade;
    }

    @GetMapping("/{userName}/repositories")
    public ResponseEntity<RepositoryResponse> getUserRepositories(@PathVariable(value = "userName") String username) {
        List<Repository> repositories = repositoryFacade.getAllUserRepositories(username);
        if (repositories.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new RepositoryResponse(RepositoryMapper.repositoryListToRepositoryDtoList(repositoryFacade.getAllUserRepositories(username))));
        }
    }

    @GetMapping("/{userName}/{repositoryName}")
    public ResponseEntity<RepositoryDto> getRepositoryByName(@PathVariable(value = "userName") String userName, @PathVariable(value = "repositoryName") String repositoryName) {
        Optional<Repository> repositoryOptional = repositoryFacade.getRepositoryByName(userName, repositoryName);
        if (repositoryOptional.isPresent()) {
            return ResponseEntity.ok(RepositoryMapper.toDto(repositoryOptional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userName}/starSum")
    public ResponseEntity<Long> getSumOfStarsForAllUserRepositories(@PathVariable(value = "userName") String userName) {
        Optional<Long> starSumOptional = repositoryFacade.getSumOfStarsForAllUserRepositories(userName);
        if (starSumOptional.isPresent()) {
            return ResponseEntity.ok(starSumOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
