package com.jwiem.rxwebapi.api;


import com.jwiem.rxwebapi.models.Contributor;
import com.jwiem.rxwebapi.models.Repository;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

import java.util.List;

public interface GithubApi {

    @GET("users/{user}/repos")
    Observable<List<Repository>> listRepos(@Path("user") String user);

    @GET("repos/{user}/{repo}/contributors")
    Observable<List<Contributor>> listRepoContributors(@Path("user") String user, @Path("repo") String repo);

}
