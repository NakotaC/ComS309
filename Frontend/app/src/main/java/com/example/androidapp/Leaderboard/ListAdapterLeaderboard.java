package com.example.androidapp.Leaderboard;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.androidapp.R;

import java.util.List;

public class ListAdapterLeaderboard extends ArrayAdapter<LeaderboardItemObject> {

    public ListAdapterLeaderboard(Context context, List<LeaderboardItemObject> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        LeaderboardItemObject item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_leaderboard, parent,
                    false);
        }

        // Lookup view for data population
        TextView itemName = convertView.findViewById(R.id.itemName);
        TextView itemScore = convertView.findViewById(R.id.itemScore);

        // Populate the data into the template view using the data object
        itemName.setText(item.getName());
        itemScore.setText(item.getScore());

        // Return the completed view to render on screen
        return convertView;
    }
}

