package btu.treasurehunt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static btu.treasurehunt.R.drawable.coin;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    SessionManager session;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    public FloatingActionButton fab;
    public TextView fabtext;
    Account myaccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        setContentView(R.layout.activity_main);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setAlpha((float)0);
        fabtext = (TextView) findViewById(R.id.fabtext);
        fabtext.setText("0");
        fabtext.setTextColor(0xffb5993f);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // do your work
                switch(position) {
                    case 0:
                        fabtext.setVisibility(View.GONE);
                        break;
                    case 1:

                        final Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://databaserest.herokuapp.com/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        Accountservice service = retrofit.create(Accountservice.class);
                        String id="0";
                        if(session.isLoggedIn())
                        {
                            id=session.getUserDetails().get("id");
                        }
                        Call<Account> call = service.get(Long.parseLong(id));
                        call.enqueue(new Callback<Account>() {
                            @Override
                            public void onResponse(Call<Account> call, Response<Account> account) {
                                myaccount=account.body();
                                fabtext.setText(Integer.toString(myaccount.coins));
                            }

                            @Override
                            public void onFailure(Call<Account> call, Throwable t) {
                            }
                        });
                        fabtext.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

    }





/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
*/
   /* @Override
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
  */
    /**
     * A placeholder fragment containing a simple view.
     */

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position)
            {
                case 0 :
                    Collectingcoins collectingcoin = new Collectingcoins();
                    return  collectingcoin;

                case 1 :
                    Fragment spendingcoin = new MapsActivity();
                    return spendingcoin;
                default:
                    return  null;
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Collecting coins";
                case 1:
                    return "Spending coins";

            }
            return null;
        }
    }
}
