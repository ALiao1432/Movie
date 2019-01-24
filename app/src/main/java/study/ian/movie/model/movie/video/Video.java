package study.ian.movie.model.movie.video;

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

    public List<VideoResult> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", results=" + results +
                '}';
    }
}
