package camdev.sokra.topnews.adapter;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import camdev.sokra.topnews.R;
import camdev.sokra.topnews.model.Articles;
import camdev.sokra.topnews.model.Author;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder>{
    private List<Articles> articlesList;
    AppCompatActivity context;
    private OnArticleCRUD listener;
    private static int TYPE_CALL = 1;
    private static int TYPE_EMAIL = 2;
    public ArticlesAdapter(List<Articles> articlesList, AppCompatActivity context) {
        this.articlesList = articlesList;
        this.context = context;
        this.listener = (OnArticleCRUD) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.articles_relate_rows_lists,viewGroup,false);
        return new ArticlesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        final Articles articles = articlesList.get(position);
        final Author author = articles.getAuthor();
        final PopupMenu popupMenu = new PopupMenu(context,viewHolder.btnOptionMenu);
        popupMenu.getMenuInflater().inflate(R.menu.option_menu,popupMenu.getMenu());
       try {
               //Glide.with(context).load(author.getImageUrl()).thumbnail(Glide.with(context).load(R.drawable.loading_100x)).into(viewHolder.profile_image);
               Glide.with(context).load(articles.getImage()).placeholder(R.drawable.ic_add_a_photo_black_24dp).thumbnail(Glide.with(context).load(R.drawable.spinner_200px)).into(viewHolder.artImage);
               viewHolder.artTitle.setText(articles.getTitle());
               //viewHolder.artAuthor.setText(author.getName());

               viewHolder.btnOptionMenu.setOnClickListener(new View.OnClickListener() {

                   @Override
                   public void onClick(View v) {
                       popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                           @Override
                           public boolean onMenuItemClick(MenuItem item) {
                               switch (item.getItemId()){
                                   case R.id.btnUpdate:
                                       if (listener !=null){
                                           listener.onArticlesUpdate(articles);
                                           return true;
                                       }
                                   case R.id.btnDelete:
                                       if (listener !=null){
                                           listener.onArticlesDelete(viewHolder.getAdapterPosition(),articles);
                                           return true;
                                       }
                                   default: return false;
                               }
                           }
                       });
                       popupMenu.show();

                   }
               });

       }catch (Exception e){
           Log.e("1111",""+e.toString());
       }
       viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (listener != null){
                   listener.onArticlesDetail(articles);
               }
           }
       });


    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView artImage;
        private TextView artTitle;
        private TextView artAuthor;
        private ImageView profile_image;
        private ImageView btnOptionMenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            artImage = itemView.findViewById(R.id.imageURL);
            artTitle = itemView.findViewById(R.id.title);
            artAuthor = itemView.findViewById(R.id.artAuthor);
            profile_image = itemView.findViewById(R.id.profile_image);
            btnOptionMenu = itemView.findViewById(R.id.btnOption);

        }
    }

    public void addMoreItem(List<Articles> articles){
        int previousDataSize = this.articlesList.size();
        this.articlesList.addAll(articles);
        notifyItemRangeInserted(previousDataSize, articles.size());
    }

    public interface OnArticleCRUD{
        void onArticlesDetail(Articles getDetailArticles);
        void onArticlesDelete(int position,Articles articles);
        void onArticlesUpdate(Articles getUpdateArticles);
    }
}
