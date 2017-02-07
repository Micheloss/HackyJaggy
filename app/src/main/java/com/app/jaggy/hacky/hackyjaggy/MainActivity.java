package com.app.jaggy.hacky.hackyjaggy;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.socket.client.IO;
import io.socket.client.Socket;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private List<String> palabras_soportadas;
    private SensorManager mSensorManager = null;
    private Sensor mLight = null;
    private String estado = "";
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 5;
    //private static final String IP = "http://192.168.43.243:54372";
    private static final String IP = "http://10.0.1.64:54372";

    private Socket socket;
    private Context ctx;

    // Create the Handler
    private Handler handler = new Handler();

    // Define the code block to be executed
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // Insert custom code here
            Operaciones.wifiMode(ctx, socket);
            // Repeat every 2 seconds
            handler.postDelayed(runnable, 1000);
        }
    };
    private float currentLux = 0;
    private int conteo = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ctx = this;

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        final ImageView top = (ImageView) findViewById(R.id.top);
        final ImageView bot = (ImageView) findViewById(R.id.bot);
        final FloatingActionButton start = (FloatingActionButton) findViewById(R.id.button);
        final TextView txt = (TextView) findViewById(R.id.Texto_empezar);
        final TextView txt_seguimiento = (TextView) findViewById(R.id.texto_seguimiento);
        final ToggleButton tg = (ToggleButton) findViewById(R.id.toggle);
        tg.setChecked(false);

        final TextView txt_lux = (TextView) findViewById(R.id.texto_lux);
        final ToggleButton tg_lux = (ToggleButton) findViewById(R.id.toggle_lux);
        tg_lux.setChecked(false);
        final SensorEventListener seL = this;

        tg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    handler.post(runnable);
                } else {
                    handler.removeCallbacks(runnable);
                }
            }
        });

        tg_lux.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mSensorManager.registerListener((SensorEventListener) ctx, mLight, SensorManager.SENSOR_DELAY_NORMAL);
                } else {
                    mSensorManager.unregisterListener(seL);
                }
            }
        });
        Animation mSlideInTop = AnimationUtils.loadAnimation(this, R.anim.fromtoptobot);
        Animation mSlideOutTop = AnimationUtils.loadAnimation(this, R.anim.frombottotop);

        mSlideInTop.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                top.setVisibility(View.GONE);
                bot.setVisibility(View.GONE);
                start.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.fadein));
                start.setVisibility(View.VISIBLE);
                txt.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.fadein));
                txt.setVisibility(View.VISIBLE);
                txt_seguimiento.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.fadein));
                txt_seguimiento.setVisibility(View.VISIBLE);
                tg.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.fadein));
                tg.setVisibility(View.VISIBLE);
                txt_lux.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.fadein));
                txt_lux.setVisibility(View.VISIBLE);
                tg_lux.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.fadein));
                tg_lux.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        top.startAnimation(mSlideInTop);
        bot.startAnimation(mSlideOutTop);

        setUpWordList();

        IO.Options opts = new IO.Options();
        opts.forceNew = true;
        opts.reconnection = false;

        try {
            socket = IO.socket(IP, opts);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        socket.connect();
        // Start the Runnable immediately


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startVoiceRecognitionActivity();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // mSensorManager.registerListener((SensorEventListener) this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void setUpWordList() {

        palabras_soportadas = new ArrayList<>();

        palabras_soportadas.add("encender");
        palabras_soportadas.add("enciende");

        palabras_soportadas.add("apagar");
        palabras_soportadas.add("apaga");

        palabras_soportadas.add("subir");
        palabras_soportadas.add("sube");

        palabras_soportadas.add("bajar");
        palabras_soportadas.add("baja");

        palabras_soportadas.add("luminosidad");
        palabras_soportadas.add("brillo");

        palabras_soportadas.add("bombilla");

        palabras_soportadas.add("1");
        palabras_soportadas.add("uno");
        palabras_soportadas.add("cocina");

        palabras_soportadas.add("2");
        palabras_soportadas.add("dos");
        palabras_soportadas.add("dormitorio");
        palabras_soportadas.add("dormitorio");

        palabras_soportadas.add("3");
        palabras_soportadas.add("tres");
        palabras_soportadas.add("pasillo");

        palabras_soportadas.add("ayuda");

        palabras_soportadas.add("color");

        palabras_soportadas.add("rojo");
        palabras_soportadas.add("cyan");
        palabras_soportadas.add("verde");
        palabras_soportadas.add("azul");
        palabras_soportadas.add("amarillo");

        palabras_soportadas.add("todas");

        palabras_soportadas.add("fiesta");

    }

    private void startVoiceRecognitionActivity() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES");

        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true);

        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "¿Qué quieres que haga?");

        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    @Override
    //Recogemos los resultados del reconocimiento de voz
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Si el reconocimiento a sido bueno
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            //El intent nos envia un ArrayList aunque en este caso solo
            //utilizaremos la pos.0
            ArrayList<String> matches = data.getStringArrayListExtra
                    (RecognizerIntent.EXTRA_RESULTS);
            //Separo el texto en palabras.
            String[] palabras = matches.get(0).toString().split(" ");

            List<String> useThis = new ArrayList<>();

            for (int i = 0; i < palabras.length; i++) {
                if (palabras_soportadas.contains(palabras[i])) {
                    useThis.add(palabras[i].toLowerCase());
                }
            }
            //Si la primera palabra es encender
            if (useThis.contains("encender") || Arrays.asList(palabras).contains("enciende")) {
                if (useThis.contains("todas")) {
                    Operaciones.encenderTodas(socket, this);
                } else {
                    Operaciones.encenderBombilla(useThis, socket, this);
                }
                Toast.makeText(this, "Enviado!", Toast.LENGTH_LONG).show();

            } else {
                if (useThis.contains("apagar") || Arrays.asList(palabras).contains("apaga")) {

                    if (useThis.contains("todas")) {
                        Operaciones.apagarTodas(socket, this);
                    } else {
                        Operaciones.apagarBombilla(useThis, socket, this);
                    }

                    Toast.makeText(this, "Enviado!", Toast.LENGTH_LONG).show();

                } else {
                    if (useThis.contains("brillo") || Arrays.asList(palabras).contains("luminosidad")) {

                        if (useThis.contains("subir") || Arrays.asList(palabras).contains("sube")) {

                            Operaciones.subirBrillo(useThis, socket, this);

                        } else {

                            Operaciones.bajarBrillo(useThis, socket, this);

                        }


                        Toast.makeText(this, "Enviado!", Toast.LENGTH_LONG).show();

                    } else {

                        if (useThis.contains("ayuda") || Arrays.asList(palabras).contains("sos")) {

                            try {
                                Operaciones.enviarAyuda(socket, this);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(this, "Enviado!", Toast.LENGTH_LONG).show();

                        } else {

                            if (useThis.contains("color") || Arrays.asList(palabras).contains("cambiar")) {


                                Operaciones.cambiarColor(useThis, socket, this);

                                Toast.makeText(this, "Enviado!", Toast.LENGTH_LONG).show();

                            } else {

                                if (useThis.contains("fiesta")) {

                                    Operaciones.fiesta(socket, this);

                                    Toast.makeText(this, "Enviado!", Toast.LENGTH_LONG).show();

                                }
                            }
                        }
                    }
                }
            }
        }
    }



    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            currentLux = currentLux + event.values[0];
            conteo++;
            if (conteo > 20) {
                conteo = 0;
                currentLux = currentLux / 20;
                Log.d("Lux", "" + currentLux);
                Operaciones.valorLux(currentLux, socket, this);
                currentLux = 0;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}


