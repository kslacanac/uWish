package hr.spacecontrol.uwish.activities;

import com.google.firebase.auth.FacebookAuthCredential;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.fragments.Calendar;
import hr.spacecontrol.uwish.fragments.Dashboard;
import hr.spacecontrol.uwish.fragments.FriendRequests;
import hr.spacecontrol.uwish.fragments.MyEvents;
import hr.spacecontrol.uwish.fragments.MyFriendsList;
import hr.spacecontrol.uwish.fragments.MyWishList;
import hr.spacecontrol.uwish.objects.User;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private TextView toolbar_title;

    public static int navItemIndex = 0;

    public static String CURRENT_TAG = "Dashboard";

    private String[] activityTitles;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private String mUserId;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFirebaseAuth = FirebaseAuth.getInstance();
        //mFirebaseUser = null;
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        } else {
            mDatabase = FirebaseDatabase.getInstance().getReference();
            DatabaseReference friendRequests = mDatabase.child("Users").child(mFirebaseUser.getUid()).child("FriendRequests");
            ValueEventListener valueEventListener = friendRequests.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getChildrenCount() != 0) {
                        navigationView.getMenu().getItem(5).setActionView(R.layout.menu_dot);

                    } else {
                        navigationView.getMenu().getItem(5).setActionView(null);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mDatabase = FirebaseDatabase.getInstance().getReference().child(mFirebaseUser.getUid());

            mUserId = mFirebaseUser.getUid();

            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            navigationView = (NavigationView) findViewById(R.id.nav_view);

            activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

            setUpNavigationView();

            if (savedInstanceState == null) {
                navItemIndex = 0;
                CURRENT_TAG = "Dashboard";
                loadHomeFragment();
            }
        }
    }


    private void loadLogInView() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /***
     * Returns respected fragment that user selected from navigation menu
     */
    private void loadHomeFragment() {
        selectNavMenu();
        setToolbarTitle();


        // if user select the current navigation menu again, just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }

        Fragment fragment = getHomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, CURRENT_TAG);
        fragmentTransaction.commit();

        drawer.closeDrawers();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                return Dashboard.newInstance();
            case 1:
                return MyWishList.newInstance();
            case 2:
                return MyFriendsList.newInstance();
            case 3:
                return FriendRequests.newInstance();
            case 4:
                return Calendar.newInstance();
            case 5:
                return MyEvents.newInstance();
            default:
                return Dashboard.newInstance();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    case R.id.nav_dashboard:
                        navItemIndex = 0;
                        CURRENT_TAG = "Dashboard";
                        break;
                    case R.id.nav_myprofile:
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_mywishlist:
                        navItemIndex = 1;
                        CURRENT_TAG = "MyWishList";
                        break;
                    case R.id.nav_makeawish:
                        startActivity(new Intent(MainActivity.this, MakeAWishActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_friendlist:
                        navItemIndex = 2;
                        CURRENT_TAG = "MyFriendsList";
                        break;
                    case R.id.nav_friendrequests:
                        navItemIndex = 3;
                        CURRENT_TAG = "FriendRequests";
                        break;
                    case R.id.nav_invitefriends:
                        startActivity(new Intent(MainActivity.this, InviteFriendsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_calendar:
                        navItemIndex = 4;
                        CURRENT_TAG = "Calendar";
                        break;
                    case R.id.nav_createevent:
                        startActivity(new Intent(MainActivity.this, CreateEventActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_myevents:
                        navItemIndex = 5;
                        CURRENT_TAG = "MyEvents";
                        break;
                    case R.id.nav_privacy:
                        startActivity(new Intent(MainActivity.this, ManagePrivacyActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_logout:
                        //log out

                        FirebaseAuth.getInstance().signOut();
                        mFirebaseAuth.signOut();
                        loadLogInView();
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle(this,
                        drawer,
                        toolbar,
                        R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close) {
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
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_logofinal_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);
        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        } else {
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = "Dashboard";
                loadHomeFragment();
                return;
            }
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("Search for friends");
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        //searchView.setOnQueryTextListener(this);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
                intent.putExtra("friends", s);
                //intent.putParcelableArrayListExtra("friends", (ArrayList<? extends Parcelable>) searchResults);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
