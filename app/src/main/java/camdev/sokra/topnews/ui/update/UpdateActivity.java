package camdev.sokra.topnews.ui.update;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import camdev.sokra.topnews.R;
import camdev.sokra.topnews.model.Articles;
import camdev.sokra.topnews.service.ArticlesService;
import camdev.sokra.topnews.ui.insert.mvp.AddPresenter;

public class UpdateActivity extends AppCompatActivity {
    private EditText edTitle,edDes,edAuthorID;
    private AddPresenter presenter;
    private ArticlesService articlesService;
    private Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        edTitle = findViewById(R.id.edTitle);
        edDes = findViewById(R.id.edDesc);
        edAuthorID = findViewById(R.id.edAuthorID);
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Articles articles = new Articles();
                articles.setId((Integer.parseInt(String.valueOf(edAuthorID.getText().toString()))));
                articles.setTitle(edTitle.getText().toString());
                articles.setDescription(edDes.getText().toString());

            }
        });
    }
}
