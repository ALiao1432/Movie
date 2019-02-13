package study.ian.movie.model.people.popular;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PopularResult {

    @SerializedName("profile_path")
    private String profile_path;

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("id")
    private int id;

    @SerializedName("known_for")
    private List<KnownFor> known_for;

    @SerializedName("name")
    private String name;

    @SerializedName("popularity")
    private double popularity;

    public String getProfile_path() {
        return profile_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public int getId() {
        return id;
    }

    public List<KnownFor> getKnown_for() {
        return known_for;
    }

    public String getName() {
        return name;
    }

    public double getPopularity() {
        return popularity;
    }

    @NotNull
    @Override
    public String toString() {
        return "PopularResult{" +
                "profile_path='" + profile_path + '\'' +
                ", adult=" + adult +
                ", id=" + id +
                ", known_for=" + known_for +
                ", name='" + name + '\'' +
                ", popularity=" + popularity +
                '}';
    }
}
