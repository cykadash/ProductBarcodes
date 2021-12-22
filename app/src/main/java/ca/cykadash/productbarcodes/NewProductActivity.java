package ca.cykadash.productbarcodes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class NewProductActivity extends AppCompatActivity {

    Button btn_cancel, btn_save, btn_test;
    EditText et_name, et_sku;
    ImageView img_barcode;
    Spinner spn_console;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        setTitle("Add Product");

        btn_cancel = findViewById(R.id.btn_cancel);
        btn_save = findViewById(R.id.btn_save);
        btn_test = findViewById(R.id.btn_test);
        et_name = findViewById(R.id.et_name);
        et_sku = findViewById(R.id.et_sku);
        img_barcode = findViewById(R.id.img_barcode);
        spn_console = findViewById(R.id.spn_console);


        btn_test.setOnClickListener((v -> {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(et_sku.getText().toString(), BarcodeFormat.QR_CODE, img_barcode.getWidth(), img_barcode.getHeight());
                Bitmap bitmap = Bitmap.createBitmap(img_barcode.getWidth(), img_barcode.getHeight(), Bitmap.Config.RGB_565);
                for (int i = 0; i < img_barcode.getWidth(); i++){
                    for (int j = 0; j < img_barcode.getHeight(); j++){
                        bitmap.setPixel(i,j,bitMatrix.get(i,j)? Color.BLACK:Color.WHITE);
                    }
                }
                img_barcode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }));

        btn_cancel.setOnClickListener((v -> {
            finish();
        }));

        btn_save.setOnClickListener((v -> {

            try {
                String name = et_name.getText().toString();
                String sku = et_sku.getText().toString();
                String console = spn_console.getSelectedItem().toString();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("name", name);
                resultIntent.putExtra("sku", sku);
                resultIntent.putExtra("console", console);

                setResult(RESULT_OK, resultIntent);
                finish();
            }
            catch (Exception e) {
                Toast.makeText(NewProductActivity.this, "Fill all fields before saving!", Toast.LENGTH_SHORT).show();
            }


        }));

    }

}