package rbs.cpb.usp.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class EmailProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		/*String payload = exchange.getIn().getBody(String.class);
		String id = null;
		if (payload.equalsIgnoreCase("indubalas@gmail.com"))
			id = "balas";
		else if (payload.equalsIgnoreCase("rajam@gmail.com"))
			id = "mraj";
		else
			id = "This is not a valid mail";

		exchange.getIn().setBody(id);*/

		String payload = exchange.getIn().getBody(String.class);

		// logic to getAttachments
		JSONObject jsonObject = new JSONObject(payload);
		jsonObject.put("attachments", new HashMap<String, String>() {{
			put("Attachment File-1", "Attachment File-Content");
		}});

		exchange.getIn().setBody(String.valueOf(jsonObject));

	}

}
