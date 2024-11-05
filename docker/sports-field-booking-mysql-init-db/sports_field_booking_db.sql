-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: sports-field-booking-mysql:3306
-- Thời gian đã tạo: Th10 25, 2024 lúc 03:57 PM
-- Phiên bản máy phục vụ: 9.0.1
-- Phiên bản PHP: 8.2.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `sports_field_booking_db`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `bookings`
--

CREATE TABLE `bookings` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `version` bigint DEFAULT NULL,
  `status` enum('ACCEPTED','CANCELED','PENDING','REFUND_REQUESTED','REJECTED','RESCHEDULED') NOT NULL,
  `field_availability_id` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `bookings`
--

INSERT INTO `bookings` (`id`, `created_at`, `created_by`, `deleted_at`, `deleted_by`, `is_deleted`, `updated_at`, `updated_by`, `version`, `status`, `field_availability_id`, `user_id`) VALUES
('0b96e171-fd58-4e4b-96e6-eabc7a946b8f', '2024-10-25 21:47:56.017000', '2a3e56c9-1f94-4c92-8e9a-6e521837fec0', NULL, NULL, b'0', '2024-10-25 21:47:56.017000', NULL, 0, 'ACCEPTED', '56b858d3-5d81-43b7-b80b-ae433dcfc7c5', '2a3e56c9-1f94-4c92-8e9a-6e521837fec0'),
('185337bc-a645-4572-b2ca-7933099fd284', '2024-10-25 21:47:55.958000', 'b1b7af8b-9ad3-4e25-85b7-9a3704cce607', NULL, NULL, b'0', '2024-10-25 21:47:55.958000', NULL, 0, 'PENDING', '13450933-959c-4f08-aac1-0a4ddd21e82e', 'b1b7af8b-9ad3-4e25-85b7-9a3704cce607'),
('21e6e7ff-d6a5-4b22-82ea-217ba42b4f69', '2024-10-25 21:47:56.001000', '6c20a29b-2faf-406b-9a8c-bf050f1be819', NULL, NULL, b'0', '2024-10-25 21:47:56.001000', NULL, 0, 'REFUND_REQUESTED', '54fef968-09d7-4ef9-a50c-086321d75776', '6c20a29b-2faf-406b-9a8c-bf050f1be819'),
('2bcac4bd-9f23-4394-b820-4fce66acddb8', '2024-10-25 21:47:56.073000', '5f46f576-8aa1-4594-8f8a-7e4df74190c8', NULL, NULL, b'0', '2024-10-25 21:47:56.073000', NULL, 0, 'ACCEPTED', 'a1feb35e-67e7-4314-afdc-bec78ccee4ff', '5f46f576-8aa1-4594-8f8a-7e4df74190c8'),
('5e93a871-fb65-4cac-8063-8f34d4530b93', '2024-10-25 21:47:56.025000', 'ac771ed5-dbee-4848-b604-d5b51b2b6517', NULL, NULL, b'0', '2024-10-25 21:47:56.025000', NULL, 0, 'ACCEPTED', '661b7f2e-f87b-4cca-ab17-fa2cd9b02a17', 'ac771ed5-dbee-4848-b604-d5b51b2b6517'),
('68cfd8a5-fe49-443a-a0f2-531360a0ed59', '2024-10-25 21:47:56.081000', 'c73542de-4c9f-476c-add0-72e3c8cf7754', NULL, NULL, b'0', '2024-10-25 21:47:56.081000', NULL, 0, 'ACCEPTED', 'ae31e916-aa43-40e7-898c-449a477d5363', 'c73542de-4c9f-476c-add0-72e3c8cf7754'),
('71988c47-5a90-4e5d-9c1e-90cfd182f638', '2024-10-25 21:47:55.969000', 'ac771ed5-dbee-4848-b604-d5b51b2b6517', NULL, NULL, b'0', '2024-10-25 21:47:55.969000', NULL, 0, 'ACCEPTED', '1cea8b38-b2a1-4f61-8557-239658c37f2d', 'ac771ed5-dbee-4848-b604-d5b51b2b6517'),
('781d8722-b708-4994-9861-9e82c9b60047', '2024-10-25 21:47:55.985000', '525bd993-756f-4f5a-94ab-2fa761226946', NULL, NULL, b'0', '2024-10-25 21:47:55.985000', NULL, 0, 'PENDING', '333f93d6-715c-402c-9252-1d13583fdc0c', '525bd993-756f-4f5a-94ab-2fa761226946'),
('7d3e3117-6f8d-4c57-9a43-6d80a97b97b0', '2024-10-25 21:47:56.049000', '525bd993-756f-4f5a-94ab-2fa761226946', NULL, NULL, b'0', '2024-10-25 21:47:56.049000', NULL, 0, 'REJECTED', '81a411bc-3156-4ff6-a805-db0971e8052e', '525bd993-756f-4f5a-94ab-2fa761226946'),
('7dddfc1c-517d-467d-9026-0db4a96a3cde', '2024-10-25 21:47:55.952000', '5f46f576-8aa1-4594-8f8a-7e4df74190c8', NULL, NULL, b'0', '2024-10-25 21:47:55.952000', NULL, 0, 'RESCHEDULED', '0d3df7ef-8f56-40b8-b4ac-e5d04e180dc0', '5f46f576-8aa1-4594-8f8a-7e4df74190c8'),
('82b86a1d-283f-4368-931f-e27d1e698d7d', '2024-10-25 21:47:56.058000', 'db1c5740-4fe4-4e50-9bff-7fc41afe65a8', NULL, NULL, b'0', '2024-10-25 21:47:56.058000', NULL, 0, 'CANCELED', '88dc341a-2af8-4fa7-9b70-68e0e962eed9', 'db1c5740-4fe4-4e50-9bff-7fc41afe65a8'),
('974cfd3a-ae69-4c27-bbf8-87d4455f555b', '2024-10-25 21:47:56.034000', '33e0fa26-5765-47fa-94d3-312ecaf21ebb', NULL, NULL, b'0', '2024-10-25 21:47:56.034000', NULL, 0, 'PENDING', '67c1cff3-a194-468a-b14f-4a85d14edb63', '33e0fa26-5765-47fa-94d3-312ecaf21ebb'),
('9a7c8567-6b7f-4c01-9dda-1858d38058a1', '2024-10-25 21:47:55.976000', '6b6ad2ee-83c9-431c-a768-81ce2aec7756', NULL, NULL, b'0', '2024-10-25 21:47:55.976000', NULL, 0, 'ACCEPTED', '20cfa303-6f90-41c2-bb23-6b3669cba9a3', '6b6ad2ee-83c9-431c-a768-81ce2aec7756'),
('b1b7b08f-0847-4b49-93d6-8721c95325da', '2024-10-25 21:47:56.009000', 'ed0359c4-1960-41b0-b8b9-5d359abdb342', NULL, NULL, b'0', '2024-10-25 21:47:56.009000', NULL, 0, 'RESCHEDULED', '563f1c3e-c6ea-4792-bea3-b1098947171d', 'ed0359c4-1960-41b0-b8b9-5d359abdb342'),
('b4376e8d-8cfe-461c-b7d6-865a60943566', '2024-10-25 21:47:56.088000', '1bed8435-e46f-4e30-8bde-cc3ad7b05a62', NULL, NULL, b'0', '2024-10-25 21:47:56.088000', NULL, 0, 'PENDING', 'ae5a97e2-8d7f-4ca7-ab45-737bd284d62b', '1bed8435-e46f-4e30-8bde-cc3ad7b05a62'),
('b8ccf81e-5cf9-4e3e-98a5-f795193843ac', '2024-10-25 21:47:55.945000', '194e724a-8438-4fe9-bfdb-49f888e62811', NULL, NULL, b'0', '2024-10-25 21:47:55.945000', NULL, 0, 'REJECTED', '05d93127-5a90-49db-9821-e6d9822fa4d3', '194e724a-8438-4fe9-bfdb-49f888e62811'),
('cdd8a014-f036-4da1-ba8f-4ef459496f2d', '2024-10-25 21:47:55.964000', '89e7dc5e-2b07-4a96-9384-9c58170019bd', NULL, NULL, b'0', '2024-10-25 21:47:55.964000', NULL, 0, 'CANCELED', '1aa22fa8-4ad6-4ec9-bce6-583a28d0a08b', '89e7dc5e-2b07-4a96-9384-9c58170019bd'),
('cf511958-1b23-4a7f-8893-face628c65d5', '2024-10-25 21:47:56.065000', 'ac771ed5-dbee-4848-b604-d5b51b2b6517', NULL, NULL, b'0', '2024-10-25 21:47:56.065000', NULL, 0, 'ACCEPTED', '94a8811e-e880-4f4b-9ce7-cb336666bd7c', 'ac771ed5-dbee-4848-b604-d5b51b2b6517'),
('ea632e70-9473-4088-ad41-d9301cc61126', '2024-10-25 21:47:55.992000', 'e7daba0e-1da9-42a7-a015-80bd50ba1bae', NULL, NULL, b'0', '2024-10-25 21:47:55.992000', NULL, 0, 'REJECTED', '481eb06d-9598-4691-a073-89b69169c515', 'e7daba0e-1da9-42a7-a015-80bd50ba1bae'),
('fad25bd3-0bec-4a8a-9f2c-5753db75d1c7', '2024-10-25 21:47:56.041000', '89e7dc5e-2b07-4a96-9384-9c58170019bd', NULL, NULL, b'0', '2024-10-25 21:47:56.041000', NULL, 0, 'REJECTED', '6bca2dd3-83c1-46d7-9ba1-d83a1d41519f', '89e7dc5e-2b07-4a96-9384-9c58170019bd');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `booking_items`
--

CREATE TABLE `booking_items` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `version` bigint DEFAULT NULL,
  `available_date` date NOT NULL,
  `end_time` datetime(6) NOT NULL,
  `price` double NOT NULL,
  `start_time` datetime(6) NOT NULL,
  `booking_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `categories`
--

CREATE TABLE `categories` (
  `id` int NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `version` bigint DEFAULT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `categories`
--

INSERT INTO `categories` (`id`, `created_at`, `created_by`, `deleted_at`, `deleted_by`, `is_deleted`, `updated_at`, `updated_by`, `version`, `name`) VALUES
(1, '2024-10-25 21:47:54.063000', '6b6ad2ee-83c9-431c-a768-81ce2aec7756', NULL, NULL, b'0', '2024-10-25 21:47:54.063000', NULL, 0, 'oil wrestling'),
(2, '2024-10-25 21:47:54.194000', '1bed8435-e46f-4e30-8bde-cc3ad7b05a62', NULL, NULL, b'0', '2024-10-25 21:47:54.194000', NULL, 0, 'rugby'),
(3, '2024-10-25 21:47:54.323000', 'b1b7af8b-9ad3-4e25-85b7-9a3704cce607', NULL, NULL, b'0', '2024-10-25 21:47:54.323000', NULL, 0, 'rugby'),
(4, '2024-10-25 21:47:54.457000', 'ac4df3c2-98c6-4ac5-8603-9a5ada8129a5', NULL, NULL, b'0', '2024-10-25 21:47:54.457000', NULL, 0, 'kabaddi'),
(5, '2024-10-25 21:47:54.550000', 'f0e59b19-3cad-44ef-b2e2-3dd5aa3656ea', NULL, NULL, b'0', '2024-10-25 21:47:54.550000', NULL, 0, 'basketball');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `field_availabilities`
--

CREATE TABLE `field_availabilities` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `version` bigint DEFAULT NULL,
  `available_date` date NOT NULL,
  `end_time` datetime(6) NOT NULL,
  `is_available` bit(1) NOT NULL,
  `price` double NOT NULL,
  `start_time` datetime(6) NOT NULL,
  `sports_field_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `field_availabilities`
--

