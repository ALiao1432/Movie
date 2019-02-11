package study.ian.movie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.jakewharton.rxbinding3.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import study.ian.morphviewlib.MorphView;
import study.ian.movie.adapter.CreditAdapter;
import study.ian.movie.adapter.PersonDetailImageAdapter;
import study.ian.movie.service.PeopleService;
import study.ian.movie.service.ServiceBuilder;
import study.ian.movie.util.Config;
import study.ian.movie.util.DetailActivity;
import study.ian.movie.util.ExpandableTextView;

public class PersonDetailActivity extends DetailActivity {

    private final String TAG = "PersonDetailActivity";
    private int personId;

    private ViewPager personImagePager;
    private TextView nameText;
    private TextView genderText;
    private TextView birthText;
    private TextView deathText;
    private TextView birthPlaceText;
    private ExpandableTextView bioText;
    private MorphView expandHintView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);

        personId = getIntent().getIntExtra(PeopleService.KEY_ID, 0);

        findViews();
        setViews();
    }

    private void findViews() {
        personImagePager = findViewById(R.id.personDetailImagePager);
        nameText = findViewById(R.id.nameText);
        genderText = findViewById(R.id.genderText);
        birthText = findViewById(R.id.birthText);
        deathText = findViewById(R.id.deathText);
        birthPlaceText = findViewById(R.id.birthPlaceText);
        bioText = findViewById(R.id.bioText);
    }

    private void setViews() {
        ServiceBuilder.getService(PeopleService.class)
                .getDetail(personId, ServiceBuilder.API_KEY, Config.REQUEST_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(detail -> {
                    nameText.setText(detail.getName());
                    genderText.setText(getGender(detail.getGender()));
                    birthText.setText(detail.getBirthday());
                    deathText.setText((detail.getDeathday() == null) ? "" : detail.getDeathday());
                    birthPlaceText.setText(detail.getPlace_of_birth());
                    bioText.setText(detail.getBiography());

                    if (bioText.isExpandable()) {
                        View v = ((ViewStub) findViewById(R.id.expandHintViewStub)).inflate();
                        expandHintView = v.findViewById(R.id.expandHintView);
                        expandHintView.setCurrentId(R.drawable.vd_expand_arrow_down);
                        expandHintView.setPaintColor("#E2E2E2");
                        expandHintView.setSize(75, 75);
                    }
                })
                .doOnError(throwable -> Log.d(TAG, "setViews: get detail error : " + throwable))
                .subscribe();

        ServiceBuilder.getService(PeopleService.class)
                .getImage(personId, ServiceBuilder.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(image -> personImagePager.setAdapter(new PersonDetailImageAdapter(this, image.getProfiles())))
                .doOnError(throwable -> Log.d(TAG, "setViews: get image error :  " + throwable))
                .subscribe();

        ServiceBuilder.getService(PeopleService.class)
                .getMovieCreditFromPerson(personId, ServiceBuilder.API_KEY, Config.REQUEST_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(credit -> {
                    if (credit.getCast().size() != 0) {
                        View v = ((ViewStub) findViewById(R.id.movieViewStub)).inflate();
                        RecyclerView creditRecyclerView = v.findViewById(R.id.recyclerViewPersonCredit);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

                        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        creditRecyclerView.setNestedScrollingEnabled(false);
                        creditRecyclerView.setLayoutManager(layoutManager);
                        creditRecyclerView.setAdapter(new CreditAdapter(this, credit));

                        ((TextView) v.findViewById(R.id.personCreditText)).setText(getResources().getString(R.string.movies));
                    }
                })
                .doOnError(throwable -> Log.d(TAG, "setViews: get movie credit error : " + throwable))
                .subscribe();

        ServiceBuilder.getService(PeopleService.class)
//                .getTvCreditFromPerson(1195620, ServiceBuilder.API_KEY, Config.REQUEST_LANGUAGE)
                .getTvCreditFromPerson(personId, ServiceBuilder.API_KEY, Config.REQUEST_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(credit -> {
                    if (credit.getCast().size() != 0) {
                        View v = ((ViewStub) findViewById(R.id.tvViewStub)).inflate();
                        RecyclerView creditRecyclerView = v.findViewById(R.id.recyclerViewPersonCredit);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

                        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        creditRecyclerView.setNestedScrollingEnabled(false);
                        creditRecyclerView.setLayoutManager(layoutManager);
                        creditRecyclerView.setAdapter(new CreditAdapter(this, credit));

                        ((TextView) v.findViewById(R.id.personCreditText)).setText(getResources().getString(R.string.tv_shows));
                    }
                })
                .doOnError(throwable -> Log.d(TAG, "setViews: get tv credit error : " + throwable))
                .subscribe();

        RxView.clicks(bioText)
                .throttleFirst(ExpandableTextView.DURATION, TimeUnit.MILLISECONDS)
                .doOnNext(unit -> {
                    bioText.setExpand();
                    if (bioText.isExpand()) {
                        expandHintView.performAnimation(R.drawable.vd_expand_arrow_up);
                    } else {
                        expandHintView.performAnimation(R.drawable.vd_expand_arrow_down);
                    }
                })
                .doOnError(throwable -> Log.d(TAG, "setViews: click bioText error : " + throwable))
                .subscribe();
    }

    private String getGender(int gender) {
        switch (gender) {
            case 1:
                return "Female";
            case 2:
                return "Male";
            default:
                return "Third gender";
        }
    }
}