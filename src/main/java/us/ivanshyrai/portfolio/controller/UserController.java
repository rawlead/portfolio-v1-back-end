package us.ivanshyrai.portfolio.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import us.ivanshyrai.portfolio.exception.ResourceNotFoundException;
import us.ivanshyrai.portfolio.model.User;
import us.ivanshyrai.portfolio.payload.*;
import us.ivanshyrai.portfolio.repository.LikeRepository;
import us.ivanshyrai.portfolio.repository.ProjectRepository;
import us.ivanshyrai.portfolio.repository.UserRepository;
import us.ivanshyrai.portfolio.security.CurrentUser;
import us.ivanshyrai.portfolio.security.UserPrincipal;
import us.ivanshyrai.portfolio.service.ProjectService;
import us.ivanshyrai.portfolio.service.UserService;
import us.ivanshyrai.portfolio.util.AppConstants;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final LikeRepository likeRepository;
    private final ProjectService projectService;
    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserRepository userRepository, ProjectRepository projectRepository, LikeRepository likeRepository, ProjectService projectService, UserService userService) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.likeRepository = likeRepository;
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping("/user/me")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(),
                currentUser.getName(),
                currentUser.getUsername());
        return userSummary;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        long projectCount = projectRepository.countByCreatedBy(user.getId());
        long likeCount = likeRepository.countByUserId(user.getId());

        return new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt(), projectCount, likeCount);
    }

    @GetMapping("/users/{username}/projects")
    public PagedResponse<ProjectResponse> getProjectsCreatedBy(@PathVariable String username,
                                                               @CurrentUser UserPrincipal currentUser,
                                                               @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                               @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return projectService.getProjectsCreatedBy(username, currentUser, page, size);
    }

    @GetMapping("/users")
    public PagedResponse<UserSummary> getAllUsers(@CurrentUser UserPrincipal currentUser,
                                                  @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                  @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return userService.getAllUsers(currentUser, page, size);
    }


}
