INSERT INTO `field_availabilities` (`id`, `created_at`, `created_by`, `deleted_at`, `deleted_by`, `is_deleted`, `updated_at`, `updated_by`, `version`, `available_date`, `end_time`, `is_available`, `price`, `start_time`, `sports_field_id`) VALUES
('05d93127-5a90-49db-9821-e6d9822fa4d3', '2024-10-25 21:47:55.672000', '33e0fa26-5765-47fa-94d3-312ecaf21ebb', NULL, NULL, b'0', '2024-10-25 21:47:55.672000', NULL, 0, '2024-11-14', '2024-11-14 21:00:00.000000', b'1', 56.61, '2024-11-14 15:00:00.000000', '869b03b5-8ad9-45fd-9949-afb33b1a7cb8'),
('0d3df7ef-8f56-40b8-b4ac-e5d04e180dc0', '2024-10-25 21:47:55.512000', '1bed8435-e46f-4e30-8bde-cc3ad7b05a62', NULL, NULL, b'0', '2024-10-25 21:47:55.512000', NULL, 0, '2024-11-14', '2024-11-14 18:00:00.000000', b'1', 36.38, '2024-11-14 11:00:00.000000', '5e04084a-1778-4131-8f7a-7597b5c8bc77'),
('13450933-959c-4f08-aac1-0a4ddd21e82e', '2024-10-25 21:47:55.573000', 'ed0359c4-1960-41b0-b8b9-5d359abdb342', NULL, NULL, b'0', '2024-10-25 21:47:55.573000', NULL, 0, '2024-10-26', '2024-10-26 15:00:00.000000', b'1', 67.54, '2024-10-26 08:00:00.000000', '20ba0ccf-e0e4-4b6a-b4ca-c99a2d4833c7'),
('1aa22fa8-4ad6-4ec9-bce6-583a28d0a08b', '2024-10-25 21:47:55.696000', '1bed8435-e46f-4e30-8bde-cc3ad7b05a62', NULL, NULL, b'0', '2024-10-25 21:47:55.696000', NULL, 0, '2024-11-15', '2024-11-15 18:00:00.000000', b'0', 12.56, '2024-11-15 14:00:00.000000', 'd91aa10c-9a11-4fc6-87df-edebda96cf63'),
('1cea8b38-b2a1-4f61-8557-239658c37f2d', '2024-10-25 21:47:55.652000', 'ed0359c4-1960-41b0-b8b9-5d359abdb342', NULL, NULL, b'0', '2024-10-25 21:47:55.652000', NULL, 0, '2024-10-26', '2024-10-26 18:00:00.000000', b'0', 84.96, '2024-10-26 16:00:00.000000', '20ba0ccf-e0e4-4b6a-b4ca-c99a2d4833c7'),
('20cfa303-6f90-41c2-bb23-6b3669cba9a3', '2024-10-25 21:47:55.736000', '5f46f576-8aa1-4594-8f8a-7e4df74190c8', NULL, NULL, b'0', '2024-10-25 21:47:55.736000', NULL, 0, '2024-11-01', '2024-11-01 17:00:00.000000', b'1', 79.36, '2024-11-01 09:00:00.000000', 'a1ba8ad9-e448-4d16-81a1-b28d25663495'),
('333f93d6-715c-402c-9252-1d13583fdc0c', '2024-10-25 21:47:55.705000', '33e0fa26-5765-47fa-94d3-312ecaf21ebb', NULL, NULL, b'0', '2024-10-25 21:47:55.705000', NULL, 0, '2024-11-12', '2024-11-12 21:00:00.000000', b'1', 67.89, '2024-11-12 14:00:00.000000', '869b03b5-8ad9-45fd-9949-afb33b1a7cb8'),
('481eb06d-9598-4691-a073-89b69169c515', '2024-10-25 21:47:55.714000', '194e724a-8438-4fe9-bfdb-49f888e62811', NULL, NULL, b'0', '2024-10-25 21:47:55.714000', NULL, 0, '2024-11-03', '2024-11-04 00:00:00.000000', b'1', 86.81, '2024-11-03 17:00:00.000000', '6827740c-5bfe-4fbe-8574-3eeff12470f8'),
('54fef968-09d7-4ef9-a50c-086321d75776', '2024-10-25 21:47:55.537000', '194e724a-8438-4fe9-bfdb-49f888e62811', NULL, NULL, b'0', '2024-10-25 21:47:55.537000', NULL, 0, '2024-11-03', '2024-11-03 21:00:00.000000', b'1', 87.51, '2024-11-03 17:00:00.000000', 'af367cef-5f88-4fd9-b6d6-756516ead131'),
('563f1c3e-c6ea-4792-bea3-b1098947171d', '2024-10-25 21:47:55.519000', '33e0fa26-5765-47fa-94d3-312ecaf21ebb', NULL, NULL, b'0', '2024-10-25 21:47:55.519000', NULL, 0, '2024-11-11', '2024-11-11 19:00:00.000000', b'1', 83.85, '2024-11-11 17:00:00.000000', '869b03b5-8ad9-45fd-9949-afb33b1a7cb8'),
('56b858d3-5d81-43b7-b80b-ae433dcfc7c5', '2024-10-25 21:47:55.663000', '3ccc685a-7e85-463e-9f65-84d527849e26', NULL, NULL, b'0', '2024-10-25 21:47:55.663000', NULL, 0, '2024-10-27', '2024-10-27 17:00:00.000000', b'1', 54.85, '2024-10-27 09:00:00.000000', '3a8759a4-ffe4-48c2-a147-80d70d1cad89'),
('661b7f2e-f87b-4cca-ab17-fa2cd9b02a17', '2024-10-25 21:47:55.728000', '3ccc685a-7e85-463e-9f65-84d527849e26', NULL, NULL, b'0', '2024-10-25 21:47:55.728000', NULL, 0, '2024-11-03', '2024-11-03 15:00:00.000000', b'0', 12.51, '2024-11-03 13:00:00.000000', '3a8759a4-ffe4-48c2-a147-80d70d1cad89'),
('67c1cff3-a194-468a-b14f-4a85d14edb63', '2024-10-25 21:47:55.743000', '3ccc685a-7e85-463e-9f65-84d527849e26', NULL, NULL, b'0', '2024-10-25 21:47:55.743000', NULL, 0, '2024-11-11', '2024-11-11 15:00:00.000000', b'1', 81.15, '2024-11-11 09:00:00.000000', '669e5d4d-151d-4b6a-9dae-faed754f05e5'),
('6bca2dd3-83c1-46d7-9ba1-d83a1d41519f', '2024-10-25 21:47:55.589000', '3ccc685a-7e85-463e-9f65-84d527849e26', NULL, NULL, b'0', '2024-10-25 21:47:55.589000', NULL, 0, '2024-10-31', '2024-10-31 23:00:00.000000', b'0', 63.96, '2024-10-31 18:00:00.000000', 'b88751f2-c9f5-46e1-811e-5e0a38905240'),
('81a411bc-3156-4ff6-a805-db0971e8052e', '2024-10-25 21:47:55.631000', '3ccc685a-7e85-463e-9f65-84d527849e26', NULL, NULL, b'0', '2024-10-25 21:47:55.631000', NULL, 0, '2024-11-05', '2024-11-05 15:00:00.000000', b'0', 81.74, '2024-11-05 13:00:00.000000', 'e0b7b8f2-829c-4d15-856d-dd4a78043b7e'),
('88dc341a-2af8-4fa7-9b70-68e0e962eed9', '2024-10-25 21:47:55.543000', '6c20a29b-2faf-406b-9a8c-bf050f1be819', NULL, NULL, b'0', '2024-10-25 21:47:55.543000', NULL, 0, '2024-11-11', '2024-11-12 03:00:00.000000', b'1', 54.98, '2024-11-11 17:00:00.000000', 'c4283fc0-e055-4d5a-9d50-16eb759145dc'),
('94a8811e-e880-4f4b-9ce7-cb336666bd7c', '2024-10-25 21:47:55.722000', 'db1c5740-4fe4-4e50-9bff-7fc41afe65a8', NULL, NULL, b'0', '2024-10-25 21:47:55.722000', NULL, 0, '2024-11-10', '2024-11-11 03:00:00.000000', b'0', 56.11, '2024-11-10 18:00:00.000000', 'a774c2c1-41d9-4cbe-95b9-dfd6cd5c35e2'),
('a1feb35e-67e7-4314-afdc-bec78ccee4ff', '2024-10-25 21:47:55.688000', '5f46f576-8aa1-4594-8f8a-7e4df74190c8', NULL, NULL, b'0', '2024-10-25 21:47:55.688000', NULL, 0, '2024-11-12', '2024-11-12 21:00:00.000000', b'0', 60.2, '2024-11-12 11:00:00.000000', 'cfe98404-9f6c-49aa-b88f-203a915ad68f'),
('ae31e916-aa43-40e7-898c-449a477d5363', '2024-10-25 21:47:55.524000', 'ac771ed5-dbee-4848-b604-d5b51b2b6517', NULL, NULL, b'0', '2024-10-25 21:47:55.524000', NULL, 0, '2024-10-30', '2024-10-30 15:00:00.000000', b'1', 91.1, '2024-10-30 13:00:00.000000', '33b10ae9-1eb3-44d7-ae1b-877ab1be4d50'),
('ae5a97e2-8d7f-4ca7-ab45-737bd284d62b', '2024-10-25 21:47:55.615000', '194e724a-8438-4fe9-bfdb-49f888e62811', NULL, NULL, b'0', '2024-10-25 21:47:55.615000', NULL, 0, '2024-11-14', '2024-11-14 20:00:00.000000', b'1', 87.35, '2024-11-14 14:00:00.000000', '6827740c-5bfe-4fbe-8574-3eeff12470f8'),
('ae83e8aa-a30e-4e2e-9b7b-636ea6e4da21', '2024-10-25 21:47:55.556000', 'ed0359c4-1960-41b0-b8b9-5d359abdb342', NULL, NULL, b'0', '2024-10-25 21:47:55.556000', NULL, 0, '2024-11-09', '2024-11-09 20:00:00.000000', b'0', 32.26, '2024-11-09 12:00:00.000000', '20ba0ccf-e0e4-4b6a-b4ca-c99a2d4833c7'),
('b161abf8-2b18-4c95-ac16-45f37eb4f9ed', '2024-10-25 21:47:55.623000', '1bed8435-e46f-4e30-8bde-cc3ad7b05a62', NULL, NULL, b'0', '2024-10-25 21:47:55.623000', NULL, 0, '2024-11-13', '2024-11-13 16:00:00.000000', b'1', 86.5, '2024-11-13 15:00:00.000000', '541518e1-0367-494b-9582-a818ede75307'),
('b72e9038-32ac-4ad1-921f-e30445c75413', '2024-10-25 21:47:55.606000', '5f46f576-8aa1-4594-8f8a-7e4df74190c8', NULL, NULL, b'0', '2024-10-25 21:47:55.606000', NULL, 0, '2024-11-14', '2024-11-14 18:00:00.000000', b'1', 75.41, '2024-11-14 15:00:00.000000', 'a1ba8ad9-e448-4d16-81a1-b28d25663495'),
('b92df590-13b1-4502-8264-ffa3374350a8', '2024-10-25 21:47:55.680000', '1bed8435-e46f-4e30-8bde-cc3ad7b05a62', NULL, NULL, b'0', '2024-10-25 21:47:55.680000', NULL, 0, '2024-10-31', '2024-10-31 16:00:00.000000', b'1', 10.98, '2024-10-31 10:00:00.000000', '541518e1-0367-494b-9582-a818ede75307'),
('c172ac5c-a67c-4443-9693-86d8c28249d1', '2024-10-25 21:47:55.564000', '6c20a29b-2faf-406b-9a8c-bf050f1be819', NULL, NULL, b'0', '2024-10-25 21:47:55.564000', NULL, 0, '2024-11-22', '2024-11-22 20:00:00.000000', b'0', 23.23, '2024-11-22 18:00:00.000000', 'c4283fc0-e055-4d5a-9d50-16eb759145dc'),
('ce7d017b-53d8-4d03-b958-bbe1242bf4e3', '2024-10-25 21:47:55.549000', 'db1c5740-4fe4-4e50-9bff-7fc41afe65a8', NULL, NULL, b'0', '2024-10-25 21:47:55.549000', NULL, 0, '2024-11-14', '2024-11-14 14:00:00.000000', b'0', 60.78, '2024-11-14 12:00:00.000000', 'a774c2c1-41d9-4cbe-95b9-dfd6cd5c35e2'),
('d0006613-cf9a-4dce-b809-817f209bf05f', '2024-10-25 21:47:55.598000', '61626200-36a4-40d3-b598-9071f9e7a87f', NULL, NULL, b'0', '2024-10-25 21:47:55.598000', NULL, 0, '2024-11-21', '2024-11-22 00:00:00.000000', b'1', 44.17, '2024-11-21 18:00:00.000000', '837db666-9cdf-4074-aeb5-22fc0bc1a326'),
('f8f6b3eb-5d72-4ae2-943b-7feff0024b56', '2024-10-25 21:47:55.581000', 'db1c5740-4fe4-4e50-9bff-7fc41afe65a8', NULL, NULL, b'0', '2024-10-25 21:47:55.581000', NULL, 0, '2024-10-31', '2024-10-31 21:00:00.000000', b'0', 44.25, '2024-10-31 17:00:00.000000', 'a774c2c1-41d9-4cbe-95b9-dfd6cd5c35e2'),
('fa1ccf17-b99a-4857-9457-31d9b6d7ddf9', '2024-10-25 21:47:55.640000', '3ccc685a-7e85-463e-9f65-84d527849e26', NULL, NULL, b'0', '2024-10-25 21:47:55.640000', NULL, 0, '2024-11-11', '2024-11-11 16:00:00.000000', b'0', 68.14, '2024-11-11 11:00:00.000000', '669e5d4d-151d-4b6a-9dae-faed754f05e5'),
('fa2754cf-e5d2-412e-b671-cee41fa4905d', '2024-10-25 21:47:55.531000', '1bed8435-e46f-4e30-8bde-cc3ad7b05a62', NULL, NULL, b'0', '2024-10-25 21:47:55.531000', NULL, 0, '2024-11-20', '2024-11-20 18:00:00.000000', b'1', 21.57, '2024-11-20 08:00:00.000000', '541518e1-0367-494b-9582-a818ede75307');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `file_metadata`
--

