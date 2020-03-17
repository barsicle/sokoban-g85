CREATE TABLE `spelbord` (
  `spelbordNaam` varchar(45) NOT NULL,
  `volgorde` int(11) NOT NULL,
  `spelNaam` varchar(45) NOT NULL,
  PRIMARY KEY (`spelbordNaam`),
  UNIQUE KEY `spelbordNaam_UNIQUE` (`spelbordNaam`),
  KEY `spelNaam_idx` (`spelNaam`),
  CONSTRAINT `spelNaam` FOREIGN KEY (`spelNaam`) REFERENCES `spel` (`spelNaam`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
