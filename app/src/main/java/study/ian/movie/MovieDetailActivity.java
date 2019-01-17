package study.ian.movie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import study.ian.movie.service.MovieService;
import study.ian.movie.service.ServiceBuilder;

public class MovieDetailActivity extends AppCompatActivity {

    private final String TAG = "MovieDetailActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView textView = findViewById(R.id.textView);


        toolbar.setTitle("this is toolbar title");
        toolbar.setSubtitle("subtitle this is");
        toolbar.setLogo(R.drawable.ic_movie);
        setSupportActionBar(toolbar);

        int id = getIntent().getIntExtra(MovieService.KEY_ID, 0);
        ServiceBuilder.getService(MovieService.class)
                .getDetail(id, ServiceBuilder.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        detail -> {
                            StringBuilder sb = new StringBuilder();
                            for (int i = 0; i < 4; i++) {
                                sb.append(detail.toString());
                            }
                            textView.setText(sb.toString());
                        },
                        throwable -> Log.d(TAG, "onCreate: error : " + throwable)
                );
    }
}
