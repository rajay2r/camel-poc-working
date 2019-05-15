package rbs.cpb.usp.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rbs.cpb.usp.camel.processor.*;

@Component
public class CamelEmailRouteHandler extends RouteBuilder {

	private EmailAttachmentProcessor emailAttachmentProcessor;
	private OCRServiceProcessor ocrServiceProcessor;
	private TopicDetectionAIProcessor topicDetectionAIProcessor;
	private DataExtractionAIProcessor dataExtractionAIProcessor;
	private EDSStopChequeProcessor edsStopChequeProcessor;
	private DemoService demoService;

	@Autowired
	public CamelEmailRouteHandler(EmailAttachmentProcessor emailAttachmentProcessor,
								  OCRServiceProcessor ocrServiceProcessor,
								  TopicDetectionAIProcessor topicDetectionAIProcessor,
								  DataExtractionAIProcessor dataExtractionAIProcessor,
								  DemoService demoService,
								  EDSStopChequeProcessor edsStopChequeProcessor) {
		this.emailAttachmentProcessor = emailAttachmentProcessor;
		this.ocrServiceProcessor = ocrServiceProcessor;
		this.topicDetectionAIProcessor = topicDetectionAIProcessor;
		this.dataExtractionAIProcessor = dataExtractionAIProcessor;
		this.edsStopChequeProcessor = edsStopChequeProcessor;
		this.demoService = demoService;
	}

	@Override
	public void configure() throws Exception {

		// "direct:start"
		// "timer://myTimer?period=2000"
		// "timer://runOnce?repeatCount=1&delay=5000"

		/*from("timer://runOnce?repeatCount=3").to("class:rbs.cpb.usp.camel.DemoService?method=initiateEmailNotificationProcess")
				.choice()
				.when(body().isEqualTo("indubalas@gmail.com")).process(eprocessor)
				.to("class:rbs.cpb.usp.camel.DemoService?method=requestHandlerFirst(${body})")
				.when(body().isEqualTo("rajam@gmail.com")).process(eprocessor)
				.to("class:rbs.cpb.usp.camel.DemoService?method=requestHandlerSecond(${body})")
				.otherwise()
				.log("Please check your MailId is valid");*/

		onException(Exception.class).maximumRedeliveries(2).maximumRedeliveryDelay(500);

		from("direct:mailNotificationProcess")
				//.autoStartup(false)
				.routeId("mailNotificationProcess")
				.choice()
					// If Mail from valid source
					.when(method(this.demoService, "isMailSrcValid(${body})"))
						.choice()
							// If hasAttachments=true perform getAttachment and OCR-Service for the attachments
							.when(method(this.demoService, "hasAttachment(${body})")).process(this.emailAttachmentProcessor).process(this.ocrServiceProcessor)
						.endChoice()
						// Topic Detection call
						.process(this.topicDetectionAIProcessor)
							.choice()
								// If STOP CHEQUE identified
								.when(method(this.demoService, "isStopChequeTopic(${body})"))
									// Data Extraction call
									.process(this.dataExtractionAIProcessor)
										.choice()
											// If isDataExtractionValid
											.when(method(this.demoService, "isDataExtractionValid(${body})"))
												// EDS Service call
												.process(this.edsStopChequeProcessor)
										// Exception case 2: Stop Cheque Topic not detected
										.otherwise().bean(this.demoService, "handleDataExtractionValidationException")//.to("bean:demoService?method=handleDataExtractionValidationException")
										.endChoice()
							// Exception case 3: DataExtraction Validation Exception
							.otherwise().bean(this.demoService, "handleStopChequeNotDetected")
							.endChoice()
					// Exception case 1: Mail source not valid
					.otherwise().bean(this.demoService, "handleMailSrcNotValid");
	}
}
