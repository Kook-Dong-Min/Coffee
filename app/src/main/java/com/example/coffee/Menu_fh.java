package com.example.coffee;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

public class Menu_fh extends AppCompatActivity {

    TabLayout tb_fh1;
    ViewPager2 vp_fh1;
    TableLayout tl_fh1;
    Menu_fh This;
    int topping_price=0;
    boolean icy=false,vanilla=false,hazelnut=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_menu_fh);

        tb_fh1=findViewById(R.id.tb_tg1);
        vp_fh1=findViewById(R.id.vp_tg1);
        tl_fh1=findViewById(R.id.tl_tg1);
        Button back1=findViewById(R.id.back2);
        Button pay1=findViewById(R.id.pay2);
        This=this;

        pay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tl_fh1.getChildAt(0) != null) {
                    Intent intent = new Intent(Menu_fh.this, Pay_fh.class);

                    for (int i = 0; i < tl_fh1.getChildCount(); i++) {
                        LinearLayout linearLayout = (LinearLayout) tl_fh1.getChildAt(i);
                        TableRow tableRow = (TableRow) linearLayout.getChildAt(0);
                        TextView tvname = (TextView) tableRow.getChildAt(0);
                        TextView tvcount = (TextView) tableRow.getChildAt(2);
                        TextView tvprice = (TextView) tableRow.getChildAt(4);

                        String name = tvname.getText().toString();
                        String count = tvcount.getText().toString();
                        String price = tvprice.getText().toString();

                        intent.putExtra("이름" + i, name);
                        intent.putExtra("수량" + i, count);
                        intent.putExtra("가격" + i, price);
                    }
                    intent.putExtra("횟수", tl_fh1.getChildCount());

                    startActivity(intent);
                }
            }
        });

        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        vp_fh1.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position)
                {
                    case 0:
                        return new Fragfh_best();
                    case 1:
                        return new Fragfh_coffee();
                    case 2:
                        return new Fragfh_noncoffee();
                    case 3:
                        return new Fragfh_smoothie();
                    case 4:
                        return new Fragfh_juice();
                    case 5:
                        return new Fragfh_dessert();
                    default:
                        return new Fragfh_best();
                }
            }

            @Override
            public int getItemCount() {
                return 6;
            }
        });

        for (int i=0;i<6;i++)
        {
            TabLayout.Tab tab=tb_fh1.newTab();
            if(i==0)
            {
                tab.setCustomView(createCustomTab("베스트"));
            } else if (i==1) {
                tab.setCustomView(createCustomTab("커피"));
            } else if (i==2) {
                tab.setCustomView(createCustomTab("논커피"));
            } else if (i==3) {
                tab.setCustomView(createCustomTab("스무디"));
            } else if (i==4) {
                tab.setCustomView(createCustomTab("주스"));
            } else if (i==5) {
                tab.setCustomView(createCustomTab("디저트"));
            }

            tb_fh1.addTab(tab);
        }

        tb_fh1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp_fh1.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        vp_fh1.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tb_fh1.selectTab(tb_fh1.getTabAt(position));
            }
        });
    }

    private View createCustomTab(String tabText)
    {
        View customView= LayoutInflater.from(this).inflate(R.layout.custom_tab_fh,null,false);
        TextView ttv1=customView.findViewById(R.id.ttv1);
        ttv1.setText(tabText);
        return customView;
    }

    public void putShoppingCart(String name, int count, int price)
    {
        for (int i = 0; i < tl_fh1.getChildCount(); i++) {
            LinearLayout ll = (LinearLayout) tl_fh1.getChildAt(i);
            TextView tvName = ll.findViewById(R.id.tv1);
            if (tvName.getText().toString().equals(name)) {
                return;
            }
        }

        LinearLayout ll1=(LinearLayout) LayoutInflater.from(this).inflate(R.layout.act_menu_fh_shoppingcart,null,false);
        TableRow tr1=ll1.findViewById(R.id.tr1);
        TextView tv1=tr1.findViewById(R.id.tv1);
        TextView tv2=tr1.findViewById(R.id.tv2);
        TextView tv3=tr1.findViewById(R.id.tv3);
        Button bt1=tr1.findViewById(R.id.bt1);
        Button bt2=tr1.findViewById(R.id.bt2);
        Button bt3=tr1.findViewById(R.id.bt3);
        tv1.setText(name);
        tv2.setText(""+count);
        tv3.setText(""+price);
        tl_fh1.addView(ll1);

        bt1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (Integer.parseInt(tv2.getText().toString()) > 1) {
                    int localCount = Integer.parseInt(tv2.getText().toString());
                    int localPrice = Integer.parseInt(tv3.getText().toString());

                    localCount--;
                    localPrice -= price;
                    tv2.setText("" + localCount);
                    tv3.setText("" + localPrice);
                }
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int localCount = Integer.parseInt(tv2.getText().toString());
                int localPrice = Integer.parseInt(tv3.getText().toString());

                localCount++;
                localPrice += price;
                tv2.setText("" + localCount);
                tv3.setText("" + localPrice);
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TableRow tableRow=(TableRow) view.getParent();
                LinearLayout linearLayout=(LinearLayout) tableRow.getParent();
                TableLayout tableLayout=(TableLayout) linearLayout.getParent();
                tableLayout.removeView(linearLayout);
            }
        });
    }

    public void cold_brew_float(View view)
    {
        topping_cold_brew_float(true,true,false);
    }

    public void cold_brew_malt(View view)
    {
        topping_cold_brew_malt(true,false,true);
    }

    public void topping_cold_brew_float(boolean icy,boolean vanilla,boolean hazelnut)
    {
        this.topping_price=0;
        this.icy=false;
        this.vanilla=false;
        this.hazelnut=false;
        Dialog toppingDialog=new Dialog(this);
        toppingDialog.setContentView(R.layout.act_topping);
        toppingDialog.setTitle("토핑");

        RadioButton hot=toppingDialog.findViewById(R.id.hot);
        RadioButton ice=toppingDialog.findViewById(R.id.ice);
        CheckBox syrup_vanilla=toppingDialog.findViewById(R.id.vanilla);
        CheckBox syrup_hazelnut=toppingDialog.findViewById(R.id.hazelnut);
        Button back=toppingDialog.findViewById(R.id.topping_back1);
        Button ok=toppingDialog.findViewById(R.id.topping_ok1);

        if (icy)
        {
            hot.setVisibility(View.VISIBLE);
            ice.setVisibility(View.VISIBLE);
        }
        else
        {
            hot.setVisibility(View.INVISIBLE);
            ice.setVisibility(View.INVISIBLE);
        }

        if (vanilla)
        {
            syrup_vanilla.setVisibility(View.VISIBLE);
        }
        else
        {
            syrup_vanilla.setVisibility(View.INVISIBLE);
        }

        if(hazelnut)
        {
            syrup_hazelnut.setVisibility(View.VISIBLE);
        }
        else
        {
            syrup_hazelnut.setVisibility(View.INVISIBLE);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toppingDialog.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ice.isChecked())
                {
                    Menu_fh.this.topping_price+=500;
                    Menu_fh.this.icy=true;
                }

                if (syrup_vanilla.isChecked())
                {
                    Menu_fh.this.topping_price+=600;
                    Menu_fh.this.vanilla=true;
                }

                if (syrup_hazelnut.isChecked())
                {
                    Menu_fh.this.topping_price+=700;
                    Menu_fh.this.hazelnut=true;
                }

                toppingDialog.dismiss();

                String name="콜드 브루 플로트";

                if (Menu_fh.this.icy)
                {
                    name+="(아이스)";
                }

                if (Menu_fh.this.vanilla)
                {
                    name+="(바닐라)";
                }

                if (Menu_fh.this.hazelnut)
                {
                    name+="(헤이즐넛)";
                }

                putShoppingCart(name,1,3000+topping_price);
            }
        });

        toppingDialog.show();
    }

    public void topping_cold_brew_malt(boolean icy,boolean vanilla,boolean hazelnut)
    {
        this.topping_price=0;
        this.icy=false;
        this.vanilla=false;
        this.hazelnut=false;
        Dialog toppingDialog=new Dialog(this);
        toppingDialog.setContentView(R.layout.act_topping);
        toppingDialog.setTitle("토핑");

        RadioButton hot=toppingDialog.findViewById(R.id.hot);
        RadioButton ice=toppingDialog.findViewById(R.id.ice);
        CheckBox syrup_vanilla=toppingDialog.findViewById(R.id.vanilla);
        CheckBox syrup_hazelnut=toppingDialog.findViewById(R.id.hazelnut);
        Button back=toppingDialog.findViewById(R.id.topping_back1);
        Button ok=toppingDialog.findViewById(R.id.topping_ok1);

        if (icy)
        {
            hot.setVisibility(View.VISIBLE);
            ice.setVisibility(View.VISIBLE);
        }
        else
        {
            hot.setVisibility(View.INVISIBLE);
            ice.setVisibility(View.INVISIBLE);
        }

        if (vanilla)
        {
            syrup_vanilla.setVisibility(View.VISIBLE);
        }
        else
        {
            syrup_vanilla.setVisibility(View.INVISIBLE);
        }

        if(hazelnut)
        {
            syrup_hazelnut.setVisibility(View.VISIBLE);
        }
        else
        {
            syrup_hazelnut.setVisibility(View.INVISIBLE);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toppingDialog.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ice.isChecked())
                {
                    Menu_fh.this.topping_price+=500;
                    Menu_fh.this.icy=true;
                }

                if (syrup_vanilla.isChecked())
                {
                    Menu_fh.this.topping_price+=600;
                    Menu_fh.this.vanilla=true;
                }

                if (syrup_hazelnut.isChecked())
                {
                    Menu_fh.this.topping_price+=700;
                    Menu_fh.this.hazelnut=true;
                }

                toppingDialog.dismiss();

                String name="콜드 브루 몰트";

                if (Menu_fh.this.icy)
                {
                    name+="(아이스)";
                }

                if (Menu_fh.this.vanilla)
                {
                    name+="(바닐라)";
                }

                if (Menu_fh.this.hazelnut)
                {
                    name+="(헤이즐넛)";
                }

                putShoppingCart(name,1,3500+topping_price);
            }
        });

        toppingDialog.show();
    }
}
