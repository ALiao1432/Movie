package study.ian.movie.model.movie.keyword;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Keyword {

    @SerializedName("id")
    private int id;

    @SerializedName("keywords")
    private List<KeywordResult> keywords;
}
