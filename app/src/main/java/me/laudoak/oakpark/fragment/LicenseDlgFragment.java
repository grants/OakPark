package me.laudoak.oakpark.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.laudoak.oakpark.R;
import me.laudoak.oakpark.ui.dialog.XBaseDialog;
import me.laudoak.oakpark.utils.FileUtil;

/**
 * Created by LaudOak on 2015-11-25 at 14:46.
 */
public class LicenseDlgFragment extends XBaseDialog {

    private static final String TAG = "LicenseDlgFragment";

    private static final String LICENSE_NAME = "license.txt";


    @Bind(R.id.fragment_license_text) TextView license;
    private View contentView;
    private String licenseText;

    public static LicenseDlgFragment newInstance()
    {
        return new LicenseDlgFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readLicenseString();
        buildView();
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

    private void buildView()
    {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_license,null);
        ButterKnife.bind(this,view);
        contentView = view;
        license.setText(licenseText);
    }

    @Override
    public Dialog callDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.CustomDialog)
                .setTitle("开源协议")
                .setNegativeButton("确定",null)
                .setView(contentView);

        return builder.create();
    }
}
