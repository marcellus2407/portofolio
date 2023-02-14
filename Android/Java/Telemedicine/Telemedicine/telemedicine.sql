-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 09, 2022 at 10:34 AM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `telemedicine`
--

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `user` int(11) NOT NULL,
  `obat` int(11) NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `dokter`
--

CREATE TABLE `dokter` (
  `id` int(11) NOT NULL,
  `specialist` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `dokter`
--

INSERT INTO `dokter` (`id`, `specialist`) VALUES
(5, 'Mata'),
(8, 'Jantung');

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `sender` int(11) NOT NULL,
  `receiver` int(11) NOT NULL,
  `message` longtext NOT NULL,
  `time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `message`
--

INSERT INTO `message` (`id`, `sender`, `receiver`, `message`, `time`) VALUES
(1, 1, 5, 'test', '2022-07-07 16:58:35'),
(2, 5, 1, 'test', '2022-07-07 17:17:11'),
(3, 1, 5, 'test', '2022-07-07 17:17:20'),
(4, 5, 1, 'test', '2022-07-07 17:17:23'),
(5, 1, 5, 'test', '2022-07-07 17:21:28'),
(6, 1, 5, 'test', '2022-07-07 17:21:31'),
(7, 1, 5, 'testlagi', '2022-07-07 17:21:37'),
(8, 1, 5, 'testlagi', '2022-07-07 17:21:40'),
(9, 1, 5, 'testlagi', '2022-07-07 17:21:42'),
(10, 1, 5, 'testlagi', '2022-07-07 17:21:57'),
(11, 1, 5, 'awo', '2022-07-07 17:22:06'),
(12, 1, 5, 'awo', '2022-07-07 17:22:12'),
(13, 1, 5, 'awooooo', '2022-07-07 17:23:19'),
(14, 1, 5, 'awoooo', '2022-07-07 17:26:07'),
(15, 1, 5, 'test', '2022-07-07 17:32:14'),
(16, 1, 5, 'jtgttgtgt', '2022-07-07 17:33:10'),
(17, 1, 5, 'yyy', '2022-07-07 17:44:53'),
(18, 1, 5, 'ttt', '2022-07-07 17:47:09'),
(19, 1, 5, 'Jsjsjs', '2022-07-07 17:51:23'),
(20, 1, 5, 'ejjejss', '2022-07-07 17:51:25'),
(21, 1, 5, 'jwjwjs', '2022-07-07 17:51:26'),
(22, 1, 5, 'uauajaua', '2022-07-07 17:51:28'),
(23, 5, 1, 'awo', '2022-07-07 18:14:18'),
(24, 5, 1, 'yeye', '2022-07-07 18:14:28'),
(25, 1, 5, 'skis', '2022-07-07 18:15:33'),
(26, 1, 5, 'saa', '2022-07-07 18:15:38'),
(27, 1, 5, 'dd', '2022-07-07 18:15:40'),
(28, 1, 5, 'fff', '2022-07-07 18:15:42'),
(29, 1, 5, 'dsw', '2022-07-07 18:15:44'),
(30, 1, 5, 'fff', '2022-07-07 18:15:46'),
(31, 1, 5, 'ggg', '2022-07-07 18:15:48'),
(32, 1, 5, 'f', '2022-07-07 18:15:48'),
(33, 1, 5, 'f', '2022-07-07 18:15:50'),
(34, 1, 5, 'f', '2022-07-07 18:15:51'),
(35, 1, 5, 'f', '2022-07-07 18:15:54'),
(36, 1, 5, 'yooo', '2022-07-07 18:16:00'),
(37, 1, 5, 'kiyomasa', '2022-07-07 18:16:04'),
(38, 1, 5, 'nande nande', '2022-07-07 18:16:08'),
(39, 5, 1, 'kk', '2022-07-07 18:16:15'),
(40, 1, 5, 'hj', '2022-07-07 18:17:59'),
(41, 1, 5, 'oi', '2022-07-07 18:19:50'),
(42, 1, 5, 'lagi apa', '2022-07-07 18:19:54'),
(43, 1, 5, 'gpp kok', '2022-07-07 18:19:59'),
(44, 1, 5, '😭', '2022-07-07 18:20:08'),
(45, 1, 5, 'j', '2022-07-07 18:26:11'),
(46, 1, 5, 'j', '2022-07-07 18:26:13'),
(47, 1, 5, 'k', '2022-07-07 18:26:15'),
(48, 1, 5, 'u', '2022-07-07 18:26:16'),
(49, 1, 5, 'usjsjddks', '2022-07-07 18:26:18'),
(50, 1, 5, 'jsisjss', '2022-07-07 18:26:19'),
(51, 1, 5, 'jsisj', '2022-07-07 18:26:21'),
(52, 1, 5, 'ieisiss', '2022-07-07 18:26:22'),
(53, 1, 5, 'kske', '2022-07-07 18:26:23'),
(54, 1, 5, 'skskse', '2022-07-07 18:26:24'),
(55, 1, 5, 'oeoeoe', '2022-07-07 18:26:25'),
(56, 1, 5, 'kkkssk', '2022-07-07 18:26:29'),
(57, 1, 5, 'mNJ', '2022-07-07 18:26:30'),
(58, 1, 5, 'mzksk', '2022-07-07 18:26:32'),
(59, 5, 1, 'berisik', '2022-07-07 18:26:35'),
(60, 1, 5, 'jsjss', '2022-07-07 18:26:39'),
(61, 5, 1, 'lo anak banyak gaya', '2022-07-07 18:26:43'),
(62, 1, 5, 'sksks', '2022-07-07 18:26:45'),
(63, 5, 1, 'dih', '2022-07-07 18:26:48'),
(64, 1, 5, 'jJaka', '2022-07-07 18:26:50'),
(65, 5, 1, 'test', '2022-07-07 18:27:06'),
(66, 5, 1, 'tets', '2022-07-07 18:27:07'),
(67, 5, 1, 'test', '2022-07-07 18:27:08'),
(68, 5, 1, 'test', '2022-07-07 18:27:10'),
(69, 5, 1, 'test', '2022-07-07 18:27:11'),
(70, 1, 5, 'shshsj', '2022-07-07 18:27:17'),
(71, 1, 5, 'jsjsjs', '2022-07-07 18:27:18'),
(72, 1, 5, 'jjj', '2022-07-07 18:27:19'),
(73, 1, 5, 'aiajakai', '2022-07-07 18:27:21'),
(74, 1, 5, 'titit', '2022-07-07 18:27:22'),
(75, 1, 5, 'titit marcell', '2022-07-07 18:27:26'),
(76, 1, 6, 'test', '2022-07-07 18:35:44'),
(77, 1, 6, 'twst tets', '2022-07-07 18:35:47');

-- --------------------------------------------------------

--
-- Table structure for table `obat`
--

CREATE TABLE `obat` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` int(11) NOT NULL,
  `description` longtext NOT NULL,
  `stock` int(11) NOT NULL,
  `photo` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `obat`
--

INSERT INTO `obat` (`id`, `name`, `price`, `description`, `stock`, `photo`) VALUES
(1, 'Stimuno Orange Berry Sirup 60ml (per Botol)', 30000, 'STIMUNO ORANGE BERRY SYR 60ML merupakan produk herbal fitofarmaka, yang terbukti berkhasiat dan aman untuk meningkatkan kekebalan tubuh, berguna untuk mencegah sakit dan mempercepat penyembuhan dan didistribusikan oleh Dexa Medica.\r\nBeli STIMUNO ORANGE BERRY SYR 60ML di K24Klik dan dapatkan manfaatnya.', 0, 'stimuno.jpg'),
(2, 'Laserin Sirup 110ml (per Botol)', 24500, 'Laserin merupakan obat herbal yang digunakan untuk meredakan batuk secara tradisional. Mengandung berbagai bahan herbal seperti jahe, cengkeh, daun sirih, dan ekstrak patikan yang pada dasarnya memiliki efek mempermudah pengeluaran dan mengencerkan lendir pada saluran napas serta memberikan sensasi manis dan hangat pada tenggorokan.', 8, 'laserin.jpg'),
(3, 'Termorex Sirup 60ml (per Botol)', 15500, 'Termorex sirup merupakan sirup turun panas yang mengandung parasetamol 160 mg tiap 5 mL. Termorex sirup dapat membantu menurunkan panas dan demam pada bayi dan anak-anak pada sakit gigi, sakit kepala, dan setelah mendapat imunisasi. Termorex sirup tidak mengandung alkohol dan dilengkapi dengan rasa jeruk yang manis sehingga disukai oleh anak-anak. Termorex sirup diproduksi oleh Konimex dan sudah terdaftar pada BPOM.', 23, 'termorex.jpg'),
(4, 'Fitkom Gummy Multivitamin 21g Sach (per Pcs)', 45000, 'Suplemen untuk membantu kebutuhan vitamin dan mineral pada anak di masa pertumbuhan. ', 50, 'fitkom.jpg'),
(5, 'Panadol Biru Tablet 1 Strip (per Blister)', 12500, 'PANADOL BIRU TABLET 1 STRIP memiliki kandungan bahan aktif Paracetamol. Paracetamol bekerja dengan cara mengurangi kadar prostaglandin di dalam tubuh, sehingga tanda peradangan seperti demam dan nyeri akan berkurang. Obat ini digunakan untuk meringankan rasa sakit seperti sakit kepala, sakit gigi serta menurunkan demam.', 35, 'panadol.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `user` int(11) NOT NULL,
  `address` longtext NOT NULL,
  `time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `user`, `address`, `time`) VALUES
(1, 1, 'Jl. Bla Bla Bla', '2022-07-09 00:19:25'),
(2, 1, 'Jl. Blo BLo BLo', '2022-07-09 00:22:36'),
(3, 1, 'Jl. tesr', '2022-07-09 01:18:20');

-- --------------------------------------------------------

--
-- Table structure for table `order_details`
--

CREATE TABLE `order_details` (
  `id` int(11) NOT NULL,
  `obat` int(11) NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `order_details`
--

INSERT INTO `order_details` (`id`, `obat`, `quantity`) VALUES
(1, 3, 2),
(2, 2, 3),
(2, 1, 3),
(3, 2, 4),
(3, 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `email` varchar(150) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `role` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `email`, `password`, `name`, `role`) VALUES
(1, 'test@test.com', '098f6bcd4621d373cade4e832627b4f6', 'test', 'Pasien'),
(5, 'dokter1@dokter.com', '5db479bc6453dea4e990cadafd5cede8', 'dokter satu', 'Dokter'),
(6, 'admin1@admin.com', '21232f297a57a5a743894a0e4a801fc3', 'admin satu', 'Admin'),
(7, 'admin2@admin.com', 'c84258e9c39059a89ab77d846ddab909', 'admin dua', 'Admin'),
(8, 'dokter2@dokter.com', '83ac5c3ef493ab7cdebd68dc1712ca89', 'Dokter dua', 'Dokter');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD KEY `obat` (`obat`),
  ADD KEY `user` (`user`);

--
-- Indexes for table `dokter`
--
ALTER TABLE `dokter`
  ADD KEY `id` (`id`);

--
-- Indexes for table `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sender` (`sender`),
  ADD KEY `receiver` (`receiver`);

--
-- Indexes for table `obat`
--
ALTER TABLE `obat`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user` (`user`);

--
-- Indexes for table `order_details`
--
ALTER TABLE `order_details`
  ADD KEY `obat` (`obat`),
  ADD KEY `id` (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `message`
--
ALTER TABLE `message`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=78;

--
-- AUTO_INCREMENT for table `obat`
--
ALTER TABLE `obat`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`user`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`obat`) REFERENCES `obat` (`id`);

--
-- Constraints for table `dokter`
--
ALTER TABLE `dokter`
  ADD CONSTRAINT `dokter_ibfk_1` FOREIGN KEY (`id`) REFERENCES `user` (`id`);

--
-- Constraints for table `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `message_ibfk_1` FOREIGN KEY (`sender`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `message_ibfk_2` FOREIGN KEY (`receiver`) REFERENCES `user` (`id`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user`) REFERENCES `user` (`id`);

--
-- Constraints for table `order_details`
--
ALTER TABLE `order_details`
  ADD CONSTRAINT `order_details_ibfk_1` FOREIGN KEY (`id`) REFERENCES `orders` (`id`),
  ADD CONSTRAINT `order_details_ibfk_2` FOREIGN KEY (`obat`) REFERENCES `obat` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
