package me.laudoak.oakpark.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.regex.Pattern;

import me.laudoak.oakpark.R;

/**
 * Created by LaudOak on 2015-9-27.
 */
public class LoginView extends LinearLayout implements TextWatcher , View.OnClickListener{

    private EditText email,password;
    private Button login;

    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+"
    );

    public LoginView(Context context) {
        super(context);

        init();
    }

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public LoginView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init()
    {
        inflate(getContext(), R.layout.view_login,this);
        email = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);
        login = (Button) findViewById(R.id.login_login);

        login.setOnClickListener(this);

        email.addTextChangedListener(this);
        password.addTextChangedListener(this);

        login.setEnabled(false);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if(EMAIL_ADDRESS_PATTERN.matcher(email.getText().toString()).matches()
                &&password.getText().toString().length()>=6)
        {
            login.setEnabled(true);
        }else
        {
            login.setEnabled(false);
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {

    }
}
