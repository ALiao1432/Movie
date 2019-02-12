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
import io.reactivex.schedulers.Schedulers;
import study.ian.movie.R;
import study.ian.movie.adapter.SearchAdapter;
import study.ian.movie.adapter.YearAdapter;
import study.ian.movie.service.DiscoverService;
import study.ian.movie.service.ServiceBuilder;
import study.ian.movie.util.Config;
import study.ian.movie.util.OnYearSelectedListener;

public class FragmentDiscover extends Fragment implements OnYearSelectedListener {

    private final String TAG = "FragmentDiscover";

    private Context context;
    private RecyclerView yearRecyclerView;
    private RecyclerView searchResultRecyclerView;
    private Spinner optionSpinner;
    private EditText dbSearchEdt;
    private YearAdapter yearAdapter;
    private SearchAdapter searchAdapter;
    private String lastQuery = "";
    private String lastSearchType;
    private boolean isSearching = false;
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
            search((String) optionSpinner.getSelectedItem(), dbSearchEdt.getText().toString(), yearAdapter.getSelectedYear());
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
        optionSpinner = view.findViewById(R.id.genreOptionSpinner);
        dbSearchEdt = view.findViewById(R.id.dbSearchEdt);
    }

    private void setViews() {
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
                search((String) optionSpinner.getSelectedItem(), dbSearchEdt.getText().toString(), yearAdapter.getSelectedYear());
            }
        });

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                context,
                R.array.search_genre_option_array,
                R.layout.spinner_item
        );
        optionSpinner.setAdapter(spinnerAdapter);
        optionSpinner.setOnItemSelectedListener(itemSelectedListener);
        lastSearchType = (String) optionSpinner.getSelectedItem();

        RxTextView.textChanges(dbSearchEdt)
                .throttleLast(2000, TimeUnit.MILLISECONDS)
                .doOnNext(charSequence -> search((String) optionSpinner.getSelectedItem(), charSequence.toString(), yearAdapter.getSelectedYear()))
                .doOnError(throwable -> Log.d(TAG, "setViews: dbSearchEdt error : " + throwable))
                .subscribe();
    }

    private void search(String searchType, String query, @Nullable Integer year) {
        if (query.equals("")) {
            // if query is empty, then just return
            return;
        }

        isSearching = true;
        if (lastQuery.equals(query) && lastSearchType.equals(searchType)) {
            page++;
        } else {
            searchAdapter.clearResultList();
            page = 1;
        }

        switch (searchType) {
            case "Movie":
                ServiceBuilder.getService(DiscoverService.class)
                        .searchMovie(ServiceBuilder.API_KEY, query, page, year, Config.INCLUDE_ADULT, Config.REQUEST_LANGUAGE)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(movie -> {
                            isSearching = false;
                            totalSearchPages = movie.getTotal_pages();
                            searchAdapter.addResultList(movie.getMovieResults());
                        })
                        .doOnError(throwable -> Log.d(TAG, "search: search Movie error : " + throwable))
                        .subscribe();
                break;
            case "Tv Show":
                ServiceBuilder.getService(DiscoverService.class)
                        .searchTvShow(ServiceBuilder.API_KEY, query, page, year, Config.REQUEST_LANGUAGE)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(tvShow -> {
                            isSearching = false;
                            totalSearchPages = tvShow.getTotal_pages();
                            searchAdapter.addResultList(tvShow.getTvShowResults());
                        })
                        .doOnError(throwable -> Log.d(TAG, "search: search tv show error : " + throwable))
                        .subscribe();
                break;
            case "Person":
                ServiceBuilder.getService(DiscoverService.class)
                        .searchPerson(ServiceBuilder.API_KEY, query, page, Config.INCLUDE_ADULT, Config.REQUEST_LANGUAGE)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(popular -> {
                            isSearching = false;
                            totalSearchPages = popular.getTotal_pages();
                            searchAdapter.addResultList(popular.getResults());
                        })
                        .doOnError(throwable -> Log.d(TAG, "search: search person error : " + throwable))
                        .subscribe();
                break;
        }

        lastQuery = query;
        lastSearchType = searchType;
    }

    private void searchByYear(String searchType, String query, @Nullable Integer year) {
        if (query.equals("")) {
            // if query is empty, then just return
            return;
        }

        page = 1;
        searchAdapter.clearResultList();

        switch (searchType) {
            case "Movie":
                ServiceBuilder.getService(DiscoverService.class)
                        .searchMovie(ServiceBuilder.API_KEY, query, page, year, Config.INCLUDE_ADULT, Config.REQUEST_LANGUAGE)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(movie -> {
                            isSearching = false;
                            totalSearchPages = movie.getTotal_pages();
                            searchAdapter.addResultList(movie.getMovieResults());
                        })
                        .doOnError(throwable -> Log.d(TAG, "search: search Movie error : " + throwable))
                        .subscribe();
                break;
            case "Tv Show":
                ServiceBuilder.getService(DiscoverService.class)
                        .searchTvShow(ServiceBuilder.API_KEY, query, page, year, Config.REQUEST_LANGUAGE)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(tvShow -> {
                            isSearching = false;
                            totalSearchPages = tvShow.getTotal_pages();
                            searchAdapter.addResultList(tvShow.getTvShowResults());
                        })
                        .doOnError(throwable -> Log.d(TAG, "search: search tv show error : " + throwable))
                        .subscribe();
                break;
            case "Person":
                ServiceBuilder.getService(DiscoverService.class)
                        .searchPerson(ServiceBuilder.API_KEY, query, page, Config.INCLUDE_ADULT, Config.REQUEST_LANGUAGE)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(popular -> {
                            isSearching = false;
                            totalSearchPages = popular.getTotal_pages();
                            searchAdapter.addResultList(popular.getResults());
                        })
                        .doOnError(throwable -> Log.d(TAG, "search: search person error : " + throwable))
                        .subscribe();
                break;
        }

        lastQuery = query;
        lastSearchType = searchType;
    }

    @Override
    public void onYearSelected(@Nullable Integer year) {
        searchByYear((String) optionSpinner.getSelectedItem(), dbSearchEdt.getText().toString(), year);
    }
}
