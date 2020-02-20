package com.gps.api.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gps.api.common.annotation.SysLog;
import com.gps.db.entity.CommandTypeEntity;
import com.gps.db.entity.DeviceEntity;
import com.gps.db.entity.ProductEntity;
import com.gps.db.service.CommandTypeService;
import com.gps.db.service.DeviceService;
import com.gps.db.service.GroupsService;
import com.gps.db.service.ProductService;
import com.gps.db.utils.R;
import com.gps.db.utils.SnowFlakeFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("product")
@Api(tags = "GPS设备厂商管理")
public class ProductController extends AbstractController {
    @Autowired
    private ProductService productService;
    @Autowired
    private GroupsService groupsService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private CommandTypeService commandTypeService;

    @GetMapping(value = "ownerByUser")
    @RequiresPermissions("product:list")
    @ApiOperation(value = "查询用户已拥有的设备类型") //先全部
    public R<List<ProductEntity>> ownerByUser() {
        List<ProductEntity> ps = productService.list();
        return R.ok(ps);
    }

    @GetMapping(value = "protocalTypes")
    @Deprecated
    @ApiOperation(value = "查询设备类型code")
    public R<?> protocalTypes() {
        List<String> ps = new ArrayList<>();
        ps.add("jt808");
        ps.add("bsj");
        ps.add("tianqin");
        ps.add("goome");
        ps.add("gb32960");
        return R.ok(ps);
    }

    @GetMapping(value = "allProts")
    @ApiOperation(value = "添加设备类型时用，显示所有支持的协议")
    public R<?> allProts() {
        String[] p = new String[] {"jt808", "bsj", "tianqin", "goome", "kks", "jb32960"};
        List<String> prots = Arrays.asList(p);
        return R.ok(prots);
    }

    @GetMapping(value = "allFuncs")
    @ApiOperation(value = "添加设备类型时用，显示所有支持的功能 ")
    public R<Map> allFuncs() {
        Map<String, String> funcs = new HashMap<>();
        funcs.put("audrec", "录音");
        funcs.put("vidrec", "摄像");
        funcs.put("other", "其它");
        return R.ok(funcs);
    }

    @SysLog("添加产品")
    @PostMapping(value = "save")
    @RequiresPermissions("product:save")
    @ApiOperation(value = "添加产品")
    public R<?> addprotocalType(@RequestBody ProductEntity productEntity) {
        productEntity.setId("product-" + SnowFlakeFactory.getSnowFlake().nextIdStr());
        productService.save(productEntity);
        return R.ok();
    }

    @SysLog("修改产品")
    @PutMapping(value = "update")
    @RequiresPermissions("product:update")
    @ApiOperation(value = "修改产品")
    public R updateProduct(@RequestBody ProductEntity productEntity) {
        productService.updateById(productEntity);
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除产品")
    @DeleteMapping("delete/{id}")
    @RequiresPermissions("product:delete")
    @ApiOperation(value = "删除产品")
    @Transactional
    public R delete(@PathVariable String id) {
        //修改条件s
//        UpdateWrapper<DeviceEntity> userUpdateWrapper = new UpdateWrapper<>();
//        userUpdateWrapper.eq("name", "lqf");

        LambdaUpdateWrapper<DeviceEntity> wr = Wrappers.lambdaUpdate();
        wr.eq(DeviceEntity::getProductId, id);
        wr.set(DeviceEntity::getProductId, "");

//        LambdaUpdateWrapper<DeviceEntity> wr = Wrappers.lambdaUpdate(deviceEntity).eq(DeviceEntity::getProductId, id);
        deviceService.update(wr);
        commandTypeService.remove(Wrappers.<CommandTypeEntity>lambdaQuery().eq(CommandTypeEntity::getDeviceType, id));
        productService.removeById(id);
        return R.ok();
    }

}
