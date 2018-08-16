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
import mapprr.deepak.gitsearch.models.Repo;

/**
 * Created by Sharmaji on 8/17/2018.
 */

public class GitSearchPresenter {

    GitSearchContract.GitSearchView gitSearchView;
    private GitSearchContract.GitSearchModel gitSearchModel;

    public GitSearchPresenter(GitSearchContract.GitSearchModel gitSearchModel) {
        this.gitSearchModel = gitSearchModel;
    }

    public void bind(GitSearchContract.GitSearchView gitSearchView) {
        this.gitSearchView= gitSearchView;
    }

    public void unbind() {
        gitSearchView = null;
    }

    public void fetchRepos(String query) {
        gitSearchView.showDialog();
        gitSearchModel.getRepos(query, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray docs;
                    if(response != null) {
                        docs = response.getJSONArray("items");
                        final ArrayList<Repo> repos = Repo.fromJson(docs);
                        gitSearchView.updateUi(repos);
                        gitSearchView.hideDialog();
                    }
                } catch (JSONException e) {
                    gitSearchView.hideDialog();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                gitSearchView.hideDialog();
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    public void sortRepos(ArrayList<Repo> repos, int sortBy, final int orderBy) {
        ArrayList<Repo> arepos = new ArrayList<Repo>();
        arepos.addAll(repos);
        switch (sortBy)
        {
            case 0:
                Collections.sort(arepos, new Comparator<Repo>(){
                    public int compare(Repo o1, Repo o2){
                        if(orderBy==0) {
                            return o1.getStarCount() - o2.getStarCount();
                        } else {
                            return o2.getStarCount() - o1.getStarCount();
                        }
                    }
                });
                break;
            case 1:
                Collections.sort(arepos, new Comparator<Repo>(){
                    public int compare(Repo o1, Repo o2){
                        if(orderBy==0) {
                            return o1.getForksCount() - o2.getForksCount();
                        } else {
                            return o2.getForksCount() - o1.getForksCount();
                        }
                    }
                });
                break;
            case 2:
                Collections.sort(arepos, new Comparator<Repo>(){
                    public int compare(Repo o1, Repo o2){
                        if(orderBy==0) {
                            return o1.getWatchersCount() - o2.getWatchersCount();
                        } else {
                            return o2.getWatchersCount() - o1.getWatchersCount();
                        }
                    }
                });
                break;
        }
        gitSearchView.updateUi(arepos);
    }
}
