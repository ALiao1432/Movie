package study.ian.movie.service;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import study.ian.movie.model.tv.TvShow;
import study.ian.movie.model.tv.detail.Detail;

public interface TvShowService {

    String KEY_ID = "TV_SHOW_KEY_ID";

    @GET("/3/discover/tv?language=en-US")
    Observable<TvShow> getTvShow(
            @Query("api_key") String api_key,
            @Query("sort_by") String sort_by,
            @Query("page") int page
    );

    @GET("/3/tv/{id}?language=en-US")
    Observable<Detail> getDetail(
            @Path("id") int id,
            @Query("api_key") String api_key
    );
}
