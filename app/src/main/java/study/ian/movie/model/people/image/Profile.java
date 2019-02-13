package study.ian.movie.model.people.image;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class Profile {

    @SerializedName("aspect_ratio")
    private double aspect_ratio;

    @SerializedName("file_path")
    private String file_path;

    @SerializedName("height")
    private int height;

    @SerializedName("iso_639_1")
    private Object iso_639_1;

    @SerializedName("vote_average")
    private double vote_average;

    @SerializedName("vote_count")
    private int vote_count;

    @SerializedName("width")
    private int width;

    public double getAspect_ratio() {
        return aspect_ratio;
    }

    public String getFile_path() {
        return file_path;
    }

    public int getHeight() {
        return height;
    }

    public Object getIso_639_1() {
        return iso_639_1;
    }

    public double getVote_average() {
        return vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public int getWidth() {
        return width;
    }

    @NotNull
    @Override
    public String toString() {
        return "Profile{" +
                "aspect_ratio=" + aspect_ratio +
                ", file_path='" + file_path + '\'' +
                ", height=" + height +
                ", iso_639_1=" + iso_639_1 +
                ", vote_average=" + vote_average +
                ", vote_count=" + vote_count +
                ", width=" + width +
                '}';
    }
}
