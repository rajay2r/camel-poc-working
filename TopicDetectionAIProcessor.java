package rbs.cpb.usp.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TopicDetectionAIProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("In TopicDetectionAIProcessor");
        String payload = exchange.getIn().getBody(String.class);

        // Logic to invoke "Topic Detection" to identify the 'Topic'
        String topicIdentified = "STOP CHEQUE";

        JSONObject jsonObject = new JSONObject(payload);
        jsonObject.put("topicIdentified", topicIdentified);

        exchange.getIn().setBody(String.valueOf(jsonObject));
    }
}
