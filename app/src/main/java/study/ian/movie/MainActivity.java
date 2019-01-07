package study.ian.movie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import study.ian.movie.service.TestService;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView text = findViewById(R.id.text);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TestService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        retrofit.create(TestService.class).getTodoList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        responses -> {
                            StringBuilder sb = new StringBuilder();
                            responses.forEach(todo -> sb.append(todo.getTitle() + "\n"));
                            text.setText(sb.toString());
                        },
                        throwable -> {
                            text.setText("something is error : " + throwable);
                            Log.d(TAG, "onCreate : " + throwable);
                        }
                );
    }
}
