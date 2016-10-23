CREATE TABLE `t_contact` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `birthday` bigint(20) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `remark` varchar(128) DEFAULT NULL,
  `head_img` varchar(128) DEFAULT NULL,
  `figure_id` int(10) DEFAULT NULL COMMENT '形象ID',
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  `contact_type_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `t_contact_figure` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `head_url` varchar(256) NOT NULL COMMENT '形象URL',
  `sort_index` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `t_contact_figure_prop` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `figure_id` int(11) NOT NULL COMMENT '形象ID',
  `prop_unit` varchar(45) NOT NULL COMMENT '属性的单位',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

CREATE TABLE `t_contact_figure_value` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `figure_prop_id` int(11) NOT NULL,
  `contact_id` int(11) NOT NULL,
  `prop_value` varchar(45) NOT NULL COMMENT '属性值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

CREATE TABLE `t_contact_pro` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `contact_id` int(11) NOT NULL COMMENT '联系人ID',
  `name` varchar(45) NOT NULL COMMENT '自定义字段名',
  `value` varchar(45) NOT NULL COMMENT '自定义字段值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `t_contact_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `source_type` tinyint(3) NOT NULL DEFAULT '0' COMMENT '来源 0-系统 1-用户自定义',
  `user_id` varchar(45) NOT NULL DEFAULT '0' COMMENT 'source_type为1时指定所属用户',
  `sort_index` int(11) NOT NULL,
  `create_time` bigint(20) NOT NULL,
  `update_time` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

CREATE TABLE `t_device_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `device_id` varchar(64) NOT NULL COMMENT '设备号ID',
  `create_time` bigint(20) NOT NULL COMMENT '绑定时间',
  `login_user_id` int(10) NOT NULL,
  `login_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `DID` (`device_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `t_event` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `event_time` bigint(20) NOT NULL,
  `create_time` bigint(20) NOT NULL,
  `update_time` bigint(20) NOT NULL,
  `remark` varchar(256) DEFAULT NULL,
  `remind_interval` int(11) NOT NULL DEFAULT '0' COMMENT '提醒间隔',
  `remind_interval_unit` tinyint(3) NOT NULL DEFAULT '0' COMMENT '提醒间隔类型 0-日 1-周 2-月',
  `contact_id` int(11) NOT NULL DEFAULT '0' COMMENT '关联的联系人',
  `event_type` int(11) NOT NULL DEFAULT '0' COMMENT '事件的类型',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '所属的用户',
  PRIMARY KEY (`id`),
  KEY `IDX_USER_ETIME` (`user_id`,`event_time`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

CREATE TABLE `t_event_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `source_type` tinyint(3) NOT NULL DEFAULT '0' COMMENT '类别 0-系统 1-自定义',
  `user_id` int(10) NOT NULL DEFAULT '0' COMMENT 'source_type为1时标志所属用户',
  `sort_index` int(11) NOT NULL DEFAULT '0' COMMENT '排序规则',
  `create_time` bigint(20) NOT NULL,
  `update_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

CREATE TABLE `t_note` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(128) NOT NULL COMMENT '小计标题',
  `content` varchar(512) NOT NULL,
  `create_time` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态 0-正常 1-已删除',
  PRIMARY KEY (`id`),
  KEY `USER_DEL` (`user_id`,`del_flag`,`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `t_sms_code` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `mobile` char(11) NOT NULL DEFAULT '',
  `type` tinyint(3) NOT NULL DEFAULT '0',
  `code` varchar(20) NOT NULL DEFAULT '',
  `create_time` bigint(20) NOT NULL DEFAULT '0',
  `valid_interval` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

CREATE TABLE `t_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `source` tinyint(3) NOT NULL DEFAULT '0' COMMENT '注册渠道',
  `phone` char(11) NOT NULL DEFAULT '' COMMENT '绑定的手机',
  `bind_open_id` varchar(128) NOT NULL DEFAULT '',
  `create_time` bigint(20) NOT NULL DEFAULT '0',
  `access_token` varchar(128) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `t_user_certificate` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `password` varchar(45) NOT NULL DEFAULT '',
  `salt` int(11) NOT NULL DEFAULT '0',
  `update_time` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
SELECT * FROM d_xunuo.t_event;