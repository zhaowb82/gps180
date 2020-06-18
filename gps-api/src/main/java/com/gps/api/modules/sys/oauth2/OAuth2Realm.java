/**
 * Copyright (c) 2018-2020 GPS180 All rights reserved.
 *
 * https://www.gps180.com
 *
 * 版权所有，侵权必究！
 */

package com.gps.api.modules.sys.oauth2;

import com.gps.api.modules.sys.service.ShiroService;
import com.gps.api.modules.sys.service.SysUserTokenService;
import com.gps.db.entity.DeviceEntity;
import com.gps.db.service.DeviceService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import com.gps.api.common.utils.JwtUtils;
import com.gps.api.modules.sys.entity.SysUserEntity;
import com.gps.api.modules.sys.entity.SysUserTokenEntity;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 认证
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {
    @Autowired
    private ShiroService shiroService;
    @Autowired
    private SysUserTokenService sysUserTokenService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private DeviceService deviceService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        GpsPrincipal gpsPrincipal = (GpsPrincipal)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        SysUserEntity user = gpsPrincipal.getUserEntity();
        if (user != null) {
            Long userId = user.getUserId();
            //用户权限列表
            Set<String> permsSet = shiroService.getUserPermissions(userId);
            info.setStringPermissions(permsSet);
        } else {
            Set<String> permsSet = shiroService.getUserPermissions(2L);
            info.setStringPermissions(permsSet);
        }
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();

        Jws<Claims> cs = jwtUtils.getJwsByToken(accessToken);
        if (cs == null || cs.getBody().getExpiration().getTime() < System.currentTimeMillis()){
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }
        String subj = cs.getBody().getSubject();
        String loginType = String.valueOf(cs.getHeader().get("logTyp"));
        String fromType = String.valueOf(cs.getHeader().get("fromTyp"));
        long tokenId = Long.parseLong(String.valueOf(cs.getHeader().get("tokenId")));
        boolean fromPhone = fromType.equals("phone");
        boolean loginDevice = loginType.equals("Device");
        GpsPrincipal.GpsPrincipalBuilder principal = new GpsPrincipal.GpsPrincipalBuilder();
        principal.isPhoneLogin(fromPhone);
        if (loginDevice) {
            DeviceEntity device = deviceService.getById(subj);
            if (!device.getCanLogin()){
                throw new LockedAccountException("账号已被锁定,请联系管理员");
            }
            principal.deviceEntity(device);
        } else {
//        //根据accessToken，查询用户信息
//        SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
//        //token失效
//        if(tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()){
//            throw new IncorrectCredentialsException("token失效，请重新登录");
//        }
            SysUserTokenEntity tokenEntity = sysUserTokenService.getById2(tokenId);
            if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                throw new IncorrectCredentialsException("token失效，请重新登录");
            }

            //查询用户信息
//            SysUserEntity user = shiroService.queryUser(tokenEntity.getUserId());
            SysUserEntity user = shiroService.queryUser(Long.parseLong(subj));
            //账号锁定
            if (user.getStatus() == 0) {
                throw new LockedAccountException("账号已被锁定,请联系管理员");
            }
            principal.userEntity(user);
            principal.tokenId(tokenId);
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal.build(), accessToken, getName());
        return info;
    }
}
