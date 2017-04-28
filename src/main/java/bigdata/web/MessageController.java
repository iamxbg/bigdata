//package bigdata.web;
//
//import javax.annotation.Resource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//public class MessageController {
//
//	static int count=0;
//	static int template_count=0;
//
//	@Autowired
//	private SimpMessagingTemplate msgTemplate;
//	
//	@MessageMapping("/test")
//	public String test(@Payload String message){
//	
//		count++;
//
//		System.out.println(count+" : enter Messgge Controller");
//		System.out.println("payload:"+message);
//		
//		return count+message;
//	}
//	
//	
//	@RequestMapping(path="/testMsgTemplate")
//	@SendTo("/topic/test")
//	@MessageMapping
//	public ResponseEntity doMsgtemplatetest(){
//		template_count++;
//		
//		msgTemplate.convertAndSend("/topic/test"," from Message Template :: "+template_count);
//		return new ResponseEntity<>(HttpStatus.OK);
//	}
//	
//	
//	
//	
//}
