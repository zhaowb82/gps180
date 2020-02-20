package com.gps.api.modules.sys.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gps.db.dbutils.Constant;
import com.gps.db.dbutils.MyPage;
import com.gps.db.entity.GroupsEntity;
import com.gps.db.service.GroupsService;
import com.gps.db.utils.R;
import com.gps.api.modules.sys.entity.SysDeptEntity;
import com.gps.api.modules.sys.service.SysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 部门管理
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-20 15:23:47
 */
@RestController
@RequestMapping("/sys/dept")
@Api(tags = "A公司部门管理")
public class SysDeptController extends AbstractController {
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private GroupsService groupsService;

    /**
     * 列表
     */
    @GetMapping("list")
    @RequiresPermissions("sys:dept:list")
    @ApiOperation(value = "部门列表分页")
    public R list(@RequestParam Map<String, Object> params) {
        MyPage<SysDeptEntity> page = sysDeptService.queryPage(params);

        return R.ok(page);
    }

    @GetMapping("deps")
    @RequiresPermissions("sys:dept:list")
    @ApiOperation(value = "部门列表2")
    public R deps() {
        List<SysDeptEntity> deptList = sysDeptService.listByUser();
//        //添加一级部门
//        if (getUserId() != null && getUserId() == Constant.SUPER_ADMIN) {
//            SysDeptEntity root = new SysDeptEntity();
//            root.setDeptId(0L);
//            root.setName("一级部门");
//            root.setParentId(-1L);
//            root.setOpen(true);
//            deptList.add(root);
//        }
        return R.ok(deptList);
    }

    /**
     * 选择部门(添加、修改菜单)
     */
    @GetMapping("/select")
    @RequiresPermissions("sys:dept:select")
    @ApiOperation(value = "选择部门(添加、修改菜单)")
    public R select() {
        List<SysDeptEntity> deptList = sysDeptService.listByUser();

        //添加一级部门
        if (getUserId() == Constant.SUPER_ADMIN) {
            SysDeptEntity root = new SysDeptEntity();
            root.setDeptId(0L);
            root.setName("一级部门");
            root.setParentId(-1L);
//            root.setOpen(true);
            deptList.add(root);
        }

        return R.ok(deptList);
    }

    /**
     * 上级部门Id(管理员则为0)
     */
    @GetMapping("/info")
    @RequiresPermissions("sys:dept:list")
    @ApiOperation(value = "级部门Id(管理员则为0)")
    public R info() {
        long deptId = 0;
        if (getUserId() != Constant.SUPER_ADMIN) {
            SysDeptEntity dept = sysDeptService.getById(getDeptId());
            deptId = dept.getParentId();
        }

        return R.ok(deptId);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{deptId}")
    @RequiresPermissions("sys:dept:info")
    @ApiOperation(value = "信息")
    public R info(@PathVariable("deptId") Long deptId) {
        SysDeptEntity dept = sysDeptService.getById(deptId);

        return R.ok(dept);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("sys:dept:save")
    @ApiOperation(value = "保存")
    public R<?> save(@RequestBody SysDeptEntity dept) {
        dept.setCreateTime(new Date());
        dept.setEnable(true);
        sysDeptService.save(dept);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    @RequiresPermissions("sys:dept:update")
    @ApiOperation(value = "修改")
    public R update(@RequestBody SysDeptEntity dept) {
        sysDeptService.updateById(dept);

        return R.ok();
    }

    @PutMapping("enable")
    @RequiresPermissions("sys:dept:update")
    @ApiOperation(value = "使能")
    public R<?> enable(@RequestParam String deptId, @RequestParam Boolean enable) {
        sysDeptService.update(
                Wrappers.<SysDeptEntity>lambdaUpdate()
                        .set(SysDeptEntity::getEnable, enable)
                        .eq(SysDeptEntity::getDeptId, deptId)
        );
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @RequiresPermissions("sys:dept:delete")
    @ApiOperation(value = "删除")
    public R delete(long deptId) {
        //判断是否有子部门
        List<Long> deptList = sysDeptService.queryDetpIdList(deptId);
        if (deptList.size() > 0) {
            return R.error("请先删除子部门");
        }

        sysDeptService.delete(deptId);

        return R.ok();
    }

    ////////////////////////////////////////////////////////////////////////////

//    @GetMapping(value = "deptAndGroup")
//    @ApiOperation(value = "")
//    public R deptAndGroup(String detpId) {
//        List<SysDeptEntity> list = sysDeptService.listByUser();
//        for (SysDeptEntity e : list) {
//            e.setList(groupsService.list(Wrappers.<GroupsEntity>query()
//                    .lambda()
//                    .eq(GroupsEntity::getDeptId, e.getDeptId())));
//        }
//        return R.ok(list);
//    }
}
