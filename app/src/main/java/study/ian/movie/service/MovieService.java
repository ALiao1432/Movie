package study.ian.movie.service;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import study.ian.movie.model.movie.detail.Detail;
import study.ian.movie.model.movie.Movie;
import study.ian.movie.model.movie.keyword.Keyword;
import study.ian.movie.model.movie.recommend.Recommend;
import study.ian.movie.model.genral.video.Video;

public interface MovieService {

    String KEY_ID = "KEY_ID";

    @GET("/3/discover/movie")
    Observable<Movie> getMovie(
            @Query("api_key") String api_key,
            @Query("sort_by") String sort_by,
            @Query("page") int page,
            @Query("include_adult") boolean include_adult,
            @Query("include_video") boolean include_video,
            @Query("language") String language
    );

    @GET("/3/movie/{id}")
    Observable<Detail> getDetail(
            @Path("id") int id,
            @Query("api_key") String api_key,
            @Query("language") String language
    );

    @GET("/3/movie/{id}/videos")
    Observable<Video> getVideo(
            @Path("id") int id,
            @Query("api_key") String api_key,
            @Query("language") String language
    );

    @GET("/3/movie/{id}/keywords")
    Observable<Keyword> getKeyword(
            @Path("id") int id,
            @Query("api_key") String api_key
    );

    @GET("/3/movie/{id}/recommendations")
    Observable<Recommend> getRecommend(
            @Path("id") int id,
            @Query("api_key") String api_key,
            @Query("page") int page,
            @Query("language") String language
    );
}
