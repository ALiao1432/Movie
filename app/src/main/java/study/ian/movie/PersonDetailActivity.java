package study.ian.movie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import study.ian.movie.service.PeopleService;

public class PersonDetailActivity extends AppCompatActivity {

    private final String TAG = "PersonDetailActivity";
    private int personId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        personId = getIntent().getIntExtra(PeopleService.KEY_ID, 0);
        Log.d(TAG, "onCreate: personId : " + personId);
    }
}
