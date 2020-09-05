package com.jwiem.rxwebapi.service;

import com.jwiem.rxwebapi.api.GithubApi;
import com.jwiem.rxwebapi.models.Contributor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubService {

    private GithubApi githubApi;

    public GithubService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        githubApi = retrofit.create(GithubApi.class);
    }

    // TODO! Observable implementation changes with RxJava 2
    rx.Observable<String> getTopContributors(String userName) {
        return githubApi.listRepos(userName)
                .flatMapIterable(x -> x).flatMap(repo -> githubApi.listRepoContributors(userName, repo.getName()))
                .flatMapIterable(x -> x).filter(c -> c.getContributions() > 100)
                .sorted((a, b) -> b.getContributions() - a.getContributions()).map(Contributor::getName).distinct();
    }
}

