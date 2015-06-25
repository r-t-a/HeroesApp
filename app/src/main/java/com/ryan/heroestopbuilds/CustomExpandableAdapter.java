package com.ryan.heroestopbuilds;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Custom Adapter for the Expandable List to be used for all the Heroes
 * This will override a regular Expandable List since I want to use
 * images and an array list to hold the information.  The usual getters
 * and setters are here that are required for a BaseExpandableListAdapter
 *
 * Created by ryan on 6/18/15.
 */
public class CustomExpandableAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<Heroes> heroes;

    public CustomExpandableAdapter(Context context, ArrayList<Heroes> heroes) {
        this.context = context;
        this.heroes = heroes;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<Skills> skillsArrayList = heroes.get(groupPosition).getSkills();
        return skillsArrayList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        Skills skills = (Skills) getChild(groupPosition,childPosition);
        if(convertView == null) {
            LayoutInflater factory = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = factory.inflate(R.layout.child_list,null);
        }
        TextView tv = (TextView)convertView.findViewById(R.id.skill_text);
        tv.setText(skills.getName());

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<Skills>skillsArrayList = heroes.get(groupPosition).getSkills();
        return skillsArrayList.size();
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
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Heroes heroes = (Heroes) getGroup(groupPosition);
        if(convertView == null) {
            LayoutInflater factory = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = factory.inflate(R.layout.group_list,null);
        }
        TextView tv = (TextView)convertView.findViewById(R.id.hero_text);
        ImageView iv = (ImageView)convertView.findViewById(R.id.hero_portrait);
        tv.setText(heroes.getName());
        iv.setImageResource(heroes.getPortrait());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
