package us.ivanshyrai.portfolio.payload;

import java.time.Instant;
import java.util.List;

public class ProjectResponse {
    private Long id;
    private String title;
    private String description;
    private List<LikeResponse> likes;
    private UserSummary createdBy;
    private Instant creationDateTime;

    private Long totalLikes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<LikeResponse> getLikes() {
        return likes;
    }

    public void setLikes(List<LikeResponse> likes) {
        this.likes = likes;
    }

    public UserSummary getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserSummary createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(Instant creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public Long getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(Long totalLikes) {
        this.totalLikes = totalLikes;
    }
}
