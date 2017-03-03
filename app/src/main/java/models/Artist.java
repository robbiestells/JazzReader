package models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rob on 3/3/17.
 */

public class Artist implements Parcelable {
    String id;
    String artistName;
    String topicName;
    String artistImage;
    String artistImageRetina;
    String artistBio;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getArtistImage() {
        return artistImage;
    }

    public void setArtistImage(String artistImage) {
        this.artistImage = artistImage;
    }

    public String getArtistImageRetina() {
        return artistImageRetina;
    }

    public void setArtistImageRetina(String artistImageRetina) {
        this.artistImageRetina = artistImageRetina;
    }

    public String getArtistBio() {
        return artistBio;
    }

    public void setArtistBio(String artistBio) {
        this.artistBio = artistBio;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int i) {
        out.writeString(id);
        out.writeString(artistName);
        out.writeString(topicName);
        out.writeString(artistImage);
        out.writeString(artistImageRetina);
        out.writeString(artistBio);

    }

    public Artist(){}

    public Artist(String id, String artistName, String topicName, String artistImage, String artistImageRetina,
                    String artistBio){
        this.id = id;
        this.artistName = artistName;
        this.topicName = topicName;
        this.artistImage = artistImage;
        this.artistImageRetina = artistImageRetina;
        this.artistBio = artistBio;

    }

    private Artist(Parcel in){
        id = in.readString();
        artistName = in.readString();
        topicName = in.readString();
        artistImage = in.readString();
        artistImageRetina = in.readString();
        artistBio = in.readString();
    }

    public static final Parcelable.Creator<Artist> CREATOR = new Parcelable.Creator<Artist>(){

        @Override
        public Artist createFromParcel(Parcel parcel) {
            return new Artist(parcel);
        }

        @Override
        public Artist[] newArray(int i) {
            return new Artist[i];
        }
    };
}