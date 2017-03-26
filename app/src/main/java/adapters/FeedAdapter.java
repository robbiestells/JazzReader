package adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.primeperspective.jazzreader.MainActivity;
import com.primeperspective.jazzreader.R;

import java.util.ArrayList;

import models.Artist;
import models.FeedItem;

import static android.R.attr.id;

/**
 * Created by rob on 3/2/17.
 */

public class FeedAdapter extends RecyclerView.Adapter<ViewHolder> {
    private static final int ITEM_TYPE_NEWS = 0;
    private static final int ITEM_TYPE_RELEASE = 1;
    private static final int ITEM_TYPE_VIDEO = 2;
    private static final int ITEM_TYPE_LINK = 3;
    private static final int ITEM_TYPE_EVENT = 4;

    ArrayList<FeedItem> feedItems;
    Context context;

    public FeedAdapter(Context context, ArrayList<FeedItem> feedItems) {
        this.feedItems = feedItems;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        final FeedItem current = feedItems.get(position);
        if (current.getType().contains("release")) {
            return ITEM_TYPE_RELEASE;
        } else if (current.getType().contains("video")) {
            return ITEM_TYPE_VIDEO;
        } else if (current.getType().contains("link")) {
            return ITEM_TYPE_LINK;
        } else if (current.getType().contains("event")) {
            return ITEM_TYPE_EVENT;
        } else {
            //news event
            return ITEM_TYPE_NEWS;
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder;
        switch (viewType) {
            case ITEM_TYPE_NEWS:
                View normalView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_post_type, parent, false);
                holder = new NewsViewHolder(normalView);
                break;
            case ITEM_TYPE_RELEASE:
                View releaseView = LayoutInflater.from(parent.getContext()).inflate(R.layout.release_post_type, parent, false);
                holder = new ReleaseViewHolder(releaseView);
                break;
            case ITEM_TYPE_VIDEO:
                View videoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_post_type, parent, false);
                holder = new VideoViewHolder(videoView);
                break;
            case ITEM_TYPE_EVENT:
                View eventView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_post_type, parent, false);
                holder = new EventViewHolder(eventView);
                break;
            case ITEM_TYPE_LINK:
                View linkView = LayoutInflater.from(parent.getContext()).inflate(R.layout.link_post_type, parent, false);
                holder = new LinkViewHolder(linkView);
                break;
            default:
                View defaultView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_post_type, parent, false);
                holder = new NewsViewHolder(defaultView);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int itemType = getItemViewType(position);
        final FeedItem currentItem = feedItems.get(position);

        switch (itemType) {
            case ITEM_TYPE_NEWS:
                ((NewsViewHolder) holder).bindData(currentItem);
                break;
            case ITEM_TYPE_RELEASE:
                ((ReleaseViewHolder) holder).bindData(currentItem);
                break;
            case ITEM_TYPE_VIDEO:
                ((VideoViewHolder) holder).bindData(currentItem);
                break;
            case ITEM_TYPE_EVENT:
                ((EventViewHolder) holder).bindData(currentItem);
                break;
            case ITEM_TYPE_LINK:
                ((LinkViewHolder) holder).bindData(currentItem);
                break;
            default:
                ((NewsViewHolder) holder).bindData(currentItem);
                break;
            //set feed item data
//        final FeedItem current = feedItems.get(position);
//        holder.Title.setText(current.getTitle());
//        holder.Date.setText(current.getCreatedAt());
//        holder.Type.setText(current.getType());

//        holder.playButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //pass item to mainactivity to paly
//                MainActivity mainActivity = MainActivity.getInstance();
//                mainActivity.PlayEpisode(current);
//            }
//        });
        }
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Title, Date, Type, Artists, Id;
        ImageView Image;
        CardView cardView;
        ArrayList<Artist> artists;

        public NewsViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            //get views
            Title = (TextView) itemView.findViewById(R.id.postTitle);
            Type = (TextView) itemView.findViewById(R.id.postType);
            Date = (TextView) itemView.findViewById(R.id.postDate);
            Image = (ImageView) itemView.findViewById(R.id.postImage);
            cardView = (CardView) itemView.findViewById(R.id.feedItemCard);
            Artists = (TextView) itemView.findViewById(R.id.postArtists);
            Id = (TextView) itemView.findViewById(R.id.postId);
        }

        public void bindData(FeedItem item) {

            //get artists
//            artists = item.getArtists();
//            String artistList = "";
//            for (Artist artist : artists) {
//                artistList = artistList + artist.getArtistName() + ", ";
//            }

            Id.setText(item.getId());
            Title.setText(item.getTitle());
            Date.setText(item.getCreatedAt());
            Type.setText(item.getType());
         //   Artists.setText(artistList.substring(0, artistList.length() - 2));
            Glide.with(context).load(item.getImageLink()).into(Image);

        }

        @Override
        public void onClick(View view) {
            //get episode that was clicked on and pass to main activity
            FeedItem selected = feedItems.get(getPosition());

            MainActivity mainActivity = MainActivity.getInstance();
            mainActivity.goToDetail(selected, Image);
            Image.setTransitionName("transition" + selected.getId());
        }
    }

    public class ReleaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Title, Type, Id;
        //        ImageButton playButton;
        CardView cardView;

        public ReleaseViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            //get views
            Id = (TextView) itemView.findViewById(R.id.postId);
            Title = (TextView) itemView.findViewById(R.id.postTitle);
            Type = (TextView) itemView.findViewById(R.id.postType);
            //playButton = (ImageButton) itemView.findViewById(playEpisode);
            cardView = (CardView) itemView.findViewById(R.id.releaseItemCard);
        }

        public void bindData(FeedItem item) {

            Title.setText(item.getTitle());
            Type.setText(item.getType());
            Id.setText(item.getId());
        }

        @Override
        public void onClick(View view) {
            //get episode that was clicked on and pass to main activity
//            FeedItem selected = feedItems.get(getPosition());
//
//            MainActivity mainActivity = MainActivity.getInstance();
//            mainActivity.OnEpisodeSelected(selected);
        }
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView videoTitle, Id;
        WebView videoView;
        ImageView videoImage;
        CardView cardView;

        int cardWidth;

        public VideoViewHolder(View itemView) {
            super(itemView);

            //itemView.setOnClickListener(this);

            //get views
            Id = (TextView) itemView.findViewById(R.id.postId);
            videoView = (WebView) itemView.findViewById(R.id.videoView);
            videoTitle = (TextView) itemView.findViewById(R.id.videoTitle);
            videoImage = (ImageView) itemView.findViewById(R.id.videoImage);
            cardView = (CardView) itemView.findViewById(R.id.videoItemCard);
        }

        public void bindData(final FeedItem item) {

            //cardWidth = cardView.getWidth();
            //viewHeight = view.getHeight();
            String videoId = "";

            String url = item.getVideoLink();

            if (url.contains("youtube")) {
                videoId = url.split("=")[1];
                Glide.with(context).load("https://img.youtube.com/vi/" + videoId + "/0.jpg").into(videoImage);
            } else {

                //TODO get vimeo preview image
            }

            Id.setText(item.getId());
            videoTitle.setText(item.getTitle());
            videoTitle.setVisibility(View.VISIBLE);
            //TODO decide whether or not to keep preview image
            videoImage.setVisibility(View.VISIBLE);
            //videoImage.setVisibility(View.GONE);


            //TODO autoplay not working
            String youtubeUrl = "src=\"https://www.youtube.com/embed/" + videoId + "?autoplay=1\"";

            // https://www.youtube.com/watch?v=3WirydZ4I2Y

            float test = 300;

            String iframe = " <iframe width=\"" + test + "\" height=\"200\"" + youtubeUrl + " frameborder=\"0\" allowfullscreen></iframe>";

            videoView.getSettings().setJavaScriptEnabled(true);
            videoView.loadDataWithBaseURL("", iframe, "text/html", "UTF-8", "");



            videoImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoImage.setVisibility(View.GONE);
                    videoTitle.setVisibility(View.GONE);

//                    String videoId = "";
//                    String url = item.getVideoLink();
//
//                    if (url.contains("youtube")) {
//                        videoId = url.split("=")[1];
//                        //  Glide.with(context).load("https://img.youtube.com/vi/" + videoId + "/0.jpg").into(videoImage);
//                    } else {
//
//                    }
//
//                    //TODO autoplay not working
//                    String youtubeUrl = "src=\"https://www.youtube.com/embed/" + videoId + "?autoplay=1\"";
//
//                    // https://www.youtube.com/watch?v=3WirydZ4I2Y
//
//                    float test = 200;
//
//                    String iframe = " <iframe width=\"" + test + "\" height=\"200\"" + youtubeUrl + " frameborder=\"0\" allowfullscreen></iframe>";
//
//                    videoView.getSettings().setJavaScriptEnabled(true);
//                    videoView.loadDataWithBaseURL("", iframe, "text/html", "UTF-8", "");
                }
            });