CREATE TABLE `file_metadata` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `version` bigint DEFAULT NULL,
  `content_type` varchar(255) NOT NULL,
  `object_key` varchar(255) NOT NULL,
  `size` bigint NOT NULL,
  `url` text NOT NULL,
  `sports_field_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `file_metadata`
--

INSERT INTO `file_metadata` (`id`, `created_at`, `created_by`, `deleted_at`, `deleted_by`, `is_deleted`, `updated_at`, `updated_by`, `version`, `content_type`, `object_key`, `size`, `url`, `sports_field_id`, `user_id`) VALUES
('0590854c-8360-40f3-83cc-f1c835d24b1a', '2024-10-25 21:50:08.318000', '525bd993-756f-4f5a-94ab-2fa761226946', NULL, NULL, b'0', '2024-10-25 22:20:39.795000', 'anonymous', 3, 'image/jpeg', 'image_20241025_215008_8e155c20-c48f-41c1-86d8-6f05f49f2d4f.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_215008_8e155c20-c48f-41c1-86d8-6f05f49f2d4f.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T145008Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=68e1a0a3b79394e4a80f8f526cc30b78c3e6753cea02c3a0ab72fbf973d620e6', '14e9017d-ff11-47e1-972c-0e2d5a1cc417', NULL),
('05d57d89-d88f-4e09-95c3-8474139b462f', '2024-10-25 21:49:55.454000', '525bd993-756f-4f5a-94ab-2fa761226946', NULL, NULL, b'0', '2024-10-25 22:20:39.821000', 'anonymous', 3, 'image/jpeg', 'image_20241025_214955_10dc7ea4-54ef-439b-94fe-fd07af996e9a.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214955_10dc7ea4-54ef-439b-94fe-fd07af996e9a.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144955Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=3f3c7b3acc24511cd8fb53b13fde53c55205fa3605bedc5dcd26214fbdecfb84', '14e9017d-ff11-47e1-972c-0e2d5a1cc417', NULL),
('19abcebc-5a99-4481-936b-54a3903b7e1b', '2024-10-25 21:49:38.949000', 'ed0359c4-1960-41b0-b8b9-5d359abdb342', NULL, NULL, b'0', '2024-10-25 22:20:39.835000', 'anonymous', 4, 'image/jpeg', 'image_20241025_214938_9c744fe1-bcac-41fa-ab5e-e504c3855f6c.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214938_9c744fe1-bcac-41fa-ab5e-e504c3855f6c.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144938Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=8a84164f6ca6409ebeb989438ec4922d67cb1a7775566db0f5c264793a9800b5', '20ba0ccf-e0e4-4b6a-b4ca-c99a2d4833c7', NULL),
('1c9de88e-8629-4c2d-91b9-311a9267e5cc', '2024-10-25 21:49:38.998000', 'ed0359c4-1960-41b0-b8b9-5d359abdb342', NULL, NULL, b'0', '2024-10-25 22:20:39.848000', 'anonymous', 3, 'image/jpeg', 'image_20241025_214938_aa033807-8a5e-4f8a-b331-1fc5de2d6698.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214938_aa033807-8a5e-4f8a-b331-1fc5de2d6698.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144938Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=b3e4db8b2deaa546dea8ed5d9f143a2989cfe425ae646ac9ffaf3a977d05201a', '20ba0ccf-e0e4-4b6a-b4ca-c99a2d4833c7', NULL),
('36cb7a5c-a384-4c6a-b4d7-8976c01e7fc5', '2024-10-25 21:50:00.035000', 'ac771ed5-dbee-4848-b604-d5b51b2b6517', NULL, NULL, b'0', '2024-10-25 22:20:39.861000', 'anonymous', 3, 'image/jpeg', 'image_20241025_214959_d9aa97cc-6677-46e7-b536-23a7ac50a267.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214959_d9aa97cc-6677-46e7-b536-23a7ac50a267.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T145000Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=b28966fbf22be36ff6ea49a9648c731e5c809a1b9b1a03b5c5638012b5c7576a', '33b10ae9-1eb3-44d7-ae1b-877ab1be4d50', NULL),
('3cb58a1c-c11f-4054-a2cb-2f89015aec92', '2024-10-25 21:49:39.038000', 'ac771ed5-dbee-4848-b604-d5b51b2b6517', NULL, NULL, b'0', '2024-10-25 22:20:39.871000', 'anonymous', 2, 'image/jpeg', 'image_20241025_214938_f155eea6-e6d3-404d-800f-621096caabd4.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214938_f155eea6-e6d3-404d-800f-621096caabd4.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144939Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=62505ebc967ca3f5b29f3092e033f82ee886af45c2d972ffb4789d699148b483', '33b10ae9-1eb3-44d7-ae1b-877ab1be4d50', NULL),
('416bd34b-8bc0-412a-ab24-48d7a8b17e43', '2024-10-25 21:50:00.023000', '3ccc685a-7e85-463e-9f65-84d527849e26', NULL, NULL, b'0', '2024-10-25 22:20:39.880000', 'anonymous', 3, 'image/jpeg', 'image_20241025_214959_b7d58998-49a4-44c3-b2c3-c66cb7f12959.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214959_b7d58998-49a4-44c3-b2c3-c66cb7f12959.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144959Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=4c3e311eec211040a93ecda16ceaa0e1a6447b31a16c568324f23617df03f317', '3a8759a4-ffe4-48c2-a147-80d70d1cad89', NULL),
('4a0fd2f8-64f4-4068-a77a-bc347cda790d', '2024-10-25 21:49:55.477000', '3ccc685a-7e85-463e-9f65-84d527849e26', NULL, NULL, b'0', '2024-10-25 22:20:39.889000', 'anonymous', 2, 'image/jpeg', 'image_20241025_214955_55ae859e-a823-449e-9fa3-3677085d541c.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214955_55ae859e-a823-449e-9fa3-3677085d541c.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144955Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=9412c726316a624668ece212fa9ce1a1020878bb4068fe4fc234b5bad6b87214', '3a8759a4-ffe4-48c2-a147-80d70d1cad89', NULL),
('5333e474-d18b-4f3f-9079-3c07c3db7f1d', '2024-10-25 21:49:38.971000', '1bed8435-e46f-4e30-8bde-cc3ad7b05a62', NULL, NULL, b'0', '2024-10-25 22:20:39.899000', 'anonymous', 3, 'image/jpeg', 'image_20241025_214938_d0f4ee4e-b9b5-43c4-964f-c6f30c91341d.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214938_d0f4ee4e-b9b5-43c4-964f-c6f30c91341d.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144938Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=df65004b3a286a9caf8e83308114fd3b2e08a9c90b48222419275c492af4b941', '541518e1-0367-494b-9582-a818ede75307', NULL),
('560ef98c-be80-4511-94ca-9a182eb4ff58', '2024-10-25 21:50:08.351000', '1bed8435-e46f-4e30-8bde-cc3ad7b05a62', NULL, NULL, b'0', '2024-10-25 22:20:39.909000', 'anonymous', 2, 'image/jpeg', 'image_20241025_215008_00d60e11-4900-4ff9-bbf2-2d900345ac8f.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_215008_00d60e11-4900-4ff9-bbf2-2d900345ac8f.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T145008Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=744d472e2abe4e98a2b8548106e333d11b8c1a3ed5cd089ed098b4d401c46086', '541518e1-0367-494b-9582-a818ede75307', NULL),
('5c4354eb-e327-4098-ab36-f51c225f23b9', '2024-10-25 21:50:08.331000', '1bed8435-e46f-4e30-8bde-cc3ad7b05a62', NULL, NULL, b'0', '2024-10-25 22:20:39.918000', 'anonymous', 3, 'image/jpeg', 'image_20241025_215008_ffa150ad-75e6-4245-8b86-43d145e2883c.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_215008_ffa150ad-75e6-4245-8b86-43d145e2883c.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T145008Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=81f5cf65bbd3b6934ac1e659a4f558340c6b7965337c3a7b3e3e2f3a2d6a0589', '5e04084a-1778-4131-8f7a-7597b5c8bc77', NULL),
('5d283c32-2426-4209-8e99-7f8fdce1a64c', '2024-10-25 21:49:50.395000', '1bed8435-e46f-4e30-8bde-cc3ad7b05a62', NULL, NULL, b'0', '2024-10-25 22:20:39.927000', 'anonymous', 2, 'image/jpeg', 'image_20241025_214950_7f595506-7b32-4ad4-be80-fb240525c992.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214950_7f595506-7b32-4ad4-be80-fb240525c992.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144950Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=ea03d2e285b6f87e7dbb7b39e3d30a01cc9178869e1e2cbf516e0c927876ca51', '5e04084a-1778-4131-8f7a-7597b5c8bc77', NULL),
('61381578-e228-4fc8-8aeb-73569086e207', '2024-10-25 21:49:55.419000', '3ccc685a-7e85-463e-9f65-84d527849e26', NULL, NULL, b'0', '2024-10-25 22:20:39.936000', 'anonymous', 3, 'image/jpeg', 'image_20241025_214955_6655acf5-7694-472d-8293-ee5a23c5ab22.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214955_6655acf5-7694-472d-8293-ee5a23c5ab22.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144955Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=013cc5b840d90c11a00205457a8088b9e8b403835895911fb908ab26687aa64e', '669e5d4d-151d-4b6a-9dae-faed754f05e5', NULL),
('637da18d-ba9e-46d7-9e35-bbe3e0e69188', '2024-10-25 21:49:50.380000', '3ccc685a-7e85-463e-9f65-84d527849e26', NULL, NULL, b'0', '2024-10-25 22:20:39.945000', 'anonymous', 2, 'image/jpeg', 'image_20241025_214950_a31b45ce-db8c-4192-92cf-e7dfb64592d2.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214950_a31b45ce-db8c-4192-92cf-e7dfb64592d2.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144950Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=16a1f6777255b4f04a6d9e3aebbe1ce8341910b6b509167a8d2968d10e54075e', '669e5d4d-151d-4b6a-9dae-faed754f05e5', NULL),
('74960988-f855-469e-87a4-02697bcf009e', '2024-10-25 21:49:50.333000', '194e724a-8438-4fe9-bfdb-49f888e62811', NULL, NULL, b'0', '2024-10-25 22:20:39.955000', 'anonymous', 3, 'image/jpeg', 'image_20241025_214950_bb02460e-022e-40ea-8830-8323f0b833b0.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214950_bb02460e-022e-40ea-8830-8323f0b833b0.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144950Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=28fe937b0840c1aadc3e2dde44b356d9d7278bd2c2789c19c793c98d966f49b0', '6827740c-5bfe-4fbe-8574-3eeff12470f8', NULL),
('778d842f-af7a-4e29-b061-1595a909464c', '2024-10-25 21:49:59.945000', '194e724a-8438-4fe9-bfdb-49f888e62811', NULL, NULL, b'0', '2024-10-25 22:20:39.964000', 'anonymous', 2, 'image/jpeg', 'image_20241025_214959_9fd7f797-fe03-4bd5-8918-ad2f1f156d74.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214959_9fd7f797-fe03-4bd5-8918-ad2f1f156d74.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144959Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=b9b8195775662029dfbf81c7ef426901d18e080accf76385b821947b57ab2f88', '6827740c-5bfe-4fbe-8574-3eeff12470f8', NULL),
('7f15ec93-57a6-4365-8b30-78f9ec1b7a74', '2024-10-25 21:49:50.405000', '61626200-36a4-40d3-b598-9071f9e7a87f', NULL, NULL, b'0', '2024-10-25 22:20:39.972000', 'anonymous', 3, 'image/jpeg', 'image_20241025_214950_92efc767-619f-45de-9e6d-8b8f5b14d2c1.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214950_92efc767-619f-45de-9e6d-8b8f5b14d2c1.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144950Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=61d5979a82bc6eb015950a74f2ba8c99bb73a08518244e9eb0979ea3d75647cf', '837db666-9cdf-4074-aeb5-22fc0bc1a326', NULL),
('859c3051-9dc6-4ca9-9d93-b0f7cac6d923', '2024-10-25 21:49:59.976000', '61626200-36a4-40d3-b598-9071f9e7a87f', NULL, NULL, b'0', '2024-10-25 22:20:39.983000', 'anonymous', 2, 'image/jpeg', 'image_20241025_214959_3c9f90ad-17de-4ae4-9382-44df2986ea58.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214959_3c9f90ad-17de-4ae4-9382-44df2986ea58.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144959Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=1b739ffae229b32082fe07b9fc0361805911afd31214cdc559691bc6980db00b', '837db666-9cdf-4074-aeb5-22fc0bc1a326', NULL),
('86c48995-dba6-4c28-b7da-ccb302709cb6', '2024-10-25 21:50:00.002000', '33e0fa26-5765-47fa-94d3-312ecaf21ebb', NULL, NULL, b'0', '2024-10-25 22:20:39.992000', 'anonymous', 3, 'image/jpeg', 'image_20241025_214959_3f4ddda6-ccbf-4c28-b461-5d27369c27c7.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214959_3f4ddda6-ccbf-4c28-b461-5d27369c27c7.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144959Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=e278b04aef296e342bc543a9e926f27ec59293435fe0e6490ad45c6da53beba8', '869b03b5-8ad9-45fd-9949-afb33b1a7cb8', NULL),
('892f2d6c-2d26-4ea8-bc89-502b952d09c0', '2024-10-25 21:49:55.485000', '33e0fa26-5765-47fa-94d3-312ecaf21ebb', NULL, NULL, b'0', '2024-10-25 22:20:40.001000', 'anonymous', 2, 'image/jpeg', 'image_20241025_214955_0a8924f6-7823-411e-a52c-55d40c5d1a41.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214955_0a8924f6-7823-411e-a52c-55d40c5d1a41.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144955Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=d615099efb440de97f9b5d979ee314c27692d1a1717931d804df25d89d7ec57a', '869b03b5-8ad9-45fd-9949-afb33b1a7cb8', NULL),
('8a86758e-aff2-467d-972d-1143567162fb', '2024-10-25 21:49:50.419000', '5f46f576-8aa1-4594-8f8a-7e4df74190c8', NULL, NULL, b'0', '2024-10-25 22:20:40.012000', 'anonymous', 3, 'image/jpeg', 'image_20241025_214950_d2240941-c60b-476b-85af-fd1ebcccbe4a.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214950_d2240941-c60b-476b-85af-fd1ebcccbe4a.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144950Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=71fa5d4bc1360cbbec2721c2b6b00672cceceff00d181292aa21884247f4af36', 'a1ba8ad9-e448-4d16-81a1-b28d25663495', NULL),
('92e9a573-f50d-4bab-8f07-9d7f821783c4', '2024-10-25 21:50:08.340000', '5f46f576-8aa1-4594-8f8a-7e4df74190c8', NULL, NULL, b'0', '2024-10-25 22:20:40.023000', 'anonymous', 2, 'image/jpeg', 'image_20241025_215008_8a819b05-c27f-413f-a01b-c891759b2656.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_215008_8a819b05-c27f-413f-a01b-c891759b2656.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T145008Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=7ad54951f1453799746f3454bfb736df64a918d00daf15def866c944dec0648e', 'a1ba8ad9-e448-4d16-81a1-b28d25663495', NULL),
('97a9f71b-488d-4862-864c-487ef497ae23', '2024-10-25 21:49:50.449000', 'db1c5740-4fe4-4e50-9bff-7fc41afe65a8', NULL, NULL, b'0', '2024-10-25 22:20:40.035000', 'anonymous', 3, 'image/jpeg', 'image_20241025_214950_937cc3e3-a8e7-45ff-96a4-291bb3b2cadd.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214950_937cc3e3-a8e7-45ff-96a4-291bb3b2cadd.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144950Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=2fd40d49c39c87b813d56f857b2f543e675c31c69715b1ff9fc5a978b99d30c9', 'a774c2c1-41d9-4cbe-95b9-dfd6cd5c35e2', NULL),
('a4d610d7-4bf6-4962-a57d-38a15d895b58', '2024-10-25 21:49:50.436000', 'db1c5740-4fe4-4e50-9bff-7fc41afe65a8', NULL, NULL, b'0', '2024-10-25 22:20:40.047000', 'anonymous', 2, 'image/jpeg', 'image_20241025_214950_337488f2-0e15-432f-8d67-41f39fa230d5.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214950_337488f2-0e15-432f-8d67-41f39fa230d5.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144950Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=458b3544f1729f7a7a8aca4370196fba9ec7d81afe75ebabcf70921c13c7723e', 'a774c2c1-41d9-4cbe-95b9-dfd6cd5c35e2', NULL),
('a575f513-31de-4a0a-944b-5bc6145ba56d', '2024-10-25 21:49:55.429000', '194e724a-8438-4fe9-bfdb-49f888e62811', NULL, NULL, b'0', '2024-10-25 22:20:40.059000', 'anonymous', 3, 'image/jpeg', 'image_20241025_214955_388da84f-3867-4736-a2e0-a4129793fabd.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214955_388da84f-3867-4736-a2e0-a4129793fabd.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144955Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=2ea69fdda55592be70902c313825d7eb4503c17f95abcc6ca04a267ddf17f905', 'af367cef-5f88-4fd9-b6d6-756516ead131', NULL),
('b046b711-ffbe-4c8e-a9a0-11a4a202595d', '2024-10-25 21:49:59.988000', '194e724a-8438-4fe9-bfdb-49f888e62811', NULL, NULL, b'0', '2024-10-25 22:20:40.072000', 'anonymous', 2, 'image/jpeg', 'image_20241025_214959_c42a6b9f-3df2-499e-9b7d-b825ba8c6ed1.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214959_c42a6b9f-3df2-499e-9b7d-b825ba8c6ed1.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144959Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=9c21da73190e66d6dcb3b64e1f252dacd5444d8aca1ef031f673a999d658caf1', 'af367cef-5f88-4fd9-b6d6-756516ead131', NULL),
('b5a0b717-d241-405e-9d24-be83e64a0eb9', '2024-10-25 21:49:59.924000', '3ccc685a-7e85-463e-9f65-84d527849e26', NULL, NULL, b'0', '2024-10-25 22:20:40.083000', 'anonymous', 3, 'image/jpeg', 'image_20241025_214959_2c7eedac-a15e-4f96-a421-7587034c94e8.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214959_2c7eedac-a15e-4f96-a421-7587034c94e8.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144959Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=3bcbb906164ee46959bb27d7ff653dd63872217bb8929109a2ae5ef25fccbcc5', 'b88751f2-c9f5-46e1-811e-5e0a38905240', NULL),
('b873668a-4ae9-4cae-92a2-331b72f41e02', '2024-10-25 21:49:50.429000', '3ccc685a-7e85-463e-9f65-84d527849e26', NULL, NULL, b'0', '2024-10-25 22:20:40.096000', 'anonymous', 2, 'image/jpeg', 'image_20241025_214950_6dc3219e-66ea-4f2d-9be8-4d2b06126981.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214950_6dc3219e-66ea-4f2d-9be8-4d2b06126981.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144950Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=0ec03e0b425287f2b28ab04146702633bd32e7b8090e0a020fa374a0669a9a58', 'b88751f2-c9f5-46e1-811e-5e0a38905240', NULL),
('ba6413a0-2233-41c0-bbff-af2e5f1d3e57', '2024-10-25 21:49:38.961000', '6c20a29b-2faf-406b-9a8c-bf050f1be819', NULL, NULL, b'0', '2024-10-25 22:20:40.108000', 'anonymous', 3, 'image/jpeg', 'image_20241025_214938_b76b5b6c-1c17-4028-b911-a11cd37327df.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214938_b76b5b6c-1c17-4028-b911-a11cd37327df.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144938Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=f90bfc4a922a7ced859f99d16188a0738717c8534365b349f4264ec67c072f22', 'c4283fc0-e055-4d5a-9d50-16eb759145dc', NULL),
('c1609413-4f38-4edb-8a75-fe775c72608b', '2024-10-25 21:49:55.391000', '6c20a29b-2faf-406b-9a8c-bf050f1be819', NULL, NULL, b'0', '2024-10-25 22:20:40.119000', 'anonymous', 2, 'image/jpeg', 'image_20241025_214955_16d681ec-1746-4dca-8373-435175f1f3ae.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214955_16d681ec-1746-4dca-8373-435175f1f3ae.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144955Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=b89f371b59f4f1ce033e489dadc8aef9647d9fa4f962a826b4d4927da56569ed', 'c4283fc0-e055-4d5a-9d50-16eb759145dc', NULL),
('c296cf0d-2f76-4275-9d12-031d364c5f36', '2024-10-25 21:50:00.012000', '1bed8435-e46f-4e30-8bde-cc3ad7b05a62', NULL, NULL, b'0', '2024-10-25 22:20:40.129000', 'anonymous', 3, 'image/jpeg', 'image_20241025_214959_342700b0-c18d-4907-8c16-268bb59edcf3.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214959_342700b0-c18d-4907-8c16-268bb59edcf3.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144959Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=9f91fc5b489c83c221c21e4aeaf2b30777f0d4283bfe5a2e9b24f162868e8876', 'cfb872b6-77a3-480e-8b2f-812139fa0188', NULL),
('d2e8f309-bee4-476e-b759-a7bb5ed7c965', '2024-10-25 21:49:39.031000', '1bed8435-e46f-4e30-8bde-cc3ad7b05a62', NULL, NULL, b'0', '2024-10-25 22:20:40.140000', 'anonymous', 2, 'image/jpeg', 'image_20241025_214938_b15a8649-6dcd-450b-81ff-3d1a2044343c.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214938_b15a8649-6dcd-450b-81ff-3d1a2044343c.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144938Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=d288889eccbe6acfbd5d01739f0777c43f81c58dc5a29ea2ed36c9b2078a2ebb', 'cfb872b6-77a3-480e-8b2f-812139fa0188', NULL),
('e37f6cb8-d173-40d2-a7cf-02a23bd2da54', '2024-10-25 21:49:39.020000', '5f46f576-8aa1-4594-8f8a-7e4df74190c8', NULL, NULL, b'0', '2024-10-25 22:20:40.148000', 'anonymous', 3, 'image/jpeg', 'image_20241025_214938_f5d6741f-da03-4c14-8af7-83c6b2d3562f.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214938_f5d6741f-da03-4c14-8af7-83c6b2d3562f.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144938Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=993fb40f3f3c7b62177df69c8eeed89798d60dc39a79bd40a0c5cca7a5b853d8', 'cfe98404-9f6c-49aa-b88f-203a915ad68f', NULL),
('e7ce1c60-97a0-43f5-959f-f522d6dde62b', '2024-10-25 21:49:50.363000', '5f46f576-8aa1-4594-8f8a-7e4df74190c8', NULL, NULL, b'0', '2024-10-25 22:20:40.156000', 'anonymous', 2, 'image/jpeg', 'image_20241025_214950_9e4cd4ec-fa2e-4e9a-85c2-e3b64d5e6c6b.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214950_9e4cd4ec-fa2e-4e9a-85c2-e3b64d5e6c6b.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144950Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=907ef7bcd8b7004ab42c28b540429304f6c68460b4b88ed95cb44e4f52b6002f', 'cfe98404-9f6c-49aa-b88f-203a915ad68f', NULL),
('e9c52907-245b-45ed-bae8-71fde822a19d', '2024-10-25 21:49:59.960000', '1bed8435-e46f-4e30-8bde-cc3ad7b05a62', NULL, NULL, b'0', '2024-10-25 22:20:40.165000', 'anonymous', 3, 'image/jpeg', 'image_20241025_214959_c563be3b-2564-4e91-a7c6-b63d3be485e3.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214959_c563be3b-2564-4e91-a7c6-b63d3be485e3.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144959Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=636492bad3c1ef38324e987129891b7f853621e4483a4956a4fa3cd412beb2a2', 'd91aa10c-9a11-4fc6-87df-edebda96cf63', NULL),
('ed2e6cbf-e9b7-4ed8-9ac2-26bfa8616745', '2024-10-25 21:49:38.980000', '1bed8435-e46f-4e30-8bde-cc3ad7b05a62', NULL, NULL, b'0', '2024-10-25 22:20:40.173000', 'anonymous', 2, 'image/jpeg', 'image_20241025_214938_1d7b343b-ecd3-40f8-beff-e4d94f73e216.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214938_1d7b343b-ecd3-40f8-beff-e4d94f73e216.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144938Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=977e985abd95736f2c0bf027047369bbe88481079e4f61a093e5547a07dbf37c', 'd91aa10c-9a11-4fc6-87df-edebda96cf63', NULL),
('ee881b24-ec4a-4c55-bcfd-2b0d02d6298b', '2024-10-25 21:49:55.468000', '3ccc685a-7e85-463e-9f65-84d527849e26', NULL, NULL, b'0', '2024-10-25 22:20:40.183000', 'anonymous', 3, 'image/jpeg', 'image_20241025_214955_92ecde89-94d6-4a1d-bbb9-6af46b7f0149.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214955_92ecde89-94d6-4a1d-bbb9-6af46b7f0149.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144955Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=61ffe441fdd6aca00c5eb8687350a2438b1cf28d1863cece5d2a04c8933fd89d', 'e0b7b8f2-829c-4d15-856d-dd4a78043b7e', NULL),
('f2b0483c-6c52-4043-ad6c-7ff69f8a7e14', '2024-10-25 21:49:55.500000', '3ccc685a-7e85-463e-9f65-84d527849e26', NULL, NULL, b'0', '2024-10-25 22:20:40.192000', 'anonymous', 2, 'image/jpeg', 'image_20241025_214955_607a0aba-d134-4ff3-9929-8d0469ddbd40.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214955_607a0aba-d134-4ff3-9929-8d0469ddbd40.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144955Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=9ec38b5613c44efd2c83bf62cec72a6ef79222cf5fbea6be3dec7a4180efe902', 'e0b7b8f2-829c-4d15-856d-dd4a78043b7e', NULL),
('fc7e9de0-905e-48e1-a5ea-3016d40c5e8e', '2024-10-25 21:49:39.006000', 'ac4df3c2-98c6-4ac5-8603-9a5ada8129a5', NULL, NULL, b'0', '2024-10-25 22:20:40.200000', 'anonymous', 3, 'image/jpeg', 'image_20241025_214938_83cfa832-1edd-44c0-9cf5-ddcf908a291b.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214938_83cfa832-1edd-44c0-9cf5-ddcf908a291b.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144938Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=2a49d12a933b64dda68126609191a6fa8070ed4ecd2a5677211718e1c745fc52', 'f684fe6b-7748-47a0-b6f1-14e35eb42f33', NULL),
('fcd39f4a-e150-480b-8ab6-a3ab4ea775a5', '2024-10-25 21:49:55.439000', 'ac4df3c2-98c6-4ac5-8603-9a5ada8129a5', NULL, NULL, b'0', '2024-10-25 22:20:40.208000', 'anonymous', 2, 'image/jpeg', 'image_20241025_214955_fd0bc187-b3e8-446c-9296-1ead1cb95c25.jpeg', 0, 'http://localhost:9000/sports-field-booking-bucket/image_20241025_214955_fd0bc187-b3e8-446c-9296-1ead1cb95c25.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=group23%2F20241025%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241025T144955Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=d1079c4c64827690a16ba6fc4090b1660b4c0538230fe4cdff20a90d94240b0d', 'f684fe6b-7748-47a0-b6f1-14e35eb42f33', NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `notifications`
--

CREATE TABLE `notifications` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `version` bigint DEFAULT NULL,
  `message` varchar(255) NOT NULL,
  `type` enum('BOOKING_REMINDER','ORDER_STATUS_UPDATE','PAYMENT_REMINDER','PAYMENT_STATUS_UPDATE','PROMOTION','YARD_STATUS_UPDATE') NOT NULL,
  `booking_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `notifications`
