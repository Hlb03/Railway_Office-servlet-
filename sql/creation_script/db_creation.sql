-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema railway_office
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `railway_office` ;

-- -----------------------------------------------------
-- Schema railway_office
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `railway_office` DEFAULT CHARACTER SET utf8mb3 ;
USE `railway_office` ;

-- -----------------------------------------------------
-- Table `railway_office`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_office`.`role` ;

CREATE TABLE IF NOT EXISTS `railway_office`.`role` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `role` VARCHAR(10) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `role_UNIQUE` (`role` ASC) VISIBLE)
    ENGINE = InnoDB
    AUTO_INCREMENT = 3
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `railway_office`.`settlement`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_office`.`settlement` ;

CREATE TABLE IF NOT EXISTS `railway_office`.`settlement` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 38
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `railway_office`.`train`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_office`.`train` ;

CREATE TABLE IF NOT EXISTS `railway_office`.`train` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `number` VARCHAR(4) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `number_UNIQUE` (`number` ASC) VISIBLE)
ENGINE = InnoDB
    AUTO_INCREMENT = 7
    DEFAULT CHARACTER SET = utf8mb3;


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
   `seats` INT UNSIGNED NOT NULL,
   `cost` DECIMAL(5,2) NOT NULL,
   `train_id` INT NOT NULL,
   PRIMARY KEY (`id`),
   INDEX `fk_trip_train1_idx` (`train_id` ASC) VISIBLE,
   CONSTRAINT `fk_trip_train1`
   FOREIGN KEY (`train_id`)
   REFERENCES `railway_office`.`train` (`id`)
   ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB
    AUTO_INCREMENT = 12
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `railway_office`.`trip_has_settlement`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_office`.`trip_has_settlement` ;

