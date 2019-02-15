package study.ian.movie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import study.ian.movie.service.PeopleService;
import study.ian.movie.service.ServiceBuilder;

public class PersonImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_image);

        String path = ServiceBuilder.POSTER_BASE_URL + getIntent().getStringExtra(PeopleService.KEY_PERSON_IMAGE_PATH);
        Glide.with(this)
                .load(path)
                .apply(new RequestOptions().centerCrop())
                .into((ImageView) findViewById(R.id.personImage));
    }
}
