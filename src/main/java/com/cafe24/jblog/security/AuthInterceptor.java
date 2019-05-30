package com.cafe24.jblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cafe24.jblog.vo.UserVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(
			HttpServletRequest request,
			HttpServletResponse response,
			Object handler)
			throws Exception {
		
		//1. handler 종류 확인
		if( handler instanceof HandlerMethod == false ) {
			//defaultServletHandler(DefaultServletHttpRequestHandler.class) 인 경우
			return true;//이미지, ... 와 같은 것들 assets을 거치지 않는 것들이 여기서 처리가 됨
		}
		
		//casting
		// HandlerMapping 의 정보를 가지고 있음(@, 메소드, 파라미터,...)
		// 1을 처리 안했을 경우 DefaultServletHttpRequestHandler 캐스팅 오류가 발생할 수 있다.
		HandlerMethod handlerMethod = (HandlerMethod) handler;
				
		//3. Method의 @Auth 받아오기
		Auth auth = handlerMethod.getMethodAnnotation( Auth.class );
		
		//4. Method에 @Auth 없으면
		//	Class(Type)에 @Auth를 받아오기
		boolean isAuth = handlerMethod.getMethod().getDeclaringClass().isAnnotationPresent(Auth.class);
		
		//5. @Auth가 안 붙어있는 경우
		if( auth == null && isAuth == false) {
			return true;
		}
		
		//6. @Auth가 (class또는 method에) 붙어 있기 때문에
		//	인중 여부 체크
		HttpSession session = request.getSession();

		if(session == null) { //인증이 안되어 있음
			response.sendRedirect(request.getContextPath() + "/user/login"); 
			return false;
		}
		
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) { //인증이 안 되어 있음
			response.sendRedirect(request.getContextPath() + "/user/login"); 
			return false;
		}
		
		return true;
	}

}
