CREATE TABLE `speler` (
  `gebruikersnaam` varchar(45) NOT NULL,
  `wachtwoord` varchar(45) NOT NULL,
  `adminrechten` tinyint(4) NOT NULL,
  `naam` varchar(45) DEFAULT NULL,
  `voornaam` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`gebruikersnaam`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
