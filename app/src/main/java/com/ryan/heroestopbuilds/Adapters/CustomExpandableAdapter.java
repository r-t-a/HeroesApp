package com.ryan.heroestopbuilds.Adapters;

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
import com.ryan.heroestopbuilds.Models.Heroes;
import com.ryan.heroestopbuilds.PicassoClient;
import com.ryan.heroestopbuilds.R;

import java.util.ArrayList;
import java.util.List;

public class CustomExpandableAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<Heroes> heroes;
    private ArrayList<Heroes> originalList;
    public CallBackInterface listener;

    public CustomExpandableAdapter(Context context, List<Heroes> heroes) {
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

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater factory = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = factory.inflate(R.layout.group_list,null);
        }
        TextView tv = convertView.findViewById(R.id.hero_text);
        ImageView iv = convertView.findViewById(R.id.hero_portrait);
        Button refresh = convertView.findViewById(R.id.refresh);
        Heroes i = heroes.get(groupPosition);
        final Heroes hero = new Heroes(i.getName(), i.getIcon().getPortrait());
        refresh.setFocusable(false);
        refresh.setOnClickListener(new View.OnClickListener() {
            String selection = "";
            @Override
            public void onClick(View v) {
                if(selection == null) {
                    return;
                }
                selection = hero.getName();
                if (context instanceof MainActivity) {
                    ((MainActivity)context).onRefreshButton(selection);
                }
            }
        });

        tv.setText(hero.getName());
        setImageView(hero, iv);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater factory = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = factory.inflate(R.layout.child_list,null);
        }
        TextView tv = convertView.findViewById(R.id.skill_text);
        Heroes i = heroes.get(groupPosition);
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
            ArrayList<Heroes> newList = new ArrayList<>();
            for(Heroes hero: originalList) {
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

    private void setImageView(Heroes hero, ImageView iv) {

        switch (hero.getName()){
            case "The Butcher":
                iv.setImageResource(R.drawable.the_butcher);
                break;
            case "Alexstrasza":
                iv.setImageResource(R.drawable.alexstrasza);
                break;
            case "Blaze":
                iv.setImageResource(R.drawable.blaze);
                break;
            case "Hanzo":
                iv.setImageResource(R.drawable.hanzo);
                break;
            case "Junkrat":
                iv.setImageResource(R.drawable.junkrat);
                break;
            case "Maiev":
                iv.setImageResource(R.drawable.maiev);
                break;
            case "Zul'jin":
                iv.setImageResource(R.drawable.zuljin);
                break;
            default:
                PicassoClient.downloadImage(context,hero.getIcon().getPortrait(), iv);
        }
    }
}
