package us.ivanshyrai.portfolio.model;

public class ProjectLikeCount {

    private Long projectId;
    private Long likeCount;

    public ProjectLikeCount(Long projectId, Long likeCount) {
        this.projectId = projectId;
        this.likeCount = likeCount;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }
}
