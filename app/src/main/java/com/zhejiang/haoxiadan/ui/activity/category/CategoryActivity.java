package com.zhejiang.haoxiadan.ui.activity.category;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.fragment.category.CategoryFragment;

public class CategoryActivity extends BaseFragmentActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        fragmentManager = getSupportFragmentManager();

        CategoryFragment categoryFragment = CategoryFragment.newInstance(true);
        categoryFragment.setCallback(new CategoryFragment.Callback() {
            @Override
            public void onBackClick() {
                finish();
            }
        });

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content, categoryFragment);
        fragmentTransaction.commit();
    }
}
