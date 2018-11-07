-- MySQL Script generated by MySQL Workbench
-- Tue Nov  6 16:56:05 2018
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema bluelib
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `bluelib` ;

-- -----------------------------------------------------
-- Schema bluelib
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bluelib` DEFAULT CHARACTER SET utf8 ;
USE `bluelib` ;

-- -----------------------------------------------------
-- Table `bluelib`.`anrede`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bluelib`.`anrede` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `bezeichnung` VARCHAR(5) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bluelib`.`ort`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bluelib`.`ort` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `plz` VARCHAR(6) NULL,
  `ort` VARCHAR(30) NULL,
  `kanton` VARCHAR(30) NULL,
  `abkürzung` VARCHAR(3) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bluelib`.`statusMA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bluelib`.`statusMA` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `bezeichnung` VARCHAR(10) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bluelib`.`mitarbeiter`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bluelib`.`mitarbeiter` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `benutzername` VARCHAR(30) NULL,
  `passwort` VARCHAR(16) NULL,
  `admin` TINYINT(1) NULL,
  `statusMA_id` INT NOT NULL,
  PRIMARY KEY (`id`, `statusMA_id`),
  INDEX `fk_Mitarbeiter_statusMA1_idx` (`statusMA_id` ASC),
  CONSTRAINT `fk_Mitarbeiter_statusMA1`
    FOREIGN KEY (`statusMA_id`)
    REFERENCES `bluelib`.`statusMA` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bluelib`.`statusPers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bluelib`.`statusPers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `bezeichnung` VARCHAR(10) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bluelib`.`person`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bluelib`.`person` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `vorname` VARCHAR(50) NULL,
  `nachname` VARCHAR(50) NULL,
  `Geburtstag` DATE NULL,
  `telefon` VARCHAR(30) NULL,
  `StrasseUndNr` VARCHAR(70) NULL,
  `bemerkung` VARCHAR(300) NULL,
  `email` VARCHAR(50) NULL,
  `erfassungsdatum` DATE NULL,
  `person_id` INT NULL,
  `anrede_id` INT NULL,
  `ort_id` INT NULL,
  `mitarbeiter_id` INT NULL,
  `statusPers_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Person_Anrede1_idx` (`anrede_id` ASC),
  INDEX `fk_Person_Ort1_idx` (`ort_id` ASC),
  INDEX `fk_Person_Mitarbeiter1_idx` (`mitarbeiter_id` ASC),
  INDEX `fk_Person_StatusPers1_idx` (`statusPers_id` ASC),
  INDEX `fk_Person_Person1_idx` (`person_id` ASC),
  CONSTRAINT `fk_Person_Anrede1`
    FOREIGN KEY (`anrede_id`)
    REFERENCES `bluelib`.`anrede` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Person_Ort1`
    FOREIGN KEY (`ort_id`)
    REFERENCES `bluelib`.`ort` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Person_Mitarbeiter1`
    FOREIGN KEY (`mitarbeiter_id`)
    REFERENCES `bluelib`.`mitarbeiter` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Person_StatusPers1`
    FOREIGN KEY (`statusPers_id`)
    REFERENCES `bluelib`.`statusPers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Person_Person1`
    FOREIGN KEY (`person_id`)
    REFERENCES `bluelib`.`person` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bluelib`.`verlag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bluelib`.`verlag` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NULL,
  `gruendungsdatum` DATE NULL,
  `enddatum` DATE NULL,
  `geloescht` TINYINT(1) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bluelib`.`statusMedi`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bluelib`.`statusMedi` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `bezeichnung` VARCHAR(10) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bluelib`.`medium`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bluelib`.`medium` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `titel` VARCHAR(100) NULL,
  `barcode` INT NULL,
  `preis` DECIMAL NULL,
  `erscheinungsjahr` YEAR NULL,
  `reihe` VARCHAR(30) NULL,
  `erscheinungsort` VARCHAR(30) NULL,
  `erfassungsdatum` DATE NULL,
  `bemerkung` VARCHAR(300) NULL,
  `signatur` VARCHAR(20) NULL,
  `verlag_id` INT NULL,
  `statusMedi_id` INT NULL,
  `person_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Medium_Verlag1_idx` (`verlag_id` ASC),
  INDEX `fk_Medium_StatusMedi1_idx` (`statusMedi_id` ASC),
  INDEX `fk_Person_person_id_idx` (`person_id` ASC),
  CONSTRAINT `fk_Medium_Verlag1`
    FOREIGN KEY (`verlag_id`)
    REFERENCES `bluelib`.`verlag` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Medium_StatusMedi1`
    FOREIGN KEY (`statusMedi_id`)
    REFERENCES `bluelib`.`statusMedi` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Person_person_id`
    FOREIGN KEY (`person_id`)
    REFERENCES `bluelib`.`person` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bluelib`.`ausleihe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bluelib`.`ausleihe` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `person_id` INT NOT NULL,
  `medium_id` INT NOT NULL,
  `von` DATE NULL,
  `retour` DATE NULL,
  `erfasser_person_id` INT NOT NULL,
  `retour_person_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Person_to_Medium_Person1_idx` (`person_id` ASC),
  INDEX `fk_Person_to_Medium_Medium1_idx` (`medium_id` ASC),
  INDEX `fk_erfasser_person_id_idx` (`erfasser_person_id` ASC),
  INDEX `fk_retour_person_id_idx` (`retour_person_id` ASC),
  CONSTRAINT `fk_Person_to_Medium_Person1`
    FOREIGN KEY (`person_id`)
    REFERENCES `bluelib`.`person` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Person_to_Medium_Medium1`
    FOREIGN KEY (`medium_id`)
    REFERENCES `bluelib`.`medium` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_erfasser_person_id`
    FOREIGN KEY (`erfasser_person_id`)
    REFERENCES `bluelib`.`person` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_retour_person_id`
    FOREIGN KEY (`retour_person_id`)
    REFERENCES `bluelib`.`person` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bluelib`.`buch`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bluelib`.`buch` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `seiten` INT NULL,
  `isbn` VARCHAR(45) NULL,
  `auflage` INT NULL,
  `medium_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Buch_Medium1_idx` (`medium_id` ASC),
  CONSTRAINT `fk_Buch_Medium1`
    FOREIGN KEY (`medium_id`)
    REFERENCES `bluelib`.`medium` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bluelib`.`tondokumente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bluelib`.`tondokumente` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `mb` INT NULL,
  `dauer` TIME NULL,
  `medium_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_CD_DVD_Medium1_idx` (`medium_id` ASC),
  CONSTRAINT `fk_CD_DVD_Medium1`
    FOREIGN KEY (`medium_id`)
    REFERENCES `bluelib`.`medium` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bluelib`.`film`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bluelib`.`film` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `gb` INT NULL,
  `dauer` TIME NULL,
  `medium_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_x_Medium1_idx` (`medium_id` ASC),
  CONSTRAINT `fk_x_Medium1`
    FOREIGN KEY (`medium_id`)
    REFERENCES `bluelib`.`medium` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bluelib`.`autor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bluelib`.`autor` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `vorname` VARCHAR(50) NULL,
  `nachname` VARCHAR(50) NULL,
  `geburtsdatum` DATE NULL,
  `todesdatum` DATE NULL,
  `geloescht` TINYINT(1) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bluelib`.`schlagwort`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bluelib`.`schlagwort` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `schlagwort` VARCHAR(30) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bluelib`.`mediumAutor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bluelib`.`mediumAutor` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `medium_id` INT NOT NULL,
  `autor_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Medium_has_Autor_Autor1_idx` (`autor_id` ASC),
  INDEX `fk_Medium_has_Autor_Medium1_idx` (`medium_id` ASC),
  CONSTRAINT `fk_Medium_has_Autor_Medium1`
    FOREIGN KEY (`medium_id`)
    REFERENCES `bluelib`.`medium` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Medium_has_Autor_Autor1`
    FOREIGN KEY (`autor_id`)
    REFERENCES `bluelib`.`autor` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bluelib`.`stammdaten`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bluelib`.`stammdaten` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NULL,
  `strasse` VARCHAR(50) NULL,
  `land` VARCHAR(50) NULL,
  `email` VARCHAR(50) NULL,
  `telefon` VARCHAR(30) NULL,
  `leihfrist` VARCHAR(45) NULL,
  `ort_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_stammdaten_ort1_idx` (`ort_id` ASC),
  CONSTRAINT `fk_stammdaten_ort1`
    FOREIGN KEY (`ort_id`)
    REFERENCES `bluelib`.`ort` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bluelib`.`dezGrp`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bluelib`.`dezGrp` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `gruppe` INT NULL,
  `bezeichnung` VARCHAR(100) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bluelib`.`dezKla`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bluelib`.`dezKla` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `dezGrp_id` INT NULL,
  `dezklasse` VARCHAR(15) NULL,
  `bezeichnung` VARCHAR(200) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_DezKla_DezGrp1_idx` (`dezGrp_id` ASC),
  CONSTRAINT `fk_DezKla_DezGrp1`
    FOREIGN KEY (`dezGrp_id`)
    REFERENCES `bluelib`.`dezGrp` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bluelib`.`inventur`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bluelib`.`inventur` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NULL,
  `beginn` DATE NULL,
  `abgeschlossen` TINYINT(1) NULL,
  `person_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Inventur_Person1_idx` (`person_id` ASC),
  CONSTRAINT `fk_Inventur_Person1`
    FOREIGN KEY (`person_id`)
    REFERENCES `bluelib`.`person` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bluelib`.`inventurMedium`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bluelib`.`inventurMedium` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `inventur_id` INT NOT NULL,
  `medium_id` INT NOT NULL,
  `datumZeit` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Inventur_has_Medium_Medium1_idx` (`medium_id` ASC),
  INDEX `fk_Inventur_has_Medium_Inventur1_idx` (`inventur_id` ASC),
  CONSTRAINT `fk_Inventur_has_Medium_Inventur1`
    FOREIGN KEY (`inventur_id`)
    REFERENCES `bluelib`.`inventur` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Inventur_has_Medium_Medium1`
    FOREIGN KEY (`medium_id`)
    REFERENCES `bluelib`.`medium` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bluelib`.`mediumSchlagwort`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bluelib`.`mediumSchlagwort` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `medium_id` INT NOT NULL,
  `schlagwort_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_medium_has_schlagwort_schlagwort1_idx` (`schlagwort_id` ASC),
  INDEX `fk_medium_has_schlagwort_medium1_idx` (`medium_id` ASC),
  CONSTRAINT `fk_medium_has_schlagwort_medium1`
    FOREIGN KEY (`medium_id`)
    REFERENCES `bluelib`.`medium` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_medium_has_schlagwort_schlagwort1`
    FOREIGN KEY (`schlagwort_id`)
    REFERENCES `bluelib`.`schlagwort` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
