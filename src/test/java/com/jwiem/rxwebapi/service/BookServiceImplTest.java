package com.jwiem.rxwebapi.service;

import com.jwiem.rxwebapi.api.request.AddBookRequest;
import com.jwiem.rxwebapi.api.request.UpdateBookRequest;
import com.jwiem.rxwebapi.api.response.BookResponse;
import com.jwiem.rxwebapi.models.Author;
import com.jwiem.rxwebapi.models.Book;
import com.jwiem.rxwebapi.repo.AuthorRepository;
import com.jwiem.rxwebapi.repo.BookRepository;
import com.jwiem.rxwebapi.service.book.BookServiceImpl;
import io.reactivex.observers.TestObserver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookServiceImplTest {

  @Mock private BookRepository bookRepository;
  @Mock private AuthorRepository authorRepository;
  @InjectMocks private BookServiceImpl bookService;

  @Before public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test public void addBookSuccess() {
    when(authorRepository.findById(anyString()))
            .thenReturn(Optional.of(new Author("1", "1")));
    when(bookRepository.save(any(Book.class)))
            .thenReturn(new Book("1", "1", new Author()));

    bookService.addBook(new AddBookRequest("1", "1"))
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue("1")
            .awaitTerminalEvent();

    InOrder inOrder = inOrder(authorRepository, bookRepository);
    inOrder.verify(authorRepository, times(1)).findById(anyString());
    inOrder.verify(bookRepository, times(1)).save(any(Book.class));
  }

  // Should throw an entityNotFoundException when attempting to add a book
  @Test public void addBookException() {
    when(authorRepository.findById(anyString()))
            .thenReturn(Optional.empty());

    bookService.addBook(new AddBookRequest("1", "1"))
            .test()
            .assertNotComplete()
            .assertError(EntityNotFoundException.class)
            .awaitTerminalEvent();

    InOrder inOrder = inOrder(authorRepository, bookRepository);
    inOrder.verify(authorRepository, times(1)).findById(anyString());
    inOrder.verify(bookRepository, never()).save(any(Book.class));
  }

  @Test public void updateBookSuccess() {
    when(bookRepository.findById(anyString()))
            .thenReturn(Optional.of(new Book("1", "1", new Author())));
    when(bookRepository.save(any(Book.class)))
            .thenReturn(new Book("1", "1", new Author()));

    bookService.updateBook(new UpdateBookRequest("1", "1"))
            .test()
            .assertComplete()
            .assertNoErrors()
            .awaitTerminalEvent();

    InOrder inOrder = inOrder(bookRepository);
    inOrder.verify(bookRepository, times(1)).findById(anyString());
    inOrder.verify(bookRepository, times(1)).save(any(Book.class));
  }

  // Should throw an EntityNotFoundException when attempting to update the book.
  @Test public void updateBookException() {
    when(bookRepository.findById(anyString()))
            .thenReturn(Optional.empty());

    bookService.updateBook(new UpdateBookRequest("1", "1"))
            .test()
            .assertNotComplete()
            .assertError(EntityNotFoundException.class)
            .awaitTerminalEvent();

    InOrder inOrder = inOrder(bookRepository);
    inOrder.verify(bookRepository, times(1)).findById(anyString());
    inOrder.verify(bookRepository, never()).save(any(Book.class));
  }

  @Test public void returnSingleBookResponseList() {
    Book book1 = new Book("1", "1", new Author());
    Book book2 = new Book("2", "2", new Author());

    when(bookRepository.findAll(any(PageRequest.class)))
            .thenReturn(new PageImpl<>(
                    Arrays.asList(book1, book2)));

    TestObserver<List<BookResponse>> testObserver = bookService.getAllBooks(1, 1).test();

    testObserver.awaitTerminalEvent();

    testObserver.assertValue(bookResponses -> bookResponses.get(0).getId().equals("1") && bookResponses.get(1).getId().equals("2"));

    verify(bookRepository, times(1)).findAll(any(PageRequest.class));
  }

  @Test public void returnSingleBookResponse() {
    Book book1 = new Book("1", "1", new Author("1", "1"));

    when(bookRepository.findById(anyString())).thenReturn(Optional.of(book1));

    TestObserver<BookResponse> testObserver = bookService.getBookDetail("1").test();

    testObserver.awaitTerminalEvent();
    testObserver.assertValue(bookResponse -> bookResponse.getId().equals("1"));

    verify(bookRepository, times(1)).findById(anyString());
  }

  @Test public void getBookDetailException() {
    when(bookRepository.findById(anyString())).thenReturn(Optional.empty());

    bookService.getBookDetail("1")
            .test()
            .assertNotComplete()
            .assertError(EntityNotFoundException.class)
            .awaitTerminalEvent();

    verify(bookRepository, times(1)).findById(anyString());
  }

  @Test public void returnCompleteableSuccess() {
    when(bookRepository.findById(anyString()))
            .thenReturn(Optional.of(new Book("1", "1", new Author())));

    doNothing().when(bookRepository).delete(any(Book.class));

    bookService.deleteBook("1")
            .test()
            .assertComplete()
            .assertNoErrors()
            .awaitTerminalEvent();

    InOrder inOrder = inOrder(bookRepository);
    inOrder.verify(bookRepository, times(1)).findById(anyString());
    inOrder.verify(bookRepository, times(1)).delete(any(Book.class));
  }

  @Test public void deleteBookException() {
    when(bookRepository.findById(anyString()))
            .thenReturn(Optional.empty());

    bookService.deleteBook("1")
            .test()
            .assertNotComplete()
            .assertError(EntityNotFoundException.class)
            .awaitTerminalEvent();

    InOrder inOrder = inOrder(bookRepository);
    inOrder.verify(bookRepository, times(1)).findById(anyString());
    inOrder.verify(bookRepository, never()).delete(any(Book.class));
  }
}

