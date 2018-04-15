/*
Navicat MySQL Data Transfer

Source Server         : LearningStudy
Source Server Version : 50534
Source Host           : 39.108.147.44:3306
Source Database       : safezyw

Target Server Type    : MYSQL
Target Server Version : 50534
File Encoding         : 65001

Date: 2017-10-01 21:00:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS file;
CREATE TABLE file (
  fileId varchar(45) NOT NULL,
  fileGroupId varchar(45) DEFAULT NULL,
  title varchar(40) DEFAULT NULL,
  fileSrc text,
  uploadTime datetime DEFAULT NULL,
  PRIMARY KEY (fileId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
