package mapprr.deepak.gitsearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mapprr.deepak.gitsearch.R;
import mapprr.deepak.gitsearch.activities.RepoDetailActivity;
import mapprr.deepak.gitsearch.models.Repo;

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.ViewHolder> {
    private List<Repo> mRepos;
    private Context mContext;

    // View lookup cache
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUrl;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            tvUrl = (TextView)itemView.findViewById(R.id.tvUrl);
        }
    }

    public RepoListAdapter(Context context, ArrayList<Repo> aRepos) {
        mRepos = aRepos;
        mContext = context;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public RepoListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View repoView = inflater.inflate(R.layout.item_repourl, parent, false);
        // Return a new holder instance
        RepoListAdapter.ViewHolder viewHolder = new RepoListAdapter.ViewHolder(repoView);
        return viewHolder;
    }


    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(RepoListAdapter.ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        Repo repo = mRepos.get(position);
        // Populate data into the template view using the data object
        viewHolder.tvUrl.setText(repo.getName());
        // Return the completed view to render on screen
        viewHolder.tvUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mContext, RepoDetailActivity.class);
                myIntent.putExtra("Repo", (Serializable) mRepos.get(position));
                mContext.startActivity(myIntent);
            }
        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mRepos.size();
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }
}
