package com.ryan.heroestopbuilds;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private CallBackInterface listener;

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
                switch (groupPosition) {
                    case 0:
                        selection = Constants.HERO_NAMES[0];
                        break;
                    case 1:
                        selection = Constants.HERO_NAMES[1];
                        break;
                    case 2:
                        selection = Constants.HERO_NAMES[2];
                        break;
                    case 3:
                        selection = Constants.HERO_NAMES[3];
                        break;
                    case 4:
                        selection = Constants.HERO_NAMES[4];
                        break;
                    case 5:
                        selection = Constants.HERO_NAMES[5];
                        break;
                    case 6:
                        selection = Constants.HERO_NAMES[6];
                        break;
                    case 7:
                        selection = Constants.HERO_NAMES[7];
                        break;
                    case 8:
                        selection = Constants.HERO_NAMES[8];
                        break;
                    case 9:
                        selection = Constants.HERO_NAMES[9];
                        break;
                    case 10:
                        selection = Constants.HERO_NAMES[10];
                        break;
                    case 11:
                        selection = Constants.HERO_NAMES[11];
                        break;
                    case 12:
                        selection = Constants.HERO_NAMES[12];
                        break;
                    case 13:
                        selection = Constants.HERO_NAMES[13];
                        break;
                    case 14:
                        selection = Constants.HERO_NAMES[14];
                        break;
                    case 15:
                        selection = Constants.HERO_NAMES[15];
                        break;
                    case 16:
                        selection = Constants.HERO_NAMES[16];
                        break;
                    case 17:
                        selection = Constants.HERO_NAMES[17];
                        break;
                    case 18:
                        selection = Constants.HERO_NAMES[18];
                        break;
                    case 19:
                        selection = Constants.HERO_NAMES[19];
                        break;
                    case 20:
                        selection = Constants.HERO_NAMES[20];
                        break;
                    case 21:
                        selection = Constants.HERO_NAMES[21];
                        break;
                    case 22:
                        selection = Constants.HERO_NAMES[22];
                        break;
                    case 23:
                        selection = Constants.HERO_NAMES[23];
                        break;
                    case 24:
                        selection = Constants.HERO_NAMES[24];
                        break;
                    case 25:
                        selection = Constants.HERO_NAMES[25];
                        break;
                    case 26:
                        selection = Constants.HERO_NAMES[26];
                        break;
                    case 27:
                        selection = Constants.HERO_NAMES[27];
                        break;
                    case 28:
                        selection = Constants.HERO_NAMES[28];
                        break;
                    case 29:
                        selection = Constants.HERO_NAMES[29];
                        break;
                    case 30:
                        selection = Constants.HERO_NAMES[30];
                        break;
                    case 31:
                        selection = Constants.HERO_NAMES[31];
                        break;
                    case 32:
                        selection = Constants.HERO_NAMES[32];
                        break;
                    case 33:
                        selection = Constants.HERO_NAMES[33];
                        break;
                    case 34:
                        selection = Constants.HERO_NAMES[34];
                        break;
                    case 35:
                        selection = Constants.HERO_NAMES[35];
                        break;
                    case 36:
                        selection = Constants.HERO_NAMES[36];
                        break;
                    case 37:
                        selection = Constants.HERO_NAMES[37];
                        break;
                    case 38:
                        selection = Constants.HERO_NAMES[38];
                        break;
                    case 39:
                        selection = Constants.HERO_NAMES[39];
                        break;
                    case 40:
                        selection = Constants.HERO_NAMES[40];
                        break;
                    default:
                        selection = null;
                        break;
                }
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
