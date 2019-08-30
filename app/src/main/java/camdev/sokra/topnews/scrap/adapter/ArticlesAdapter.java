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
import camdev.sokra.topnews.scrap.model.ScrapArticles;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.Viewholder> {
    private AppCompatActivity context;
    private List<ScrapArticles> articlseResponeList;
    private OnCallback listener;

    public ArticlesAdapter(List<ScrapArticles> articlseResponeList, AppCompatActivity context) {
        this.articlseResponeList = articlseResponeList;
        this.context = context;
        this.listener = (OnCallback) context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.articles_scrap_rows_lists,viewGroup,false);
        return new ArticlesAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, int i) {
        final ScrapArticles scrapArticles = articlseResponeList.get(i);
        viewholder.title.setText(scrapArticles.getTitle());
        viewholder.datetime.setText(scrapArticles.getPublic_date());
        Glide.with(context).load(scrapArticles.getImageURL()).thumbnail(Glide.with(context).load(R.drawable.loading)).into(viewholder.simageURL);

        viewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener !=null){
                    listener.OnArticleClick(i, scrapArticles);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return articlseResponeList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        private TextView title,datetime;
        ImageView simageURL;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.stitle);
            datetime = itemView.findViewById(R.id.sdateTime);
            simageURL = itemView.findViewById(R.id.simageURL);
        }
    }

    public void addMoreItem(List<ScrapArticles> articles){
        int previousDataSize = this.articlseResponeList.size();
        this.articlseResponeList.addAll(articles);
        notifyItemRangeInserted(previousDataSize, articles.size());
    }

    public interface OnCallback{
        void OnArticleClick(int position, ScrapArticles sabayScrapArticles);
    }
}