CREATE TABLE IF NOT EXISTS `railway_office`.`trip_has_settlement` (
    `trip_id` INT NOT NULL,
    `settlement_id` INT NOT NULL,
    `order` INT UNSIGNED NULL DEFAULT NULL,
    PRIMARY KEY (`trip_id`, `settlement_id`),
    INDEX `fk_trip_has_settlement_settlement1_idx` (`settlement_id` ASC) VISIBLE,
    INDEX `fk_trip_has_settlement_trip1_idx` (`trip_id` ASC) VISIBLE,
    CONSTRAINT `fk_trip_has_settlement_settlement1`
    FOREIGN KEY (`settlement_id`)
    REFERENCES `railway_office`.`settlement` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT `fk_trip_has_settlement_trip1`
    FOREIGN KEY (`trip_id`)
    REFERENCES `railway_office`.`trip` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `railway_office`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_office`.`user` ;

CREATE TABLE IF NOT EXISTS `railway_office`.`user` (
   `id` INT NOT NULL AUTO_INCREMENT,
   `login` VARCHAR(45) NOT NULL,
   `first_name` VARCHAR(20) NULL DEFAULT NULL,
   `last_name` VARCHAR(25) NOT NULL,
   `password` VARCHAR(30) NOT NULL,
   `role_id` INT NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE INDEX `nickname_UNIQUE` (`login` ASC) VISIBLE,
   INDEX `fk_user_role_idx` (`role_id` ASC) VISIBLE,
   CONSTRAINT `fk_user_role`
   FOREIGN KEY (`role_id`)
   REFERENCES `railway_office`.`role` (`id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 35
    DEFAULT CHARACTER SET = utf8mb3;


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
    CONSTRAINT `fk_user_has_trip_trip1`
    FOREIGN KEY (`trip_id`)
REFERENCES `railway_office`.`trip` (`id`),
    CONSTRAINT `fk_user_has_trip_user1`
        FOREIGN KEY (`user_id`)
            REFERENCES `railway_office`.`user` (`id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


-- -----------------------------------------------------
-- Data insertion into `railway_office`.`role`
-- -----------------------------------------------------
INSERT INTO `role` (`role`) VALUES ('admin'), ('user');

-- -----------------------------------------------------
-- Data insertion into `railway_office`.`user`
-- -----------------------------------------------------
-- Create administrator example
INSERT INTO `user` (`login`, `first_name`, `last_name`, `password`, `role_id`)
    VALUE ('some@gmail.com', 'Anthony', 'Perk', 'passCode', '3');
-- Create user example
INSERT INTO `user` (`login`, `first_name`, `last_name`, `password`, `role_id`)
    VALUES ('surname@gmail.com', 'Nataly', 'Star', 'userPass', '4'),
           ('Tanan@ukr.net', 'Ana', 'Tan', 'anan', '4');

-- -----------------------------------------------------
-- Data insertion into `railway_office`.`settlement`
-- -----------------------------------------------------
INSERT INTO `settlement` (`name`) VALUES ('Kvasy'), ('Rakhiv'), ('Yasinya'), ('Vorokhta'),
                                         ('Yaremche'), ('Ivano-Frankivsk'), ('Lviv'), ('Kyiv'),
                                         ('Poltava'), ('Kharkiv'), ('Odessa'), ('Podilsk'),
                                         ('Vinnytsia'), ('Kherson'), ('Mykolaiv'), ('Kovel'),
                                         ('Rivne'), ('Lutsk'), ('Ostroh'), ('Korosten'),
                                         ('Zaporizhzhia'), ('Korsun'), ('Bila Tserkva'),
                                         ('Uzhhorod'), ('Chernivtsi'), ('Ternopil'),
                                         ('Khmelnytskyi'), ('Zhytomyr'), ('Chernihiv'),
                                         ('Konotop'), ('Pryluky'), ('Cherkasy'),
                                         ('Kirovohrad'), ('Kryvyi Rih'),
                                         ('Kremenchuk'), ('Dnipro'), ('Melitipol');

-- -----------------------------------------------------
-- Data insertion into `railway_office`.`train`
-- -----------------------------------------------------
INSERT INTO `train` (`number`) VALUES ('097К'),
                                       ('017О'),
                                       ('001Д'),
                                       ('715К'),
                                       ('749К');

-- -----------------------------------------------------
-- Data insertion into `railway_office`.`trip`
-- -----------------------------------------------------
INSERT INTO `trip` (`start_station`, `departure_date`, `departure_time`, `final_station`,
                    `arrival_date`, `arrival_time`, `seats`, `cost`, `train_id`)
VALUES ('Kyiv', '2022-08-25', '22:40:36', 'Kovel', '2022-08-26', '06:53:12','40', '320.50', '8'),
       ('Odessa', '2022-08-19', '21:12:28', 'Kyiv', '2022-08-20', '08:22:36', '36', '307.19', '9'),
       ('Zaporizhzhia', '2022-08-22', '01:15:10', 'Rivne', '2022-08-22', '20:19:43', '25', '223.11', '11'),
       ('Lviv', '2022-08-20', '03:40:22', 'Odessa', '2022-08-20', '17:03:12',  '20', '412.76', '7'),
       ('Kherson', '2022-09:01', '09:48:37', 'Konotop', '2022-09-01', '18:16:01', '42', '295.97', '10'),
       ('Mykolaiv', '2022-08-27', '10:38:50', 'Kharkiv', '2022-08-27', '20:43:15', '32', '318.53', '9'),
       ('Chernivtsi', '2022-08-21', '13:20:18', 'Korosten', '2022-08-21', '19:47:11', '27', '240.20', '10'),
       ('Melitipol', '2022-08-24', '11:48:22', 'Poltava', '2022-08-24', '16:01:53', '42', '275.12', '9'),
       ('Chernihiv', '2022-08-26', '23:20:55', 'Vinnytsia', '2022-08-27', '4:17:35', '31', '332.84', '14'),
       ('Kyiv', '2022-08-27', '06:44:02', 'Kovel', '2022-08-27', '13:33:16', '28', '370.41', '12'),
       ('Zhytomyr', '2022-08-23', '02:39:16', 'Cherkasy', '2022-08-23', '06:51:09', '20', '180.90', '13'),
       ('Kryvyi Rih', '2022-08-25', '21:44:17', 'Khmelnytskyi', '2022-08-26', '9:01:05', '34', '290.15', '7');

-- -----------------------------------------------------
-- Data insertion into `railway_office`.`trip_has_settlement`
-- -----------------------------------------------------
INSERT INTO `trip_has_settlement` (`trip_id`, `settlement_id`, `order`)
VALUES ('1', '8', '1'), ('1', '20', '2'), ('1', '19', '3'), ('1', '17', '4'), ('1', '18', '5'), ('1', '16', '6'),
       ('2', '11', '1'), ('2', '12', '2'), ('2', '13', '3'), ('2', '8', '4'),
       ('3', '21', '1'), ('3', '22', '2'), ('3', '23', '3'), ('3', '19', '4'), ('3', '17', '5'),

       ('4', '7', '1'), ('4', '26', '2'), ('4', '27', '3'), ('4', '11', '4'),
       ('5', '14', '1'), ('5', '34', '2'), ('5', '32', '3'), ('5', '31', '4'), ('5', '30', '5'),
       ('6', '15', '1'), ('6', '33', '2'), ('6', '35', '3'), ('6', '9', '4'), ('6', '10', '5');
