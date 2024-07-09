package com.hk.webrtc_study.chat.controller;

import com.hk.webrtc_study.chat.dto.ChatRoom;
import com.hk.webrtc_study.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService service;

    @GetMapping
    public List<ChatRoom> findAllRooms() {
        return service.findAllRoom();
    }

    @PostMapping
    public ChatRoom createRoom(@RequestParam String name) {
        return service.createRoom(name);
    }
}
