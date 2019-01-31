package study.ian.movie.model.tv.keyword;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Keyword {

    @SerializedName("id")
    private int id;

    @SerializedName("results")
    private List<KeywordResult> keywords;

    public int getId() {
        return id;
    }

    public List<KeywordResult> getKeywords() {
        return keywords;
    }
}
