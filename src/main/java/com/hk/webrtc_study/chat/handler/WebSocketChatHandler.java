package com.hk.webrtc_study.chat.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hk.webrtc_study.chat.dto.ChatDTO;
import com.hk.webrtc_study.chat.dto.ChatRoom;
import com.hk.webrtc_study.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;

    private final ChatService service;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 내용 갖고오기
        String payload = message.getPayload();
        log.info("payload: {}", payload);

        // ObjectMapper로 메시지 읽어서 바로 DTO로 바꾸기
        ChatDTO chatMessage = objectMapper.readValue(payload, ChatDTO.class);
        log.info("session : {}", chatMessage.toString());

        ChatRoom room = service.findRoomById(chatMessage.getRoomId());
        log.info("room : {}", room.toString());

        room.handleAction(session, chatMessage, service);
    }
}
