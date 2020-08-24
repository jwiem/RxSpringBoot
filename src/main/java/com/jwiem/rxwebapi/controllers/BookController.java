package com.jwiem.rxwebapi.controllers;


import com.jwiem.rxwebapi.api.request.AddBookRequest;
import com.jwiem.rxwebapi.api.response.BookResponse;
import com.jwiem.rxwebapi.api.response.Response;
import com.jwiem.rxwebapi.service.book.BookService;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/books")
public class BookController {

  @Autowired
  private BookService bookService;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Single<ResponseEntity<Response>> addBook(@RequestBody AddBookRequest addBookRequest) {
    return bookService.addBook(addBookRequest).subscribeOn(Schedulers.io()).map(
            s -> ResponseEntity.created(URI.create("/api/books/" + s))
                    .body(Response.successNoData()));
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Single<ResponseEntity<Response<List<BookResponse>>>> getAllBooks(@RequestParam(value = "limit", defaultValue = "5") int limit,
                                                                          @RequestParam(value = "page", defaultValue = "0") int page) {
    return bookService.getAllBooks(limit, page)
            .subscribeOn(Schedulers.io())
            .map(bookResponses -> ResponseEntity.ok(Response.successWithData(bookResponses)));
  }

  @GetMapping(value = "/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Single<ResponseEntity<Response<BookResponse>>> getBookDetail(@PathVariable(value = "bookId") String bookId) {

    return bookService.getBookDetail(bookId)
            .subscribeOn(Schedulers.io())
            .map(bookResponse -> ResponseEntity.ok(Response.successWithData(bookResponse)));
  }

  @DeleteMapping(value = "/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Single<ResponseEntity<Response>> deleteBook(@PathVariable(value = "bookId") String bookId) {
    return bookService.deleteBook(bookId)
            .subscribeOn(Schedulers.io())
            .toSingle(() -> ResponseEntity.ok(Response.successNoData()));
  }

}
