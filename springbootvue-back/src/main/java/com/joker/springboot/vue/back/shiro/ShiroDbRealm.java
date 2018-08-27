package com.joker.springboot.vue.back.shiro;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.time.DateUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.joker.springboot.vue.back.VO.UserVO;

@Component("shiroDbRealm")
public class ShiroDbRealm extends AuthorizingRealm {
	@Autowired
	private RestTemplate restTemplate;

	public static final Logger logger = LoggerFactory.getLogger(ShiroDbRealm.class);

	public static final String HASH_ALGORITHM = "MD5";
	public static final int HASH_INTERATIONS = 1;
	private static final String OR_OPERATOR = " or ";
	private static final String AND_OPERATOR = " and ";
	private static final String NOT_OPERATOR = "not ";

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		if (!SecurityUtils.getSubject().isAuthenticated()) {
			doClearCache(principalCollection);
			SecurityUtils.getSubject().logout();
			return null;
		}
		ShiroUser shiroUser = (ShiroUser) principalCollection.getPrimaryPrincipal();
		SimpleAuthorizationInfo sazi = new SimpleAuthorizationInfo();
		try {
			// 获取用户权限信息
			sazi.addRoles(new ArrayList<String>());
			sazi.addStringPermissions(new ArrayList<String>());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new AuthorizationException(e);
		}
		return sazi;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		try {
			// 获取用户信息
			JSONObject jsonObject = new JSONObject();

			if (jsonObject.getBoolean("SUCCESS")) {
				UserVO result = jsonObject.getObject("result", UserVO.class);

				// 账户已被删除
				// if
				// (Constants.VALUE_YES.equalsIgnoreCase(result.getFlagDeleted()))
				// {
				// throw new UnknownAccountException();
				// }
				// 账户被禁用
				if ("0".equalsIgnoreCase(result.getActive())) {
					throw new DisabledAccountException();
				}
				// 账户被锁定
				if ("1".equalsIgnoreCase(result.getBlocked())) {
					// 账户被禁用
					// if
					// (UserBlockTypeEnum.TAKE_LOCK.getDesc().equalsIgnoreCase(result.getBlockType()))
					// {
					// throw new DisabledAccountException();
					// } else if
					// (UserBlockTypeEnum.PWD_MAXNUM.getDesc().equalsIgnoreCase(result.getBlockType()))
					// {
					// 当前时间大于锁定时间24小时即不锁定
					// Date blockDate = DateUtils.addHour(result.getBlockDate(),
					// 24);
					// Date nowDate = new Date();
					// int pp = nowDate.compareTo(blockDate);
					// if (pp < 0) {
					throw new LockedAccountException();
					// }

					// }
				}

				// AuthenticationInfo authinfo = new SimpleAuthenticationInfo(
				// new ShiroUser(result.getLoginId(), result.getUserName(),
				// result.getUserType(),
				// result.getUserOid(), result.getActive(), result.getBlocked(),
				// getName()));
				AuthenticationInfo authinfo = new SimpleAuthenticationInfo(result, result.getLoginPwd(), getName());

				return authinfo;
			} else {
				throw new UnknownAccountException();
			}
		} catch (UnknownAccountException e) {
			logger.warn(e.getMessage(), e);
			throw new UnknownAccountException(e);
		} catch (LockedAccountException e) {
			logger.warn(e.getMessage(), e);
			throw new LockedAccountException(e);
		} catch (DisabledAccountException e) {
			logger.warn(e.getMessage(), e);
			throw new DisabledAccountException(e);
		} catch (Exception e) {
			logger.error("", e);
			throw new AuthenticationException(e);
		}
	}

	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(HASH_ALGORITHM);
		matcher.setHashIterations(HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @author wanghan
	 * @see org.apache.shiro.realm.AuthorizingRealm#isPermitted(org.apache.shiro.subject.PrincipalCollection,
	 *      String)
	 */
	@Override
	public boolean isPermitted(PrincipalCollection principals, String permission) {
		if (permission.contains(OR_OPERATOR)) {
			String[] permissions = permission.split(OR_OPERATOR);
			for (String orPermission : permissions) {
				if (isPermittedWithNotOperator(principals, orPermission)) {
					return true;
				}
			}
			return false;
		} else if (permission.contains(AND_OPERATOR)) {
			String[] permissions = permission.split(AND_OPERATOR);
			for (String orPermission : permissions) {
				if (!isPermittedWithNotOperator(principals, orPermission)) {
					return false;
				}
			}
			return true;
		} else {
			return isPermittedWithNotOperator(principals, permission);
		}
	}

	private boolean isPermittedWithNotOperator(PrincipalCollection principals, String permission) {
		if (permission.startsWith(NOT_OPERATOR)) {
			return !super.isPermitted(principals, permission.substring(NOT_OPERATOR.length()));
		} else {
			return super.isPermitted(principals, permission);
		}
	}
}
