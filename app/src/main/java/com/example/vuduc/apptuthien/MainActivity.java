package com.example.vuduc.apptuthien;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vuduc.adapters.MainMenuAdapter;
import com.example.vuduc.model.HomeCard;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvMainMenu;
    MainMenuAdapter mmaAdapter;
    List<HomeCard> homeCardLists;
    private NavigationView navView;
    public DrawerLayout dlHomePage;
    private Toolbar toolbar;
    private View navHeader;
    private LinearLayout llUserCover;
    private ImageView ivUserAvatar;
    private TextView tvUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.addControls();
        this.initMainMenu();
        this.initNavigationDrawer();
    }

    private void addControls() {
        rvMainMenu = (RecyclerView) findViewById(R.id.rvMainMenu);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    private void initMainMenu() {
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
                R.drawable.img_logo
        };
        HomeCard a = new HomeCard(anhMenu[0], "Dự án từ thiện");
        homeCardLists.add(a);

        a = new HomeCard(anhMenu[0], "Điểm nóng");
        homeCardLists.add(a);

        a = new HomeCard(anhMenu[0], "Duyệt điểm nóng");
        homeCardLists.add(a);

        mmaAdapter.notifyDataSetChanged();
    }

    public void initNavigationDrawer() {
        navView = (NavigationView) findViewById(R.id.navView);
        // nav header set item
        navHeader = navView.getHeaderView(0);
        llUserCover = (LinearLayout) navHeader.findViewById(R.id.llUserCover);
        ivUserAvatar = (ImageView) navHeader.findViewById(R.id.ivUserAvatar);
        tvUserName = (TextView) navHeader.findViewById(R.id.tvUserName);
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                //Closing drawer on item click
                dlHomePage.closeDrawers();
                // Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    // Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.itemProfile:
                        startActivity(new Intent(MainActivity.this, UserProfileActivity.class));
                        return true;
                    case R.id.itemProject:
                        startActivity(new Intent(MainActivity.this, ListProjectMeActivity.class));
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }

            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        dlHomePage = (DrawerLayout) findViewById(R.id.dlHomePage);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, dlHomePage, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        dlHomePage.addDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_search, menu);
        return true;
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
