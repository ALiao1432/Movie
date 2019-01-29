package study.ian.movie.service;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import study.ian.movie.model.tv.TvShow;

public interface TvShowService {

    @GET("/3/discover/tv?language=en-US")
    Observable<TvShow> getTvShow(
            @Query("api_key") String api_key,
            @Query("sort_by") String sort_by,
            @Query("page") int page
    );
}
