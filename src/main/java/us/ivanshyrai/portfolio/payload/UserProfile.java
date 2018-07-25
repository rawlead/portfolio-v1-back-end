package us.ivanshyrai.portfolio.payload;

import java.time.Instant;

public class UserProfile {

    private Long id;
    private String username;
    private String name;
    private Instant joinedAt;
    private Long projectCount;
    private Long likeCount;

    public UserProfile(Long id, String username, String name, Instant joinedAt, Long projectCount, Long likeCount) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.joinedAt = joinedAt;
        this.projectCount = projectCount;
        this.likeCount = likeCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Instant joinedAt) {
        this.joinedAt = joinedAt;
    }

    public Long getProjectCount() {
        return projectCount;
    }

    public void setProjectCount(Long projectCount) {
        this.projectCount = projectCount;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }
}
