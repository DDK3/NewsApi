package dips.co.news.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import dips.co.news.R;
import dips.co.news.Util.Settergetter;

/**
 * Created by Dipesh on 04-Jan-17.
 */

public class News_Adapter extends RecyclerView.Adapter<News_Adapter.ViewHolder> {

    List<Settergetter> newsList;
    Context mContext;
    String imgPath,timedate,date,time;

    public News_Adapter(Context mContext, List<Settergetter> newsList) {
        this.mContext = mContext;
        this.newsList = newsList;
    }

    @Override
    public News_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_for_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(News_Adapter.ViewHolder holder, int position) {
        final Settergetter list = newsList.get(position);
        imgPath = list.getNews_image_path();
        Picasso.with(mContext).load(imgPath).error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).into(holder.news_image);

        holder.news_title.setText(list.getNews_title());
        holder.news_author.setText(list.getNews_author());

        timedate = list.getNews_DateTime();
        date = timedate.substring(0, 10);
        time = timedate.substring(11, 16);
        holder.news_date.setText(date);
        holder.news_time.setText(time);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView news_title,news_author,news_date,news_time;
        ImageView news_image;
        public ViewHolder(View itemView) {
            super(itemView);

            news_title = (TextView) itemView.findViewById(R.id.news_title);
            news_author = (TextView) itemView.findViewById(R.id.news_author);
            news_date = (TextView) itemView.findViewById(R.id.news_date);
            news_time = (TextView) itemView.findViewById(R.id.news_time);
            news_image = (ImageView) itemView.findViewById(R.id.news_image);
        }
    }
}
