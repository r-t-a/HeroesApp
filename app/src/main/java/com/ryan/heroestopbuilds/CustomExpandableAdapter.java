package com.ryan.heroestopbuilds;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Custom Adapter for the Expandable List to be used for all the Heroes
 * This will override a regular Expandable List since I want to use
 * images and an array list to hold the information.  The usual getters
 * and setters are here that are required for a BaseExpandableListAdapter
 *
 * @author ryan
 */
public class CustomExpandableAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<Heroes> heroes;
    private ArrayList<Skills> skillsArrayList;
    public CallBackInterface listener;

    public CustomExpandableAdapter(Context context, ArrayList<Heroes> heroes) {
        this.context = context;
        this.heroes = heroes;
    }

    public CustomExpandableAdapter(CallBackInterface listener) {
        this.listener = listener;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        skillsArrayList = heroes.get(groupPosition).getSkills();
        return skillsArrayList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    // Suppress is ok in this situation since we rebuild our child view at refresh
    @SuppressLint("InflateParams")
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
    public Object getGroup(int groupPosition) {
        return heroes.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return heroes.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        skillsArrayList = heroes.get(groupPosition).getSkills();
        if(skillsArrayList.size() == 0) {
            return 0;
        } else {
            return skillsArrayList.size();
        }
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
        Heroes heroes = (Heroes) getGroup(groupPosition);
        if(convertView == null) {
            LayoutInflater factory = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = factory.inflate(R.layout.group_list,null);
        }
        TextView tv = (TextView)convertView.findViewById(R.id.hero_text);
        ImageView iv = (ImageView)convertView.findViewById(R.id.hero_portrait);
        Button refresh = (Button)convertView.findViewById(R.id.refresh);
        tv.setText(heroes.getName());
        iv.setImageResource(heroes.getPortrait());
        refresh.setFocusable(false);
        refresh.setOnClickListener(new View.OnClickListener() {
            String selection = "";
            @Override
            public void onClick(View v) {
                // Rick's "6Cans Tricky Dicky" refactor ;)
                if(selection == null) {
                    return;
                }
                selection = Constants.HERO_NAMES[groupPosition];
                if (context instanceof MainActivity) {
                    ((MainActivity)context).onRefreshButton(selection);
                }
            }
        });
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
}
