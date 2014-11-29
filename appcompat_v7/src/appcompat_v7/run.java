package appcompat_v7;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.appcompat.R;
import android.util.Log;

/**
 * Created by user on 29/11/14.
 */
public class main extends Activity {
    String tag = "GyroSensor";


    //called when it is first created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

    }
}