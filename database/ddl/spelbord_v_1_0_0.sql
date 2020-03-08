CREATE TABLE `ID222177_g85`.`spelbord` (
  `spelbordId` INT NOT NULL AUTO_INCREMENT,
  `spelbordNaam` VARCHAR(45) NOT NULL,
  `spelId` INT NOT NULL,
  `volgorde` INT NOT NULL,
  PRIMARY KEY (`spelbordId`),
  UNIQUE INDEX `spelbordId_UNIQUE` (`spelbordId` ASC),
  INDEX `spelId_idx` (`spelId` ASC),
  CONSTRAINT `spelId`
    FOREIGN KEY (`spelId`)
    REFERENCES `ID222177_g85`.`spel` (`spelId`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);
