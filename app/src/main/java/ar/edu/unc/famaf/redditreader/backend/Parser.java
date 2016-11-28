package ar.edu.unc.famaf.redditreader.backend;

import android.util.JsonReader;
import android.util.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;

/**
 * Created by aguilarjp on 27/10/16.
 */

public class Parser {
    public Listing readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readListing(reader);
        } finally {
            reader.close();
        }
    }

    private Listing readListing(JsonReader reader) throws IOException {
        List<PostModel> children = null;
        String after = null;
        String before = null;

        reader.beginObject();

        while (reader.hasNext()) {
            if (reader.nextName().equals("data")) {
                reader.beginObject();
                while (reader.hasNext()) {
                    switch (reader.nextName()) {
                        case "children":
                            children = readChildren(reader);
                            break;

                        case "after":
                            after = reader.nextString();
                            break;

                        case "before":
                            if (reader.peek() != JsonToken.NULL) {
                                before = reader.nextString();
                            } else {
                                reader.nextNull();
                            }
                            break;
                        default:
                            reader.skipValue();
                    }
                }
                reader.endObject();
                break;
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Listing(children, after, before);
    }

    private List<PostModel> readChildren(JsonReader reader) throws IOException {
        List<PostModel> postModels = new ArrayList<>();

        reader.beginArray();
        while(reader.hasNext()) {
            postModels.add(readPostModel(reader));
        }
        reader.endArray();
       return postModels;
    }

    private PostModel readPostModel(JsonReader reader) throws IOException {
        String title = null;
        String author = null;
        long createdTime = -1;
        int commentNumber = -1;
        String thumbnail = null;
        String subreddit = null;
        String id = null;
        String postHint = null;
        String image = null;

        reader.beginObject();
        while(reader.hasNext()) {
            if (reader.nextName().equals("data")) {
                reader.beginObject();

                while(reader.hasNext()) {
                    switch (reader.nextName()) {
                        case "subreddit":
                            subreddit = reader.nextString();
                            break;
                        case "id":
                            id = reader.nextString();
                            break;
                        case "author":
                            author = reader.nextString();
                            break;
                        case "thumbnail":
                            thumbnail = reader.nextString();
                            break;
                        case "title":
                            title = reader.nextString();
                            break;
                        case "created_utc":
                            createdTime = reader.nextLong() * 1000; // Epoch isn't in seconds, it's in miliseconds
                            break;
                        case "num_comments":
                            commentNumber = reader.nextInt();
                            break;
                        case "post_hint":
                            postHint = reader.nextString();
                            break;
                        case "url":
                            image = reader.nextString();
                            break;
                        default:
                            reader.skipValue();
                    }
                }
                reader.endObject();
                break;
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new PostModel(title, author, createdTime, commentNumber, thumbnail, subreddit, id, postHint, image);
    }

}
