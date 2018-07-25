package us.ivanshyrai.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.ivanshyrai.portfolio.model.ProjectImage;

@Repository
public interface ProjectImageRepository extends JpaRepository<ProjectImage,Long> {



}
