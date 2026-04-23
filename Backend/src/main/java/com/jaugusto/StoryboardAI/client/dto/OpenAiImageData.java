package com.jaugusto.StoryboardAI.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OpenAiImageData(
        @JsonProperty("b64_json") String b64Json
) {}