package com.jslps.pgmisnew;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.View;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.jslps.pgmisnew.adapter.PdfReportAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import android.os.Handler;

public class GeneratePdfActivity extends AppCompatActivity {
    String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };
    @BindView(R.id.list)
    RecyclerView list;
    public static String from;
    public static String to;

    //class gobals
    PdfReportAdapter aAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_pdf);
        ButterKnife.bind(this);

        init();



    }

    private void init() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Generating PDF please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        to = intent.getStringExtra("to");

        aAdapter = new PdfReportAdapter(this, PgPaymentReceiptReport.pdfGenerateList);
        LinearLayoutManager verticalLayoutmanager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(verticalLayoutmanager);
        list.setAdapter(aAdapter);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 8s = 8000ms
                generatePDF(list,"","");
            }
        }, 800);



    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void generatePDF(RecyclerView view,String fromdate,String todate) {
        RecyclerView.Adapter adapter = view.getAdapter();
        Bitmap bigBitmap = null;
        if (adapter != null) {
            int size = adapter.getItemCount();
            int height = 0;
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

            // Use 1/8th of the available memory for this memory cache.
            final int cacheSize = maxMemory / 8;
            LruCache<String, Bitmap> bitmaCache = new LruCache<>(cacheSize);
            for (int i = 0; i < size; i++) {
                RecyclerView.ViewHolder holder = adapter.createViewHolder(view, adapter.getItemViewType(i));
                adapter.onBindViewHolder(holder, i);
                holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
                holder.itemView.setDrawingCacheEnabled(true);
                holder.itemView.buildDrawingCache();
                Bitmap drawingCache = holder.itemView.getDrawingCache();
                if (drawingCache != null) {

                    bitmaCache.put(String.valueOf(i), drawingCache);
                }

                height += holder.itemView.getMeasuredHeight();
            }

            bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888);
            Canvas bigCanvas = new Canvas(bigBitmap);
            bigCanvas.drawColor(Color.WHITE);

            Document document = new Document(PageSize.A4,0,0,0,0);
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PGMISPaymentReceiptPDF";

            File dir = new File(path);
            if(!dir.exists())
                dir.mkdirs();

            final File file = new File(dir, "pgmis.pdf");
            FileOutputStream fOut = null;
            try {
                fOut = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            try {
                PdfWriter.getInstance(document, fOut);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            //

            for (int i = 0; i < size; i++) {

                try {
                    //Adding the content to the document
                    Bitmap bmp = bitmaCache.get(String.valueOf(i));
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    Image image = Image.getInstance(stream.toByteArray());
                    float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                            - document.rightMargin() - 0) / image.getWidth()) * 100; // 0 means you have no indentation. If you have any, change it.
                    image.scalePercent(scaler);
                    image.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER | com.itextpdf.text.Image.ALIGN_TOP);




                    if (!document.isOpen()) {
                        document.open();
                    }

                    document.add(image);

                } catch (Exception ex) {

                }
            }

            if (document.isOpen()) {
                document.close();
            }
            // Set on UI Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(progressDialog!=null){
                        progressDialog.dismiss();
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(GeneratePdfActivity.this);
                    builder.setTitle("Success")
                            .setMessage("PDF File Generated Successfully.")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setCancelable(false)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setDataAndType( FileProvider.getUriForFile(GeneratePdfActivity.this, BuildConfig.APPLICATION_ID + ".provider",file), "application/pdf");
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    startActivity(intent);

                                    finish();
                                }

                            }).show();
                }
            });
        }
    }
}
