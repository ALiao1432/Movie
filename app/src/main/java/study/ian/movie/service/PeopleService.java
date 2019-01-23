package study.ian.movie.service;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import study.ian.movie.model.people.credit.Credit;

public interface PeopleService {

    @GET("/3/movie/{id}/credits")
    Observable<Credit> getCredit(
            @Path("id") int id,
            @Query("api_key") String api_key
    );
}
