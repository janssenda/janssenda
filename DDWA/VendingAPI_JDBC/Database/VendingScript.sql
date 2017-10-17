-- MySQL dump 10.16  Distrib 10.2.9-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: vendingdatabase
-- ------------------------------------------------------
-- Server version	10.2.9-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inventory` (
  `InventoryID` int(11) NOT NULL AUTO_INCREMENT,
  `ProductName` varchar(255) NOT NULL,
  `ProductDate` date NOT NULL,
  `ProductMessage` varchar(255) DEFAULT NULL,
  `ProductInformation` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`InventoryID`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
INSERT INTO `inventory` VALUES (1,'Coke','2020-12-29','Erik  ','Nutrtional Information'),(2,'Coke','2020-06-08','Yuonne  ','Nutrtional Information'),(3,'Coke','2018-07-30','Robt  ','Nutrtional Information'),(4,'Coke','2018-10-18','Aurelia  ','Nutrtional Information'),(5,'Coke','2019-05-30','Cameron  ','Nutrtional Information'),(6,'Coke','2020-02-19','Corazon  ','Nutrtional Information'),(7,'Coke','2021-10-13','Felicitas  ','Nutrtional Information'),(8,'Coke','2021-11-17','Sona  ','Nutrtional Information'),(9,'Diet Coke','2019-10-04','Gracie  ','Nutrtional Information'),(10,'Diet Coke','2019-10-12','Darlene  ','Nutrtional Information'),(11,'Sprite','2019-11-12','Maura  ','Nutrtional Information'),(12,'Sprite','2021-08-05','Katy  ','Nutrtional Information'),(13,'Sprite','2020-07-06','Shawn  ','Nutrtional Information'),(14,'Sprite','2019-10-19','Marcelino  ','Nutrtional Information'),(15,'Sprite','2021-02-26','Hulda  ','Nutrtional Information'),(16,'Sprite','2018-05-11','Bessie  ','Nutrtional Information'),(17,'Sprite','2021-05-25','Angelita  ','Nutrtional Information'),(18,'Sprite','2019-08-25','Ashlee  ','Nutrtional Information'),(19,'Sprite','2019-09-23','Carmela  ','Nutrtional Information'),(20,'Mello Yellow','2018-12-01','Deangelo  ','Nutrtional Information'),(21,'Mello Yellow','2020-09-03','Marquita  ','Nutrtional Information'),(22,'Mello Yellow','2018-07-04','Hien  ','Nutrtional Information'),(23,'Mello Yellow','2018-10-15','Susie  ','Nutrtional Information'),(24,'Mello Yellow','2021-06-06','Nana  ','Nutrtional Information'),(25,'Mello Yellow','2021-01-27','Latoyia  ','Nutrtional Information'),(26,'Mello Yellow','2018-12-07','Claribel  ','Nutrtional Information'),(27,'Dasani','2019-08-04','Martin  ','Nutrtional Information'),(28,'Dasani','2019-12-15','Krystina  ','Nutrtional Information'),(29,'Dasani','2019-08-19','Shelia  ','Nutrtional Information'),(30,'Dasani','2020-06-22','Donnette  ','Nutrtional Information'),(31,'Dasani','2019-09-15','Magda  ','Nutrtional Information'),(32,'Dasani','2021-10-10','Salvatore  ','Nutrtional Information'),(33,'Dasani','2018-10-07','Ernie  ','Nutrtional Information'),(34,'Dasani','2021-09-11','Tiffani  ','Nutrtional Information'),(35,'Cherry Coke','2021-08-24','Dawne  ','Nutrtional Information'),(36,'Cherry Coke','2018-12-23','Cathrine  ','Nutrtional Information'),(37,'Cherry Coke','2021-10-20','Rosio  ','Nutrtional Information'),(38,'Cherry Coke','2019-06-27','Arden  ','Nutrtional Information'),(39,'Cherry Coke','2020-06-08','Kasha  ','Nutrtional Information'),(40,'Cherry Coke','2019-12-13','Leonor  ','Nutrtional Information'),(41,'Cherry Coke','2019-11-08','Kina  ','Nutrtional Information'),(42,'Coke Zero','2020-11-03','Letha  ','Nutrtional Information'),(43,'Coke Zero','2019-04-13','Tanisha  ','Nutrtional Information'),(44,'Coke Zero','2020-12-26','Lindy  ','Nutrtional Information'),(45,'Coke Zero','2021-02-02','Karissa  ','Nutrtional Information'),(46,'Coke Zero','2018-12-06','Sharice  ','Nutrtional Information'),(47,'Coke Zero','2021-01-10','Tamie  ','Nutrtional Information'),(48,'Coke Zero','2018-07-11','Kristine  ','Nutrtional Information'),(49,'Coke Zero','2020-03-26','Rich  ','Nutrtional Information'),(50,'Coke Zero','2020-08-10','Benito  ','Nutrtional Information'),(51,'Coke Zero','2020-06-04','Olive  ','Nutrtional Information');
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prices`
--

DROP TABLE IF EXISTS `prices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prices` (
  `ProductID` int(11) NOT NULL AUTO_INCREMENT,
  `ProductName` varchar(255) NOT NULL,
  `ProductPrice` decimal(10,2) NOT NULL,
  PRIMARY KEY (`ProductID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prices`
--

LOCK TABLES `prices` WRITE;
/*!40000 ALTER TABLE `prices` DISABLE KEYS */;
INSERT INTO `prices` VALUES (1,'Coke',1.50),(2,'Diet Coke',1.50),(3,'Coke Zero',1.50),(4,'Cherry Coke',1.50),(5,'Sprite',1.50),(6,'Mello Yellow',1.50),(7,'Dasani',1.75),(8,'Diet Sprite',1.50),(9,'Nuka Cola',2.00);
/*!40000 ALTER TABLE `prices` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-17 11:34:54
