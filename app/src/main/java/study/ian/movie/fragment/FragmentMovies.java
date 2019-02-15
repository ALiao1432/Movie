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

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import study.ian.movie.R;
import study.ian.movie.adapter.MovieAdapter;
import study.ian.movie.adapter.SortAdapter;
import study.ian.movie.model.movie.Movie;
import study.ian.movie.service.MovieService;
import study.ian.movie.service.ServiceBuilder;
import study.ian.movie.util.Config;
import study.ian.movie.util.ObserverHelper;
import study.ian.movie.util.OnOptionSelectedListener;

public class FragmentMovies extends Fragment implements OnOptionSelectedListener {

    private final String TAG = "FragmentMovies";

    private RecyclerView movieRecyclerView;
    private RecyclerView movieSortRecyclerView;
    private MovieAdapter movieAdapter;
    private MovieService movieService = ServiceBuilder.getService(MovieService.class);
    private List<String> movieSortOptionList;
    private String sortBy;
    private int currentPage = 0;
    private int totalPages = 0;
    private boolean isLoading = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_movies, container, false);

        movieSortOptionList = Arrays.asList(getResources().getStringArray(R.array.sort_movie_option_array));
        sortBy = movieSortOptionList.get(0);

        findViews(view);
        setViews();

        return view;
    }

    private void findViews(View parent) {
        movieRecyclerView = parent.findViewById(R.id.recyclerViewMovie);
        movieSortRecyclerView = parent.findViewById(R.id.recyclerViewMovieSort);
    }

    private void setViews() {
        GridLayoutManager movieLayoutManager = new GridLayoutManager(getContext(), 2);
        movieLayoutManager.setOrientation(OrientationHelper.VERTICAL);

        LinearLayoutManager movieSortLayoutManager = new LinearLayoutManager(getContext());
        movieSortLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        movieAdapter = new MovieAdapter(getContext());
        SortAdapter sortAdapter = new SortAdapter(getContext());
        sortAdapter.setOptionList(movieSortOptionList);
        sortAdapter.setOptionSelectedListener(this);

        movieRecyclerView.setLayoutManager(movieLayoutManager);
        movieRecyclerView.setAdapter(movieAdapter);
        // setup load more listener
        movieRecyclerView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            int lastVisibleItem = movieLayoutManager.findLastVisibleItemPosition();
            int totalItemCount = movieLayoutManager.getItemCount();

            if (!isLoading && (lastVisibleItem + Config.VISIBLE_THRESHOLD) >= totalItemCount && currentPage < totalPages) {
                loadMorePage();
            }
        });

        movieSortRecyclerView.setLayoutManager(movieSortLayoutManager);
        movieSortRecyclerView.setAdapter(sortAdapter);

        if (currentPage == 0) {
            loadMorePage();
        }
    }

    private void loadMorePage() {
        currentPage++;
        isLoading = true;
        subscribeForData(movieService.getMovie(ServiceBuilder.API_KEY, sortBy, currentPage, true, false, Config.REQUEST_LANGUAGE));
    }

    private void subscribeForData(Observable<Movie> observable) {
        observable.compose(ObserverHelper.applyHelper())
                .doOnNext(movie -> {
                    movieAdapter.addResults(movie.getMovieResults());
                    totalPages = movie.getTotal_pages();
                    isLoading = false;
                })
                .doOnError(throwable -> Log.d(TAG, "onCreateView: get movie list error : " + throwable))
                .subscribe();
    }

    @Override
    public void onOptionSelected(String option) {
        sortBy = option;
        currentPage = 0;
        movieAdapter.clearResults();
        loadMorePage();
    }
}
