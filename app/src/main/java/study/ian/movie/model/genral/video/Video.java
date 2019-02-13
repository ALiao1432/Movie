package study.ian.movie.model.genral.video;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

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

    @NotNull
    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", results=" + results +
                '}';
    }
}
