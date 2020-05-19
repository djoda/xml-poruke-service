package com.example.demo.controller;

import com.example.demo.dto.MessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/message")
public class MessageController {

    @GetMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> getUserMessages(@PathVariable Long id){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<MessageDTO> addMessages(@RequestBody MessageDTO messageDTO){
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
