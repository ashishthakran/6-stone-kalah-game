package com.backbase.kalah.data.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan(value = {"com.backbase.kalah.data.entity"})
@EnableJpaRepositories(value = {"com.backbase.kalah.data.dao"})
public class KalahDataRepositoryConfiguration {
}
