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
public class RegisterView extends LinearLayout implements TextWatcher ,View.OnClickListener{

    private EditText email,nick,password,confirmpassword;
    private Button register;

    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+"
    );

    public RegisterView(Context context) {
        super(context);
        init();
    }

    public RegisterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RegisterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        inflate(getContext(),R.layout.view_register,this);
        email = (EditText) findViewById(R.id.register_email);
        nick = (EditText) findViewById(R.id.register_nick);
        password = (EditText) findViewById(R.id.register_password);
        confirmpassword = (EditText) findViewById(R.id.register_confirmpassword);
        register = (Button) findViewById(R.id.register_register);

        register.setOnClickListener(this);

        email.addTextChangedListener(this);
        nick.addTextChangedListener(this);
        password.addTextChangedListener(this);
        confirmpassword.addTextChangedListener(this);

        register.setEnabled(false);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(EMAIL_ADDRESS_PATTERN.matcher(email.getText().toString()).matches()
                &&nick.getText().toString().length()>=3
                &&password.getText().toString().length()>=6
                &&confirmpassword.getText().toString().equals(password.getText().toString()))
        {
            register.setEnabled(true);
        }else
        {
            register.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {

    }
}
