package com.example.vuduc.apptuthien;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.example.vuduc.adapters.MainMenuAdapter;
import com.example.vuduc.model.HomeCard;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvMainMenu;
    MainMenuAdapter mmaAdapter;
    List<HomeCard> homeCardLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.addControls();
        this.initMainMenu();
    }

    private void addControls(){
        rvMainMenu = (RecyclerView) findViewById(R.id.rvMainMenu);
    }

    private void initMainMenu(){
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvMainMenu.setLayoutManager(gridLayoutManager);
        rvMainMenu.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(0), true));
        rvMainMenu.setItemAnimator(new DefaultItemAnimator());
        homeCardLists = new ArrayList<>();
        mmaAdapter = new MainMenuAdapter(this, homeCardLists);
        rvMainMenu.setAdapter(mmaAdapter);

        MenuData();
    }

    private void MenuData() {
        int[] anhMenu = new int[]{
                R.drawable.logo
        };
        HomeCard a = new HomeCard(anhMenu[0],"Dự án từ thiện");
        homeCardLists.add(a);

        a = new HomeCard(anhMenu[0],"Điểm nóng");
        homeCardLists.add(a);

        a = new HomeCard(anhMenu[0],"Duyệt điểm nóng");
        homeCardLists.add(a);

        mmaAdapter.notifyDataSetChanged();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
