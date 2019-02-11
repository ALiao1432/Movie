package study.ian.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import study.ian.movie.R;
import study.ian.movie.model.people.image.Profile;
import study.ian.movie.service.ServiceBuilder;

public class PersonDetailImageAdapter extends PagerAdapter {

    private final String TAG = "PersonDetailImageAdapter";

    private List<Profile> profileList;
    private Context context;

    public PersonDetailImageAdapter(Context context, List<Profile> profileList) {
        this.context = context;
        this.profileList = profileList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.holder_person_detail_image, container, false);

        RequestOptions options = new RequestOptions().fitCenter();
        Glide.with(context)
                .load(ServiceBuilder.PERSON_BASE_URL + profileList.get(position).getFile_path())
                .apply(options)
                .into((ImageView) v.findViewById(R.id.personDetailImage));

        container.addView(v);
        return v;
    }

    @Override
    public int getCount() {
        return profileList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}