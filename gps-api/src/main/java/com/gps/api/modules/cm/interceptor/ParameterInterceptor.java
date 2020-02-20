package com.gps.api.modules.cm.interceptor;

import com.gps.api.modules.cm.MD5Util;
import com.gps.api.modules.cm.Sha1;
import com.gps.db.exception.RRException;
import com.gps.api.modules.cm.entity.Channel;
import com.gps.api.modules.cm.service.ChannelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

/**
 * 拦截器 请求参数签名校验
 * Created by jiyang on 14:47 2017/12/14
 */
@Component
@Slf4j
public class ParameterInterceptor implements HandlerInterceptor {

    public static final String VERIFY_FAIL_MSG = "The request parameter signature verification failed!";

    @Autowired
    private ChannelService channelService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
//        if (true) {
//            return true;
//        }
        // 渠道编号
        String channelCode = request.getParameter("channelCode");
        // 签名
        String signStr = request.getParameter("sign");

        if (StringUtils.isBlank(channelCode) || StringUtils.isBlank(signStr)) {
            log.warn(VERIFY_FAIL_MSG);
            throw new RRException("channelCode signStr不能为空");
        }

        Channel channel = channelService.selectByChannelCode(channelCode);
        if (channel == null) {
            log.warn(VERIFY_FAIL_MSG);
            throw new RRException("channelCode 不对");
        }

        boolean right = verifySign(channel.getEncryptKey(), request);
        if (right) {
            return true;
        }

        log.warn(VERIFY_FAIL_MSG);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        throw new RRException("效验码不对");
//        return false;
    }

    /**
     * md5签名
     * <p>
     * 按参数名称升序，将参数值进行连接 签名
     *
     * @param appSecret
     * @param params
     * @return
     */
    private String sign(String appSecret, TreeMap<String, String> params) {
        StringBuilder paramValues = new StringBuilder();
        params.put("appSecret", appSecret);

        for (Map.Entry<String, String> entry : params.entrySet()) {
            paramValues.append(entry.getKey());
            paramValues.append(entry.getValue());
        }
        if (StringUtils.equals(params.get("st"), "md5")) {
            String md = MD5Util.md5(paramValues.toString());
            System.out.println(md);
            return md;
        }
        String signature = Sha1.SHA1(paramValues.toString());
        return signature;
    }


    /**
     * 请求参数签名验证
     *
     * @param appSecret
     * @param request
     * @return true 验证通过 false 验证失败
     * @throws Exception
     */
    private boolean verifySign(String appSecret, HttpServletRequest request) throws Exception {
        TreeMap<String, String> params = new TreeMap<>();

        String signStr = request.getParameter("sign");
        if (StringUtils.isBlank(signStr)) {
            throw new RuntimeException("There is no signature field in the request parameter!");
        }

        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paramName = enu.nextElement().trim();
            if (!paramName.equals("sign")) {
                params.put(paramName, URLDecoder.decode(request.getParameter(paramName), "UTF-8"));
            }
        }

        if (!sign(appSecret, params).equals(signStr)) {
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {
        return;
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        return;
    }

}