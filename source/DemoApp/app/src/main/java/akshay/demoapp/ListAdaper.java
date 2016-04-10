package akshay.demoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Akshay on 10-Apr-16.
 */
public class ListAdaper extends ArrayAdapter<GitHubUser> {
    Context context;
    ArrayList<GitHubUser> userArrayList;

    static class ViewHolder {
        TextView tvName, tvCommit, tvCommitMessage;
    }

    public ListAdaper(Context context, ArrayList<GitHubUser> userArrayList) {
        super(context, android.R.layout.simple_list_item_1);
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @Override
    public int getCount() {
        return userArrayList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        GitHubUser user = userArrayList.get(position);

        if (convertView == null) {

            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_list_item, null);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvCommit = (TextView) convertView.findViewById(R.id.tvCommit);
            holder.tvCommitMessage = (TextView) convertView.findViewById(R.id.tvCommitMessage);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(user.getName());
        holder.tvCommit.setText(user.getCommit());
        holder.tvCommitMessage.setText(user.getCommitMessage());
        return convertView;
    }
}
