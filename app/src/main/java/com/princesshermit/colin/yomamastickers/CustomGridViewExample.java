package com.princesshermit.colin.yomamastickers;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static com.princesshermit.colin.yomamastickers.R.id.imgbtnDemo;

public class CustomGridViewExample extends Activity {

    private Integer[] mThumbIds = {
            R.drawable.test1,
            R.drawable.test2,
            R.drawable.test3,
            R.drawable.test4,
            R.drawable.test5,
            R.drawable.test6,
            R.drawable.test7,
            R.drawable.test8,
            R.drawable.test9,
            R.drawable.test10,
            R.drawable.test11,
            R.drawable.test12,
            R.drawable.test13,
            R.drawable.test14,
            R.drawable.test15,
            R.drawable.test16,
            R.drawable.test17,
            R.drawable.test18,
            R.drawable.test19,
            R.drawable.test20,
            R.drawable.test21,
            R.drawable.test22,
            R.drawable.test23,
            R.drawable.test24,
            R.drawable.test25,

    };


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridview = (GridView) findViewById(R.id.myGridView);
        gridview.setAdapter(new MyAdapter(this));
        gridview.setNumColumns(4);

        /* final ImageButton imageButton = (ImageButton) findViewById(imgbtnDemo);
        ViewTreeObserver vto = imageButton.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener(){
            public boolean onPreDraw() {
                imageButton.getViewTreeObserver().removeOnPreDrawListener(this);
                DroidUtils.scaleButtonDrawables(imageButton);
            }
        });*/
    }

    public class MyAdapter extends BaseAdapter {

        private Context mContext;

        public MyAdapter(Context c) {
            mContext = c;
        }

        @Override
        public int getCount() {
            return mThumbIds.length;
        }

        @Override
        public Object getItem(int arg0) {
            return mThumbIds[arg0];
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        public void shareImage(int position) {

            if (android.os.Build.VERSION.SDK_INT > 23) {

                Uri bmpUri = null;
                try {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mThumbIds[position]);

                    // getExternalFilesDir() + "/Pictures" should match the declaration in fileprovider.xml paths
                    File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");

                    // wrap File object into a content provider. NOTE: authority here should match authority in manifest declaration
                    bmpUri = FileProvider.getUriForFile(CustomGridViewExample.this, "com.codepath.fileprovider", file);
                    file.getParentFile().mkdirs();

                    boolean isCreated = file.createNewFile();

                    FileOutputStream out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                    out.close();


                } catch (IOException e) {

                }

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("image/png");
                String imagePath = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS).getPath() + "share_image_" + ".png";
                File imageFileToShare = new File(imagePath);
                shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                startActivity(Intent.createChooser(shareIntent, "Share Sticker With..."));

            } else {

                Context context = getApplicationContext();
                CharSequence text = "Android Version not supported!";
                int duration = Toast.LENGTH_LONG;
                final Toast tost = Toast.makeText(context, text, duration);
                tost.show();
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final int testnum = position;
            View grid;

            if(convertView == null){
                grid = new View(mContext);
                LayoutInflater inflater = getLayoutInflater();
                grid = inflater.inflate(R.layout.grid_items, parent, false);
            }else{
                grid = (View)convertView;
            }

            ImageButton imageButton = (ImageButton)grid.findViewById(imgbtnDemo);
            imageButton.setBackground(getDrawable(mThumbIds[position]));
            imageButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    shareImage(testnum);
                }
            });
            return grid;
        }
    }
}