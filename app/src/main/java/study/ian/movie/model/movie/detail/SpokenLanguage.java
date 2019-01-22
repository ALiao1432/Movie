package study.ian.movie.model.movie.detail;

import com.google.gson.annotations.SerializedName;

public class SpokenLanguage {

    @SerializedName("iso_639_1")
    private String iso_639_1;

    @SerializedName("name")
    private String name;

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
