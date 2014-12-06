// 
//
//package com.lpii.evma.view.organizer;
//
//import com.lpii.evma.R;
//
//import android.app.ActionBar.TabListener;
//import android.os.Bundle;
//import android.support.v4.app.FragmentTransaction;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.ViewAnimator;
// 
//public class MainOrganiActivity extends SampleActivityBase implements TabListener{
//
//    public static final String TAG = "MainActivity";
//
//    // Whether the Log Fragment is currently shown
//    private boolean mLogShown;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        SlidingTabsColorsFragment fragment = new SlidingTabsColorsFragment();
//        transaction.replace(R.id.sample_content_fragment, fragment);
//        transaction.commit();
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        //getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//       /* MenuItem logToggle = menu.findItem(R.id.menu_toggle_log);
//        logToggle.setVisible(findViewById(R.id.sample_output) instanceof ViewAnimator);
//        logToggle.setTitle(mLogShown ? R.string.sample_hide_log : R.string.sample_show_log);*/
//
//        return super.onPrepareOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        /*switch(item.getItemId()) {
//            case R.id.menu_toggle_log:
//                mLogShown = !mLogShown;
//                ViewAnimator output = (ViewAnimator) findViewById(R.id.sample_output);
//                if (mLogShown) {
//                    output.setDisplayedChild(1);
//                } else {
//                    output.setDisplayedChild(0);
//                }
//                supportInvalidateOptionsMenu();
//                return true;
//        }*/
//        return super.onOptionsItemSelected(item);
//    }
//
//    
//}
