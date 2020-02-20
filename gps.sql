
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for alarm
-- ----------------------------
DROP TABLE IF EXISTS `alarm`;
CREATE TABLE `alarm`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `imei` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'imei',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报警类型',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报警内容',
  `level` tinyint(2) NULL DEFAULT NULL,
  `occur_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '发生时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '最新一次上报时间',
  `longitude` double NULL DEFAULT NULL COMMENT '第一次上报位置',
  `latitude` double NULL DEFAULT NULL,
  `occur_num` int(11) NULL DEFAULT NULL COMMENT '报警次数',
  `company_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `process_status` tinyint(2) NOT NULL DEFAULT 0 COMMENT '是否处理，1已处理',
  `process_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `process_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `imei`(`imei`) USING BTREE,
  INDEX `type`(`type`) USING BTREE,
  INDEX `times`(`occur_time`) USING BTREE,
  INDEX `timee`(`end_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for alarm_type
-- ----------------------------
DROP TABLE IF EXISTS `alarm_type`;
CREATE TABLE `alarm_type`  (
  `alarmcode` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `alarmname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `alarmdescr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`alarmcode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for command
-- ----------------------------
DROP TABLE IF EXISTS `command`;
CREATE TABLE `command`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '命令名称',
  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '命令编码',
  `description` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `expire` int(11) NULL DEFAULT NULL COMMENT '离线有效时间，单位秒',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for command_logs
-- ----------------------------
DROP TABLE IF EXISTS `command_logs`;
CREATE TABLE `command_logs`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备id',
  `imei` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备imei',
  `command_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '命令名称',
  `command_body` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `executor` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行人',
  `executor_phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `execute_time` datetime(0) NULL DEFAULT NULL COMMENT '下发时间',
  `feedback_result` tinyint(1) NULL DEFAULT NULL COMMENT '执行结果',
  `feedback_time` datetime(0) NULL DEFAULT NULL COMMENT '反馈时间',
  `platform` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设防撤防需添加原因',
  `attribute` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '包含发起机型信息，IP地址等等',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for command_type
-- ----------------------------
DROP TABLE IF EXISTS `command_type`;
CREATE TABLE `command_type`  (
  `predict_cmd_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `device_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cmd_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cmd_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cmd_name_en` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cmd_name_tw` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cmd_descr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cmd_descr_en` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cmd_descr_tw` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `params` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `params_en` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `params_tw` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cmd_pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cmd_level` int(11) NULL DEFAULT NULL,
  `sync` bit(1) NULL DEFAULT NULL,
  `cmd_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`predict_cmd_id`) USING BTREE,
  INDEX `devType`(`device_type`) USING BTREE,
  INDEX `cmdC`(`cmd_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1073 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for command_type_m
-- ----------------------------
DROP TABLE IF EXISTS `command_type_m`;
CREATE TABLE `command_type_m`  (
  `cmdm_id` int(255) NOT NULL,
  `cmd_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cmd_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cmd_name_en` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cmd_name_tw` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cmd_descr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cmd_descr_en` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cmd_descr_tw` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `params` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `params_en` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `params_tw` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cmd_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sync` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`cmdm_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device`  (
  `imei` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '设备imei号',
  `sim` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'sim卡号',
  `iccid` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'iccid号',
  `sim_start_date` date NULL DEFAULT NULL COMMENT 'sim卡开始时间',
  `sim_end_date` date NULL DEFAULT NULL COMMENT 'sim卡到期时间',
  `protocol` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '协议类型',
  `product_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '产品id',
  `plate_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '车牌号',
  `device_type` int(1) NOT NULL DEFAULT 0 COMMENT '设备类型(0: 有线， 1：无线)',
  `car_vin` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '车辆vin号',
  `driver_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '司机名称',
  `company_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属公司',
  `group_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组',
  `can_login` bit(1) NULL DEFAULT b'0' COMMENT '是否可单独登录',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录用密码',
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` int(1) NULL DEFAULT 0 COMMENT '设备状态：1：正常绑定，0：空闲2：待审核',
  `total_up` int(11) NULL DEFAULT NULL COMMENT '无线设备总上传次数  >0 为无线设备',
  `is_test` bit(1) NULL DEFAULT b'0' COMMENT '测试设备',
  `crt_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `crt_time` datetime(0) NULL DEFAULT NULL COMMENT '设备创建时间',
  `update_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`imei`) USING BTREE,
  INDEX `plate_no`(`plate_no`) USING BTREE,
  INDEX `group_id`(`group_id`) USING BTREE,
  INDEX `company_id`(`company_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_geofence
-- ----------------------------
DROP TABLE IF EXISTS `device_geofence`;
CREATE TABLE `device_geofence`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `imei` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备imei',
  `geofence_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '围栏id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_rec
-- ----------------------------
DROP TABLE IF EXISTS `device_rec`;
CREATE TABLE `device_rec`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `imei` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `rec_time` datetime NULL DEFAULT NULL COMMENT '录音时间',
  `file_size` int(11) NULL DEFAULT NULL COMMENT '文件大小',
  `rec_data` blob NULL COMMENT '录音内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_status
-- ----------------------------
DROP TABLE IF EXISTS `device_status`;
CREATE TABLE `device_status`  (
  `imei` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备id',
  `longitude` double NULL DEFAULT NULL COMMENT '经度',
  `latitude` double NULL DEFAULT NULL COMMENT '纬度',
  `speed` float NULL DEFAULT 0 COMMENT '速度',
  `direction` float NULL DEFAULT 0 COMMENT '方向',
  `total_distance` double(20, 2) NULL DEFAULT 0.00 COMMENT '总里程',
  `gotsrc` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '定位类型 gps lbs',
  `connection_status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '连接状态',
  `position_update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后位置更新时间',
  `signal_update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后信号更新时间',
  `geofence_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '在围栏内的围栏ID',
  `geofence_status` tinyint(2) NULL DEFAULT 0 COMMENT '围栏状态（围栏外：-1 /围栏内：1）',
  `block_status` tinyint(2) NULL DEFAULT 0 COMMENT '设防状态（1：未设防， 2： 设防）',
  `alarm_status` tinyint(2) NULL DEFAULT 0 COMMENT '报警状态（0: 无告警，1：拆除报警, 2：）',
  `alarms` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所有存在报警',
  `report_frequency` int(8) NULL DEFAULT NULL COMMENT '上报频率，单位分钟',
  `attribute` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '扩展属性',
  `org_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '原始消息报文',
  PRIMARY KEY (`imei`) USING BTREE,
  INDEX `st`(`connection_status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for events
-- ----------------------------
DROP TABLE IF EXISTS `events`;
CREATE TABLE `events`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `imei` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备imei',
  `type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '事件类型',
  `level` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '事件等级',
  `occur_time` datetime(0) NULL DEFAULT NULL COMMENT '发生时间',
  `longitude` double NULL DEFAULT NULL COMMENT '经度',
  `latitude` double NULL DEFAULT NULL COMMENT '纬度',
  `geofence_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '围栏id',
  `company_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司id',
  `attributes` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for geofence
-- ----------------------------
DROP TABLE IF EXISTS `geofence`;
CREATE TABLE `geofence`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `icon` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '围栏图标',
  `area` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区域',
  `description` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `company_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属公司',
  `attributes` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '附加参数',
  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for jt809_dev_plat
-- ----------------------------
DROP TABLE IF EXISTS `jt809_dev_plat`;
CREATE TABLE `jt809_dev_plat`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `imei` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'imei',
  `gnss` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `gnss`(`gnss`) USING BTREE,
  INDEX `imei`(`imei`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for jt809_user
-- ----------------------------
DROP TABLE IF EXISTS `jt809_user`;
CREATE TABLE `jt809_user`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `gnss_center_id` bigint(20) NULL DEFAULT NULL COMMENT '平台ID',
  `user_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户类型：DOWN_USER(下级平台), UP_USER(上级平台)',
  `platform_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上级平台IP地址',
  `platform_port` int(255) NULL DEFAULT NULL COMMENT '上级平台端口',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `enrypt` tinyint(1) NULL DEFAULT NULL COMMENT '是否加密',
  `encrypt_key` int(11) NULL DEFAULT NULL COMMENT '秘钥',
  `miyao_m` bigint(20) NULL DEFAULT NULL COMMENT 'M1',
  `miyao_a` bigint(20) NULL DEFAULT NULL COMMENT 'A1',
  `miyao_c` bigint(20) NULL DEFAULT NULL COMMENT 'C1',
  `conn_sub_link` tinyint(1) NULL DEFAULT NULL COMMENT '子链路连接',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for positions
-- ----------------------------
DROP TABLE IF EXISTS `positions`;
CREATE TABLE `positions`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `imei` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `device_time` datetime(0) NOT NULL,
  `latitude` double(10, 6) NULL DEFAULT NULL,
  `longitude` double(10, 6) NULL DEFAULT NULL,
  `speed` float NULL DEFAULT NULL,
  `direction` float NULL DEFAULT NULL,
  `total_distance` double(20, 2) NULL DEFAULT NULL COMMENT '总里程',
  `gotsrc` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '定位类型 gps lbs',
  `attribute` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `org_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `time`(`device_time`) USING BTREE,
  INDEX `imei`(`imei`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `manufacturer` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '厂商',
  `device_model` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品型号',
  `protocol` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '协议 bsj jt808 tianqin',
  `device_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品类型(0: 有线， 1：无线)',
  `command_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '支持命令',
  `functions` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支持功能',
  `device_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `reg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备号正则式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `BLOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `SCHED_NAME`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CRON_EXPRESSION` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES ('GpsApiScheduler', 'TASK_1', 'DEFAULT', '0 0/30 * * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ENTRY_ID` varchar(95) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TRIG_INST_NAME`(`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE,
  INDEX `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY`(`SCHED_NAME`, `INSTANCE_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
  INDEX `IDX_QRTZ_FT_J_G`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_JG`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_T_G`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TG`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `IS_DURABLE` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_J_REQ_RECOVERY`(`SCHED_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
  INDEX `IDX_QRTZ_J_GRP`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `LOCK_NAME` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `STR_PROP_1` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STR_PROP_2` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STR_PROP_3` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `INT_PROP_1` int(11) NULL DEFAULT NULL,
  `INT_PROP_2` int(11) NULL DEFAULT NULL,
  `LONG_PROP_1` bigint(20) NULL DEFAULT NULL,
  `LONG_PROP_2` bigint(20) NULL DEFAULT NULL,
  `DEC_PROP_1` decimal(13, 4) NULL DEFAULT NULL,
  `DEC_PROP_2` decimal(13, 4) NULL DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) NULL DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) NULL DEFAULT NULL,
  `PRIORITY` int(11) NULL DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) NULL DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) NULL DEFAULT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_J`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_JG`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_C`(`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE,
  INDEX `IDX_QRTZ_T_G`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_STATE`(`SCHED_NAME`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_N_STATE`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_N_G_STATE`(`SCHED_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_NEXT_FIRE_TIME`(`SCHED_NAME`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST`(`SCHED_NAME`, `TRIGGER_STATE`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_MISFIRE`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for risk_point
-- ----------------------------
DROP TABLE IF EXISTS `risk_point`;
CREATE TABLE `risk_point`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `latitude` double(10, 6) NULL DEFAULT NULL COMMENT '纬度',
  `longitude` double(10, 6) NULL DEFAULT NULL COMMENT '经度',
  `address_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '详细地址',
  `store_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '附近风险点名称',
  `city_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '所属城市',
  `city_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '风险点' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job`  (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `bean_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '定时任务' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schedule_job
-- ----------------------------
INSERT INTO `schedule_job` VALUES (1, 'checkTask', 'gps180', '0 0/30 * * * ?', 0, '删除不用的token,检测离线设备', '2019-06-20 16:27:15');

-- ----------------------------
-- Table structure for schedule_job_log
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job_log`;
CREATE TABLE `schedule_job_log`  (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `job_id` bigint(20) NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数',
  `status` tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `error` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '失败信息',
  `times` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`) USING BTREE,
  INDEX `job_id`(`job_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10747 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '定时任务日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_captcha
-- ----------------------------
DROP TABLE IF EXISTS `sys_captcha`;
CREATE TABLE `sys_captcha`  (
  `uuid` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
  `code` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '验证码',
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统验证码' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'key',
  `param_value` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `param_key`(`param_key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统配置信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '上级部门ID，一级部门为0',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '排序',
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型：集团，公司，部门，小组',
  `max_group_num` int(11) NULL DEFAULT NULL COMMENT '最大分组数',
  `max_device_num` int(11) NULL DEFAULT NULL COMMENT '最大设备数',
  `max_user_num` int(11) NULL DEFAULT NULL COMMENT '最大管理用户数',
  `enable` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否可用  1：可用  0：不可用',
  `remark` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, 0, 'Gps180平台', 0, NULL, NULL, NULL, NULL, 1, NULL, '2020-02-11 16:19:04');
INSERT INTO `sys_dept` VALUES (2, 1, '锦智公司', 1, NULL, NULL, NULL, NULL, 1, NULL, '2020-02-11 16:19:16');
INSERT INTO `sys_dept` VALUES (3, 1, '车云网公司', 2, NULL, 6, 8, 7, 1, NULL, '2020-02-10 16:19:20');
INSERT INTO `sys_dept` VALUES (4, 3, '子公司1', 0, NULL, 3, 5, 4, 1, NULL, '2020-02-11 16:19:25');
INSERT INTO `sys_dept` VALUES (5, 3, '子公司2', 1, NULL, 8, 88, 9, 1, NULL, '2020-02-12 16:19:28');
INSERT INTO `sys_dept` VALUES (6, 1, '测试公司1', 1, NULL, NULL, NULL, NULL, 1, NULL, '2020-02-11 16:19:31');
INSERT INTO `sys_dept` VALUES (7, 2, 'tyrrtrt', NULL, NULL, 55, 66, 5, 0, '6565', '2020-02-11 19:06:20');
INSERT INTO `sys_dept` VALUES (8, 2, '7777', NULL, NULL, 777, 777, 777, 0, NULL, '2020-02-11 19:07:21');
INSERT INTO `sys_dept` VALUES (9, 2, '5646', NULL, NULL, 444, 65, 46, 1, NULL, '2020-02-04 19:07:52');
INSERT INTO `sys_dept` VALUES (10, 2, '999', NULL, NULL, 99, 99, 99, 1, NULL, '2020-02-05 19:07:57');
INSERT INTO `sys_dept` VALUES (11, 2, '66', NULL, NULL, 666, 66, 66, 1, NULL, '2020-02-04 19:08:01');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 267 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 82 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '系统管理', '/system', NULL, 0, 'system', 3);
INSERT INTO `sys_menu` VALUES (2, 39, '用户管理', '/manage/user', NULL, 1, 'admin', 2);
INSERT INTO `sys_menu` VALUES (3, 1, '角色管理', '/system/role', NULL, 1, 'role', 2);
INSERT INTO `sys_menu` VALUES (4, 1, '菜单管理', '/system/menu', NULL, 1, 'menu', 1);
INSERT INTO `sys_menu` VALUES (5, 1, 'SQL监控', '/system/sql', NULL, 1, 'sql', 4);
INSERT INTO `sys_menu` VALUES (6, 1, '定时任务', '/system/schedule', NULL, 1, 'job', 5);
INSERT INTO `sys_menu` VALUES (7, 6, '查看', NULL, 'sys:schedule:list,sys:schedule:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (8, 6, '新增', NULL, 'sys:schedule:save', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (9, 6, '修改', NULL, 'sys:schedule:update', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (10, 6, '删除', NULL, 'sys:schedule:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (11, 6, '暂停', NULL, 'sys:schedule:pause', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (12, 6, '恢复', NULL, 'sys:schedule:resume', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (13, 6, '立即执行', NULL, 'sys:schedule:run', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (14, 6, '日志列表', NULL, 'sys:schedule:log', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (15, 2, '查看', NULL, 'sys:user:list,sys:user:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (16, 2, '新增', NULL, 'sys:user:save,sys:role:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (17, 2, '修改', NULL, 'sys:user:update,sys:role:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (18, 2, '删除', NULL, 'sys:user:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (19, 3, '查看', NULL, 'sys:role:list,sys:role:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (20, 3, '新增', NULL, 'sys:role:save,sys:menu:list', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (21, 3, '修改', NULL, 'sys:role:update,sys:menu:list', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (22, 3, '删除', NULL, 'sys:role:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (23, 4, '查看', NULL, 'sys:menu:list,sys:menu:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (24, 4, '新增', NULL, 'sys:menu:save,sys:menu:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (25, 4, '修改', NULL, 'sys:menu:update,sys:menu:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (26, 4, '删除', NULL, 'sys:menu:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (27, 1, '参数管理', 'sys/config', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', 1, 'sql', 6);
INSERT INTO `sys_menu` VALUES (29, 1, '系统日志', '/system/log', 'sys:log:list,sys:log:delete', 1, 'log', 7);
INSERT INTO `sys_menu` VALUES (30, 1, '文件上传', 'oss/oss', 'sys:oss:all', 1, 'oss', 6);
INSERT INTO `sys_menu` VALUES (31, 39, '客户公司管理', '/manage/dept', NULL, 1, 'shoucangfill', 1);
INSERT INTO `sys_menu` VALUES (32, 31, '查看', NULL, 'sys:dept:list,sys:dept:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (33, 31, '新增', NULL, 'sys:dept:save,sys:dept:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (34, 31, '修改', NULL, 'sys:dept:update,sys:dept:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (35, 31, '删除', NULL, 'sys:dept:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (36, 1, 'GPS产品&指令', '/system/deviceCommand', NULL, 1, 'daohang', 10);
INSERT INTO `sys_menu` VALUES (37, 0, '定位监控', '/map', '', 0, 'dangdifill', 0);
INSERT INTO `sys_menu` VALUES (38, 0, '统计报表', '/report', '', 0, 'log', 1);
INSERT INTO `sys_menu` VALUES (39, 0, '运营管理', '/manage', '', 0, 'config', 2);
INSERT INTO `sys_menu` VALUES (40, 1, 'GPS车辆设置', '/system/deviceType', '', 1, 'sousuo', 11);
INSERT INTO `sys_menu` VALUES (42, 36, '命令列表', NULL, 'command:list', 2, NULL, 1);
INSERT INTO `sys_menu` VALUES (43, 36, '添加命令', NULL, 'command:save', 2, NULL, 2);
INSERT INTO `sys_menu` VALUES (44, 36, '修改命令', NULL, 'command:update', 2, NULL, 3);
INSERT INTO `sys_menu` VALUES (45, 36, '删除命令', NULL, 'command:delete', 2, NULL, 4);
INSERT INTO `sys_menu` VALUES (46, 36, '设备类型列表', NULL, 'product:list', 2, NULL, 5);
INSERT INTO `sys_menu` VALUES (47, 36, '设备类型A', NULL, 'product:save', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (48, 36, '设备类型U', NULL, 'product:update', 2, NULL, 7);
INSERT INTO `sys_menu` VALUES (49, 36, '设备类型D', NULL, 'product:delete', 2, NULL, 8);
INSERT INTO `sys_menu` VALUES (50, 66, '组L', NULL, 'group:list', 2, NULL, 1);
INSERT INTO `sys_menu` VALUES (51, 66, '组A', NULL, 'group:save', 2, NULL, 2);
INSERT INTO `sys_menu` VALUES (52, 66, '组U', NULL, 'group:update', 2, NULL, 3);
INSERT INTO `sys_menu` VALUES (53, 66, '组D', NULL, 'group:delete', 2, NULL, 4);
INSERT INTO `sys_menu` VALUES (54, 69, '命令下发&取消', NULL, 'command:send', 2, NULL, 5);
INSERT INTO `sys_menu` VALUES (55, 66, '设备L', '', 'device:list', 2, '', 0);
INSERT INTO `sys_menu` VALUES (56, 66, '设备A', '', 'device:save', 2, '', 0);
INSERT INTO `sys_menu` VALUES (57, 66, '设备U', '', 'device:update', 2, '', 0);
INSERT INTO `sys_menu` VALUES (58, 66, '设备D', '', 'device:delete', 2, '', 0);
INSERT INTO `sys_menu` VALUES (66, 39, '分组&设备', '/manage/devices', NULL, 1, 'tixing', 4);
INSERT INTO `sys_menu` VALUES (67, 38, '行驶报表', '/report/travel', '', 1, 'tixing', 1);
INSERT INTO `sys_menu` VALUES (68, 38, '报警报表', '/report/alarms', '', 1, 'tubiao', 2);
INSERT INTO `sys_menu` VALUES (69, 37, '地图显示', '45', '', 1, 'xiangqu', 1);
INSERT INTO `sys_menu` VALUES (70, 37, '围栏设置', '45', '', 1, 'geren', 2);
INSERT INTO `sys_menu` VALUES (74, 70, '围栏显示', '', 'geofence:list', 2, '', 1);
INSERT INTO `sys_menu` VALUES (79, 1, 'Api管理', '/system/api', '', 1, 'xiangqu', 9);
INSERT INTO `sys_menu` VALUES (80, 70, '围栏编辑新增删除', '', 'geofence:save,geofence:update,geofence:delete', 2, '', 5);
INSERT INTO `sys_menu` VALUES (81, 70, '围栏绑定解绑', '', 'geofence:bind,geofence:unbind', 2, '', 7);
INSERT INTO `sys_menu` VALUES (83, 82, '显示', '', 'alarm:list', 2, '', 1);
INSERT INTO `sys_menu` VALUES (84, 82, '删除', '', 'alarm:delete', 2, '', 3);

-- ----------------------------
-- Table structure for sys_oss
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件上传' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '系统管理员', 'admin', NULL, 1, '2019-07-03 11:05:51');


-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色与部门对应关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NULL DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 688 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色与菜单对应关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (486, 3, 74);
INSERT INTO `sys_role_menu` VALUES (487, 3, 66);
INSERT INTO `sys_role_menu` VALUES (488, 3, 55);
INSERT INTO `sys_role_menu` VALUES (489, 3, 56);
INSERT INTO `sys_role_menu` VALUES (490, 3, 57);
INSERT INTO `sys_role_menu` VALUES (491, 3, 58);
INSERT INTO `sys_role_menu` VALUES (492, 3, 50);
INSERT INTO `sys_role_menu` VALUES (493, 3, 51);
INSERT INTO `sys_role_menu` VALUES (494, 3, 52);
INSERT INTO `sys_role_menu` VALUES (495, 3, 53);
INSERT INTO `sys_role_menu` VALUES (666, 4, 69);
INSERT INTO `sys_role_menu` VALUES (667, 4, 54);
INSERT INTO `sys_role_menu` VALUES (668, 4, 58);
INSERT INTO `sys_role_menu` VALUES (669, 4, 57);
INSERT INTO `sys_role_menu` VALUES (670, 4, 56);
INSERT INTO `sys_role_menu` VALUES (671, 4, 55);
INSERT INTO `sys_role_menu` VALUES (721, 20, 83);
INSERT INTO `sys_role_menu` VALUES (722, 20, 74);
INSERT INTO `sys_role_menu` VALUES (723, 20, 38);
INSERT INTO `sys_role_menu` VALUES (724, 20, 67);
INSERT INTO `sys_role_menu` VALUES (725, 20, 68);
INSERT INTO `sys_role_menu` VALUES (726, 20, 32);
INSERT INTO `sys_role_menu` VALUES (727, 20, 15);
INSERT INTO `sys_role_menu` VALUES (728, 20, 55);
INSERT INTO `sys_role_menu` VALUES (729, 20, 50);
INSERT INTO `sys_role_menu` VALUES (730, 20, 72);
INSERT INTO `sys_role_menu` VALUES (731, 20, 23);
INSERT INTO `sys_role_menu` VALUES (732, 20, 19);
INSERT INTO `sys_role_menu` VALUES (733, 20, 5);
INSERT INTO `sys_role_menu` VALUES (734, 20, 7);
INSERT INTO `sys_role_menu` VALUES (735, 20, 42);
INSERT INTO `sys_role_menu` VALUES (736, 20, 46);
INSERT INTO `sys_role_menu` VALUES (737, 20, 76);
INSERT INTO `sys_role_menu` VALUES (738, 20, 37);
INSERT INTO `sys_role_menu` VALUES (739, 20, 82);
INSERT INTO `sys_role_menu` VALUES (740, 20, 70);
INSERT INTO `sys_role_menu` VALUES (741, 20, 39);
INSERT INTO `sys_role_menu` VALUES (742, 20, 31);
INSERT INTO `sys_role_menu` VALUES (743, 20, 2);
INSERT INTO `sys_role_menu` VALUES (744, 20, 66);
INSERT INTO `sys_role_menu` VALUES (745, 20, 71);
INSERT INTO `sys_role_menu` VALUES (746, 20, 1);
INSERT INTO `sys_role_menu` VALUES (747, 20, 4);
INSERT INTO `sys_role_menu` VALUES (748, 20, 3);
INSERT INTO `sys_role_menu` VALUES (749, 20, 6);
INSERT INTO `sys_role_menu` VALUES (750, 20, 36);
INSERT INTO `sys_role_menu` VALUES (751, 20, 75);
INSERT INTO `sys_role_menu` VALUES (752, 21, 83);
INSERT INTO `sys_role_menu` VALUES (753, 21, 74);
INSERT INTO `sys_role_menu` VALUES (754, 21, 38);
INSERT INTO `sys_role_menu` VALUES (755, 21, 67);
INSERT INTO `sys_role_menu` VALUES (756, 21, 68);
INSERT INTO `sys_role_menu` VALUES (757, 21, 32);
INSERT INTO `sys_role_menu` VALUES (758, 21, 15);
INSERT INTO `sys_role_menu` VALUES (759, 21, 55);
INSERT INTO `sys_role_menu` VALUES (760, 21, 50);
INSERT INTO `sys_role_menu` VALUES (761, 21, 72);
INSERT INTO `sys_role_menu` VALUES (762, 21, 78);
INSERT INTO `sys_role_menu` VALUES (763, 21, 37);
INSERT INTO `sys_role_menu` VALUES (764, 21, 82);
INSERT INTO `sys_role_menu` VALUES (765, 21, 70);
INSERT INTO `sys_role_menu` VALUES (766, 21, 39);
INSERT INTO `sys_role_menu` VALUES (767, 21, 31);
INSERT INTO `sys_role_menu` VALUES (768, 21, 2);
INSERT INTO `sys_role_menu` VALUES (769, 21, 66);
INSERT INTO `sys_role_menu` VALUES (770, 21, 71);
INSERT INTO `sys_role_menu` VALUES (771, 2, 37);
INSERT INTO `sys_role_menu` VALUES (772, 2, 69);
INSERT INTO `sys_role_menu` VALUES (773, 2, 54);
INSERT INTO `sys_role_menu` VALUES (774, 2, 82);
INSERT INTO `sys_role_menu` VALUES (775, 2, 83);
INSERT INTO `sys_role_menu` VALUES (776, 2, 84);
INSERT INTO `sys_role_menu` VALUES (777, 2, 70);
INSERT INTO `sys_role_menu` VALUES (778, 2, 74);
INSERT INTO `sys_role_menu` VALUES (779, 2, 80);
INSERT INTO `sys_role_menu` VALUES (780, 2, 81);
INSERT INTO `sys_role_menu` VALUES (781, 2, 38);
INSERT INTO `sys_role_menu` VALUES (782, 2, 67);
INSERT INTO `sys_role_menu` VALUES (783, 2, 68);
INSERT INTO `sys_role_menu` VALUES (784, 2, 39);
INSERT INTO `sys_role_menu` VALUES (785, 2, 31);
INSERT INTO `sys_role_menu` VALUES (786, 2, 35);
INSERT INTO `sys_role_menu` VALUES (787, 2, 34);
INSERT INTO `sys_role_menu` VALUES (788, 2, 33);
INSERT INTO `sys_role_menu` VALUES (789, 2, 32);
INSERT INTO `sys_role_menu` VALUES (790, 2, 2);
INSERT INTO `sys_role_menu` VALUES (791, 2, 18);
INSERT INTO `sys_role_menu` VALUES (792, 2, 17);
INSERT INTO `sys_role_menu` VALUES (793, 2, 16);
INSERT INTO `sys_role_menu` VALUES (794, 2, 15);
INSERT INTO `sys_role_menu` VALUES (795, 2, 66);
INSERT INTO `sys_role_menu` VALUES (796, 2, 58);
INSERT INTO `sys_role_menu` VALUES (797, 2, 55);
INSERT INTO `sys_role_menu` VALUES (798, 2, 56);
INSERT INTO `sys_role_menu` VALUES (799, 2, 57);
INSERT INTO `sys_role_menu` VALUES (800, 2, 50);
INSERT INTO `sys_role_menu` VALUES (801, 2, 51);
INSERT INTO `sys_role_menu` VALUES (802, 2, 52);
INSERT INTO `sys_role_menu` VALUES (803, 2, 53);
INSERT INTO `sys_role_menu` VALUES (804, 2, 71);
INSERT INTO `sys_role_menu` VALUES (805, 2, 72);
INSERT INTO `sys_role_menu` VALUES (806, 2, 73);
INSERT INTO `sys_role_menu` VALUES (807, 2, 85);
INSERT INTO `sys_role_menu` VALUES (808, 2, 78);
INSERT INTO `sys_role_menu` VALUES (809, 1, 37);
INSERT INTO `sys_role_menu` VALUES (810, 1, 69);
INSERT INTO `sys_role_menu` VALUES (811, 1, 54);
INSERT INTO `sys_role_menu` VALUES (812, 1, 82);
INSERT INTO `sys_role_menu` VALUES (813, 1, 83);
INSERT INTO `sys_role_menu` VALUES (814, 1, 84);
INSERT INTO `sys_role_menu` VALUES (815, 1, 70);
INSERT INTO `sys_role_menu` VALUES (816, 1, 74);
INSERT INTO `sys_role_menu` VALUES (817, 1, 80);
INSERT INTO `sys_role_menu` VALUES (818, 1, 81);
INSERT INTO `sys_role_menu` VALUES (819, 1, 38);
INSERT INTO `sys_role_menu` VALUES (820, 1, 67);
INSERT INTO `sys_role_menu` VALUES (821, 1, 68);
INSERT INTO `sys_role_menu` VALUES (822, 1, 39);
INSERT INTO `sys_role_menu` VALUES (823, 1, 31);
INSERT INTO `sys_role_menu` VALUES (824, 1, 35);
INSERT INTO `sys_role_menu` VALUES (825, 1, 34);
INSERT INTO `sys_role_menu` VALUES (826, 1, 33);
INSERT INTO `sys_role_menu` VALUES (827, 1, 32);
INSERT INTO `sys_role_menu` VALUES (828, 1, 2);
INSERT INTO `sys_role_menu` VALUES (829, 1, 18);
INSERT INTO `sys_role_menu` VALUES (830, 1, 17);
INSERT INTO `sys_role_menu` VALUES (831, 1, 16);
INSERT INTO `sys_role_menu` VALUES (832, 1, 15);
INSERT INTO `sys_role_menu` VALUES (833, 1, 66);
INSERT INTO `sys_role_menu` VALUES (834, 1, 58);
INSERT INTO `sys_role_menu` VALUES (835, 1, 55);
INSERT INTO `sys_role_menu` VALUES (836, 1, 56);
INSERT INTO `sys_role_menu` VALUES (837, 1, 57);
INSERT INTO `sys_role_menu` VALUES (838, 1, 50);
INSERT INTO `sys_role_menu` VALUES (839, 1, 51);
INSERT INTO `sys_role_menu` VALUES (840, 1, 52);
INSERT INTO `sys_role_menu` VALUES (841, 1, 53);
INSERT INTO `sys_role_menu` VALUES (842, 1, 71);
INSERT INTO `sys_role_menu` VALUES (843, 1, 72);
INSERT INTO `sys_role_menu` VALUES (844, 1, 73);
INSERT INTO `sys_role_menu` VALUES (845, 1, 85);
INSERT INTO `sys_role_menu` VALUES (846, 1, 78);
INSERT INTO `sys_role_menu` VALUES (847, 1, 1);
INSERT INTO `sys_role_menu` VALUES (848, 1, 4);
INSERT INTO `sys_role_menu` VALUES (849, 1, 26);
INSERT INTO `sys_role_menu` VALUES (850, 1, 25);
INSERT INTO `sys_role_menu` VALUES (851, 1, 24);
INSERT INTO `sys_role_menu` VALUES (852, 1, 23);
INSERT INTO `sys_role_menu` VALUES (853, 1, 3);
INSERT INTO `sys_role_menu` VALUES (854, 1, 22);
INSERT INTO `sys_role_menu` VALUES (855, 1, 21);
INSERT INTO `sys_role_menu` VALUES (856, 1, 20);
INSERT INTO `sys_role_menu` VALUES (857, 1, 19);
INSERT INTO `sys_role_menu` VALUES (858, 1, 5);
INSERT INTO `sys_role_menu` VALUES (859, 1, 6);
INSERT INTO `sys_role_menu` VALUES (860, 1, 14);
INSERT INTO `sys_role_menu` VALUES (861, 1, 13);
INSERT INTO `sys_role_menu` VALUES (862, 1, 12);
INSERT INTO `sys_role_menu` VALUES (863, 1, 11);
INSERT INTO `sys_role_menu` VALUES (864, 1, 10);
INSERT INTO `sys_role_menu` VALUES (865, 1, 9);
INSERT INTO `sys_role_menu` VALUES (866, 1, 8);
INSERT INTO `sys_role_menu` VALUES (867, 1, 7);
INSERT INTO `sys_role_menu` VALUES (868, 1, 27);
INSERT INTO `sys_role_menu` VALUES (869, 1, 30);
INSERT INTO `sys_role_menu` VALUES (870, 1, 29);
INSERT INTO `sys_role_menu` VALUES (871, 1, 79);
INSERT INTO `sys_role_menu` VALUES (872, 1, 36);
INSERT INTO `sys_role_menu` VALUES (873, 1, 42);
INSERT INTO `sys_role_menu` VALUES (874, 1, 43);
INSERT INTO `sys_role_menu` VALUES (875, 1, 44);
INSERT INTO `sys_role_menu` VALUES (876, 1, 45);
INSERT INTO `sys_role_menu` VALUES (877, 1, 46);
INSERT INTO `sys_role_menu` VALUES (878, 1, 47);
INSERT INTO `sys_role_menu` VALUES (879, 1, 48);
INSERT INTO `sys_role_menu` VALUES (880, 1, 49);
INSERT INTO `sys_role_menu` VALUES (881, 1, 40);
INSERT INTO `sys_role_menu` VALUES (882, 1, 75);
INSERT INTO `sys_role_menu` VALUES (883, 1, 76);
INSERT INTO `sys_role_menu` VALUES (884, 1, 77);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盐',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `data_scope_type` int(11) NULL DEFAULT NULL COMMENT '数据权限类型',
  `multilogin` bit(1) NULL DEFAULT b'0' COMMENT '允许同时登录',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', 'z4567890', 'root@gps180.com', '13980808787', 1, 1, NULL, b'1', 1, '2016-11-11 11:11:11');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户与角色对应关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (6, 1, 1);

-- ----------------------------
-- Table structure for sys_user_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `token` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'token',
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `token`(`token`) USING BTREE,
  INDEX `userid`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 261 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户Token' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_groups
-- ----------------------------
DROP TABLE IF EXISTS `t_groups`;
CREATE TABLE `t_groups`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `parent_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父组ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组名',
  `max_user_num` int(11) NULL DEFAULT NULL COMMENT '最大管理用户数',
  `max_device_num` int(11) NULL DEFAULT NULL COMMENT '最大设备数',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `crt_user_id` bigint(20) NULL DEFAULT NULL,
  `crt_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1, 'mark', '13612345678', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '2017-03-23 22:37:41');

-- ----------------------------
-- Table structure for user_device
-- ----------------------------
DROP TABLE IF EXISTS `user_device`;
CREATE TABLE `user_device`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `device_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_group
-- ----------------------------
DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `group_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `uid`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
