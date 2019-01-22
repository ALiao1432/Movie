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
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import study.ian.movie.R;
import study.ian.movie.adapter.MovieAdapter;
import study.ian.movie.model.movie.Movie;
import study.ian.movie.service.MovieService;
import study.ian.movie.service.ServiceBuilder;

public class FragmentMovies extends Fragment {

    private final String TAG = "FragmentMovies";
    private final int VISIBLE_THRESHOLD = 10;

    private RecyclerView movieRecyclerView;
    private MovieAdapter movieAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MovieService movieService = ServiceBuilder.getService(MovieService.class);
    private int currentPage = 0;
    private boolean isLoading = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_movies, container, false);

        findViews(view);
        setViews();

        return view;
    }

    @Override
    public void onDestroyView() {
        compositeDisposable.clear();
        super.onDestroyView();
    }

    private void findViews(View parent) {
        movieRecyclerView = parent.findViewById(R.id.recyclerViewMovie);
    }

    private void setViews() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        movieAdapter = new MovieAdapter(getContext());

        movieRecyclerView.setLayoutManager(layoutManager);
        movieRecyclerView.setAdapter(movieAdapter);
        // setup load more listener
        movieRecyclerView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            int totalItemCount = layoutManager.getItemCount();

            if (!isLoading && (lastVisibleItem + VISIBLE_THRESHOLD) >= totalItemCount) {
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
        subscribeForData(movieService.getPage(ServiceBuilder.API_KEY, "popularity.desc", currentPage, true, false));
    }

    private void subscribeForData(Observable<Movie> observable) {
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        movie -> {
                            movieAdapter.addResults(movie.getResults());
                            isLoading = false;
                        },
                        // TODO: 2019-01-15 retry when network is not work out...
                        throwable -> Log.d(TAG, "onCreateView: t : " + throwable)
                );

        compositeDisposable.add(disposable);
    }
}
