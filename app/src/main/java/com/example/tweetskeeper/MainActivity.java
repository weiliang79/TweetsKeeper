package com.example.tweetskeeper;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;

    private boolean addIsRotate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Floating Button
        FloatingActionButton fabAddTweet = findViewById(R.id.fabAddTweet);
        FloatingActionButton fabAddTag = findViewById(R.id.fabAddTag);
        AddAnimation.init(fabAddTweet);
        AddAnimation.init(fabAddTag);

        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addIsRotate = AddAnimation.rotateFab(view, !addIsRotate);
                if(addIsRotate){
                    AddAnimation.showIn(fabAddTweet);
                    AddAnimation.showIn(fabAddTag);
                }else{
                    AddAnimation.showOut(fabAddTweet);
                    AddAnimation.showOut(fabAddTag);
                }
            }
        });

        fabAddTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(MainActivity.this);
                View addTweetView = li.inflate(R.layout.add_tweet_dialog, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setView(addTweetView);

                alertDialogBuilder
                        .setTitle("Add Tweet")
                        .setCancelable(true)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();
            }
        });

        fabAddTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(MainActivity.this);
                View addTweetView = li.inflate(R.layout.add_tag_dialog, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setView(addTweetView);

                alertDialogBuilder
                        .setTitle("Add Tweet")
                        .setCancelable(true)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();
            }
        });

        //drawer layout
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_settings, R.id.nav_tags)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId() == R.id.nav_settings){
                    AddAnimation.showOut(fab);
                    if(addIsRotate){
                        addIsRotate = AddAnimation.rotateFab(findViewById(R.id.fabAdd), !addIsRotate);
                        AddAnimation.showOut(fabAddTweet);
                        AddAnimation.showOut(fabAddTag);
                    }
                } else {
                    AddAnimation.showIn(fab);
                }
            }
        });

        AddAnimation.init(fabAddTweet);
        AddAnimation.init(fabAddTag);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}