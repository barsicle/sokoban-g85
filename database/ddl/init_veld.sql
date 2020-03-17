CREATE TABLE `veld` (
  `veldId` int(11) NOT NULL AUTO_INCREMENT,
  `x` int(11) NOT NULL,
  `y` int(11) NOT NULL,
  `doel` tinyint(1) NOT NULL,
  `veldType` tinyint(1) NOT NULL,
  `moveable` tinyint(4) DEFAULT NULL,
  `spelbordNaam` varchar(45) NOT NULL,
  PRIMARY KEY (`veldId`),
  KEY `spelbordNaam_idx` (`spelbordNaam`),
  CONSTRAINT `spelbordNaam` FOREIGN KEY (`spelbordNaam`) REFERENCES `spelbord` (`spelbordNaam`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=148 DEFAULT CHARSET=latin1;
