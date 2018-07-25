package us.ivanshyrai.portfolio.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import us.ivanshyrai.portfolio.model.Like;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

//    @Query("SELECT NEW us.ivanshyrai.portfolio.model.ProjectLikeCount(l.like.id, count(l.id)) FROM Like l WHERE v.project.id in :projectIds GROUP BY v.like.id")
//    List<ProjectLikeCount> countByProjectIdInGroupByLikeId(@Param("projectIds") List<Long> projectIds);

    @Query("SELECT l FROM Like l WHERE l.user.id = :userId AND l.project.id IN :projectIds")
    List<Like> findByUserIdAndProjectIdIn(@Param("userId") Long userId, @Param("projectIds") List<Long> projectIds);

    @Query("SELECT l FROM Like l WHERE l.user.id = :userId AND l.project.id = :projectId")
    Like findByUserIdAndProjectId(@Param("userId") Long userId, @Param("projectId") Long projectId);

    @Query("SELECT COUNT(l.id) FROM Like l WHERE l.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);

    @Query("SELECT l.project.id FROM Like l WHERE l.user.id = :userId")
    Page<Long> findLikedProjectIdsByUserId(@Param("userId") Long userId, Pageable pageable);
}
