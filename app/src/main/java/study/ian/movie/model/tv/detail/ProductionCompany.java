package study.ian.movie.model.tv.detail;

import com.google.gson.annotations.SerializedName;

class ProductionCompany {

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
}
