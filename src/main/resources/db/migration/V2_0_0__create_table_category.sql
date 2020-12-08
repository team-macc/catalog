CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `catalog_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5wdwj8exshk4qeqk7puil67o6` (`catalog_id`),
  CONSTRAINT `FK5wdwj8exshk4qeqk7puil67o6` FOREIGN KEY (`catalog_id`) REFERENCES `catalog` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;