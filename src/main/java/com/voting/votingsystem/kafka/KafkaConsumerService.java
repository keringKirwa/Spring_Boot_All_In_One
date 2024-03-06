package com.voting.votingsystem.kafka;

import com.voting.votingsystem.sockets.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumerService {
    private static final String TOPIC_PUBLIC_CHAT = "public-chats";
    private final SimpMessagingTemplate messagingTemplate;
    private final List<ChatMessage> chatMessages = new ArrayList<>();

    @KafkaListener(topics = TOPIC_PUBLIC_CHAT, groupId = "my-group")
    public void handleMessage(ChatMessage message) {
        log.info("Received message from Kafka: {}", message);
        chatMessages.add(message);
        messagingTemplate.convertAndSend("/topic/public", message);
    }
    public List<ChatMessage> getChatMessages() {

        return new ArrayList<>(chatMessages);
    }
}
