-- 测试用户数据（仅供本地开发/演示用，生产环境请删除）
-- 注：因为 chemical 库中没有 t_user 表（用户管理在另一个微服务），
-- 这里只是占位说明：前端 fixedUserInfo 写死的 userId=12345，
-- 此 ID 在 chemical 库中无对应记录，但不影响业务功能（业务表只用 userId 做隔离）。
-- 师弟如果接入了真实的用户系统，可以删除前端的写死逻辑。

-- 如有需要，可以插入一些测试数据示例：
-- INSERT INTO t_classify_single (user_id, reaction_smiles, reaction_class, predict_model, create_time)
-- VALUES (12345, 'CC>>CC', '示例反应类别', '系统默认10大类模型', NOW());
