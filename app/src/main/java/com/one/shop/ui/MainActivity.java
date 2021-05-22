package com.one.shop.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.one.shop.R;
import com.one.shop.data.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.one.shop.R.drawable.store_aldi;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tv_username, header_name, header_email;
    private FirebaseUser currentUser;
    private FirebaseAuth firebaseAuth;
    LinearLayout zipcode_search;
    LinearLayout selected_store;
    LinearLayout storeList;
    ImageView zipcode_edit;
    ImageView store_edit;
    SearchView searchview;
    RecyclerView recycler_stores;
    RecyclerView.LayoutManager layoutManager;
    StoreAdapter adapter;
    SharedPreferences preferences;
    TextView current_store;
    TextView current_zip;
    ImageView store_image;
    LinearLayout store_layout;
    TextView clickandenter;
    private int storeIndex = -1;

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPref = getSharedPreferences("settings", Context.MODE_PRIVATE);
        header_email.setText(sharedPref.getString("email", "name"));
        header_name.setText(sharedPref.getString("name", "email"));

        String selectedStore = preferences.getString("store_name", "");
        String selectedImage = preferences.getString("store_image", "");
        String selected_zipcode = preferences.getString("zipcode", "");
        if (selectedStore.isEmpty() && selectedImage.isEmpty()) {
            selected_store.setVisibility(View.GONE);
            zipcode_search.setVisibility(View.VISIBLE);
            storeList.setVisibility(View.VISIBLE);
        } else {
            zipcode_search.setVisibility(View.GONE);
            storeList.setVisibility(View.GONE);
            selected_store.setVisibility(View.VISIBLE);
            store_layout.setVisibility(View.VISIBLE);
            current_store.setText("Store you have selected: " + selectedStore);
            current_zip.setText("Current zipcode: " + selected_zipcode);
            switch (selectedImage) {
                case "store_aldi":
                    store_image.setBackgroundResource(store_aldi);
                    break;
                case "store_bigy":
                    store_image.setBackgroundResource(R.drawable.store_bigy);
                    break;
                case "store_brothersmarket":
                    store_image.setBackgroundResource(R.drawable.store_brothersmarket);
                    break;
                case "store_rochebros":
                    store_image.setBackgroundResource(R.drawable.store_rochebros);
                    break;
                case "store_shaws":
                    store_image.setBackgroundResource(R.drawable.store_shaws);
                    break;
                case "store_stopshop":
                    store_image.setBackgroundResource(R.drawable.store_stopshop);
                    break;
                case "store_target":
                    store_image.setBackgroundResource(R.drawable.store_target);
                    break;
                case "store_wegmen":
                    store_image.setBackgroundResource(R.drawable.store_wegmen);
                    break;
                case "store_americafood":
                    store_image.setBackgroundResource(R.drawable.store_americafood);
                    break;

                case "store_marketbasket":
                    store_image.setBackgroundResource(R.drawable.store_marketbasket);
                    break;

                case "store_market32":
                    store_image.setBackgroundResource(R.drawable.store_market32);
                    break;

                case "store_starmarket":
                    store_image.setBackgroundResource(R.drawable.store_starmarket);
                    break;
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences("OneShop", Context.MODE_PRIVATE);
        current_store = findViewById(R.id.current_store);
        current_zip = findViewById(R.id.current_zip);
        zipcode_search = findViewById(R.id.zipcode_search);
        selected_store = findViewById(R.id.selected_store);
        storeList = findViewById(R.id.storeList);
        zipcode_edit = findViewById(R.id.zipcode_edit);
        store_edit = findViewById(R.id.store_edit);
        store_image = findViewById(R.id.store_image);
        store_layout = findViewById(R.id.store_layout);
        clickandenter = findViewById(R.id.clickandenter);
        recycler_stores = findViewById(R.id.recycler_stores);
        recycler_stores.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_stores.setLayoutManager(layoutManager);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firebaseAuth = FirebaseAuth.getInstance();

        currentUser = firebaseAuth.getCurrentUser();

        //tv_username = findViewById(R.id.tv_username);
        searchview = findViewById(R.id.searchview);

        zipcode_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zipcode_search.setVisibility(View.VISIBLE);
                storeList.setVisibility(View.VISIBLE);
                selected_store.setVisibility(View.GONE);
            }
        });
        store_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                store_layout.setVisibility(View.GONE);
                storeList.setVisibility(View.VISIBLE);
                fetchStores();
            }
        });
        store_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new
                        Intent(MainActivity.this, ProductsActivity.class));
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        header_name = (TextView) hView.findViewById(R.id.header_name);
        header_email = (TextView) hView.findViewById(R.id.header_email);
        SharedPreferences sharedPref = getSharedPreferences("settings", Context.MODE_PRIVATE);
        header_email.setText(sharedPref.getString("email", "name"));
        header_name.setText(sharedPref.getString("name", "email"));
        searchview.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if (adapter != null)
                    adapter.clear();
                clickandenter.setVisibility(View.VISIBLE);
                return false;
            }
        });
        searchview.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickandenter.setVisibility(View.GONE);
            }
        });

    }

    private void fetchStores() {

        if (!searchview.getQuery().toString().isEmpty()) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("zipcode", searchview.getQuery().toString());
            editor.commit();
        }

        int storeIndex_ = getIndex();
        if (storeIndex_ == storeIndex) {
            storeIndex = getIndex();
        } else {
            storeIndex = storeIndex_;
        }
        ArrayList<Store> stores = getStores(storeIndex);
        adapter = new StoreAdapter(stores, MainActivity.this);
        recycler_stores.setAdapter(adapter);
    }

    private int getIndex() {
        Random r = new Random();
        int i1 = r.nextInt(5 - 1) + 1;
        return i1;
    }

    private ArrayList<Store> getStores(int storeIndex) {
        Log.e("GET", "storeIndex " + storeIndex);
        List<Store> stores = new ArrayList<>();
        if (storeIndex == 1 || storeIndex == 0) {
            Store store1 = new Store();
            store1.setName("ALDI");
            store1.setImage("store_aldi");
            stores.add(store1);
            Store store2 = new Store();
            store2.setName("Big Y World Class Market");
            store2.setImage("store_bigy");
            stores.add(store2);
            Store store3 = new Store();
            store3.setName("Brothers Marketplace");
            store3.setImage("store_brothersmarket");
            stores.add(store3);
        }
        if (storeIndex == 2) {
            Store store4 = new Store();
            store4.setName("Roche Bros.");
            store4.setImage("store_rochebros");
            stores.add(store4);
            Store store5 = new Store();
            store5.setName("Shaw's");
            store5.setImage("store_shaws");
            stores.add(store5);
            Store store6 = new Store();
            store6.setName("Stop & Shop");
            store6.setImage("store_stopshop");
            stores.add(store6);
        }

        if (storeIndex == 3) {
            Store store7 = new Store();
            store7.setName("Target");
            store7.setImage("store_target");
            stores.add(store7);
            Store store8 = new Store();
            store8.setName("Wegmans");
            store8.setImage("store_wegmen");
            stores.add(store8);
            Store store9 = new Store();
            store9.setName("America's Food Basket");
            store9.setImage("store_americafood");
            stores.add(store9);
        }

        if (storeIndex == 4 || storeIndex > 4) {
            Store store10 = new Store();
            store10.setName("Market Basket");
            store10.setImage("store_marketbasket");
            stores.add(store10);
            Store store11 = new Store();
            store11.setName("Market 32");
            store11.setImage("store_market32");
            stores.add(store11);
            Store store12 = new Store();
            store12.setName("Star Market");
            store12.setImage("store_starmarket");
            stores.add(store12);
        }

        return (ArrayList<Store>) stores;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.btn_logout) {
            if (currentUser != null) {
                firebaseAuth.signOut();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "You aren't logged in Yet!", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.btn_cart) {
            Intent i = new Intent(MainActivity.this, CartActivity.class);
            startActivity(i);
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_orders) {
            // Handle the camera action
            startActivity(new Intent(MainActivity.this, OrdersActivity.class));
        } else if (id == R.id.nav_cart) {
            startActivity(new Intent(MainActivity.this, CartActivity.class));
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void findStores(View view) {
        String search_key = searchview.getQuery().toString();
        if (search_key.isEmpty() || search_key.length() > 5) {
            Toast.makeText(getApplicationContext(),
                    "Please enter valid zip code", Toast.LENGTH_SHORT).show();

            return;
        }
        fetchStores();
        //Toast.makeText(getApplicationContext(), searchview.getQuery().toString(), Toast.LENGTH_SHORT).show();

    }
}
