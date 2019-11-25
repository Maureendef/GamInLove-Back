-- MySQL Script generated by MySQL Workbench
-- Mon Nov 25 12:29:14 2019
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`like_emis`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`like_emis` (
  `like_emis_id` INT NOT NULL,
  `geek_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`like_emis_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `mydb`.`like_recus`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`like_recus` (
  `like_recus_id` INT NOT NULL,
  `geek_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`like_recus_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `mydb`.`geek`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`geek` (
  `geek_id` INT NOT NULL AUTO_INCREMENT,
  `pseudo` VARCHAR(16) NOT NULL,
  `sexe` CHAR NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `photo` VARCHAR(255) NULL,
  `ville` VARCHAR(45) NOT NULL,
  `jeux` VARCHAR(45) NOT NULL,
  `type_compte` INT NOT NULL DEFAULT 0,
  `type_compte_darkside` INT NOT NULL DEFAULT 0,
  `like_emis_id` INT UNSIGNED NOT NULL,
  `like_recu_id` INT UNSIGNED NOT NULL,
  `like_emis_like_emis_id` INT NOT NULL,
  `like_recus_like_recus_id` INT NOT NULL,
  PRIMARY KEY (`geek_id`),
  UNIQUE INDEX `id_UNIQUE` (`geek_id` ASC) VISIBLE,
  INDEX `fk_geek_like_emis_idx` (`like_emis_like_emis_id` ASC) VISIBLE,
  INDEX `fk_geek_like_recus1_idx` (`like_recus_like_recus_id` ASC) VISIBLE,
  CONSTRAINT `fk_geek_like_emis`
    FOREIGN KEY (`like_emis_like_emis_id`)
    REFERENCES `mydb`.`like_emis` (`like_emis_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_geek_like_recus1`
    FOREIGN KEY (`like_recus_like_recus_id`)
    REFERENCES `mydb`.`like_recus` (`like_recus_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`jeux`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`jeux` (
  `jeux_id` INT NOT NULL AUTO_INCREMENT,
  `jeux` VARCHAR(255) NOT NULL,
  `rang` VARCHAR(45) NULL,
  `icone` VARCHAR(45) NULL,
  PRIMARY KEY (`jeux_id`),
  UNIQUE INDEX `jeux_id_UNIQUE` (`jeux_id` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `mydb`.`Events`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Events` (
  `events_id` INT NOT NULL AUTO_INCREMENT,
  `nom` VARCHAR(255) NULL,
  `ville` VARCHAR(45) NULL,
  `description` VARCHAR(45) NULL,
  PRIMARY KEY (`events_id`),
  UNIQUE INDEX `events_id_UNIQUE` (`events_id` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `mydb`.`geek_has_jeux`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`geek_has_jeux` (
  `geek_geek_id` INT NOT NULL,
  `jeux_jeux_id` INT NOT NULL,
  PRIMARY KEY (`geek_geek_id`, `jeux_jeux_id`),
  INDEX `fk_geek_has_jeux_jeux1_idx` (`jeux_jeux_id` ASC) VISIBLE,
  INDEX `fk_geek_has_jeux_geek1_idx` (`geek_geek_id` ASC) VISIBLE,
  CONSTRAINT `fk_geek_has_jeux_geek1`
    FOREIGN KEY (`geek_geek_id`)
    REFERENCES `mydb`.`geek` (`geek_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_geek_has_jeux_jeux1`
    FOREIGN KEY (`jeux_jeux_id`)
    REFERENCES `mydb`.`jeux` (`jeux_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`geek_has_Events`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`geek_has_Events` (
  `geek_geek_id` INT NOT NULL,
  `Events_events_id` INT NOT NULL,
  PRIMARY KEY (`geek_geek_id`, `Events_events_id`),
  INDEX `fk_geek_has_Events_Events1_idx` (`Events_events_id` ASC) VISIBLE,
  INDEX `fk_geek_has_Events_geek1_idx` (`geek_geek_id` ASC) VISIBLE,
  CONSTRAINT `fk_geek_has_Events_geek1`
    FOREIGN KEY (`geek_geek_id`)
    REFERENCES `mydb`.`geek` (`geek_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_geek_has_Events_Events1`
    FOREIGN KEY (`Events_events_id`)
    REFERENCES `mydb`.`Events` (`events_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`match`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`match` (
  `match_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `like_recus_like_recus_id` INT NOT NULL,
  `like_emis_like_emis_id` INT NOT NULL,
  INDEX `fk_user_like_recus1_idx` (`like_recus_like_recus_id` ASC) VISIBLE,
  INDEX `fk_user_like_emis1_idx` (`like_emis_like_emis_id` ASC) VISIBLE,
  PRIMARY KEY (`match_id`),
  CONSTRAINT `fk_user_like_recus1`
    FOREIGN KEY (`like_recus_like_recus_id`)
    REFERENCES `mydb`.`like_recus` (`like_recus_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_like_emis1`
    FOREIGN KEY (`like_emis_like_emis_id`)
    REFERENCES `mydb`.`like_emis` (`like_emis_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
