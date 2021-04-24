package hantczak.githubrepositoryviewer.repository.infrastructure.api;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.Objects;

public class RepositoryDto {
    private String repositoryName;
    private long starsCount;

    public RepositoryDto(String repositoryName, long starsCount) {
        this.repositoryName = repositoryName;
        this.starsCount = starsCount;
    }

    public RepositoryDto() {
    }

    @JsonSetter("repositoryName")
    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    @JsonSetter("starsCount")
    public void setStarsCount(long starsCount) {
        this.starsCount = starsCount;
    }

    @JsonGetter("repositoryName")
    public String getRepositoryName() {
        return repositoryName;
    }

    @JsonGetter("starsCount")
    public long getStarsCount() {
        return starsCount;
    }

    @Override
    public String toString() {
        return "RepositoryDto{" +
                "repositoryName='" + repositoryName + '\'' +
                ", starsCount=" + starsCount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepositoryDto that = (RepositoryDto) o;
        return starsCount == that.starsCount && Objects.equals(repositoryName, that.repositoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(repositoryName, starsCount);
    }
}

