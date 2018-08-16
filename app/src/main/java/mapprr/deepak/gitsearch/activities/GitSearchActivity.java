package mapprr.deepak.gitsearch.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import mapprr.deepak.gitsearch.R;
import mapprr.deepak.gitsearch.adapters.GitAdapter;
import mapprr.deepak.gitsearch.contract.GitSearchContract;
import mapprr.deepak.gitsearch.fragment.GitFilterFragment;
import mapprr.deepak.gitsearch.models.Repo;
import mapprr.deepak.gitsearch.net.GitSearchClientImpl;
import mapprr.deepak.gitsearch.presenter.GitSearchPresenter;


public class GitSearchActivity extends AppCompatActivity implements GitSearchContract.GitSearchView, SearchView.OnQueryTextListener, GitFilterFragment.OnButtonclickListener {
    private RecyclerView rvRepos;
    private FloatingActionButton fab;
    private LinearLayout linearLayout;
    private GitAdapter gitAdapter;
    private ArrayList<Repo> arepos;
    private int i=0, sortBy=0, orderBy=0;
    ProgressDialog pDialog;
    private GitSearchPresenter gitSearchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_list);
        rvRepos = (RecyclerView) findViewById(R.id.rvRepos);
        fab = (FloatingActionButton) findViewById(R.id.filter_fab);
        linearLayout = (LinearLayout) findViewById(R.id.noResult);
        arepos = new ArrayList<>();
        gitAdapter = new GitAdapter(this, arepos);
        rvRepos.setAdapter(gitAdapter);
        rvRepos.setLayoutManager(new LinearLayoutManager(this));

        GitSearchContract.GitSearchModel gitSearchModel = new GitSearchClientImpl();
        gitSearchPresenter = new GitSearchPresenter(gitSearchModel);
        gitSearchPresenter.bind(this);
        gitSearchPresenter.fetchRepos("Dialog");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.addToBackStack(null);
                DialogFragment newFragment =
                        GitFilterFragment.newInstance(sortBy, orderBy);
                newFragment.show(ft, "dialog");
            }
        });
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        return true; // Return true to collapse action view
                    }
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return true; // Return true to expand action view
                    }
                });
        return true;
    }

    @Override
    protected void onDestroy() {
        gitSearchPresenter.unbind();
        pDialog = null;
        super.onDestroy();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        gitSearchPresenter.fetchRepos(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onApply(int sortBy, final int orderBy) {
        this.sortBy = sortBy;
        this.orderBy = orderBy;
        gitSearchPresenter.sortRepos(arepos, sortBy, orderBy);
    }

    @Override
    public void updateUi(ArrayList<Repo> repos) {
        if (repos.isEmpty()) {
            rvRepos.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            rvRepos.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
        }
        arepos.clear();
        arepos.addAll(repos);
        gitAdapter.notifyDataSetChanged();
    }
}
