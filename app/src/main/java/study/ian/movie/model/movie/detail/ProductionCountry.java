package study.ian.movie.model.movie.detail;

import com.google.gson.annotations.SerializedName;

class ProductionCountry {

    @SerializedName("iso_3166_1")
    private String iso_3166_1;

    @SerializedName("name")
    private String name;

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public String getName() {
        return name;
    }
}
