/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80037 (8.0.37)
 Source Host           : localhost:3306
 Source Schema         : bms

 Target Server Type    : MySQL
 Target Server Version : 80037 (8.0.37)
 File Encoding         : 65001

 Date: 18/06/2024 21:07:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for vehicles
-- ----------------------------
DROP TABLE IF EXISTS `vehicles`;
CREATE TABLE `vehicles`  (
  `vid` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `car_id` int NOT NULL,
  `battery_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `total_mileage_km` int NOT NULL,
  `battery_health_percentage` int NOT NULL,
  PRIMARY KEY (`vid`) USING BTREE,
  UNIQUE INDEX `unique_car_id_battery_type`(`car_id` ASC, `battery_type` ASC) USING BTREE,
  UNIQUE INDEX `UK5875rr7t47f8sxiv2w4o7iqny`(`car_id` ASC, `battery_type` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vehicles
-- ----------------------------
INSERT INTO `vehicles` VALUES ('G4f6vBn3wQ2Zp8Lr', 2, '铁锂电池', 600, 95);
INSERT INTO `vehicles` VALUES ('P1lT9u4Yr5wGq3Hj', 3, '三元电池', 300, 98);
INSERT INTO `vehicles` VALUES ('x3Yh9Zr8k7MpQ5St', 1, '三元电池', 100, 100);

-- ----------------------------
-- Table structure for warn
-- ----------------------------
DROP TABLE IF EXISTS `warn`;
CREATE TABLE `warn`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `warn_id` int NOT NULL,
  `warn_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `battery_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `warn_rule` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of warn
-- ----------------------------
INSERT INTO `warn` VALUES (1, 1, '电压差报警', '三元电池', '5<=(Mx-Mi),报警等级：0\n3<=(Mx-Mi)<5,报警等级：1\n1<=(Mx-Mi)<3,报警等级：2\n0.6<=(Mx-Mi)<1,报警等级：3\n0.2<=(Mx-Mi)<0.6,报警等级：4\n(Mx-Mi)<0.2,不报警');
INSERT INTO `warn` VALUES (2, 1, '电压差报警', '铁锂电池', '2<=(Mx-Mi),报警等级：0\n1<=(Mx-Mi)<2,报警等级：1\n0.7<=(Mx-Mi)<1,报警等级：2\n0.4<=(Mx-Mi)<0.7,报警等级：3\n0.2<=(Mx-Mi)<0.4,报警等级：4\n(Mx-Mi)<0.2,不报警');
INSERT INTO `warn` VALUES (3, 2, '电流差报警', '三元电池', '3<=(Ix-Ii),报警等级：0\n1<=(Ix-Ii)<3,报警等级：1\n0.2<=(Ix-Ii)<1,报警等级：2\n(Ix-Ii)<0.2,不报警');
INSERT INTO `warn` VALUES (4, 2, '电流差报警', '铁锂电池', '1<=(Ix-Ii),报警等级：0\n0.5<=(Ix-Ii)<1,报警等级：1\n0.2<=(Ix-Ii)<0.5,报警等级：2\n(Ix-Ii)<0.2,不报警');

SET FOREIGN_KEY_CHECKS = 1;
