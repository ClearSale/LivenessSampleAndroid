package com.clear.liveness;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.clear.liveness.databinding.ActivityMainBinding;
import com.clear.studio.csliveness.core.CSLiveness;
import com.clear.studio.csliveness.core.CSLivenessConfig;
import com.clear.studio.csliveness.core.CSLivenessConfigColors;
import com.clear.studio.csliveness.core.CSLivenessResult;
import com.clear.studio.csliveness.view.CSLivenessActivity;


public class MainActivity extends AppCompatActivity {
    private final  int REQUEST_CODE = 40;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        binding.startSdk.setOnClickListener(v -> start());
        binding.clientId.setText("");
        binding.clientSecret.setText("");

        setContentView(binding.getRoot());
    }

    void start (){
        binding.response.setText("");

        String clientID = binding.clientId.getText().toString();
        String clientSecret = binding.clientSecret.getText().toString();
        String identifierId = binding.identifierId.getText().toString();
        String cpf = binding.cpf.getText().toString();

        if (clientID.isEmpty() || clientSecret.isEmpty()) {
            Toast.makeText(this, getString(R.string.fill_all_blanks), Toast.LENGTH_SHORT).show();
            return;
        }

        CSLivenessConfigColors colors = getCustomColors();
        CSLivenessConfig config = new CSLivenessConfig(binding.vocalGuidance.isChecked(), colors);
        CSLiveness mCSLiveness = new CSLiveness(clientID, clientSecret, identifierId, cpf, config);

        Intent mIntent = new Intent(this, CSLivenessActivity.class);
        mIntent.putExtra(CSLiveness.PARAMETER_NAME, mCSLiveness);
        startActivityForResult(mIntent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE) {
            binding.response.setText(getResponseMessage(resultCode, data));
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getResponseMessage(int resultCode, @NonNull Intent data) {
        if (resultCode != RESULT_OK || data == null) {
            return "UserCancel";
        }

        CSLivenessResult mCSLivenessResult = (CSLivenessResult) data.getSerializableExtra(CSLiveness.PARAMETER_NAME);
        if (mCSLivenessResult.getImage() != null) {
            byte[] imageBytes;
            //decode base64 string to image
            imageBytes = Base64.decode(mCSLivenessResult.getImage(), Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            binding.imgVwAuditImage.setImageBitmap(decodedImage);
            binding.imgVwAuditImage.setVisibility(View.VISIBLE);
        }

        return mCSLivenessResult.getResponseMessage();
    }

    public CSLivenessConfigColors getCustomColors() {
        CSLivenessConfigColors defaultColors = new CSLivenessConfigColors();

        try {
            String primaryText = binding.primaryColor.getText().toString();
            int primary = defaultColors.getPrimaryColor();
            if (!primaryText.isEmpty())
                primary = Color.parseColor(primaryText);

            String secondaryText = binding.secondaryColor.getText().toString();
            int secondary = defaultColors.getSecondaryColor();
            if (!secondaryText.isEmpty())
                secondary = Color.parseColor(secondaryText);

            String titleText = binding.titleColor.getText().toString();
            int title = defaultColors.getTitleColor();
            if (!titleText.isEmpty())
                title = Color.parseColor(titleText);

            String paragraphText = binding.paragraphColor.getText().toString();
            int paragraph = defaultColors.getParagraphColor();
            if (!paragraphText.isEmpty())
                paragraph = Color.parseColor(paragraphText);

            return new CSLivenessConfigColors(primary, secondary, title, paragraph);
        } catch (IllegalArgumentException iae) {
            Toast.makeText(this, "Cor inválida", Toast.LENGTH_SHORT).show();
        }

        return defaultColors;
    }
}