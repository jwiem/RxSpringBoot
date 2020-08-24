package com.jwiem.rxwebapi.controllers;


import com.jwiem.rxwebapi.api.request.AddAuthorRequest;
import com.jwiem.rxwebapi.api.response.Response;
import com.jwiem.rxwebapi.service.author.AuthorService;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping(value = "/api/authors")
public class AuthorController {

  @Autowired private AuthorService authorService;

  @PostMapping(
          consumes = APPLICATION_JSON_VALUE,
          produces = APPLICATION_JSON_VALUE
  )
  public Single<ResponseEntity<Response>> addAuthor(@RequestBody AddAuthorRequest addAuthorRequest) {
    return authorService.addAuthor(addAuthorRequest)
            .subscribeOn(Schedulers.io())
            .map(s -> ResponseEntity
                    .created(URI.create("/api/authors/" + s))
                    .body(Response.successNoData()));
  }

}
