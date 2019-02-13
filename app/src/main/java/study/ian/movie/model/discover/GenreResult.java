package study.ian.movie.model.discover;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

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

    @NotNull
    @Override
    public String toString() {
        return "GenreResult{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
