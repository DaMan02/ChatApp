package com.dayal.talkative.data;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dayal.talkative.R;
import com.dayal.talkative.model.Message;

import java.util.List;

public class ChatAdapter extends ArrayAdapter<Message>{
    private String mUserId;

    public ChatAdapter(@NonNull Context context, String userId, List<Message> messages) {
        super(context, 0, messages);
        mUserId = userId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_row,parent,false);
                  final ViewHolder holder = new ViewHolder();
                holder.body = (TextView)convertView.findViewById(R.id.chat_text);
                 convertView.setTag(holder);

            }
       final Message message = (Message) getItem(position);
        final ViewHolder holder = (ViewHolder)convertView.getTag();
        final boolean isMe = message.getUserId().equals(mUserId);
        if (isMe){
            holder.body.setGravity(Gravity.RIGHT);
        }else{
            holder.body.setGravity(Gravity.LEFT);
        }

        holder.body.setText(message.getBody());
      return convertView;
    }

    class ViewHolder{
        public TextView body;
    }
}
