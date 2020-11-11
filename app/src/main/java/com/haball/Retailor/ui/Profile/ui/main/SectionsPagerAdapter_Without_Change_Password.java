package com.haball.Retailor.ui.Profile.ui.main;

        import android.content.Context;

        import com.haball.R;

        import androidx.annotation.Nullable;
        import androidx.annotation.StringRes;
        import androidx.fragment.app.Fragment;
        import androidx.fragment.app.FragmentManager;
        import androidx.fragment.app.FragmentPagerAdapter;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter_Without_Change_Password extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_retailer_profile};
    private final Context mContext;

    public SectionsPagerAdapter_Without_Change_Password(Context context, FragmentManager fm) {
        super( fm );
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance( position + 1 );
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString( TAB_TITLES[position] );
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 1;
    }
}