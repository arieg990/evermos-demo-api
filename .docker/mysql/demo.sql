/*
 Navicat Premium Data Transfer

 Source Server         : demo local
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : localhost:13307
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 07/11/2021 01:37:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order_product_snapshots
-- ----------------------------
DROP TABLE IF EXISTS `order_product_snapshots`;
CREATE TABLE `order_product_snapshots`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `product` json NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `order_id_idx`(`order_id`) USING BTREE,
  CONSTRAINT `order_id_idx` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_product_snapshots
-- ----------------------------

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `price` decimal(12, 2) NOT NULL,
  `qty` int(11) NOT NULL,
  `order_number` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `product_id_idx`(`product_id`) USING BTREE,
  CONSTRAINT `product_id_idx` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------

-- ----------------------------
-- Table structure for products
-- ----------------------------
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `description` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `price` decimal(12, 2) NULL DEFAULT NULL,
  `qty` int(11) NULL DEFAULT NULL,
  `reserved_qty` int(11) NULL DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of products
-- ----------------------------
INSERT INTO `products` VALUES (1, 'Kerudung panjang', 'Kerudung panjang merek ternama', 100000.00, 100, 0, '2021-11-06 05:45:45', '2021-11-07 01:07:11');
INSERT INTO `products` VALUES (2, 'Sarung BHS', 'Sarung dengan kualitas terbaik', 50000.00, 0, 0, '2021-11-06 18:09:41', '2021-11-06 18:09:41');
INSERT INTO `products` VALUES (3, 'Jam Rolex', 'Jam limited Edition', 5000000.00, 5, 0, '2021-11-06 18:10:10', '2021-11-06 18:10:10');

SET FOREIGN_KEY_CHECKS = 1;
