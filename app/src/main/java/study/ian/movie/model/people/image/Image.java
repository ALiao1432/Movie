package study.ian.movie.model.people.image;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Image {

    @SerializedName("id")
    private int id;

    @SerializedName("profiles")
    private List<Profile> profiles;

    public int getId() {
        return id;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", profiles=" + profiles +
                '}';
    }
}
