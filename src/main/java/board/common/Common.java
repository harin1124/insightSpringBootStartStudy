package board.common;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Common {
	public String getRedirectAddress(String address){
		return "redirect:" + address;
	}
}