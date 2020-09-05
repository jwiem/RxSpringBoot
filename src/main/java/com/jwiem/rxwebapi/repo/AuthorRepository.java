package com.jwiem.rxwebapi.repo;

import com.jwiem.rxwebapi.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository public interface AuthorRepository extends JpaRepository<Author, String> {

}
