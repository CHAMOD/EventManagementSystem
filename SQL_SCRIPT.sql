

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `idUser` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `isActive` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

INSERT INTO `user` VALUES (1,'chamod','1234','admin','chamod',1),(5,'dfdf','1234','user','kasun',1),(6,'fdsf','d','user','dsafd',1),(8,'fff','fff','user','fff',1),(9,'dff','dfdf','user','fdfds',1),(10,'new','new','user','new',1),(11,'c','c','admin','c',1),(12,'hasi','123','user','hasitha',1),(13,'chaminda','123','user','chaminda',1),(14,'gayan','123456','user','gayan',1),(15,'dilanka','123','user','dilanka',1),(16,'richard','123','user','richard',1),(17,'sahan','sahan','user','sahan',1);

DROP TABLE IF EXISTS `team`;

CREATE TABLE `team` (
  `idTeam` int(11) NOT NULL AUTO_INCREMENT,
  `teamName` varchar(45) DEFAULT NULL,
  `lead` int(11) NOT NULL,
  `isActive` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`idTeam`),
  KEY `fk_User_Team1` (`lead`),
  CONSTRAINT `fk_User_Team1` FOREIGN KEY (`lead`) REFERENCES `user` (`idUser`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

INSERT INTO `team` VALUES (2,'Fin',8,1),(7,'ICT',9,1),(14,'tttt',12,1);

DROP TABLE IF EXISTS `member`;

CREATE TABLE `member` (
  `idMember` int(11) NOT NULL AUTO_INCREMENT,
  `teamId` int(11) NOT NULL,
  `isActive` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`idMember`,`teamId`),
  KEY `teamId_idx` (`teamId`),
  CONSTRAINT `teamId` FOREIGN KEY (`teamId`) REFERENCES `team` (`idTeam`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `userId` FOREIGN KEY (`idMember`) REFERENCES `user` (`idUser`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

INSERT INTO `member` VALUES (5,2,1),(6,7,1),(10,14,1),(13,2,1),(14,2,1),(15,2,1),(16,7,1),(17,2,0);

DROP TABLE IF EXISTS `task`;

CREATE TABLE `task` (
  `idTask` int(11) NOT NULL AUTO_INCREMENT,
  `taskName` varchar(45) DEFAULT NULL,
  `duration` int(10) DEFAULT NULL,
  `dateCreate` date DEFAULT NULL,
  `createUserId` int(11) DEFAULT NULL,
  `isActive` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`idTask`),
  KEY `createdUserId_idx` (`createUserId`),
  CONSTRAINT `createdUserId` FOREIGN KEY (`createUserId`) REFERENCES `user` (`idUser`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

INSERT INTO `task` VALUES (3,'T1',2,'2018-07-31',11,1),(4,'T22',22,'2018-07-31',11,1),(5,'t3',20,'2018-07-31',11,1),(6,'taskx',30,'2018-08-01',11,1),(7,'ee',12,'2018-08-01',11,0);

DROP TABLE IF EXISTS `task_user`;

CREATE TABLE `task_user` (
  `idTask` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  `status` varchar(25) DEFAULT 'pending',
  `isActive` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`idTask`,`idUser`),
  KEY `fk1_idx` (`idUser`),
  CONSTRAINT `idTask` FOREIGN KEY (`idTask`) REFERENCES `task` (`idTask`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `idUser` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `task_user` VALUES (3,16,'pending',1),(4,10,'done',1),(5,10,'pending',1);





























