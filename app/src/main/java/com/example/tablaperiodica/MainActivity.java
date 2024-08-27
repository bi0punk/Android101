package com.example.tablaperiodica;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import okhttp3.*;
import org.json.JSONObject;
import android.text.Html;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText numberInput;
    private TextView resultText;
    private Button submitButton;
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberInput = findViewById(R.id.numberInput);
        resultText = findViewById(R.id.resultText);
        submitButton = findViewById(R.id.submitButton);
        client = new OkHttpClient();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = numberInput.getText().toString();
                if (!number.isEmpty()) {
                    sendRequest(number);
                } else {
                    resultText.setText("Por favor, ingresa un número.");
                }
            }
        });
    }

    private void sendRequest(String number) {
        String url = "http://192.168.1.93:8000/elementos/" + number;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultText.setText("Error al realizar la solicitud.");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    System.out.println(myResponse);

                    String formattedResponse;
                    try {
                        JSONObject jsonObject = new JSONObject(myResponse);

                        // Usar formato HTML para negrita
                        StringBuilder sb = new StringBuilder();
                        sb.append("<b>Nombre:</b> ").append(jsonObject.optString("name")).append("<br>");
                        sb.append("<b>Símbolo:</b> ").append(jsonObject.optString("symbol")).append("<br>");
                        sb.append("<b>Número atómico:</b> ").append(jsonObject.optString("atomic_number")).append("<br>");
                        sb.append("<b>Masa:</b> ").append(jsonObject.optString("mass")).append("<br>");
                        sb.append("<b>Masa exacta:</b> ").append(jsonObject.optString("exact_mass")).append("<br>");
                        sb.append("<b>Ionización:</b> ").append(jsonObject.optString("ionization")).append("<br>");
                        sb.append("<b>Afinidad electrónica:</b> ").append(jsonObject.optString("electron_affinity")).append("<br>");
                        sb.append("<b>Electronegatividad:</b> ").append(jsonObject.optString("electronegativity")).append("<br>");
                        sb.append("<b>Radio covalente:</b> ").append(jsonObject.optString("covalent_radius")).append("<br>");
                        sb.append("<b>Radio de Van der Waals:</b> ").append(jsonObject.optString("van_der_waals_radius")).append("<br>");
                        sb.append("<b>Punto de fusión:</b> ").append(jsonObject.optString("melting_point")).append("<br>");
                        sb.append("<b>Punto de ebullición:</b> ").append(jsonObject.optString("boiling_point")).append("<br>");
                        sb.append("<b>Familia:</b> ").append(jsonObject.optString("family")).append("<br>");

                        formattedResponse = sb.toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                        formattedResponse = "Error al procesar la respuesta JSON.";
                    }

                    // Actualizar la interfaz de usuario
                    final String finalFormattedResponse = formattedResponse;
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            resultText.setText(Html.fromHtml(finalFormattedResponse));
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            resultText.setText("Solicitud fallida: " + response.message());
                        }
                    });
                }
            }
        });
    }
}
