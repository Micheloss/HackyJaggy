package com.app.jaggy.hacky.hackyjaggy;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.socket.client.Socket;

/**
 * Created by madiazb on 04/02/2017.
 */

public final class Operaciones {


    public Operaciones() {
    }

    public static void wifiMode(Context ctx, Socket socket) {
        WifiManager wifiManager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        int numberOfLevels = 10;
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int level = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), numberOfLevels);
        JSONObject obj = new JSONObject();
        try {
            obj.put("level", String.valueOf(level));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("wifiLevel", obj);
    }

    public static void encenderBombilla(List<String> palabras, Socket socket, Context ctx) {
        JSONObject obj = new JSONObject();
        if (palabras.contains("1") || palabras.contains("uno") || palabras.contains("cocina")) {
            Toast.makeText(ctx, "Enciende 1", Toast.LENGTH_LONG).show();

            try {
                obj.put("bombilla", "1");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            socket.emit("Encender", obj);

        } else {
            if (palabras.contains("2") || palabras.contains("dos") || palabras.contains("dormitorio") || palabras.contains("dormitorio")) {

                try {
                    obj.put("bombilla", "2");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                socket.emit("Encender", obj);

            } else {
                if (palabras.contains("3") || palabras.contains("tres") || palabras.contains("pasillo")) {

                    try {
                        obj.put("bombilla", "3");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    socket.emit("Encender", obj);
                } else {
                    Toast.makeText(ctx, "No he reconocido el número", Toast.LENGTH_LONG);
                }

            }
        }


    }

    public static void apagarBombilla(List<String> palabras, Socket socket, Context ctx) {
        JSONObject obj = new JSONObject();
        if (palabras.contains("1") || palabras.contains("uno") || palabras.contains("cocina")) {
            Toast.makeText(ctx, "Apagar 1", Toast.LENGTH_LONG).show();

            try {
                obj.put("bombilla", "1");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            socket.emit("Apagar", obj);

        } else {
            if (palabras.contains("2") || palabras.contains("dos") || palabras.contains("dormitorio") || palabras.contains("dormitorio")) {

                try {
                    obj.put("bombilla", "2");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                socket.emit("Apagar", obj);

            } else {
                if (palabras.contains("3") || palabras.contains("tres") || palabras.contains("pasillo")) {

                    try {
                        obj.put("bombilla", "3");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    socket.emit("Apagar", obj);
                } else {
                    Toast.makeText(ctx, "No he reconocido el número", Toast.LENGTH_LONG);
                }

            }
        }


    }


    public static void subirBrillo(List<String> palabras, Socket socket, Context ctx) {
        JSONObject obj = new JSONObject();
        if (palabras.contains("1") || palabras.contains("uno") || palabras.contains("cocina")) {
            Toast.makeText(ctx, "Apagar 1", Toast.LENGTH_LONG).show();

            try {
                obj.put("bombilla", "1");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            socket.emit("brilloSubir", obj);

        } else {
            if (palabras.contains("2") || palabras.contains("dos") || palabras.contains("dormitorio")) {

                try {
                    obj.put("bombilla", "2");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                socket.emit("brilloSubir", obj);

            } else {
                if (palabras.contains("3") || palabras.contains("tres") || palabras.contains("pasillo")) {

                    try {
                        obj.put("bombilla", "3");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    socket.emit("brilloSubir", obj);
                } else {
                    Toast.makeText(ctx, "No he reconocido el número", Toast.LENGTH_LONG);
                }

            }
        }

    }

    public static void bajarBrillo(List<String> palabras, Socket socket, Context ctx) {
        JSONObject obj = new JSONObject();
        if (palabras.contains("1") || palabras.contains("uno") || palabras.contains("cocina")) {

            try {
                obj.put("bombilla", "1");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            socket.emit("brilloBajar", obj);

        } else {
            if (palabras.contains("2") || palabras.contains("dos") || palabras.contains("dormitorio")) {

                try {
                    obj.put("bombilla", "2");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                socket.emit("brilloBajar", obj);

            } else {
                if (palabras.contains("3") || palabras.contains("tres") || palabras.contains("pasillo")) {

                    try {
                        obj.put("bombilla", "3");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    socket.emit("brilloBajar", obj);
                } else {
                    Toast.makeText(ctx, "No he reconocido el número", Toast.LENGTH_LONG);
                }

            }
        }

    }

    public static void enviarAyuda(Socket socket, Context context) throws InterruptedException {
        JSONObject obj = new JSONObject();
        try {
            obj.put("bombilla", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject obj2 = new JSONObject();
        try {
            obj2.put("bombilla", "2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject obj3 = new JSONObject();
        try {
            obj3.put("bombilla", "3");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("Apagar", obj);
        socket.emit("Apagar", obj2);
        socket.emit("Apagar", obj3);

        Thread.sleep(3000);

        socket.emit("Encender", obj);
        socket.emit("Encender", obj2);
        socket.emit("Encender", obj3);

        Thread.sleep(1000);

        socket.emit("Apagar", obj);
        socket.emit("Apagar", obj2);
        socket.emit("Apagar", obj3);

        Thread.sleep(2000);

        socket.emit("Encender", obj);
        socket.emit("Encender", obj2);
        socket.emit("Encender", obj3);

        Thread.sleep(1000);

        socket.emit("Apagar", obj);
        socket.emit("Apagar", obj2);
        socket.emit("Apagar", obj3);

        Thread.sleep(2000);


        socket.emit("Encender", obj);
        socket.emit("Encender", obj2);
        socket.emit("Encender", obj3);

        Thread.sleep(1000);

        socket.emit("Apagar", obj);
        socket.emit("Apagar", obj2);
        socket.emit("Apagar", obj3);

        Thread.sleep(1000);
//Largos
        socket.emit("Encender", obj);
        socket.emit("Encender", obj2);
        socket.emit("Encender", obj3);

        Thread.sleep(2500);

        socket.emit("Apagar", obj);
        socket.emit("Apagar", obj2);
        socket.emit("Apagar", obj3);

        Thread.sleep(2000);

        socket.emit("Encender", obj);
        socket.emit("Encender", obj2);
        socket.emit("Encender", obj3);

        Thread.sleep(2500);

        socket.emit("Apagar", obj);
        socket.emit("Apagar", obj2);
        socket.emit("Apagar", obj3);

        Thread.sleep(2000);


        socket.emit("Encender", obj);
        socket.emit("Encender", obj2);
        socket.emit("Encender", obj3);

        Thread.sleep(2500);

        socket.emit("Apagar", obj);
        socket.emit("Apagar", obj2);
        socket.emit("Apagar", obj3);


        //cortos
        socket.emit("Encender", obj);
        socket.emit("Encender", obj2);
        socket.emit("Encender", obj3);

        Thread.sleep(1000);

        socket.emit("Apagar", obj);
        socket.emit("Apagar", obj2);
        socket.emit("Apagar", obj3);

        Thread.sleep(2000);

        socket.emit("Encender", obj);
        socket.emit("Encender", obj2);
        socket.emit("Encender", obj3);

        Thread.sleep(1000);

        socket.emit("Apagar", obj);
        socket.emit("Apagar", obj2);
        socket.emit("Apagar", obj3);

        Thread.sleep(2000);


        socket.emit("Encender", obj);
        socket.emit("Encender", obj2);
        socket.emit("Encender", obj3);

        Thread.sleep(1000);

        socket.emit("Apagar", obj);
        socket.emit("Apagar", obj2);
        socket.emit("Apagar", obj3);

        Thread.sleep(3000);

        socket.emit("Encender", obj);
        socket.emit("Encender", obj2);
        socket.emit("Encender", obj3);

    }

    public static void cambiarColor(List<String> palabras, Socket socket, Context ctx) {

        String color = averiguarColor(palabras);

        JSONObject obj = new JSONObject();
        if (palabras.contains("1") || palabras.contains("uno") || palabras.contains("cocina")) {


            try {
                obj.put("bombilla", "1");
                obj.put("color", color);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            socket.emit("color", obj);

        } else {
            if (palabras.contains("2") || palabras.contains("dos") || palabras.contains("dormitorio")) {

                try {
                    obj.put("bombilla", "2");
                    obj.put("color", color);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                socket.emit("color", obj);

            } else {
                if (palabras.contains("3") || palabras.contains("tres") || palabras.contains("pasillo")) {

                    try {
                        obj.put("bombilla", "3");
                        obj.put("color", color);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    socket.emit("color", obj);
                } else {
                    Toast.makeText(ctx, "No he reconocido el número", Toast.LENGTH_LONG);
                }

            }
        }


    }

    private static String averiguarColor(List<String> palabras) {

        if (palabras.contains("rojo")) {
            return "rojo";
        }
        if (palabras.contains("cyan") || palabras.contains("cian")) {
            return "cyan";
        }
        if (palabras.contains("verde")) {
            return "verde";
        }
        if (palabras.contains("amarillo")) {
            return "amarillo";
        }
        if (palabras.contains("azul")) {
            return "azul";
        }
        return "blanco";

    }

    public static void apagarTodas(Socket socket, MainActivity mainActivity) {

        JSONObject obj = new JSONObject();
        try {
            obj.put("bombilla", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject obj2 = new JSONObject();
        try {
            obj2.put("bombilla", "2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject obj3 = new JSONObject();
        try {
            obj3.put("bombilla", "3");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("Apagar", obj);
        socket.emit("Apagar", obj2);
        socket.emit("Apagar", obj3);

    }

    public static void encenderTodas(Socket socket, MainActivity mainActivity) {

        JSONObject obj = new JSONObject();
        try {
            obj.put("bombilla", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject obj2 = new JSONObject();
        try {
            obj2.put("bombilla", "2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject obj3 = new JSONObject();
        try {
            obj3.put("bombilla", "3");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("Encender", obj);
        socket.emit("Encender", obj2);
        socket.emit("Encender", obj3);

    }

    public static void subirBrillo(Socket socket, MainActivity mainActivity) {

        JSONObject obj = new JSONObject();
        try {
            obj.put("bombilla", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject obj2 = new JSONObject();
        try {
            obj2.put("bombilla", "2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject obj3 = new JSONObject();
        try {
            obj3.put("bombilla", "3");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("subirBrillo", obj);
        socket.emit("subirBrillo", obj2);
        socket.emit("subirBrillo", obj3);
    }

    public static void bajarBrillo(Socket socket, MainActivity mainActivity) {

        JSONObject obj = new JSONObject();
        try {
            obj.put("bombilla", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject obj2 = new JSONObject();
        try {
            obj2.put("bombilla", "2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject obj3 = new JSONObject();
        try {
            obj3.put("bombilla", "3");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("bajarBrillo", obj);
        socket.emit("bajarBrillo", obj2);
        socket.emit("bajarBrillo", obj3);
    }

    public static void valorLux(float currentLux, Socket socket, MainActivity mainActivity) {

        JSONObject obj2 = new JSONObject();
        try {
            obj2.put("lux", ""+currentLux);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        socket.emit("valorLux", obj2);

    }

    public static void fiesta(Socket socket, MainActivity mainActivity) {

        socket.emit("Fiesta", "hola");
    }
}
