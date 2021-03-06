package starter.kit.rx.app.feature.main;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import butterknife.Bind;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import java.util.ArrayList;
import java.util.List;
import starter.kit.rx.app.R;
import starter.kit.rx.app.RxApp;
import starter.kit.rx.app.RxStarterActivity;
import starter.kit.rx.app.feature.feed.FeedFragment;

public class MainActivity extends RxStarterActivity {

  //save our header or result
  private AccountHeader headerResult = null;
  private Drawer result = null;
  @Bind(R.id.toolbar) Toolbar mToolbar;
  @Bind(R.id.viewPager) ViewPager mViewPager;
  @Bind(R.id.tabLayout) TabLayout mTabLayout;
  @Bind(R.id.collapsingToolbarLayout) CollapsingToolbarLayout mCollapsingToolbarLayout;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    setupToolbar();
    setupDrawer(savedInstanceState);

    setupViewPager();
    setupTabLayout();

    setupCollapsingToolbar();
  }

  private void setupCollapsingToolbar() {
    mCollapsingToolbarLayout.setTitleEnabled(false);
  }

  private void setupTabLayout() {
    mTabLayout.setupWithViewPager(mViewPager);
  }

  private void setupViewPager() {
    SimplePagerAdapter adapter = new SimplePagerAdapter(getSupportFragmentManager());
    adapter.addFrag(FeedFragment.create(), "Tab 1");
    adapter.addFrag(FeedFragment.create(), "Tab 2");
    adapter.addFrag(FeedFragment.create(), "Tab 3");
    mViewPager.setAdapter(adapter);

    final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, RxApp.appResources().getDisplayMetrics());
    mViewPager.setPageMargin(pageMargin);
  }

  private void setupToolbar() {
    setSupportActionBar(mToolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setTitle(R.string.app_name);
    }
  }

  private void setupDrawer(Bundle savedInstanceState) {
    // Create a few sample profile
    // NOTE you have to define the loader logic too. See the CustomApplication for more details
    final IProfile profile = new ProfileDrawerItem().withName("Smartydroid")
        .withEmail("smartydroid@gmail.com")
        .withIcon("https://avatars2.githubusercontent.com/u/13810934?v=3&s=460");

    // Create the AccountHeader
    headerResult = new AccountHeaderBuilder().withActivity(this)
        .withHeaderBackground(R.drawable.header)
        .addProfiles(profile)
        .withSavedInstance(savedInstanceState)
        .build();

    //Create the drawer
    result = new DrawerBuilder().withActivity(this)
        .withToolbar(mToolbar)
        .withHasStableIds(true)
        .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
        .addDrawerItems(new PrimaryDrawerItem().withName("Feeds").withIcon(FontAwesome.Icon.faw_android))
        .addStickyDrawerItems(
            new PrimaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cog).withIdentifier(10),
            new PrimaryDrawerItem().withName(R.string.drawer_item_open_source).withIcon(FontAwesome.Icon.faw_github)
        )
        .withSavedInstance(savedInstanceState)
        .withShowDrawerOnFirstLaunch(true)
        .build();
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    //add the values which need to be saved from the drawer to the bundle
    outState = result.saveInstanceState(outState);
    //add the values which need to be saved from the accountHeader to the bundle
    outState = headerResult.saveInstanceState(outState);
    super.onSaveInstanceState(outState);
  }

  @Override public void onBackPressed() {
    //handle the back press :D close the drawer first and if the drawer is closed close the activity
    if (result != null && result.isDrawerOpen()) {
      result.closeDrawer();
    } else {
      super.onBackPressed();
    }
  }

  static class SimplePagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragments = new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();

    public SimplePagerAdapter(FragmentManager manager) {
      super(manager);
    }

    @Override
    public Fragment getItem(int position) {
      return mFragments.get(position);
    }

    @Override
    public int getCount() {
      return mFragments.size();
    }

    public void addFrag(Fragment fragment, String title) {
      mFragments.add(fragment);
      mFragmentTitles.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return mFragmentTitles.get(position);
    }
  }

}
