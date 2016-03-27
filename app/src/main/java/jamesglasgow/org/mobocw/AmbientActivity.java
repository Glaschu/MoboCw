package jamesglasgow.org.mobocw;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class AmbientActivity extends AppCompatActivity {
    ImageView view;
    AnimationDrawable frameAnimation;
    int size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambient);
        Bundle extras =getIntent().getExtras();
        if(extras !=null){
            size =extras.getInt("listSize");

        }
        // Typecasting the Image View
        view = (ImageView) findViewById(R.id.imageView);

        // Setting animation_list.xml as the background of the image view
        if(size<25) {
            view.setBackgroundResource(R.drawable.anim);
        }else if (size<75) {
            view.setBackgroundResource(R.drawable.anim1);}
        else if (size<150) {
            view.setBackgroundResource(R.drawable.anim2);}
        else  {
            view.setBackgroundResource(R.drawable.anim4);}
        // Typecasting the Animation Drawable
        frameAnimation = (AnimationDrawable) view.getBackground();
    }

    // Called when Activity becomes visible or invisible to the user
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            // Starting the animation when in Focus
            frameAnimation.start();
        } else {
            // Stoping the animation when not in Focus
            frameAnimation.stop();
        }
    }
}
