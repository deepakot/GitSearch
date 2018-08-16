package mapprr.deepak.gitsearch.activities;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ImageView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import mapprr.deepak.gitsearch.GlideApp;
import mapprr.deepak.gitsearch.R;
import mapprr.deepak.gitsearch.adapters.RepoListAdapter;
import mapprr.deepak.gitsearch.contract.GitSearchContract;
import mapprr.deepak.gitsearch.models.Contributor;
import mapprr.deepak.gitsearch.models.Repo;
import mapprr.deepak.gitsearch.net.GitSearchClientImpl;
import mapprr.deepak.gitsearch.presenter.ContributorPresenter;
import mapprr.deepak.gitsearch.presenter.RepoDetailPresenter;

public class ContributorActivity extends AppCompatActivity implements GitSearchContract.ContributorView {
    private ImageView ivRepoCover;
    private RecyclerView rvRepoList;

    private GitSearchClientImpl client;
    private ArrayList<Repo> aRepos;
    private RepoListAdapter repoListAdapter;
    ProgressDialog pDialog;
    private ContributorPresenter contributorPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributor);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ivRepoCover = (ImageView) findViewById(R.id.ivRepoCover);
        rvRepoList = (RecyclerView) findViewById(R.id.rvRepoList);
        aRepos = new ArrayList<>();
        repoListAdapter = new RepoListAdapter(this, aRepos);
        rvRepoList.setAdapter(repoListAdapter);
        rvRepoList.setLayoutManager(new LinearLayoutManager(this));
        rvRepoList.setNestedScrollingEnabled(false);
        String imgUrl = "";
        String repo = "";
        if(getIntent()!=null) {
            imgUrl = getIntent().getStringExtra("image");
            repo = getIntent().getStringExtra("repo");
        }
        GlideApp.with(this)
                .load(Uri.parse(imgUrl))
                .placeholder(R.drawable.ic_nocover)
                .into(ivRepoCover);
        // Extract repo object from intent extras
        GitSearchContract.GitSearchModel gitSearchModel = new GitSearchClientImpl();
        contributorPresenter = new ContributorPresenter(gitSearchModel);
        contributorPresenter.bind(this);
        contributorPresenter.fetchRepos(repo);
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

    @Override
    public void updateUi(ArrayList<Repo> repos) {
        aRepos.clear();
        aRepos.addAll(repos);
        repoListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showDialog() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        pDialog.setCancelable(false);
    }

    @Override
    public void hideDialog() {
        pDialog.hide();
    }
    @Override
    protected void onDestroy() {
        contributorPresenter.unbind();
        pDialog = null;
        super.onDestroy();
    }
}
