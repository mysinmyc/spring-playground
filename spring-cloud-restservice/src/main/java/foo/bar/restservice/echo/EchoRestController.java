package foo.bar.restservice.echo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoRestController {

	
	static final String DEFAULT="__DEFAULT__";
	
	@Value("${echo.message.default:Hello!!!}")
	String echoMessageDefault;
	
	@GetMapping("/echo")
	public Message echo(@Value("${myProcessId}")String processId, @RequestParam(name="message",defaultValue = DEFAULT) String message) {
		Message result = new Message();
		result.setMessage(DEFAULT.equals(message) ? echoMessageDefault : message);
		result.setMeta(processId);
		return result;
	}
}
