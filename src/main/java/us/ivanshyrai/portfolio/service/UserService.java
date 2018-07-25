package us.ivanshyrai.portfolio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import us.ivanshyrai.portfolio.exception.BadRequestException;
import us.ivanshyrai.portfolio.model.User;
import us.ivanshyrai.portfolio.payload.PagedResponse;
import us.ivanshyrai.portfolio.payload.UserSummary;
import us.ivanshyrai.portfolio.repository.UserRepository;
import us.ivanshyrai.portfolio.security.UserPrincipal;
import us.ivanshyrai.portfolio.util.AppConstants;
import us.ivanshyrai.portfolio.util.ModelMapper;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public PagedResponse<UserSummary> getAllUsers(UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<User> users = userRepository.findAll(pageable);

        if(users.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), users.getNumber(),
                    users.getSize(), users.getTotalElements(), users.getTotalPages(), users.isLast());
        }


        List<UserSummary> userProfiles = users.map(ModelMapper::userToUserSummary).getContent();

        return new PagedResponse<>(userProfiles, users.getNumber(),
                users.getSize(), users.getTotalElements(), users.getTotalPages(), users.isLast());
    }

    private void validatePageNumberAndSize(int page, int size) {
        if (page < 0)
            throw new BadRequestException("Page number cannot be less than zero");
        if (size > AppConstants.MAX_PAGE_SIZE)
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
    }
}
