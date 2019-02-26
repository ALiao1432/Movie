package study.ian.movie.util;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

import study.ian.movie.R;

public class Config {

    private final String TAG = "Config";

    public static String REQUEST_LANGUAGE = "en-US";
    public static final boolean INCLUDE_ADULT = true;
    public static final int VISIBLE_THRESHOLD = 20;

    public Config(Context context) {

        configLanguage(context);
    }

    private void configLanguage(@NotNull Context context) {
        String[] languages = context.getResources().getStringArray(R.array.language_config);

        Optional<String> stringOptional = Arrays.stream(languages)
                .filter(l -> l.startsWith(Locale.getDefault().getLanguage()))
                .findFirst();

        stringOptional.ifPresent(s -> REQUEST_LANGUAGE = s);
    }
}
