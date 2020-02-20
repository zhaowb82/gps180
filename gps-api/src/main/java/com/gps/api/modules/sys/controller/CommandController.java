package com.gps.api.modules.sys.controller;

import com.gps.api.common.annotation.SysLog;
import com.gps.common.model.CommandType;
import com.gps.db.entity.ProductEntity;
import com.gps.db.entityvo.PageVo;
import com.gps.db.entityvo.SearchVo;
import com.gps.db.service.AlarmService;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gps.common.Constants;
import com.gps.api.cmd.CommandSender;
import com.gps.db.entity.CommandLogsEntity;
import com.gps.db.entity.CommandTypeMEntity;
import com.gps.db.entity.DeviceEntity;
import com.gps.db.service.CommandLogsService;
import com.gps.db.service.CommandTypeMService;
import com.gps.db.service.DeviceService;
import com.gps.db.GpsRedisUtils;
import com.gps.db.service.ProductService;
import com.gps.db.utils.UIDGenerator;
import com.gps.db.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.BeanUtils;
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

import com.gps.db.entity.CommandTypeEntity;
import com.gps.db.service.CommandTypeService;
import com.gps.common.cache.CacheKeys;
import com.gps.common.model.Command;


/**
 * @author zb
 * @email zb@gmail.com
 * @date 2019-06-30 11:13:45
 */
@RestController
@RequestMapping("command")
@Api(tags = "GPS指令管理")
public class CommandController extends AbstractController {
    @Autowired
    private CommandTypeService commandTypeService;
    @Autowired
    private CommandTypeMService commandTypeMService;
    @Autowired
    private CommandLogsService commandLogsService;
    @Autowired
    private ProductService productService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private GpsRedisUtils gpsRedisUtils;
    @Autowired
    private CommandSender commandSender;
    @Autowired
    private AlarmService alarmService;

    @GetMapping(value = "commandLogs") // 命令管理
    @ApiOperation(value = "设备指令记录分页")
//    @RequiresPermissions("command:list") //这里不能加
    public R<?> commandLogs(@RequestParam(required = false) Long pageNum,
                            @RequestParam(required = false) Long pageSize,
                            @RequestParam(required = false) String imei,
                            @RequestParam(required = false) Date startDate,
                            @RequestParam(required = false) Date endDate,
                            @RequestParam(required = false, defaultValue = "execute_time") String sort,
                            @RequestParam(required = false, defaultValue = "DESC") String order) {
        return R.ok(commandLogsService.queryPage(pageNum, pageSize, sort, order, imei, startDate, endDate));
    }

    @GetMapping(value = "records")
    @ApiOperation(value = "查询已经发送的指令")
    public R records(String imei) {
        return R.ok(commandLogsService.list(
                Wrappers.<CommandLogsEntity>query().lambda()
                        .eq(CommandLogsEntity::getImei, imei)
                        .orderByDesc(CommandLogsEntity::getExecuteTime)
                ));
    }

//    @GetMapping(value = "commandLogs1") // 命令管理
//    @ApiOperation(value = "设备指令记录分页")
//    @RequiresPermissions("command:list")
//    public R<?> commandLogs1(@RequestParam(required = false) PageVo pageVo,
//                             @RequestParam(required = false) String imei,
//                             @RequestParam(required = false) SearchVo searchVo) {
//        return R.ok(1);
//    }

    @GetMapping(value = "allCmd") // 命令管理
    @ApiOperation(value = "设备指令类型")
    @RequiresPermissions("command:list")
    public R<List<CommandTypeEntity>> allCmd() {
        return R.ok(commandTypeService.list());
    }

    @GetMapping(value = "cmdAction") // 命令管理
    @ApiOperation(value = "查询所有指令列表")
    @RequiresPermissions("command:list")
    public R<?> cmdAction() {
        return R.ok(commandTypeMService.list(Wrappers.<CommandTypeMEntity>query()
                .lambda()
                //.select(CommandTypeMEntity::getCmdmId, CommandTypeMEntity::getCmdName)
                .orderByAsc(CommandTypeMEntity::getCmdmId)));
    }

