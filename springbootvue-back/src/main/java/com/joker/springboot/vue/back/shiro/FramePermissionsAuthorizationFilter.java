package com.joker.springboot.vue.back.shiro;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component("frameperms")
public class FramePermissionsAuthorizationFilter extends PermissionsAuthorizationFilter {
	public Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private CacheManager shiroCacheManager;

	@Override
	public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws IOException {
		Subject user = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) user.getPrincipal();

		HttpServletRequest req = (HttpServletRequest) request;
		Subject subject = getSubject(request, response);
		String uri = req.getRequestURI();
		// String requestURL = req.getRequestURL().toString();
		String contextPath = req.getContextPath();
		if (uri.endsWith("/pre")) {
			uri = uri.substring(0, uri.length() - 4);
		}
		int i = uri.indexOf(contextPath);
		if (i > -1) {
			uri = uri.substring(i + contextPath.length());
		}
		if (StringUtils.isBlank(uri)) {
			uri = "/";
		}
		boolean permitted = false;
		if ("/".equals(uri)) {
			permitted = true;
		} else {
			permitted = subject.isPermitted(uri);
		}
		return permitted;
	}
}
