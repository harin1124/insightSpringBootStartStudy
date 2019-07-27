package board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;

@SpringBootApplication(exclude={MultipartAutoConfiguration.class}) //스프링 부트의 자동구성 사용 시, 특정 자동구성 사용하지 않도록 함.
public class BoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}

}
