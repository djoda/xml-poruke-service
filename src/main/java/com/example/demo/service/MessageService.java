package com.example.demo.service;

import com.example.demo.config.TLSConfiguration;
import com.example.demo.dto.MessageDTO;
import com.example.demo.model.Message;
import com.example.demo.repository.MessageRepository;
import com.example.demo.security.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private RestTemplate restTemplate;


    public boolean addMessage(MessageDTO messageDTO,String token){
        if(checkRequestState(token,messageDTO.getSenderUsername(),messageDTO.getReceiverUsername())) {
            Message mess = new Message();
            mess.setReceiverUsername(messageDTO.getReceiverUsername());
            mess.setSenderUsername(messageDTO.getSenderUsername());
            mess.setSubject(messageDTO.getSubject());
            mess.setText(messageDTO.getText());
            mess.setTimeSent(messageDTO.getTimeSent());
            messageRepository.save(mess);
            return true;
        }else
            return false;
    }

    public boolean checkRequestState(String token,String senderUsername,String receiverUsername){

        String role = tokenUtils.getRoleFromToken(token);

        String customerUsername = "";
        String agentUsername =  "";

        if(role.equals("ROLE_CUSTOMER"))
        {
            customerUsername = senderUsername;
            agentUsername = receiverUsername;
        }else {
            customerUsername = receiverUsername;
            agentUsername = senderUsername;
        }

        final String url = TLSConfiguration.URL + "orders/request/messageCheck/{customerUsername}/{agentUsername}";
        Map<String, String> params = new HashMap<String, String>();
        params.put("customerUsername",customerUsername);
        params.put("agentUsername",agentUsername);
        boolean result = restTemplate.getForObject(url, Boolean.class, params);
        return  result;
    }

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
