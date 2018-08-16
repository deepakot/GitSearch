package mapprr.deepak.gitsearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import mapprr.deepak.gitsearch.GlideApp;
import mapprr.deepak.gitsearch.R;
import mapprr.deepak.gitsearch.activities.ContributorActivity;
import mapprr.deepak.gitsearch.models.Contributor;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    private List<Contributor> contributors;
    private Context mContext;

    // View lookup cache
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivCover;
        public TextView tvName;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            ivCover = (ImageView)itemView.findViewById(R.id.ivCover);
            tvName = (TextView)itemView.findViewById(R.id.tvName);
        }
    }

    public UserListAdapter(Context context, ArrayList<Contributor> contributorArrayList) {
        contributors = contributorArrayList;
        mContext = context;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public UserListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View repoView = inflater.inflate(R.layout.item_user, parent, false);
        // Return a new holder instance
        UserListAdapter.ViewHolder viewHolder = new UserListAdapter.ViewHolder(repoView);
        return viewHolder;
    }


    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(UserListAdapter.ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        final Contributor contributor = contributors.get(position);
        // Populate data into the template view using the data object
        viewHolder.tvName.setText(contributor.getLogin());
        GlideApp.with(getContext())
                .load(Uri.parse(contributor.getAvatarUrl()))
                .placeholder(R.drawable.ic_nocover)
                .into(viewHolder.ivCover);
        viewHolder.ivCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mContext, ContributorActivity.class);
                myIntent.putExtra("image", contributor.getAvatarUrl());
                myIntent.putExtra("repo", contributor.getReposUrl());
                mContext.startActivity(myIntent);
            }
        });
        // Return the completed view to render on screen
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return contributors.size();
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }
}
