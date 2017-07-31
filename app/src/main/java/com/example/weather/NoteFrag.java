package com.example.weather;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.weather.adapter.note.NoteAdapter;
import com.example.weather.adapter.note.NoteItem;
import com.example.weather.adapter.weather.WeatherAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.example.weather.WeatherFrag.REQUESTCODE;

/**
 * Created by 滕磊 on 2017/7/19.
 */

public class NoteFrag extends Fragment {
    public final static int NOTEREQUEST =2;
    private View view;
    private ArrayList<NoteItem> items = new ArrayList<NoteItem>();
    private ListView note_list;
    protected NoteAdapter noteadapter=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.frag_note, container, false);


        ImageButton edit_note = view.findViewById(R.id.edit_note);
        noteadapter = new NoteAdapter(getActivity(), items);
        note_list = (ListView) view.findViewById(R.id.note_container);
        note_list.setAdapter(noteadapter);

        edit_note.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity(), NoteEditActivity.class);
                startActivityForResult(intent, NOTEREQUEST);
            }
        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            if (requestCode == NOTEREQUEST) {
                  String title = data.getStringExtra("title");
                  if (!title.equals("empty")){
//                      String content = data.getStringExtra("content");
//                      String edit_time = data.getStringExtra("edit_time");
//                      items.add(new NoteItem(title,content, edit_time));
//                      noteadapter.notifyDataSetChanged();
                  }
//                this.show_city = data.getStringExtra("show_city");
//                Log.i("reserror",this.show_city);
//                startGetData(this.show_city,false);
//                sharedPreferences.edit().putString("send_city",this.show_city).commit();
            }
        }

    }
}
