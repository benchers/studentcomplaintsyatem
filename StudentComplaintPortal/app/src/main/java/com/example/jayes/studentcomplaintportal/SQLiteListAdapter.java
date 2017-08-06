package com.example.jayes.studentcomplaintportal;
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SQLiteListAdapter extends BaseAdapter {
	
    Context context;
    ArrayList<String> aid = new ArrayList<String>();
    ArrayList<String> amessage = new ArrayList<String>();
    ArrayList<String> asubject = new ArrayList<String>();
    ArrayList<String> aauthority = new ArrayList<String>();
    ArrayList<String> adate = new ArrayList<String>();
    ArrayList<String> atime = new ArrayList<String>();
    ArrayList<String> apriority = new ArrayList<String>();

    public SQLiteListAdapter(Context context2, ArrayList<String> id, ArrayList<String> amessage, ArrayList<String> asubject, ArrayList<String> aauthority, ArrayList<String> adate, ArrayList<String> atime, ArrayList<String> apriority)
    {
    	this.context = context2;
        this.aid = id;
        this.amessage = amessage;
        this.asubject = asubject;
        this.aauthority = aauthority;
        this.adate = adate;
        this.atime = atime;
        this.apriority = apriority;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return aid.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int position, View child, ViewGroup parent) {
    	
        Holder holder;
        
        LayoutInflater layoutInflater;
        
        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.listviewdatalayout, null);
            
            holder = new Holder();
            
            holder.ida = (TextView) child.findViewById(R.id.id);
            holder.prioritya = (TextView) child.findViewById(R.id.priority);
            holder.messagea = (TextView) child.findViewById(R.id.complaint);
            holder.datea = (TextView) child.findViewById(R.id.date);
            holder.timea = (TextView) child.findViewById(R.id.time);
            holder.subjecta = (TextView) child.findViewById(R.id.subject);
            holder.authoritya = (TextView) child.findViewById(R.id.authority);

            child.setTag(holder);
            
        } else {
        	
        	holder = (Holder) child.getTag();
        }
        holder.ida.setText(aid.get(position));
        holder.prioritya.setText(apriority.get(position));
        holder.messagea.setText(amessage.get(position));
        holder.datea.setText(adate.get(position));
        holder.timea.setText(atime.get(position));
        holder.subjecta.setText(asubject.get(position));
        holder.authoritya.setText(aauthority.get(position));
        return child;
    }

    public class Holder {
        TextView ida;
        TextView messagea;
        TextView subjecta;
        TextView authoritya;
        TextView datea;
        TextView timea;
        TextView prioritya;

    }

}