--

INSERT INTO `notifications` (`id`, `created_at`, `created_by`, `deleted_at`, `deleted_by`, `is_deleted`, `updated_at`, `updated_by`, `version`, `message`, `type`, `booking_id`, `user_id`) VALUES
('0572c6e4-b54d-4f89-a646-5596e75a8491', '2024-10-25 21:47:56.715000', '5f46f576-8aa1-4594-8f8a-7e4df74190c8', NULL, NULL, b'0', '2024-10-25 21:47:56.715000', NULL, 0, 'Sed omnis iste illum et aut nobis perspiciatis in fugiat iste voluptatum.', 'YARD_STATUS_UPDATE', '9a7c8567-6b7f-4c01-9dda-1858d38058a1', '5f46f576-8aa1-4594-8f8a-7e4df74190c8'),
('0cc3395d-f19e-4560-8609-50b16a038b1d', '2024-10-25 21:47:56.679000', '6c20a29b-2faf-406b-9a8c-bf050f1be819', NULL, NULL, b'0', '2024-10-25 21:47:56.679000', NULL, 0, 'Quas pariatur optio ipsa eum aut dolores aliquid dolorem non adipisci id cum consequuntur fugit.', 'ORDER_STATUS_UPDATE', 'b1b7b08f-0847-4b49-93d6-8721c95325da', '6c20a29b-2faf-406b-9a8c-bf050f1be819'),
('180f7ee3-c0d0-4674-bcf7-5d3206127e13', '2024-10-25 21:47:56.695000', '6b6ad2ee-83c9-431c-a768-81ce2aec7756', NULL, NULL, b'0', '2024-10-25 21:47:56.695000', NULL, 0, 'Enim voluptatibus autem eligendi et laboriosam repellat ipsum sed est sint non quis.', 'PAYMENT_REMINDER', '71988c47-5a90-4e5d-9c1e-90cfd182f638', '6b6ad2ee-83c9-431c-a768-81ce2aec7756'),
('2d9e03cf-d49b-4d3c-b337-1c8164861c4c', '2024-10-25 21:47:56.631000', 'ac771ed5-dbee-4848-b604-d5b51b2b6517', NULL, NULL, b'0', '2024-10-25 21:47:56.631000', NULL, 0, 'Voluptate corrupti consequuntur repudiandae id qui accusantium blanditiis autem et sit et id.', 'YARD_STATUS_UPDATE', '2bcac4bd-9f23-4394-b820-4fce66acddb8', 'ac771ed5-dbee-4848-b604-d5b51b2b6517'),
('3b888ce5-6eff-45e5-b55c-d53b32696759', '2024-10-25 21:47:56.663000', 'ed0359c4-1960-41b0-b8b9-5d359abdb342', NULL, NULL, b'0', '2024-10-25 21:47:56.663000', NULL, 0, 'Quia esse et doloremque dicta dolorem sunt ea voluptas alias ut et ipsum quidem aut.', 'PROMOTION', 'cdd8a014-f036-4da1-ba8f-4ef459496f2d', 'ed0359c4-1960-41b0-b8b9-5d359abdb342'),
('4eca7f30-eaa9-4633-80ef-78d2e1e70cfe', '2024-10-25 21:47:56.639000', '3ccc685a-7e85-463e-9f65-84d527849e26', NULL, NULL, b'0', '2024-10-25 21:47:56.639000', NULL, 0, 'Aperiam recusandae autem vel ut fuga ut architecto omnis ut.', 'BOOKING_REMINDER', 'cdd8a014-f036-4da1-ba8f-4ef459496f2d', '3ccc685a-7e85-463e-9f65-84d527849e26'),
('63bce55d-3921-4287-9da4-2fe76d2aed8f', '2024-10-25 21:47:56.701000', 'ed0359c4-1960-41b0-b8b9-5d359abdb342', NULL, NULL, b'0', '2024-10-25 21:47:56.701000', NULL, 0, 'Omnis commodi ullam totam repudiandae dignissimos distinctio ut fugiat dolores aut.', 'ORDER_STATUS_UPDATE', 'b4376e8d-8cfe-461c-b7d6-865a60943566', 'ed0359c4-1960-41b0-b8b9-5d359abdb342'),
('70a3b8bc-d3b1-4de6-b76a-62a3a19bf1d4', '2024-10-25 21:47:56.720000', 'ac771ed5-dbee-4848-b604-d5b51b2b6517', NULL, NULL, b'0', '2024-10-25 21:47:56.720000', NULL, 0, 'Ea et eligendi nemo totam numquam maiores expedita voluptatibus dolorem neque beatae.', 'ORDER_STATUS_UPDATE', '7dddfc1c-517d-467d-9026-0db4a96a3cde', 'ac771ed5-dbee-4848-b604-d5b51b2b6517'),
('70aa174c-a03e-4218-baf8-c7203856341a', '2024-10-25 21:47:56.623000', 'ac4df3c2-98c6-4ac5-8603-9a5ada8129a5', NULL, NULL, b'0', '2024-10-25 21:47:56.623000', NULL, 0, 'Itaque debitis fugit repellat veritatis corporis et voluptatibus sed quia occaecati qui aut aut.', 'ORDER_STATUS_UPDATE', '9a7c8567-6b7f-4c01-9dda-1858d38058a1', 'ac4df3c2-98c6-4ac5-8603-9a5ada8129a5'),
('72df12b2-b510-4889-944c-810a0252b922', '2024-10-25 21:47:56.616000', '1bed8435-e46f-4e30-8bde-cc3ad7b05a62', NULL, NULL, b'0', '2024-10-25 21:47:56.616000', NULL, 0, 'Soluta nulla corporis distinctio amet non officia voluptatem similique mollitia molestiae.', 'YARD_STATUS_UPDATE', '7dddfc1c-517d-467d-9026-0db4a96a3cde', '1bed8435-e46f-4e30-8bde-cc3ad7b05a62'),
('a9de0e9b-e5f9-41a2-9c7e-c651886b4d12', '2024-10-25 21:47:56.708000', 'ac4df3c2-98c6-4ac5-8603-9a5ada8129a5', NULL, NULL, b'0', '2024-10-25 21:47:56.708000', NULL, 0, 'Reprehenderit aut sit laboriosam autem qui modi ut dignissimos sed aut incidunt hic.', 'YARD_STATUS_UPDATE', '21e6e7ff-d6a5-4b22-82ea-217ba42b4f69', 'ac4df3c2-98c6-4ac5-8603-9a5ada8129a5'),
('b277c527-64a2-4de6-bc96-8ab6e7381eb0', '2024-10-25 21:47:56.749000', '6c20a29b-2faf-406b-9a8c-bf050f1be819', NULL, NULL, b'0', '2024-10-25 21:47:56.749000', NULL, 0, 'Est nulla quia ipsam perferendis nobis non quidem autem ex libero qui et corporis.', 'PROMOTION', '5e93a871-fb65-4cac-8063-8f34d4530b93', '6c20a29b-2faf-406b-9a8c-bf050f1be819'),
('bb6bee33-a587-442a-bcaf-78d9736ac638', '2024-10-25 21:47:56.739000', '33e0fa26-5765-47fa-94d3-312ecaf21ebb', NULL, NULL, b'0', '2024-10-25 21:47:56.739000', NULL, 0, 'Aut dolor ipsam recusandae inventore eos quo eius eum pariatur vel est id accusantium.', 'YARD_STATUS_UPDATE', '185337bc-a645-4572-b2ca-7933099fd284', '33e0fa26-5765-47fa-94d3-312ecaf21ebb'),
('c029528c-c17c-42bd-b5df-28f2097fdf69', '2024-10-25 21:47:56.671000', '6b6ad2ee-83c9-431c-a768-81ce2aec7756', NULL, NULL, b'0', '2024-10-25 21:47:56.671000', NULL, 0, 'Minima modi voluptatem velit ut est sed voluptatibus veritatis aut voluptatum quae odio.', 'ORDER_STATUS_UPDATE', 'cdd8a014-f036-4da1-ba8f-4ef459496f2d', '6b6ad2ee-83c9-431c-a768-81ce2aec7756'),
('cdf90d53-8a9f-4802-8a2e-00d14338c071', '2024-10-25 21:47:56.688000', '1bed8435-e46f-4e30-8bde-cc3ad7b05a62', NULL, NULL, b'0', '2024-10-25 21:47:56.688000', NULL, 0, 'Accusantium non vitae voluptas quia et qui enim quas ut itaque et ipsum rem.', 'ORDER_STATUS_UPDATE', '974cfd3a-ae69-4c27-bbf8-87d4455f555b', '1bed8435-e46f-4e30-8bde-cc3ad7b05a62'),
('d3ec9349-b936-481a-b061-1167644c89e2', '2024-10-25 21:47:56.726000', '61626200-36a4-40d3-b598-9071f9e7a87f', NULL, NULL, b'0', '2024-10-25 21:47:56.726000', NULL, 0, 'Voluptatem eligendi ea id impedit nesciunt aperiam molestiae eos excepturi ut ut laboriosam.', 'BOOKING_REMINDER', 'ea632e70-9473-4088-ad41-d9301cc61126', '61626200-36a4-40d3-b598-9071f9e7a87f'),
('e6b56c2c-f35e-4dca-8faf-becd3c9ced88', '2024-10-25 21:47:56.733000', '525bd993-756f-4f5a-94ab-2fa761226946', NULL, NULL, b'0', '2024-10-25 21:47:56.733000', NULL, 0, 'Nemo est voluptatum molestiae nesciunt sequi ut voluptatem qui est.', 'PAYMENT_REMINDER', 'b8ccf81e-5cf9-4e3e-98a5-f795193843ac', '525bd993-756f-4f5a-94ab-2fa761226946'),
('e8b671c3-c4cd-48d1-b8ee-97b21a020375', '2024-10-25 21:47:56.646000', '6b6ad2ee-83c9-431c-a768-81ce2aec7756', NULL, NULL, b'0', '2024-10-25 21:47:56.646000', NULL, 0, 'Atque et inventore nulla quaerat quis architecto iusto illo voluptates quis porro.', 'ORDER_STATUS_UPDATE', '7d3e3117-6f8d-4c57-9a43-6d80a97b97b0', '6b6ad2ee-83c9-431c-a768-81ce2aec7756'),
('e9864c5f-369f-4f9a-96f3-9308523e4b75', '2024-10-25 21:47:56.655000', '5f46f576-8aa1-4594-8f8a-7e4df74190c8', NULL, NULL, b'0', '2024-10-25 21:47:56.655000', NULL, 0, 'Fuga voluptates occaecati aut ad quia ipsum et placeat sed repellat possimus esse repellendus.', 'YARD_STATUS_UPDATE', '68cfd8a5-fe49-443a-a0f2-531360a0ed59', '5f46f576-8aa1-4594-8f8a-7e4df74190c8'),
('f64cff43-1cad-44bd-8748-06e19a45c5b6', '2024-10-25 21:47:56.744000', 'ac4df3c2-98c6-4ac5-8603-9a5ada8129a5', NULL, NULL, b'0', '2024-10-25 21:47:56.744000', NULL, 0, 'Occaecati aliquid nulla et nisi qui vel qui veniam deserunt quia hic ullam ut.', 'PAYMENT_STATUS_UPDATE', '7d3e3117-6f8d-4c57-9a43-6d80a97b97b0', 'ac4df3c2-98c6-4ac5-8603-9a5ada8129a5');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `payments`
--

