package camdev.sokra.topnews.ui.update;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;

import camdev.sokra.topnews.R;
import camdev.sokra.topnews.auth.ServiceGenerator;
import camdev.sokra.topnews.model.Articles;
import camdev.sokra.topnews.model.UploadImageRespone;
import camdev.sokra.topnews.model.crud.ArticlesCRUD;
import camdev.sokra.topnews.service.ArticlesService;
import camdev.sokra.topnews.ui.insert.AddActivity;
import camdev.sokra.topnews.ui.insert.mvp.AddPresenter;
import camdev.sokra.topnews.util.RequestPermissions;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {
    private EditText edTitle,edDes,edAuthorID;
    private AddPresenter presenter;
    private Button btnSave;
    private ImageView articleImage;
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private ArticlesService articlesService;
    private String imageURL;
    private ProgressBar progressBar;
    private Button btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        edTitle = findViewById(R.id.edTitle);
        edDes = findViewById(R.id.edDesc);
        edAuthorID = findViewById(R.id.edAuthorID);
        btnSave = findViewById(R.id.btnSave);
        progressBar = findViewById(R.id.progressBar);
        articleImage = findViewById(R.id.articlesImage);
        btnCancel = findViewById(R.id.btnCancel);

        articlesService = ServiceGenerator.createService(ArticlesService.class);
        RequestPermissions.checkStorageAccessPermissions(this);

        btnCancel.setOnClickListener(v->{
            finish();
        });

        articleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
                openGalleryIntent.setType("image/*");
                startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
            }
        });
        if (getIntent() !=null){
            final ArticlesCRUD articlesCRUD = getIntent().getParcelableExtra("articlesCRUD");
            try {
                edAuthorID.setText(Integer.valueOf(articlesCRUD.getAuthor()).toString());
                edTitle.setText(articlesCRUD.getTitle());
                edDes.setText(articlesCRUD.getDescription());
                Glide.with(this).load(articlesCRUD.getImage()).into(articleImage);
                articleImage.setPadding(0,0,0,0);
                articleImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }catch (Exception e){
                Log.e("1111",""+e.toString());
            }

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = edAuthorID.getText().toString();
                    if (id.isEmpty()){
                        edAuthorID.requestFocus();
                        return;
                    }
                    articlesCRUD.setAuthor(Integer.valueOf(id));
                    articlesCRUD.setTitle(edTitle.getText().toString());
                    articlesCRUD.setDescription(edDes.getText().toString());
                    articlesCRUD.setImage(imageURL);
                    Intent addInten = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("data",articlesCRUD);
                    addInten.putExtras(bundle);
                    setResult(RESULT_OK,addInten);
                    finish();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.RESULT_CANCELED){
            return;
        }
        if (requestCode == REQUEST_GALLERY_CODE){
            if (data !=null){
                try {
                    Uri imgUri = data.getData();
                    String[] colomInfo = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(imgUri,colomInfo,null,null,null);
                    cursor.moveToFirst();
                    int columIndex = cursor.getColumnIndex(colomInfo[0]);
                    String fileName = cursor.getString(columIndex);
                    Bitmap bitmap = BitmapFactory.decodeFile(fileName);
                    Glide.with(this).load(bitmap).into(articleImage);
                    articleImage.setPadding(0,0,0,0);
                    articleImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

                    //upload image here
                    upLoadImage(fileName);

                }catch (Exception e){
                    Log.e("0000",""+e.toString());
                }
            }
        }
    }

    private void upLoadImage(String fileName){
        final File file = new File(fileName);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("FILE",fileName,requestBody);
        progressBar.setVisibility(View.VISIBLE);
        Call<UploadImageRespone> call = articlesService.uploadImage(part);
        call.enqueue(new Callback<UploadImageRespone>() {
            @Override
            public void onResponse(Call<UploadImageRespone> call, Response<UploadImageRespone> response) {
                imageURL = response.body().getData();
                progressBar.setVisibility(View.GONE);
                Toast.makeText(UpdateActivity.this, "Upload Success", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<UploadImageRespone> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(UpdateActivity.this, "Upload Fail"+t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
