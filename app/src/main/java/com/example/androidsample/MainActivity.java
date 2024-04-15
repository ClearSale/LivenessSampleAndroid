package com.example.androidsample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.clear.studio.csliveness.core.CSLivenessConfig;
import com.clear.studio.csliveness.core.CSLivenessConfigColors;
import com.clear.studio.csliveness.view.CSLivenessActivity;
import com.clear.studio.csliveness.core.CSLiveness;
import com.clear.studio.csliveness.core.CSLivenessResult;

import br.clearsale.studio.sampleliveness.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 40;

    ActivityMainBinding binding;
    boolean vocalGuidance = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Preencha as credenciais
        String clientID =  "";
        String clientSecret = "";
        //Opcional
        String identifierId = "";
        //Opcional
        String cpf = "";

        binding.clientId.setText(clientID);
        binding.clientSecret.setText(clientSecret);
        binding.identifierId.setText(identifierId);
        binding.cpf.setText(cpf);

        //Caso deseje customizar as cores, pode adicioná-las abaixo em formato de código hexadecimal (#FF000000)
        binding.primaryColor.setText("");
        binding.secondaryColor.setText("");
        binding.titleColor.setText("");
        binding.paragraphColor.setText("");

        binding.vocalGuidance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                vocalGuidance = b;
            }
        });

        binding.startSdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.response.setText("Response: ");

                //Método para iniciar a nossa classe CSLivenessConfigColors (Opcional) de acordo com as cores preenchidas nos campos do Layout de exemplo
                //Caso alguma cor não seja preenchida, será usado nosso default
                CSLivenessConfigColors colors = getCustomColors();
                if(colors == null)
                    return;

                //Iniciando a classe de configuração de customizações (Opcional)
                CSLivenessConfig config = new CSLivenessConfig(vocalGuidance, colors);

                //Iniciando o SDK
                CSLiveness mCSLiveness = new CSLiveness(binding.clientId.getText().toString(), binding.clientSecret.getText().toString(), binding.identifierId.getText().toString(), binding.cpf.getText().toString(), config);
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

               binding.response.setText(mCSLivenessResult.getResponseMessage());

                Log.d("Result", mCSLivenessResult.getResponseMessage());
                if(mCSLivenessResult.getSessionId() != null)
                    Log.d("Session ID", mCSLivenessResult.getSessionId());
                if(mCSLivenessResult.getImage() != null)
                    Log.d("Image", mCSLivenessResult.getImage());

            } else {
                Log.d("Result","UserCancel");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public CSLivenessConfigColors getCustomColors()
    {
        try {
            CSLivenessConfigColors defaultColors = new CSLivenessConfigColors();
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
        }catch (IllegalArgumentException iae)
        {
            Toast.makeText(this, "Cor inválida", Toast.LENGTH_SHORT).show();
        }

        return null;
    }
}