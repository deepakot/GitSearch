package mapprr.deepak.gitsearch.net;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import mapprr.deepak.gitsearch.contract.GitSearchContract;
import mapprr.deepak.gitsearch.models.Repo;

public class GitSearchClientImpl implements GitSearchContract.GitSearchModel {

    private static final String API_BASE_URL = "https://api.github.com/search/";
    private AsyncHttpClient client;

    public GitSearchClientImpl() {
        this.client = new AsyncHttpClient();
        this.client.setUserAgent(System.getProperty("http.agent"));
    }

    public String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }

    @Override
    public void getRepos(String query, JsonHttpResponseHandler handler) {
        try {
            String url = getApiUrl("repositories?q=");
            client.get(url + URLEncoder.encode(query, "utf-8"), handler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getContributors(String url, JsonHttpResponseHandler handler) {
        try {
            client.get(url, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}