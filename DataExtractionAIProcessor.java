package rbs.cpb.usp.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class DataExtractionAIProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("In DataExtractionAIProcessor");
        String payload = exchange.getIn().getBody(String.class);

        // Logic to invoke "Data Extraction" to extract the required data

        // Validate "Data Extraction" response

        // 1. Is extracted data sufficient?

        // 2. Is Sort-code to Account mapping correct?
        //findBrand(sortCode);

        boolean isDataExtractionValid = false;

        JSONObject jsonObject = new JSONObject(payload);
        jsonObject.put("isDataExtractionValid", isDataExtractionValid);
        jsonObject.put("stopChequeJsonStr", "");//String.valueOf( createEDSStopChequeServiceRequest() ));

        exchange.getIn().setBody(String.valueOf(jsonObject));
    }
}
