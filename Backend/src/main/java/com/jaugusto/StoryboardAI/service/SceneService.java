package com.jaugusto.StoryboardAI.service;

import com.jaugusto.StoryboardAI.model.SceneModel;
import com.jaugusto.StoryboardAI.model.StoryboardModel;
import com.jaugusto.StoryboardAI.repository.SceneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SceneService {

    private static final int MAX_SCENES_PER_STORYBOARD = 6;

    private final SceneRepository sceneRepository;
    private final StoryboardService storyboardService;

    public SceneModel addSceneToStoryboard(Long storyboardId, SceneModel scene) {
        StoryboardModel storyboard = storyboardService.findStoryboardById(storyboardId);
        if (storyboard == null) {
            return null;
        }

        if (hasReachedSceneLimit(storyboard)) {
            return null;
        }

        storyboard.addScene(scene);
        return sceneRepository.save(scene);
    }

    public SceneModel findSceneById(Long id) {
        return sceneRepository.findById(id).orElse(null);
    }

    public SceneModel updateSceneContent(Long id, SceneModel updatedData) {
        SceneModel existingScene = findSceneById(id);
        if (existingScene == null) {
            return null;
        }
        existingScene.setTitle(updatedData.getTitle());
        existingScene.setDescription(updatedData.getDescription());
        existingScene.setOrder(updatedData.getOrder());
        return sceneRepository.save(existingScene);
    }

    public boolean deleteSceneById(Long id) {
        if (!sceneRepository.existsById(id)) {
            return false;
        }
        sceneRepository.deleteById(id);
        return true;
    }

    private boolean hasReachedSceneLimit(StoryboardModel storyboard) {
        return storyboard.getScenes().size() >= MAX_SCENES_PER_STORYBOARD;
    }
}