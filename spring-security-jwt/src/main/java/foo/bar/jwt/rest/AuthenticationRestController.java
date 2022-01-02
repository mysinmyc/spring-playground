package foo.bar.jwt.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import foo.bar.jwt.security.JwtTokenUtil;


@RestController
public class AuthenticationRestController {

	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@RequestMapping({"/auth/createToken"})
	public String createToken(@RequestParam("userName") String userName,@RequestParam("password") String password ) {		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
		UserDetails user = userDetailsService.loadUserByUsername(authentication.getName());
		return jwtTokenUtil.generateToken(user);
	}
	
}
