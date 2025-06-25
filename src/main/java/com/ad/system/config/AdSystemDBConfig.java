package com.ad.system.config;

import com.ad.system.exception.AdSystemException;
import com.ad.system.exception.ErrorCode;
import com.ad.system.exception.ErrorSeverity;
import com.ad.system.utls.AdConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class AdSystemDBConfig {
    @Bean
    DataSource dataSource() {
        String dbUser = AdConstant.DB_USER_NAME;
        String dbPassword = AdConstant.DB_PASSWORD;
        String driverClassName = AdConstant.DB_DRIVER_CLASS_NAME;
        DriverManagerDataSource ds = new DriverManagerDataSource(getDBUrl(), dbUser, dbPassword);
        try {
            ds.setDriverClassName(driverClassName);
        } catch (Exception e) {
            throw new AdSystemException(ErrorCode.ERR002.getErrorCode(), ErrorSeverity.FATAL,
                    ErrorCode.ERR002.getErrorMessage(), e);
        }
        try {
            ds.getConnection().close();
        } catch (SQLException e) {
            throw new AdSystemException(ErrorCode.ERR002.getErrorCode(), ErrorSeverity.FATAL,
                    ErrorCode.ERR002.getErrorMessage(), e);
        }
        return ds;
    }

    private String getDBUrl() {
        String dbHost = AdConstant.DB_HOST;
        String dbPort = AdConstant.DB_PORT;
        String dbName = AdConstant.DB_NAME;
        String dbUrlPrefix = AdConstant.DB_URL_PREFIX;
        //		baseUrl.append(EMPConstant.COLON);
        return dbUrlPrefix + dbHost +
                AdConstant.COLON +
                dbPort +
                dbName;
    }
}
