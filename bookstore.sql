-- phpMyAdmin SQL Dump
-- version 4.4.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 08, 2017 at 08:37 PM
-- Server version: 5.6.26
-- PHP Version: 5.6.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bookstore`
--

-- --------------------------------------------------------

--
-- Table structure for table `author`
--

CREATE TABLE IF NOT EXISTS `author` (
  `ISBN` int(20) NOT NULL,
  `Auth_Name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `author`
--

INSERT INTO `author` (`ISBN`, `Auth_Name`) VALUES
(1234, 'fg'),
(1234, 'hamada');

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE IF NOT EXISTS `book` (
  `ISBN` int(20) NOT NULL,
  `Title` varchar(50) NOT NULL,
  `Category` enum('Science','Art','Religion','History','Geography') NOT NULL,
  `Price` float NOT NULL DEFAULT '0',
  `Pub_Year` year(4) DEFAULT NULL,
  `Pub_Name` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`ISBN`, `Title`, `Category`, `Price`, `Pub_Year`, `Pub_Name`) VALUES
(1234, 'Me and You', 'Science', 105.5, 0000, 'pub n'),
(78965, 'Social Media', 'Religion', 1.98, 2016, 'pub n');

-- --------------------------------------------------------

--
-- Table structure for table `book_stock`
--

