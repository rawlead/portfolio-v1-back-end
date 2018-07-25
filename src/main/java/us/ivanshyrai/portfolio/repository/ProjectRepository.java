package us.ivanshyrai.portfolio.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import us.ivanshyrai.portfolio.model.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository  extends JpaRepository<Project, Long> {
    Optional<Project> findById(Long projectId);

    Page<Project> findByCreatedBy(Long userId, Pageable pageable);

    long countByCreatedBy(Long userId);

    List<Project> findByIdIn(List<Long> projectsIds);

    List<Project> findByIdIn(List<Long> projectIds, Sort sort);
}
