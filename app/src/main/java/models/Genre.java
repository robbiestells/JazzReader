package models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rob on 3/3/17.
 */

public class Genre implements Parcelable {
    String id;
    String genreName;
    String topicName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int i) {
        out.writeString(id);
        out.writeString(genreName);
        out.writeString(topicName);
    }

    public Genre(){}

    public Genre(String id, String genreName, String topicName){
        this.id = id;
        this.genreName = genreName;
        this.topicName = topicName;
    }

    private Genre(Parcel in){
        id = in.readString();
        genreName = in.readString();
        topicName = in.readString();
    }

    public static final Parcelable.Creator<Genre> CREATOR = new Parcelable.Creator<Genre>(){

        @Override
        public Genre createFromParcel(Parcel parcel) {
            return new Genre(parcel);
        }

        @Override
        public Genre[] newArray(int i) {
            return new Genre[i];
        }
    };
}