CREATE TABLE IF NOT EXISTS `book_stock` (
  `ISBN` int(20) NOT NULL,
  `Threshold` int(20) NOT NULL,
  `Left_Num` int(20) NOT NULL DEFAULT '0',
  `Sold_Num` int(20) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Triggers `book_stock`
--
DELIMITER $$
CREATE TRIGGER `check_left_books` BEFORE UPDATE ON `book_stock`
 FOR EACH ROW BEGIN
IF NEW.Left_Num < 0 THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Remaining quantity can not be negative';
END IF; 
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `left_less_than_threshold` AFTER UPDATE ON `book_stock`
 FOR EACH ROW BEGIN
	IF NEW.Left_Num < OLD.Threshold THEN
		IF EXISTS (SELECT * FROM needs_orders WHERE ISBN = NEW.ISBN) THEN
			update needs_orders set Quantity =  NEW.Threshold - NEW.Left_Num where ISBN = NEW.ISBN;
		ELSE
			INSERT INTO needs_orders VALUES (NEW.ISBN, NEW.Threshold - NEW.Left_Num, 1);
		END IF;
	END IF; 
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE IF NOT EXISTS `customer` (
  `Username` varchar(100) NOT NULL,
  `FName` varchar(50) NOT NULL,
  `LName` varchar(50) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Password` varchar(100) NOT NULL,
  `Telephone` varchar(11) NOT NULL,
  `Ship_Address` varchar(250) NOT NULL,
  `Is_Manager` tinyint(1) NOT NULL DEFAULT '0',
  `Is_Logged_In` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`Username`, `FName`, `LName`, `Email`, `Password`, `Telephone`, `Ship_Address`, `Is_Manager`, `Is_Logged_In`) VALUES
('ahmed123', 'ahmed', 'el', 'kjklre', '1234', '28888', 'jhjhjh', 1, 1),
('hhh', 'rr', 'tt', 'fff', 'yyy', 'c', 'gg', 1, 1),
('kk', 'oo', 'pp', 'll', '[[[', ',,', 'jj', 1, 1),
('oooooooooooooooo', 'ajj', 'loowew', 'jj@kldk.efe', 'kllwdlwk', '01258', 'slffjke', 0, 1),
('pp', 'oo', 'ii', 'yy', 'uu', 'ff', 'hh', 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `needs_orders`
--

CREATE TABLE IF NOT EXISTS `needs_orders` (
  `ISBN` int(50) NOT NULL,
  `Quantity` int(11) NOT NULL,
  `Is_Need` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Triggers `needs_orders`
--
DELIMITER $$
CREATE TRIGGER `delete_order` BEFORE DELETE ON `needs_orders`
 FOR EACH ROW BEGIN
	UPDATE book_stock
	SET book_stock.Left_Num = Left_Num + OLD.Quantity
    WHERE OLD.ISBN = book_stock.ISBN AND OLD.Is_Need = 0;

END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `publisher`
--

CREATE TABLE IF NOT EXISTS `publisher` (
  `Pub_Name` varchar(50) NOT NULL,
  `Address` varchar(250) NOT NULL,
  `Telephone` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `publisher`
--

INSERT INTO `publisher` (`Pub_Name`, `Address`, `Telephone`) VALUES
('pub n', 'iiyyyyyyyyyyyyyyyyiiiiiiiiiii', '89889');

-- --------------------------------------------------------

--
-- Table structure for table `shopping_cart`
--

CREATE TABLE IF NOT EXISTS `shopping_cart` (
  `Cart_Id` int(11) NOT NULL,
  `ISBN` int(50) NOT NULL,
  `Username` varchar(100) NOT NULL,
  `Quantity` int(11) NOT NULL,
  `Price` int(11) NOT NULL,
  `Date` datetime DEFAULT NULL,
  `Credit_Card_No` int(16) DEFAULT NULL,
  `Expiry_Date` date DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `shopping_cart`
--

INSERT INTO `shopping_cart` (`Cart_Id`, `ISBN`, `Username`, `Quantity`, `Price`, `Date`, `Credit_Card_No`, `Expiry_Date`) VALUES
(1, 1234, 'ahmed123', 55, 2, '2017-05-11 00:00:00', 5668886, '2017-05-10'),
(2, 1234, 'hhh', 9, 10, '2017-04-01 00:00:00', 8899, '2017-05-06'),
(3, 78965, 'ahmed123', 98, 2, '2017-04-19 00:00:00', 6556, '2017-05-20'),
(4, 78965, 'ahmed123', 50, 20, '0000-00-00 00:00:00', NULL, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `author`
--
ALTER TABLE `author`
  ADD PRIMARY KEY (`ISBN`,`Auth_Name`);

--
-- Indexes for table `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`ISBN`),
  ADD KEY `Pub_Name` (`Pub_Name`);

--
-- Indexes for table `book_stock`
--
ALTER TABLE `book_stock`
  ADD PRIMARY KEY (`ISBN`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`Username`);

--
-- Indexes for table `needs_orders`
--
ALTER TABLE `needs_orders`
  ADD PRIMARY KEY (`ISBN`);

--
-- Indexes for table `publisher`
--
ALTER TABLE `publisher`
  ADD PRIMARY KEY (`Pub_Name`);

--
-- Indexes for table `shopping_cart`
--
ALTER TABLE `shopping_cart`
  ADD PRIMARY KEY (`Cart_Id`,`ISBN`,`Username`),
  ADD KEY `fk_customer_sales` (`Username`),
  ADD KEY `fk_book_sales` (`ISBN`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `shopping_cart`
--
ALTER TABLE `shopping_cart`
  MODIFY `Cart_Id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `author`
--
ALTER TABLE `author`
  ADD CONSTRAINT `fk_book_author` FOREIGN KEY (`ISBN`) REFERENCES `book` (`ISBN`) ON UPDATE CASCADE ON DELETE CASCADE;

--
-- Constraints for table `book`
--
ALTER TABLE `book`
  ADD CONSTRAINT `Pub_Name` FOREIGN KEY (`Pub_Name`) REFERENCES `publisher` (`Pub_Name`) ON UPDATE CASCADE ON DELETE CASCADE;

--
-- Constraints for table `book_stock`
--
ALTER TABLE `book_stock`
  ADD CONSTRAINT `fk_book_stock` FOREIGN KEY (`ISBN`) REFERENCES `book` (`ISBN`) ON UPDATE CASCADE ON DELETE CASCADE;

--
-- Constraints for table `needs_orders`
--
ALTER TABLE `needs_orders`
  ADD CONSTRAINT `fk_book_needs` FOREIGN KEY (`ISBN`) REFERENCES `book` (`ISBN`) ON UPDATE CASCADE ON DELETE CASCADE;

--
-- Constraints for table `shopping_cart`
--
ALTER TABLE `shopping_cart`
  ADD CONSTRAINT `fk_book_sales` FOREIGN KEY (`ISBN`) REFERENCES `book` (`ISBN`),
  ADD CONSTRAINT `fk_customer_sales` FOREIGN KEY (`Username`) REFERENCES `customer` (`Username`) ON UPDATE CASCADE ON DELETE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
