package rbs.cpb.usp.camel;

import org.apache.camel.ProducerTemplate;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class DemoService {

	// Spring Boot creates and inject the ProducerTemplate
	@Autowired
	private ProducerTemplate producerTemplate;

	/*public DemoService(ProducerTemplate producerTemplate) {
		this.producerTemplate = producerTemplate;
	}*/

	String userMail = "rajam@gmail.com";

	public String saySomething() {
		System.out.println("say");
		return "Hello All";
	}

	public String emailService() {
		return userMail;
	}

	public void requestHandlerFirst(String id) {
		System.out.println("requestHandlerFirst :" + id);
	}

	public void requestHandlerSecond(String id) {
		System.out.println("requestHandlerSecond :" + id);
	}

	@GetMapping("/beginCamelProcess")
	public void beginCamelProcess() {
		try {
			producerTemplate.sendBody("direct:mailNotificationProcess", initiateEmailNotificationProcess());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String initiateEmailNotificationProcess() {
		System.out.println("In initiateEmailNotificationProcess");

		JSONObject emailDet = new JSONObject();
		emailDet.put("itemId", "Ite-id-12389u7-jkaksdf-988");
		emailDet.put("hasAttachment", true);
		emailDet.put("mailSrc", "sender");
		emailDet.put("timeStamp", (new Date()).toString());

		return emailDet.toString();
	}

	public boolean isMailSrcValid(String body) {
		System.out.println("In isMailSrcValid");
		boolean isMailSrcValid = true;
		System.out.println("---isMailSrcValid="+isMailSrcValid);
		return isMailSrcValid;
	}

	public void handleMailSrcNotValid() {
		System.out.println("In handleMailSrcNotValid");

	}

	public boolean hasAttachment(String body) {
		System.out.println("In hasAttachment");
		JSONObject emailDet = new JSONObject(body);
		System.out.println("---hasAttachment="+emailDet.getBoolean("hasAttachment"));
		return emailDet.getBoolean("hasAttachment");
	}

	public boolean isStopChequeTopic(String body) {
		System.out.println("In isStopChequeTopic");
		JSONObject emailDet = new JSONObject(body);
		boolean isStopChequeTopic = "STOP CHEQUE".equalsIgnoreCase(emailDet.getString("topicIdentified"));
		System.out.println("---isStopChequeTopic="+isStopChequeTopic);
		return isStopChequeTopic;
	}

	public void handleStopChequeNotDetected() {
		System.out.println("In handleStopChequeNotDetected");
	}

	public boolean isDataExtractionValid(String body) {
		System.out.println("In isDataExtractionValid");
		JSONObject emailDet = new JSONObject(body);
		System.out.println("---isDataExtractionValid="+emailDet.getBoolean("isDataExtractionValid"));
		return emailDet.getBoolean("isDataExtractionValid");
	}

	public void handleDataExtractionValidationException() {
		System.out.println("In handleDataExtractionValidationException");
	}

}
