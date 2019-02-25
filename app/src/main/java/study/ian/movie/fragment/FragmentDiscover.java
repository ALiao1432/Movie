package study.ian.movie.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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

import com.jakewharton.rxbinding3.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import study.ian.morphviewlib.MorphView;
import study.ian.movie.R;
import study.ian.movie.adapter.SearchAdapter;
import study.ian.movie.adapter.YearAdapter;
import study.ian.movie.service.DiscoverService;
import study.ian.movie.service.ServiceBuilder;
import study.ian.movie.util.Config;
import study.ian.movie.util.ObserverHelper;
import study.ian.movie.util.OnYearSelectedListener;

public class FragmentDiscover extends Fragment implements OnYearSelectedListener {

    private final String TAG = "FragmentDiscover";

    private Context context;
    private RecyclerView yearRecyclerView;
    private RecyclerView searchResultRecyclerView;
    private MorphView searchHintView;
    private Spinner optionSpinner;
    private EditText dbSearchEdt;
    private YearAdapter yearAdapter;
    private SearchAdapter searchAdapter;
    private String lastQuery = "";
    private boolean isSearching = false;
    private int lastSearchType;
    private int totalSearchPages = 0;
    private int page = 1;

    private AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                case 1:
                    yearAdapter.setSearchType(true);
                    break;
                case 2:
                    yearAdapter.setSearchType(false);
                    break;
            }

            if (dbSearchEdt.getText().length() != 0) {
                startSearchAnim();
                search(optionSpinner.getSelectedItemPosition(), dbSearchEdt.getText().toString(), yearAdapter.getSelectedYear());
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
        yearRecyclerView = view.findViewById(R.id.recyclerViewYear);
        searchResultRecyclerView = view.findViewById(R.id.recyclerViewSearchResult);
        searchHintView = view.findViewById(R.id.searchHintView);
        optionSpinner = view.findViewById(R.id.genreOptionSpinner);
        dbSearchEdt = view.findViewById(R.id.dbSearchEdt);
    }

    private void setViews() {
        searchHintView.setCurrentId(R.drawable.vd_search_1);
        searchHintView.setSize(70, 70);
        searchHintView.setPaintWidth(4);
        searchHintView.setPaintColor(context.getColor(R.color.colorAccent));
        searchHintView.setAnimationDuration(240);

        LinearLayoutManager yearLayoutManager = new LinearLayoutManager(context);
        yearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        GridLayoutManager searchLayoutManager = new GridLayoutManager(context, 2);
        searchLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        yearAdapter = new YearAdapter(context);
        yearAdapter.setSearchType(true);
        yearAdapter.setOnYearSelectedListener(this);

        searchAdapter = new SearchAdapter(context);

        yearRecyclerView.setLayoutManager(yearLayoutManager);
        yearRecyclerView.setAdapter(yearAdapter);

        searchResultRecyclerView.setLayoutManager(searchLayoutManager);
        searchResultRecyclerView.setAdapter(searchAdapter);
        searchResultRecyclerView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            int lastVisibleItem = searchLayoutManager.findLastVisibleItemPosition();
            int totalItemCount = searchLayoutManager.getItemCount();

            if (!isSearching && (lastVisibleItem + Config.VISIBLE_THRESHOLD) >= totalItemCount && page < totalSearchPages) {
                search(optionSpinner.getSelectedItemPosition(), dbSearchEdt.getText().toString(), yearAdapter.getSelectedYear());
            }
        });

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                context,
                R.array.search_genre_option_array,
                R.layout.spinner_item
        );
        optionSpinner.setAdapter(spinnerAdapter);
        optionSpinner.setOnItemSelectedListener(itemSelectedListener);
        lastSearchType = optionSpinner.getSelectedItemPosition();

        RxTextView.textChanges(dbSearchEdt)
                .debounce(1500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .filter(charSequence -> charSequence.length() > 0)
                .doOnEach(charSequenceNotification -> startSearchAnim())
                .doOnNext(charSequence -> search(optionSpinner.getSelectedItemPosition(), charSequence.toString(), yearAdapter.getSelectedYear()))
                .doOnError(throwable -> Log.d(TAG, "setViews: dbSearchEdt error : " + throwable))
                .subscribe();
    }

    private void search(int searchType, String query, @Nullable Integer year) {
        if (query.equals("")) {
            // if query is empty, then just return
            return;
        }

        isSearching = true;
        if (lastQuery.equals(query) && lastSearchType == searchType) {
            page++;
        } else {
            searchAdapter.clearResultList();
            page = 1;
        }

        switch (searchType) {
            case 0:
                ServiceBuilder.getService(DiscoverService.class)
                        .searchMovie(ServiceBuilder.API_KEY, query, page, year, Config.INCLUDE_ADULT, Config.REQUEST_LANGUAGE)
                        .compose(ObserverHelper.applyHelper())
                        .doOnNext(movie -> {
                            isSearching = false;
                            totalSearchPages = movie.getTotal_pages();
                            searchAdapter.addResultList(movie.getMovieResults());
                            stopSearchAnim();
                        })
                        .doOnError(throwable -> Log.d(TAG, "search: search Movie error : " + throwable))
                        .subscribe();
                break;
            case 1:
                ServiceBuilder.getService(DiscoverService.class)
                        .searchTvShow(ServiceBuilder.API_KEY, query, page, year, Config.REQUEST_LANGUAGE)
                        .compose(ObserverHelper.applyHelper())
                        .doOnNext(tvShow -> {
                            isSearching = false;
                            totalSearchPages = tvShow.getTotal_pages();
                            searchAdapter.addResultList(tvShow.getTvShowResults());
                            stopSearchAnim();
                        })
                        .doOnError(throwable -> Log.d(TAG, "search: search tv show error : " + throwable))
                        .subscribe();
                break;
            case 2:
                ServiceBuilder.getService(DiscoverService.class)
                        .searchPerson(ServiceBuilder.API_KEY, query, page, Config.INCLUDE_ADULT, Config.REQUEST_LANGUAGE)
                        .compose(ObserverHelper.applyHelper())
                        .doOnNext(popular -> {
                            isSearching = false;
                            totalSearchPages = popular.getTotal_pages();
                            searchAdapter.addResultList(popular.getResults());
                            stopSearchAnim();
                        })
                        .doOnError(throwable -> Log.d(TAG, "search: search person error : " + throwable))
                        .subscribe();
                break;
        }

        lastQuery = query;
        lastSearchType = searchType;
    }

    private void searchByYear(int searchType, String query, @Nullable Integer year) {
        if (query.equals("")) {
            // if query is empty, then just return
            return;
        }

        page = 1;
        searchAdapter.clearResultList();

        switch (searchType) {
            case 0:
                ServiceBuilder.getService(DiscoverService.class)
                        .searchMovie(ServiceBuilder.API_KEY, query, page, year, Config.INCLUDE_ADULT, Config.REQUEST_LANGUAGE)
                        .compose(ObserverHelper.applyHelper())
                        .doOnNext(movie -> {
                            isSearching = false;
                            totalSearchPages = movie.getTotal_pages();
                            searchAdapter.addResultList(movie.getMovieResults());
                            stopSearchAnim();
                        })
                        .doOnError(throwable -> Log.d(TAG, "search: search Movie error : " + throwable))
                        .subscribe();
                break;
            case 1:
                ServiceBuilder.getService(DiscoverService.class)
                        .searchTvShow(ServiceBuilder.API_KEY, query, page, year, Config.REQUEST_LANGUAGE)
                        .compose(ObserverHelper.applyHelper())
                        .doOnNext(tvShow -> {
                            isSearching = false;
                            totalSearchPages = tvShow.getTotal_pages();
                            searchAdapter.addResultList(tvShow.getTvShowResults());
                            stopSearchAnim();
                        })
                        .doOnError(throwable -> Log.d(TAG, "search: search tv show error : " + throwable))
                        .subscribe();
                break;
            case 2:
                ServiceBuilder.getService(DiscoverService.class)
                        .searchPerson(ServiceBuilder.API_KEY, query, page, Config.INCLUDE_ADULT, Config.REQUEST_LANGUAGE)
                        .compose(ObserverHelper.applyHelper())
                        .doOnNext(popular -> {
                            isSearching = false;
                            totalSearchPages = popular.getTotal_pages();
                            searchAdapter.addResultList(popular.getResults());
                            stopSearchAnim();
                        })
                        .doOnError(throwable -> Log.d(TAG, "search: search person error : " + throwable))
                        .subscribe();
                break;
        }

        lastQuery = query;
        lastSearchType = searchType;
    }

    private void startSearchAnim() {
        searchHintView.performInfiniteAnimation(
                R.drawable.vd_search_2,
                R.drawable.vd_search_3,
                R.drawable.vd_search_4,
                R.drawable.vd_search_5
        );
    }

    private void stopSearchAnim() {
        searchHintView.stopInfiniteAnimation();
        searchHintView.performAnimation(R.drawable.vd_search_1);
    }

    @Override
    public void onYearSelected(@Nullable Integer year) {
        if (dbSearchEdt.getText().length() != 0) {
            startSearchAnim();
            searchByYear(optionSpinner.getSelectedItemPosition(), dbSearchEdt.getText().toString(), year);
        }
    }
}
