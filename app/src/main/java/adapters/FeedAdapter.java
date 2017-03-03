package adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.primeperspective.jazzreader.MainActivity;
import com.primeperspective.jazzreader.R;

import java.util.ArrayList;

import models.FeedItem;

/**
 * Created by rob on 3/2/17.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder> {
    ArrayList<FeedItem> feedItems;
    Context context;

    public FeedAdapter(Context context, ArrayList<FeedItem> feedItems) {
        this.feedItems = feedItems;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //set feed item data
        final FeedItem current = feedItems.get(position);
        holder.Title.setText(current.getTitle());
        holder.Date.setText(current.getCreatedAt());
        holder.Type.setText(current.getType());

//        holder.playButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //pass item to mainactivity to paly
//                MainActivity mainActivity = MainActivity.getInstance();
//                mainActivity.PlayEpisode(current);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Title, Date, Type;
//        ImageButton playButton;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            //get views
            Title = (TextView) itemView.findViewById(R.id.postTitle);
            Type = (TextView) itemView.findViewById(R.id.postType);
            Date = (TextView) itemView.findViewById(R.id.postDate);
            //playButton = (ImageButton) itemView.findViewById(playEpisode);
            cardView = (CardView) itemView.findViewById(R.id.feedItemCard);
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
