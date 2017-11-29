package com.example.spolti.speechrecognition;

import android.content.Intent;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


    public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
        private TextToSpeech tts;
        private Button bt;
        public static TextView text;
        private String id = "id=001D0F-DSL%2520Gateway-18A6F7315BA0" // ID TP-LINK supondo que a aplicação tem conhecimento da ID do usuário

        RequestQueue requestQueue;
        String baseUrl = "http://172.20.10.76:8080/api/";
        String url;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

           // bt = (Button) findViewById(R.id.button_ouvir);
            text = (TextView) findViewById(R.id.textView);
            tts = new TextToSpeech(this, this);
//
//            bt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    FetchData process = new FetchData();
//                    process.execute();
//                }
//            });
        }

        @Override
        public void onInit(int status) {
            if(status != TextToSpeech.ERROR) {
                tts.setLanguage(Locale.getDefault());
                //bt.setEnabled(true);
            }
        }


        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            switch (requestCode) {
                case 10:
                    if (resultCode == RESULT_OK && data != null) {
                        ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        //text.setText(result.get(0));
                        //processSpeech(result.get(0));
                        processSpeech("Qual o meu endereço MAC");
                        processSpeech(result.get(0));

                    }
                    break;
            }


        }

        public void onPause(){
            if(tts != null){
                tts.stop();
                tts.shutdown();
            }
            super.onPause();

        }

        ///////////////////// Speech Recognition //////////////////////////////////////
        //Captura de Voz
        public void getSpeechInput(View view){
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, 10);
            } else {
                Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
            }

        }


        //Texto para voz
//        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
//        public void textToSpeech(View view) {
//            EditText et = (EditText) findViewById(R.id.editText);
//            Log.i("Script", "Máximo: " + TextToSpeech.getMaxSpeechInputLength());
//            tts.speak(et.getText().toString(), tts.QUEUE_FLUSH, null);
//        }
        //////////////////////////////////////////////////////////////////////////////


        ///////////////////// Speech Processing //////////////////////////////////////

        public void processSpeech(String speech){
            String palavras[] = new String[];
            palavras = speech.split(" ");
            List aux = Arrays.asList(palavras);
            ArrayList words = new ArrayList(aux);

            if(words.contains("mac")){

            }

        }

        //////////////////////////////////////////////////////////////////////////////


}

