package com.jaugusto.StoryboardAI.client;

import com.jaugusto.StoryboardAI.client.dto.OpenAiImageData;
import com.jaugusto.StoryboardAI.client.dto.OpenAiImageRequest;
import com.jaugusto.StoryboardAI.client.dto.OpenAiImageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;

@Component
@RequiredArgsConstructor
public class OpenAiImageClient {

    private final WebClient openAiWebClient;

    @Value("${openai.api.model}")
    private String model;

    @Value("${openai.api.image-size}")
    private String imageSize;

    public byte[] generateImageFromPrompt(String prompt) {
        OpenAiImageRequest requestBody = new OpenAiImageRequest(
                model,
                prompt,
                1,
                imageSize
        );

        OpenAiImageResponse response = openAiWebClient
                .post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(OpenAiImageResponse.class)
                .block();

        if (response == null || response.data() == null || response.data().isEmpty()) {
            return null;
        }

        OpenAiImageData imageData = response.data().get(0);
        if (imageData.b64Json() == null) {
            return null;
        }

        return Base64.getDecoder().decode(imageData.b64Json());
    }
}