//            ViewTreeObserver viewTreeObserver = cardView.getViewTreeObserver();
//            if (viewTreeObserver.isAlive()) {
//                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//                        cardView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                        cardWidth = cardView.getWidth();
//                        //viewHeight = view.getHeight();
//
//                        String url = item.getVideoLink();
//
//                        if (url.contains("youtube")) {
//                            String videoId = url.split("=")[1];
//                            Glide.with(context).load("https://img.youtube.com/vi/" + videoId + "/0.jpg").into(videoImage);
//                        } else {
//
//                            //TODO get vimeo preview image
//                        }
//
//                        videoTitle.setText(item.getTitle());
//                        videoImage.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                videoImage.setVisibility(View.GONE);
//                                videoTitle.setVisibility(View.GONE);
//
//                                String videoId = "";
//                                String url = item.getVideoLink();
//
//                                if (url.contains("youtube")) {
//                                    videoId = url.split("=")[1];
//                                    //  Glide.with(context).load("https://img.youtube.com/vi/" + videoId + "/0.jpg").into(videoImage);
//                                } else {
//
//                                }
//
//                                //TODO autoplay not working
//                                String youtubeUrl = "src=\"https://www.youtube.com/embed/" + videoId + "?autoplay=1\"";
//
//                                // https://www.youtube.com/watch?v=3WirydZ4I2Y
//
//                                float test = cardWidth / context.getResources().getDisplayMetrics().density;
//
//                                String iframe = " <iframe width=\"" + test + "\" height=\"200\"" + youtubeUrl + " frameborder=\"0\" allowfullscreen></iframe>";
//
//                                videoView.getSettings().setJavaScriptEnabled(true);
//                                videoView.loadDataWithBaseURL("", iframe, "text/html", "UTF-8", "");
//                            }
//                        });
//
//                    }
//                });
//            }

        }
    }

    public class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Title, EventDate, Type, Id;
        ImageView Image;
        CardView cardView;

        public EventViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            //get views
            Id = (TextView) itemView.findViewById(R.id.postId);

            Title = (TextView) itemView.findViewById(R.id.postTitle);
            // Type = (TextView) itemView.findViewById(R.id.postType);
            EventDate = (TextView) itemView.findViewById(R.id.eventDate);
            Image = (ImageView) itemView.findViewById(R.id.postImage);
            cardView = (CardView) itemView.findViewById(R.id.feedItemCard);
        }

        public void bindData(FeedItem item) {

            Id.setText(item.getId());
            Title.setText(item.getTitle());
            EventDate.setText(item.getEventDate());
            //  Type.setText(item.getType());
            Glide.with(context).load(item.getImageLink()).into(Image);
        }

        @Override
        public void onClick(View view) {
            //get episode that was clicked on and pass to main activity
//            FeedItem selected = feedItems.get(getPosition());
//
//            MainActivity mainActivity = MainActivity.getInstance();
//            mainActivity.OnEpisodeSelected(selected);
        }
    }

    public class LinkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Title, Date, Type, Id;
        //ImageView Image;
        CardView cardView;

        public LinkViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            //get views
            Id = (TextView) itemView.findViewById(R.id.postId);

            Title = (TextView) itemView.findViewById(R.id.postTitle);
            Type = (TextView) itemView.findViewById(R.id.postType);
            Date = (TextView) itemView.findViewById(R.id.postDate);
            //  Image = (ImageView) itemView.findViewById(R.id.postImage);
            cardView = (CardView) itemView.findViewById(R.id.feedItemCard);
        }

        public void bindData(FeedItem item) {
            Id.setText(item.getId());
            Title.setText(item.getTitle());
            Date.setText(item.getCreatedAt());
            Type.setText(item.getType());
//            Glide.with(context).load(item.getImageLink()).into(Image);
        }

        @Override
        public void onClick(View view) {
            //get episode that was clicked on and pass to main activity
//            FeedItem selected = feedItems.get(getPosition());
//
//            MainActivity mainActivity = MainActivity.getInstance();
//            mainActivity.OnEpisodeSelected(selected);
        }
    }
}
