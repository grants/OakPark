package me.laudoak.oakpark.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.entity.Verse;
import me.laudoak.oakpark.entity.XVerse;
import me.laudoak.oakpark.fragment.FontPickerFragment;
import me.laudoak.oakpark.utils.font.FontsManager;
import me.laudoak.oakpark.view.PrinterPanelView;
import me.laudoak.oakpark.widget.damp.DampEditor;
import me.laudoak.oakpark.widget.fittext.AutofitTextView;

/**
 * Created by LaudOak on 2015-11-6 at 22:46.
 */
public class PrinterActivity extends XBaseActivity implements
        PrinterPanelView.CallBack, FontPickerFragment.CallBack{

    private static final String TAG = "PrinterActivity";

    public static final String EXTRA_VERSE_CONT = "extra data content";
    public static final String EXTRA_VERSE_URI_STR = "extra data uri string";

    private DampEditor dampEditor;
    private SimpleDraweeView draweeView;
    private AutofitTextView title,author,verse;
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
    protected void setView() {
        setContentView(R.layout.view_editor_printer);
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
    }

    private void findViews()
    {
        dampEditor = (DampEditor) findViewById(R.id.printer_damp);
        draweeView = (SimpleDraweeView) findViewById(R.id.printer_image);
        title = (AutofitTextView) findViewById(R.id.printer_title);
        author = (AutofitTextView) findViewById(R.id.printer_author);
        verse = (AutofitTextView) findViewById(R.id.printer_verse);
        pinterPanel = (PrinterPanelView) findViewById(R.id.printer_panel);
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

                break;
            }
            case R.id.printer_panel_save:
            {
                break;
            }
            case R.id.printer_panel_share:
            {
                break;
            }
        }
    }

    /**FontPickerFragment CallBack*/
    @Override
    public void onSelected(Typeface typeface) {
        FontsManager.init(typeface);
        FontsManager.changeFonts(dampEditor);

    }
}
