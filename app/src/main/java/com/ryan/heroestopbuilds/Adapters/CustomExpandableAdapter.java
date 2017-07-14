package com.ryan.heroestopbuilds.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ryan.heroestopbuilds.Interface.CallBackInterface;
import com.ryan.heroestopbuilds.MainActivity;
import com.ryan.heroestopbuilds.R;
import com.ryan.heroestopbuilds.Utilities.HeroSelection;

import java.util.ArrayList;
import java.util.List;

public class CustomExpandableAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<HeroSelection> heroes;
    private ArrayList<HeroSelection> originalList;
    public CallBackInterface listener;

    public CustomExpandableAdapter(Context context, List<HeroSelection> heroes) {
        this.context = context;
        this.heroes = new ArrayList<>();
        this.heroes.addAll(heroes);
        this.originalList = new ArrayList<>();
        this.originalList.addAll(heroes);
    }

    public CustomExpandableAdapter(CallBackInterface listener) {
        this.listener = listener;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return heroes.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return heroes.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return heroes.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    // Ok to suppress this for Lint
    @SuppressLint("InflateParams")
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater factory = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = factory.inflate(R.layout.group_list,null);
        }
        TextView tv = (TextView)convertView.findViewById(R.id.hero_text);
        ImageView iv = (ImageView)convertView.findViewById(R.id.hero_portrait);
        Button refresh = (Button)convertView.findViewById(R.id.refresh);
        final HeroSelection i = heroes.get(groupPosition);
        refresh.setFocusable(false);
        refresh.setOnClickListener(new View.OnClickListener() {
            String selection = "";
            @Override
            public void onClick(View v) {
                // Rick's "6Cans Tricky Dicky" refactor ;)
                if(selection == null) {
                    return;
                }
                selection = i.getName();
                if (context instanceof MainActivity) {
                    ((MainActivity)context).onRefreshButton(selection);
                }
            }
        });
        tv.setText(i.getName());
        iv.setImageResource(i.getPortrait());
        return convertView;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater factory = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = factory.inflate(R.layout.child_list,null);
        }
        TextView tv = (TextView)convertView.findViewById(R.id.skill_text);
        HeroSelection i = heroes.get(groupPosition);
        String selection = i.getName();
        if (context instanceof MainActivity) {
            String skills = ((MainActivity)context).onChildPress(selection);
            tv.setText(skills);
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    public void filterData(String query) {
        query = query.toLowerCase();
        heroes.clear();

        if(query.isEmpty()) {
            heroes.addAll(originalList);
        } else {
            ArrayList<HeroSelection> newList = new ArrayList<>();
            for(HeroSelection hero: originalList) {
                if(hero.getName().toLowerCase().contains(query)) {
                    newList.add(hero);
                }
                if(newList.size() > 0) {
                    heroes = newList;
                }
            }
        }
        notifyDataSetChanged();
    }
}