CREATE TABLE `payments` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `version` bigint DEFAULT NULL,
  `method` enum('CASH','CREDIT_CARD','DEBIT_CARD','PAYPAL','VN_PAY') NOT NULL,
  `price` double NOT NULL,
  `status` enum('COMPLETED','PENDING') NOT NULL,
  `booking_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `payments`
--

INSERT INTO `payments` (`id`, `created_at`, `created_by`, `deleted_at`, `deleted_by`, `is_deleted`, `updated_at`, `updated_by`, `version`, `method`, `price`, `status`, `booking_id`) VALUES
('07fd0f66-f936-46fe-963a-57e15f23f1f6', '2024-10-25 21:47:56.436000', 'e7daba0e-1da9-42a7-a015-80bd50ba1bae', NULL, NULL, b'0', '2024-10-25 21:47:56.436000', NULL, 0, 'VN_PAY', 637.6999999999999, 'PENDING', 'ea632e70-9473-4088-ad41-d9301cc61126'),
('173392f0-f493-4219-9a61-d774bba875ec', '2024-10-25 21:47:56.444000', '89e7dc5e-2b07-4a96-9384-9c58170019bd', NULL, NULL, b'0', '2024-10-25 21:47:56.444000', NULL, 0, 'VN_PAY', 436.75, 'COMPLETED', 'fad25bd3-0bec-4a8a-9f2c-5753db75d1c7'),
('22909586-5860-467c-b3b8-448f441cb115', '2024-10-25 21:47:56.366000', '5f46f576-8aa1-4594-8f8a-7e4df74190c8', NULL, NULL, b'0', '2024-10-25 21:47:56.366000', NULL, 0, 'CASH', 586.9499999999999, 'PENDING', '7dddfc1c-517d-467d-9026-0db4a96a3cde'),
('2f865ee1-fe7e-4a8d-8cff-e652e56a28ca', '2024-10-25 21:47:56.335000', '5f46f576-8aa1-4594-8f8a-7e4df74190c8', NULL, NULL, b'0', '2024-10-25 21:47:56.335000', NULL, 0, 'VN_PAY', 125.60000000000001, 'PENDING', '2bcac4bd-9f23-4394-b820-4fce66acddb8'),
('3381d0c3-1e66-4e55-aec6-fb048b9507fd', '2024-10-25 21:47:56.372000', 'db1c5740-4fe4-4e50-9bff-7fc41afe65a8', NULL, NULL, b'0', '2024-10-25 21:47:56.372000', NULL, 0, 'VN_PAY', 548.5, 'COMPLETED', '82b86a1d-283f-4368-931f-e27d1e698d7d'),
('3c2623cc-54ab-4ba1-bbfe-0f5430b5d15e', '2024-10-25 21:47:56.393000', 'ed0359c4-1960-41b0-b8b9-5d359abdb342', NULL, NULL, b'0', '2024-10-25 21:47:56.393000', NULL, 0, 'PAYPAL', 127.92, 'COMPLETED', 'b1b7b08f-0847-4b49-93d6-8721c95325da'),
('3d0bdcd2-2e3d-47f1-9315-b92bd92e477e', '2024-10-25 21:47:56.429000', 'ac771ed5-dbee-4848-b604-d5b51b2b6517', NULL, NULL, b'0', '2024-10-25 21:47:56.429000', NULL, 0, 'DEBIT_CARD', 541.8000000000001, 'COMPLETED', 'cf511958-1b23-4a7f-8893-face628c65d5'),
('5ac1ca69-a51b-4f3f-8031-7d6c37b1040d', '2024-10-25 21:47:56.329000', '6c20a29b-2faf-406b-9a8c-bf050f1be819', NULL, NULL, b'0', '2024-10-25 21:47:56.329000', NULL, 0, 'CREDIT_CARD', 270.16, 'PENDING', '21e6e7ff-d6a5-4b22-82ea-217ba42b4f69'),
('7593174e-8508-4b9b-8cab-86f80cbf1643', '2024-10-25 21:47:56.377000', '33e0fa26-5765-47fa-94d3-312ecaf21ebb', NULL, NULL, b'0', '2024-10-25 21:47:56.377000', NULL, 0, 'CASH', 75.06, 'PENDING', '974cfd3a-ae69-4c27-bbf8-87d4455f555b'),
('8a61a1c6-01ac-4c8f-98f9-685f4791ea4b', '2024-10-25 21:47:56.351000', 'ac771ed5-dbee-4848-b604-d5b51b2b6517', NULL, NULL, b'0', '2024-10-25 21:47:56.351000', NULL, 0, 'DEBIT_CARD', 135.78, 'PENDING', '71988c47-5a90-4e5d-9c1e-90cfd182f638'),
('99c70215-17dc-4bed-a1de-415327b86bfa', '2024-10-25 21:47:56.385000', '6b6ad2ee-83c9-431c-a768-81ce2aec7756', NULL, NULL, b'0', '2024-10-25 21:47:56.385000', NULL, 0, 'PAYPAL', 649.2, 'COMPLETED', '9a7c8567-6b7f-4c01-9dda-1858d38058a1'),
('a1eade9b-3ecb-423f-8428-89c181a5cb54', '2024-10-25 21:47:56.409000', '194e724a-8438-4fe9-bfdb-49f888e62811', NULL, NULL, b'0', '2024-10-25 21:47:56.409000', NULL, 0, 'PAYPAL', 329.88, 'COMPLETED', 'b8ccf81e-5cf9-4e3e-98a5-f795193843ac'),
('a41915e0-2474-40fb-8179-b6991f5f3ade', '2024-10-25 21:47:56.401000', '1bed8435-e46f-4e30-8bde-cc3ad7b05a62', NULL, NULL, b'0', '2024-10-25 21:47:56.401000', NULL, 0, 'VN_PAY', 490.43999999999994, 'COMPLETED', 'b4376e8d-8cfe-461c-b7d6-865a60943566'),
('a9276339-3308-4bd4-94d6-918249611403', '2024-10-25 21:47:56.418000', '89e7dc5e-2b07-4a96-9384-9c58170019bd', NULL, NULL, b'0', '2024-10-25 21:47:56.418000', NULL, 0, 'CREDIT_CARD', 224.44, 'PENDING', 'cdd8a014-f036-4da1-ba8f-4ef459496f2d'),
('b9d7599f-05be-4807-b417-a181f2fdec17', '2024-10-25 21:47:56.345000', 'c73542de-4c9f-476c-add0-72e3c8cf7754', NULL, NULL, b'0', '2024-10-25 21:47:56.345000', NULL, 0, 'CASH', 158.72, 'COMPLETED', '68cfd8a5-fe49-443a-a0f2-531360a0ed59'),
('bab68357-1e40-43bd-969d-0f22597d3f4c', '2024-10-25 21:47:56.318000', '2a3e56c9-1f94-4c92-8e9a-6e521837fec0', NULL, NULL, b'0', '2024-10-25 21:47:56.318000', NULL, 0, 'CREDIT_CARD', 452.88, 'PENDING', '0b96e171-fd58-4e4b-96e6-eabc7a946b8f'),
('d246232e-38a4-4d6e-9136-076d5531e62e', '2024-10-25 21:47:56.356000', '525bd993-756f-4f5a-94ab-2fa761226946', NULL, NULL, b'0', '2024-10-25 21:47:56.356000', NULL, 0, 'CASH', 607.6700000000001, 'COMPLETED', '781d8722-b708-4994-9861-9e82c9b60047'),
('d9decba6-e081-438d-933b-b55eaae3dde2', '2024-10-25 21:47:56.324000', 'b1b7af8b-9ad3-4e25-85b7-9a3704cce607', NULL, NULL, b'0', '2024-10-25 21:47:56.324000', NULL, 0, 'DEBIT_CARD', 254.66000000000003, 'PENDING', '185337bc-a645-4572-b2ca-7933099fd284'),
('dd44b5a5-4a43-4479-a8b1-501962753b7f', '2024-10-25 21:47:56.340000', 'ac771ed5-dbee-4848-b604-d5b51b2b6517', NULL, NULL, b'0', '2024-10-25 21:47:56.340000', NULL, 0, 'PAYPAL', 169.92, 'COMPLETED', '5e93a871-fb65-4cac-8063-8f34d4530b93'),
('dda97da6-16ca-42df-9083-3307921d80c6', '2024-10-25 21:47:56.361000', '525bd993-756f-4f5a-94ab-2fa761226946', NULL, NULL, b'0', '2024-10-25 21:47:56.361000', NULL, 0, 'CASH', 175.02, 'COMPLETED', '7d3e3117-6f8d-4c57-9a43-6d80a97b97b0');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `promotions`
--

CREATE TABLE `promotions` (
  `id` int NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `version` bigint DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `discount_percentage` double NOT NULL,
  `end_date` date NOT NULL,
  `name` varchar(100) NOT NULL,
  `start_date` date NOT NULL,
  `status` enum('ACTIVE','BANNED','INACTIVE') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `promotions`
--

INSERT INTO `promotions` (`id`, `created_at`, `created_by`, `deleted_at`, `deleted_by`, `is_deleted`, `updated_at`, `updated_by`, `version`, `description`, `discount_percentage`, `end_date`, `name`, `start_date`, `status`) VALUES
(1, '2024-10-25 21:47:56.886000', 'e7daba0e-1da9-42a7-a015-80bd50ba1bae', NULL, NULL, b'0', '2024-10-25 21:47:56.887000', NULL, 0, 'Cupiditate vel reprehenderit dicta sunt omnis magni aut rerum sed ipsum perferendis temporibus et sint commodi in odio voluptatem quod eveniet cum ut.', 14.86, '2024-11-23', 'KillerDeal171660', '2024-10-14', 'INACTIVE'),
(2, '2024-10-25 21:47:56.964000', '6b6ad2ee-83c9-431c-a768-81ce2aec7756', NULL, NULL, b'0', '2024-10-25 21:47:56.964000', NULL, 0, 'Dignissimos voluptatum molestiae dolorum mollitia dolores et animi quae atque expedita qui nobis non corrupti aut sapiente cum adipisci consequatur error.', 38.41, '2024-11-24', 'AwesomePromo688275', '2024-10-09', 'INACTIVE'),
(3, '2024-10-25 21:47:57.074000', 'c73542de-4c9f-476c-add0-72e3c8cf7754', NULL, NULL, b'0', '2024-10-25 21:47:57.074000', NULL, 0, 'Suscipit ab ut occaecati id voluptatem quis perspiciatis est consequatur sint quas et nemo quod praesentium rerum iste harum aliquid odit molestiae rerum.', 38.01, '2024-11-07', 'PremiumPromotion615845', '2024-09-27', 'INACTIVE'),
(4, '2024-10-25 21:47:57.152000', '525bd993-756f-4f5a-94ab-2fa761226946', NULL, NULL, b'0', '2024-10-25 21:47:57.152000', NULL, 0, 'Dicta odit officia vel et pariatur consequuntur aut qui minus quas vel aut ratione voluptate accusantium commodi impedit expedita eum.', 28.28, '2024-10-29', 'SweetPromo096441', '2024-10-21', 'INACTIVE'),
(5, '2024-10-25 21:47:57.252000', 'ac771ed5-dbee-4848-b604-d5b51b2b6517', NULL, NULL, b'0', '2024-10-25 21:47:57.252000', NULL, 0, 'Eum dolorem sunt odit nisi vero repudiandae non eum earum quae voluptatem temporibus repellat tenetur exercitationem quia sed id sit ut ut occaecati sit assumenda.', 30, '2024-11-15', 'KillerSavings083078', '2024-10-21', 'INACTIVE'),
(6, '2024-10-25 21:47:57.345000', '33e0fa26-5765-47fa-94d3-312ecaf21ebb', NULL, NULL, b'0', '2024-10-25 21:47:57.345000', NULL, 0, 'Numquam debitis dicta odio saepe vel dignissimos dignissimos eos nulla voluptas vel culpa et quia rem est impedit tempora qui suscipit a dolores perferendis.', 43.08, '2024-11-10', 'AmazingPromo155176', '2024-10-01', 'INACTIVE'),
(7, '2024-10-25 21:47:57.442000', 'c73542de-4c9f-476c-add0-72e3c8cf7754', NULL, NULL, b'0', '2024-10-25 21:47:57.442000', NULL, 0, 'Aut delectus reprehenderit nihil dolor suscipit aut in et alias sed quia quia iusto est cupiditate ducimus id voluptatibus ipsam exercitationem.', 22.59, '2024-11-13', 'PremiumSavings251556', '2024-10-15', 'BANNED'),
(8, '2024-10-25 21:47:57.540000', 'e7daba0e-1da9-42a7-a015-80bd50ba1bae', NULL, NULL, b'0', '2024-10-25 21:47:57.540000', NULL, 0, 'Voluptas occaecati et maiores non nam aut iusto illo at placeat molestiae omnis nobis voluptas neque veniam ea molestias quasi repellendus saepe aliquid animi commodi.', 5.7, '2024-11-24', 'AmazingDeal016651', '2024-09-28', 'BANNED'),
(9, '2024-10-25 21:47:57.636000', '5f46f576-8aa1-4594-8f8a-7e4df74190c8', NULL, NULL, b'0', '2024-10-25 21:47:57.636000', NULL, 0, 'Ipsa rerum ut in quas sed sint unde quas vitae eum repudiandae ipsa porro at perferendis sapiente incidunt nemo ab occaecati.', 10.51, '2024-11-21', 'SweetDeal333332', '2024-10-21', 'INACTIVE'),
(10, '2024-10-25 21:47:57.740000', '6b6ad2ee-83c9-431c-a768-81ce2aec7756', NULL, NULL, b'0', '2024-10-25 21:47:57.740000', NULL, 0, 'Qui omnis maiores corporis qui non blanditiis illo ut tempora aut sint doloribus magnam ab quia at quis est sit.', 19.54, '2024-10-27', 'PremiumDiscount582467', '2024-09-30', 'INACTIVE');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `reviews`
--

CREATE TABLE `reviews` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `version` bigint DEFAULT NULL,
  `comment` varchar(255) NOT NULL,
  `parent_review_id` varchar(255) DEFAULT NULL,
  `sports_field_id` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `reviews`
--

INSERT INTO `reviews` (`id`, `created_at`, `created_by`, `deleted_at`, `deleted_by`, `is_deleted`, `updated_at`, `updated_by`, `version`, `comment`, `parent_review_id`, `sports_field_id`, `user_id`) VALUES
('03a791ff-e486-4491-a45a-331cdbe0e01e', '2024-10-25 21:47:54.979000', 'db1c5740-4fe4-4e50-9bff-7fc41afe65a8', NULL, NULL, b'0', '2024-10-25 21:47:54.979000', NULL, 0, 'Blanditiis nam unde tempora placeat est soluta assumenda in rerum molestias esse quaerat numquam tempore veniam aut est.', NULL, 'd91aa10c-9a11-4fc6-87df-edebda96cf63', 'db1c5740-4fe4-4e50-9bff-7fc41afe65a8'),
('10b578e0-7e9f-462a-a6fd-677af678f181', '2024-10-25 21:47:55.464000', 'db1c5740-4fe4-4e50-9bff-7fc41afe65a8', NULL, NULL, b'0', '2024-10-25 21:47:55.464000', NULL, 0, 'Incidunt expedita explicabo libero et pariatur non hic et facere recusandae dicta quod eius quis laboriosam.', '32086158-8593-4f16-9399-b3c2325a9afe', '541518e1-0367-494b-9582-a818ede75307', 'db1c5740-4fe4-4e50-9bff-7fc41afe65a8'),
('162344a1-2487-48c6-9377-3dc2728c69e7', '2024-10-25 21:47:54.948000', '33e0fa26-5765-47fa-94d3-312ecaf21ebb', NULL, NULL, b'0', '2024-10-25 21:47:54.948000', NULL, 0, 'Similique sunt sit dolor sit deserunt aspernatur porro quia molestiae qui nesciunt tempore vel eveniet qui nihil non consequatur magnam.', NULL, '33b10ae9-1eb3-44d7-ae1b-877ab1be4d50', '33e0fa26-5765-47fa-94d3-312ecaf21ebb'),
('27c87cf0-8504-4ba5-a0be-5b9c6e1c2886', '2024-10-25 21:47:55.078000', '61626200-36a4-40d3-b598-9071f9e7a87f', NULL, NULL, b'0', '2024-10-25 21:47:55.078000', NULL, 0, 'Esse nisi magnam veniam ut beatae provident magni similique laboriosam vero repellat labore ducimus beatae quia repellat culpa et ad.', NULL, 'cfb872b6-77a3-480e-8b2f-812139fa0188', '61626200-36a4-40d3-b598-9071f9e7a87f'),
('30258174-181d-4379-b5f6-607111ce0781', '2024-10-25 21:47:54.970000', '6c20a29b-2faf-406b-9a8c-bf050f1be819', NULL, NULL, b'0', '2024-10-25 21:47:54.970000', NULL, 0, 'Et corrupti eos enim aut odit laborum aut quibusdam et ut repudiandae odio dolor quibusdam nisi eos.', NULL, 'af367cef-5f88-4fd9-b6d6-756516ead131', '6c20a29b-2faf-406b-9a8c-bf050f1be819'),
('32086158-8593-4f16-9399-b3c2325a9afe', '2024-10-25 21:47:55.057000', '194e724a-8438-4fe9-bfdb-49f888e62811', NULL, NULL, b'0', '2024-10-25 21:47:55.057000', NULL, 0, 'Explicabo mollitia vel modi quas voluptatem quisquam assumenda placeat quidem fugit autem exercitationem quia nemo quibusdam vel.', NULL, '837db666-9cdf-4074-aeb5-22fc0bc1a326', '194e724a-8438-4fe9-bfdb-49f888e62811'),
('38ab90f3-c975-4999-a595-cbe324e880e4', '2024-10-25 21:47:55.213000', 'f0e59b19-3cad-44ef-b2e2-3dd5aa3656ea', NULL, NULL, b'0', '2024-10-25 21:47:55.213000', NULL, 0, 'Commodi non earum nesciunt corrupti officia ipsa recusandae similique et omnis autem repudiandae omnis repudiandae.', 'a33a6a5b-8640-4710-b256-97ff6091fe0c', '20ba0ccf-e0e4-4b6a-b4ca-c99a2d4833c7', 'f0e59b19-3cad-44ef-b2e2-3dd5aa3656ea'),
('3d433d19-0595-4098-b5c3-d4f81ef53121', '2024-10-25 21:47:55.040000', 'ac771ed5-dbee-4848-b604-d5b51b2b6517', NULL, NULL, b'0', '2024-10-25 21:47:55.040000', NULL, 0, 'Omnis consequatur et aliquid est quae harum molestiae a temporibus minus placeat eius non eveniet qui.', NULL, '669e5d4d-151d-4b6a-9dae-faed754f05e5', 'ac771ed5-dbee-4848-b604-d5b51b2b6517'),
('3fd9ca4d-5920-4300-879c-b46892eb6387', '2024-10-25 21:47:55.090000', 'db1c5740-4fe4-4e50-9bff-7fc41afe65a8', NULL, NULL, b'0', '2024-10-25 21:47:55.090000', NULL, 0, 'Incidunt ut aliquid harum recusandae et corrupti consectetur enim aut aut voluptatem corporis voluptas illo quidem.', NULL, 'd91aa10c-9a11-4fc6-87df-edebda96cf63', 'db1c5740-4fe4-4e50-9bff-7fc41afe65a8'),
('4d910f45-561e-4cfd-96ed-72c8a5e869c2', '2024-10-25 21:47:54.986000', '2a3e56c9-1f94-4c92-8e9a-6e521837fec0', NULL, NULL, b'0', '2024-10-25 21:47:54.986000', NULL, 0, 'Deleniti voluptatem sunt aut est provident perspiciatis iste incidunt at eos unde et vitae enim tempora placeat et alias.', NULL, '14e9017d-ff11-47e1-972c-0e2d5a1cc417', '2a3e56c9-1f94-4c92-8e9a-6e521837fec0'),
('5faeb47e-b08f-493e-82b6-10da9ed553b5', '2024-10-25 21:47:55.330000', '2a3e56c9-1f94-4c92-8e9a-6e521837fec0', NULL, NULL, b'0', '2024-10-25 21:47:55.330000', NULL, 0, 'Illum exercitationem culpa aut et dolores laboriosam quos nihil perspiciatis fuga eos fugiat vitae molestiae praesentium dolorum.', '27c87cf0-8504-4ba5-a0be-5b9c6e1c2886', '33b10ae9-1eb3-44d7-ae1b-877ab1be4d50', '2a3e56c9-1f94-4c92-8e9a-6e521837fec0'),
('7750785e-e269-433d-93f4-ae5db363872f', '2024-10-25 21:47:54.996000', '3ccc685a-7e85-463e-9f65-84d527849e26', NULL, NULL, b'0', '2024-10-25 21:47:54.996000', NULL, 0, 'Recusandae itaque sunt rerum numquam cupiditate dicta aspernatur explicabo iusto nihil et reprehenderit soluta delectus doloremque.', NULL, 'c4283fc0-e055-4d5a-9d50-16eb759145dc', '3ccc685a-7e85-463e-9f65-84d527849e26'),
('798bc093-25ec-45df-bc27-3ce47a03490d', '2024-10-25 21:47:55.084000', '2a3e56c9-1f94-4c92-8e9a-6e521837fec0', NULL, NULL, b'0', '2024-10-25 21:47:55.084000', NULL, 0, 'Animi nam reprehenderit voluptates dolore non dolores excepturi esse nisi ab expedita quae omnis facilis rem nihil corrupti et sint.', NULL, 'cfe98404-9f6c-49aa-b88f-203a915ad68f', '2a3e56c9-1f94-4c92-8e9a-6e521837fec0'),
('81b13404-7ad2-458e-8f85-e9d1ebea7938', '2024-10-25 21:47:55.097000', 'ac771ed5-dbee-4848-b604-d5b51b2b6517', NULL, NULL, b'0', '2024-10-25 21:47:55.097000', NULL, 0, 'Sed exercitationem quis et rerum atque nobis harum atque sed voluptatibus sit ullam commodi saepe est molestiae laborum.', NULL, 'c4283fc0-e055-4d5a-9d50-16eb759145dc', 'ac771ed5-dbee-4848-b604-d5b51b2b6517'),
('9a70fd21-4ba8-4b20-a0cc-fac284b175f1', '2024-10-25 21:47:54.962000', 'e7daba0e-1da9-42a7-a015-80bd50ba1bae', NULL, NULL, b'0', '2024-10-25 21:47:54.962000', NULL, 0, 'Quas qui earum maxime quibusdam aut autem aut quia molestiae hic reiciendis eos maxime doloribus vel.', NULL, 'c4283fc0-e055-4d5a-9d50-16eb759145dc', 'e7daba0e-1da9-42a7-a015-80bd50ba1bae'),
('9e322f21-8d11-4559-ace7-b568b4ccad35', '2024-10-25 21:47:55.021000', 'db1c5740-4fe4-4e50-9bff-7fc41afe65a8', NULL, NULL, b'0', '2024-10-25 21:47:55.021000', NULL, 0, 'Et occaecati incidunt et maiores totam et tempore voluptatum et qui tenetur dolorem aut velit.', NULL, '541518e1-0367-494b-9582-a818ede75307', 'db1c5740-4fe4-4e50-9bff-7fc41afe65a8'),
('a33a6a5b-8640-4710-b256-97ff6091fe0c', '2024-10-25 21:47:55.013000', '6b6ad2ee-83c9-431c-a768-81ce2aec7756', NULL, NULL, b'0', '2024-10-25 21:47:55.013000', NULL, 0, 'At dignissimos quo dolorum quam culpa quod enim architecto modi eveniet voluptatem porro molestiae asperiores in quae maiores quas voluptatem.', NULL, 'af367cef-5f88-4fd9-b6d6-756516ead131', '6b6ad2ee-83c9-431c-a768-81ce2aec7756'),
('ba2a5850-a5fc-45b5-a4f7-b922bf2feaa6', '2024-10-25 21:47:55.005000', '525bd993-756f-4f5a-94ab-2fa761226946', NULL, NULL, b'0', '2024-10-25 21:47:55.005000', NULL, 0, 'Dolore libero quis consequatur at est aperiam enim expedita dicta eius enim eaque iste eum.', NULL, 'cfe98404-9f6c-49aa-b88f-203a915ad68f', '525bd993-756f-4f5a-94ab-2fa761226946'),
('ba4b2ca5-0269-40a1-b1d2-8f5ab35f0fbd', '2024-10-25 21:47:55.065000', 'ac771ed5-dbee-4848-b604-d5b51b2b6517', NULL, NULL, b'0', '2024-10-25 21:47:55.065000', NULL, 0, 'Fugit eos consequatur quia ad officiis fugit omnis asperiores quia qui quia qui debitis quidem consequuntur omnis.', NULL, 'c4283fc0-e055-4d5a-9d50-16eb759145dc', 'ac771ed5-dbee-4848-b604-d5b51b2b6517'),
('bf735332-c69f-4e38-afef-058c76869872', '2024-10-25 21:47:55.048000', 'e7daba0e-1da9-42a7-a015-80bd50ba1bae', NULL, NULL, b'0', '2024-10-25 21:47:55.048000', NULL, 0, 'Ducimus sint quaerat temporibus inventore in cum soluta rem numquam rerum ipsam nulla animi rerum alias quo aut repudiandae ut.', NULL, '869b03b5-8ad9-45fd-9949-afb33b1a7cb8', 'e7daba0e-1da9-42a7-a015-80bd50ba1bae'),
('ca805cc7-476e-470d-a26e-7b4e68170ee3', '2024-10-25 21:47:55.032000', '6b6ad2ee-83c9-431c-a768-81ce2aec7756', NULL, NULL, b'0', '2024-10-25 21:47:55.032000', NULL, 0, 'Laudantium blanditiis reiciendis unde sapiente iure accusamus assumenda deleniti dolor voluptas veniam quaerat et dolores voluptatem suscipit.', NULL, 'b88751f2-c9f5-46e1-811e-5e0a38905240', '6b6ad2ee-83c9-431c-a768-81ce2aec7756'),
('cdcd9025-939f-40be-8621-327b0ab25f81', '2024-10-25 21:47:55.072000', '3ccc685a-7e85-463e-9f65-84d527849e26', NULL, NULL, b'0', '2024-10-25 21:47:55.072000', NULL, 0, 'Placeat minima quae quam consequuntur repellat adipisci est eligendi nesciunt delectus consequatur fugit nihil natus qui.', NULL, '3a8759a4-ffe4-48c2-a147-80d70d1cad89', '3ccc685a-7e85-463e-9f65-84d527849e26'),
('e6dd8d83-5271-4199-920f-de25aba2d451', '2024-10-25 21:47:54.954000', 'c73542de-4c9f-476c-add0-72e3c8cf7754', NULL, NULL, b'0', '2024-10-25 21:47:54.954000', NULL, 0, 'Optio omnis illo id nam molestiae sequi dolores esse dolores facilis fugiat iure non deserunt ut occaecati velit harum.', NULL, '33b10ae9-1eb3-44d7-ae1b-877ab1be4d50', 'c73542de-4c9f-476c-add0-72e3c8cf7754');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `roles`
--

CREATE TABLE `roles` (
  `id` int NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `version` bigint DEFAULT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `roles`
--

INSERT INTO `roles` (`id`, `created_at`, `created_by`, `deleted_at`, `deleted_by`, `is_deleted`, `updated_at`, `updated_by`, `version`, `name`) VALUES
(1, '2024-10-25 21:47:51.901000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:51.901000', NULL, 0, 'CUSTOMER'),
(2, '2024-10-25 21:47:51.923000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:51.923000', NULL, 0, 'FIELD_OWNER'),
(3, '2024-10-25 21:47:51.926000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:51.926000', NULL, 0, 'ADMIN');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sports_fields`
--

