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
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;
import java.util.Locale;


    public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
        private TextToSpeech tts;
        private Button bt;
        public static TextView text;

        RequestQueue requestQueue;
        String baseUrl = "http://172.20.10.76:8080/api/";  // This is the API base URL (GitHub API)
        String url;  // This will hold the full URL which will include the username entered in the etGitHubUser.


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            bt = (Button) findViewById(R.id.button_ouvir);
            text = (TextView) findViewById(R.id.textView);
            tts = new TextToSpeech(this, this);

           // this.tvRepoList = (TextView) findViewById(R.id.tv_repo_list);  // Link our repository list text output box.
            //this.tvRepoList.setMovementMethod(new ScrollingMovementMethod());  // This makes our text box scrollable, for those big GitHub contributors with lots of repos :)


            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FetchData process = new FetchData();
                    process.execute();
                }
            });
        }

        @Override
        public void onInit(int status) {
            if(status != TextToSpeech.ERROR) {
                tts.setLanguage(Locale.getDefault());
                bt.setEnabled(true);
            }
        }


        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        public void textToSpeech(View view) {
            EditText et = (EditText) findViewById(R.id.editText);
            Log.i("Script", "Máximo: " + TextToSpeech.getMaxSpeechInputLength());
            tts.speak(et.getText().toString(), tts.QUEUE_FLUSH, null);
        }

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

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            switch (requestCode) {
                case 10:
                    if (resultCode == RESULT_OK && data != null) {
                        ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        text.setText(result.get(0));

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

}

