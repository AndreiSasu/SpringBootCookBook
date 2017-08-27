package org.test.bookpub.dbcount;

import java.util.Collection;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;

@Configuration
public class DbCountAutoConfiguration {
	@Bean
//	@ConditionalOnMissingBean // will not start if one Bean like this is configured in the main app.
	public DbCountRunner dbCountRunner(Collection<CrudRepository> repositories) {
		return new DbCountRunner(repositories);
	}
}
