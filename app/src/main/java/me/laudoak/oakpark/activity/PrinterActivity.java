package me.laudoak.oakpark.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.analytics.MobclickAgent;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import me.laudoak.oakpark.R;
import me.laudoak.oakpark.entity.Poet;
import me.laudoak.oakpark.entity.Verse;
import me.laudoak.oakpark.entity.XVerse;
import me.laudoak.oakpark.fragment.FontPickerFragment;
import me.laudoak.oakpark.net.UserProxy;
import me.laudoak.oakpark.utils.FileUtil;
import me.laudoak.oakpark.utils.font.FontsManager;
import me.laudoak.oakpark.view.PrinterPanelView;
import me.laudoak.oakpark.widget.damp.DampEditor;
import me.laudoak.oakpark.widget.fittext.AutofitTextView;
import me.laudoak.oakpark.widget.message.AppMsg;

/**
 * Created by LaudOak on 2015-11-6 at 22:46.
 */
public class PrinterActivity extends XBaseActivity implements
        PrinterPanelView.CallBack, FontPickerFragment.CallBack{

    private static final String TAG = "PrinterActivity";

    public static final String EXTRA_VERSE_CONT = "extra data content";
    public static final String EXTRA_VERSE_URI_STR = "extra data uri string";

    private static final String TAG_DLG_FONT = "dialog of fontpicker fragment";

    private FragmentManager fm;

    private DampEditor dampEditor;
    private SimpleDraweeView draweeView;
    private AutofitTextView title,author,verse;
    private TextView nick;
    private PrinterPanelView pinterPanel;


    public enum VerseTag {

        XVERSE, VERSE;

        private static final String name = VerseTag.class.getName();

        public void attachTo(Intent intent)
        {
            intent.putExtra(name, ordinal());
        }

        public static VerseTag detachFrom(Intent intent)
        {
            if(!intent.hasExtra(name)) throw new IllegalStateException();

            return values()[intent.getIntExtra(name, -1)];
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void setView() {
        setContentView(R.layout.view_editor_printer);
        fm = getSupportFragmentManager();
        findViews();
        getExtraData();
    }


    /**
     * Sender usage
     * AwesomeEnum.Something.attachTo(intent);
     * Receiver usage
     * AwesomeEnum result = AwesomeEnum.detachFrom(intent);
     * */
    private void getExtraData() {

        Intent intent = getIntent();

        VerseTag tag = VerseTag.detachFrom(intent);
        switch (tag)
        {
            case XVERSE:
            {
                XVerse xVerse = (XVerse) intent.getSerializableExtra(EXTRA_VERSE_CONT);
                title.setText(xVerse.getTitle());
                author.setText(xVerse.getAuthor());
                verse.setText(xVerse.getVerse());

                break;
            }
            case VERSE:
            {
                Verse vrse = (Verse) intent.getSerializableExtra(EXTRA_VERSE_CONT);
                title.setText(vrse.getTitle());
                author.setText(vrse.getAuthor());
                verse.setText(vrse.getVerse());

                break;
            }
        }

        String uriStr = intent.getStringExtra(EXTRA_VERSE_URI_STR);
        if (null != uriStr)
        {
            Uri uri = Uri.parse(uriStr);
            draweeView.setAspectRatio(1.67f);
            draweeView.setImageURI(uri);
        }

    }

    @Override
    public void buildView()
    {
        buildBar();
        Poet poet = UserProxy.currentPoet(this);
        if (null != poet)
        {
            nick.setText(poet.getUsername());
        }
    }

    private void findViews()
    {
        dampEditor = (DampEditor) findViewById(R.id.printer_damp);
        draweeView = (SimpleDraweeView) findViewById(R.id.printer_image);
        title = (AutofitTextView) findViewById(R.id.printer_title);
        author = (AutofitTextView) findViewById(R.id.printer_author);
        verse = (AutofitTextView) findViewById(R.id.printer_verse);
        pinterPanel = (PrinterPanelView) findViewById(R.id.printer_panel);
        nick = (TextView) findViewById(R.id.printer_nick);
        setListener();
    }

    private void setListener() {
        pinterPanel.setCallBack(this);
    }

    private void buildBar() {
        getSupportActionBar().hide();

        ImageView acBack = (ImageView) findViewById(R.id.ca_normal_back);
        acBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrinterActivity.this.finish();
            }
        });
        TextView acDone = (TextView) findViewById(R.id.ca_normal_done);
        acDone.setVisibility(View.GONE);
    }

    /**PrinterPanelView CallBack*/
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.printer_panel_font:
            {
                showFontDlg();
                break;
            }
            case R.id.printer_panel_save:
            {
                doSave();
                break;
            }
            case R.id.printer_panel_share:
            {
                doShare();
                break;
            }
        }
    }

    private void doShare()
    {
        hideCursor();
        String filname = FileUtil.saveImageToExternalStorage(this,dampEditor.getThisBitmap());
        showCursor();

        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();

        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("分享来自橡树园的一首诗");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(title.getText().toString()+"\n"+author.getText().toString()+"\n"+verse.getText().toString());

        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(filname);//确保SDcard下面存在此张图片

        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        oks.setUrl(null);

        // 启动分享GUI
        oks.show(this);
    }

    private void doSave()
    {
        hideCursor();
        String fileName = FileUtil.saveImageToExternalStorage(this,dampEditor.getThisBitmap());
        AppMsg.makeText(this, "已保存到"+fileName ,AppMsg.STYLE_INFO).show();
        showCursor();
    }


    private void showFontDlg()
    {
        FontPickerFragment fragment = FontPickerFragment.newInstance();
        if (null != fm)
        {
            fragment.show(fm,TAG_DLG_FONT);
        }
    }

    private void hideCursor()
    {
        title.setCursorVisible(false);
        author.setCursorVisible(false);
        verse.setCursorVisible(false);
    }
    private void showCursor()
    {
        title.setCursorVisible(true);
        author.setCursorVisible(true);
        verse.setCursorVisible(true);
    }

    /**FontPickerFragment CallBack*/
    @Override
    public void onSelected(Typeface typeface) {

        FontsManager.init(typeface);
        FontsManager.changeFonts(dampEditor);
    }
}
