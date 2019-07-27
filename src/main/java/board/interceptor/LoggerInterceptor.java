package board.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggerInterceptor extends HandlerInterceptorAdapter{
	//private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override //BEFORE CONTROLLER 
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)throws Exception{
		log.debug("===========================START===========================");
		log.debug("REQUEST URI :" + req.getRequestURI());
		return super.preHandle(req, res, handler);
	}
	
	@Override //AFTER CONTROLLER
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView modelAndView)throws Exception{
		log.debug("===========================END===========================");
	}
}