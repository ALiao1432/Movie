package study.ian.movie.util;

import android.content.Context;
import android.util.Log;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

import study.ian.movie.R;

public class LanguageConfig {

    private final String TAG = "LanguageConfig";

    public static String REQUEST_LANGUAGE = "en-US";

    public LanguageConfig(Context context) {
        String[] languages = context.getResources().getStringArray(R.array.language_config);

         Optional<String> stringOptional = Arrays.stream(languages)
                .filter(l -> l.startsWith(Locale.getDefault().getLanguage()))
                .findFirst();

        stringOptional.ifPresent(s -> REQUEST_LANGUAGE = s);
    }
}
