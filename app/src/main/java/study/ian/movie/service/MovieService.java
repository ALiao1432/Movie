package study.ian.movie.service;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import study.ian.movie.model.detail.Detail;
import study.ian.movie.model.movie.Movie;

public interface MovieService {

    String KEY_ID = "KEY_ID";

    @GET("/3/discover/movie?language=en-US")
    Observable<Movie> getPage(
            @Query("api_key") String api_key,
            @Query("sort_by") String sort_by,
            @Query("page") int page,
            @Query("include_adult") boolean include_adult,
            @Query("include_video") boolean include_video
    );

    @GET("/3/movie/{id}?language=en-US")
    Observable<Detail> getDetail(
            @Path("id") int id,
            @Query("api_key") String api_key
    );
}
