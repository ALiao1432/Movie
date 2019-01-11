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

import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.view.ViewScrollChangeEvent;
import com.jakewharton.rxbinding3.widget.RxAdapterView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import study.ian.movie.R;
import study.ian.movie.adapter.MovieAdapter;
import study.ian.movie.service.MovieService;
import study.ian.movie.service.ServiceBuilder;

public class FragmentMovies extends Fragment {

    private final String TAG = "FragmentMovies";

    private RecyclerView recyclerView;
    private Disposable serviceDisposable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_movies, container, false);

        findViews(view);
        setViews();

        serviceDisposable = ServiceBuilder.getService(MovieService.class)
                .getPage(ServiceBuilder.API_KEY, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        movie -> recyclerView.setAdapter(new MovieAdapter(getContext(), movie)),
                        throwable -> Log.d(TAG, "onCreateView: t : " + throwable)
                );

//        Observable.interval(500, TimeUnit.MILLISECONDS)
//                .map(l -> recyclerView.computeVerticalScrollOffset())
//                .subscribe(offset -> {
//                    if (recyclerView.getChildAt(0) != null) {
//                        Log.d(TAG, "onCreateView: offset : " + offset + ", height : " + recyclerView.getChildAt(0).getHeight());
//                    }
//                });
        return view;
    }

    @Override
    public void onDestroyView() {
        serviceDisposable.dispose();
        super.onDestroyView();
    }

    private void findViews(View parent) {
        recyclerView = parent.findViewById(R.id.recyclerViewMovie);
    }

    private void setViews() {
        GridLayoutManager layoutManager;

        layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);

    }
}
