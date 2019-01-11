package study.ian.movie.service;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;
import study.ian.movie.model.movie.Movie;

public interface MovieService {

    @GET("/discover/movie")
    Observable<Movie> getMovie(@Field("api_key") String api_key);

    @GET("/3/discover/movie?api_key=281abc6eb92e0bb92d58167a5f0e5e9a&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1")
    Observable<Movie> getTest(@Query("api_key") String api_key);

    @GET("/3/discover/movie?language=en-US&sort_by=popularity.desc")
    Observable<Movie> getPage(@Query("api_key") String api_key, @Query("page") int page);

}
