package hantczak.githubrepositoryviewer.repository.infrastructure.api;

import hantczak.githubrepositoryviewer.repository.domain.StarSum;

public class StarSumMapper {

    public static StarSumDto toDto(StarSum starSum) {
        return new StarSumDto(starSum.getStarSum());
    }
}
