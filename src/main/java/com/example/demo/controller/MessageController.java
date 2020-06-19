package com.example.demo.controller;

import com.example.demo.dto.MessageDTO;
import com.example.demo.security.TokenUtils;
import com.example.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private TokenUtils tokenUtils;

    @GetMapping()
    public ResponseEntity<List<MessageDTO>> getUserMessages(HttpServletRequest request){
        String token = tokenUtils.getToken(request);
        String username = tokenUtils.getUsernameFromToken(token);
        return new ResponseEntity<>(messageService.getUserMessages(username),HttpStatus.OK);
    }

    @PostMapping( value = "/reply",consumes = "application/json")
    public ResponseEntity<MessageDTO> addMessages(@RequestBody MessageDTO messageDTO){
        return new ResponseEntity<>(messageService.addMessage(messageDTO),HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<MessageDTO> addMessages(@RequestBody MessageDTO messageDTO,HttpServletRequest request){
        String token = tokenUtils.getToken(request);
        String username = tokenUtils.getUsernameFromToken(token);
        messageDTO.setSenderUsername(username);
        return new ResponseEntity<>(messageService.addMessage(messageDTO),HttpStatus.OK);
    }
}
