package com.jaugusto.StoryboardAI.client.dto;

public record OpenAiImageRequest(
        String model,
        String prompt,
        Integer n,
        String size
) {}