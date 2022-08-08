-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema railway_office
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `railway_office` ;

-- -----------------------------------------------------
-- Schema railway_office
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `railway_office` DEFAULT CHARACTER SET utf8 ;
USE `railway_office` ;

-- -----------------------------------------------------
-- Table `railway_office`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_office`.`role` ;

CREATE TABLE IF NOT EXISTS `railway_office`.`role` (
   `id` INT NOT NULL AUTO_INCREMENT,
   `role` VARCHAR(10) NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `role_UNIQUE` (`role` ASC) VISIBLE);


-- -----------------------------------------------------
-- Table `railway_office`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_office`.`user` ;

CREATE TABLE IF NOT EXISTS `railway_office`.`user` (
   `id` INT NOT NULL AUTO_INCREMENT,
   `login` VARCHAR(45) NOT NULL,
    `fist_name` VARCHAR(20) NOT NULL,
    `last_name` VARCHAR(25) NOT NULL,
    `password` VARCHAR(30) NOT NULL,
    `role_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `nickname_UNIQUE` (`login` ASC) VISIBLE,
    INDEX `fk_user_role_idx` (`role_id` ASC) VISIBLE,
    CONSTRAINT `fk_user_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `railway_office`.`role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `railway_office`.`train`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_office`.`train` ;

CREATE TABLE IF NOT EXISTS `railway_office`.`train` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `number` INT UNSIGNED NOT NULL,
    `seats` INT UNSIGNED NOT NULL,
    `cost` DOUBLE UNSIGNED NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `number_UNIQUE` (`number` ASC) VISIBLE);


-- -----------------------------------------------------
-- Table `railway_office`.`trip`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_office`.`trip` ;

CREATE TABLE IF NOT EXISTS `railway_office`.`trip` (
`id` INT NOT NULL AUTO_INCREMENT,
    `start_station` VARCHAR(30) NOT NULL,
    `departure_date` DATE NOT NULL,
    `departure_time` TIME NOT NULL,
    `final_station` VARCHAR(30) NOT NULL,
    `arrival_date` DATE NOT NULL,
    `arrival_time` TIME NOT NULL,
    `train_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_trip_train1_idx` (`train_id` ASC) VISIBLE,
    CONSTRAINT `fk_trip_train1`
    FOREIGN KEY (`train_id`)
    REFERENCES `railway_office`.`train` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table `railway_office`.`user_has_trip`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_office`.`user_has_trip` ;

CREATE TABLE IF NOT EXISTS `railway_office`.`user_has_trip` (
    `user_id` INT NOT NULL,
    `trip_id` INT NOT NULL,
    `amount` INT UNSIGNED NOT NULL,
    PRIMARY KEY (`user_id`, `trip_id`),
    INDEX `fk_user_has_trip_trip1_idx` (`trip_id` ASC) VISIBLE,
    INDEX `fk_user_has_trip_user1_idx` (`user_id` ASC) VISIBLE,
CONSTRAINT `fk_user_has_trip_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `railway_office`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_user_has_trip_trip1`
    FOREIGN KEY (`trip_id`)
    REFERENCES `railway_office`.`trip` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `railway_office`.`settlement`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_office`.`settlement` ;

CREATE TABLE IF NOT EXISTS `railway_office`.`settlement` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `railway_office`.`trip_has_settlement`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_office`.`trip_has_settlement` ;

CREATE TABLE IF NOT EXISTS `railway_office`.`trip_has_settlement` (
    `trip_id` INT NOT NULL,
    `settlement_id` INT NOT NULL,
    PRIMARY KEY (`trip_id`, `settlement_id`),
    INDEX `fk_trip_has_settlement_settlement1_idx` (`settlement_id` ASC) VISIBLE,
    INDEX `fk_trip_has_settlement_trip1_idx` (`trip_id` ASC) VISIBLE,
    CONSTRAINT `fk_trip_has_settlement_trip1`
    FOREIGN KEY (`trip_id`)
    REFERENCES `railway_office`.`trip` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT `fk_trip_has_settlement_settlement1`
    FOREIGN KEY (`settlement_id`)
    REFERENCES `railway_office`.`settlement` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data insertion into `railway_office`.`role`
-- -----------------------------------------------------
INSERT INTO `role` (`role`) VALUES ('admin'), ('user');
