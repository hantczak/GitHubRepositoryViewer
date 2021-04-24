package hantczak.githubrepositoryviewer.repository.infrastructure.provider;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class GithubRepositoryDto {
    private String repositoryName;
    private long starsCount;

    public GithubRepositoryDto() {
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
