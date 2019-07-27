package board.common;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice //예외 처리 클래스임을 알리는 어노테이션
public class ExceptionHandler {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ModelAndView defaultExceptionHandler(HttpServletRequest request, Exception exception){
		System.out.println("핸들러 진입");
		ModelAndView mv = new ModelAndView("/error/errorDefault");
		mv.addObject("exception", exception);
		log.error("exception", exception);
		return mv;
	}
}