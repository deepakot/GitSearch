package mapprr.deepak.gitsearch.presenter;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import cz.msebera.android.httpclient.Header;
import mapprr.deepak.gitsearch.contract.GitSearchContract;
import mapprr.deepak.gitsearch.models.Contributor;
import mapprr.deepak.gitsearch.models.Repo;
import mapprr.deepak.gitsearch.net.GitSearchClientImpl;

/**
 * Created by Sharmaji on 8/17/2018.
 */

public class RepoDetailPresenter {

    GitSearchContract.RepoDetailView repoDetailView;
    private GitSearchContract.GitSearchModel gitSearchModel;

    public RepoDetailPresenter(GitSearchContract.GitSearchModel gitSearchModel) {
        this.gitSearchModel = gitSearchModel;
    }

    public void bind(GitSearchContract.RepoDetailView repoDetailView) {
        this.repoDetailView = repoDetailView;
    }

    public void unbind() {
        repoDetailView = null;
    }

    public void fetchContributors(String contributorsUrl) {
        repoDetailView.showDialog();
        gitSearchModel.getContributors(contributorsUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                if(response != null) {
                    final ArrayList<Contributor> contributors = Contributor.fromJson(response);
                    repoDetailView.updateUi(contributors);
                }
                repoDetailView.hideDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                repoDetailView.hideDialog();
            }
        });
    }

}
