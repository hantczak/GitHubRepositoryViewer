package hantczak.githubrepositoryviewer.repository.domain;

import hantczak.githubrepositoryviewer.repository.infrastructure.api.RepositoryDto;

import java.util.List;
import java.util.Objects;

public class RepositoryResponse {
    private List<RepositoryDto> repositories;
    private int repositoryCount;

    public RepositoryResponse(List<RepositoryDto> repositories) {
        this.repositories = repositories;
        this.repositoryCount = repositories.size();
    }

    public RepositoryResponse() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepositoryResponse that = (RepositoryResponse) o;
        return repositoryCount == that.repositoryCount && repositories.equals(that.repositories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(repositories, repositoryCount);
    }

    @Override
    public String toString() {
        return "RepositoryResponse{" +
                "repositories=" + repositories +
                ", repositoryCount=" + repositoryCount +
                '}';
    }
}
