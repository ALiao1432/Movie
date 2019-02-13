package study.ian.movie.model.discover;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Genre {

    @SerializedName("genres")
    private List<GenreResult> genres;

    public List<GenreResult> getGenres() {
        return genres;
    }

    @NotNull
    @Override
    public String toString() {
        return "Genre{" +
                "genres=" + genres +
                '}';
    }
}
