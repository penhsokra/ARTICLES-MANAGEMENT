package camdev.sokra.topnews.scrap;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.HashMap;
import camdev.sokra.topnews.R;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.Viewholder>{

    HashMap<String, Integer> modelList;
    Context mContext;
    private String[] mKeys;

    public BlogAdapter(Context context, HashMap<String, Integer> modelList) {
        this.modelList = modelList;
        mContext = context;
        mKeys = modelList.keySet().toArray(new String[modelList.size()]);
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_words, viewGroup, false);
        return new BlogAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, int i) {
        viewholder.txtWord.setText(mKeys[i]);
        viewholder.txtCount.setText(String.valueOf(modelList.get(mKeys[i])));
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        TextView txtWord;
        TextView txtCount;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            txtWord = itemView.findViewById(R.id.txtWord);
            txtCount = itemView.findViewById(R.id.txtCount);
        }
    }

}
