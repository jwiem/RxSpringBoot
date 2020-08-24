package com.jwiem.rxwebapi.service.book;

import com.jwiem.rxwebapi.api.request.AddBookRequest;
import com.jwiem.rxwebapi.api.request.UpdateBookRequest;
import com.jwiem.rxwebapi.api.response.BookResponse;
import io.reactivex.Completable;
import io.reactivex.Single;

import java.util.List;

public interface BookService {

  Single<String> addBook(AddBookRequest addBookRequest);

  Completable updateBook(UpdateBookRequest updateBookRequest);

  Single<List<BookResponse>> getAllBooks(int limit, int page);

  Single<BookResponse> getBookDetail(String id);

  Completable deleteBook(String id);
}
