package study.ian.movie.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import study.ian.movie.R;
import study.ian.movie.adapter.PeopleAdapter;
import study.ian.movie.model.people.popular.Popular;
import study.ian.movie.service.PeopleService;
import study.ian.movie.service.ServiceBuilder;
import study.ian.movie.util.LanguageConfig;

public class FragmentPeople extends Fragment {

    private final String TAG = "FragmentPeople";
    private final int VISIBLE_THRESHOLD = 10;

    private RecyclerView peopleRecyclerView;
    private PeopleAdapter peopleAdapter;
    private PeopleService peopleService = ServiceBuilder.getService(PeopleService.class);
    private int currentPage = 0;
    private int totalPages = 0;
    private boolean isLoading = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_people, container, false);

        findViews(view);
        setViews();

        return view;
    }

    private void findViews(View parent) {
        peopleRecyclerView = parent.findViewById(R.id.recyclerViewPeople);
    }

    private void setViews() {
        GridLayoutManager peopleLayoutManager = new GridLayoutManager(getContext(), 2);
        peopleLayoutManager.setOrientation(OrientationHelper.VERTICAL);

        peopleAdapter = new PeopleAdapter(getContext());
        peopleRecyclerView.setLayoutManager(peopleLayoutManager);
        peopleRecyclerView.setAdapter(peopleAdapter);
        peopleRecyclerView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            int lastVisibleItem = peopleLayoutManager.findLastVisibleItemPosition();
            int totalItemCount = peopleLayoutManager.getItemCount();

            if (!isLoading && (lastVisibleItem + VISIBLE_THRESHOLD) >= totalItemCount && currentPage < totalPages) {
                loadMorePage();
            }
        });

        if (currentPage == 0) {
            loadMorePage();
        }
    }

    private void loadMorePage() {
        currentPage++;
        isLoading = true;
        subscribeForData(peopleService.getPopular(ServiceBuilder.API_KEY, currentPage, LanguageConfig.REQUEST_LANGUAGE));
    }

    private void subscribeForData(Observable<Popular> observable) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(popular -> {
                    peopleAdapter.addResults(popular.getResults());
                    totalPages = popular.getTotal_pages();
                    isLoading = false;
                })
                .doOnError(
                        // TODO: 2019-01-15 retry when network is not work out...
                        throwable -> Log.d(TAG, "onCreateView: t : " + throwable))
                .subscribe();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
