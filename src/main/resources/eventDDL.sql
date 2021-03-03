CREATE DATABASE IF NOT EXISTS events ;
CREATE TABLE IF NOT EXISTS `events`.`schedule` (
  `eventId` int NOT NULL AUTO_INCREMENT,
  `startDateTime` date DEFAULT NULL,
  `endDateTime` date DEFAULT NULL,
  `repeatable` tinyint DEFAULT NULL,
  `repeatationType` varchar(45) DEFAULT NULL,
  `repeationFrequency` varchar(45) DEFAULT NULL,
  `days` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `eventName` varchar(45) DEFAULT NULL,
  `eventDetails` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`eventId`)
) ENGINE=InnoDB ;
