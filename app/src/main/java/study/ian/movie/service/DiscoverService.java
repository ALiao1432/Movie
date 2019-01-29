package study.ian.movie.service;

import android.support.annotation.Nullable;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import study.ian.movie.model.discover.Genre;
import study.ian.movie.model.movie.Movie;

public interface DiscoverService {

    @GET("/3/genre/movie/list?language=en-US")
    Observable<Genre> getMovieGenre(
            @Query("api_key") String api_key
    );

    @GET("/3/genre/tv/list?language=en-US")
    Observable<Genre> getTvGenre(
            @Query("api_key") String api_key
    );

    @GET("/3/search/movie?language=en-US&include_adult=true")
    Observable<Movie> searchMovie(
            @Query("api_key") String api_key,
            @Query("query") String query,
            @Nullable @Query("primary_release_year") Integer primary_release_year
    );
}
