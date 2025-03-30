package com.songshilong.starter.mail.core;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.starter.mail.core
 * @Author: Ice, Song
 * @CreateTime: 2025-03-28  23:25
 * @Description: TODO
 * @Version: 1.0
 */
public interface EmailService {

    Boolean send(String targetEmailAddress, String mailSubject, String mailContent);


}
