package study.ian.movie.model.discover;

import com.google.gson.annotations.SerializedName;

public class GenreResult {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "GenreResult{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