CREATE TABLE `sports_fields` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `version` bigint DEFAULT NULL,
  `closing_time` datetime(6) DEFAULT NULL,
  `location` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `opacity` int DEFAULT NULL,
  `opening_time` datetime(6) NOT NULL,
  `rating` double DEFAULT NULL,
  `status` enum('OPEN','CLOSED','MAINTENANCE','INACTIVE') NOT NULL,
  `thumbnail` varchar(255) DEFAULT NULL,
  `category_id` int NOT NULL,
  `user_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `sports_fields`
--

INSERT INTO `sports_fields` (`id`, `created_at`, `created_by`, `deleted_at`, `deleted_by`, `is_deleted`, `updated_at`, `updated_by`, `version`, `closing_time`, `location`, `name`, `opacity`, `opening_time`, `rating`, `status`, `thumbnail`, `category_id`, `user_id`) VALUES
('14e9017d-ff11-47e1-972c-0e2d5a1cc417', '2024-10-25 21:47:54.687000', '525bd993-756f-4f5a-94ab-2fa761226946', NULL, NULL, b'0', '2024-10-25 21:47:54.687000', NULL, 0, '2024-12-11 12:33:14.771000', 'Suite 005 06254 Lionel Point, West Alecstad, NM 98049', 'Ohio chimeras', 42, '2023-03-28 11:21:08.384000', 0.7, 'INACTIVE', NULL, 5, '525bd993-756f-4f5a-94ab-2fa761226946'),
('20ba0ccf-e0e4-4b6a-b4ca-c99a2d4833c7', '2024-10-25 21:47:54.762000', 'ed0359c4-1960-41b0-b8b9-5d359abdb342', NULL, NULL, b'0', '2024-10-25 21:47:54.762000', NULL, 0, '2028-07-12 00:10:44.361000', '2551 Efrain Corners, West Marinland, RI 12275', 'Kansas vampires', 2, '2023-04-11 18:47:32.009000', 1.43, 'OPEN', NULL, 5, 'ed0359c4-1960-41b0-b8b9-5d359abdb342'),
('33b10ae9-1eb3-44d7-ae1b-877ab1be4d50', '2024-10-25 21:47:54.723000', 'ac771ed5-dbee-4848-b604-d5b51b2b6517', NULL, NULL, b'0', '2024-10-25 21:47:54.723000', NULL, 0, '2026-12-07 12:41:37.452000', 'Apt. 478 765 Friesen Mews, Todhaven, TX 82467', 'Texas foes', 2, '2023-05-20 04:27:30.436000', 1.04, 'OPEN', NULL, 5, 'ac771ed5-dbee-4848-b604-d5b51b2b6517'),
('3a8759a4-ffe4-48c2-a147-80d70d1cad89', '2024-10-25 21:47:54.792000', '3ccc685a-7e85-463e-9f65-84d527849e26', NULL, NULL, b'0', '2024-10-25 21:47:54.792000', NULL, 0, '2026-01-23 04:35:08.030000', 'Suite 458 434 Lourie Bypass, Yundtmouth, GA 94249', 'Louisiana penguins', 42, '2023-07-12 02:30:40.734000', 0.29, 'INACTIVE', NULL, 5, '3ccc685a-7e85-463e-9f65-84d527849e26'),
('541518e1-0367-494b-9582-a818ede75307', '2024-10-25 21:47:54.772000', '1bed8435-e46f-4e30-8bde-cc3ad7b05a62', NULL, NULL, b'0', '2024-10-25 21:47:54.772000', NULL, 0, '2029-07-04 01:07:25.008000', 'Apt. 452 6562 Gibson Vista, West Fredericfurt, MI 23341', 'Louisiana foxes', 18, '2023-12-07 19:33:28.870000', 4.09, 'OPEN', NULL, 2, '1bed8435-e46f-4e30-8bde-cc3ad7b05a62'),
('5e04084a-1778-4131-8f7a-7597b5c8bc77', '2024-10-25 21:47:54.752000', '1bed8435-e46f-4e30-8bde-cc3ad7b05a62', NULL, NULL, b'0', '2024-10-25 21:47:54.752000', NULL, 0, '2026-03-20 06:56:10.116000', '47634 Constance Mountains, South Harryside, MI 76384-0515', 'North Carolina zombies', 51, '2022-07-31 04:38:37.506000', 2.83, 'OPEN', NULL, 5, '1bed8435-e46f-4e30-8bde-cc3ad7b05a62'),
('669e5d4d-151d-4b6a-9dae-faed754f05e5', '2024-10-25 21:47:54.783000', '3ccc685a-7e85-463e-9f65-84d527849e26', NULL, NULL, b'0', '2024-10-25 21:47:54.783000', NULL, 0, '2026-08-02 23:38:25.330000', '40091 Yu Groves, Norbertview, IA 07470-8813', 'New Jersey conspirators', 50, '2021-12-27 08:22:49.214000', 0.81, 'MAINTENANCE', NULL, 4, '3ccc685a-7e85-463e-9f65-84d527849e26'),
('6827740c-5bfe-4fbe-8574-3eeff12470f8', '2024-10-25 21:47:54.849000', '194e724a-8438-4fe9-bfdb-49f888e62811', NULL, NULL, b'0', '2024-10-25 21:47:54.849000', NULL, 0, '2027-05-03 02:10:55.441000', 'Suite 028 35307 Kellee Lakes, Arnoldstad, IL 67091-9887', 'Maryland lycanthropes', 62, '2021-11-13 13:22:11.909000', 0.06, 'OPEN', NULL, 2, '194e724a-8438-4fe9-bfdb-49f888e62811'),
('837db666-9cdf-4074-aeb5-22fc0bc1a326', '2024-10-25 21:47:54.717000', '61626200-36a4-40d3-b598-9071f9e7a87f', NULL, NULL, b'0', '2024-10-25 21:47:54.717000', NULL, 0, '2029-09-28 00:05:16.021000', 'Suite 146 24471 Justin Radial, East Rafaelhaven, IL 48478-9742', 'Connecticut ducks', 12, '2023-06-17 07:57:50.338000', 0.99, 'CLOSED', NULL, 2, '61626200-36a4-40d3-b598-9071f9e7a87f'),
('869b03b5-8ad9-45fd-9949-afb33b1a7cb8', '2024-10-25 21:47:54.695000', '33e0fa26-5765-47fa-94d3-312ecaf21ebb', NULL, NULL, b'0', '2024-10-25 21:47:54.695000', NULL, 0, '2025-12-10 19:38:31.479000', 'Apt. 351 945 Greenfelder Crescent, West Alvinside, PA 23614', 'Arkansas lions', 25, '2023-03-08 12:31:09.170000', 0.68, 'OPEN', NULL, 3, '33e0fa26-5765-47fa-94d3-312ecaf21ebb'),
('a1ba8ad9-e448-4d16-81a1-b28d25663495', '2024-10-25 21:47:54.737000', '5f46f576-8aa1-4594-8f8a-7e4df74190c8', NULL, NULL, b'0', '2024-10-25 21:47:54.737000', NULL, 0, '2025-06-30 11:44:38.971000', '05419 Willie Burgs, Lake Val, NC 38765', 'Oklahoma oracles', 43, '2021-02-28 16:39:46.778000', 3.13, 'OPEN', NULL, 5, '5f46f576-8aa1-4594-8f8a-7e4df74190c8'),
('a774c2c1-41d9-4cbe-95b9-dfd6cd5c35e2', '2024-10-25 21:47:54.832000', 'db1c5740-4fe4-4e50-9bff-7fc41afe65a8', NULL, NULL, b'0', '2024-10-25 21:47:54.832000', NULL, 0, '2028-06-20 07:49:52.142000', '6352 Stoltenberg Fall, Cronaside, PA 03658-2091', 'New Mexico witches', 28, '2023-08-03 01:38:16.734000', 4.06, 'INACTIVE', NULL, 5, 'db1c5740-4fe4-4e50-9bff-7fc41afe65a8'),
('af367cef-5f88-4fd9-b6d6-756516ead131', '2024-10-25 21:47:54.813000', '194e724a-8438-4fe9-bfdb-49f888e62811', NULL, NULL, b'0', '2024-10-25 21:47:54.813000', NULL, 0, '2029-03-22 15:05:58.886000', 'Suite 723 848 Miller Plain, South Hugo, WY 86507-9221', 'Missouri chickens', 16, '2023-08-18 02:32:19.217000', 1.69, 'OPEN', NULL, 2, '194e724a-8438-4fe9-bfdb-49f888e62811'),
('b88751f2-c9f5-46e1-811e-5e0a38905240', '2024-10-25 21:47:54.711000', '3ccc685a-7e85-463e-9f65-84d527849e26', NULL, NULL, b'0', '2024-10-25 21:47:54.711000', NULL, 0, '2026-09-28 06:24:28.629000', 'Apt. 258 09378 Roosevelt Summit, Port Linneamouth, CO 92583-0185', 'Maine lions', 58, '2023-12-14 08:35:31.224000', 0.67, 'OPEN', NULL, 3, '3ccc685a-7e85-463e-9f65-84d527849e26'),
('c4283fc0-e055-4d5a-9d50-16eb759145dc', '2024-10-25 21:47:54.744000', '6c20a29b-2faf-406b-9a8c-bf050f1be819', NULL, NULL, b'0', '2024-10-25 21:47:54.744000', NULL, 0, '2026-10-26 14:14:22.918000', '014 Mee Meadow, West Burl, OR 85028-2575', 'Ohio druids', 24, '2020-04-19 14:35:42.974000', 0.44, 'OPEN', NULL, 5, '6c20a29b-2faf-406b-9a8c-bf050f1be819'),
('cfb872b6-77a3-480e-8b2f-812139fa0188', '2024-10-25 21:47:54.802000', '1bed8435-e46f-4e30-8bde-cc3ad7b05a62', NULL, NULL, b'0', '2024-10-25 21:47:54.802000', NULL, 0, '2027-05-30 19:45:36.921000', 'Suite 636 254 Mac Run, Heidenreichfort, SC 66105', 'New Mexico zebras', 51, '2024-09-03 14:33:45.561000', 2.17, 'OPEN', NULL, 3, '1bed8435-e46f-4e30-8bde-cc3ad7b05a62'),
('cfe98404-9f6c-49aa-b88f-203a915ad68f', '2024-10-25 21:47:54.840000', '5f46f576-8aa1-4594-8f8a-7e4df74190c8', NULL, NULL, b'0', '2024-10-25 21:47:54.840000', NULL, 0, '2027-11-08 11:35:55.868000', '615 Cummings Bridge, Leuschkestad, AL 40514', 'Minnesota exorcists', 71, '2020-12-12 02:11:56.005000', 2.62, 'CLOSED', NULL, 2, '5f46f576-8aa1-4594-8f8a-7e4df74190c8'),
('d91aa10c-9a11-4fc6-87df-edebda96cf63', '2024-10-25 21:47:54.822000', '1bed8435-e46f-4e30-8bde-cc3ad7b05a62', NULL, NULL, b'0', '2024-10-25 21:47:54.822000', NULL, 0, '2028-08-05 18:39:53.318000', '84751 Rex Trail, Cummerataton, RI 58174', 'Maryland enchanters', 40, '2020-11-14 01:39:35.117000', 0.98, 'OPEN', NULL, 2, '1bed8435-e46f-4e30-8bde-cc3ad7b05a62'),
('e0b7b8f2-829c-4d15-856d-dd4a78043b7e', '2024-10-25 21:47:54.730000', '3ccc685a-7e85-463e-9f65-84d527849e26', NULL, NULL, b'0', '2024-10-25 21:47:54.730000', NULL, 0, '2027-04-02 03:35:27.660000', 'Apt. 633 1880 Hermann Orchard, Templeburgh, NV 94643-4834', 'Nevada geese', 80, '2023-08-08 03:18:44.556000', 2.67, 'MAINTENANCE', NULL, 1, '3ccc685a-7e85-463e-9f65-84d527849e26'),
('f684fe6b-7748-47a0-b6f1-14e35eb42f33', '2024-10-25 21:47:54.703000', 'ac4df3c2-98c6-4ac5-8603-9a5ada8129a5', NULL, NULL, b'0', '2024-10-25 21:47:54.703000', NULL, 0, '2024-11-04 05:38:37.850000', 'Suite 111 08881 Elvin Junctions, New Laurine, SD 64352', 'Louisiana crows', 49, '2022-05-11 09:06:57.729000', 0.59, 'OPEN', NULL, 2, 'ac4df3c2-98c6-4ac5-8603-9a5ada8129a5');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `statistic`
--

CREATE TABLE `statistic` (
  `id` int NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `version` bigint DEFAULT NULL,
  `active_users` int NOT NULL,
  `date` date NOT NULL,
  `total_orders` int NOT NULL,
  `total_payments` int NOT NULL,
  `total_revenue` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `statistic`
--

INSERT INTO `statistic` (`id`, `created_at`, `created_by`, `deleted_at`, `deleted_by`, `is_deleted`, `updated_at`, `updated_by`, `version`, `active_users`, `date`, `total_orders`, `total_payments`, `total_revenue`) VALUES
(1, '2024-10-25 21:47:57.754000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:57.754000', NULL, 0, 12, '2024-10-18', 19, 53, 6064),
(2, '2024-10-25 21:47:57.760000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:57.760000', NULL, 0, 12, '2024-09-29', 88, 94, 6173),
(3, '2024-10-25 21:47:57.766000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:57.766000', NULL, 0, 31, '2024-10-07', 23, 45, 8103),
(4, '2024-10-25 21:47:57.772000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:57.772000', NULL, 0, 29, '2024-09-28', 33, 73, 3327),
(5, '2024-10-25 21:47:57.778000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:57.778000', NULL, 0, 43, '2024-10-04', 22, 96, 3504),
(6, '2024-10-25 21:47:57.785000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:57.785000', NULL, 0, 47, '2024-10-19', 20, 33, 1075),
(7, '2024-10-25 21:47:57.790000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:57.790000', NULL, 0, 26, '2024-10-11', 92, 38, 1798),
(8, '2024-10-25 21:47:57.797000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:57.797000', NULL, 0, 13, '2024-10-20', 11, 31, 5731),
(9, '2024-10-25 21:47:57.805000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:57.805000', NULL, 0, 14, '2024-10-18', 74, 64, 2094),
(10, '2024-10-25 21:47:57.812000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:57.812000', NULL, 0, 43, '2024-10-05', 5, 72, 3431);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `version` bigint DEFAULT NULL,
  `bio` varchar(255) DEFAULT NULL,
  `birthdate` date DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `gender` enum('FEMALE','MALE','OTHER') DEFAULT NULL,
  `is_activated` bit(1) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `middle_name` varchar(50) DEFAULT NULL,
  `mobile_number` varchar(20) NOT NULL,
  `password` varchar(255) NOT NULL,
  `status` enum('ACTIVE','BANNED') NOT NULL,
  `username` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `created_at`, `created_by`, `deleted_at`, `deleted_by`, `is_deleted`, `updated_at`, `updated_by`, `version`, `bio`, `birthdate`, `email`, `first_name`, `gender`, `is_activated`, `last_name`, `middle_name`, `mobile_number`, `password`, `status`, `username`) VALUES
('194e724a-8438-4fe9-bfdb-49f888e62811', '2024-10-25 21:47:53.370000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:53.370000', NULL, 0, 'Quos aspernatur quisquam dolor iure rerum numquam corrupti nemo consequatur quas fuga maxime sapiente quas quasi tempore assumenda sint temporibus eveniet quasi autem atque laudantium.', '2003-10-25', 'chauncey.gutkowski@gmail.com', 'Josefina', 'OTHER', b'1', 'Zieme', 'Miss Marlon Bauch VonRueden', '3425815020', '$2a$10$hyqKmBbStT0ODSybR//PXekwRy/78YP4VCZBrKWe0giRKJJyALX3i', 'BANNED', 'carmen.little'),
('1bed8435-e46f-4e30-8bde-cc3ad7b05a62', '2024-10-25 21:47:52.652000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:52.652000', NULL, 0, 'Ut unde explicabo omnis quia ad aut excepturi earum quia culpa quasi dolores necessitatibus quidem aliquid animi culpa et maiores sed ab accusamus natus eos.', '2003-10-25', 'isiah.gibson@yahoo.com', 'Monroe', 'OTHER', b'1', 'Smith', 'Sallie Corkery Boehm', '1646026931', '$2a$10$oOds/PFOtIDC8dFAtygWCOxfXIINEqmmaLQtHl/yeNpcoFJ3K1cn6', 'ACTIVE', 'warren.shanahan'),
('2a3e56c9-1f94-4c92-8e9a-6e521837fec0', '2024-10-25 21:47:52.731000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:52.731000', NULL, 0, 'Vel nesciunt ratione reprehenderit illo necessitatibus eos veniam eos et magnam quidem nisi sequi velit hic odit eius dolor nihil qui tenetur nihil magnam sed.', '2003-10-25', 'domingo.cremin@yahoo.com', 'Mason', 'MALE', b'1', 'Greenfelder', 'Terisa Wunsch Sauer', '3785605089', '$2a$10$iOWpUhgqu5Zz61v7hASWPONDHFZKliCb.OrfXrTZA9lAJyUgISpj.', 'ACTIVE', 'laquanda.konopelski'),
('33e0fa26-5765-47fa-94d3-312ecaf21ebb', '2024-10-25 21:47:52.091000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:52.091000', NULL, 0, 'Perspiciatis dicta ut facere inventore vero aut dicta debitis maxime dolores voluptas neque quo quam in sequi quia fuga a voluptatem dolorem ullam deserunt velit.', '2003-10-25', 'elenore.deckow@yahoo.com', 'Courtney', 'FEMALE', b'1', 'Wiegand', 'Miss Leontine Wiza Zboncak', '0811009610', '$2a$10$xYFIfCGdhoZWdlao5lku/.gZLAR/rOIw.b1y/z//if5edXnEhs7zW', 'BANNED', 'laurette.graham'),
('3ccc685a-7e85-463e-9f65-84d527849e26', '2024-10-25 21:47:53.131000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:53.131000', NULL, 0, 'Sit nisi velit sit distinctio impedit sint cum odio qui eum eos et nihil neque beatae illum et exercitationem quos aut est.', '2003-10-25', 'lynn.mckenzie@yahoo.com', 'Jospeh', 'FEMALE', b'1', 'Baumbach', 'Penelope Rowe Grimes', '7036547579', '$2a$10$CPt1DpqYa4gpwQOc0T1m.e.V/GxEjyU7onGJ1z0gNrUzSSk78.BXy', 'ACTIVE', 'xuan.block'),
('525bd993-756f-4f5a-94ab-2fa761226946', '2024-10-25 21:47:52.495000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:52.495000', NULL, 0, 'Occaecati labore perspiciatis asperiores et rerum eum aliquam repellendus placeat adipisci voluptatem facilis eaque excepturi officiis ut aliquam natus soluta.', '2003-10-25', 'julio.hane@yahoo.com', 'Twana', 'MALE', b'1', 'Kertzmann', 'Brian Keeling VonRueden Sr.', '7482088733', '$2a$10$hqrk4u2IY8tRnjUKX/ox3.Azjl715DFvzvqnsW.slD3S2jrOYtyHW', 'BANNED', 'delia.wintheiser'),
('5f46f576-8aa1-4594-8f8a-7e4df74190c8', '2024-10-25 21:47:52.337000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:52.337000', NULL, 0, 'Quo quidem dolor maxime omnis voluptas maiores esse ut magnam eos iusto perspiciatis illum qui voluptatum dolorem quo sunt odit voluptates.', '2003-10-25', 'jacki.ernser@hotmail.com', 'Timika', 'OTHER', b'1', 'Huel', 'Hoa McCullough Braun', '6814512304', '$2a$10$s/5gRD1IaZEEBDQkAGfVTOWdw5aFYWWjQK0y8q9ywRZqzmSnR.qH6', 'ACTIVE', 'marvin.tromp'),
('61626200-36a4-40d3-b598-9071f9e7a87f', '2024-10-25 21:47:53.051000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:53.051000', NULL, 0, 'Qui est impedit voluptate et libero cumque exercitationem sunt mollitia molestias ea deserunt nostrum harum temporibus impedit labore et natus reprehenderit quis ullam nobis necessitatibus.', '2003-10-25', 'david.hermiston@hotmail.com', 'Sammie', 'OTHER', b'1', 'Johnston', 'Anisa Lind Beahan', '3004528438', '$2a$10$TPStY654YsOWRpYVBlOMYuG/GKTThie6MDAiuNDXvyBO0xbrHFqRe', 'ACTIVE', 'adeline.baumbach'),
('6b6ad2ee-83c9-431c-a768-81ce2aec7756', '2024-10-25 21:47:52.178000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:52.178000', NULL, 0, 'Aut laborum ut deserunt facilis et aperiam occaecati et harum voluptatem rem ea vero quaerat et amet labore mollitia autem.', '2003-10-25', 'tyree.stoltenberg@hotmail.com', 'Gia', 'FEMALE', b'1', 'Kling', 'Dale Ondricka Homenick', '9039737987', '$2a$10$wqQ5J8tsxpjgW5ByIDPKBObMwsmcwJ5Z1z3v2cd8LtoaQI.hpvUTu', 'ACTIVE', 'omer.kautzer'),
('6c20a29b-2faf-406b-9a8c-bf050f1be819', '2024-10-25 21:47:52.257000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:52.257000', NULL, 0, 'Dolor eum autem in voluptas quia omnis ratione deserunt hic voluptatem et doloribus error ut saepe dolor et natus quisquam natus fugit sunt.', '2003-10-25', 'caleb.pacocha@gmail.com', 'Deena', 'OTHER', b'1', 'Schmidt', 'Magdalene Corkery McKenzie', '1612625989', '$2a$10$XNzVs3jy4IhuLBrkx0ZfS.5g4xHCuUCGpB6t/lfA1vuQ3W8n4kmci', 'ACTIVE', 'donnie.hettinger'),
('89e7dc5e-2b07-4a96-9384-9c58170019bd', '2024-10-25 21:47:53.291000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:53.291000', NULL, 0, 'Ad quae placeat quos error vel beatae et a numquam aliquid voluptas aut hic dolorem est odio voluptatum aspernatur autem est voluptates ratione.', '2003-10-25', 'hoyt.ziemann@gmail.com', 'Stephine', 'MALE', b'1', 'Mitchell', 'Suk Haley Bartoletti', '3432526938', '$2a$10$eVQRurHrgY7hUTGfxEHQEuhrpd/gYsxRFBgWGNGHb5.X2YNBp6NKC', 'ACTIVE', 'enedina.tromp'),
('ac4df3c2-98c6-4ac5-8603-9a5ada8129a5', '2024-10-25 21:47:52.574000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:52.574000', NULL, 0, 'Maiores vitae et cupiditate fuga id qui numquam voluptatem ipsa dolor eum animi minima vitae ab quidem ut quae soluta placeat.', '2003-10-25', 'suanne.harber@yahoo.com', 'Verona', 'MALE', b'1', 'Adams', 'Natividad Johns Ward', '4593098393', '$2a$10$adVjz6Nfi0kxYVb8I1zGn.G5wWRtP0zydqxaATsVf5Phq1KtPCVcO', 'ACTIVE', 'neal.rice'),
('ac771ed5-dbee-4848-b604-d5b51b2b6517', '2024-10-25 21:47:53.448000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:53.448000', NULL, 0, 'Quia aut accusantium fuga quia sequi ullam tenetur illo deleniti molestiae accusantium neque qui culpa velit voluptatem omnis quis eveniet ut itaque qui et debitis.', '2003-10-25', 'osvaldo.prohaska@gmail.com', 'Quiana', 'FEMALE', b'1', 'Stark', 'Cliff Carter Waters', '7381530438', '$2a$10$m/85HExPY4HMGdgGXgi1DuHuZ9EhTJ1Vrho3r01Iwz0RxBO2oXGRC', 'ACTIVE', 'jacque.langosh'),
('b1b7af8b-9ad3-4e25-85b7-9a3704cce607', '2024-10-25 21:47:52.416000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:52.416000', NULL, 0, 'Dolor rerum dolorum incidunt molestiae at aperiam sit distinctio consequatur eveniet quaerat laborum at veritatis ad quisquam et corrupti est voluptate voluptate.', '2003-10-25', 'toccara.mills@gmail.com', 'Karla', 'OTHER', b'1', 'Borer', 'Collene Hyatt Russel', '4861630778', '$2a$10$gH.mGRFrwsMZxCtgXnKlXeFl01sRtGQAIwsljuEA54G2bcAWeNCaq', 'BANNED', 'shawn.hyatt'),
('c73542de-4c9f-476c-add0-72e3c8cf7754', '2024-10-25 21:47:52.971000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:52.972000', NULL, 0, 'Et delectus magnam omnis amet minus odit veniam ea beatae iste libero minima officia cupiditate rerum in earum amet perspiciatis magni.', '2003-10-25', 'maddie.doyle@yahoo.com', 'Ignacio', 'OTHER', b'1', 'Carroll', 'Solange Macejkovic Dooley', '3904045315', '$2a$10$HWjJrHVR2DDJOI21GV.HSeRpuRCr4sZ3fIR.SA0FFVguLQZjagH8i', 'ACTIVE', 'bari.thiel'),
('db1c5740-4fe4-4e50-9bff-7fc41afe65a8', '2024-10-25 21:47:53.528000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:53.528000', NULL, 0, 'Nemo ut voluptatum facilis praesentium quibusdam error et reprehenderit nisi deserunt aut ipsum consectetur sunt eum voluptatem sint corrupti illo necessitatibus molestiae dicta iure.', '2003-10-25', 'andera.kertzmann@gmail.com', 'Delinda', 'FEMALE', b'1', 'Huel', 'Santos Langosh Lind', '2131574303', '$2a$10$wDYDMbZiat2UidSEH/Wofu6gx5SWOAjw9DOEb2zp/4XPT/54wlVgG', 'ACTIVE', 'bernardina.ortiz'),
('e7daba0e-1da9-42a7-a015-80bd50ba1bae', '2024-10-25 21:47:53.607000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:53.607000', NULL, 0, 'Eaque earum itaque qui dolor impedit laudantium sit ut numquam totam ea quia architecto aut ut rem totam amet voluptatem debitis.', '2003-10-25', 'russell.haag@gmail.com', 'Arnulfo', 'MALE', b'1', 'Ernser', 'Mallie Towne Smitham', '7876220233', '$2a$10$ODXGmDb9j5f9jdvsrqkFGOa9cdXrR2npOo3fjoruFkPoC0Aw14eFK', 'ACTIVE', 'walter.marquardt'),
('ed0359c4-1960-41b0-b8b9-5d359abdb342', '2024-10-25 21:47:52.893000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:52.893000', NULL, 0, 'In enim voluptas pariatur cum eos accusamus possimus neque voluptas quas commodi quam incidunt beatae recusandae soluta aut unde voluptas assumenda maiores eum.', '2003-10-25', 'candyce.mertz@yahoo.com', 'Brian', 'MALE', b'1', 'Murphy', 'Ms. Lacy Kassulke Baumbach', '4996144920', '$2a$10$1Eic5eFMmnPiWlREV/wRuOXcV8NwqBB7TmmKLGdZhvBLd0A8o8sNa', 'ACTIVE', 'armando.lang'),
('f0e59b19-3cad-44ef-b2e2-3dd5aa3656ea', '2024-10-25 21:47:53.210000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:53.210000', NULL, 0, 'Dolorem quo libero laborum illum harum ipsum earum suscipit molestiae dolorem voluptatibus enim necessitatibus autem amet aut eos numquam aperiam aut sit molestiae est.', '2003-10-25', 'carmela.rohan@yahoo.com', 'Gerry', 'FEMALE', b'1', 'Bahringer', 'Carolin Schulist Wehner', '0298074774', '$2a$10$IDpk.eEC0Yl3Vj1.e6nfPOuVfeQahodl1DvPYL8g7.RWnHsTo0yRW', 'BANNED', 'sanjuana.schinner'),
('ffb711ac-7543-44eb-afe2-50437430009c', '2024-10-25 21:47:52.813000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:52.813000', NULL, 0, 'Reiciendis sed unde aut rerum qui excepturi quo rerum blanditiis voluptas quos quis quia quibusdam ut quis ut earum et eos voluptatum minima harum.', '2003-10-25', 'donovan.parisian@yahoo.com', 'Buffy', 'OTHER', b'1', 'Swift', 'Reynaldo Cruickshank Bartell', '0506551659', '$2a$10$TJGNKpXkgUqwMMPOuCDauO967nqKMLEluKTuqwaRoOU6jmK7G8R5G', 'BANNED', 'tina.crist');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_role`
--

