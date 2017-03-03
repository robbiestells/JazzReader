package models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.description;

/**
 * Created by rob on 3/2/17.
 */

public class FeedItem implements Parcelable {
    String id;
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
    String videoLink;
    String article;
    String eventDate;
    String eventLocation;
    String price;
    ArrayList<Artist> artists;
    ArrayList<Genre> genres;

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public ArrayList<Artist> getArtists() {
        return artists;
    }

    public void setArtists(ArrayList<Artist> artists) {
        this.artists = artists;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

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
        out.writeString(id);
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
        out.writeList(artists);
        out.writeList(genres);
        out.writeString(videoLink);
        out.writeString(article);
        out.writeString(eventDate);
        out.writeString(eventLocation);
        out.writeString(price);

    }

    public FeedItem(){}
    public FeedItem(String type, String title, String createdAt, ArrayList<Artist> artists){
        this.type = type;
        this.title = title;
        this.createdAt = createdAt;
        this.artists = artists;
    }

    public FeedItem(String id, String type, String title, String shortDescription, String imageLink, String imageLinkRetina,
                    String link, String releaseDate, String shareLink, String createdAt, String updatedAt, ArrayList<Artist> artists, ArrayList<Genre> genres,
                    String videoLink, String article, String eventDate, String eventLocation, String price){
        this.id = id;
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
        this.artists = artists;
        this.genres = genres;
        this.videoLink = videoLink;
        this.article = article;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
        this.price = price;
    }

    private FeedItem(Parcel in){
        id = in.readString();
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
        videoLink = in.readString();
        article = in.readString();
        eventDate = in.readString();
        eventLocation = in.readString();
        price = in.readString();
      //  artists = in.readArrayList();
      //  genres = in.readArrayList()

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