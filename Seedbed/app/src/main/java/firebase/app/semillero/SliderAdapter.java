package firebase.app.semillero;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {
  Context context;
  LayoutInflater layoutInflater;


    public SliderAdapter(Context context) {
        this.context = context;
    }

    //Arrays
    public int[] slide_images ={
            R.drawable.realtime,
            R.drawable.notificacionesborrar,
            R.drawable.scalable,

    };

    public String[] slide_headings ={
        "REAL DATA DISPLAY",
            "NOTIFICATIONS",
            "SCALABLE",
    };

    public String[] slide_descs ={
            "Thanks to the Firebase ICloud platform, data can be displayed in dynamic charts using its Realtime Database utility.",
            "Alert when irrigation is activated by sending a notification of a text message thanks to the Twilio platform.",
            "Thanks to the Open Source environment and platforms of Arduino and Raspberry Pi you can implement any imaginable functionality",

    };


    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View view = layoutInflater.inflate(R.layout.slide,container,false);
        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_images);
        TextView slideHeading = (TextView ) view.findViewById(R.id.slide_heading);
        TextView  slideDescription = (TextView ) view.findViewById(R.id.slide_desc);
        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_descs[position]);
        container.addView(view);

       return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
      container.removeView((RelativeLayout)object);
    }
}
