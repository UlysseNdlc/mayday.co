package com.yoann.loginregister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

  /*  private static final String[] DUMMY_CREDENTIALS = new String[]{
            "kidjo@et.esiea.fr:kidjo", "unedelec@et.esiea.fr:nedelec", "lazolatorre@et.esiea.fr:lazolatorre", "sidoli@et.esiea.fr:sidoli"
    };
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etMail = (EditText) findViewById(R.id.etMail);
        final EditText etMdp = (EditText) findViewById(R.id.etMdp);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final TextView tvRegister = (TextView) findViewById(R.id.tvRegister);

        tvRegister.setOnClickListener(new View.OnClickListener() {

                                          @Override
                                          public void onClick(View v) {
                                              Intent RegisterIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                                              LoginActivity.this.startActivity(RegisterIntent);

                                          }

        }
        );

        bLogin.setOnClickListener(new View.OnClickListener() {

                                          @Override
                                          public void onClick(View v) {
                                              Intent LoginIntent = new Intent(LoginActivity.this, ConnectedActivity.class);
                                              LoginActivity.this.startActivity(LoginIntent);

                                          }

                                      }
        );

    }
}
