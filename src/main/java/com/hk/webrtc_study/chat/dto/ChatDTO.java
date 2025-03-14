package com.hk.webrtc_study.chat.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatDTO {

    public enum MessageType{
        ENTER, TALK
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
    private String time;
}
