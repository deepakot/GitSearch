package mapprr.deepak.gitsearch.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import mapprr.deepak.gitsearch.GlideApp;
import mapprr.deepak.gitsearch.R;
import mapprr.deepak.gitsearch.adapters.UserListAdapter;
import mapprr.deepak.gitsearch.contract.GitSearchContract;
import mapprr.deepak.gitsearch.models.Contributor;
import mapprr.deepak.gitsearch.models.Repo;
import mapprr.deepak.gitsearch.net.GitSearchClientImpl;
import mapprr.deepak.gitsearch.presenter.GitSearchPresenter;
import mapprr.deepak.gitsearch.presenter.RepoDetailPresenter;

public class RepoDetailActivity extends AppCompatActivity implements GitSearchContract.RepoDetailView {
    private ImageView ivRepoCover;
    private TextView tvTitle;
    private TextView tvLink;
    private TextView tvDescription;
    private RecyclerView rvContributors;
    ProgressDialog pDialog;

    private UserListAdapter userListAdapter;
    private Repo repo;
    private ArrayList<Contributor> aContributors;
    private RepoDetailPresenter repoDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_detail);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ivRepoCover = (ImageView) findViewById(R.id.ivRepoCover);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvLink = (TextView) findViewById(R.id.tvLink);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        rvContributors = (RecyclerView) findViewById(R.id.rvContributors);
        aContributors = new ArrayList<>();
        userListAdapter = new UserListAdapter(this, aContributors);

        repo = (Repo) getIntent().getSerializableExtra("Repo");

        // attach the adapter to the RecyclerView
        rvContributors.setAdapter(userListAdapter);
        rvContributors.setNestedScrollingEnabled(false);
        // Set layout manager to position the items
        rvContributors.setLayoutManager(new GridLayoutManager(this, 3));
        if(repo !=null) {
            GitSearchContract.GitSearchModel gitSearchModel = new GitSearchClientImpl();
            repoDetailPresenter = new RepoDetailPresenter(gitSearchModel);
            repoDetailPresenter.bind(this);
            repoDetailPresenter.fetchContributors(repo.getContributorsUrl());

            tvTitle.setText(repo.getName());
            tvLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(RepoDetailActivity.this, WebviewActivity.class);
                    myIntent.putExtra("url", repo.getUrl());
                    startActivity(myIntent);
                }
            });

            tvDescription.setText(repo.getDescription());
            GlideApp.with(this)
                    .load(Uri.parse(repo.getAvatarUrl()))
                    .placeholder(R.drawable.ic_nocover)
                    .into(ivRepoCover);
        }
    }

    @Override
    public void updateUi(ArrayList<Contributor> contributors) {
        aContributors.clear();
        aContributors.addAll(contributors);
        userListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showDialog()
    {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        pDialog.setCancelable(false);
    }
    @Override
    public void hideDialog()
    {
        pDialog.hide();
    }
    @Override
    protected void onDestroy() {
        repoDetailPresenter.unbind();
        pDialog = null;
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
