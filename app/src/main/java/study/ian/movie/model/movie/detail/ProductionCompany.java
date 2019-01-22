package study.ian.movie.model.movie.detail;

import com.google.gson.annotations.SerializedName;

public class ProductionCompany {

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

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogo_path() {
        return logo_path;
    }

    public void setLogo_path(String logo_path) {
        this.logo_path = logo_path;
    }

    public String getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(String origin_country) {
        this.origin_country = origin_country;
    }
}
