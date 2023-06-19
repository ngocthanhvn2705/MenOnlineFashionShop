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
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `CUSTOMER_ID` varchar(5) NOT NULL,
  `CUSTOMER_USERNAME` varchar(45) NOT NULL,
  `CUSTOMER_NAME` varchar(45) NOT NULL,
  `CUSTOMER_BIRTH` date DEFAULT NULL,
  `CUSTOMER_GENDER` varchar(6) NOT NULL,
  `CUSTOMER_ADDRESS` varchar(250) DEFAULT NULL,
  `CUSTOMER_PHONE` varchar(11) NOT NULL,
  `CUSTOMER_EMAIL` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`CUSTOMER_ID`),
  KEY `FK_CUSTOMER_USERNAME` (`CUSTOMER_USERNAME`),
  CONSTRAINT `FK_CUSTOMER_USERNAME` FOREIGN KEY (`CUSTOMER_USERNAME`) REFERENCES `acc` (`ACC_USERNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('C01','khang2003','Tran Quoc Khang','2003-05-17','MALE','392 Tu Nghia, Quang Ngai','0979971376','trankhang@gmail.com'),('C02','nam2000vn','Nguyen Van Nam','2000-11-15','MALE','36 Quang Xuong, Thanh Hoa','0824161120','vannam@gmail.com'),('C03','manh2003','Ngo Van Manh','2003-10-30','MALE','76 Binh Son, Quang Ngai','0705288278','manhngu@gmail.com'),('C04','dinh2003','Nguyen Huu Dinh','2003-12-02','MALE','29 Binh Thanh, TP HCM','0868324028','dinhgay@gmail.com'),('C05','dat2003','Nguyen Thanh Dat','2003-09-09','MALE','19 Thu Duc, TP HCM','0983464963','datcute@gmail.com'),('C06','diep2003','Pham Tri Diep','1998-04-23','MALE','12/5 Tran Hung Dao, TP HCM','0647584734','tridiep@gmail.com'),('C07','nam2000','Ngo Giang Nam','2000-01-30','MALE','13/4 Hoc Lac, TP HCM','0384759374','giangnam@gmail.com'),('C08','hong2002','Bui Huy Hong','2002-08-26','MALE','100 Dinh Bo Linh, Ha Noi','0485738264','huyhong@gmail.com'),('C09','vinh2001','Do Phuc Vinh','2001-12-03','MALE','14 Truong Chinh, TP HCM','0243564885','phucvinh@gmail.com'),('C10','binh1999','Pham Huy Binh','1999-12-31','MALE','32/5 Tran Dai Nghia, TP HCM','0182736475','huybinh@gmail.com'),('C11','customer','Ngoc Thanh','2007-06-06','MALE','KP6 Linh Trung, Thu Duc, HCM','0383939262','21522600@gmail.com'),('C12','khang','Khang','2010-06-29','MALE','Quang Ngai','0369645852','khang@gmail.com');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-19 12:27:17
