package study.ian.movie.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import study.ian.movie.R;
import study.ian.movie.adapter.SortAdapter;
import study.ian.movie.adapter.TvShowAdapter;
import study.ian.movie.model.tv.TvShow;
import study.ian.movie.service.ServiceBuilder;
import study.ian.movie.service.TvShowService;
import study.ian.movie.util.Config;
import study.ian.movie.util.ObserverHelper;
import study.ian.movie.util.OnOptionSelectedListener;

public class FragmentTvShows extends Fragment implements OnOptionSelectedListener {

    private final String TAG = "FragmentTvShows";

    private RecyclerView tvShowRecyclerView;
    private RecyclerView tvShowSortRecyclerView;
    private TvShowAdapter tvShowAdapter;
    private final TvShowService tvShowService = ServiceBuilder.getService(TvShowService.class);
    private List<String> tvShowSortOptionList;
    private String sortBy;
    private int currentPage = 0;
    private int totalPages = 0;
    private boolean isLoading = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_tv_shows, container, false);

        tvShowSortOptionList = Arrays.asList(getResources().getStringArray(R.array.sort_tv_show_option_array));
        sortBy = tvShowSortOptionList.get(0);

        findViews(view);
        setViews();

        return view;
    }

    private void findViews(@NotNull View parent) {
        tvShowRecyclerView = parent.findViewById(R.id.recyclerViewTvShow);
        tvShowSortRecyclerView = parent.findViewById(R.id.recyclerViewTvShowSort);
    }

    private void setViews() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        LinearLayoutManager tvShowSortLayoutManager = new LinearLayoutManager(getContext());
        tvShowSortLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        tvShowAdapter = new TvShowAdapter(getContext());
        SortAdapter sortAdapter = new SortAdapter(getContext());
        sortAdapter.setOptionList(tvShowSortOptionList);
        sortAdapter.setOptionSelectedListener(this);

        tvShowRecyclerView.setLayoutManager(layoutManager);
        tvShowRecyclerView.setAdapter(tvShowAdapter);
        // setup load more listener
        tvShowRecyclerView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            int totalItemCount = layoutManager.getItemCount();

            if (!isLoading && (lastVisibleItem + Config.VISIBLE_THRESHOLD) >= totalItemCount && currentPage < totalPages) {
                loadMorePage();
            }
        });

        tvShowSortRecyclerView.setLayoutManager(tvShowSortLayoutManager);
        tvShowSortRecyclerView.setAdapter(sortAdapter);

        if (currentPage == 0) {
            loadMorePage();
        }
    }

    private void loadMorePage() {
        currentPage++;
        isLoading = true;
        subscribeForData(tvShowService.getTvShow(ServiceBuilder.API_KEY, sortBy, currentPage, Config.REQUEST_LANGUAGE));
    }

    private void subscribeForData(@NotNull Observable<TvShow> observable) {
        observable.compose(ObserverHelper.applyHelper())
                .doOnNext(tvShow -> {
                    tvShowAdapter.addResults(tvShow.getTvShowResults());
                    totalPages = tvShow.getTotal_pages();
                    isLoading = false;
                })
                .doOnError(throwable -> Log.d(TAG, "onCreateView: t : " + throwable))
                .subscribe();
    }

    @Override
    public void onOptionSelected(String option) {
        sortBy = option;
        currentPage = 0;
        tvShowAdapter.clearResults();
        loadMorePage();
    }
}
