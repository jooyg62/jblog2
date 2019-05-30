package com.cafe24.jblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.cafe24.jblog.vo.UserVo;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public Object resolveArgument(
		MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory) throws Exception {
		
		if( supportsParameter(parameter) == false ) {
			// 리턴 결과가 컨트롤러의 authUser에 세팅이 된다.
			return WebArgumentResolver.UNRESOLVED;
		}
		
		HttpServletRequest request = 
				webRequest.getNativeRequest(HttpServletRequest.class);
		
		HttpSession session = request.getSession();
		if(session == null) {
			return null;
		}
		
		return session.getAttribute("authUser");
	}
	
	/**
	 * 파라미터 정보를 보고 내가 관심있는 파라미터인지 확인해야한다.
	 */
	@Override
	public boolean supportsParameter( MethodParameter parameter ) {
		AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
		
		// @AuthUser가 안붙어 있음
		if( authUser == null) {
			return false;
		}
		
		// 파라미터 타입이 UserVo
		if(parameter.getParameterType().equals( UserVo.class ) == false) {
			return false;
		}
		
		return true;
	}

}
