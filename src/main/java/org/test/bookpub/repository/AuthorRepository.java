package org.test.bookpub.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.test.bookpub.entity.Author;

@RepositoryRestResource //provided by  spring-boot-starter-data-rest, generates controller endpoints automatically
public interface AuthorRepository extends PagingAndSortingRepository<Author, Long> {
}
