package com.zhejiang.haoxiadan.ui.activity.cart;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.fragment.cart.CartFragment;

public class CartActivity extends BaseFragmentActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        fragmentManager = getSupportFragmentManager();

        CartFragment cartFragment = CartFragment.newInstance(true);
        cartFragment.setCallback(new CartFragment.Callback() {
            @Override
            public void onBackClick() {
                finish();
            }
        });

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content,cartFragment);
        fragmentTransaction.commit();
    }
}
