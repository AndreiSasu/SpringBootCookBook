package org.test.bookpub.mainapp;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.test.bookpub.dbcount.DbCountRunner;
import org.test.bookpub.dbcount.EnableDbCounting;

@SpringBootApplication
@EnableScheduling
@EnableDbCounting
public class BookPubApplication {
	protected final Log logger = LogFactory.getLog(getClass());

	public static void main(String[] args) {
		SpringApplication.run(BookPubApplication.class, args);
	}

	@Bean
	public StartupRunner schedulerRunner() {
		return new StartupRunner();
	}

//	@Bean //DbCountRunner implementation from db-count-starter module will not start in this case!
//	public DbCountRunner dbCountRunner(Collection<CrudRepository> repositories) {
//		return new DbCountRunner(repositories) {
//			@Override
//			public void run(String... args) throws Exception {
//				logger.info("Manually Declared DbCountRunner");
//			}
//		};
//	}

}
