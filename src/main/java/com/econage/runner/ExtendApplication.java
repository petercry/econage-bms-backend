package com.econage.runner;

import com.econage.core.basic.i18n.I18nMsgFoldersAware;
import com.econage.core.basic.runner.ApplicationRunnerBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class ExtendApplication extends ApplicationRunnerBase {
    private static final Logger logger = LoggerFactory.getLogger(ExtendApplication.class);

    public static void main(String[] args) throws Exception {
        try{
            ApplicationRunnerBase.runApplication(ExtendApplication.class,args);
        }catch(Throwable t){
            logger.error(t.getMessage(),t);
            throw t;
        }
    }

    @Bean
    public I18nMsgFoldersAware i18nMsgFoldersAwareExtend()
    {
        return I18nMsgFoldersAware.builder()
                .msgFolders( List.of("i18n-core","i18n-base","i18n","i18n-extend") )
                .build() ;
    }
}
