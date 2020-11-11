package com.haball.Distributor.ui.retailer.Payment.ViewPayment;

        import android.os.Bundle;

        import com.haball.R;
        import com.google.android.material.floatingactionbutton.FloatingActionButton;
        import com.google.android.material.snackbar.Snackbar;
        import com.google.android.material.tabs.TabLayout;

        import androidx.annotation.NonNull;
        import androidx.fragment.app.Fragment;
        import androidx.viewpager.widget.ViewPager;
        import androidx.appcompat.app.AppCompatActivity;

        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;

        import com.haball.Distributor.ui.retailer.Payment.ViewPayment.ui.main.SectionsPagerAdapter;

public class RetailerPaymentView extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_retailer_payment_view, container, false);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getActivity(), getChildFragmentManager());
        final ViewPager viewPager = root.findViewById(R.id.view_pager_ret_view_payment);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = root.findViewById(R.id.tabs_ret_view_payment);
        tabs.setupWithViewPager(viewPager);

        return root;
    }
}