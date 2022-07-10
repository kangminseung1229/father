package com.example.father.msk.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Jasypt {
    private final static String KEY = "kang"; // 암복호화에 사용할 키
    private final static String ALGORITHM = "PBEWithMD5AndDES"; // 알고리즘 (고정)
    private final static String CNT = "1000";
    private final static String POOL_SIZE = "1";
    private final static String BASE64 = "base64";

    
    @Bean(name="jasyptStringEncryptor")
    public StringEncryptor stringEncryptor(){

        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(KEY);
        config.setAlgorithm(ALGORITHM);
        config.setKeyObtentionIterations(CNT); // 반복할 해싱 횟수
        config.setPoolSize(POOL_SIZE);
        config.setStringOutputType(BASE64);

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(config);

        return encryptor;
    }

}
