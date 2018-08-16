package mapprr.deepak.gitsearch.contract;

import com.loopj.android.http.JsonHttpResponseHandler;

import java.util.ArrayList;

import mapprr.deepak.gitsearch.models.Contributor;
import mapprr.deepak.gitsearch.models.Repo;

/**
 * Created by Sharmaji on 8/17/2018.
 */

public interface GitSearchContract {

    interface GitSearchView {
        void updateUi(ArrayList<Repo> repos);
        void showDialog();
        void hideDialog();
    }

    interface GitSearchModel {
        void getRepos(final String query, JsonHttpResponseHandler handler);
        void getContributors(final String url, JsonHttpResponseHandler handler);
    }

    interface RepoDetailView {
        void updateUi(ArrayList<Contributor> contributors);
        void showDialog();
        void hideDialog();
    }

    interface ContributorView {
        void updateUi(ArrayList<Repo> repos);
        void showDialog();
        void hideDialog();
    }
}