CREATE TABLE `user_role` (
  `id` int NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `version` bigint DEFAULT NULL,
  `role_id` int NOT NULL,
  `user_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `user_role`
--

INSERT INTO `user_role` (`id`, `created_at`, `created_by`, `deleted_at`, `deleted_by`, `is_deleted`, `updated_at`, `updated_by`, `version`, `role_id`, `user_id`) VALUES
(1, '2024-10-25 21:47:53.726000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:53.726000', NULL, 0, 2, '194e724a-8438-4fe9-bfdb-49f888e62811'),
(2, '2024-10-25 21:47:53.732000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:53.732000', NULL, 0, 1, '1bed8435-e46f-4e30-8bde-cc3ad7b05a62'),
(3, '2024-10-25 21:47:53.740000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:53.740000', NULL, 0, 2, '2a3e56c9-1f94-4c92-8e9a-6e521837fec0'),
(4, '2024-10-25 21:47:53.748000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:53.748000', NULL, 0, 1, '33e0fa26-5765-47fa-94d3-312ecaf21ebb'),
(5, '2024-10-25 21:47:53.758000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:53.758000', NULL, 0, 2, '3ccc685a-7e85-463e-9f65-84d527849e26'),
(6, '2024-10-25 21:47:53.768000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:53.768000', NULL, 0, 2, '525bd993-756f-4f5a-94ab-2fa761226946'),
(7, '2024-10-25 21:47:53.777000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:53.777000', NULL, 0, 2, '5f46f576-8aa1-4594-8f8a-7e4df74190c8'),
(8, '2024-10-25 21:47:53.787000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:53.787000', NULL, 0, 2, '61626200-36a4-40d3-b598-9071f9e7a87f'),
(9, '2024-10-25 21:47:53.798000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:53.798000', NULL, 0, 1, '6b6ad2ee-83c9-431c-a768-81ce2aec7756'),
(10, '2024-10-25 21:47:53.807000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:53.807000', NULL, 0, 2, '6c20a29b-2faf-406b-9a8c-bf050f1be819'),
(11, '2024-10-25 21:47:53.818000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:53.818000', NULL, 0, 2, '89e7dc5e-2b07-4a96-9384-9c58170019bd'),
(12, '2024-10-25 21:47:53.830000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:53.830000', NULL, 0, 3, 'ac4df3c2-98c6-4ac5-8603-9a5ada8129a5'),
(13, '2024-10-25 21:47:53.839000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:53.839000', NULL, 0, 1, 'ac771ed5-dbee-4848-b604-d5b51b2b6517'),
(14, '2024-10-25 21:47:53.847000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:53.847000', NULL, 0, 2, 'b1b7af8b-9ad3-4e25-85b7-9a3704cce607'),
(15, '2024-10-25 21:47:53.853000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:53.853000', NULL, 0, 2, 'c73542de-4c9f-476c-add0-72e3c8cf7754'),
(16, '2024-10-25 21:47:53.860000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:53.860000', NULL, 0, 1, 'db1c5740-4fe4-4e50-9bff-7fc41afe65a8'),
(17, '2024-10-25 21:47:53.866000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:53.866000', NULL, 0, 1, 'e7daba0e-1da9-42a7-a015-80bd50ba1bae'),
(18, '2024-10-25 21:47:53.873000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:53.873000', NULL, 0, 2, 'ed0359c4-1960-41b0-b8b9-5d359abdb342'),
(19, '2024-10-25 21:47:53.879000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:53.879000', NULL, 0, 1, 'f0e59b19-3cad-44ef-b2e2-3dd5aa3656ea'),
(20, '2024-10-25 21:47:53.885000', 'anonymous', NULL, NULL, b'0', '2024-10-25 21:47:53.885000', NULL, 0, 2, 'ffb711ac-7543-44eb-afe2-50437430009c');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `verifications`
--

CREATE TABLE `verifications` (
  `token` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `version` bigint DEFAULT NULL,
  `code` varchar(6) DEFAULT NULL,
  `expiry_time` datetime(6) NOT NULL,
  `verification_type` enum('RESET_PASSWORD','VERIFY_EMAIL_BY_CODE','VERIFY_EMAIL_BY_TOKEN') NOT NULL,
  `user_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `bookings`
--
ALTER TABLE `bookings`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKrx0ovxnr02gf9t9gnbu4de3ym` (`field_availability_id`),
  ADD KEY `fk_bookings_users` (`user_id`);

--
-- Chỉ mục cho bảng `booking_items`
--
ALTER TABLE `booking_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_booking_items_bookings` (`booking_id`);

--
-- Chỉ mục cho bảng `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `field_availabilities`
--
ALTER TABLE `field_availabilities`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_field_availabilities_sports_fields` (`sports_field_id`);

--
-- Chỉ mục cho bảng `file_metadata`
--
ALTER TABLE `file_metadata`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK47spdbof5die2tuf9wtvev9q` (`object_key`),
  ADD UNIQUE KEY `UKj37cb0xqyyo6do6ew94sek9rj` (`user_id`),
  ADD KEY `fk_file_metadata_sports_fields` (`sports_field_id`);

--
-- Chỉ mục cho bảng `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_notifications_bookings` (`booking_id`),
  ADD KEY `fk_notifications_users` (`user_id`);

--
-- Chỉ mục cho bảng `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKnuscjm6x127hkb15kcb8n56wo` (`booking_id`);

--
-- Chỉ mục cho bảng `promotions`
--
ALTER TABLE `promotions`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `reviews`
--
ALTER TABLE `reviews`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_parent_review` (`parent_review_id`),
  ADD KEY `fk_reviews_sports_fields` (`sports_field_id`),
  ADD KEY `fk_reviews_users` (`user_id`);

--
-- Chỉ mục cho bảng `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `sports_fields`
--
ALTER TABLE `sports_fields`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_sports_fields_categories` (`category_id`),
  ADD KEY `fk_sports_fields_users` (`user_id`);

--
-- Chỉ mục cho bảng `statistic`
--
ALTER TABLE `statistic`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  ADD UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`);

