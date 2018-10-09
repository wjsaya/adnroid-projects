package top.wjsaya.app.layoutstudy;

import android.net.Uri;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity, ActionBarActivity
        implements  fragment1.OnFragmentInteractionListener,
                    fragment2.OnFragmentInteractionListener {
    private List<Fragment> fragmentList;

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d("asdasd", "asd123123123");
}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Fragment> fragments = new ArrayList<Fragment>();

        fragments.add(new fragment1());
        fragments.add(new fragment2());

        FragaAdapter adapter = new FragaAdapter(getSupportFragmentManager(), fragments);
        ViewPager vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setAdapter(adapter);
    }

    public class FragaAdapter extends FragmentPagerAdapter {
        public FragaAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            fragmentList = fragments;
        }

        @Override
        public Fragment getItem(int arg0) {
       //     Log.d("getItem", "called");
            return fragmentList.get(arg0);
        }

        @Override
        public int getCount() {
       //     Log.d("getCount", "called");
            return fragmentList.size();
        }
    }

}














