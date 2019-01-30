package study.ian.movie.service;

import android.support.annotation.Nullable;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import study.ian.movie.model.discover.Genre;
import study.ian.movie.model.movie.Movie;
import study.ian.movie.model.tv.TvShow;

public interface DiscoverService {

    @GET("/3/genre/movie/list?language=en-US")
    Observable<Genre> getMovieGenre(
            @Query("api_key") String api_key
    );

    @GET("/3/genre/tv/list?language=en-US")
    Observable<Genre> getTvGenre(
            @Query("api_key") String api_key
    );

    @GET("/3/search/movie?language=en-US")
    Observable<Movie> searchMovie(
            @Query("api_key") String api_key,
            @Query("query") String query,
            @Query("page") int page,
            @Nullable @Query("primary_release_year") Integer primary_release_year,
            @Query("include_adult") boolean include_adult
    );

    @GET("/3/search/tv?language=en-US")
    Observable<TvShow> searchTvShow(
            @Query("api_key") String api_key,
            @Query("query") String query,
            @Query("page") int page,
            @Nullable @Query("first_air_date_year") Integer first_air_date_year
    );
}
