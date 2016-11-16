package com.example.maaster.teacherassessment;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView;

public class ListActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getData();
    }

    public void openAssessment(View view) {
        Intent intent = new Intent(this, AssessmentActivity.class);
        startActivity(intent);
    }

    public void getData(){
        Integer[] imageId = {
                R.drawable.im_1,
                R.drawable.im_2,
                R.drawable.im_3,
                R.drawable.im_4
        };
        String[] name = {
                "ดร.ประภาพร รัตรธำรง",
                "ดร.มนวรรัตน์ ผ่องไพบูลย์",
                "ดร.วนิดา พฤทธิวิทยา",
                "ผศ.ดร.เด่นดวง ประดับสุวรรณ"
        } ;
        /*"ดร.ประภาพร รัตรธำรง",
                "ดร.มนวรรัตน์ ผ่องไพบูลย์",
                "ดร.วนิดา พฤทธิวิทยา",
                "ผศ.ดร.เด่นดวง ประดับสุวรรณ"*/
        String[] course = {
                "CS374",
                "CS374",
                "CS342",
                "CS223"
        } ;
        String[] section = {
                "40001",
                "40002",
                "60001",
                "20001"
        } ;

        CustomListActivity adapter = new CustomListActivity(ListActivity.this,imageId,name,course,section);
        ListView list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });




    }

    public void openAssessmentActivity(View view) {

        Intent intent = new Intent(this, AssessmentActivity.class);
        startActivity(intent);
    }
}
