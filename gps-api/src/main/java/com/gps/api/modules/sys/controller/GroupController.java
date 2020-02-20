package com.gps.api.modules.sys.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gps.api.common.annotation.SysLog;
import com.gps.db.entity.DeviceEntity;
import com.gps.db.entity.GroupsEntity;
import com.gps.db.entity.UserGroupEntity;
import com.gps.db.service.DeviceService;
import com.gps.db.service.GroupsService;
import com.gps.db.utils.UIDGenerator;
import com.gps.db.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gps.db.service.UserGroupService;


/**
 * @author zb
 * @email zb@gmail.com
 * @date 2019-06-30 11:13:45
 */
@RestController
@RequestMapping("group")
@Api(tags = "GPS分组管理")
public class GroupController extends AbstractController {
    @Autowired
    private GroupsService groupsService;
    @Autowired
    private UserGroupService userGroupService;
    @Autowired
    private DeviceService deviceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("group:list")
    @ApiOperation(value = "列表")
    public R list(@RequestParam Map<String, Object> params) {
//        PageUtils page = userGoupService.queryPage(params);
        List<GroupsEntity> list = groupsService.listByUser();
        if (getUser() != null) {
            List<String> ids = userGroupService.queryGroupIdList(getUserId());
            if (ids != null && ids.size() > 0) {
                List<GroupsEntity> ids2 = groupsService.list(Wrappers.<GroupsEntity>query().lambda().in(GroupsEntity::getId, ids));
                for (GroupsEntity e : ids2) {
                    if (list.contains(e)) {
                        continue;
                    }
                    list.add(e);
                }
            }
        }
        return R.ok(list);
    }

    /**
     * 保存
     */
    @SysLog("添加分组")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @RequiresPermissions("group:save")
    @ApiOperation(value = "添加分组")
    public R save(@RequestBody GroupsEntity goupsEntity) {
        goupsEntity.setId(UIDGenerator.eventId());
        if (goupsEntity.getDeptId() == null) {
            goupsEntity.setDeptId(getDeptId());
        }
        goupsEntity.setCrtUserId(getUserId());
        goupsEntity.setCrtTime(new Date());
        groupsService.save(goupsEntity);
        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改分组")
    @PutMapping("/update")
    @RequiresPermissions("group:update")
    @ApiOperation(value = "修改")
    public R<?> update(@RequestBody GroupsEntity goupsEntity) {
        groupsService.updateById(goupsEntity);
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除分组")
    @DeleteMapping("delete/{groupId}")
    @RequiresPermissions("group:delete")
    public R<?> delete(@PathVariable String groupId) {
        deviceService.update(Wrappers.<DeviceEntity>lambdaUpdate()
                .eq(DeviceEntity::getGroupId, groupId)
                .set(DeviceEntity::getGroupId, null)
        );
        userGroupService.remove(Wrappers.<UserGroupEntity>lambdaQuery().eq(UserGroupEntity::getGroupId, groupId));
        groupsService.removeById(groupId);
        return R.ok();
    }

    @GetMapping("userGroups")
//    @RequiresPermissions("group:delete")
    @ApiOperation(value = "用户所管理的组")
    public R<List<String>> userGroups(String userId) {
        List<String> list = userGroupService.queryGroupIdList(Long.parseLong(userId));
        return R.ok(list);
    }

}
