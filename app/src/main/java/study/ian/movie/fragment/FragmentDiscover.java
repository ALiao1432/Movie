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
import study.ian.movie.util.OnYearSelectedListener;

public class FragmentDiscover extends Fragment implements OnYearSelectedListener {

    private final String TAG = "FragmentDiscover";

    private Context context;
    private RecyclerView recyclerViewYear;
    private RecyclerView recyclerViewSearchResult;
    private Spinner optionSpinner;
    private EditText dbSearchEdt;
    private YearAdapter yearAdapter;
    private SearchAdapter searchAdapter;

    private AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                case 1:
                    Log.d(TAG, "onItemSelected: true");
                    yearAdapter.setSearchType(true);
                    break;
                case 2:
                    yearAdapter.setSearchType(false);
                    Log.d(TAG, "onItemSelected: flse");
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
        recyclerViewSearchResult = view.findViewById(R.id.recyclerViewSearchResult);
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

        recyclerViewYear.setLayoutManager(yearLayoutManager);
        recyclerViewYear.setAdapter(yearAdapter);

        recyclerViewSearchResult.setLayoutManager(searchLayoutManager);
        recyclerViewSearchResult.setAdapter(searchAdapter);
        recyclerViewSearchResult.setNestedScrollingEnabled(false);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                context,
                R.array.search_genre_option_array,
                R.layout.spinner_item
        );
        optionSpinner.setAdapter(spinnerAdapter);
        optionSpinner.setOnItemSelectedListener(itemSelectedListener);


        RxTextView.textChanges(dbSearchEdt)
                .throttleLast(2000, TimeUnit.MILLISECONDS)
                .doOnNext(charSequence -> search((String) optionSpinner.getSelectedItem(), charSequence.toString(), yearAdapter.getSelectedYear()))
                .doOnError(throwable -> Log.d(TAG, "setViews: dbSearchEdt error : " + throwable))
                .subscribe();
    }

    private void search(String searchType, String query, @Nullable Integer year) {
        if (query.equals("")) {
            return;
        }

        switch (searchType) {
            case "Movie":
                ServiceBuilder.getService(DiscoverService.class)
                        .searchMovie(ServiceBuilder.API_KEY, query, year)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(movie -> searchAdapter.setResultList(movie.getMovieResults()))
                        .doOnError(throwable -> Log.d(TAG, "search: search Movie error : " + throwable))
                        .subscribe();
                break;
            case "Tv Show":
                break;
            case "Person":
                break;
        }
    }

    @Override
    public void onYearSelected(@Nullable Integer year) {
        search((String) optionSpinner.getSelectedItem(), dbSearchEdt.getText().toString(), year);
    }
}
