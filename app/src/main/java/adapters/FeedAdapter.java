package adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.primeperspective.jazzreader.MainActivity;
import com.primeperspective.jazzreader.R;

import java.util.ArrayList;

import models.FeedItem;

/**
 * Created by rob on 3/2/17.
 */

public class FeedAdapter extends RecyclerView.Adapter<ViewHolder> {
    private static final int ITEM_TYPE_NORMAL = 0;
    private static final int ITEM_TYPE_RELEASE = 1;
    private static final int ITEM_TYPE_VIDEO = 2;

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
        }
        else {
            return ITEM_TYPE_NORMAL;
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder;
        switch (viewType) {
            case ITEM_TYPE_NORMAL:
                View normalView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, parent, false);
                holder = new NormalViewHolder(normalView);
                break;
            case ITEM_TYPE_RELEASE:
                View releaseView = LayoutInflater.from(parent.getContext()).inflate(R.layout.second_post_type, parent, false);
                holder = new ReleaseViewHolder(releaseView);
                break;
            case ITEM_TYPE_VIDEO:
                View videoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_post_type, parent, false);
                holder = new VideoViewHolder(videoView);
                break;
            default:
                View defaultView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, parent, false);
                holder = new NormalViewHolder(defaultView);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int itemType = getItemViewType(position);
        final FeedItem currentItem = feedItems.get(position);

        switch (itemType) {
            case ITEM_TYPE_NORMAL:
                ((NormalViewHolder)holder).bindData(currentItem);
                break;
            case ITEM_TYPE_RELEASE:
                ((ReleaseViewHolder)holder).bindData(currentItem);
                break;
            case ITEM_TYPE_VIDEO:
                ((VideoViewHolder)holder).bindData(currentItem);
                break;
            default:
                ((NormalViewHolder)holder).bindData(currentItem);
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

    public class NormalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Title, Date, Type;
        //        ImageButton playButton;
        CardView cardView;

        public NormalViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            //get views
            Title = (TextView) itemView.findViewById(R.id.postTitle);
            Type = (TextView) itemView.findViewById(R.id.postType);
            Date = (TextView) itemView.findViewById(R.id.postDate);
            //playButton = (ImageButton) itemView.findViewById(playEpisode);
            cardView = (CardView) itemView.findViewById(R.id.feedItemCard);
        }
        public void bindData(FeedItem item){

        Title.setText(item.getTitle());
        Date.setText(item.getCreatedAt());
        Type.setText(item.getType());
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

    public class ReleaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Title;
        //        ImageButton playButton;
        CardView cardView;

        public ReleaseViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            //get views
            Title = (TextView) itemView.findViewById(R.id.postTitle);

            //playButton = (ImageButton) itemView.findViewById(playEpisode);
            cardView = (CardView) itemView.findViewById(R.id.releaseItemCard);
        }
        public void bindData(FeedItem item){

            Title.setText(item.getTitle());

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
        TextView videoTitle;
        WebView videoView;
        CardView cardView;

        public VideoViewHolder(View itemView) {
            super(itemView);

            //itemView.setOnClickListener(this);

            //get views
            videoView = (WebView) itemView.findViewById(R.id.videoView);
            videoTitle = (TextView) itemView.findViewById(R.id.videoTitle);

        }
        public void bindData(FeedItem item){

//            MediaController mediaController= new MediaController(context);
//            mediaController.setAnchorView(videoView);
//            Uri uri=Uri.parse(item.getVideoLink());
//            videoView.setMediaController(mediaController);
//            videoView.setVideoURI(uri);
//            videoView.requestFocus();
//
//            videoView.start();
          //  videoView.getSettings().setJavaScriptEnabled(true);
          //  videoView.setWebChromeClient(new WebChromeClient(){});
          //  videoView.loadUrl(item.getVideoLink());
            videoTitle.setText(item.getTitle());

        }

    }
}
