/*
Navicat MySQL Data Transfer

Source Server         : LearningStudy
Source Server Version : 50534
Source Host           : 39.108.147.44:3306
Source Database       : safezyw

Target Server Type    : MYSQL
Target Server Version : 50534
File Encoding         : 65001

Date: 2017-10-01 20:59:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for block
-- ----------------------------
DROP TABLE IF EXISTS block;
CREATE TABLE block (
  blockId varchar(45) NOT NULL,
  subject varchar(40) DEFAULT NULL,
  PRIMARY KEY (blockId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
