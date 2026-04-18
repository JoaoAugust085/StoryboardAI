package com.jaugusto.StoryboardAI.service;

import com.jaugusto.StoryboardAI.model.StoryboardModel;
import com.jaugusto.StoryboardAI.repository.StoryboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoryboardService {

    private final StoryboardRepository storyboardRepository;

    public StoryboardModel createStoryboard(StoryboardModel storyboard) {
        return storyboardRepository.save(storyboard);
    }

    public List<StoryboardModel> findAllStoryboards() {
        return storyboardRepository.findAll();
    }

    public StoryboardModel findStoryboardById(Long id) {
        return storyboardRepository.findById(id).orElse(null);
    }

    public StoryboardModel updateStoryboardTitle(Long id, StoryboardModel updatedData) {
        StoryboardModel existingStoryboard = findStoryboardById(id);
        if (existingStoryboard == null) {
            return null;
        }
        existingStoryboard.setTitle(updatedData.getTitle());
        return storyboardRepository.save(existingStoryboard);
    }

    public boolean deleteStoryboardById(Long id) {
        if (!storyboardRepository.existsById(id)) {
            return false;
        }
        storyboardRepository.deleteById(id);
        return true;
    }
}