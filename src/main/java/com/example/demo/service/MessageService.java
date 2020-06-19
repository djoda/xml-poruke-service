package com.example.demo.service;

import com.example.demo.dto.MessageDTO;
import com.example.demo.model.Message;
import com.example.demo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public MessageDTO addMessage(MessageDTO messageDTO){
        Message mess = new Message();
        mess.setReceiverUsername(messageDTO.getReceiverUsername());
        mess.setSenderUsername(messageDTO.getSenderUsername());
        mess.setSubject(messageDTO.getSubject());
        mess.setText(messageDTO.getText());
        mess.setTimeSent(messageDTO.getTimeSent());
        messageRepository.save(mess);
        return messageDTO;
    }

    public void checkRequestState(){}

    public List<MessageDTO> getUserMessages(String username){
        List<Message> messages = messageRepository.findAllByReceiverUsername(username);
        List<MessageDTO> dtos = new ArrayList<MessageDTO>();
        for(Message m : messages)
        {
            dtos.add(new MessageDTO(m));
        }
        return dtos;
    }
}