    @GetMapping(value = "cmdForDevice")
    @ApiOperation(value = "设备指令列表")
//    @RequiresPermissions("command:list")
    public R<List<CommandTypeEntity>> cmdForDevice(String imei) {
        return R.ok(commandTypeService.getByImei(imei));
    }
//
//
//    /**
//     * 信息
//     */
//    @RequestMapping("/info/{predictcmdid}")
//    @RequiresPermissions("common:commandtype:info")
//    public R info(@PathVariable("predictcmdid") Integer predictcmdid){
//		CommandTypeEntity commandType = commandTypeService.getById(predictcmdid);
//
//        return R.ok().put("commandType", commandType);
//    }
//

    /**
     * 保存
     */
    @SysLog("新建某产品的命令")
    @PostMapping("save")
    @RequiresPermissions("command:save")
    @ApiOperation(value = "新建某产品的命令")
    public R save(@RequestBody CommandTypeEntity commandType) {
        commandType.setPredictCmdId(null);
        commandType.setCmdLevel(0);
        commandTypeService.save(commandType);
        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改产品命令")
    @PutMapping("update")
    @RequiresPermissions("command:update")
    @ApiOperation(value = "修改产品命令")
    public R update(@RequestBody CommandTypeEntity commandType) {
        commandTypeService.updateById(commandType);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除产品命令")
    @DeleteMapping("delete/{id}")
    @RequiresPermissions("command:delete")
    @ApiOperation(value = "删除产品命令")
    public R<?> delete(@PathVariable String id) {
//        commandTypeService.removeByIds(Arrays.asList(predictcmdids));
        commandTypeService.removeById(id);
        return R.ok();
    }


    @GetMapping(value = "deviceCmd")
    @ApiOperation(value = "查询用户已拥有的设备指令")
    public R<?> deviceCmd() {
        return R.ok(commandTypeService.list());
    }

//    @GetMapping(value = "deviceCmdSettings")
//    @ApiOperation(value = "")
//    public R deviceCmdSettings(String imei) {
//        List<String> a = new ArrayList<>();
//        a.add("4444");
//        a.add("555");
//        return R.ok().put("aaa", a);
//    }

    //////////////////////////////////////////////////////////////////send command

    @SysLog("一键导入某协议命令")
    @GetMapping(value = "importCmd")
    @RequiresPermissions("command:save")
    @ApiOperation(value = "一键导入某协议命令")
    public R<?> importCmd(@RequestParam String prodId) {
        ProductEntity prod = productService.getById(prodId);
        if (prod == null) {
            return R.error("没有此产品");
        }
        String prot = prod.getProtocol();
        Command command = new Command();
        command.setType(CommandType.TYPE_IMPORT_CMD);
        command.set("prot", prot);
        command.setSync(true);
        commandSender.sendCommand(command);
        List<CommandType> cmd = commandSender.waitCmds();
        if (cmd == null) {
            return R.error("没有找到指令");
        }
        for (CommandType ct : cmd) {
            CommandTypeEntity c = commandTypeService.getOne(Wrappers.<CommandTypeEntity>query()
                    .lambda()
                    .eq(CommandTypeEntity::getCmdCode, ct.name())
                    .eq(CommandTypeEntity::getDeviceType, prod.getId())
            );
            if (c != null) {
                continue;
            }
            CommandTypeMEntity cm = commandTypeMService.getOne(Wrappers.<CommandTypeMEntity>query()
                    .lambda()
                    .eq(CommandTypeMEntity::getCmdCode, ct.name())
            );
            if (cm != null) {
                CommandTypeEntity commandType = new CommandTypeEntity();
                BeanUtils.copyProperties(cm, commandType);
                commandType.setPredictCmdId(null);
                commandType.setCmdLevel(100);
                commandType.setDeviceType(prod.getId());
                commandTypeService.save(commandType);
            }
        }
        return R.ok();
    }

    @SysLog("取消没有发出的命令")
    @DeleteMapping(value = "delCacheCmd")
    @RequiresPermissions("command:send")
    @ApiOperation(value = "取消没有发出的命令")
    public R delCacheCmd(String cmdid) {
        CommandLogsEntity logsEntity = commandLogsService.getById(cmdid);
        if (logsEntity == null) {
            return R.error("没有此指令");
        }
        String key = CacheKeys.getCommandKeys(logsEntity.getCommandType(), logsEntity.getImei());
        gpsRedisUtils.delete(key);
        commandLogsService.removeById(cmdid);
        return R.ok();
    }

    @SysLog("下发命令")
    @PostMapping(value = "sendCmd")
    @RequiresPermissions("command:send")
    @ApiOperation(value = "下发命令")
    public R<?> sendCmd(@RequestBody Command command) {
        String imei = command.getImei();
        DeviceEntity device = deviceService.getById(imei);
        if (device == null) {
            return R.error("没有此设备");
        }

        //默认命令处理
        if (CommandType.TYPE_SERVER_DIS_ALARM.name().equals(command.getType())) {
            final boolean result = alarmService.dismissAlarm(imei, command.getString("alarmId"), getUser().getUsername(), command.getString("remark"));
            if (result) {
                //gengxin
                return R.ok();
            } else {
                return R.error("报警记录不存在");
            }
        }
        //是否有这个权限 TODO
        CommandTypeEntity cmdType = commandTypeService.getOne(Wrappers.<CommandTypeEntity>query()
                .lambda()
                .eq(CommandTypeEntity::getCmdCode, command.getType())
                .eq(CommandTypeEntity::getDeviceType, device.getProductId()));
        if (cmdType == null) {
            return R.error("此设备没有配置此命令");
        }
        command.set("name", cmdType.getCmdName());
        if (StringUtils.isNotEmpty(cmdType.getCmdPwd())
                && !StringUtils.equals(cmdType.getCmdPwd(), command.getPassword())) {
            return R.error("命令密码不正确");
        }
        command.setSync(cmdType.getSync());
        try {
            Map<String, Object> par = parseCmd(StringUtils.equals(cmdType.getCmdType(), "list"),
                    cmdType.getParams(), command.getParams());
            if (par != null) {
                command.getAttributes().putAll(par);
                command.setParams(null);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            return R.error(e.getMessage());
        }

        String message = JSON.toJSONString(command);

        String cmdId = "cmd" + UIDGenerator.eventId();
        command.setId(cmdId);
        command.set(Constants.USER_ID, getUserId());
        command.set(Constants.PLATFORM, "web");
        command.set(Constants.USER_PHONE, getUser().getNickname());
        commandSender.sendCommand(command);


        CommandLogsEntity logsEntity = new CommandLogsEntity();
        logsEntity.setId(cmdId);
        logsEntity.setImei(command.getImei());
        logsEntity.setCommandType(command.getType());
        logsEntity.setExecuteTime(new Date());
        logsEntity.setExecutor(getUser().getNickname());
        logsEntity.setExecutorPhone(getUser().getMobile());
        logsEntity.setPlatform("web");
//        logsEntity.setReason("set");
        logsEntity.setCommandBody(message);
//        logsEntity.setAttribute(attribute);
        commandLogsService.save(logsEntity);
        return R.ok();
    }

    private Map<String, Object> parseCmd(boolean isList, String params, List<String> paramsValue) throws Exception {
        //1.创建解析器对象
        SAXReader saxReader = new SAXReader();
        int i = 0;
        int valsSize = 0;
        if (paramsValue != null) {
            valsSize = paramsValue.size();
        }
        Map<String, Object> ret = new HashMap<>();
        String paramsKey = "<params>" + params + "</params>";
        //2.使用解析器加载web.xml文件得到document对象
        Document document = saxReader.read(new ByteArrayInputStream(paramsKey.getBytes(StandardCharsets.UTF_8)));
        //3.获取根元素节点
        Element rootElement = document.getRootElement();
        for (Iterator iterator = rootElement.elementIterator("param"); iterator.hasNext(); ) {
            Element paramElement = (Element) iterator.next();
//                String text = paramElement.getText();
            Attribute a1 = paramElement.attribute("type");
            if (a1 == null) {
                throw new InvalidParameterException("XML 参数没有type");
            }
            String key = a1.getValue();
            Attribute e2 = paramElement.attribute("value");//test val
            if (isList) {
                if (valsSize != 1) {
                    throw new InvalidParameterException("list类型需要传参数，数量为1");
                }
                String valSel = paramsValue.get(0);
                if (StringUtils.isEmpty(valSel)) {
                    throw new InvalidParameterException("list类型参数为空");
                }
                String value = e2.getValue();
                if (StringUtils.equals(value, valSel)) {
                    ret.put(key, value);
                    break;
                }
            } else {
                if (valsSize < i + 1) {
                    throw new InvalidParameterException("text类型参数不够");
                }
                String val = paramsValue.get(i);
                ret.put(key, val);
            }
            i++;
        }
        return ret;
    }
}
