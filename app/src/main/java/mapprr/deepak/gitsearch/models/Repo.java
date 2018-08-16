package mapprr.deepak.gitsearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Repo implements Serializable {
    private String name;
    private String fullName;    //full_name
    private String avatarUrl;   //avatar_url
    private String url;         //url
    private String description;   //description
    private String contributorsUrl;   //contributors_url
    private int starCount;      //stargazers_count
    private int watchersCount;  //watchers_count
    private int forksCount;     //forks_count

    // Returns a Repo given the expected JSON
    public static Repo fromJson(JSONObject jsonObject) {
        Repo repo = new Repo();
        try {
            if (jsonObject.has("name")) {
                repo.name = jsonObject.getString("name");
            }
            if (jsonObject.has("full_name")) {
                repo.fullName = jsonObject.getString("full_name");
            }
            if(jsonObject.has("owner")) {
                JSONObject ids = jsonObject.getJSONObject("owner");
                if (ids.has("avatar_url")) {
                    repo.avatarUrl = ids.getString("avatar_url");
                }
            }
            if (jsonObject.has("contributors_url")) {
                repo.contributorsUrl = jsonObject.getString("contributors_url");
            }
            if (jsonObject.has("html_url")) {
                repo.url = jsonObject.getString("html_url");
            }
            if (jsonObject.has("description")) {
                repo.description = jsonObject.getString("description");
            }
            if (jsonObject.has("stargazers_count")) {
                repo.starCount = jsonObject.getInt("stargazers_count");
            }
            if (jsonObject.has("watchers_count")) {
                repo.watchersCount = jsonObject.getInt("watchers_count");
            }
            if (jsonObject.has("forks_count")) {
                repo.forksCount = jsonObject.getInt("forks_count");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return repo;
    }

    // Decodes array of repo json results into business model objects
    public static ArrayList<Repo> fromJson(JSONArray jsonArray) {
        ArrayList<Repo> repos = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject repoJson;
            try {
                repoJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            Repo repo = Repo.fromJson(repoJson);
            if (repo != null) {
                repos.add(repo);
            }
            if(i==9) {
                break;
            }
        }
        return repos;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public int getStarCount() {
        return starCount;
    }

    public int getWatchersCount() {
        return watchersCount;
    }

    public int getForksCount() {
        return forksCount;
    }

    public String getContributorsUrl() {
        return contributorsUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }
}
