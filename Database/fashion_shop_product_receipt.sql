-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: fashion_shop
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `product_receipt`
--

DROP TABLE IF EXISTS `product_receipt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_receipt` (
  `RECEIPT_ID` varchar(10) NOT NULL,
  `RECEIPT_DATE` date NOT NULL,
  `PRODUCT_ID` varchar(20) NOT NULL,
  `RECEIPT_QUANTITY` int NOT NULL,
  `RECEIPT_PRICE` int NOT NULL,
  PRIMARY KEY (`RECEIPT_ID`),
  KEY `PRODUCT_ID` (`PRODUCT_ID`),
  CONSTRAINT `product_receipt_ibfk_1` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `product` (`PRODUCT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_receipt`
--

LOCK TABLES `product_receipt` WRITE;
/*!40000 ALTER TABLE `product_receipt` DISABLE KEYS */;
INSERT INTO `product_receipt` VALUES ('R01','2022-12-01','T01',145,600000),('R02','2022-12-01','T02',255,350000),('R03','2022-12-01','T03',157,600000),('R04','2022-12-01','T04',110,800000),('R05','2022-12-01','T05',100,1250000),('R06','2022-12-01','T06',180,1250000),('R07','2022-12-01','T07',100,2300000),('R08','2022-12-01','T08',205,1540000),('R09','2022-12-01','T09',100,1700000),('R10','2022-12-01','T10',60,1300000),('R11','2022-12-01','T11',160,1250000),('R12','2022-12-01','T12',50,1800000),('R13','2022-12-01','T13',110,1300000),('R14','2022-12-01','T14',110,1300000),('R15','2022-12-01','T15',70,2000000),('R16','2022-12-01','JNS01',150,600000),('R17','2022-12-01','JNS02',150,600000),('R18','2022-12-01','JNS03',190,370000),('R19','2022-12-01','JNS04',100,485000),('R20','2022-12-01','JNS05',120,600000),('R21','2022-12-01','JNS06',100,485000),('R22','2022-12-01','JNS07',130,600000),('R23','2022-12-01','JNS08',130,485000),('R24','2022-12-01','JNS09',110,1075000),('R25','2022-12-01','JNS10',100,1075000),('R26','2022-12-01','JNS11',180,370000),('R27','2022-12-01','JNS12',100,840000),('R28','2022-12-01','JNS13',70,840000),('R29','2022-12-01','JNS14',150,600000),('R30','2022-12-01','JNS15',80,840000),('R31','2022-12-01','JW01',60,1760000),('R32','2022-12-01','JW02',57,2189000),('R33','2022-12-01','JW03',65,1855000),('R34','2022-12-01','JW04',60,3334000),('R35','2022-12-01','JW05',60,3334000),('R36','2022-12-01','JW06',65,2237000),('R37','2022-12-01','JW07',80,2046000),('R38','2022-12-01','JW08',80,671000),('R39','2022-12-01','JW09',120,390000),('R40','2022-12-01','JW10',110,493000),('R41','2022-12-01','JW11',90,777000),('R42','2022-12-01','JW12',70,1212000),('R43','2022-12-01','JW13',60,1116000),('R44','2022-12-01','JW14',130,687000),('R45','2022-12-01','JW15',120,711000),('R46','2022-12-01','S01',50,1280000),('R47','2022-12-01','S02',50,780000),('R48','2022-12-01','S03',60,1480000),('R49','2022-12-01','S04',180,780000),('R50','2022-12-01','S05',35,2280000),('R51','2022-12-01','S06',40,1130000),('R52','2022-12-01','S07',36,980000),('R53','2022-12-01','S08',40,1280000),('R54','2022-12-01','S09',90,780000),('R55','2022-12-01','S10',20,1480000),('R56','2022-12-01','S11',28,780000),('R57','2022-12-01','S12',25,1130000),('R58','2022-12-01','S13',43,1180000),('R59','2022-12-01','S14',55,980000),('R60','2022-12-01','S15',64,1980000),('R61','2022-12-01','J01',77,1080000),('R62','2022-12-01','J02',58,1980000),('R63','2022-12-01','J03',48,1130000),('R64','2022-12-01','J04',90,880000),('R65','2022-12-01','J05',95,1530000),('R66','2022-12-01','J06',95,1080000),('R67','2022-12-01','J07',100,470000),('R68','2022-12-01','J08',38,620000),('R69','2022-12-01','J09',90,1680000),('R70','2022-12-01','J10',30,1340000),('R71','2022-12-01','J11',25,740000),('R72','2022-12-01','J12',25,580000),('R73','2022-12-01','J13',126,520000),('R74','2022-12-01','J14',5,2080000),('R75','2022-12-01','J15',30,460000),('R76','2023-06-12','S04',5,100),('R77','2023-06-12','S04',5,100),('R78','2023-06-12','J01',1,1100000);
/*!40000 ALTER TABLE `product_receipt` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-19 12:27:16
