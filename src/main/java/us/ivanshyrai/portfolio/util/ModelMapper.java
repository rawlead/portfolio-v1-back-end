package us.ivanshyrai.portfolio.util;

import us.ivanshyrai.portfolio.model.Like;
import us.ivanshyrai.portfolio.model.Project;
import us.ivanshyrai.portfolio.model.User;
import us.ivanshyrai.portfolio.payload.LikeResponse;
import us.ivanshyrai.portfolio.payload.ProjectResponse;
import us.ivanshyrai.portfolio.payload.UserSummary;

import java.util.List;
import java.util.stream.Collectors;

public class ModelMapper {

    public static ProjectResponse mapProjectToProjectResponse(Project project,
                                                              User creator,
                                                              Long userLike) {
        ProjectResponse projectResponse = new ProjectResponse();
        projectResponse.setId(project.getId());
        projectResponse.setTitle(project.getTitle());
        projectResponse.setDescription(project.getDescription());
        projectResponse.setCreationDateTime(project.getCreatedAt());

        UserSummary userSummary = new UserSummary(creator.getId(),
                creator.getUsername(),
                creator.getName());
        projectResponse.setCreatedBy(userSummary);


        List<LikeResponse> likeResponses = project.getLikes().stream()
                .map(like -> {
                    return new LikeResponse(like.getId(),
                            like.getUser().getId(),
                            like.getProject().getId());
                }).collect(Collectors.toList());

        projectResponse.setLikes(likeResponses);

        projectResponse.setTotalLikes((long) likeResponses.size());
        return projectResponse;
    }

    public static UserSummary userToUserSummary(User user) {
        return new UserSummary(user.getId(),user.getName(),user.getUsername());
    }
}
