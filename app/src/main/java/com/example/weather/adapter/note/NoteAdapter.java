package com.example.weather.adapter.note;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.weather.R;
import java.util.ArrayList;

/**
 * Created by 滕磊 on 2017/7/26.
 */

public class NoteAdapter extends BaseAdapter{
        private ArrayList<NoteItem> items = new ArrayList<NoteItem>();
        private Context context;
        private class Holder{
            private TextView title;
            private TextView start_time;
            private ImageButton delete_button;
        }
        public NoteAdapter(Context context,ArrayList<NoteItem> items){
            this.items = items;
            this.context = context;
        }
        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Holder holder=null;
            final int delete_num=i;
            if(view==null){
                holder = new Holder();
                view = LayoutInflater.from(this.context).inflate(R.layout.note_list_item,null);
                holder.title = view.findViewById(R.id.show_title);
                holder.start_time = view.findViewById(R.id.edit_time);
                holder.delete_button = view.findViewById(R.id.delete_plan);
                view.setTag(holder);
            }
            else{
                holder = (Holder)view.getTag();
            }
            if(items.get(i).getTitle()==null){
                holder.title.setText("（无标题）");
            }
            else{
                holder.title.setText(items.get(i).getTitle());
            }
            holder.start_time.setText(items.get(i).getStart_date()+"/"+items.get(i).getStart_time()+"-"+items.get(i).getEnd_date()+"/"+items.get(i).getEnd_time());
            holder.delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete_item(delete_num);
                }
            });
            return view;
        }
        public void delete_item(int index){
            items.remove(index);
            this.notifyDataSetChanged();
        }
    }


