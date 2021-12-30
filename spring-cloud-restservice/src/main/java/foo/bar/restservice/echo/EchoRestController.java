package foo.bar.restservice.echo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoRestController {

	
	@GetMapping("/echo")
	public Message echo(@Value("${myProcessId}")String processId, @RequestParam(name="message",defaultValue = "Hello!!!") String message) {
		Message result = new Message();
		result.setMessage(message);
		result.setMeta(processId);
		return result;
	}
}
