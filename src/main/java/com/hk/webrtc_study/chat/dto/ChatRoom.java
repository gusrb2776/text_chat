package com.hk.webrtc_study.chat.dto;

import com.hk.webrtc_study.chat.service.ChatService;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Data
public class ChatRoom {
    private String roomId;
    private String name;
    // 채팅방에 누가 있는지 담고있는 컬렉션임.
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    public void handleAction(WebSocketSession session, ChatDTO message, ChatService service) {
        /*
        메시지에 담긴 타입이 ENTER라면 채팅방에 입장하는거임.
         */
        if(message.getType().equals(ChatDTO.MessageType.ENTER)){
            sessions.add(session);

            // 입장했따는 메서드 띄우기
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
            sendMessage(message, service);
        }else if(message.getType().equals(ChatDTO.MessageType.TALK)){
            message.setMessage(message.getMessage());
            sendMessage(message, service);
        }
    }

    /**
     * 현재 채팅방에 있는 모든 session(유저)에게 handleAcgtion으로 부터 넘어온 message를 전달하는 메서드
     */
    public <T> void sendMessage(T message, ChatService service){
        sessions.parallelStream().forEach(session -> service.sendMessage(session, message));
    }


}
