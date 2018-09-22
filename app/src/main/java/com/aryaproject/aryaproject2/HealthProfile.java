package com.aryaproject.aryaproject2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;

public class HealthProfile extends AppCompatActivity {

    static TextView pulse;
    static TextView temp;
    static TextView sys;
    static TextView dia;
    public int a=0;
    double b=0.0;
    int c=0,d=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_profile);

        pulse=(TextView)findViewById(R.id.ht);
        temp=(TextView)findViewById(R.id.utemp);
        sys=(TextView)findViewById(R.id.bp);
        dia=(TextView)findViewById(R.id.ubp);

        Random r=new Random();

        int low=60,high=100,heartrate;
        double low1=36.5,high1=37.2,bodytemperature,bt;
        bodytemperature=high1-low1;
        int low2=90,high2=120;
        int low3=60,high3=80;
        int systolicpressure,diastolicpressure;
        for(int i=0;i<=100;i++)
        {
            heartrate= r.nextInt(high-low)+low;
            a=a+heartrate;
            bodytemperature=r.nextDouble()+low1;
            DecimalFormat df=new DecimalFormat("#.###");
            df.setRoundingMode(RoundingMode.CEILING);
            bt=Double.parseDouble(df.format(bodytemperature));
            b=b+bt;
            systolicpressure=r.nextInt(high2-low2)+low2;
            c=c+systolicpressure;
            diastolicpressure=r.nextInt(high3-low3)+low3;
            d=d+diastolicpressure;
        }
        a=a/100;
        c=c/100;
        d=d/100;
        b=b/100;
        String hrate=a+"",temperature=b+"",spressure=c+"",dpressure=d+"";

        temp.setText(temperature);
        dia.setText(dpressure);
        sys.setText(spressure);
        pulse.setText(hrate);

    }

}

