
package com.gps.api.modules.job.task;

import com.gps.api.modules.sys.service.SysUserTokenService;
import com.gps.db.service.DeviceStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 测试定时任务(演示Demo，可删除)
 *
 * testTask为spring bean的名称
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component("checkTask")
@Slf4j
public class CheckTask implements ITask {
	@Autowired
	private DeviceStatusService deviceStatusService;
	@Autowired
	private SysUserTokenService sysUserTokenService;

	@Override
	public void run(String params){
		log.debug("TestTask定时任务正在执行，参数为：{}", params);
		deviceStatusService.checkOfflineDevice();
		sysUserTokenService.checkExpiredToken();
	}
}
