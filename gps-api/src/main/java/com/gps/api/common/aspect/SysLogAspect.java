
package com.gps.api.common.aspect;

import com.alibaba.fastjson.JSON;
import com.gps.api.common.annotation.SysLog;
import com.gps.api.modules.sys.entity.SysLogEntity;
import com.gps.api.modules.sys.entity.SysUserEntity;
import com.gps.api.modules.sys.form.SysLoginForm;
import com.gps.api.modules.sys.service.SysLogService;
import com.gps.api.common.utils.HttpContextUtils;
import com.gps.api.common.utils.IPUtils;
import com.gps.api.common.utils.ShiroUtils;
import com.gps.db.entity.DeviceEntity;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * 系统日志，切面处理类
 */
@Aspect
@Component
public class SysLogAspect {
    @Autowired
    private SysLogService sysLogService;

    @Pointcut("@annotation(com.gps.api.common.annotation.SysLog)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        //保存日志
        saveSysLog(point, time);

        return result;
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLogEntity sysLog = new SysLogEntity();
        SysLog syslog = method.getAnnotation(SysLog.class);
        if (syslog != null) {
            //注解上的描述
            sysLog.setOperation(syslog.value());
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");

        //请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            String params = JSON.toJSONString(args);
            sysLog.setParams(params);
        } catch (Exception e) {

        }

        //获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        //设置IP地址
        sysLog.setIp(IPUtils.getIpAddr(request));

        sysLog.setTime(time);
        sysLog.setCreateDate(new Date());
        //用户名
        SysUserEntity u = ShiroUtils.getUserEntity();
        if (u != null) {
            String username = u.getUsername();
            sysLog.setUsername(username);
            sysLogService.save(sysLog);
            return;
        }
		DeviceEntity d = ShiroUtils.getDeviceEntity();
		if (d != null) {
			sysLog.setUsername(d.getImei());
			sysLogService.save(sysLog);
			return;
		}
        if (StringUtils.equals(methodName, "login")) {
			Object o = args[0];
			if (o instanceof SysLoginForm) {
				SysLoginForm lf = (SysLoginForm) o;
				sysLog.setUsername(lf.getUsername());
				SysLoginForm lfclone = new SysLoginForm();
				BeanUtils.copyProperties(lf, lfclone);
				lfclone.setPassword("secret");
				String params = JSON.toJSONString(lfclone);
				sysLog.setParams(params);
				sysLogService.save(sysLog);
			}
		}

    }
}
