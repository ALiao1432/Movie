package study.ian.movie.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import study.ian.movie.R;
import study.ian.movie.adapter.GenreOptionAdapter;
import study.ian.movie.adapter.SortAdapter;
import study.ian.movie.adapter.YearAdapter;
import study.ian.movie.model.discover.GenreResult;
import study.ian.movie.service.DiscoverService;
import study.ian.movie.service.ServiceBuilder;

public class FragmentDiscover extends Fragment {

    private final String TAG = "FragmentDiscover";

    private Context context;
    private RecyclerView recyclerViewYear;
    private RecyclerView recyclerViewSort;
    private RecyclerView recyclerViewGenreOption;
    private GenreOptionAdapter genreOptionAdapter;
    private Spinner optionSpinner;
    private EditText dbSearchEdt;
    private List<GenreResult> movieGenreList;
    private List<GenreResult> tvGenreList;
    private AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    genreOptionAdapter.setGenreResultList(movieGenreList);
                    break;
                case 1:
                    genreOptionAdapter.setGenreResultList(tvGenreList);
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_discover, container, false);

        findViews(view);
        setViews();
        getGenreList();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public void onDetach() {
        this.context = null;

        super.onDetach();
    }

    private void findViews(View view) {
        recyclerViewYear = view.findViewById(R.id.recyclerViewYear);
        recyclerViewSort = view.findViewById(R.id.recyclerViewSort);
        recyclerViewGenreOption = view.findViewById(R.id.recyclerViewGenreOption);
        optionSpinner = view.findViewById(R.id.genreOptionSpinner);
        dbSearchEdt = view.findViewById(R.id.dbSearchEdt);
    }

    private void setViews() {
        LinearLayoutManager yearLayoutManager = new LinearLayoutManager(getContext());
        yearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager sortLayoutManager = new LinearLayoutManager(getContext());
        sortLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager genreLayoutManager = new LinearLayoutManager(getContext());
        genreLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        genreOptionAdapter = new GenreOptionAdapter(context);

        recyclerViewYear.setLayoutManager(yearLayoutManager);
        recyclerViewSort.setLayoutManager(sortLayoutManager);
        recyclerViewGenreOption.setLayoutManager(genreLayoutManager);

        recyclerViewYear.setAdapter(new YearAdapter(context));
        recyclerViewSort.setAdapter(new SortAdapter(context));
        recyclerViewGenreOption.setAdapter(genreOptionAdapter);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                context,
                R.array.search_genre_option_array,
                R.layout.spinner_item
        );
        optionSpinner.setAdapter(spinnerAdapter);
    }

    private void getGenreList() {
        ServiceBuilder.getService(DiscoverService.class)
                .getMovieGenre(ServiceBuilder.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(genre -> {
                    movieGenreList = genre.getGenres();
                    optionSpinner.setOnItemSelectedListener(itemSelectedListener);
                    genreOptionAdapter.setGenreResultList(movieGenreList);
                })
                .doOnError(throwable -> Log.d(TAG, "getGenreList: get movie genre error : " + throwable))
                .subscribe();

        ServiceBuilder.getService(DiscoverService.class)
                .getTvGenre(ServiceBuilder.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(genre -> tvGenreList = genre.getGenres())
                .doOnError(throwable -> Log.d(TAG, "getGenreList: get tv genre error : " + throwable))
                .subscribe();
    }
}
