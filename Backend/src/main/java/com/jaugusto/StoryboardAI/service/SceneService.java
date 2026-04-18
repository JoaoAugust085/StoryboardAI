package com.jaugusto.StoryboardAI.service;


import com.jaugusto.StoryboardAI.model.SceneModel;
import com.jaugusto.StoryboardAI.model.StoryboardModel;
import com.jaugusto.StoryboardAI.repository.SceneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SceneService {

    private static final int MAX_SCENES = 6;

    private final SceneRepository sceneRepository;
    private final StoryboardService storyboardService;

    public SceneModel addSceneToStoryboard(Long storyboardId, SceneModel scene) {
        StoryboardModel storyboard = storyboardService.findById(storyboardId);
        if (storyboard == null) {
            return null;
        }

        if (storyboard.getScenes().size() >= MAX_SCENES) {
            return null;
        }

        storyboard.addScene(scene);
        return sceneRepository.save(scene);
    }

    public SceneModel findById(Long id) {
        return sceneRepository.findById(id).orElse(null);
    }

    public SceneModel update(Long id, SceneModel updated) {
        SceneModel existing = findById(id);
        if (existing == null) {
            return null;
        }
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setOrder(updated.getOrder());
        return sceneRepository.save(existing);
    }

    public boolean delete(Long id) {
        if (!sceneRepository.existsById(id)) {
            return false;
        }
        sceneRepository.deleteById(id);
        return true;
    }
}