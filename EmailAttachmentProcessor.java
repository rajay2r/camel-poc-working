package rbs.cpb.usp.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class EmailAttachmentProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("In EmailAttachmentProcessor");
        String payload = exchange.getIn().getBody(String.class);

        // logic to getAttachments
        JSONObject jsonObject = new JSONObject(payload);
        jsonObject.put("attachments", new HashMap<String, String>() {{
            put("Attachment File-1", "Attachment Byte[]-Content");
        }});

        exchange.getIn().setBody(String.valueOf(jsonObject));
    }
}
