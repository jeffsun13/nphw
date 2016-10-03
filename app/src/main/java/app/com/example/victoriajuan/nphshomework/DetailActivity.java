package app.com.example.victoriajuan.nphshomework;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(GlobalVariables.getClassType()+" Homework Details");

        TextView detailTitle = (TextView) findViewById(R.id.detail_title);
        detailTitle.setText(GlobalVariables.getClassType());
        TextView descr = (TextView) findViewById(R.id.detail_details);
        descr.setText(GlobalVariables.getClassDetails());
        ImageView iconImg = (ImageView) findViewById(R.id.detail_icon);
        iconImg.setImageResource(GlobalVariables.getIconType());

    }



}
