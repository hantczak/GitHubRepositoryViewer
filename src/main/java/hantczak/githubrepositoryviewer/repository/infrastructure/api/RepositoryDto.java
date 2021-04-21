package hantczak.githubrepositoryviewer.repository.infrastructure.api;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class RepositoryDto {
    private String repositoryName;
    private long starsCount;

    public RepositoryDto(String repositoryName, long starsCount) {
        this.repositoryName = repositoryName;
        this.starsCount = starsCount;
    }

    public RepositoryDto() {
    }

    @JsonSetter("name")
    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    @JsonSetter("stargazers_count")
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
}
