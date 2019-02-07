package study.ian.movie.service;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import study.ian.movie.model.people.movie.credit.Credit;
import study.ian.movie.model.people.popular.Popular;

public interface PeopleService {

    String KEY_ID = "PEOPLE_KEY_ID";

    @GET("/3/movie/{id}/credits")
    Observable<Credit> getMovieCredit(
            @Path("id") int id,
            @Query("api_key") String api_key
    );

    @GET("/3/tv/{id}/credits")
    Observable<study.ian.movie.model.people.tv.credit.Credit> getTvCredit(
            @Path("id") int id,
            @Query("api_key") String api_key,
            @Query("language") String language
    );

    @GET("/3/person/popular")
    Observable<Popular> getPopular(
            @Query("api_key") String api_key,
            @Query("page") int page,
            @Query("language") String language
    );
}
