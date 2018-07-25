package us.ivanshyrai.portfolio.model;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import us.ivanshyrai.portfolio.model.audit.DateAudit;
import us.ivanshyrai.portfolio.model.audit.UserDateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 150)
    private String title;

    @NotBlank
    @Size(max = 500)
    private String description;

    @OneToMany(mappedBy = "project",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @Size(min = 1, max = 10)
    private List<ProjectImage> projectImages = new ArrayList<>();


    @OneToMany(mappedBy = "project",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

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

    public List<ProjectImage> getProjectImages() {
        return projectImages;
    }

    public void setProjectImages(List<ProjectImage> projectImages) {
        this.projectImages = projectImages;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public void addLike(Like like) {
        likes.add(like);
        like.setProject(this);
    }

    public void removeLike(Like like) {
        likes.remove(like);
        like.setProject(null);
    }
}
