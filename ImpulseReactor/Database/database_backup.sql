-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: cabirdb
-- ------------------------------------------------------
-- Server version	5.5.5-10.2.9-MariaDB

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
-- Table structure for table `cabs`
--

DROP TABLE IF EXISTS `cabs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cabs` (
  `CabID` int(11) NOT NULL AUTO_INCREMENT,
  `Brand` varchar(255) DEFAULT NULL,
  `CabName` varchar(255) DEFAULT NULL,
  `Size` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`CabID`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cabs`
--

LOCK TABLES `cabs` WRITE;
/*!40000 ALTER TABLE `cabs` DISABLE KEYS */;
INSERT INTO `cabs` VALUES (1,'Bogner','212C','2x12\r'),(2,'Fender','Blues','1x12\r'),(3,'Fender','DeVille','4x10\r'),(4,'Carvin','Legacy','4x12\r'),(5,'Marshall','1960A','4x12\r'),(7,'Mesa','Rectifier','4x12\r'),(8,'Morgan','212','2x12\r'),(9,'Port City','OS Wave 212','2x12\r'),(10,'Port City','OS Wave 412','4x12\r'),(11,'EVH','5150 III','4x12\r'),(12,'Orange','PPC412','4x12\r'),(13,'Mojotone','Lexrst Alex Lifeson','4x12\r'),(14,'Marshall','1960TV','4x12\r'),(15,'Orange','PPC212','2x12\r'),(16,'Diezel','Rear Load 4x12','4x12\r'),(17,'Diezel','Front Load 4x12','4x12\r'),(18,'Marshall','Mode Four','4x12\r'),(19,'Fender','Tweed Deluxe','1x12\r'),(20,'Suppro','Thunderbolt S6420','1x15\r'),(21,'Fender','Tweed Bassman','4x10\r'),(22,'Marshall','68 Basketweave','4x12\r'),(23,'Hiwatt','75 SE4123','4x12\r'),(24,'Ampeg','SVT 810','8x10\r'),(25,'Hartke','4.5XL','4x10\r'),(26,'Bogner','Uberkab','4x12\r'),(27,'ENGL','Pro E412VS','4x12\r'),(28,'Fender','Dual Showman ','2x12\r'),(29,'Fender','Twin','2x12\r'),(30,'Fender','1960','4x12\r'),(31,'Matchless','ES212','2x12\r'),(32,'Peavey','5150 4x12','4x12\r'),(33,'Roland','JC-120','2x12\r'),(34,'Soldano','4x12','4x12\r'),(35,'Vox','AC-30','2x12\r'),(36,'Peavey','Headliner 410','4x10\r'),(37,'Allston','2x12','2x12\r'),(38,'Allston','2x10','2x10\r'),(39,'Marshall','1960B','4x12\r'),(40,'Roland','JC-120','2x12\r'),(41,'Zilla Cabs','Modern 2x12','2x12\r'),(42,'Zilla Cabs','Custom Fatboy 2x12','2x12\r'),(43,'Zilla Cabs','Super Fatboy 2x12','2x12\r');
/*!40000 ALTER TABLE `cabs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `impulses`
--

DROP TABLE IF EXISTS `impulses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `impulses` (
  `ImpulseID` int(11) NOT NULL AUTO_INCREMENT,
  `ImpulseName` varchar(255) NOT NULL,
  `Creator` varchar(255) DEFAULT NULL,
  `PackName` varchar(255) DEFAULT NULL,
  `CabID` int(11) DEFAULT NULL,
  `SpeakerID` int(11) DEFAULT NULL,
  `Description` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`ImpulseID`),
  KEY `CabID` (`CabID`),
  CONSTRAINT `impulses_ibfk_1` FOREIGN KEY (`CabID`) REFERENCES `cabs` (`CabID`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `impulses`
--

LOCK TABLES `impulses` WRITE;
/*!40000 ALTER TABLE `impulses` DISABLE KEYS */;
INSERT INTO `impulses` VALUES (1,'Bog 212','3Sigma','Bog 212',1,1,'<none>\r'),(2,'Fend Blues','3Sigma','Fend Blues',2,34,'<none>\r'),(3,'Fend Deville','3Sigma','Fend Deville',3,36,'<none>\r'),(4,'LegaC 412','3Sigma','LegaC 412',4,1,'<none>\r'),(5,'Marsh 1960A','3Sigma','Marsh 1960A',5,2,'<none>\r'),(6,'Mart 45','3Sigma','Mart 45',NULL,NULL,'<none>\r'),(7,'Mes Rec','3Sigma','Mes Rec',7,1,'<none>\r'),(8,'Morg 212','3Sigma','Morg 212',8,3,'<none>\r'),(9,'Port 212','3Sigma','Port 212',9,1,'<none>\r'),(10,'Port 412','3Sigma','Port 412',10,1,'<none>\r'),(11,'Catharsis','Catharsis','Catharsis',7,1,'<none>\r'),(12,'5153','Fractal','4x12 Collection',11,4,'<none>\r'),(13,'Cali','Fractal','4x12 Collection',7,5,'<none>\r'),(14,'Citrus','Fractal','4x12 Collection',12,1,'<none>\r'),(15,'Lerxst','Fractal','4x12 Collection',13,6,'<none>\r'),(16,'Recto','Fractal','4x12 Collection',7,1,'<none>\r'),(17,'Recto_New','Fractal','4x12 Collection',7,1,'<none>\r'),(18,'TV','Fractal','4x12 Collection',14,7,'<none>\r'),(19,'God\'s Cab','God\'s Cab','God\'s Cab',7,1,'<none>\r'),(20,'GuitarHack','GuitarHack','GuitarHack',NULL,NULL,'<none>\r'),(21,'Orange212','Danimae','Orange212',15,1,'<none>\r'),(22,'Port City OS2x12','Seane Ashe','Port City OS2x12',9,1,'<none>\r'),(23,'412_DZL-RL','Ownhammer','412_DZL-RL',16,1,'<none>\r'),(24,'412_DZL-RL','Ownhammer','412_DZL-RL',16,9,'<none>\r'),(25,'GTR_MES-ST','Ownhammer','GTR_MES-ST',7,1,'<none>\r'),(26,'412 DZL','Ownhammer','Heavy Hitters 1',17,1,'<none>\r'),(27,'412 DZL','Ownhammer','Heavy Hitters 1',17,9,'<none>\r'),(28,'412 DZL','Ownhammer','Heavy Hitters 1',17,10,'<none>\r'),(29,'412 MAR','Ownhammer','Heavy Hitters 1',18,4,'<none>\r'),(30,'412 MAR','Ownhammer','Heavy Hitters 1',18,2,'<none>\r'),(31,'412 MAR','Ownhammer','Heavy Hitters 1',18,11,'<none>\r'),(32,'412 MES','Ownhammer','Heavy Hitters 1',7,12,'<none>\r'),(33,'412 MES','Ownhammer','Heavy Hitters 1',7,13,'<none>\r'),(34,'412 MES','Ownhammer','Heavy Hitters 1',7,1,'<none>\r'),(35,'412 ORN','Ownhammer','Heavy Hitters 1',12,13,'<none>\r'),(36,'412 ORN','Ownhammer','Heavy Hitters 1',12,7,'<none>\r'),(37,'412 ORN','Ownhammer','Heavy Hitters 1',12,1,'<none>\r'),(38,'Tweed Deluxe • Celestion Blue','Redwirez','Redwirez',19,14,'<none>\r'),(39,'Tweed Deluxe • Jensen P12R','Redwirez','Redwirez',19,15,'<none>\r'),(40,'Supro Thunderbolt S6420 • Jensen 15\'\'','Redwirez','Redwirez',20,16,'<none>\r'),(41,'Tweed Bassman • \'57 Jensen P10Qs','Redwirez','Redwirez',21,17,'<none>\r'),(42,'\'68 Marshall Basketweave • Celestion G12Ls','Redwirez','Redwirez',22,18,'<none>\r'),(43,'\'68 Marshall Basketweave • Celestion G12M20s','Redwirez','Redwirez',22,19,'<none>\r'),(44,'\'68 Marshall Basketweave • Vintage G12H30s','Redwirez','Redwirez',22,13,'<none>\r'),(45,'\'68 Marshall Basketweave • Vintage G12M25s','Redwirez','Redwirez',22,7,'<none>\r'),(46,'\'75 Hiwatt SE4123 • 50-watt Fane Purplebacks','Redwirez','Redwirez',23,20,'<none>\r'),(47,'Ampeg SVT 810 • SVT 10s • Cabinet IR Library','Redwirez','Bass Cabinets',24,21,'<none>\r'),(48,'Hartke 4.5XL • Hartke Aluminum 10s','Redwirez','Bass Cabinets',25,22,'<none>\r'),(49,'Bogner Uberkab • Celestion G12T-75s','Redwirez','Redwirez',26,2,'<none>\r'),(50,'Bogner Uberkab • Celestion Vintage 30s','Redwirez','Redwirez',26,1,'<none>\r'),(51,'ENGL Pro 4x12 • Vintage 30s','Redwirez','Redwirez',27,1,'<none>\r'),(52,'Fender Dual Showman • JBL D130s','Redwirez','Redwirez',28,23,'<none>\r'),(53,'Fender Twin • JBL D120Fs','Redwirez','Redwirez',29,24,'<none>\r'),(54,'Fender Twin • Jensen C12Ns','Redwirez','Redwirez',29,25,'<none>\r'),(55,'Marshall 1960 • Celestion G12T-75s','Redwirez','Redwirez',5,2,'<none>\r'),(56,'Marshall 1960 4x12 • Celestion G12Ms','Redwirez','Redwirez',5,6,'<none>\r'),(57,'Marshall 1960 4x12 • JBL K120s','Redwirez','Redwirez',5,26,'<none>\r'),(58,'Marshall 1960 4x12 • Vintage 30s','Redwirez','Redwirez',5,1,'<none>\r'),(59,'Marshall1960A-G12Ms','Redwirez','Redwirez',5,6,'<none>\r'),(60,'Marshall1960B-V30s','Redwirez','Redwirez',39,1,'<none>\r'),(61,'Matchless ES212 • Celestion G12H30','Redwirez','Redwirez',31,13,'<none>\r'),(62,'Matchless ES212 • Celestion G12M25','Redwirez','Redwirez',31,7,'<none>\r'),(63,'Mesa Rectifier 4x12 • Vintage 30s','Redwirez','Redwirez',7,1,'<none>\r'),(64,'Orange PPC412 4x12 • Vintage 30s','Redwirez','Redwirez',12,1,'<none>\r'),(65,'Peavey 5150 4x12 • Sheffield 1200s','Redwirez','Redwirez',32,27,'<none>\r'),(66,'Roland JC-120 • Roland 12s','Redwirez','Redwirez',40,37,'<none>\r'),(67,'Soldano 412B • Eminence Legend V12s','Redwirez','Redwirez',34,28,'<none>\r'),(68,'Soldano 412B • Eminence S12Xs','Redwirez','Redwirez',34,29,'<none>\r'),(69,'Vox AC30 • Vox Alnico Silvers','Redwirez','Redwirez',35,30,'<none>\r'),(70,'Vox AC30 • Vox-labeled Blues','Redwirez','Redwirez',35,31,'<none>\r'),(71,'AES_big_EV_4x12','AES V30 + EV','AES V30 + EV',NULL,32,'<none>\r'),(72,'AES_big_Vint30_4x12','AES V30 + EV','AES V30 + EV',NULL,1,'<none>\r'),(73,'AES_big_4x10','AES V30 + EV','AES V30 + EV',36,33,'<none>\r'),(74,'Allston 2x12','Allston 2x12','Allston 2x12',37,NULL,'<none>\r'),(75,'Allston 2x12','Allston 2x10','Allston 2x10',38,NULL,'<none>\r'),(76,'ML Zilla Cream ALN','Fractal','ML Bulb Zilla',41,38,'<none>\r'),(77,'ML Zilla Cream H','Fractal','ML Bulb Zilla',42,3,'<none>\r'),(78,'ML Zilla Cream M','Fractal','ML Bulb Zilla',41,39,'<none>\r'),(79,'ML Zilla Green M','Fractal','ML Bulb Zilla',43,6,'<none>\r'),(80,'ML Zilla K100 M','Fractal','ML Bulb Zilla',43,9,'<none>\r'),(81,'ML Zilla V30','Fractal','ML Bulb Zilla',42,1,'<none>\r');
/*!40000 ALTER TABLE `impulses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `impulsesmics`
--

DROP TABLE IF EXISTS `impulsesmics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `impulsesmics` (
  `ImpulseID` int(11) NOT NULL,
  `MicID` int(11) NOT NULL,
  PRIMARY KEY (`ImpulseID`,`MicID`),
  KEY `MicID` (`MicID`),
  CONSTRAINT `impulsesmics_ibfk_1` FOREIGN KEY (`ImpulseID`) REFERENCES `impulses` (`ImpulseID`),
  CONSTRAINT `impulsesmics_ibfk_2` FOREIGN KEY (`MicID`) REFERENCES `mics` (`MicID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `impulsesmics`
--

LOCK TABLES `impulsesmics` WRITE;
/*!40000 ALTER TABLE `impulsesmics` DISABLE KEYS */;
INSERT INTO `impulsesmics` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(2,1),(2,2),(2,3),(2,4),(2,5),(3,1),(3,2),(3,3),(3,4),(3,5),(4,1),(4,2),(4,3),(4,4),(4,5),(5,1),(5,2),(5,3),(5,4),(5,5),(6,1),(6,2),(6,3),(6,4),(6,5),(7,1),(7,2),(7,3),(7,4),(7,5),(8,1),(8,2),(8,3),(8,4),(8,5),(9,1),(9,2),(9,3),(9,4),(9,5),(10,1),(10,2),(10,3),(10,4),(10,5),(11,1),(26,1),(26,6),(26,7),(26,8),(26,9),(26,10);
/*!40000 ALTER TABLE `impulsesmics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mics`
--

DROP TABLE IF EXISTS `mics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mics` (
  `MicID` int(11) NOT NULL AUTO_INCREMENT,
  `MicModel` varchar(255) NOT NULL,
  `MicBrand` varchar(255) NOT NULL,
  PRIMARY KEY (`MicID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mics`
--

LOCK TABLES `mics` WRITE;
/*!40000 ALTER TABLE `mics` DISABLE KEYS */;
INSERT INTO `mics` VALUES (1,'MP47','Microphone Parts\r'),(2,'ATM 250DE','Audio-Tecnica\r'),(3,'U47','Neumann\r'),(4,'Aurora','Roswell\r'),(5,'R121','Royer\r'),(6,'M160','Beyerdynamic\r'),(7,'AT4047','Audio-Technica\r'),(8,'RNR1','SE Electronics\r'),(9,'KSM313','Shure\r'),(10,'e906','Sennheiser\r'),(11,'MD421','Sennheiser\r');
/*!40000 ALTER TABLE `mics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `speakers`
--

DROP TABLE IF EXISTS `speakers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `speakers` (
  `SpeakerID` int(11) NOT NULL AUTO_INCREMENT,
  `SpeakerModel` varchar(255) NOT NULL,
  `SpeakerBrand` varchar(255) DEFAULT NULL,
  `SpeakerName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`SpeakerID`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `speakers`
--

LOCK TABLES `speakers` WRITE;
/*!40000 ALTER TABLE `speakers` DISABLE KEYS */;
INSERT INTO `speakers` VALUES (1,'V30','Celestion','Vintage 30\r'),(2,'G12T-75','Celestion','G12T-75\r'),(3,'G12H-75','Celestion','Creamback\r'),(4,'G12EVH','Celestion','Van Halen\r'),(5,'MC-90','Celestion','Black Shadow Lead 80\'s\r'),(6,'G12M','Celestion','Greenback\r'),(7,'G12M-25','Celestion','Greenback\r'),(8,'G12H','Celestion','Anniversary\r'),(9,'G12K-100','Celestion','K100\r'),(10,'Governer','Eminence','RedCoat\r'),(11,'V30MF','Celestion','Vintage 30 MF\r'),(12,'G12-80','Celestion','Classic Lead 80\r'),(13,'G12H-30','Celestion','Anniversary\r'),(14,'G12T-530','Celestion','Celestion Blue\r'),(15,'P12R','Jensen','P12R\r'),(16,'TB15','Suppro','TB15\r'),(17,'P10Q','Jensen','P10Q\r'),(18,'G12L','Celestion','G12L\r'),(19,'G12M-20','Celestion','Greenback\r'),(20,'Purpleback','Fane','Purpleback\r'),(21,'SVT 10','Ampeg','CTS SVT\r'),(22,'10XL8','Hartke','Aluminum 10\r'),(23,'D130','JBL','D130\r'),(24,'D120F','JBL','D120F\r'),(25,'C12N','Jensen','C12N\r'),(26,'K120','JBL','JBL K120\r'),(27,'1200','Peavey','Sheffield Pro 1200\r'),(28,'V12','Eminence','Legend V12\r'),(29,'S12X','Eminence','S12X\r'),(30,'T1088','Vox','Silver Alnico\r'),(31,'T530','Vox','Blue Alnico\r'),(32,'EV','Electro Voice','Electro Voice\r'),(33,'Peavey 10','Peavey','Peavey 10\r'),(34,'Special Design','Eminence','Eminence 12\r'),(35,'G12H-30','Celestion','Creamback\r'),(36,'Special Design','Eminence','Eminence 10\r'),(37,'Roland 12','Roland','Roland 12\r'),(38,'T5953','Celestion','Cream Alnico\r'),(39,'G12M-65','Celestion','Creamback\r');
/*!40000 ALTER TABLE `speakers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'cabirdb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-30 14:09:57
