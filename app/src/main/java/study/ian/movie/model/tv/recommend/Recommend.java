package study.ian.movie.model.tv.recommend;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

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

    public List<RecommendResult> getResults() {
        return results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    @NotNull
    @Override
    public String toString() {
        return "Recommend{" +
                "page=" + page +
                ", results=" + results +
                ", total_pages=" + total_pages +
                ", total_results=" + total_results +
                '}';
    }
}
