package hantczak.githubrepositoryviewer.repository.infrastructure.api;

import hantczak.githubrepositoryviewer.repository.domain.UserStarInfo;

public class UserStarInfoMapper {

    public static UserStarInfoDto toDto(UserStarInfo userStarInfo) {
        return new UserStarInfoDto(userStarInfo.getStarSum());
    }
}
