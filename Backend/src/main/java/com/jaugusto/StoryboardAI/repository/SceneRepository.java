package com.jaugusto.StoryboardAI.repository;

import com.jaugusto.StoryboardAI.model.SceneModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SceneRepository extends JpaRepository<SceneModel, Long> {
}