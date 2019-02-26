package study.ian.movie.model.movie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie {

    @SerializedName("page")
    private int page;

    @SerializedName("total_results")
    private int total_results;

    @SerializedName("total_pages")
    private int total_pages;

    @SerializedName("results")
    private final List<MovieResult> movieResults = null;

    public int getPage() {
        return page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public List<MovieResult> getMovieResults() {
        return movieResults;
    }
}