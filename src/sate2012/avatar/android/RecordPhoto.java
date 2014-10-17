package sate2012.avatar.android;

import gupta.ashutosh.avatar.R;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class RecordPhoto extends Activity implements View.OnClickListener {
	private static File pic;
	private ImageView iv;
	private ImageButton ib;
	private ImageButton uploadB;
	public final static int cameraData = 0;
	private Bitmap bmp;
	private String OUTPUT_FILE = "_P.png";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.avatar_photo);
		iv = (ImageView) findViewById(R.id.ivReturnedPicture);
		ib = (ImageButton) findViewById(R.id.ibTakePic);
		ib.setOnClickListener(this);
		uploadB = (ImageButton) findViewById(R.id.upload_button);
		uploadB.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
			case (R.id.ibTakePic):
				Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(i, cameraData);
				break;
			case (R.id.upload_button):
				Intent data = new Intent();
				setResult(Activity.RESULT_OK, data);
				SelectMedia.setImage_filepath(pic.getAbsolutePath());
				finish();
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == cameraData) {
			if (data != null && data.getExtras() != null) {
				Bundle extras = data.getExtras();
				bmp = (Bitmap) extras.get("data");
				iv.setImageBitmap(bmp);
				pic = new File(Environment.getExternalStorageDirectory(), Constants.STORAGE_DIRECTORY + Constants.MEDIA_DIRECTORY
						+ System.currentTimeMillis() + OUTPUT_FILE);
				FileOutputStream stream = null;
				try {
					stream = new FileOutputStream(pic);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
			}
		}
	}

	public static String getPath() {
		return pic.getAbsolutePath();
	}
}