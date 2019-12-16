/*
 Navicat Premium Data Transfer

 Source Server         : Mysql_localhost
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : mybatis_plus

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 16/12/2019 10:46:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `name` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '姓名',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `email` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for user_like
-- ----------------------------
DROP TABLE IF EXISTS `user_like`;
CREATE TABLE `user_like` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `liked_user_id` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '被点赞的用户id',
  `liked_post_id` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '点赞的用户id',
  `status` tinyint(1) DEFAULT '1' COMMENT '点赞状态，0取消，1点赞',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `liked_user_id` (`liked_user_id`),
  KEY `liked_post_id` (`liked_post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户点赞表';

SET FOREIGN_KEY_CHECKS = 1;
