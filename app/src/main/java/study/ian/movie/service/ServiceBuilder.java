package study.ian.movie.service;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.processors.PublishProcessor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import study.ian.networkstateutil.ConnectionType;
import study.ian.networkstateutil.NetworkStateChangeListener;
import study.ian.networkstateutil.NetworkStateUtil;

public class ServiceBuilder {

    private final static String API_BASE_URL = "https://api.themoviedb.org/";
    public final static String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w1280";
    public final static String CREDIT_BASE_URL = "https://image.tmdb.org/t/p/w1280";
    public final static String BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w1400_and_h450_face";
    public final static String PERSON_BASE_URL = "https://image.tmdb.org/t/p/original";
    public final static String API_KEY = "281abc6eb92e0bb92d58167a5f0e5e9a";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    private static final PublishProcessor<ConnectionType> connectionTypeProcessor = PublishProcessor.create();

    private ServiceBuilder() {
    }

    public static void watchNetworkState(Context context) {
        new NetworkStateUtil(context, new NetworkStateChangeListener() {
            @Override
            public void onConnected(ConnectionType connectionType) {
                connectionTypeProcessor.onNext(connectionType);
            }

            @Override
            public void onDisconnected() {

            }
        });
    }

    @NotNull
    public static <T> T getService(Class<T> tClass) {
        return retrofit.create(tClass);
    }

    public static Observable<ConnectionType> getConnectStateObservable() {
        return connectionTypeProcessor.toObservable();
    }
}
