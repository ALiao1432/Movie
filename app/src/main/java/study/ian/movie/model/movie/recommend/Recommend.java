package study.ian.movie.model.movie.recommend;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recommend {

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<RecommendResult> results;

    @SerializedName("total_pages")
    private int total_pages;

    @SerializedName("total_results")
    private int total_results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<RecommendResult> getResults() {
        return results;
    }

    public void setResults(List<RecommendResult> results) {
        this.results = results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }
}
