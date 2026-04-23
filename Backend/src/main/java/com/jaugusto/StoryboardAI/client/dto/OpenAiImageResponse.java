package com.jaugusto.StoryboardAI.client.dto;

import java.util.List;

public record OpenAiImageResponse(
        Long created,
        List<OpenAiImageData> data
) {}