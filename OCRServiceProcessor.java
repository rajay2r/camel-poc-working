package rbs.cpb.usp.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class OCRServiceProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("In OCRServiceProcessor");
        String payload = exchange.getIn().getBody(String.class);

        // OCR-Service logic
        JSONObject jsonObject = new JSONObject(payload);
        jsonObject.put("mailAttachExtractedText", new HashMap<String, String>() {{
            put("Attachment File-1", "Attachment File Text-Content");
        }});

        exchange.getIn().setBody(String.valueOf(jsonObject));
        throw new Exception();
    }
}
