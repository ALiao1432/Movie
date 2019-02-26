package study.ian.movie.model.tv;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShow {

    @SerializedName("page")
    private int page;

    @SerializedName("total_results")
    private int total_results;

    @SerializedName("total_pages")
    private int total_pages;

    @SerializedName("results")
    private final List<TvShowResult> tvShowResults = null;

    public int getPage() {
        return page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public List<TvShowResult> getTvShowResults() {
        return tvShowResults;
    }
}
