package com.jwiem.rxwebapi.retrofit;

import static org.assertj.core.api.Assertions.assertThat;

import com.jwiem.rxwebapi.models.Contributor;
import com.jwiem.rxwebapi.models.Repository;
import org.junit.Before;
import org.junit.Test;

import com.jwiem.rxwebapi.api.GithubApi;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubApiTest {

    private GithubApi gitHub;

    @Before public void init() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        gitHub = retrofit.create(GithubApi.class);
    }

    @Test public void whenListRepos_thenExpectReposThatContainTutorials() {
        gitHub.listRepos("eugenp").subscribe(repos -> {
            assertThat(repos).isNotEmpty().extracting(Repository::getName).contains("tutorials");
        });
    }

    @Test public void whenListRepoContributers_thenExpectContributorsThatContainEugenp() {
        gitHub.listRepoContributors("eugenp", "tutorials").subscribe(contributors -> {
            assertThat(contributors).isNotEmpty().extracting(Contributor::getName).contains("eugenp");
        });
    }


}
