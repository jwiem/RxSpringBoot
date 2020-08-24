package com.jwiem.rxwebapi.service;

import com.jwiem.rxwebapi.api.request.AddAuthorRequest;
import com.jwiem.rxwebapi.entity.Author;
import com.jwiem.rxwebapi.repo.AuthorRepository;
import com.jwiem.rxwebapi.service.author.AuthorServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthorServiceImplTest {

  @Mock private AuthorRepository authorRepository;
  @InjectMocks private AuthorServiceImpl authorService;

  @Before public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test public void AddAuthor_Success_ReturnSingleOfAddedAuthorId() {
    when(authorRepository.save(any(Author.class)))
            .thenReturn(new Author("1", "Axell"));

    authorService.addAuthor(new AddAuthorRequest("1"))
            .test()
            .assertComplete()
            .assertNoErrors()
            .awaitTerminalEvent();

    verify(authorRepository, times(1)).save(any(Author.class));
  }
}