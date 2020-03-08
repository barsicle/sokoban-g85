CREATE TABLE `ID222177_g85`.`veld` (
  `veldId` INT NOT NULL AUTO_INCREMENT,
  `x` INT NOT NULL,
  `y` INT NOT NULL,
  `doel` TINYINT(4) NOT NULL,
  `veldType` TINYINT(1) NOT NULL,
  `moveable` INT NULL,
  `spelbordId` INT NOT NULL,
  PRIMARY KEY (`veldId`),
  INDEX `spelbord_idx` (`spelbordId` ASC),
  CONSTRAINT `spelbord`
    FOREIGN KEY (`spelbordId`)
    REFERENCES `ID222177_g85`.`spelbord` (`spelbordId`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);
COMMIT;