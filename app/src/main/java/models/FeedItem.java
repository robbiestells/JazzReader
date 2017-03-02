package models;

import android.os.Parcel;
import android.os.Parcelable;

import static android.R.attr.description;

/**
 * Created by rob on 3/2/17.
 */

public class FeedItem implements Parcelable {
    String type;
    String title;
    String shortDescription;
    String imageLink;
    String imageLinkRetina;
    String link;
    String releaseDate;
    String shareLink;
    String createdAt;
    String updatedAt;
//    String artists;
//    String genres;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getImageLinkRetina() {
        return imageLinkRetina;
    }

    public void setImageLinkRetina(String imageLinkRetina) {
        this.imageLinkRetina = imageLinkRetina;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }


    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int i) {
        out.writeString(type);
        out.writeString(title);
        out.writeString(shortDescription);
        out.writeString(imageLink);
        out.writeString(imageLinkRetina);
        out.writeString(link);
        out.writeString(releaseDate);
        out.writeString(shareLink);
        out.writeString(createdAt);
        out.writeString(updatedAt);
    }

    public FeedItem(){}

    public FeedItem(String type, String title, String shortDescription, String imageLink, String imageLinkRetina,
                    String link, String releaseDate, String shareLink, String createdAt, String updatedAt){
        this.type = type;
        this.title = title;
        this.shortDescription = shortDescription;
        this.imageLink = imageLink;
        this.imageLinkRetina = imageLinkRetina;
        this.link = link;
        this.releaseDate = releaseDate;
        this.shareLink = shareLink;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private FeedItem(Parcel in){
        type = in.readString();
        title = in.readString();
        shortDescription = in.readString();
        imageLink = in.readString();
        imageLinkRetina = in.readString();
        link = in.readString();
        releaseDate = in.readString();
        shareLink = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
    }

    public static final Parcelable.Creator<FeedItem> CREATOR = new Parcelable.Creator<FeedItem>(){

        @Override
        public FeedItem createFromParcel(Parcel parcel) {
            return new FeedItem(parcel);
        }

        @Override
        public FeedItem[] newArray(int i) {
            return new FeedItem[i];
        }
    };
}