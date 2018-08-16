package mapprr.deepak.gitsearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mapprr.deepak.gitsearch.GlideApp;
import mapprr.deepak.gitsearch.R;
import mapprr.deepak.gitsearch.activities.RepoDetailActivity;
import mapprr.deepak.gitsearch.models.Repo;

public class GitAdapter extends RecyclerView.Adapter<GitAdapter.ViewHolder> implements View.OnClickListener {
    private List<Repo> mRepos;
    private Context mContext;

    // View lookup cache
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgAvatar;
        public TextView name;
        public TextView fullName;
        public TextView watchCount;
        public TextView forkCount;
        public TextView starCount;
        public CardView cardView;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            imgAvatar = (ImageView)itemView.findViewById(R.id.imgAvatar);
            name = (TextView)itemView.findViewById(R.id.name);
            fullName = (TextView)itemView.findViewById(R.id.fullName);
            watchCount = (TextView)itemView.findViewById(R.id.watchCount);
            forkCount = (TextView)itemView.findViewById(R.id.forkCount);
            starCount = (TextView)itemView.findViewById(R.id.starCount);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }

    }

    public GitAdapter(Context context, ArrayList<Repo> aRepos) {
        mRepos = aRepos;
        mContext = context;
    }

// Usually involves inflating a layout from XML and returning the holder
    @Override
    public GitAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View repoView = inflater.inflate(R.layout.row_repo, parent, false);
        // Return a new holder instance

        GitAdapter.ViewHolder viewHolder = new GitAdapter.ViewHolder(repoView);

        return viewHolder;
    }


    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(GitAdapter.ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        Repo repo = mRepos.get(position);
        viewHolder.cardView.setTag(position);
        viewHolder.cardView.setOnClickListener(this);
        // Populate data into the template view using the data object
        viewHolder.name.setText(repo.getName());
        viewHolder.fullName.setText(repo.getFullName());
        viewHolder.watchCount.setText(repo.getWatchersCount()+"");
        viewHolder.starCount.setText(repo.getStarCount()+"");
        viewHolder.forkCount.setText(repo.getForksCount()+"");
        GlideApp.with(getContext())
                .load(Uri.parse(repo.getAvatarUrl()))
                .placeholder(R.drawable.ic_nocover)
                .into(viewHolder.imgAvatar);
        // Return the completed view to render on screen
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

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        switch (v.getId()) {
            case R.id.card_view:
                Intent myIntent = new Intent(mContext, RepoDetailActivity.class);
                myIntent.putExtra("Repo", (Serializable) mRepos.get(position));
                mContext.startActivity(myIntent);
                break;
        }
    }

}
