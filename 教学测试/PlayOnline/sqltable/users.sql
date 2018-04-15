/*
Navicat MySQL Data Transfer

Source Server         : LearningStudy
Source Server Version : 50534
Source Host           : 39.108.147.44:3306
Source Database       : safezyw

Target Server Type    : MYSQL
Target Server Version : 50534
File Encoding         : 65001

Date: 2017-10-01 21:04:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS users;
CREATE TABLE users (
  userId varchar(45) NOT NULL,
  account varchar(40) DEFAULT NULL,
  password varchar(40) DEFAULT NULL,
  name varchar(40) DEFAULT NULL,
  fileIdArr text,
  PRIMARY KEY (userId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
