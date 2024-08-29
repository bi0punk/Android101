package com.example.tablaperiodica;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import okhttp3.*;
import org.json.JSONObject;
import android.text.Html;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText numberInput;
    private TableLayout resultTable;
    private Button submitButton;
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberInput = findViewById(R.id.numberInput);
        resultTable = findViewById(R.id.resultTable);
        submitButton = findViewById(R.id.submitButton);
        client = new OkHttpClient();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = numberInput.getText().toString();
                if (!number.isEmpty()) {
                    sendRequest(number);
                } else {
                    clearTable();
                    addRowToTable("Error", "Por favor, ingresa un número.");
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
                        clearTable();
                        addRowToTable("Error", "Error al realizar la solicitud.");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {

                    final String myResponse = response.body().string();
                    System.out.println(myResponse);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            clearTable();
                            parseAndDisplayResponse(myResponse);
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            clearTable();
                            addRowToTable("Error", "Solicitud fallida: " + response.message());
                        }
                    });
                }
            }
        });
    }

    private void parseAndDisplayResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            addRowToTable("Nombre", jsonObject.optString("name"));
            addRowToTable("Símbolo", jsonObject.optString("symbol"));
            addRowToTable("Número atómico", jsonObject.optString("atomic_number"));
            addRowToTable("Masa", jsonObject.optString("mass"));
            addRowToTable("Masa exacta", jsonObject.optString("exact_mass"));
            addRowToTable("Ionización", jsonObject.optString("ionization"));
            addRowToTable("Afinidad electrónica", jsonObject.optString("electron_affinity"));
            addRowToTable("Electronegatividad", jsonObject.optString("electronegativity"));
            addRowToTable("Radio covalente", jsonObject.optString("covalent_radius"));
            addRowToTable("Radio de Van der Waals", jsonObject.optString("van_der_waals_radius"));
            addRowToTable("Punto de fusión", jsonObject.optString("melting_point"));
            addRowToTable("Punto de ebullición", jsonObject.optString("boiling_point"));
            addRowToTable("Familia", jsonObject.optString("family"));
        } catch (Exception e) {
            e.printStackTrace();
            addRowToTable("Error", "Error al procesar la respuesta JSON.");
        }
    }

    private void addRowToTable(String property, String value) {
        TableRow row = new TableRow(this);
        TextView propertyTextView = new TextView(this);
        TextView valueTextView = new TextView(this);

        propertyTextView.setText(property);
        propertyTextView.setTextSize(18);
        propertyTextView.setTextColor(getResources().getColor(android.R.color.black));
        propertyTextView.setPadding(5, 5, 5, 5);

        valueTextView.setText(value);
        valueTextView.setTextSize(18);
        valueTextView.setTextColor(getResources().getColor(android.R.color.darker_gray));
        valueTextView.setPadding(5, 5, 5, 5);

        row.addView(propertyTextView);
        row.addView(valueTextView);

        resultTable.addView(row);
    }

    private void clearTable() {
        resultTable.removeAllViews();
    }
}
