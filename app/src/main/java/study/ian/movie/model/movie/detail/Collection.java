package study.ian.movie.model.movie.detail;

import com.google.gson.annotations.SerializedName;

public class Collection {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("poster_path")
    private String poster_path;

    @SerializedName("backdrop_path")
    private String backdrop_path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }
}
