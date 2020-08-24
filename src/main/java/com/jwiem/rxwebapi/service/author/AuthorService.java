package com.jwiem.rxwebapi.service.author;

import com.jwiem.rxwebapi.api.request.AddAuthorRequest;
import io.reactivex.Single;

public interface AuthorService {
  Single<String> addAuthor(AddAuthorRequest addAuthorRequest);
}
