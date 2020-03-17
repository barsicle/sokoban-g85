CREATE TABLE `spel` (
  `spelNaam` varchar(45) NOT NULL,
  `speler` varchar(45) NOT NULL,
  PRIMARY KEY (`spelNaam`),
  UNIQUE KEY `spelNaam_UNIQUE` (`spelNaam`),
  KEY `spelerUsername_idx` (`speler`),
  CONSTRAINT `spelerUsername` FOREIGN KEY (`speler`) REFERENCES `speler` (`gebruikersnaam`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
