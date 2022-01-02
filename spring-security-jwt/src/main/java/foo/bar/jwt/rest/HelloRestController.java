package foo.bar.jwt.rest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRestController {

	@RequestMapping({ "/hello" })
	public String hello() {
		return "hello " + SecurityContextHolder.getContext().getAuthentication().getName();
	}
}
