package ca.cykadash.productbarcodes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class MainActivity extends AppCompatActivity {

    Button btn_search;
    FloatingActionButton btn_add;
    EditText et_search;
    ListView lv_productList;

    ArrayAdapter productArrayAdapter;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_search = findViewById(R.id.btn_search);
        btn_add = findViewById(R.id.btn_add);
        et_search = findViewById(R.id.et_search);
        lv_productList = findViewById(R.id.lv_productList);

        databaseHelper = new DatabaseHelper(MainActivity.this);

        ShowProductsOnListView(databaseHelper);



        btn_add.setOnClickListener(v -> {
                Intent intent = new Intent(this, NewProductActivity.class);
                int requestCode = 1;
                startActivityForResult(intent, requestCode);
        });

        btn_search.setOnClickListener(v -> {
            DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);

            try {
                ShowProductsOnSearchView(databaseHelper, et_search.getText().toString());
            }
            catch (Exception e) {
                Toast.makeText(MainActivity.this, "Enter a search term", Toast.LENGTH_SHORT).show();
            }
        });

        lv_productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductModel clickedProduct = (ProductModel) parent.getItemAtPosition(position);

                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                final View v = factory.inflate(R.layout.alert, null);
                ImageView dialog_imageview = v.findViewById(R.id.dialog_imageview);
                builder1.setView(v);
                builder1.setMessage(clickedProduct.toString() + " " + clickedProduct.getSku());
                builder1.setCancelable(true);

                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(clickedProduct.getSku().toString(), BarcodeFormat.QR_CODE, 512, 512);
                    Bitmap bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.RGB_565);
                    for (int i = 0; i < 512; i++){
                        for (int j = 0; j < 512; j++){
                            bitmap.setPixel(i,j,bitMatrix.get(i,j)? Color.BLACK:Color.WHITE);
                        }
                    }
                    dialog_imageview.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }

                builder1.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                databaseHelper.deleteOne(clickedProduct);
                                Toast.makeText(MainActivity.this, "Deleted " + clickedProduct, Toast.LENGTH_SHORT).show();
                                ShowProductsOnListView(databaseHelper);
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                String name = data.getStringExtra("name");
                String sku = data.getStringExtra("sku");
                String console = data.getStringExtra("console");

                ProductModel productModel;

                try {
                    productModel = new ProductModel(-1, name, sku, console);
                }
                catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error creating entry", Toast.LENGTH_SHORT).show();
                    productModel = new ProductModel(-1, "error", "0", "");
                }

                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);

                boolean success = databaseHelper.addOne(productModel);

                Toast.makeText(MainActivity.this, "Success = " + success, Toast.LENGTH_SHORT).show();
                ShowProductsOnListView(databaseHelper);

            }
        }
    }

    private void ShowProductsOnListView(DatabaseHelper databaseHelper) {
        productArrayAdapter = new ArrayAdapter<ProductModel>(MainActivity.this, android.R.layout.simple_list_item_1, databaseHelper.getEveryone());
        lv_productList.setAdapter(productArrayAdapter);
    }

    private void ShowProductsOnSearchView(DatabaseHelper databaseHelper, String s) {
        productArrayAdapter = new ArrayAdapter<ProductModel>(MainActivity.this, android.R.layout.simple_list_item_1, databaseHelper.getSearch(s));
        lv_productList.setAdapter(productArrayAdapter);
    }
}