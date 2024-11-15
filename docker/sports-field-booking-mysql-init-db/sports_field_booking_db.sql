-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: sports-field-booking-mysql:3306
-- Thời gian đã tạo: Th10 12, 2024 lúc 04:56 PM
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
  `user_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `bookings`
--

INSERT INTO `bookings` (`id`, `created_at`, `created_by`, `deleted_at`, `deleted_by`, `is_deleted`, `updated_at`, `updated_by`, `version`, `status`, `user_id`) VALUES
('0b7f4e5f-47a4-40c1-8c35-8ed25073523b', '2024-11-07 11:18:05.035000', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd', NULL, NULL, b'0', '2024-11-07 11:18:05.035000', NULL, 0, 'REJECTED', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd'),
('0ccab24c-b432-47d8-b6f1-4e06a0d72795', '2024-11-07 11:18:05.083000', 'a2d509d5-724c-419c-a68d-122020217f4c', NULL, NULL, b'0', '2024-11-07 11:18:05.083000', NULL, 0, 'CANCELED', 'a2d509d5-724c-419c-a68d-122020217f4c'),
('1bf9f5b1-0b68-4c34-a4dd-cae87c96c0b2', '2024-11-07 11:18:05.076000', 'dd3a7406-14b8-432a-9ac4-152fe76d2df0', NULL, NULL, b'0', '2024-11-07 11:18:05.076000', NULL, 0, 'CANCELED', 'dd3a7406-14b8-432a-9ac4-152fe76d2df0'),
('3f0159f7-9ce7-4e96-980e-7de5027ee9a0', '2024-11-07 11:18:05.041000', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd', NULL, NULL, b'0', '2024-11-07 11:18:05.041000', NULL, 0, 'PENDING', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd'),
('53a6edca-1f76-4328-b18e-7af85920f2f1', '2024-11-07 11:18:04.998000', 'c4bce12e-56fd-4739-b5c8-7e62a047019a', NULL, NULL, b'0', '2024-11-07 11:18:04.998000', NULL, 0, 'REFUND_REQUESTED', 'c4bce12e-56fd-4739-b5c8-7e62a047019a'),
('55077964-848c-460a-88f5-b4c71403e04b', '2024-11-07 11:18:05.099000', 'd3e49ea3-a2c2-4e45-bcb5-10bf5793ac1b', NULL, NULL, b'0', '2024-11-07 11:18:05.099000', NULL, 0, 'PENDING', 'd3e49ea3-a2c2-4e45-bcb5-10bf5793ac1b'),
('56a62388-2156-4f4b-a88f-4fe996bd6440', '2024-11-07 11:18:05.089000', '4b39fcc6-1a05-41af-8504-fea9cba0248a', NULL, NULL, b'0', '2024-11-07 11:18:05.089000', NULL, 0, 'CANCELED', '4b39fcc6-1a05-41af-8504-fea9cba0248a'),
('5f034663-22da-4974-8f2a-3ce7d0a0e169', '2024-11-07 11:18:05.004000', '21ff59ff-cd4f-4f67-8886-22aa674d3645', NULL, NULL, b'0', '2024-11-07 11:18:05.004000', NULL, 0, 'ACCEPTED', '21ff59ff-cd4f-4f67-8886-22aa674d3645'),
('6105d623-1ddf-4617-b285-2fadab6c464a', '2024-11-07 11:18:05.009000', '96669b72-a198-4e69-adce-ffe4c839c830', NULL, NULL, b'0', '2024-11-07 11:18:05.009000', NULL, 0, 'REFUND_REQUESTED', '96669b72-a198-4e69-adce-ffe4c839c830'),
('6ad06a93-1ccb-4fc9-81b2-2c443b2faf3f', '2024-11-07 11:18:05.029000', '384a388e-5be5-4ce1-80ac-db14c91ee478', NULL, NULL, b'0', '2024-11-07 11:18:05.029000', NULL, 0, 'CANCELED', '384a388e-5be5-4ce1-80ac-db14c91ee478'),
('6d2fe410-4503-486a-93a3-a2a52e6b11e4', '2024-11-07 11:18:05.069000', 'dd3a7406-14b8-432a-9ac4-152fe76d2df0', NULL, NULL, b'0', '2024-11-07 11:18:05.069000', NULL, 0, 'REFUND_REQUESTED', 'dd3a7406-14b8-432a-9ac4-152fe76d2df0'),
('6fc38e7e-e6ba-45fd-95a9-6d065b9184be', '2024-11-07 11:18:05.052000', '4b39fcc6-1a05-41af-8504-fea9cba0248a', NULL, NULL, b'0', '2024-11-07 11:18:05.052000', NULL, 0, 'PENDING', '4b39fcc6-1a05-41af-8504-fea9cba0248a'),
('9dedc088-cc5f-4f8e-b3bb-476730a29c81', '2024-11-07 11:18:04.986000', '384a388e-5be5-4ce1-80ac-db14c91ee478', NULL, NULL, b'0', '2024-11-07 11:18:04.986000', NULL, 0, 'CANCELED', '384a388e-5be5-4ce1-80ac-db14c91ee478'),
('a24eb51b-45f3-4283-9707-a75cba84fdc7', '2024-11-07 11:18:05.015000', 'c4bce12e-56fd-4739-b5c8-7e62a047019a', NULL, NULL, b'0', '2024-11-07 11:18:05.015000', NULL, 0, 'RESCHEDULED', 'c4bce12e-56fd-4739-b5c8-7e62a047019a'),
('aca9e214-53a9-42ac-9a14-0f6b67c337b8', '2024-11-07 11:18:05.021000', '21ff59ff-cd4f-4f67-8886-22aa674d3645', NULL, NULL, b'0', '2024-11-07 11:18:05.021000', NULL, 0, 'PENDING', '21ff59ff-cd4f-4f67-8886-22aa674d3645'),
('c378e14a-8d96-4259-a065-37a86a9c0d5f', '2024-11-07 11:18:05.058000', '126c7720-3664-45f4-8202-e00bf8b81de6', NULL, NULL, b'0', '2024-11-07 11:18:05.058000', NULL, 0, 'REFUND_REQUESTED', '126c7720-3664-45f4-8202-e00bf8b81de6'),
('cfc08892-2416-47e6-8c85-e192d2d61c6f', '2024-11-07 11:18:05.046000', 'a2d509d5-724c-419c-a68d-122020217f4c', NULL, NULL, b'0', '2024-11-07 11:18:05.046000', NULL, 0, 'ACCEPTED', 'a2d509d5-724c-419c-a68d-122020217f4c'),
('e757fdf7-c12a-4150-b2c6-7fecf2faebbb', '2024-11-07 11:18:04.993000', '6d43695c-72f2-48be-9416-5428b986a008', NULL, NULL, b'0', '2024-11-07 11:18:04.993000', NULL, 0, 'CANCELED', '6d43695c-72f2-48be-9416-5428b986a008'),
('eaa31eb0-6923-49b2-8535-dff3c0a80a4d', '2024-11-07 11:18:05.106000', '4a221dca-8e45-4a32-be17-2db4a063b2ee', NULL, NULL, b'0', '2024-11-07 11:18:05.106000', NULL, 0, 'CANCELED', '4a221dca-8e45-4a32-be17-2db4a063b2ee'),
('f0136341-0c4f-44dd-93ac-4910d4f50b14', '2024-11-07 11:18:05.064000', '9d9b99fe-693d-4383-ab6a-9fa9e86957c5', NULL, NULL, b'0', '2024-11-07 11:18:05.064000', NULL, 0, 'ACCEPTED', '9d9b99fe-693d-4383-ab6a-9fa9e86957c5');

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
  `booking_id` varchar(255) NOT NULL,
  `field_availability_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------
