-- MySQL dump 10.13  Distrib 5.7.12, for Win32 (AMD64)
--
-- Host: 127.0.0.1    Database: veterinaria
-- ------------------------------------------------------
-- Server version	5.7.15-log

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
-- Table structure for table `servicio`
--

DROP TABLE IF EXISTS `servicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `servicio` (
  `ID_SERVICIO` int(11) NOT NULL AUTO_INCREMENT,
  `ID_MASCOTA` int(11) NOT NULL,
  `ESTADO` varchar(15) DEFAULT NULL,
  `ID_CLIENTE` int(11) NOT NULL,
  `ID_TIPO_SERVICIO` int(11) DEFAULT NULL,
  `SUBTOTAL` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`ID_SERVICIO`,`ID_MASCOTA`,`ID_CLIENTE`),
  KEY `fk_SERVICIO_MASCOTA1_idx` (`ID_MASCOTA`),
  KEY `fk_SERVICIO_CLIENTE1_idx` (`ID_CLIENTE`),
  KEY `FK_TIPO_SERVICIO_SERVICIO` (`ID_TIPO_SERVICIO`),
  CONSTRAINT `FK_TIPO_SERVICIO_SERVICIO` FOREIGN KEY (`ID_TIPO_SERVICIO`) REFERENCES `tipo_servicio` (`ID_TIPO_SERVICIO`),
  CONSTRAINT `fk_SERVICIO_CLIENTE1` FOREIGN KEY (`ID_CLIENTE`) REFERENCES `cliente` (`ID_CLIENTE`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_SERVICIO_MASCOTA1` FOREIGN KEY (`ID_MASCOTA`) REFERENCES `mascota` (`ID_MASCOTA`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicio`
--

LOCK TABLES `servicio` WRITE;
/*!40000 ALTER TABLE `servicio` DISABLE KEYS */;
INSERT INTO `servicio` VALUES (1,2,'PENDIENTE',1,3,20),(2,1,'PENDIENTE',1,10,120),(3,2,'PENDIENTE',1,3,20);
/*!40000 ALTER TABLE `servicio` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-22  1:58:49
