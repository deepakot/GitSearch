package mapprr.deepak.gitsearch.presenter;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import mapprr.deepak.gitsearch.contract.GitSearchContract;
import mapprr.deepak.gitsearch.models.Contributor;
import mapprr.deepak.gitsearch.models.Repo;

/**
 * Created by Sharmaji on 8/17/2018.
 */

public class ContributorPresenter {

    GitSearchContract.ContributorView contributorView;
    private GitSearchContract.GitSearchModel gitSearchModel;

    public ContributorPresenter(GitSearchContract.GitSearchModel gitSearchModel) {
        this.gitSearchModel = gitSearchModel;
    }

    public void bind(GitSearchContract.ContributorView contributorView) {
        this.contributorView = contributorView;
    }

    public void unbind() {
        contributorView = null;
    }


    public void fetchRepos(final String repo)  {
        contributorView.showDialog();
        gitSearchModel.getContributors(repo, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                if(response != null) {
                    final ArrayList<Repo> repos = Repo.fromJson(response);
                    contributorView.updateUi(repos);
                }
                contributorView.hideDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                contributorView.hideDialog();
            }
        });
    }

}
