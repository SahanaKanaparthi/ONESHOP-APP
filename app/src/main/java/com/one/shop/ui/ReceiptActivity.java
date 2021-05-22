package com.one.shop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gkemon.XMLtoPDF.PdfGenerator;
import com.gkemon.XMLtoPDF.PdfGeneratorListener;
import com.gkemon.XMLtoPDF.model.FailureResponse;
import com.gkemon.XMLtoPDF.model.SuccessResponse;
import com.one.shop.R;
import com.one.shop.data.Cart;

import java.util.List;

import info.hoang8f.widget.FButton;

public class ReceiptActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private TextView receipt_total;
    private List<Cart> receiptList;
    private double receipt_total_string;
    private FButton btn_generate_receipt;
    private LinearLayout item_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt_layout);
        btn_generate_receipt = findViewById(R.id.btn_generate_receipt);
        receiptList = getIntent().getExtras().getParcelableArrayList("cartdata");
        receipt_total_string = getIntent().getExtras().getDouble("total");
        recyclerView = findViewById(R.id.recycler_receipt);
        receipt_total = findViewById(R.id.receipt_total);
        item_layout = findViewById(R.id.item_layout);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        ReceiptAdapter adapter = new ReceiptAdapter(receiptList, ReceiptActivity.this);
        recyclerView.setAdapter(adapter);
        receipt_total.setText("Total:                     $" + String.format("%.2f", Double.valueOf(receipt_total_string)));

        btn_generate_receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateReceipt();
            }
        });
    }

    private void generateReceipt() {
        PdfGenerator.getBuilder()
                .setContext(ReceiptActivity.this)
                .fromViewSource()
                .fromView(item_layout)
                .setPageSize(PdfGenerator.PageSize.A5)
                .setFileName("Test-PDF")
                .setFolderName("Test-PDF-folder")
                .openPDFafterGeneration(true)
                .build(new PdfGeneratorListener() {
                    @Override
                    public void onFailure(FailureResponse failureResponse) {
                        super.onFailure(failureResponse);
                    }

                    @Override
                    public void onStartPDFGeneration() {
                        /*When PDF generation begins to start*/
                    }

                    @Override
                    public void onFinishPDFGeneration() {
                        /*When PDF generation is finished*/
                    }

                    @Override
                    public void showLog(String log) {
                        super.showLog(log);
                    }

                    @Override
                    public void onSuccess(SuccessResponse response) {
                        super.onSuccess(response);
                    }
                });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(new Intent(ReceiptActivity.this, MainActivity.class));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        //finish();
    }
}