package com.example.father.msk.config;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JasyptMaking {

    private final static String KEY = "kang"; // 암복호화에 사용할 키
    private final static String ALGORITHM = "PBEWithMD5AndDES"; // 알고리즘 (고정)
    private final static String CNT = "1000";
    private final static String POOL_SIZE = "1";
    private final static String BASE64 = "base64";

    @DisplayName("properties 관련 자료 암호화 값 생성")
    @Test
    public void properties_jasypt(){

        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(KEY);
        config.setAlgorithm(ALGORITHM);
        config.setKeyObtentionIterations(CNT); // 반복할 해싱 횟수
        config.setPoolSize(POOL_SIZE);
        config.setStringOutputType(BASE64);

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(config);


        String URL, USERNAME, PASSWORD;

        URL = encryptor.encrypt("jdbc:mariadb://maria.taxbox.ai:3306/SpringToyProject?serverTimezone=UTC");
        USERNAME = encryptor.encrypt("msk");
        PASSWORD = encryptor.encrypt("msk2021!");

        System.out.println("--------------------------------------------------------------");
        System.err.println("URL : " + URL);
        System.err.println("USERNAME : " + USERNAME);
        System.err.println("PASSWORD : " + PASSWORD);
        System.out.println("--------------------------------------------------------------");


    }
    
}