INSERT INTO `booking_items` (`id`, `created_at`, `created_by`, `deleted_at`, `deleted_by`, `is_deleted`, `updated_at`, `updated_by`, `version`, `available_date`, `end_time`, `price`, `start_time`, `booking_id`, `field_availability_id`) VALUES
('0625941b-d758-4e4e-b00e-58afc6165ee4', '2024-11-07 11:18:05.616000', '21ff59ff-cd4f-4f67-8886-22aa674d3645', NULL, NULL, b'0', '2024-11-07 11:18:05.616000', NULL, 0, '2024-11-06', '2024-11-06 13:00:00.000000', 37.1, '2024-11-06 11:00:00.000000', 'aca9e214-53a9-42ac-9a14-0f6b67c337b8', '1258336a-b051-464c-a4d8-ed33c664e8dd'),
('0e1c4686-bf60-473c-93a8-fce04db521de', '2024-11-07 11:18:05.243000', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd', NULL, NULL, b'0', '2024-11-07 11:18:05.243000', NULL, 0, '2024-10-28', '2024-10-28 19:00:00.000000', 84.16, '2024-10-28 17:00:00.000000', '0b7f4e5f-47a4-40c1-8c35-8ed25073523b', '1336619e-6260-4a7b-80a1-7a4cf322f4ed'),
('145d6ff2-b582-4e26-974f-38b3feb839de', '2024-11-07 11:18:05.284000', 'dd3a7406-14b8-432a-9ac4-152fe76d2df0', NULL, NULL, b'0', '2024-11-07 11:18:05.284000', NULL, 0, '2024-11-06', '2024-11-06 14:00:00.000000', 68.88, '2024-11-06 13:00:00.000000', '1bf9f5b1-0b68-4c34-a4dd-cae87c96c0b2', '2799115c-daa8-4022-91cb-677e69fb9c27');
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
(1, '2024-11-07 11:18:03.171000', '2ac09f96-e32a-4aa5-9888-65f005f4a8fc', NULL, NULL, b'0', '2024-11-07 11:18:03.171000', NULL, 0, 'kabaddi'),
(2, '2024-11-07 11:18:03.289000', 'c4bce12e-56fd-4739-b5c8-7e62a047019a', NULL, NULL, b'0', '2024-11-07 11:18:03.289000', NULL, 0, 'bull fighting'),
(3, '2024-11-07 11:18:03.421000', '384a388e-5be5-4ce1-80ac-db14c91ee478', NULL, NULL, b'0', '2024-11-07 11:18:03.421000', NULL, 0, 'oil wrestling'),
(4, '2024-11-07 11:18:03.549000', '21ff59ff-cd4f-4f67-8886-22aa674d3645', NULL, NULL, b'0', '2024-11-07 11:18:03.549000', NULL, 0, 'rugby union'),
(5, '2024-11-07 11:18:03.651000', '7662b5d8-51e3-48da-badc-a6009fb8edaa', NULL, NULL, b'0', '2024-11-07 11:18:03.651000', NULL, 0, 'archery');

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
('1258336a-b051-464c-a4d8-ed33c664e8dd', '2024-11-07 11:18:04.620000', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd', NULL, NULL, b'0', '2024-11-07 11:18:04.620000', NULL, 0, '2024-11-24', '2024-11-24 18:00:00.000000', b'0', 85.25, '2024-11-24 15:00:00.000000', '3f9c6d27-1aca-46ae-b3aa-dd1221a8ad08'),
('1336619e-6260-4a7b-80a1-7a4cf322f4ed', '2024-11-07 11:18:04.782000', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd', NULL, NULL, b'0', '2024-11-12 23:19:37.247000', 'anonymous', 1, '2024-11-11', '2024-11-11 18:00:00.000000', b'0', 86.51, '2024-11-11 10:00:00.000000', '5a3b082a-6f6b-4ccc-b22a-9b594517444f'),
('2799115c-daa8-4022-91cb-677e69fb9c27', '2024-11-07 11:18:04.714000', '384a388e-5be5-4ce1-80ac-db14c91ee478', NULL, NULL, b'0', '2024-11-12 23:19:37.483000', 'anonymous', 1, '2024-11-11', '2024-11-12 00:00:00.000000', b'0', 26.71, '2024-11-11 14:00:00.000000', 'd813f7a0-c6bf-4030-b53b-b7a5b256f083'),
('37f6e61a-188c-4fb9-aec9-df9bad167371', '2024-11-07 11:18:04.809000', '2ac09f96-e32a-4aa5-9888-65f005f4a8fc', NULL, NULL, b'0', '2024-11-07 11:18:04.809000', NULL, 0, '2024-11-26', '2024-11-27 01:00:00.000000', b'1', 76.24, '2024-11-26 16:00:00.000000', '8a91d4ba-2755-4bd1-a4b5-85f991f80beb'),
('472d00d0-f653-4da6-b36a-d04a7eb51665', '2024-11-07 11:18:04.655000', '50fce098-0ac1-4ac3-9860-4b04e8b9afe7', NULL, NULL, b'0', '2024-11-07 11:18:04.655000', NULL, 0, '2024-11-25', '2024-11-26 00:00:00.000000', b'0', 32.73, '2024-11-25 18:00:00.000000', 'd4ecc945-ea33-471e-8d14-298ccb8d60f6'),
('4876ee50-2389-4134-b7b4-54f738599967', '2024-11-07 11:18:04.802000', '384a388e-5be5-4ce1-80ac-db14c91ee478', NULL, NULL, b'0', '2024-11-07 11:18:04.802000', NULL, 0, '2024-11-27', '2024-11-27 18:00:00.000000', b'0', 88.17, '2024-11-27 10:00:00.000000', 'd813f7a0-c6bf-4030-b53b-b7a5b256f083'),
('49eb6773-7010-481e-a56a-7a8f1474e7db', '2024-11-07 11:18:04.771000', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd', NULL, NULL, b'0', '2024-11-07 11:18:04.771000', NULL, 0, '2024-12-04', '2024-12-05 02:00:00.000000', b'0', 37.1, '2024-12-04 15:00:00.000000', 'f31dcf08-b452-4fca-82d3-b86007612ee8'),
('5c35853d-b56c-4404-9e1f-11b27eba5a8d', '2024-11-07 11:18:04.820000', '2ac09f96-e32a-4aa5-9888-65f005f4a8fc', NULL, NULL, b'0', '2024-11-07 11:18:04.820000', NULL, 0, '2024-11-14', '2024-11-15 03:00:00.000000', b'0', 83.8, '2024-11-14 18:00:00.000000', '1d25affd-83fa-4f5c-96ef-b4e63dc6f0e6'),
('75afb756-0ea2-4650-8e78-6f32a029a8ee', '2024-11-07 11:18:04.638000', '96669b72-a198-4e69-adce-ffe4c839c830', NULL, NULL, b'0', '2024-11-07 11:18:04.638000', NULL, 0, '2024-12-01', '2024-12-01 23:00:00.000000', b'1', 84.16, '2024-12-01 18:00:00.000000', '3890c5e7-21aa-4ba0-b9c4-5b23cb3c235c'),
('79d67f15-32cf-474a-84bd-5859b7792345', '2024-11-07 11:18:04.698000', '21ff59ff-cd4f-4f67-8886-22aa674d3645', NULL, NULL, b'0', '2024-11-07 11:18:04.698000', NULL, 0, '2024-12-04', '2024-12-04 18:00:00.000000', b'1', 48.6, '2024-12-04 10:00:00.000000', '5c4d53a7-2e4f-4601-915b-7a526681f8b6'),
('7b2c5dfd-dd5d-4357-8fc4-cd4d70467582', '2024-11-07 11:18:04.747000', '96669b72-a198-4e69-adce-ffe4c839c830', NULL, NULL, b'0', '2024-11-12 23:19:37.605000', 'anonymous', 1, '2024-11-12', '2024-11-12 20:00:00.000000', b'0', 59.86, '2024-11-12 09:00:00.000000', '3890c5e7-21aa-4ba0-b9c4-5b23cb3c235c'),
('7eac705d-5632-4510-ae53-f04267cc22ca', '2024-11-07 11:18:04.613000', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd', NULL, NULL, b'0', '2024-11-07 11:18:04.613000', NULL, 0, '2024-11-27', '2024-11-28 01:00:00.000000', b'0', 70.67, '2024-11-27 18:00:00.000000', '3f9c6d27-1aca-46ae-b3aa-dd1221a8ad08'),
('8442e460-1182-4f9f-b891-276d68c9af03', '2024-11-07 11:18:04.661000', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd', NULL, NULL, b'0', '2024-11-07 11:18:04.661000', NULL, 0, '2024-11-13', '2024-11-13 23:00:00.000000', b'1', 82.71, '2024-11-13 16:00:00.000000', '3f9c6d27-1aca-46ae-b3aa-dd1221a8ad08'),
('8dc1f42c-7e80-4524-8352-3970c5d33d7c', '2024-11-07 11:18:04.649000', '96669b72-a198-4e69-adce-ffe4c839c830', NULL, NULL, b'0', '2024-11-07 11:18:04.649000', NULL, 0, '2024-11-20', '2024-11-21 02:00:00.000000', b'1', 86.4, '2024-11-20 16:00:00.000000', '55a961aa-b590-4b0c-86f8-6c1db7eb88af'),
('93621ea1-848b-40ed-aed7-15646f88e8e5', '2024-11-07 11:18:04.707000', '96669b72-a198-4e69-adce-ffe4c839c830', NULL, NULL, b'0', '2024-11-07 11:18:04.707000', NULL, 0, '2024-11-30', '2024-11-30 19:00:00.000000', b'1', 66.12, '2024-11-30 10:00:00.000000', 'f67ddbf3-9780-4149-b0b6-016901179f37'),
('af67fdf5-7f28-4141-9c56-053bea199b5e', '2024-11-07 11:18:04.737000', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd', NULL, NULL, b'0', '2024-11-07 11:18:04.737000', NULL, 0, '2024-11-26', '2024-11-26 21:00:00.000000', b'1', 68.88, '2024-11-26 18:00:00.000000', '3f9c6d27-1aca-46ae-b3aa-dd1221a8ad08'),
('b372397e-cdaf-45a2-bb12-25bf96ac553c', '2024-11-07 11:18:04.763000', '96669b72-a198-4e69-adce-ffe4c839c830', NULL, NULL, b'0', '2024-11-07 11:18:04.763000', NULL, 0, '2024-11-12', '2024-11-12 15:00:00.000000', b'0', 57.04, '2024-11-12 12:00:00.000000', 'f67ddbf3-9780-4149-b0b6-016901179f37'),
('c0d228f5-f907-4e13-ae24-ad00479944db', '2024-11-07 11:18:04.755000', '96669b72-a198-4e69-adce-ffe4c839c830', NULL, NULL, b'0', '2024-11-07 11:18:04.755000', NULL, 0, '2024-11-23', '2024-11-23 19:00:00.000000', b'1', 26.6, '2024-11-23 11:00:00.000000', '55a961aa-b590-4b0c-86f8-6c1db7eb88af'),
('c5c8472b-a7fa-482c-9648-2c53381b14c1', '2024-11-07 11:18:04.644000', '384a388e-5be5-4ce1-80ac-db14c91ee478', NULL, NULL, b'0', '2024-11-07 11:18:04.644000', NULL, 0, '2024-11-27', '2024-11-27 20:00:00.000000', b'0', 89.1, '2024-11-27 17:00:00.000000', '47e8dfed-658f-41fc-9bce-b4dec2aec8ec'),
('c71ba2ad-c25a-4ade-838b-46412d431fa1', '2024-11-07 11:18:04.675000', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd', NULL, NULL, b'0', '2024-11-07 11:18:04.675000', NULL, 0, '2024-12-04', '2024-12-04 16:00:00.000000', b'0', 47.09, '2024-12-04 13:00:00.000000', '5a3b082a-6f6b-4ccc-b22a-9b594517444f'),
('cecdfb36-3923-4a1b-af58-d7fbb40cf3bc', '2024-11-07 11:18:04.814000', '96669b72-a198-4e69-adce-ffe4c839c830', NULL, NULL, b'0', '2024-11-07 11:18:04.814000', NULL, 0, '2024-12-06', '2024-12-06 22:00:00.000000', b'1', 88.99, '2024-12-06 12:00:00.000000', 'f67ddbf3-9780-4149-b0b6-016901179f37'),
('d75ca262-974a-4f9b-bf49-9db4abb1e9c0', '2024-11-07 11:18:04.788000', '384a388e-5be5-4ce1-80ac-db14c91ee478', NULL, NULL, b'0', '2024-11-07 11:18:04.788000', NULL, 0, '2024-11-10', '2024-11-10 18:00:00.000000', b'0', 78.73, '2024-11-10 10:00:00.000000', '47e8dfed-658f-41fc-9bce-b4dec2aec8ec'),
('dadf1631-241a-4537-a207-3b3d744adee0', '2024-11-07 11:18:04.668000', '50fce098-0ac1-4ac3-9860-4b04e8b9afe7', NULL, NULL, b'0', '2024-11-07 11:18:04.668000', NULL, 0, '2024-12-04', '2024-12-04 23:00:00.000000', b'0', 84.98, '2024-12-04 14:00:00.000000', 'd4ecc945-ea33-471e-8d14-298ccb8d60f6'),
('e0aad38c-fbc3-4134-b4e3-1f791974d017', '2024-11-07 11:18:04.632000', '96669b72-a198-4e69-adce-ffe4c839c830', NULL, NULL, b'0', '2024-11-07 11:18:04.632000', NULL, 0, '2024-12-06', '2024-12-06 13:00:00.000000', b'1', 26.32, '2024-12-06 10:00:00.000000', '3890c5e7-21aa-4ba0-b9c4-5b23cb3c235c'),
('ed219246-e49c-4539-8220-64040a90de7d', '2024-11-07 11:18:04.722000', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd', NULL, NULL, b'0', '2024-11-07 11:18:04.722000', NULL, 0, '2024-11-10', '2024-11-10 22:00:00.000000', b'0', 12.3, '2024-11-10 12:00:00.000000', '01686ef7-5932-45dd-a343-d6339543c6d7'),
('ed7e6250-75c5-49d0-8a56-a3c740ba8585', '2024-11-07 11:18:04.626000', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd', NULL, NULL, b'0', '2024-11-07 11:18:04.626000', NULL, 0, '2024-11-26', '2024-11-27 02:00:00.000000', b'1', 54.53, '2024-11-26 17:00:00.000000', '01686ef7-5932-45dd-a343-d6339543c6d7'),
('f1c36909-9863-4143-a10a-a93b099eb776', '2024-11-07 11:18:04.794000', '2ac09f96-e32a-4aa5-9888-65f005f4a8fc', NULL, NULL, b'0', '2024-11-07 11:18:04.794000', NULL, 0, '2024-11-27', '2024-11-27 18:00:00.000000', b'1', 85.99, '2024-11-27 17:00:00.000000', '1d25affd-83fa-4f5c-96ef-b4e63dc6f0e6'),
('f38c6365-08c7-4929-b51c-9aabe5be4081', '2024-11-07 11:18:04.681000', '0a5b5126-6ae1-4169-aeb3-dcd2f0cb20ad', NULL, NULL, b'0', '2024-11-07 11:18:04.681000', NULL, 0, '2024-11-15', '2024-11-15 15:00:00.000000', b'0', 61.81, '2024-11-15 09:00:00.000000', '65a6b881-286a-4144-b7bf-29d372715d31'),
('fbcc9450-cf3b-4625-a290-9f8304aea4ae', '2024-11-07 11:18:04.729000', '126c7720-3664-45f4-8202-e00bf8b81de6', NULL, NULL, b'0', '2024-11-07 11:18:04.729000', NULL, 0, '2024-12-04', '2024-12-04 23:00:00.000000', b'0', 15.07, '2024-12-04 12:00:00.000000', '8e532147-b8f5-4291-9fef-0729723596c4'),
('fc1f700f-b08a-49fc-9ee4-bc97242f9bd9', '2024-11-07 11:18:04.689000', '21ff59ff-cd4f-4f67-8886-22aa674d3645', NULL, NULL, b'0', '2024-11-07 11:18:04.689000', NULL, 0, '2024-11-21', '2024-11-21 18:00:00.000000', b'0', 52.12, '2024-11-21 11:00:00.000000', '5c4d53a7-2e4f-4601-915b-7a526681f8b6');

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
  `sports_field_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `file_metadata`
--

INSERT INTO `file_metadata` (`id`, `created_at`, `created_by`, `deleted_at`, `deleted_by`, `is_deleted`, `updated_at`, `updated_by`, `version`, `content_type`, `object_key`, `size`, `sports_field_id`, `user_id`) VALUES
('086b22c8-3ab0-4418-83ad-846badce07e4', '2024-11-07 11:20:48.571000', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd', NULL, NULL, b'0', '2024-11-07 11:26:20.988000', 'anonymous', 4, 'image/jpeg', 'image_20241107_112048_c95fd446-0ccb-407d-9dc1-07460b260185.jpeg', 0, '01686ef7-5932-45dd-a343-d6339543c6d7', NULL),
('09decabd-c9b3-46d5-bd45-086f0d5dcfa2', '2024-11-07 11:19:28.091000', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd', NULL, NULL, b'0', '2024-11-07 11:26:21.015000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111928_3fa9d15e-a808-44ad-b565-5c954c7fe2d6.jpeg', 0, '01686ef7-5932-45dd-a343-d6339543c6d7', NULL),
('0af536d9-792d-4654-88b3-c7607d24856e', '2024-11-07 11:19:46.948000', 'a2d509d5-724c-419c-a68d-122020217f4c', NULL, NULL, b'0', '2024-11-07 11:26:21.025000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111946_f8f164e3-abc5-4b6d-b33a-da1b070f255c.jpeg', 0, '0769e854-d225-4ebc-8e22-92cc34e0cbb0', NULL),
('0bdd080d-a199-4468-aedf-f3583881876f', '2024-11-07 11:19:47.322000', 'a2d509d5-724c-419c-a68d-122020217f4c', NULL, NULL, b'0', '2024-11-07 11:26:21.036000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111947_3d95ecef-2f47-42df-a0fc-ca52f1a7ed74.jpeg', 0, '0769e854-d225-4ebc-8e22-92cc34e0cbb0', NULL),
('2e9ae81e-a64a-457c-a3b5-42f5545a2325', '2024-11-07 11:19:27.732000', '2ac09f96-e32a-4aa5-9888-65f005f4a8fc', NULL, NULL, b'0', '2024-11-07 11:26:21.047000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111927_e6d420ad-e6c9-46d3-9e3b-64b7ca6e37bd.jpeg', 0, '1d25affd-83fa-4f5c-96ef-b4e63dc6f0e6', NULL),
('31570bce-cad4-4342-aaf4-9af1c93570d8', '2024-11-07 11:19:06.428000', '2ac09f96-e32a-4aa5-9888-65f005f4a8fc', NULL, NULL, b'0', '2024-11-07 11:26:21.059000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111906_70baf85a-0111-4fee-812d-ac0243a05937.jpeg', 0, '1d25affd-83fa-4f5c-96ef-b4e63dc6f0e6', NULL),
('37638cfa-ac6a-461b-99da-2d3a9f40730e', '2024-11-07 11:19:49.187000', '96669b72-a198-4e69-adce-ffe4c839c830', NULL, NULL, b'0', '2024-11-07 11:26:21.071000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111949_d780fa1a-77b7-437b-9c6b-b43133d41333.jpeg', 0, '34d49c93-d4ec-4296-8bd4-df561873b422', NULL),
('41fba3ef-93a6-4e60-86b8-3d57ad4c520c', '2024-11-07 11:18:35.609000', '96669b72-a198-4e69-adce-ffe4c839c830', NULL, NULL, b'0', '2024-11-07 11:26:21.080000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111835_7eab0020-ecbf-48e4-b1c1-2336dd4cbb6e.jpeg', 0, '34d49c93-d4ec-4296-8bd4-df561873b422', NULL),
('4831303b-3e7e-4022-973b-e4c504474590', '2024-11-07 11:19:05.716000', '96669b72-a198-4e69-adce-ffe4c839c830', NULL, NULL, b'0', '2024-11-07 11:26:21.091000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111905_7a5e4850-fba9-4adc-bf48-0291fec3f05f.jpeg', 0, '3890c5e7-21aa-4ba0-b9c4-5b23cb3c235c', NULL),
('4a7623e8-2583-4017-9071-9f67411452e2', '2024-11-07 11:18:37.009000', '96669b72-a198-4e69-adce-ffe4c839c830', NULL, NULL, b'0', '2024-11-07 11:26:21.100000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111836_5ccf2215-80b6-40d8-adb8-55bbae4ef88a.jpeg', 0, '3890c5e7-21aa-4ba0-b9c4-5b23cb3c235c', NULL),
('503aee74-b5b7-4468-a37c-923190c7515e', '2024-11-07 11:18:35.654000', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd', NULL, NULL, b'0', '2024-11-07 11:26:21.108000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111835_48981517-b20f-4a12-8f79-f4ea594aeafd.jpeg', 0, '3f9c6d27-1aca-46ae-b3aa-dd1221a8ad08', NULL),
('51882b1a-8785-40e8-a9ce-fdbad9b4d56c', '2024-11-07 11:19:26.701000', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd', NULL, NULL, b'0', '2024-11-07 11:26:21.117000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111926_e27c6c7e-69ca-4047-9ee0-833286b7e699.jpeg', 0, '3f9c6d27-1aca-46ae-b3aa-dd1221a8ad08', NULL),
('583d7b7b-642a-4641-8d44-8206560df36a', '2024-11-07 11:18:38.110000', '384a388e-5be5-4ce1-80ac-db14c91ee478', NULL, NULL, b'0', '2024-11-07 11:26:21.125000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111838_9d998609-2727-41fc-bebe-bd0c09004c75.jpeg', 0, '47e8dfed-658f-41fc-9bce-b4dec2aec8ec', NULL),
('59da1174-5264-4d64-b73f-746b950e64ad', '2024-11-07 11:19:28.452000', '384a388e-5be5-4ce1-80ac-db14c91ee478', NULL, NULL, b'0', '2024-11-07 11:26:21.133000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111928_c657e443-2cdb-4f80-a0d7-dd8b3ed1cb38.jpeg', 0, '47e8dfed-658f-41fc-9bce-b4dec2aec8ec', NULL),
('5a37cdbc-03c7-4af4-8b7d-217a0a153df5', '2024-11-07 11:19:03.635000', '96669b72-a198-4e69-adce-ffe4c839c830', NULL, NULL, b'0', '2024-11-07 11:26:21.142000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111903_4f48db9e-769b-4864-b7ca-005f6dd96012.jpeg', 0, '55a961aa-b590-4b0c-86f8-6c1db7eb88af', NULL),
('5b5aa86f-f5ae-4ec6-8990-28495b386cc9', '2024-11-07 11:19:47.705000', '96669b72-a198-4e69-adce-ffe4c839c830', NULL, NULL, b'0', '2024-11-07 11:26:21.150000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111947_468b2511-d1dc-4f01-a6b9-3b78dce824f1.jpeg', 0, '55a961aa-b590-4b0c-86f8-6c1db7eb88af', NULL),
('61827e12-05f1-4c62-9e09-21ce8d2de0e0', '2024-11-07 11:19:48.094000', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd', NULL, NULL, b'0', '2024-11-07 11:26:21.157000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111948_c9f5b1a8-00f7-4f22-999b-97016be2f2ee.jpeg', 0, '5a3b082a-6f6b-4ccc-b22a-9b594517444f', NULL),
('634dac40-c1a5-4260-8638-caf98336df51', '2024-11-07 11:19:27.702000', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd', NULL, NULL, b'0', '2024-11-07 11:26:21.165000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111927_9dc5bd8d-665e-4465-a70b-dae61636ecdc.jpeg', 0, '5a3b082a-6f6b-4ccc-b22a-9b594517444f', NULL),
('6390b056-9193-419c-9bd0-f382a56fba17', '2024-11-07 11:19:48.766000', '21ff59ff-cd4f-4f67-8886-22aa674d3645', NULL, NULL, b'0', '2024-11-07 11:26:21.173000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111948_5555add3-3519-49f3-8f26-d74e6ce70317.jpeg', 0, '5c4d53a7-2e4f-4601-915b-7a526681f8b6', NULL),
('720e565c-48b5-4c60-9202-732f18cead17', '2024-11-07 11:20:50.127000', '21ff59ff-cd4f-4f67-8886-22aa674d3645', NULL, NULL, b'0', '2024-11-07 11:26:21.181000', 'anonymous', 4, 'image/jpeg', 'image_20241107_112050_7d6af888-f21f-41ce-bc83-e7026753de1a.jpeg', 0, '5c4d53a7-2e4f-4601-915b-7a526681f8b6', NULL),
('7669715a-6971-4d92-8dfc-6267f0320aa6', '2024-11-07 11:19:26.353000', '0a5b5126-6ae1-4169-aeb3-dcd2f0cb20ad', NULL, NULL, b'0', '2024-11-07 11:26:21.191000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111926_f1b1669f-cbc1-4892-92c6-fd1cde6ed924.jpeg', 0, '65a6b881-286a-4144-b7bf-29d372715d31', NULL),
('7933a6da-5449-429f-83e8-96a208a1403b', '2024-11-07 11:19:04.995000', '0a5b5126-6ae1-4169-aeb3-dcd2f0cb20ad', NULL, NULL, b'0', '2024-11-07 11:26:21.198000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111904_8c7bc515-e97b-4bf8-b45c-fcef8f3c25a9.jpeg', 0, '65a6b881-286a-4144-b7bf-29d372715d31', NULL),
('7cab0add-d4f1-4aec-8131-6aee0c5244f4', '2024-11-07 11:20:49.500000', 'd3e49ea3-a2c2-4e45-bcb5-10bf5793ac1b', NULL, NULL, b'0', '2024-11-07 11:26:21.207000', 'anonymous', 4, 'image/jpeg', 'image_20241107_112049_740830f7-14cc-433c-a61a-d820cbd24897.jpeg', 0, '6d104403-0d37-4e35-8a5b-4960285bc1d7', NULL),
('90664703-2011-4943-a9db-c27eeaae4fd4', '2024-11-07 11:19:05.044000', 'd3e49ea3-a2c2-4e45-bcb5-10bf5793ac1b', NULL, NULL, b'0', '2024-11-07 11:26:21.216000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111904_168f2b35-95bb-4328-b8a1-3f1829d08129.jpeg', 0, '6d104403-0d37-4e35-8a5b-4960285bc1d7', NULL),
('9d318426-da9b-4072-9e8c-d9a7cd569692', '2024-11-07 11:19:46.587000', '2ac09f96-e32a-4aa5-9888-65f005f4a8fc', NULL, NULL, b'0', '2024-11-07 11:26:21.224000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111946_e65b14a9-be78-4414-9837-5665cd4ca96c.jpeg', 0, '8a91d4ba-2755-4bd1-a4b5-85f991f80beb', NULL),
('9e8dd3e7-0b3d-48c1-b948-6e7f219ccf96', '2024-11-07 11:20:49.705000', '2ac09f96-e32a-4aa5-9888-65f005f4a8fc', NULL, NULL, b'0', '2024-11-07 11:26:21.234000', 'anonymous', 4, 'image/jpeg', 'image_20241107_112049_7fec6da6-d266-4c45-845b-3d28b7e54e12.jpeg', 0, '8a91d4ba-2755-4bd1-a4b5-85f991f80beb', NULL),
('9f13e2e2-137c-47fb-a4d4-a85dd8ab2443', '2024-11-07 11:18:36.581000', '126c7720-3664-45f4-8202-e00bf8b81de6', NULL, NULL, b'0', '2024-11-07 11:26:21.243000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111836_5efa4440-25cc-4765-94b2-7014c3a88d08.jpeg', 0, '8e532147-b8f5-4291-9fef-0729723596c4', NULL),
('a0462774-c7fb-4f2b-a4c2-e1900aba496e', '2024-11-07 11:18:37.353000', '126c7720-3664-45f4-8202-e00bf8b81de6', NULL, NULL, b'0', '2024-11-07 11:26:21.253000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111837_24b1ab26-fe68-4ec0-b8a8-67da85f5ae2c.jpeg', 0, '8e532147-b8f5-4291-9fef-0729723596c4', NULL),
('a3959da5-b066-4798-8d11-f5b561c73f4c', '2024-11-07 11:18:36.089000', '50fce098-0ac1-4ac3-9860-4b04e8b9afe7', NULL, NULL, b'0', '2024-11-07 11:26:21.262000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111836_ab2173a7-6f97-4bf3-8897-aefd41c2e275.jpeg', 0, 'd4ecc945-ea33-471e-8d14-298ccb8d60f6', NULL),
('a51bfe99-69b7-4d2a-844f-28b87fe8d597', '2024-11-07 11:19:06.061000', '50fce098-0ac1-4ac3-9860-4b04e8b9afe7', NULL, NULL, b'0', '2024-11-07 11:26:21.273000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111906_d6b12074-c486-4dc0-a23f-69474271f191.jpeg', 0, 'd4ecc945-ea33-471e-8d14-298ccb8d60f6', NULL),
('a79da37a-d6c0-46f9-a70e-052b6edbcd61', '2024-11-07 11:19:49.557000', '384a388e-5be5-4ce1-80ac-db14c91ee478', NULL, NULL, b'0', '2024-11-07 11:26:21.283000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111949_d4364c81-284c-45cb-b171-ada412ab317b.jpeg', 0, 'd813f7a0-c6bf-4030-b53b-b7a5b256f083', NULL),
('ac356e79-5460-41b6-8eda-a7f6da410c7f', '2024-11-07 11:19:04.337000', '384a388e-5be5-4ce1-80ac-db14c91ee478', NULL, NULL, b'0', '2024-11-07 11:26:21.292000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111904_18826b41-3698-443d-9056-ce75f3f7bd62.jpeg', 0, 'd813f7a0-c6bf-4030-b53b-b7a5b256f083', NULL),
('b61b532d-e32c-40a0-ab97-e5201f53c4a0', '2024-11-07 11:18:37.765000', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd', NULL, NULL, b'0', '2024-11-07 11:26:21.301000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111837_d125de23-786e-4d2f-a52c-823da16659c6.jpeg', 0, 'f31dcf08-b452-4fca-82d3-b86007612ee8', NULL),
('c8e6e6ff-c67f-4b65-b975-31c0bfa0476b', '2024-11-07 11:19:48.424000', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd', NULL, NULL, b'0', '2024-11-07 11:26:21.310000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111948_946a00b3-bdd1-4fa9-9002-a491c60724f0.jpeg', 0, 'f31dcf08-b452-4fca-82d3-b86007612ee8', NULL),
('d021aa75-8711-48c8-abe1-41c246924afa', '2024-11-07 11:19:27.051000', '96669b72-a198-4e69-adce-ffe4c839c830', NULL, NULL, b'0', '2024-11-07 11:26:21.320000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111926_31e29ce5-1cd3-46e9-8ee9-f1871b1517ed.jpeg', 0, 'f67ddbf3-9780-4149-b0b6-016901179f37', NULL),
('d0a174bb-5c82-4385-abfb-123f12e96163', '2024-11-07 11:19:05.366000', '96669b72-a198-4e69-adce-ffe4c839c830', NULL, NULL, b'0', '2024-11-07 11:26:21.328000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111905_1f6b53ff-3f45-4c6d-8336-24bd64486031.jpeg', 0, 'f67ddbf3-9780-4149-b0b6-016901179f37', NULL),
('e203491d-d92c-4b3f-94e8-6d763248d033', '2024-11-07 11:19:03.969000', 'a2d509d5-724c-419c-a68d-122020217f4c', NULL, NULL, b'0', '2024-11-07 11:26:21.336000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111903_ae6e3c07-2547-45df-a0ed-4811e1a17207.jpeg', 0, 'f99f368f-7d4d-4617-a89e-7c79728a1dff', NULL),
('e3c222c6-4cc1-4fb0-9d6f-aa7a9011c5da', '2024-11-07 11:19:26.004000', 'a2d509d5-724c-419c-a68d-122020217f4c', NULL, NULL, b'0', '2024-11-07 11:26:21.346000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111925_7ba8b6e4-d2c2-4e37-9bdb-0fdc77980f92.jpeg', 0, 'f99f368f-7d4d-4617-a89e-7c79728a1dff', NULL),
('e8778060-3cb0-489f-a6d3-ad306c379323', '2024-11-07 11:19:28.801000', '384a388e-5be5-4ce1-80ac-db14c91ee478', NULL, NULL, b'0', '2024-11-07 11:26:21.354000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111928_c0390118-b312-434c-b119-4858e10cfe5a.jpeg', 0, 'faa74327-1428-4539-a618-45acf4fa3aee', NULL),
('fc2fcb32-24a0-41a5-9f76-de71ca1bdc78', '2024-11-07 11:18:38.556000', '384a388e-5be5-4ce1-80ac-db14c91ee478', NULL, NULL, b'0', '2024-11-07 11:26:21.362000', 'anonymous', 4, 'image/jpeg', 'image_20241107_111838_a82ee390-07db-44c1-bca1-63890030aa0e.jpeg', 0, 'faa74327-1428-4539-a618-45acf4fa3aee', NULL);

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
  `type` enum('BOOKING_REMINDER','ORDER_STATUS_UPDATE','PAYMENT_REMINDER','PAYMENT_STATUS_UPDATE','PROMOTION','YARD_STATUS_UPDATE','COMMENT_FEEDBACK') NOT NULL,
  `booking_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) NOT NULL,
  `review_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `notifications`
--

INSERT INTO `notifications` (`id`, `created_at`, `created_by`, `deleted_at`, `deleted_by`, `is_deleted`, `updated_at`, `updated_by`, `version`, `message`, `type`, `booking_id`, `user_id`, `review_id`) VALUES
('1c522034-cb18-46e6-80e8-adef47231b60', '2024-11-07 11:18:06.361000', 'c4bce12e-56fd-4739-b5c8-7e62a047019a', NULL, NULL, b'0', '2024-11-07 11:18:06.361000', NULL, 0, 'Accusantium sapiente commodi provident natus tempora placeat praesentium temporibus ipsa cupiditate aliquam.', 'BOOKING_REMINDER', '6d2fe410-4503-486a-93a3-a2a52e6b11e4', 'c4bce12e-56fd-4739-b5c8-7e62a047019a',NULL),
('243f226d-765c-4290-8550-de70f8371040', '2024-11-07 11:18:06.339000', '9d9b99fe-693d-4383-ab6a-9fa9e86957c5', NULL, NULL, b'0', '2024-11-07 11:18:06.339000', NULL, 0, 'Ut qui et nesciunt unde aut officia in eius pariatur dolorem minima quis in.', 'PAYMENT_REMINDER', 'e757fdf7-c12a-4150-b2c6-7fecf2faebbb', '9d9b99fe-693d-4383-ab6a-9fa9e86957c5',NULL),
('2c4f1efa-30d8-4057-84ee-4474cba557c9', '2024-11-07 11:18:06.317000', 'c4bce12e-56fd-4739-b5c8-7e62a047019a', NULL, NULL, b'0', '2024-11-07 11:18:06.317000', NULL, 0, 'Eos corporis voluptatem et rem officia expedita enim asperiores sed id magni est.', 'YARD_STATUS_UPDATE', '6105d623-1ddf-4617-b285-2fadab6c464a', 'c4bce12e-56fd-4739-b5c8-7e62a047019a',NULL),
('3b3c3a5f-7ebe-4995-bd58-b5c99f5c3052', '2024-11-07 11:18:06.347000', 'bf1aad2f-be18-41bc-a738-ddd8ae96cd8c', NULL, NULL, b'0', '2024-11-07 11:18:06.347000', NULL, 0, 'Laudantium similique exercitationem explicabo commodi similique enim sequi accusamus et voluptatum distinctio a doloribus.', 'YARD_STATUS_UPDATE', '9dedc088-cc5f-4f8e-b3bb-476730a29c81', 'bf1aad2f-be18-41bc-a738-ddd8ae96cd8c',NULL),
('3d723dc7-75dc-43d4-aebf-da449b56fd2a', '2024-11-07 11:18:06.275000', '126c7720-3664-45f4-8202-e00bf8b81de6', NULL, NULL, b'0', '2024-11-07 11:18:06.275000', NULL, 0, 'Magni consequatur vel praesentium aliquam et consequatur odio aperiam perferendis.', 'BOOKING_REMINDER', '6105d623-1ddf-4617-b285-2fadab6c464a', '126c7720-3664-45f4-8202-e00bf8b81de6',NULL),
('4ed5e09b-3324-416b-8c12-ed5d2610f435', '2024-11-07 11:18:06.249000', '4b39fcc6-1a05-41af-8504-fea9cba0248a', NULL, NULL, b'0', '2024-11-07 11:18:06.249000', NULL, 0, 'Vel nulla cupiditate quis optio vero aut illum perspiciatis sequi necessitatibus praesentium ipsa quia.', 'BOOKING_REMINDER', '6ad06a93-1ccb-4fc9-81b2-2c443b2faf3f', '4b39fcc6-1a05-41af-8504-fea9cba0248a',NULL),
('57d3b575-c764-4625-a973-f479e8c8c31a', '2024-11-07 11:18:06.310000', '9baedc05-08e1-4f12-9735-195827c72845', NULL, NULL, b'0', '2024-11-07 11:18:06.310000', NULL, 0, 'Aperiam dicta et minima reiciendis aut dolor perspiciatis voluptatibus deserunt iusto.', 'PROMOTION', '6ad06a93-1ccb-4fc9-81b2-2c443b2faf3f', '9baedc05-08e1-4f12-9735-195827c72845',NULL),
('5a12cd36-91d5-40b7-9254-4d12b6b9fce0', '2024-11-07 11:18:06.242000', '7662b5d8-51e3-48da-badc-a6009fb8edaa', NULL, NULL, b'0', '2024-11-07 11:18:06.242000', NULL, 0, 'Ut voluptatum placeat aliquam voluptatum quia fugit et eos in consequatur harum ipsam.', 'ORDER_STATUS_UPDATE', 'aca9e214-53a9-42ac-9a14-0f6b67c337b8', '7662b5d8-51e3-48da-badc-a6009fb8edaa',NULL),
('6897a48b-fe69-406d-9215-2b8b4517bceb', '2024-11-07 11:18:06.254000', 'a2d509d5-724c-419c-a68d-122020217f4c', NULL, NULL, b'0', '2024-11-07 11:18:06.254000', NULL, 0, 'Dolorem aut aut quis sequi qui doloremque fugiat est alias similique atque.', 'PROMOTION', 'a24eb51b-45f3-4283-9707-a75cba84fdc7', 'a2d509d5-724c-419c-a68d-122020217f4c',NULL),
('724e5c5b-78ef-4008-b1b8-ebf2e2374fef', '2024-11-07 11:18:06.270000', '96669b72-a198-4e69-adce-ffe4c839c830', NULL, NULL, b'0', '2024-11-07 11:18:06.270000', NULL, 0, 'Id velit incidunt dolorem ducimus recusandae vel nihil similique porro rerum minus aut dolorum.', 'PAYMENT_STATUS_UPDATE', '6ad06a93-1ccb-4fc9-81b2-2c443b2faf3f', '96669b72-a198-4e69-adce-ffe4c839c830',NULL),
('81ec1f82-7ca7-465e-9dcb-fc4278d029c5', '2024-11-07 11:18:06.304000', '96669b72-a198-4e69-adce-ffe4c839c830', NULL, NULL, b'0', '2024-11-07 11:18:06.304000', NULL, 0, 'Ex veritatis quam vel in harum est nisi qui ab sapiente atque molestias.', 'BOOKING_REMINDER', '56a62388-2156-4f4b-a88f-4fe996bd6440', '96669b72-a198-4e69-adce-ffe4c839c830',NULL),
('8c1f8b5d-1e34-44ce-aced-b05030d87dc9', '2024-11-07 11:18:06.298000', '21ff59ff-cd4f-4f67-8886-22aa674d3645', NULL, NULL, b'0', '2024-11-07 11:18:06.298000', NULL, 0, 'Quia ab distinctio ullam beatae commodi nulla iste voluptate occaecati sit distinctio quo.', 'PAYMENT_STATUS_UPDATE', '9dedc088-cc5f-4f8e-b3bb-476730a29c81', '21ff59ff-cd4f-4f67-8886-22aa674d3645',NULL),
('a0cb8135-1655-4f6b-ad98-a4e3e93eabff', '2024-11-07 11:18:06.333000', '0a5b5126-6ae1-4169-aeb3-dcd2f0cb20ad', NULL, NULL, b'0', '2024-11-07 11:18:06.333000', NULL, 0, 'Itaque occaecati qui possimus et cupiditate ipsam occaecati voluptatem aut.', 'PAYMENT_STATUS_UPDATE', '1bf9f5b1-0b68-4c34-a4dd-cae87c96c0b2', '0a5b5126-6ae1-4169-aeb3-dcd2f0cb20ad',NULL),
('a59128c2-3e1b-41d3-ab19-eb1fa0a27575', '2024-11-07 11:18:06.264000', '7662b5d8-51e3-48da-badc-a6009fb8edaa', NULL, NULL, b'0', '2024-11-07 11:18:06.264000', NULL, 0, 'Omnis eveniet quibusdam est voluptatum consectetur est voluptatum officia vero sunt.', 'PAYMENT_REMINDER', '53a6edca-1f76-4328-b18e-7af85920f2f1', '7662b5d8-51e3-48da-badc-a6009fb8edaa',NULL),
('a801b96f-257e-4db4-916c-aa745840dc84', '2024-11-07 11:18:06.259000', '126c7720-3664-45f4-8202-e00bf8b81de6', NULL, NULL, b'0', '2024-11-07 11:18:06.259000', NULL, 0, 'Impedit est harum autem expedita aliquid eum mollitia et excepturi.', 'ORDER_STATUS_UPDATE', 'eaa31eb0-6923-49b2-8535-dff3c0a80a4d', '126c7720-3664-45f4-8202-e00bf8b81de6',NULL),
('a8219d52-e4eb-4a80-8082-d30f11d8cb68', '2024-11-07 11:18:06.325000', 'dd3a7406-14b8-432a-9ac4-152fe76d2df0', NULL, NULL, b'0', '2024-11-07 11:18:06.325000', NULL, 0, 'Sed dignissimos amet nam nulla vero sapiente provident debitis libero dolorem voluptate corporis facere aperiam.', 'BOOKING_REMINDER', 'cfc08892-2416-47e6-8c85-e192d2d61c6f', 'dd3a7406-14b8-432a-9ac4-152fe76d2df0',NULL),
('c99414e4-417b-4708-938c-d055148444eb', '2024-11-07 11:18:06.292000', '126c7720-3664-45f4-8202-e00bf8b81de6', NULL, NULL, b'0', '2024-11-07 11:18:06.292000', NULL, 0, 'Tempore nulla tempora ipsa omnis veniam ullam sit doloribus architecto sunt rerum eos.', 'ORDER_STATUS_UPDATE', 'c378e14a-8d96-4259-a065-37a86a9c0d5f', '126c7720-3664-45f4-8202-e00bf8b81de6',NULL),
('e7b0bbfd-a5b7-4ea4-a3a3-fcbaf005c06f', '2024-11-07 11:18:06.354000', '126c7720-3664-45f4-8202-e00bf8b81de6', NULL, NULL, b'0', '2024-11-07 11:18:06.354000', NULL, 0, 'Et dolorem eius molestiae fuga omnis neque labore vero unde ut.', 'PROMOTION', 'e757fdf7-c12a-4150-b2c6-7fecf2faebbb', '126c7720-3664-45f4-8202-e00bf8b81de6',NULL),
('e9a6600a-60d4-4d4a-83a8-96a89878c79a', '2024-11-07 11:18:06.286000', '384a388e-5be5-4ce1-80ac-db14c91ee478', NULL, NULL, b'0', '2024-11-07 11:18:06.286000', NULL, 0, 'Voluptas nulla distinctio et est maiores quibusdam velit fugiat distinctio corporis asperiores harum autem exercitationem.', 'ORDER_STATUS_UPDATE', '0ccab24c-b432-47d8-b6f1-4e06a0d72795', '384a388e-5be5-4ce1-80ac-db14c91ee478',NULL),
('eaf64502-3b14-45e5-9b5f-340660577a31', '2024-11-07 11:18:06.281000', '126c7720-3664-45f4-8202-e00bf8b81de6', NULL, NULL, b'0', '2024-11-07 11:18:06.281000', NULL, 0, 'Itaque perferendis dolorem ipsam eius et soluta recusandae sit molestiae reiciendis.', 'PAYMENT_STATUS_UPDATE', 'aca9e214-53a9-42ac-9a14-0f6b67c337b8', '126c7720-3664-45f4-8202-e00bf8b81de6',NULL);

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
('03dffb4b-edbc-4015-ab47-a707a8fbf341', '2024-11-07 11:18:05.974000', 'c4bce12e-56fd-4739-b5c8-7e62a047019a', NULL, NULL, b'0', '2024-11-07 11:18:05.974000', NULL, 0, 'CREDIT_CARD', 32.73, 'PENDING', '53a6edca-1f76-4328-b18e-7af85920f2f1'),
('213e202f-a6e2-4044-b76d-82fc4470e3fa', '2024-11-07 11:18:05.982000', 'd3e49ea3-a2c2-4e45-bcb5-10bf5793ac1b', NULL, NULL, b'0', '2024-11-07 11:18:05.982000', NULL, 0, 'VN_PAY', 88.17, 'COMPLETED', '55077964-848c-460a-88f5-b4c71403e04b'),
('2381abc1-1118-4daa-90cc-f7011fae3ecb', '2024-11-07 11:18:06.061000', '4a221dca-8e45-4a32-be17-2db4a063b2ee', NULL, NULL, b'0', '2024-11-07 11:18:06.061000', NULL, 0, 'CREDIT_CARD', 89.1, 'PENDING', 'eaa31eb0-6923-49b2-8535-dff3c0a80a4d'),
('35706914-9c5c-49c1-b288-25b0ef712248', '2024-11-07 11:18:06.006000', '96669b72-a198-4e69-adce-ffe4c839c830', NULL, NULL, b'0', '2024-11-07 11:18:06.006000', NULL, 0, 'CREDIT_CARD', 84.16, 'COMPLETED', '6105d623-1ddf-4617-b285-2fadab6c464a'),
('4fe820ed-fa97-4ca7-b3dc-114a93398559', '2024-11-07 11:18:06.021000', 'dd3a7406-14b8-432a-9ac4-152fe76d2df0', NULL, NULL, b'0', '2024-11-07 11:18:06.021000', NULL, 0, 'DEBIT_CARD', 59.86, 'PENDING', '6d2fe410-4503-486a-93a3-a2a52e6b11e4'),
('7527fb40-b84f-46fa-ab33-e52abc03a213', '2024-11-07 11:18:06.056000', '6d43695c-72f2-48be-9416-5428b986a008', NULL, NULL, b'0', '2024-11-07 11:18:06.056000', NULL, 0, 'VN_PAY', 26.6, 'COMPLETED', 'e757fdf7-c12a-4150-b2c6-7fecf2faebbb'),
('7ee966c5-228d-47c9-a192-3c3afb99fa30', '2024-11-07 11:18:05.966000', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd', NULL, NULL, b'0', '2024-11-07 11:18:05.966000', NULL, 0, 'DEBIT_CARD', 76.24, 'PENDING', '3f0159f7-9ce7-4e96-980e-7de5027ee9a0'),
('7f00c3b9-24e8-4163-965f-b3da816ce5a9', '2024-11-07 11:18:06.014000', '384a388e-5be5-4ce1-80ac-db14c91ee478', NULL, NULL, b'0', '2024-11-07 11:18:06.014000', NULL, 0, 'VN_PAY', 48.6, 'COMPLETED', '6ad06a93-1ccb-4fc9-81b2-2c443b2faf3f'),
('927cfa42-540d-4e0c-bef3-9e767a7c1639', '2024-11-07 11:18:06.052000', 'a2d509d5-724c-419c-a68d-122020217f4c', NULL, NULL, b'0', '2024-11-07 11:18:06.052000', NULL, 0, 'PAYPAL', 57.04, 'PENDING', 'cfc08892-2416-47e6-8c85-e192d2d61c6f'),
('a194b48b-b52c-4202-ac25-4dc9890b3921', '2024-11-07 11:18:06.067000', '9d9b99fe-693d-4383-ab6a-9fa9e86957c5', NULL, NULL, b'0', '2024-11-07 11:18:06.067000', NULL, 0, 'CASH', 47.09, 'PENDING', 'f0136341-0c4f-44dd-93ac-4910d4f50b14'),
('a70133a5-db6a-4522-884d-c73fe59924e7', '2024-11-07 11:18:05.951000', 'a2d509d5-724c-419c-a68d-122020217f4c', NULL, NULL, b'0', '2024-11-07 11:18:05.951000', NULL, 0, 'CREDIT_CARD', 86.51, 'PENDING', '0ccab24c-b432-47d8-b6f1-4e06a0d72795'),
('a7babccb-6811-4fe3-9bad-301243991721', '2024-11-07 11:18:05.943000', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd', NULL, NULL, b'0', '2024-11-07 11:18:05.943000', NULL, 0, 'CREDIT_CARD', 85.25, 'PENDING', '0b7f4e5f-47a4-40c1-8c35-8ed25073523b'),
('ad565492-aebd-4bc0-b527-6fa57c8338ab', '2024-11-07 11:18:06.037000', 'c4bce12e-56fd-4739-b5c8-7e62a047019a', NULL, NULL, b'0', '2024-11-07 11:18:06.037000', NULL, 0, 'CASH', 86.4, 'COMPLETED', 'a24eb51b-45f3-4283-9707-a75cba84fdc7'),
('adcb0667-d933-4139-b26d-68b7502dc160', '2024-11-07 11:18:05.991000', '4b39fcc6-1a05-41af-8504-fea9cba0248a', NULL, NULL, b'0', '2024-11-07 11:18:05.991000', NULL, 0, 'CREDIT_CARD', 37.1, 'COMPLETED', '56a62388-2156-4f4b-a88f-4fe996bd6440'),
('b8142820-5a1c-430b-98ba-69ad310e1303', '2024-11-07 11:18:05.959000', 'dd3a7406-14b8-432a-9ac4-152fe76d2df0', NULL, NULL, b'0', '2024-11-07 11:18:05.959000', NULL, 0, 'CASH', 26.71, 'PENDING', '1bf9f5b1-0b68-4c34-a4dd-cae87c96c0b2'),
('c8737a3e-8a04-46b6-ba04-30649c7d5642', '2024-11-07 11:18:06.047000', '126c7720-3664-45f4-8202-e00bf8b81de6', NULL, NULL, b'0', '2024-11-07 11:18:06.047000', NULL, 0, 'CASH', 68.88, 'COMPLETED', 'c378e14a-8d96-4259-a065-37a86a9c0d5f'),
('e7ef7b00-6a68-4586-bd02-05f102056afa', '2024-11-07 11:18:06.032000', '384a388e-5be5-4ce1-80ac-db14c91ee478', NULL, NULL, b'0', '2024-11-07 11:18:06.032000', NULL, 0, 'VN_PAY', 82.71, 'COMPLETED', '9dedc088-cc5f-4f8e-b3bb-476730a29c81'),
('eacf71b5-7284-4068-87dd-f54d8ef4e795', '2024-11-07 11:18:05.999000', '21ff59ff-cd4f-4f67-8886-22aa674d3645', NULL, NULL, b'0', '2024-11-07 11:18:05.999000', NULL, 0, 'CREDIT_CARD', 83.8, 'PENDING', '5f034663-22da-4974-8f2a-3ce7d0a0e169'),
('eb295bb4-ca4d-44f5-a7c1-02df1a4e79f9', '2024-11-07 11:18:06.026000', '4b39fcc6-1a05-41af-8504-fea9cba0248a', NULL, NULL, b'0', '2024-11-07 11:18:06.026000', NULL, 0, 'PAYPAL', 70.67, 'COMPLETED', '6fc38e7e-e6ba-45fd-95a9-6d065b9184be'),
('fce5ea65-9c46-44ba-a2e0-be98225e6cda', '2024-11-07 11:18:06.041000', '21ff59ff-cd4f-4f67-8886-22aa674d3645', NULL, NULL, b'0', '2024-11-07 11:18:06.041000', NULL, 0, 'VN_PAY', 66.12, 'COMPLETED', 'aca9e214-53a9-42ac-9a14-0f6b67c337b8');

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
(1, '2024-11-07 11:18:06.458000', '4b39fcc6-1a05-41af-8504-fea9cba0248a', NULL, NULL, b'0', '2024-11-07 11:18:06.458000', NULL, 0, 'Pariatur fuga quia iure dolorum voluptatem modi nisi aut distinctio eligendi nisi ut iste est alias deserunt soluta consequuntur quo est adipisci dolorem voluptatum.', 18.41, '2024-11-25', 'AmazingPromotion731202', '2024-10-08', 'ACTIVE'),
(2, '2024-11-07 11:18:06.542000', '6d43695c-72f2-48be-9416-5428b986a008', NULL, NULL, b'0', '2024-11-07 11:18:06.542000', NULL, 0, 'Assumenda ut ipsam qui ipsa ullam voluptas qui dolores aliquid natus repellendus perferendis quibusdam ea sunt ab et et quam.', 7.3, '2024-12-01', 'SweetPromo751980', '2024-10-28', 'INACTIVE'),
(3, '2024-11-07 11:18:06.648000', '50fce098-0ac1-4ac3-9860-4b04e8b9afe7', NULL, NULL, b'0', '2024-11-07 11:18:06.648000', NULL, 0, 'Incidunt nesciunt at laboriosam qui officia est voluptates sed dolor voluptatem mollitia officia laborum quia totam quam est natus sit fugit neque esse ut expedita.', 6.4, '2024-11-12', 'AwesomeSavings641824', '2024-10-23', 'INACTIVE'),
(4, '2024-11-07 11:18:06.728000', '6d43695c-72f2-48be-9416-5428b986a008', NULL, NULL, b'0', '2024-11-07 11:18:06.728000', NULL, 0, 'Saepe et optio vel ex recusandae illum reiciendis voluptatem omnis in eos nemo qui et necessitatibus eos amet voluptas modi ratione et eos eos recusandae.', 22.11, '2024-11-26', 'CoolDeal467546', '2024-10-16', 'ACTIVE'),
(5, '2024-11-07 11:18:06.834000', '2ac09f96-e32a-4aa5-9888-65f005f4a8fc', NULL, NULL, b'0', '2024-11-07 11:18:06.834000', NULL, 0, 'Qui laboriosam repellat eum nam assumenda veritatis numquam qui qui officiis eos voluptatibus voluptatibus sed id alias eveniet omnis accusamus.', 31.07, '2024-11-26', 'SweetPromotion640565', '2024-10-26', 'BANNED'),
(6, '2024-11-07 11:18:06.908000', '4a221dca-8e45-4a32-be17-2db4a063b2ee', NULL, NULL, b'0', '2024-11-07 11:18:06.908000', NULL, 0, 'Quod rerum est ex numquam est sit quam dicta placeat nesciunt enim nisi nobis deserunt unde maxime harum dolor quasi harum mollitia rem.', 36.19, '2024-12-02', 'StellarSavings367788', '2024-10-12', 'BANNED'),
(7, '2024-11-07 11:18:07.020000', '7662b5d8-51e3-48da-badc-a6009fb8edaa', NULL, NULL, b'0', '2024-11-07 11:18:07.020000', NULL, 0, 'Ullam impedit neque officiis ut adipisci laborum ullam aut aut et non consequatur fuga quo voluptas voluptates quae esse quia qui expedita sit iure.', 43.47, '2024-11-11', 'GreatSale212263', '2024-10-13', 'ACTIVE'),
(8, '2024-11-07 11:18:07.095000', '2ac09f96-e32a-4aa5-9888-65f005f4a8fc', NULL, NULL, b'0', '2024-11-07 11:18:07.095000', NULL, 0, 'Porro est quia repellat veniam sed voluptates nihil deleniti natus delectus dolores consequuntur rerum veniam exercitationem dolorem eius eligendi molestias nesciunt dolorem.', 20.6, '2024-12-04', 'AmazingPrice661325', '2024-11-01', 'ACTIVE'),
(9, '2024-11-07 11:18:07.202000', '9d9b99fe-693d-4383-ab6a-9fa9e86957c5', NULL, NULL, b'0', '2024-11-07 11:18:07.202000', NULL, 0, 'Asperiores earum commodi expedita totam sint corrupti veniam dolor autem nihil distinctio ipsa nam rerum earum quis eveniet consequuntur cupiditate earum non dolorum.', 37.84, '2024-11-20', 'SpecialPromo573567', '2024-10-13', 'BANNED'),
(10, '2024-11-07 11:18:07.283000', '126c7720-3664-45f4-8202-e00bf8b81de6', NULL, NULL, b'0', '2024-11-07 11:18:07.283000', NULL, 0, 'Voluptatibus autem vel et molestiae architecto itaque quia sed similique voluptatem molestiae blanditiis eos corporis rem nobis maxime natus voluptatem occaecati ratione sed et laboriosam.', 40.98, '2024-11-15', 'SpecialSavings566526', '2024-10-14', 'INACTIVE');

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
('05d54489-4422-4e27-bb78-6dc73b871b53', '2024-11-07 11:18:04.185000', '7662b5d8-51e3-48da-badc-a6009fb8edaa', NULL, NULL, b'0', '2024-11-07 11:18:04.185000', NULL, 0, 'Voluptas omnis velit doloremque et sit qui alias dolore rerum ratione suscipit minus ut aut labore.', NULL, '8e532147-b8f5-4291-9fef-0729723596c4', '7662b5d8-51e3-48da-badc-a6009fb8edaa'),
('06c8c33c-f65e-4bf2-bec6-61dcafd74752', '2024-11-07 11:18:04.556000', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd', NULL, NULL, b'0', '2024-11-07 11:18:04.556000', NULL, 0, 'Velit sint dicta sed qui architecto iste ipsam eveniet optio vitae inventore explicabo voluptatum et nihil.', '1ec3167a-caba-4683-9749-bcce9e37230f', '3890c5e7-21aa-4ba0-b9c4-5b23cb3c235c', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd'),
('0c45000c-5415-43f4-bad5-9bba7c3ad362', '2024-11-07 11:18:04.176000', '6d43695c-72f2-48be-9416-5428b986a008', NULL, NULL, b'0', '2024-11-07 11:18:04.176000', NULL, 0, 'Dignissimos assumenda repudiandae sapiente et perspiciatis aut dignissimos omnis enim magni fugiat dolorum at hic aliquid quaerat itaque velit.', NULL, '34d49c93-d4ec-4296-8bd4-df561873b422', '6d43695c-72f2-48be-9416-5428b986a008'),
('111af0f3-5a1c-49a7-a42d-61133023c01e', '2024-11-07 11:18:04.060000', '2ac09f96-e32a-4aa5-9888-65f005f4a8fc', NULL, NULL, b'0', '2024-11-07 11:18:04.060000', NULL, 0, 'Et est fugit aut tempora velit porro molestiae laudantium et ea porro possimus dolores asperiores ipsa.', NULL, '1d25affd-83fa-4f5c-96ef-b4e63dc6f0e6', '2ac09f96-e32a-4aa5-9888-65f005f4a8fc'),
('1ec3167a-caba-4683-9749-bcce9e37230f', '2024-11-07 11:18:04.074000', '21ff59ff-cd4f-4f67-8886-22aa674d3645', NULL, NULL, b'0', '2024-11-07 11:18:04.074000', NULL, 0, 'Beatae suscipit laboriosam nesciunt tempora distinctio adipisci iure est qui possimus excepturi qui laborum praesentium consequatur aut mollitia.', NULL, '01686ef7-5932-45dd-a343-d6339543c6d7', '21ff59ff-cd4f-4f67-8886-22aa674d3645'),
('1ff140c4-3f09-4885-8465-c20a926329ee', '2024-11-07 11:18:04.117000', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd', NULL, NULL, b'0', '2024-11-07 11:18:04.117000', NULL, 0, 'Eum quas qui eos quibusdam omnis et sunt repudiandae incidunt minus dolores praesentium sit dicta facilis ducimus temporibus inventore aut.', NULL, '6d104403-0d37-4e35-8a5b-4960285bc1d7', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd'),
('236c6563-0ad1-4c0b-9e60-4dbf95d9cc90', '2024-11-07 11:18:04.036000', '4b39fcc6-1a05-41af-8504-fea9cba0248a', NULL, NULL, b'0', '2024-11-07 11:18:04.036000', NULL, 0, 'Qui dolores qui qui error blanditiis est possimus deserunt fugit maxime delectus fugit molestiae sunt.', NULL, '8a91d4ba-2755-4bd1-a4b5-85f991f80beb', '4b39fcc6-1a05-41af-8504-fea9cba0248a'),
('2e2cbf76-1607-4b30-9738-1a4d71e89f17', '2024-11-07 11:18:04.082000', '126c7720-3664-45f4-8202-e00bf8b81de6', NULL, NULL, b'0', '2024-11-07 11:18:04.082000', NULL, 0, 'Minus aliquid fugit libero reiciendis doloribus consectetur iste maxime soluta repellendus doloribus provident quidem omnis et.', NULL, '65a6b881-286a-4144-b7bf-29d372715d31', '126c7720-3664-45f4-8202-e00bf8b81de6'),
('33a4a3b6-3154-43a4-a19b-35a52d0d8f0d', '2024-11-07 11:18:04.100000', '384a388e-5be5-4ce1-80ac-db14c91ee478', NULL, NULL, b'0', '2024-11-07 11:18:04.100000', NULL, 0, 'Suscipit nisi alias voluptatem ut ut maxime temporibus qui odit molestias ducimus voluptas esse dolor dolores.', NULL, 'f67ddbf3-9780-4149-b0b6-016901179f37', '384a388e-5be5-4ce1-80ac-db14c91ee478'),
('40b8786e-6cda-403d-aa5e-83dc5889f066', '2024-11-07 11:18:04.042000', '9baedc05-08e1-4f12-9735-195827c72845', NULL, NULL, b'0', '2024-11-07 11:18:04.042000', NULL, 0, 'Nihil culpa aut ab debitis nihil sapiente incidunt sit omnis voluptatem sunt ex aliquam sed minus iusto qui quos eius.', NULL, '3f9c6d27-1aca-46ae-b3aa-dd1221a8ad08', '9baedc05-08e1-4f12-9735-195827c72845'),
('5fdeca8a-1a84-44a6-9645-deecd5be0b74', '2024-11-07 11:18:04.124000', 'dd3a7406-14b8-432a-9ac4-152fe76d2df0', NULL, NULL, b'0', '2024-11-07 11:18:04.124000', NULL, 0, 'Iusto nam vel nobis non dolorem et tenetur ratione quam neque corrupti et necessitatibus consequatur aut.', NULL, '8a91d4ba-2755-4bd1-a4b5-85f991f80beb', 'dd3a7406-14b8-432a-9ac4-152fe76d2df0'),
('692b932a-8966-4d35-8ba6-cf6b99d989cf', '2024-11-07 11:18:04.055000', '126c7720-3664-45f4-8202-e00bf8b81de6', NULL, NULL, b'0', '2024-11-07 11:18:04.055000', NULL, 0, 'Minima id maxime ut temporibus ullam laudantium saepe ea minus suscipit alias minus inventore aliquam distinctio eos.', NULL, 'd813f7a0-c6bf-4030-b53b-b7a5b256f083', '126c7720-3664-45f4-8202-e00bf8b81de6'),
('761a955c-6958-4eb5-9048-01d21408deb9', '2024-11-07 11:18:04.141000', '96669b72-a198-4e69-adce-ffe4c839c830', NULL, NULL, b'0', '2024-11-07 11:18:04.141000', NULL, 0, 'Ut vel temporibus minus et voluptas nihil ut quod ut sapiente rerum a eveniet voluptate quis.', NULL, '34d49c93-d4ec-4296-8bd4-df561873b422', '96669b72-a198-4e69-adce-ffe4c839c830'),
('8d4299db-6699-48c1-b873-740d83de218a', '2024-11-07 11:18:04.159000', '0a5b5126-6ae1-4169-aeb3-dcd2f0cb20ad', NULL, NULL, b'0', '2024-11-07 11:18:04.159000', NULL, 0, 'Aut autem expedita laudantium aut porro facere eum voluptatem consectetur voluptas mollitia et ab dicta nisi et.', NULL, '65a6b881-286a-4144-b7bf-29d372715d31', '0a5b5126-6ae1-4169-aeb3-dcd2f0cb20ad'),
('9c7c74c0-09e1-4790-adec-a3f533fd45dc', '2024-11-07 11:18:04.066000', '7662b5d8-51e3-48da-badc-a6009fb8edaa', NULL, NULL, b'0', '2024-11-07 11:18:04.066000', NULL, 0, 'Dicta officia nobis rerum eligendi fugiat adipisci et repellat corporis magnam voluptatum aspernatur consequatur ad quam.', NULL, '55a961aa-b590-4b0c-86f8-6c1db7eb88af', '7662b5d8-51e3-48da-badc-a6009fb8edaa'),
('a4f25eba-92e1-4c87-a058-8c8619aed2b2', '2024-11-07 11:18:04.133000', '9d9b99fe-693d-4383-ab6a-9fa9e86957c5', NULL, NULL, b'0', '2024-11-07 11:18:04.133000', NULL, 0, 'Eveniet nostrum aspernatur accusantium dolor omnis officiis id laborum eveniet sapiente quis cupiditate molestias nobis nostrum eaque dignissimos.', NULL, '5a3b082a-6f6b-4ccc-b22a-9b594517444f', '9d9b99fe-693d-4383-ab6a-9fa9e86957c5'),
('b0314e00-e698-45de-b72f-c32d6fd90ff3', '2024-11-07 11:18:04.424000', 'a2d509d5-724c-419c-a68d-122020217f4c', NULL, NULL, b'0', '2024-11-07 11:18:04.424000', NULL, 0, 'Error ut modi ex exercitationem quis eum minus velit eaque eveniet omnis aut impedit ab.', '1ec3167a-caba-4683-9749-bcce9e37230f', '8e532147-b8f5-4291-9fef-0729723596c4', 'a2d509d5-724c-419c-a68d-122020217f4c'),
('bb1d3e41-1b47-4cdb-ba52-89b0a4c5d1df', '2024-11-07 11:18:04.151000', 'd3e49ea3-a2c2-4e45-bcb5-10bf5793ac1b', NULL, NULL, b'0', '2024-11-07 11:18:04.151000', NULL, 0, 'Qui eos dolore velit quisquam quasi voluptas et expedita voluptas molestiae saepe ea nihil odit rerum.', NULL, '6d104403-0d37-4e35-8a5b-4960285bc1d7', 'd3e49ea3-a2c2-4e45-bcb5-10bf5793ac1b'),
('da26c5d1-8992-4a8b-9a5f-7d3766bee0d8', '2024-11-07 11:18:04.298000', 'bf1aad2f-be18-41bc-a738-ddd8ae96cd8c', NULL, NULL, b'0', '2024-11-07 11:18:04.298000', NULL, 0, 'Quia dolore nemo natus dolore quas nostrum assumenda necessitatibus quibusdam et architecto ea temporibus dignissimos asperiores qui sit modi odit.', 'a4f25eba-92e1-4c87-a058-8c8619aed2b2', '6d104403-0d37-4e35-8a5b-4960285bc1d7', 'bf1aad2f-be18-41bc-a738-ddd8ae96cd8c'),
('dc84d667-4b3a-4926-9424-c9a0eb50c9cc', '2024-11-07 11:18:04.048000', '0a5b5126-6ae1-4169-aeb3-dcd2f0cb20ad', NULL, NULL, b'0', '2024-11-07 11:18:04.048000', NULL, 0, 'Quia qui ut nesciunt sunt totam perspiciatis nemo excepturi vero nihil eos ex maiores et minus dignissimos laboriosam.', NULL, '0769e854-d225-4ebc-8e22-92cc34e0cbb0', '0a5b5126-6ae1-4169-aeb3-dcd2f0cb20ad'),
('f088a833-6aea-4af5-ab0a-7b1ee64bab98', '2024-11-07 11:18:04.108000', 'dd3a7406-14b8-432a-9ac4-152fe76d2df0', NULL, NULL, b'0', '2024-11-07 11:18:04.108000', NULL, 0, 'Sed illo sit illo esse soluta vitae enim dignissimos placeat id rerum et quidem animi ut aut quisquam similique.', NULL, 'f31dcf08-b452-4fca-82d3-b86007612ee8', 'dd3a7406-14b8-432a-9ac4-152fe76d2df0'),
('f76dc8de-40d3-44e8-9dc1-8fc047de1995', '2024-11-07 11:18:04.168000', '384a388e-5be5-4ce1-80ac-db14c91ee478', NULL, NULL, b'0', '2024-11-07 11:18:04.168000', NULL, 0, 'Ipsum impedit nostrum voluptas quos nihil fugit reprehenderit sapiente saepe et at deserunt minima tempora.', NULL, '0769e854-d225-4ebc-8e22-92cc34e0cbb0', '384a388e-5be5-4ce1-80ac-db14c91ee478'),
('f940bea0-7f09-4147-981d-ff4ee7c5be47', '2024-11-07 11:18:04.091000', 'bf1aad2f-be18-41bc-a738-ddd8ae96cd8c', NULL, NULL, b'0', '2024-11-07 11:18:04.091000', NULL, 0, 'Facere sed rerum dolore sunt sint ea quas nemo est libero quia sed eius officiis voluptatem ut consequatur.', NULL, '8e532147-b8f5-4291-9fef-0729723596c4', 'bf1aad2f-be18-41bc-a738-ddd8ae96cd8c');

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
(1, '2024-11-07 11:18:01.004000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:01.004000', NULL, 0, 'CUSTOMER'),
(2, '2024-11-07 11:18:01.033000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:01.033000', NULL, 0, 'FIELD_OWNER'),
(3, '2024-11-07 11:18:01.037000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:01.037000', NULL, 0, 'ADMIN');

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
  `status` enum('CLOSED','INACTIVE','MAINTENANCE','OPEN') NOT NULL,
  `thumbnail` varchar(255) DEFAULT NULL,
  `category_id` int NOT NULL,
  `user_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `sports_fields`
--

INSERT INTO `sports_fields` (`id`, `created_at`, `created_by`, `deleted_at`, `deleted_by`, `is_deleted`, `updated_at`, `updated_by`, `version`, `closing_time`, `location`, `name`, `opacity`, `opening_time`, `rating`, `status`, `thumbnail`, `category_id`, `user_id`) VALUES
('01686ef7-5932-45dd-a343-d6339543c6d7', '2024-11-07 11:18:03.868000', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd', NULL, NULL, b'0', '2024-11-07 11:18:03.868000', NULL, 0, '2028-09-08 14:45:57.714000', '8314 Stuart Overpass, Renaldochester, NE 25977', 'Georgia rabbits', 46, '2024-01-30 11:40:48.167000', 2.38, 'INACTIVE', NULL, 4, 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd'),
('0769e854-d225-4ebc-8e22-92cc34e0cbb0', '2024-11-07 11:18:03.811000', 'a2d509d5-724c-419c-a68d-122020217f4c', NULL, NULL, b'0', '2024-11-07 11:18:03.811000', NULL, 0, '2027-04-12 06:12:53.883000', '4243 Bahringer Center, Leighburgh, WA 31497', 'Iowa spirits', 49, '2023-12-21 22:35:34.068000', 2.28, 'CLOSED', NULL, 1, 'a2d509d5-724c-419c-a68d-122020217f4c'),
('1d25affd-83fa-4f5c-96ef-b4e63dc6f0e6', '2024-11-07 11:18:03.825000', '2ac09f96-e32a-4aa5-9888-65f005f4a8fc', NULL, NULL, b'0', '2024-11-07 11:18:03.825000', NULL, 0, '2025-02-22 09:31:23.404000', 'Apt. 153 5952 Greenfelder Fort, Kathlinefort, WY 75142', 'Washington elephants', 80, '2020-11-18 01:30:54.277000', 1.35, 'OPEN', NULL, 1, '2ac09f96-e32a-4aa5-9888-65f005f4a8fc'),
('34d49c93-d4ec-4296-8bd4-df561873b422', '2024-11-07 11:18:03.906000', '96669b72-a198-4e69-adce-ffe4c839c830', NULL, NULL, b'0', '2024-11-07 11:18:03.906000', NULL, 0, '2026-01-25 21:55:49.656000', '07897 Schultz Inlet, Elianatown, KY 78684', 'Wyoming sorcerors', 20, '2022-12-21 16:11:41.220000', 4.25, 'CLOSED', NULL, 5, '96669b72-a198-4e69-adce-ffe4c839c830'),
('3890c5e7-21aa-4ba0-b9c4-5b23cb3c235c', '2024-11-07 11:18:03.876000', '96669b72-a198-4e69-adce-ffe4c839c830', NULL, NULL, b'0', '2024-11-07 11:18:03.876000', NULL, 0, '2029-02-17 10:56:36.937000', '9356 Tiara Parkway, Kovacekport, HI 26224', 'Hawaii wolves', 26, '2022-05-17 16:57:44.634000', 1.42, 'OPEN', NULL, 4, '96669b72-a198-4e69-adce-ffe4c839c830'),
('3f9c6d27-1aca-46ae-b3aa-dd1221a8ad08', '2024-11-07 11:18:03.852000', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd', NULL, NULL, b'0', '2024-11-07 11:18:03.852000', NULL, 0, '2025-02-06 03:24:52.134000', '04636 Jason Crest, Vaniafurt, RI 68763', 'Tennessee birds', 37, '2021-11-08 00:52:06.789000', 2.06, 'OPEN', NULL, 2, 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd'),
('47e8dfed-658f-41fc-9bce-b4dec2aec8ec', '2024-11-07 11:18:03.887000', '384a388e-5be5-4ce1-80ac-db14c91ee478', NULL, NULL, b'0', '2024-11-07 11:18:03.887000', NULL, 0, '2026-04-08 06:09:58.368000', 'Suite 072 12213 Loura Wall, East Olympia, GA 82570-9793', 'Utah dogs', 28, '2023-10-27 23:27:55.728000', 1.86, 'INACTIVE', NULL, 3, '384a388e-5be5-4ce1-80ac-db14c91ee478'),
('55a961aa-b590-4b0c-86f8-6c1db7eb88af', '2024-11-07 11:18:03.845000', '96669b72-a198-4e69-adce-ffe4c839c830', NULL, NULL, b'0', '2024-11-07 11:18:03.845000', NULL, 0, '2027-11-05 21:20:39.872000', '12813 Ankunding Port, Wymanmouth, VT 40989', 'Iowa vixens', 10, '2020-05-01 19:47:20.339000', 3.98, 'CLOSED', NULL, 4, '96669b72-a198-4e69-adce-ffe4c839c830'),
('5a3b082a-6f6b-4ccc-b22a-9b594517444f', '2024-11-07 11:18:03.791000', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd', NULL, NULL, b'0', '2024-11-07 11:18:03.791000', NULL, 0, '2026-09-03 01:03:11.541000', '9166 Soledad Burgs, East Shelbyview, MS 44989', 'South Dakota chickens', 64, '2020-04-08 09:34:00.396000', 1.63, 'OPEN', NULL, 3, 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd'),
('5c4d53a7-2e4f-4601-915b-7a526681f8b6', '2024-11-07 11:18:03.934000', '21ff59ff-cd4f-4f67-8886-22aa674d3645', NULL, NULL, b'0', '2024-11-07 11:18:03.934000', NULL, 0, '2028-04-24 20:23:21.887000', 'Apt. 974 27531 Nicolas Hill, South Arvillaland, ME 51606', 'Maryland penguins', 29, '2022-07-16 03:56:12.709000', 2.43, 'MAINTENANCE', NULL, 2, '21ff59ff-cd4f-4f67-8886-22aa674d3645'),
('65a6b881-286a-4144-b7bf-29d372715d31', '2024-11-07 11:18:03.915000', '0a5b5126-6ae1-4169-aeb3-dcd2f0cb20ad', NULL, NULL, b'0', '2024-11-07 11:18:03.915000', NULL, 0, '2028-11-21 20:23:46.343000', '40903 Jerrica Ranch, Zulaufport, MT 09651', 'Rhode Island banshees', 53, '2019-11-23 04:05:50.666000', 1.26, 'MAINTENANCE', NULL, 1, '0a5b5126-6ae1-4169-aeb3-dcd2f0cb20ad'),
('6d104403-0d37-4e35-8a5b-4960285bc1d7', '2024-11-07 11:18:03.775000', 'd3e49ea3-a2c2-4e45-bcb5-10bf5793ac1b', NULL, NULL, b'0', '2024-11-07 11:18:03.775000', NULL, 0, '2027-07-21 15:11:18.771000', 'Apt. 646 706 Fabiola Overpass, Port Margheritaberg, LA 98236', 'Tennessee wolves', 82, '2024-08-11 15:09:52.543000', 1.17, 'CLOSED', NULL, 3, 'd3e49ea3-a2c2-4e45-bcb5-10bf5793ac1b'),
('8a91d4ba-2755-4bd1-a4b5-85f991f80beb', '2024-11-07 11:18:03.896000', '2ac09f96-e32a-4aa5-9888-65f005f4a8fc', NULL, NULL, b'0', '2024-11-07 11:18:03.896000', NULL, 0, '2026-01-05 02:47:37.710000', 'Suite 039 937 Loris Estates, Arnulfobury, AR 24522-5551', 'Mississippi sheep', 86, '2023-10-11 19:05:46.574000', 4.17, 'OPEN', NULL, 4, '2ac09f96-e32a-4aa5-9888-65f005f4a8fc'),
('8e532147-b8f5-4291-9fef-0729723596c4', '2024-11-07 11:18:03.805000', '126c7720-3664-45f4-8202-e00bf8b81de6', NULL, NULL, b'0', '2024-11-07 11:18:03.805000', NULL, 0, '2027-07-12 22:57:17.390000', '63011 Langworth Viaduct, Hettingerview, AK 91789', 'Wyoming cattle', 67, '2020-07-04 13:30:31.551000', 0.76, 'INACTIVE', NULL, 4, '126c7720-3664-45f4-8202-e00bf8b81de6'),
('d4ecc945-ea33-471e-8d14-298ccb8d60f6', '2024-11-07 11:18:03.924000', '50fce098-0ac1-4ac3-9860-4b04e8b9afe7', NULL, NULL, b'0', '2024-11-07 11:18:03.924000', NULL, 0, '2026-06-03 14:14:59.358000', 'Suite 277 1905 Turner Overpass, Solport, AL 37107', 'Wyoming spirits', 20, '2020-04-15 01:39:16.288000', 3.64, 'MAINTENANCE', NULL, 1, '50fce098-0ac1-4ac3-9860-4b04e8b9afe7'),
('d813f7a0-c6bf-4030-b53b-b7a5b256f083', '2024-11-07 11:18:03.839000', '384a388e-5be5-4ce1-80ac-db14c91ee478', NULL, NULL, b'0', '2024-11-07 11:18:03.839000', NULL, 0, '2027-07-21 09:22:43.652000', 'Apt. 907 084 Ebert Inlet, Mirnaville, RI 72200-7630', 'Illinois prophets', 86, '2022-06-23 22:43:00.941000', 1.27, 'CLOSED', NULL, 2, '384a388e-5be5-4ce1-80ac-db14c91ee478'),
('f31dcf08-b452-4fca-82d3-b86007612ee8', '2024-11-07 11:18:03.818000', 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd', NULL, NULL, b'0', '2024-11-07 11:18:03.818000', NULL, 0, '2027-09-07 12:45:02.399000', '3056 Kristel Cove, Schadenborough, KY 69042-4929', 'North Dakota penguins', 28, '2023-01-03 06:11:18.980000', 3.2, 'OPEN', NULL, 2, 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd'),
('f67ddbf3-9780-4149-b0b6-016901179f37', '2024-11-07 11:18:03.798000', '96669b72-a198-4e69-adce-ffe4c839c830', NULL, NULL, b'0', '2024-11-07 11:18:03.798000', NULL, 0, '2025-12-02 01:30:46.519000', 'Suite 326 27309 Jerlene Ridge, Arnettaborough, WV 05412', 'Oklahoma gnomes', 21, '2020-09-29 18:40:18.316000', 3.89, 'INACTIVE', NULL, 2, '96669b72-a198-4e69-adce-ffe4c839c830'),
('f99f368f-7d4d-4617-a89e-7c79728a1dff', '2024-11-07 11:18:03.783000', 'a2d509d5-724c-419c-a68d-122020217f4c', NULL, NULL, b'0', '2024-11-07 11:18:03.783000', NULL, 0, '2025-12-29 09:53:04.526000', 'Suite 563 673 Ullrich Terrace, Hickleport, MA 12732', 'Minnesota elves', 78, '2023-02-08 19:11:11.711000', 2.05, 'CLOSED', NULL, 5, 'a2d509d5-724c-419c-a68d-122020217f4c'),
('faa74327-1428-4539-a618-45acf4fa3aee', '2024-11-07 11:18:03.859000', '384a388e-5be5-4ce1-80ac-db14c91ee478', NULL, NULL, b'0', '2024-11-07 11:18:03.859000', NULL, 0, '2026-05-12 05:32:10.024000', '788 Dorie Pass, Tyfurt, HI 40423', 'Texas lions', 36, '2022-11-19 17:00:11.537000', 1.05, 'MAINTENANCE', NULL, 3, '384a388e-5be5-4ce1-80ac-db14c91ee478');

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
(1, '2024-11-07 11:18:07.295000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:07.295000', NULL, 0, 22, '2024-10-16', 20, 14, 5601),
(2, '2024-11-07 11:18:07.301000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:07.301000', NULL, 0, 23, '2024-10-25', 99, 68, 2926),
(3, '2024-11-07 11:18:07.307000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:07.307000', NULL, 0, 10, '2024-10-18', 12, 23, 4341),
(4, '2024-11-07 11:18:07.312000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:07.312000', NULL, 0, 40, '2024-11-06', 86, 17, 3355),
(5, '2024-11-07 11:18:07.319000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:07.319000', NULL, 0, 46, '2024-10-20', 81, 14, 7039),
(6, '2024-11-07 11:18:07.325000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:07.325000', NULL, 0, 34, '2024-10-12', 66, 45, 5218),
(7, '2024-11-07 11:18:07.332000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:07.332000', NULL, 0, 37, '2024-11-05', 41, 55, 8325),
(8, '2024-11-07 11:18:07.340000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:07.340000', NULL, 0, 11, '2024-10-27', 60, 68, 5828),
(9, '2024-11-07 11:18:07.347000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:07.347000', NULL, 0, 45, '2024-10-10', 96, 77, 9356),
(10, '2024-11-07 11:18:07.354000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:07.354000', NULL, 0, 8, '2024-11-01', 47, 21, 5085);

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
  `gender` enum('Female','Male','Other') DEFAULT NULL,
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
('0a5b5126-6ae1-4169-aeb3-dcd2f0cb20ad', '2024-11-07 11:18:01.606000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:01.606000', NULL, 0, 'Id natus dolores optio in eveniet asperiores sed consectetur ut nulla ducimus id similique ut neque minus est possimus est non explicabo.', '2003-11-07', 'daniel.pacocha@yahoo.com', 'Johnathan', 'Female', b'1', 'Bahringer', 'Dr. Davis Murphy Nikolaus', '1318656520', '$2a$10$or9a9eGNjsohmwFBIwpIAeCl.slU5wUfW6.b/Trt2EiCFAnN9hhVa', 'ACTIVE', 'helaine.harber'),
('126c7720-3664-45f4-8202-e00bf8b81de6', '2024-11-07 11:18:02.480000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:02.480000', NULL, 0, 'Ad autem nostrum quis nihil omnis voluptatibus repellendus aut quisquam et ipsa et quos modi ullam exercitationem omnis et reprehenderit.', '2003-11-07', 'vito.krajcik@gmail.com', 'Freddie', 'Male', b'1', 'Altenwerth', 'Dane Raynor D\'Amore', '0993210872', '$2a$10$YW5fiFZCTrKar2BeBxqr0eotNP4vTrAjoXggcLr0aVkOcs/Z.Tenm', 'BANNED', 'lyda.kassulke'),
('21ff59ff-cd4f-4f67-8886-22aa674d3645', '2024-11-07 11:18:02.402000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:02.402000', NULL, 0, 'Dolorem aliquam natus et id in voluptatibus delectus inventore enim veritatis ipsa atque minima id sint debitis dolores eveniet fugiat voluptatem.', '2003-11-07', 'maryjo.hickle@gmail.com', 'Daisey', 'Other', b'1', 'Nader', 'Jeramy Kovacek Padberg', '0692140222', '$2a$10$4XzNdSTxvLoFAx.R2ks8POOC.gKqoASTlXAWqNwLeM7gIl6U.MfaG', 'ACTIVE', 'tawnya.wuckert'),
('2ac09f96-e32a-4aa5-9888-65f005f4a8fc', '2024-11-07 11:18:02.719000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:02.719000', NULL, 0, 'Et veritatis ratione eligendi rerum et nihil sit qui sed tenetur ducimus sed adipisci placeat magni recusandae sint omnis ab.', '2003-11-07', 'warren.stroman@yahoo.com', 'Creola', 'Male', b'1', 'Barton', 'Santiago Schumm Jaskolski', '0182903978', '$2a$10$/l7/whcxiZQD/9fSJlD5Du/8ewr7WQmJssHhpr8UemEpvdKldcol.', 'BANNED', 'tomas.harvey'),
('384a388e-5be5-4ce1-80ac-db14c91ee478', '2024-11-07 11:18:01.765000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:01.765000', NULL, 0, 'Sint facilis vitae qui aperiam est est aliquam quia hic harum assumenda omnis et eius repellat labore quaerat nostrum consectetur voluptates.', '2003-11-07', 'zackary.quitzon@gmail.com', 'Dillon', 'Female', b'1', 'Schiller', 'Alida Greenfelder Kreiger', '5583411908', '$2a$10$CC1W41UL/5t1UeGjsM.xG.g.gDP4MjDouefI0mizJMIbrEzOqN0oK', 'BANNED', 'ali.mraz'),
('4a221dca-8e45-4a32-be17-2db4a063b2ee', '2024-11-07 11:18:02.323000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:02.323000', NULL, 0, 'Voluptas architecto fugiat et laboriosam commodi distinctio quo accusantium inventore optio et accusantium cum doloremque culpa fugiat est sequi odit minima ut beatae sint qui.', '2003-11-07', 'elroy.thompson@hotmail.com', 'Stephan', 'Other', b'1', 'Bednar', 'Arturo O\'Reilly Stamm V', '7165214564', '$2a$10$aLaynbkA4YY/d3crma0aleiKqjiQsDiUiHDtOjLlWZlxVknzJKMmW', 'ACTIVE', 'emilio.schuster'),
('4b39fcc6-1a05-41af-8504-fea9cba0248a', '2024-11-07 11:18:01.366000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:01.366000', NULL, 0, 'Tempore omnis quis odit voluptatem blanditiis ea est voluptas rerum error eligendi asperiores dicta veritatis ut voluptas quo ratione repellendus quo.', '2003-11-07', 'troy.blanda@hotmail.com', 'Pierre', 'Female', b'1', 'Pollich', 'Willy Weber Wuckert', '9530527286', '$2a$10$z5AxSVODbk9t6h0ZktOsR.4W21rJOse/yHLbToByxhsaMz9.bNrnC', 'BANNED', 'ted.blanda'),
('50fce098-0ac1-4ac3-9860-4b04e8b9afe7', '2024-11-07 11:18:01.844000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:01.844000', NULL, 0, 'Ut quis voluptatibus atque rerum velit qui nostrum eum quia cumque cumque minima ut repudiandae veritatis deleniti iste rem itaque necessitatibus eos nihil.', '2003-11-07', 'jonathan.kohler@yahoo.com', 'Taneka', 'Other', b'1', 'Weber', 'Wesley Parker Price', '3757177885', '$2a$10$JR3D5wmfUowO1YFcB4d0We/grUteFzeP55R8qx7eOVIE872IURIkW', 'ACTIVE', 'felipe.marquardt'),
('6d43695c-72f2-48be-9416-5428b986a008', '2024-11-07 11:18:02.240000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:02.240000', NULL, 0, 'Quis itaque maxime modi ea qui eaque non repudiandae omnis hic et eos blanditiis sit aliquid dolores qui placeat et sit deserunt facilis sapiente autem.', '2003-11-07', 'tyree.dubuque@gmail.com', 'Marvin', 'Female', b'1', 'Treutel', 'Reuben Kuhlman Jakubowski', '9640981592', '$2a$10$KD656KpCf6nZxi6pDzl0T.gcA5yuXf5YT9NjxR4E6maz8E3Aetx1C', 'BANNED', 'clyde.runte'),
('7662b5d8-51e3-48da-badc-a6009fb8edaa', '2024-11-07 11:18:02.560000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:02.560000', NULL, 0, 'Odio aut ducimus ipsum quam magni cumque quia nihil facilis saepe autem ullam sint tenetur vero tempora ab veniam amet.', '2003-11-07', 'concetta.kuhic@yahoo.com', 'Shirely', 'Other', b'1', 'Boyer', 'Tad Grant Gutkowski', '5544256056', '$2a$10$3HMkuWPE95ym0x2tLESrhuS1U2umjxMTqUyLetcwPIT0gfcvyWOj2', 'BANNED', 'larisa.runolfsson'),
('96669b72-a198-4e69-adce-ffe4c839c830', '2024-11-07 11:18:01.526000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:01.526000', NULL, 0, 'Voluptate nostrum et sit aut accusantium repellat ipsum autem sequi repudiandae beatae dolorem repudiandae similique quos eos qui dolores deserunt rerum.', '2003-11-07', 'chara.metz@yahoo.com', 'Willia', 'Female', b'1', 'Daniel', 'Mari Cremin Rice', '4652596999', '$2a$10$gbsBc5rQOdFybsAByzRVY.or2xxEU6/uzbKuoJCiRcB9wJQwdEfiK', 'ACTIVE', 'mckenzie.mcglynn'),
('9baedc05-08e1-4f12-9735-195827c72845', '2024-11-07 11:18:01.923000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:01.923000', NULL, 0, 'Ut sed reiciendis nam eos blanditiis rerum inventore officia sit nulla voluptatem quod illo dolorem architecto est earum labore ex.', '2003-11-07', 'thanh.bergnaum@gmail.com', 'Abraham', 'Other', b'1', 'Cummerata', 'Heath Hirthe Muller', '0030528926', '$2a$10$pANeO.zYIVDtm/Nndq1BsOw6u2m.txQXaK7uvkcLtS9VL9lcTyprC', 'BANNED', 'latoyia.schultz'),
('9d9b99fe-693d-4383-ab6a-9fa9e86957c5', '2024-11-07 11:18:01.685000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:01.685000', NULL, 0, 'Voluptatum earum quisquam voluptatibus voluptates non maxime eos sunt omnis cumque voluptatem quo eveniet voluptates laboriosam corporis magnam libero et in tenetur expedita omnis.', '2003-11-07', 'charissa.swaniawski@hotmail.com', 'Cordelia', 'Female', b'1', 'Yost', 'Cherise Hagenes Bruen', '5047313299', '$2a$10$A.9KxfoMR3MhwaKDzDSKwOqROCrK0XGpqml7asTNHfV4/vcu/nbra', 'BANNED', 'norene.schamberger'),
('a2d509d5-724c-419c-a68d-122020217f4c', '2024-11-07 11:18:02.639000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:02.639000', NULL, 0, 'Nemo placeat enim ipsum cumque voluptatibus sed cum nihil voluptatem quis distinctio voluptatibus molestiae et optio amet temporibus dolorem enim sunt eum voluptas.', '2003-11-07', 'taylor.spinka@yahoo.com', 'Raeann', 'Female', b'1', 'Rath', 'Miss Lesha Denesik Leannon', '2117990136', '$2a$10$FjXTlLnysYJk4IIq.dfjx.JA7VJUfwSMfBUfx.tDN0qb6KE7Um2mu', 'BANNED', 'matilda.steuber'),
('bf1aad2f-be18-41bc-a738-ddd8ae96cd8c', '2024-11-07 11:18:02.081000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:02.081000', NULL, 0, 'Officiis tenetur et modi earum voluptas cupiditate ut et voluptatem quia et est iste natus possimus aut cupiditate quidem perferendis fuga ratione libero.', '2003-11-07', 'tatiana.kuphal@yahoo.com', 'Novella', 'Male', b'1', 'Breitenberg', 'Miss Porter O\'Reilly Maggio', '1158736574', '$2a$10$R5/cWLg4wnDVkAxHIEnMXO0bg8mHst5eYnteb092dEECUQr9SApli', 'BANNED', 'karla.bednar'),
('c4bce12e-56fd-4739-b5c8-7e62a047019a', '2024-11-07 11:18:02.160000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:02.160000', NULL, 0, 'Sit optio nobis consequatur dolor rerum quis id doloribus corporis porro ipsum ducimus unde eum vitae assumenda ad fugit et quis et molestiae quis.', '2003-11-07', 'marlen.fadel@gmail.com', 'Jonathon', 'Female', b'1', 'Stracke', 'Miss Tana Beer Pacocha', '9016518065', '$2a$10$4M0L24mEdynrl4y0YxRw/uLN55nGopCPFzlOz1v1pOz7Ric7o7jQq', 'ACTIVE', 'carly.mcdermott'),
('d1b7ea35-88f4-4acd-ac86-90c2bf3a49cd', '2024-11-07 11:18:02.003000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:02.003000', NULL, 0, 'Minima consequatur quam fugit ipsa quis aliquid qui ut quaerat provident asperiores magni nobis optio quisquam autem magni molestiae iusto et.', '2003-11-07', 'andree.prosacco@hotmail.com', 'Efrain', 'Male', b'1', 'Willms', 'Deedra Connelly Morar', '4159195780', '$2a$10$m4.omWsjlTMqvMgOUlCUkerhWwt82a7rQanfspPgdouklbM8fs8xa', 'ACTIVE', 'dillon.mcglynn'),
('d3e49ea3-a2c2-4e45-bcb5-10bf5793ac1b', '2024-11-07 11:18:01.446000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:01.446000', NULL, 0, 'Doloremque assumenda dolorum dolor et ipsum deleniti impedit explicabo culpa animi aut aperiam eos culpa esse reiciendis nam officia numquam dolorem dolorem perferendis aut qui.', '2003-11-07', 'jarrod.jast@gmail.com', 'Jacque', 'Male', b'1', 'Gorczany', 'Ezra Rice Dooley', '2447567517', '$2a$10$GVzV053vF9kmsHigG1ur9O.UTfBEQdpH3FPLKI1MoxyGVIt2Aojeq', 'BANNED', 'georgetta.beier'),
('dd3a7406-14b8-432a-9ac4-152fe76d2df0', '2024-11-07 11:18:01.286000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:01.286000', NULL, 0, 'Adipisci delectus ipsum at eum distinctio ut in ducimus temporibus numquam quas id modi iusto illo quis debitis quia non deserunt totam facere corrupti necessitatibus.', '2003-11-07', 'candace.cole@gmail.com', 'Forrest', 'Other', b'1', 'Anderson', 'Reva King Tromp', '7748667704', '$2a$10$opd72j2BD3pQc6gzz6QY3.hVHBg0Z2WLIgt3H9X5xWd3iefSquwra', 'ACTIVE', 'jon.king'),
('e9655cf0-6f1f-4bd1-b4de-be96fe8c5b2c', '2024-11-07 11:18:01.204000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:01.204000', NULL, 0, 'Atque beatae numquam doloremque et qui quasi aperiam nihil perspiciatis perspiciatis et delectus odit vitae voluptatem quisquam veniam consequatur tempora ab.', '2003-11-07', 'samual.bartoletti@yahoo.com', 'Annita', 'Female', b'1', 'Schiller', 'Piper Corwin Bahringer', '8781755642', '$2a$10$RscMjZOKpEw1OZSCb6DELOfVHHJj/0/kbhjGxoIwsyf5sf8Q/AQSG', 'BANNED', 'estrella.bashirian');

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
(1, '2024-11-07 11:18:02.838000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:02.838000', NULL, 0, 2, '0a5b5126-6ae1-4169-aeb3-dcd2f0cb20ad'),
(2, '2024-11-07 11:18:02.845000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:02.845000', NULL, 0, 2, '126c7720-3664-45f4-8202-e00bf8b81de6'),
(3, '2024-11-07 11:18:02.853000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:02.853000', NULL, 0, 1, '21ff59ff-cd4f-4f67-8886-22aa674d3645'),
(4, '2024-11-07 11:18:02.862000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:02.862000', NULL, 0, 2, '2ac09f96-e32a-4aa5-9888-65f005f4a8fc'),
(5, '2024-11-07 11:18:02.870000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:02.870000', NULL, 0, 2, '384a388e-5be5-4ce1-80ac-db14c91ee478'),
(6, '2024-11-07 11:18:02.881000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:02.881000', NULL, 0, 2, '4a221dca-8e45-4a32-be17-2db4a063b2ee'),
(7, '2024-11-07 11:18:02.890000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:02.890000', NULL, 0, 1, '4b39fcc6-1a05-41af-8504-fea9cba0248a'),
(8, '2024-11-07 11:18:02.899000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:02.899000', NULL, 0, 1, '50fce098-0ac1-4ac3-9860-4b04e8b9afe7'),
(9, '2024-11-07 11:18:02.909000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:02.909000', NULL, 0, 1, '6d43695c-72f2-48be-9416-5428b986a008'),
(10, '2024-11-07 11:18:02.917000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:02.917000', NULL, 0, 2, '7662b5d8-51e3-48da-badc-a6009fb8edaa'),
(11, '2024-11-07 11:18:02.927000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:02.927000', NULL, 0, 1, '96669b72-a198-4e69-adce-ffe4c839c830'),
(12, '2024-11-07 11:18:02.936000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:02.936000', NULL, 0, 1, '9baedc05-08e1-4f12-9735-195827c72845'),
(13, '2024-11-07 11:18:02.944000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:02.944000', NULL, 0, 1, '9d9b99fe-693d-4383-ab6a-9fa9e86957c5'),
(14, '2024-11-07 11:18:02.954000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:02.954000', NULL, 0, 2, 'a2d509d5-724c-419c-a68d-122020217f4c'),
(15, '2024-11-07 11:18:02.961000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:02.961000', NULL, 0, 2, 'bf1aad2f-be18-41bc-a738-ddd8ae96cd8c'),
(16, '2024-11-07 11:18:02.968000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:02.968000', NULL, 0, 2, 'c4bce12e-56fd-4739-b5c8-7e62a047019a'),
(17, '2024-11-07 11:18:02.974000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:02.974000', NULL, 0, 1, 'd1b7ea35-88f4-4acd-ac86-90c2bf3a49cd'),
(18, '2024-11-07 11:18:02.981000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:02.981000', NULL, 0, 2, 'd3e49ea3-a2c2-4e45-bcb5-10bf5793ac1b'),
(19, '2024-11-07 11:18:02.987000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:02.987000', NULL, 0, 2, 'dd3a7406-14b8-432a-9ac4-152fe76d2df0'),
(20, '2024-11-07 11:18:02.993000', 'anonymous', NULL, NULL, b'0', '2024-11-07 11:18:02.993000', NULL, 0, 2, 'e9655cf0-6f1f-4bd1-b4de-be96fe8c5b2c');

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
  ADD KEY `fk_bookings_users` (`user_id`);

--
-- Chỉ mục cho bảng `booking_items`
--
ALTER TABLE `booking_items`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKd81i101irmrgigeics4e5iyx9` (`field_availability_id`),
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
--  ADD UNIQUE KEY `UK47spdbof5die2tuf9wtvev9q` (`object_key`),
--  ADD UNIQUE KEY `UKj37cb0xqyyo6do6ew94sek9rj` (`user_id`),
  ADD KEY `fk_file_metadata_sports_fields` (`sports_field_id`);

--
-- Chỉ mục cho bảng `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_notifications_bookings` (`booking_id`),
  ADD KEY `fk_notifications_users` (`user_id`),
  ADD KEY `fk_notifications_reviews` (`review_id`);
;

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
  ADD CONSTRAINT `fk_bookings_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `booking_items`
--
ALTER TABLE `booking_items`
  ADD CONSTRAINT `fk_booking_items_field_availabilities` FOREIGN KEY (`field_availability_id`) REFERENCES `field_availabilities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
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
  ADD CONSTRAINT `fk_notifications_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_notifications_reviews` FOREIGN KEY (`review_id`) REFERENCES `reviews` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
;

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
