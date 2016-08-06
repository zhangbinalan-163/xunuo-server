CREATE SCHEMA `d_xunuo` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `t_device_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `device_id` varchar(45) NOT NULL COMMENT '设备号ID',
  `create_time` bigint(20) NOT NULL COMMENT '绑定时间',
  PRIMARY KEY (`id`),
  KEY `DID` (`device_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `t_sms_code` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `mobile` char(11) NOT NULL DEFAULT '',
  `type` tinyint(3) NOT NULL DEFAULT '0',
  `code` varchar(20) NOT NULL DEFAULT '',
  `create_time` bigint(20) NOT NULL DEFAULT '0',
  `valid_interval` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

CREATE TABLE `t_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `source` tinyint(3) NOT NULL DEFAULT '0' COMMENT '注册渠道',
  `phone` char(11) NOT NULL DEFAULT '' COMMENT '绑定的手机',
  `bind_open_id` varchar(100) NOT NULL DEFAULT '',
  `create_time` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `t_user_certificate` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `password` varchar(45) NOT NULL DEFAULT '',
  `salt` int(11) NOT NULL DEFAULT '0',
  `update_time` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
