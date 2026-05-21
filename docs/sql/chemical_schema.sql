
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
DROP TABLE IF EXISTS `t_classify_single`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_classify_single` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `reaction_smiles` varchar(256) DEFAULT NULL COMMENT '反应smiles',
  `reaction_class` varchar(256) DEFAULT NULL COMMENT '反应类型名称',
  `predict_model` varchar(256) DEFAULT NULL COMMENT '预测模型名称',
  `custom_model` tinyint(1) DEFAULT NULL COMMENT '是否为用户上传模型 0: 用户上传，1: 使用系统模型',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '软删除标记：0未删除 1已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `reaction_class` (`reaction_class`),
  KEY `idx_user_create` (`user_id`,`create_time` DESC)
) ENGINE=InnoDB AUTO_INCREMENT=2056931074377805827 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='分类预测单个表';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `t_classify_batch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_classify_batch` (
  `batch_id` bigint NOT NULL AUTO_INCREMENT COMMENT '批次任务ID',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `task_name` varchar(256) DEFAULT NULL COMMENT '任务名称，取Excel名',
  `predict_model` varchar(256) DEFAULT NULL COMMENT '使用的预测模型名称',
  `custom_model` tinyint(1) DEFAULT NULL COMMENT '是否为用户上传模型（0: 用户上传，1: 使用系统模型）',
  `excel_download` varchar(512) DEFAULT NULL COMMENT '预测结果下载地址',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `job_id` varchar(255) DEFAULT NULL COMMENT 'mq发送任务id',
  `excel_upload` varchar(255) DEFAULT NULL COMMENT '用户上传的excel文件',
  `model_upload` varchar(512) DEFAULT NULL COMMENT '自定义模型文件地址',
  `status` varchar(20) DEFAULT NULL COMMENT '任务状态',
  `remark` text,
  `update_time` datetime DEFAULT (now()) COMMENT '更新时间',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '软删除标记：0未删除 1已删除',
  PRIMARY KEY (`batch_id`),
  UNIQUE KEY `uk_job_id` (`job_id`),
  KEY `task_name` (`task_name`),
  KEY `idx_user_create` (`user_id`,`create_time` DESC)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='分类预测批次任务表';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `t_yield_single`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_yield_single` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `reaction_smiles` varchar(256) DEFAULT NULL COMMENT '反应smiles',
  `reaction_yield` varchar(256) DEFAULT NULL COMMENT '产率',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_reaction_smiles` (`reaction_smiles`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='产物产率单条预测';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `t_yield_batch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_yield_batch` (
  `batch_id` bigint NOT NULL AUTO_INCREMENT COMMENT '批次任务ID',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `task_name` varchar(256) DEFAULT NULL COMMENT '任务名称，取Excel名',
  `excel_download` varchar(512) DEFAULT NULL COMMENT '预测结果下载地址',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`batch_id`),
  KEY `task_name` (`task_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='产率预测批次任务表';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

