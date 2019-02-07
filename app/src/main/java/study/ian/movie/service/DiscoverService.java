package study.ian.movie.service;

import android.support.annotation.Nullable;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import study.ian.movie.model.discover.Genre;
import study.ian.movie.model.movie.Movie;
import study.ian.movie.model.tv.TvShow;

public interface DiscoverService {

    @GET("/3/genre/movie/list")
    Observable<Genre> getMovieGenre(
            @Query("api_key") String api_key,
            @Query("language") String language
    );

    @GET("/3/genre/tv/list")
    Observable<Genre> getTvGenre(
            @Query("api_key") String api_key,
            @Query("language") String language
    );

    @GET("/3/search/movie")
    Observable<Movie> searchMovie(
            @Query("api_key") String api_key,
            @Query("query") String query,
            @Query("page") int page,
            @Nullable @Query("primary_release_year") Integer primary_release_year,
            @Query("include_adult") boolean include_adult,
            @Query("language") String language
    );

    @GET("/3/search/tv")
    Observable<TvShow> searchTvShow(
            @Query("api_key") String api_key,
            @Query("query") String query,
            @Query("page") int page,
            @Nullable @Query("first_air_date_year") Integer first_air_date_year,
            @Query("language") String language
    );
}