--
-- Chỉ mục cho bảng `user_role`
--
ALTER TABLE `user_role`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK872xec3woupu3gw59b04pj3sa` (`user_id`,`role_id`),
  ADD KEY `fk_user_role_roles` (`role_id`);

--
-- Chỉ mục cho bảng `verifications`
--
ALTER TABLE `verifications`
  ADD PRIMARY KEY (`token`),
  ADD KEY `fk_verifications_users` (`user_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `categories`
--
ALTER TABLE `categories`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `promotions`
--
ALTER TABLE `promotions`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `statistic`
--
ALTER TABLE `statistic`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `user_role`
--
ALTER TABLE `user_role`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `bookings`
--
ALTER TABLE `bookings`
  ADD CONSTRAINT `fk_bookings_field_availabilities` FOREIGN KEY (`field_availability_id`) REFERENCES `field_availabilities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_bookings_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `booking_items`
--
ALTER TABLE `booking_items`
  ADD CONSTRAINT `fk_booking_items_bookings` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `field_availabilities`
--
ALTER TABLE `field_availabilities`
  ADD CONSTRAINT `fk_field_availabilities_sports_fields` FOREIGN KEY (`sports_field_id`) REFERENCES `sports_fields` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `file_metadata`
--
ALTER TABLE `file_metadata`
  ADD CONSTRAINT `fk_file_metadata_sports_fields` FOREIGN KEY (`sports_field_id`) REFERENCES `sports_fields` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_file_metadata_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `notifications`
--
ALTER TABLE `notifications`
  ADD CONSTRAINT `fk_notifications_bookings` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_notifications_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `payments`
--
ALTER TABLE `payments`
  ADD CONSTRAINT `fk_payments_bookings` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `reviews`
--
ALTER TABLE `reviews`
  ADD CONSTRAINT `fk_parent_review` FOREIGN KEY (`parent_review_id`) REFERENCES `reviews` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_reviews_sports_fields` FOREIGN KEY (`sports_field_id`) REFERENCES `sports_fields` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_reviews_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `sports_fields`
--
ALTER TABLE `sports_fields`
  ADD CONSTRAINT `fk_sports_fields_categories` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_sports_fields_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `user_role`
--
ALTER TABLE `user_role`
  ADD CONSTRAINT `fk_user_role_roles` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_user_role_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `verifications`
--
ALTER TABLE `verifications`
  ADD CONSTRAINT `fk_verifications_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;


/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
