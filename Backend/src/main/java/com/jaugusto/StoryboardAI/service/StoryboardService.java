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

    public StoryboardModel create(StoryboardModel storyboard) {
        return storyboardRepository.save(storyboard);
    }

    public List<StoryboardModel> findAll() {
        return storyboardRepository.findAll();
    }

    public StoryboardModel findById(Long id) {
        return storyboardRepository.findById(id).orElse(null);
    }

    public StoryboardModel update(Long id, StoryboardModel updated) {
        StoryboardModel existing = findById(id);
        if (existing == null) {
            return null;
        }
        existing.setTitle(updated.getTitle());
        return storyboardRepository.save(existing);
    }

    public boolean delete(Long id) {
        if (!storyboardRepository.existsById(id)) {
            return false;
        }
        storyboardRepository.deleteById(id);
        return true;
    }
}
