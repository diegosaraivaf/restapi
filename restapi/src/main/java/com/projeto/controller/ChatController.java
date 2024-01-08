package com.projeto.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.projeto.dto.Message;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    
    //funciona como um 'endpoint' que recebe as mensaguens do cliente o nome do endpoint fica sedo referenciado como '/app/message' por causa do prefixo setado na configuracao
    @MessageMapping("/message")
    //nome topico que os cliente se inscreverem, todas as mensagem enviadas para o '/app/message' serao enviadas para os clientes que se inscrevem no topico  'chatroom/public'
    @SendTo("/chatroom/public")
    public Message receiveMessage(@Payload Message message){
        return message;
    }
    
    //endpoint com o topico dinamico
    @MessageMapping("/private-message")
    public Message recMessage(@Payload Message message){
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        System.out.println(message.toString());
        return message;
    }
}
