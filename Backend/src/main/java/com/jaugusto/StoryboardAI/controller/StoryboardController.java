package com.jaugusto.StoryboardAI.controller;


import com.jaugusto.StoryboardAI.model.StoryboardModel;
import com.jaugusto.StoryboardAI.service.StoryboardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/storyboards")
@RequiredArgsConstructor
public class StoryboardController {

    private final StoryboardService storyboardService;

    @PostMapping
    public ResponseEntity<StoryboardModel> createStoryboard(@Valid @RequestBody StoryboardModel storyboard) {
        StoryboardModel createdStoryboard = storyboardService.createStoryboard(storyboard);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStoryboard);
    }

    @GetMapping
    public ResponseEntity<List<StoryboardModel>> findAllStoryboards() {
        return ResponseEntity.ok(storyboardService.findAllStoryboards());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoryboardModel> findStoryboardById(@PathVariable Long id) {
        StoryboardModel storyboard = storyboardService.findStoryboardById(id);
        if (storyboard == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(storyboard);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoryboardModel> updateStoryboardTitle(@PathVariable Long id,
                                                                 @Valid @RequestBody StoryboardModel storyboard) {
        StoryboardModel updatedStoryboard = storyboardService.updateStoryboardTitle(id, storyboard);
        if (updatedStoryboard == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedStoryboard);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStoryboardById(@PathVariable Long id) {
        boolean wasDeleted = storyboardService.deleteStoryboardById(id);
        if (!wasDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}/final-image", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getStoryboardFinalImage(@PathVariable Long id) {
        StoryboardModel storyboard = storyboardService.findStoryboardById(id);
        if (storyboard == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] finalImage = storyboard.getFinalImage();
        if (finalImage == null || finalImage.length == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(finalImage);
    }
}