/*
Navicat MySQL Data Transfer

Source Server         : LearningStudy
Source Server Version : 50534
Source Host           : 39.108.147.44:3306
Source Database       : safezyw

Target Server Type    : MYSQL
Target Server Version : 50534
File Encoding         : 65001

Date: 2017-10-01 21:03:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for recommend
-- ----------------------------
DROP TABLE IF EXISTS recommend;
CREATE TABLE recommend (
  fileGroupId varchar(45) NOT NULL,
  userId varchar(45) DEFAULT NULL,
  PRIMARY KEY (fileGroupId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
