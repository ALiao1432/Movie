package study.ian.movie.model.people.popular;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Popular {

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<PopularResult> results;

    @SerializedName("total_results")
    private int total_results;

    @SerializedName("total_pages")
    private int total_pages;

    public int getPage() {
        return page;
    }

    public List<PopularResult> getResults() {
        return results;
    }

    public int getTotal_results() {
        return total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    @NotNull
    @Override
    public String toString() {
        return "Popular{" +
                "page=" + page +
                ", results=" + results +
                ", total_results=" + total_results +
                ", total_pages=" + total_pages +
                '}';
    }
}
