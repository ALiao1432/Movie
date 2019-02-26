package study.ian.movie;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import io.reactivex.schedulers.Schedulers;
import study.ian.movie.fragment.FragmentDiscover;
import study.ian.movie.fragment.FragmentMovies;
import study.ian.movie.fragment.FragmentPeople;
import study.ian.movie.fragment.FragmentTvShows;
import study.ian.movie.service.ServiceBuilder;
import study.ian.movie.util.Config;
import study.ian.networkstateutil.RxNetworkStateUtil;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private final FragmentDiscover fragmentDiscover = new FragmentDiscover();
    private final FragmentMovies fragmentMovies = new FragmentMovies();
    private final FragmentTvShows fragmentTvShows = new FragmentTvShows();
    private final FragmentPeople fragmentPeople = new FragmentPeople();
    private final FragmentManager fragmentManager = getSupportFragmentManager();

    private BottomNavigationView bottomNavView;
    private Fragment activeFragment;

    private final BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = menuItem -> {

        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.fading_in, R.anim.fading_out);

        switch (menuItem.getItemId()) {
            case R.id.item_discover:
                fragmentTransaction.hide(activeFragment).show(fragmentDiscover).commit();
                activeFragment = fragmentDiscover;
                return true;
            case R.id.item_movies:
                fragmentTransaction.hide(activeFragment).show(fragmentMovies).commit();
                activeFragment = fragmentMovies;
                return true;
            case R.id.item_tv_shows:
                fragmentTransaction.hide(activeFragment).show(fragmentTvShows).commit();
                activeFragment = fragmentTvShows;
                return true;
            case R.id.item_people:
                fragmentTransaction.hide(activeFragment).show(fragmentPeople).commit();
                activeFragment = fragmentPeople;
                return true;
            default:
                break;
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Config(this);
        ServiceBuilder.watchNetworkState(this);

        findViews();
        setFragmentManager();
        setBottomNavView();

        bottomNavView.setSelectedItemId(R.id.item_movies);
    }

    private void findViews() {
        bottomNavView = findViewById(R.id.bottomNavigation);
    }

    private void setFragmentManager() {
        fragmentManager.beginTransaction().add(R.id.frameLayout, fragmentPeople).hide(fragmentPeople).commit();
        fragmentManager.beginTransaction().add(R.id.frameLayout, fragmentTvShows).hide(fragmentTvShows).commit();
        fragmentManager.beginTransaction().add(R.id.frameLayout, fragmentMovies).hide(fragmentMovies).commit();
        fragmentManager.beginTransaction().add(R.id.frameLayout, fragmentDiscover).commit();
        activeFragment = fragmentDiscover;
    }

    private void setBottomNavView() {
        bottomNavView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();
        if (isShouldHideInput(v, ev)) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v instanceof EditText) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left) || !(event.getX() < right)
                    || !(event.getY() > top) || !(event.getY() < bottom);
        }
        return false;
    }
}
