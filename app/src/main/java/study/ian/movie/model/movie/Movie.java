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
    private List<DetailResult> detailResults = null;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<DetailResult> getDetailResults() {
        return detailResults;
    }

    public void setDetailResults(List<DetailResult> detailResults) {
        this.detailResults = detailResults;
    }
}