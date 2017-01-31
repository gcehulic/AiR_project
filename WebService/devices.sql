
-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 31, 2017 at 02:58 AM
-- Server version: 10.1.20-MariaDB
-- PHP Version: 5.2.17

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `u703327374_fcm`
--

-- --------------------------------------------------------

--
-- Table structure for table `devices`
--

CREATE TABLE IF NOT EXISTS `devices` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `token` text COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=32 ;

--
-- Dumping data for table `devices`
--

INSERT INTO `devices` (`id`, `email`, `token`) VALUES
(30, 'mark@net.hr', 'f7nv46XCbRU:APA91bH-eK72Ipoi3uAnTG2igFtTsGSAYLTKUwY_1w-zJfRKjJqH5hkeIkzoKsqoHyYVMl_DbHuCSo10YUoDiDGYp0tDcCciLrGZQ9656eoq6iQgB8iH0drOcgWJEWlgkn1d_4seAPT_'),
(28, 'dd@d.dd', 'ddEt7M0dmvo:APA91bGXytgqhhZMjYWMYtJLfFOegNiSscbteKsb5uORLMTOTkBye53RXmSvu1T8zVGsCSwzRqP4xGpGOdXnTDiI2PN12pTQ_lofhvvqFGw-L1-Oswmm85tLQ0hNzsCp1c0_p3B2rmcA'),
(29, 'goran.cehulic@gmail.com', 'egEi7bw8X3w:APA91bGToyXESOSZDTZTKxGec8l2Awe31NFptjY6lOaJUAskRG0_W1WwZRMyX0fVi5oKZvd-rz-QPpyDgOezNqeOnn3twyxld6rpDAgWCT5HKV7gkYukkA6LBUDUJK7oBOxSIHUwnIP1'),
(31, 'p@p.com', 'egEi7bw8X3w:APA91bGToyXESOSZDTZTKxGec8l2Awe31NFptjY6lOaJUAskRG0_W1WwZRMyX0fVi5oKZvd-rz-QPpyDgOezNqeOnn3twyxld6rpDAgWCT5HKV7gkYukkA6LBUDUJK7oBOxSIHUwnIP1'),
(26, 'g@g.hr', 'dr4IFhw1T40:APA91bE1HBUxWELKqkrxy9K9qTX-M_hBUlsL3yAcuSsPzZa8yWVzfWS9l48oEQyrVAKgLTALJ8cLio4wMmygcq6Wk6vUV8Slpq2tiAkDcZSj5vaa_0J-u64MZhaKuQUG3r0k4y4Z-I10'),
(24, 'd@gg.dd', 'f9LcWeHv6nk:APA91bH9uScfa1xUutBh7Xj_pvrgSWTDzMJrdGZsvFTOpca-gAs3O_rCDfjJP7m6Yq-swzgkIEQIymLtzMJPCSljCTo40uY1r-XiE2EHYd34VSdIKAvFobN5ZzKn7P7I7_myvDovmYND');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
