package com.suraj.controller;

import com.suraj.modal.Chat;
import com.suraj.modal.Message;
import com.suraj.modal.User;
import com.suraj.request.CreateMessageRequest;
import com.suraj.service.MessageService;
import com.suraj.service.ProjectService;
import com.suraj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @PostMapping("/send")
    private ResponseEntity<Message> sendMessage(@RequestBody CreateMessageRequest request) throws Exception{
        User user = userService.findUserById(request.getSenderId());
        if (user == null){
            throw new Exception("User not found with id: "+request.getSenderId());
        }
        Chat chat = projectService.getProjectById(request.getProjectId()).getChat();
        if (chat == null){
            throw new Exception("Chat not found");
        }

        Message sendMessage = messageService.sendMessage(request.getSenderId(), request.getProjectId(),request.getContent());
        return ResponseEntity.ok(sendMessage);
    }


    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessagesByChatId(@PathVariable Long projectId) throws Exception {
        List<Message> messages = messageService.getMessagesByProjectId(projectId);
        return ResponseEntity.ok(messages);
    }
}
