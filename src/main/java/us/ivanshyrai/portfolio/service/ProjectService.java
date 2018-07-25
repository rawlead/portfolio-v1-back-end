package us.ivanshyrai.portfolio.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import us.ivanshyrai.portfolio.exception.BadRequestException;
import us.ivanshyrai.portfolio.exception.ResourceNotFoundException;
import us.ivanshyrai.portfolio.model.Like;
import us.ivanshyrai.portfolio.model.Project;
import us.ivanshyrai.portfolio.model.User;
import us.ivanshyrai.portfolio.payload.PagedResponse;
import us.ivanshyrai.portfolio.payload.ProjectResponse;
import us.ivanshyrai.portfolio.repository.LikeRepository;
import us.ivanshyrai.portfolio.repository.ProjectImageRepository;
import us.ivanshyrai.portfolio.repository.ProjectRepository;
import us.ivanshyrai.portfolio.repository.UserRepository;
import us.ivanshyrai.portfolio.security.UserPrincipal;
import us.ivanshyrai.portfolio.util.AppConstants;
import us.ivanshyrai.portfolio.util.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private ProjectRepository projectRepository;
    private LikeRepository likeRepository;
    private ProjectImageRepository projectImageRepository;
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    public ProjectService(ProjectRepository projectRepository, LikeRepository likeRepository, ProjectImageRepository projectImageRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.likeRepository = likeRepository;
        this.projectImageRepository = projectImageRepository;
        this.userRepository = userRepository;
    }


    public PagedResponse<ProjectResponse> getProjectsCreatedBy(String username, UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
//        All projects created by username
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Project> projects = projectRepository.findByCreatedBy(user.getId(), pageable);

        if (projects.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(),
                    projects.getNumber(),
                    projects.getSize(),
                    projects.getTotalElements(),
                    projects.getTotalPages(),
                    projects.isLast());
        }

        // Map Projects to ProjectResponses containing like counts and project creator details
        List<Long> projectIds = projects.map(Project::getId).getContent();
//        Map<Long, Long> projectUserLikeMap = getProjectUserLikeMap(currentUser, projectIds);
        List<ProjectResponse> projectResponses = projects.map(project -> ModelMapper.mapProjectToProjectResponse(project, user, null)
        ).getContent();

        return new PagedResponse<>(projectResponses,
                projects.getNumber(),
                projects.getSize(),
                projects.getTotalElements(),
                projects.getTotalPages(),
                projects.isLast());
    }
//
//    private Map<Long,Long> getProjectUserLikeMap(UserPrincipal currentUser, List<Long> projectIds) {
//        // Retrieve Likes done by the logged in user to the given projectIds
//        Map<Long, Long> projectUserLikeMap = null;
//        if (currentUser != null) {
//            List<Like> userLikes = likeRepository.findByUserIdAndProjectIdIn(currentUser.getId(),projectIds);
//            projectUserLikeMap = userLikes.stream()
//                    .collect(Collectors.toMap(like -> like.getProject().getId());
//        }
//    }

    private void validatePageNumberAndSize(int page, int size) {
        if (page < 0)
            throw new BadRequestException("Page number cannot be less than zero");
        if (size > AppConstants.MAX_PAGE_SIZE)
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
    }
}



































