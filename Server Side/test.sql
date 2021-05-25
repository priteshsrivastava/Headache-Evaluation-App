-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 24, 2021 at 07:09 PM
-- Server version: 8.0.18
-- PHP Version: 7.3.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `test`
--

-- --------------------------------------------------------

--
-- Table structure for table `answer_info`
--

CREATE TABLE `answer_info` (
  `lvl` float NOT NULL,
  `ans_id` float NOT NULL,
  `headache_cat` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `branching_logic_info`
--

CREATE TABLE `branching_logic_info` (
  `lvl` float NOT NULL,
  `que_id` float NOT NULL,
  `ans_yes_id` float DEFAULT NULL,
  `ans_no_id` float DEFAULT NULL,
  `prev_que_id` float DEFAULT NULL,
  `que_str_en` text COLLATE utf8_unicode_ci,
  `que_str_hi` text COLLATE utf8_unicode_ci,
  `remarks` text COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `headache_cat_info`
--

CREATE TABLE `headache_cat_info` (
  `headache_cat` int(11) NOT NULL,
  `cat_str_en` text COLLATE utf8_unicode_ci,
  `cat_str_hi` text COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `multi_branch_logic_info`
--

CREATE TABLE `multi_branch_logic_info` (
  `lvl` float NOT NULL,
  `que_group_id` int(11) NOT NULL,
  `min_yes_response` int(11) NOT NULL,
  `next_que_group_id` int(11) DEFAULT NULL,
  `headache_cat` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `patient_detail_info`
--

CREATE TABLE `patient_detail_info` (
  `p_id` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `pwd` text COLLATE utf8_unicode_ci,
  `name` text COLLATE utf8_unicode_ci,
  `dob` date DEFAULT NULL,
  `gender` text COLLATE utf8_unicode_ci,
  `address` text COLLATE utf8_unicode_ci,
  `mobile_no` bigint(20) DEFAULT NULL,
  `adhaar_no` bigint(20) DEFAULT NULL,
  `email` text COLLATE utf8_unicode_ci,
  `health_history` text COLLATE utf8_unicode_ci,
  `image` text COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `patient_response_data`
--

CREATE TABLE `patient_response_data` (
  `t_id` int(11) NOT NULL,
  `p_id` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `lvl` float NOT NULL,
  `que_group_id` int(11) DEFAULT NULL,
  `que_id` float NOT NULL,
  `response_yes_no` int(11) DEFAULT NULL,
  `response_str` text COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `patient_test_basic_info`
--

CREATE TABLE `patient_test_basic_info` (
  `t_id` int(11) NOT NULL,
  `p_id` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `dr_id` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `test_st_date` datetime DEFAULT NULL,
  `test_end_date` datetime DEFAULT NULL,
  `is_test_completed` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `test_detail_data`
--

CREATE TABLE `test_detail_data` (
  `t_id` int(11) NOT NULL,
  `p_id` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `lvl` float NOT NULL,
  `lvl_status` text COLLATE utf8_unicode_ci,
  `que_ans_id` float DEFAULT NULL,
  `lvl_result_headache_cat` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `answer_info`
--
ALTER TABLE `answer_info`
  ADD PRIMARY KEY (`lvl`,`ans_id`);

--
-- Indexes for table `branching_logic_info`
--
ALTER TABLE `branching_logic_info`
  ADD PRIMARY KEY (`lvl`,`que_id`);

--
-- Indexes for table `headache_cat_info`
--
ALTER TABLE `headache_cat_info`
  ADD PRIMARY KEY (`headache_cat`);

--
-- Indexes for table `multi_branch_logic_info`
--
ALTER TABLE `multi_branch_logic_info`
  ADD PRIMARY KEY (`lvl`,`que_group_id`,`min_yes_response`);

--
-- Indexes for table `patient_detail_info`
--
ALTER TABLE `patient_detail_info`
  ADD PRIMARY KEY (`p_id`);

--
-- Indexes for table `patient_response_data`
--
ALTER TABLE `patient_response_data`
  ADD PRIMARY KEY (`t_id`,`p_id`,`lvl`,`que_id`);

--
-- Indexes for table `patient_test_basic_info`
--
ALTER TABLE `patient_test_basic_info`
  ADD PRIMARY KEY (`t_id`);

--
-- Indexes for table `test_detail_data`
--
ALTER TABLE `test_detail_data`
  ADD PRIMARY KEY (`t_id`,`p_id`,`lvl`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
