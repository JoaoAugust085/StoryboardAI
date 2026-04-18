package com.jaugusto.StoryboardAI.repository;

import com.jaugusto.StoryboardAI.model.StoryboardModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryboardRepository extends JpaRepository<StoryboardModel, Long> {
}
