package hantczak.githubrepositoryviewer.repository.domain;

import hantczak.githubrepositoryviewer.repository.infrastructure.api.RepositoryDto;

import java.util.List;

public class RepositoryResponse {
    private List<RepositoryDto> repositories;
    private int repositoryCount;

    public RepositoryResponse(List<RepositoryDto> repositories) {
        this.repositories = repositories;
        this.repositoryCount = repositories.size();
    }

    public List<RepositoryDto> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<RepositoryDto> repositories) {
        this.repositories = repositories;
    }

    public int getRepositoryCount() {
        return repositoryCount;
    }

    public void setRepositoryCount(int repositoryCount) {
        this.repositoryCount = repositoryCount;
    }
}
