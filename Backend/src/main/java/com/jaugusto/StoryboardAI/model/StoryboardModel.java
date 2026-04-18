package com.jaugusto.StoryboardAI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jaugusto.StoryboardAI.model.SceneModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "storyboards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoryboardModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título do storyboard é obrigatório")
    @Column(nullable = false)
    private String title;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Lob
    @JsonIgnore
    @Column(name = "final_image")
    private byte[] finalImage;

    @OneToMany(
            mappedBy = "storyboard",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @Size(max = 6, message = "Um storyboard pode ter no máximo 6 cenas")
    private List<SceneModel> scenes = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public void addScene(SceneModel scene) {
        scenes.add(scene);
        scene.setStoryboard(this);
    }

    public void removeScene(SceneModel scene) {
        scenes.remove(scene);
        scene.setStoryboard(null);
    }
}