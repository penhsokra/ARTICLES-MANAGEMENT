package camdev.sokra.topnews.scrap.adapter;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import camdev.sokra.topnews.R;
import camdev.sokra.topnews.scrap.model.Articles;

public class SabayAdapter extends RecyclerView.Adapter<SabayAdapter.Viewholder> {
    private AppCompatActivity context;
    private List<Articles> articlseResponeList;
    private OnCallback listener;

    public SabayAdapter(List<Articles> articlseResponeList, AppCompatActivity context) {
        this.articlseResponeList = articlseResponeList;
        this.context = context;
        this.listener = (OnCallback) context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.articles_sabay_rows_lists,viewGroup,false);
        return new SabayAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, int i) {
        final Articles sabayArticles = articlseResponeList.get(i);
        viewholder.title.setText(sabayArticles.getTitle());
        viewholder.detail.setText(sabayArticles.getDetail());
        viewholder.datetime.setText(sabayArticles.getPublic_date());
        Glide.with(context).load("http:"+sabayArticles.getImageURL()).thumbnail(Glide.with(context).load(R.drawable.loading)).into(viewholder.simageURL);
        viewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener !=null){
                    listener.OnArticleClick(i,sabayArticles);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return articlseResponeList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        private TextView title,detail,datetime;
        ImageView simageURL;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.stitle);
            datetime = itemView.findViewById(R.id.sdateTime);
            simageURL = itemView.findViewById(R.id.simageURL);
            detail = itemView.findViewById(R.id.sdetail);
        }
    }

    public void addMoreItem(List<Articles> articles){
        int previousDataSize = this.articlseResponeList.size();
        this.articlseResponeList.addAll(articles);
        notifyItemRangeInserted(previousDataSize, articles.size());
    }

    public interface OnCallback{
        void OnArticleClick(int position, Articles sabayArticles);
    }
}
