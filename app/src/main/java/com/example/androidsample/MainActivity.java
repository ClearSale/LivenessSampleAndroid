package com.example.androidsample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.clear.studio.csliveness.auth.view.CSLivenessActivity;
import com.clear.studio.csliveness.core.CSLiveness;
import com.clear.studio.csliveness.core.CSLivenessResult;

import br.clearsale.studio.sampleliveness.R;

public class MainActivity extends AppCompatActivity {

    private CSLiveness mCSLiveness;
    private Button mBtnStartSKD;
    private TextView mTxtViewResult;
    private static final int REQUEST_CODE = 40;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnStartSKD = (Button) findViewById(R.id.button);
        mTxtViewResult = (TextView) findViewById(R.id.textView);

        //Coloque o clientId e clientSecret
        mCSLiveness = new CSLiveness("", "");
        mBtnStartSKD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getApplicationContext(), CSLivenessActivity.class);
                mIntent.putExtra(CSLiveness.PARAMETER_NAME, mCSLiveness);
                startActivityForResult(mIntent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE){
            if (resultCode == RESULT_OK && data != null) {
                CSLivenessResult mCSLivenessResult = (CSLivenessResult) data.getSerializableExtra(CSLiveness.PARAMETER_NAME);
                mTxtViewResult.setText(mCSLivenessResult.getResponseMessage());

                Log.d("Result", mCSLivenessResult.getResponseMessage());
                Log.d("Session ID", mCSLivenessResult.getSessionId());
                Log.d("Image", mCSLivenessResult.getImage());

            } else {
                Log.d("Result","UserCancel");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}