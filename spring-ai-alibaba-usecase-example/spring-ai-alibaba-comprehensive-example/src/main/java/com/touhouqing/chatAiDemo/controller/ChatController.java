package com.touhouqing.chatAiDemo.controller;


import com.touhouqing.chatAiDemo.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.content.Media;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ai")
public class ChatController {
    private final ChatClient chatClient;

    private final ChatHistoryRepository chatHistoryRepository;

    @RequestMapping(value="/chat", produces = "text/html;charset=utf-8")
    public Flux<String> chat(
            @RequestParam("prompt") String prompt,
            @RequestParam("chatId") String chatId,
            @RequestParam(value = "files", required = false) List<MultipartFile> files){
        //保存会话id
        chatHistoryRepository.save("chat", chatId);
        //请求模型
        if(files==null || files.isEmpty()){
            return textChat(prompt, chatId);
        }
        else{
            return multiModalChat(prompt, chatId, files);
        }
    }
    private Flux<String> textChat(String prompt, String chatId){
        return chatClient.prompt()
                .user(prompt)
                .advisors(a -> a.param(CONVERSATION_ID, chatId))
                .stream()
                .content();
    }
    private Flux<String> multiModalChat(String prompt, String chatId, List<MultipartFile> files){
        List<Media> medias = files.stream()
                .map(file -> new Media(
                        MimeType.valueOf(Objects.requireNonNull(file.getContentType())),
                        file.getResource()
                    )
                ).toList();
        return chatClient.prompt()
                .user(p -> p.text(prompt).media(medias.toArray(Media[]::new)))
                .advisors(a -> a.param(CONVERSATION_ID, chatId))
                .stream()
                .content();
    }
}
