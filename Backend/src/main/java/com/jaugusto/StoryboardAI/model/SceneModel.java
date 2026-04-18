package com.jaugusto.StoryboardAI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "scenes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SceneModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A ordem da cena é obrigatória")
    @Min(value = 1, message = "A ordem mínima é 1")
    @Max(value = 6, message = "A ordem máxima é 6")
    @Column(name = "scene_order", nullable = false)
    private Integer order;

    @NotBlank(message = "O título da cena é obrigatório")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "A descrição da cena é obrigatória")
    @Column(nullable = false, length = 1000)
    private String description;

    @Lob
    @JsonIgnore
    @Column(name = "generated_image")
    private byte[] generatedImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storyboard_id", nullable = false)
    @JsonIgnore
    private StoryboardModel storyboard;
}