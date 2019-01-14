package study.ian.movie;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import study.ian.movie.fragment.FragmentDiscover;
import study.ian.movie.fragment.FragmentMovies;
import study.ian.movie.fragment.FragmentPeople;
import study.ian.movie.fragment.FragmentTvShows;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private FragmentDiscover fragmentDiscover = new FragmentDiscover();
    private FragmentMovies fragmentMovies = new FragmentMovies();
    private FragmentTvShows fragmentTvShows = new FragmentTvShows();
    private FragmentPeople fragmentPeople = new FragmentPeople();
    private FragmentManager fragmentManager = getSupportFragmentManager();

    private BottomNavigationView bottomNavView;
    private Fragment activeFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.item_discover:
                        fragmentManager.beginTransaction().hide(activeFragment).show(fragmentDiscover).commit();
                        activeFragment = fragmentDiscover;
                        return true;
                    case R.id.item_movies:
                        fragmentManager.beginTransaction().hide(activeFragment).show(fragmentMovies).commit();
                        activeFragment = fragmentMovies;
                        return true;
                    case R.id.item_tv_shows:
                        fragmentManager.beginTransaction().hide(activeFragment).show(fragmentTvShows).commit();
                        activeFragment = fragmentTvShows;
                        return true;
                    case R.id.item_people:
                        fragmentManager.beginTransaction().hide(activeFragment).show(fragmentPeople).commit();
                        activeFragment = fragmentPeople;
                        return true;
                    default:
                        break;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        setFragmentManager();
        setBottomNavView();

        // for test
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
}
