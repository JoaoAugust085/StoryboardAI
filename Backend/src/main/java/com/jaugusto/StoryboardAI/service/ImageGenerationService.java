package com.jaugusto.StoryboardAI.service;

import com.jaugusto.StoryboardAI.client.OpenAiImageClient;
import com.jaugusto.StoryboardAI.model.SceneModel;
import com.jaugusto.StoryboardAI.model.StoryboardModel;
import com.jaugusto.StoryboardAI.repository.StoryboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageGenerationService {

    private final StoryboardService storyboardService;
    private final StoryboardRepository storyboardRepository;
    private final OpenAiImageClient openAiImageClient;

    public StoryboardModel generateImagesForStoryboard(Long storyboardId) {
        StoryboardModel storyboard = storyboardService.findStoryboardById(storyboardId);
        if (storyboard == null) {
            return null;
        }

        if (storyboard.getScenes().isEmpty()) {
            return null;
        }

        for (SceneModel scene : storyboard.getScenes()) {
            String prompt = buildPromptFromScene(scene);
            byte[] generatedImage = openAiImageClient.generateImageFromPrompt(prompt);
            scene.setGeneratedImage(generatedImage);
        }

        return storyboardRepository.save(storyboard);
    }

    private String buildPromptFromScene(SceneModel scene) {
        return scene.getTitle() + ". " + scene.getDescription();
    }
}