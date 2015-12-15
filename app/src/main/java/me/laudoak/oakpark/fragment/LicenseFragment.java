package me.laudoak.oakpark.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.laudoak.oakpark.R;
import me.laudoak.oakpark.ui.dialog.XBaseDialog;
import me.laudoak.oakpark.utils.FileUtil;

/**
 * Created by LaudOak on 2015-11-25 at 14:46.
 */
public class LicenseFragment extends XBaseFragment
{

    private static final String TAG = LicenseFragment.class.getName();

    private static final String LICENSE_NAME = "license.txt";

    @Bind(R.id.fragment_license_text) TextView license;

    private String licenseText;

    public static LicenseFragment newInstance()
    {
        return new LicenseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        readLicenseString();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_license,container,false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        license.setText(licenseText);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume()
    {
        MobclickAgent.onPageStart(TAG);
        super.onResume();
    }

    @Override
    public void onPause()
    {
        MobclickAgent.onPageEnd(TAG);
        super.onPause();
    }

    private void readLicenseString()
    {
        try
        {
            licenseText = FileUtil.readTxtFromAssets(context,LICENSE_NAME);

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
