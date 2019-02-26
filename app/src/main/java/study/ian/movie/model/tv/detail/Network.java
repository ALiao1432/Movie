package study.ian.movie.model.tv.detail;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

class Network {

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("logo_path")
    private String logo_path;

    @SerializedName("origin_country")
    private String origin_country;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getLogo_path() {
        return logo_path;
    }

    public String getOrigin_country() {
        return origin_country;
    }

    @NotNull
    @Override
    public String toString() {
        return "Network{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", logo_path='" + logo_path + '\'' +
                ", origin_country='" + origin_country + '\'' +
                '}';
    }
}
