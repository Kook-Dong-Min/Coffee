package com.example.coffee;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Pay_tg extends AppCompatActivity {
    TableLayout tl_pay1;
    int total_price=0;
    TextView pay_price1;
    Button pay_back1,pay_pay1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pay_tg);

        tl_pay1=findViewById(R.id.tl_pay1);
        pay_price1=findViewById(R.id.pay_price1);
        pay_back1=findViewById(R.id.pay_back1);
        pay_pay1=findViewById(R.id.pay_pay1);

        Intent intent=getIntent();
        if (intent!=null)
        {
            for (int i=0;i<intent.getIntExtra("횟수",0);i++)
            {
                String name=intent.getStringExtra("이름"+i);
                int count=Integer.parseInt(intent.getStringExtra("수량"+i));
                int price=Integer.parseInt(intent.getStringExtra("가격"+i));

                payShoppingCart(name,count,price);
                total_price+=Integer.parseInt(intent.getStringExtra("가격"+i));
            }
            pay_price1.setText(""+total_price+"원");
        }

        pay_back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        pay_pay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbuilder=new AlertDialog.Builder(Pay_tg.this);
                alertbuilder.setTitle("결제");
                alertbuilder.setMessage("결제하시겠습니까?");
                alertbuilder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Pay_tg.this,"결제되었습니다.",Toast.LENGTH_LONG).show();
                    }
                });

                alertbuilder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });

                AlertDialog alertDialog=alertbuilder.create();
                alertDialog.show();
            }
        });
    }

    public void payShoppingCart(String name, int count, int price)
    {
        TableRow tableRow=(TableRow) LayoutInflater.from(this).inflate(R.layout.act_pay_fh_shoppingcart,null,false);
        TextView tvname=tableRow.findViewById(R.id.pay_tvname);
        TextView tvcount=tableRow.findViewById(R.id.pay_tvcount);
        TextView tvprice=tableRow.findViewById(R.id.pay_tvprice);
        tvname.setText(name);
        tvcount.setText(""+count+"개");
        tvprice.setText(""+price+"원");
        tl_pay1.addView(tableRow);
    }
}
