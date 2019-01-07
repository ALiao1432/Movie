package study.ian.movie.service;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import study.ian.movie.model.Todo;

public interface TestService {

    String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @GET("todos/1")
    Observable<Todo> getTodo();

    @GET("todos")
    Observable<List<Todo>> getTodoList();
}
