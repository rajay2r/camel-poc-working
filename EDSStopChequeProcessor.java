package rbs.cpb.usp.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class EDSStopChequeProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("In EDSStopChequeProcessor");
        String payload = exchange.getIn().getBody(String.class);

        // Logic to invoke "EDS Service for Stop Cheque Topic"

        boolean isDataExtractionValid = false;

        JSONObject jsonObject = new JSONObject(payload);
        jsonObject.put("edsSRId", "SR129876");

        exchange.getIn().setBody(String.valueOf(jsonObject));
    }
}
