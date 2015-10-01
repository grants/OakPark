package me.laudoak.oakpark.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.activity.EditorActivity;
import me.laudoak.oakpark.view.NormalEditorView;
import me.laudoak.oakpark.widget.panel.XBasePanelView;
import me.nereo.multi_image_selector.CropperActivity;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by LaudOak on 2015-9-29.
 */
public class NormalEditorFragment extends XBaseFragment implements EditorActivity.PushCallback ,XBasePanelView.OnPanelClickListener{

    private NormalEditorView normalEditorView;
    private NormalEditorView.Holder holder;
    private String imagePath;

    /*XBaseFragment callback*/
    @Override
    public void initData() {
        normalEditorView = new NormalEditorView(getActivity());
        this.holder = normalEditorView.getHolder();
        imagePath = null;
    }

    /*XBaseFragment callback*/
    @Override
    public View callView(LayoutInflater inflater, ViewGroup container) {
        return normalEditorView;
        //#2015-10-1 holy bug
        //return inflater.inflate(R.layout.fragment_editor_normal,null);
    }

    /*XBaseFragment callback*/
    @Override
    public void buildViews(View view) {
        buildActivePanelView();
    }

    private void buildActivePanelView() {
        holder.panel.setListener(this);
        holder.panel.getCalendar().setVisibility(View.GONE);
    }

    /*EditorActivity callback*/
    @Override
    public void onPush() {

    }

    /*XPanelView callback*/
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.preview:
            {
                break;
            }

            case R.id.edit_panel_camera:
            {
                Intent intent = new Intent();
                intent.setClass(getActivity(), MultiImageSelectorActivity.class);
                intent.putExtra(CropperActivity.EXTRA_CROP_MODE, CropperActivity.CROP_MODE_NORMAL);
                startActivity(intent);
                break;
            }
        }
    }

    /*Get bitmap path*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
