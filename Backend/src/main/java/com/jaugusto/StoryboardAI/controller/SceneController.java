package com.jaugusto.StoryboardAI.controller;


import com.jaugusto.StoryboardAI.model.SceneModel;
import com.jaugusto.StoryboardAI.service.SceneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SceneController {

    private final SceneService sceneService;

    @PostMapping("/storyboards/{storyboardId}/scenes")
    public ResponseEntity<SceneModel> addSceneToStoryboard(@PathVariable Long storyboardId,
                                                           @Valid @RequestBody SceneModel scene) {
        SceneModel createdScene = sceneService.addSceneToStoryboard(storyboardId, scene);
        if (createdScene == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdScene);
    }

    @GetMapping("/scenes/{id}")
    public ResponseEntity<SceneModel> findSceneById(@PathVariable Long id) {
        SceneModel scene = sceneService.findSceneById(id);
        if (scene == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(scene);
    }

    @PutMapping("/scenes/{id}")
    public ResponseEntity<SceneModel> updateSceneContent(@PathVariable Long id,
                                                         @Valid @RequestBody SceneModel scene) {
        SceneModel updatedScene = sceneService.updateSceneContent(id, scene);
        if (updatedScene == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedScene);
    }

    @DeleteMapping("/scenes/{id}")
    public ResponseEntity<Void> deleteSceneById(@PathVariable Long id) {
        boolean wasDeleted = sceneService.deleteSceneById(id);
        if (!wasDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/scenes/{id}/image", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getSceneGeneratedImage(@PathVariable Long id) {
        SceneModel scene = sceneService.findSceneById(id);
        if (scene == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] generatedImage = scene.getGeneratedImage();
        if (generatedImage == null || generatedImage.length == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(generatedImage);
    }
}