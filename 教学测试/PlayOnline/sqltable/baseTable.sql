

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS admin;
CREATE TABLE admin (
  adminId varchar(45) NOT NULL,
  account varchar(40) DEFAULT NULL,
  password varchar(40) DEFAULT NULL,
  name varchar(40) DEFAULT NULL,
  level int(2) DEFAULT NULL,
  PRIMARY KEY (adminId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO admin VALUES ('8aa801be5e3349c5015e3349e64d0000', 'admin', 'E10ADC3949BA59ABBE56E057F20F883E', '管理员', null);


-- ----------------------------
-- Table structure for block
-- ----------------------------
DROP TABLE IF EXISTS block;
CREATE TABLE block (
  blockId varchar(45) NOT NULL,
  subject varchar(40) DEFAULT NULL,
  PRIMARY KEY (blockId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



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


-- ----------------------------
-- Table structure for recommend
-- ----------------------------
DROP TABLE IF EXISTS recommend;
CREATE TABLE recommend (
  fileGroupId varchar(45) NOT NULL,
  userId varchar(45) DEFAULT NULL,
  PRIMARY KEY (fileGroupId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


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

