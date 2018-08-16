package mapprr.deepak.gitsearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sharmaji on 8/16/2018.
 */

public class Contributor {

    private String login;    //login
    private String avatarUrl;   //avatar_url
    private String reposUrl;   //repos_url

    public static Contributor fromJson(JSONObject jsonObject) {
        Contributor contributor = new Contributor();
        try {
            if (jsonObject.has("login")) {
                contributor.login = jsonObject.getString("login");
            }
            if (jsonObject.has("avatar_url")) {
                contributor.avatarUrl = jsonObject.getString("avatar_url");
            }
            if (jsonObject.has("repos_url")) {
                contributor.reposUrl = jsonObject.getString("repos_url");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return contributor;
    }

    public static ArrayList<Contributor> fromJson(JSONArray jsonArray) {
        ArrayList<Contributor> contributors = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject contributorJson;
            try {
                contributorJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            Contributor contributor = Contributor.fromJson(contributorJson);
            if (contributor != null) {
                contributors.add(contributor);
            }
        }
        return contributors;
    }

    public String getLogin() {
        return login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getReposUrl() {
        return reposUrl;
    }
}
