/*
Navicat MySQL Data Transfer

Source Server         : LearningStudy
Source Server Version : 50534
Source Host           : 39.108.147.44:3306
Source Database       : safezyw

Target Server Type    : MYSQL
Target Server Version : 50534
File Encoding         : 65001

Date: 2017-10-01 21:01:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for filegroup
-- ----------------------------
DROP TABLE IF EXISTS filegroup;
CREATE TABLE filegroup (
  fileGroupId varchar(45) NOT NULL,
  fileGroupStatus int(10) DEFAULT NULL,
  title varchar(40) ,
  discribe varchar(40) DEFAULT NULL,
  imgSrc text,
  blockId varchar(45) ,
  adminId varchar(45) DEFAULT NULL,
  flag int(10) DEFAULT NULL,
  recomNum int(10) DEFAULT NULL,
  updateDate datetime DEFAULT NULL,
  PRIMARY KEY (fileGroupId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
