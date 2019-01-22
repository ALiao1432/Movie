package study.ian.movie.model.movie.detail;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Video {

    @SerializedName("id")
    private int id;

    @SerializedName("results")
    private List<VideoResult> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<VideoResult> getResults() {
        return results;
    }

    public void setResults(List<VideoResult> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", results=" + results +
                '}';
    }
}
