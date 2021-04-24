package hantczak.githubrepositoryviewer.repository.infrastructure.api;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.Objects;

public class StarSumDto {
    private Long starSum;

    public StarSumDto() {
    }

    public StarSumDto(Long starSum) {
        this.starSum = starSum;
    }

    @JsonGetter
    public Long getStarSum() {
        return starSum;
    }

    @JsonSetter
    public void setStarSum(Long starSum) {
        this.starSum = starSum;
    }

    @Override
    public String toString() {
        return "StarSumDto{" +
                "starSum=" + starSum +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StarSumDto that = (StarSumDto) o;
        return Objects.equals(starSum, that.starSum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(starSum);
    }
}
