package study.ian.movie.service;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import study.ian.movie.model.discover.Genre;

public interface DiscoverService {

    @GET("/3/genre/movie/list?language=en-US")
    Observable<Genre> getMovieGenre(
            @Query("api_key") String api_key
    );

    @GET("/3/genre/tv/list?language=en-US")
    Observable<Genre> getTvGenre(
            @Query("api_key") String api_key
    );
}
