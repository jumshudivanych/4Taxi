package com.sandbox50572.a4taxi;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    WebView webView1;
    WebView webView2;
    Button myButton1;
    TextView opteum_text1;
    Button myButton2;
    TextView taxiClient_text2;
    String order1;
    String order2;
    View opteum_view1;
    private static final String CATEGORY_PLUGIN = "com.example.plugin.PLUGIN_APPLICATION";

    //TODO Метод для получения Context стороннего приложения
    public static Context getPackageContext(MainActivity context, String packageName) {
        try {
            return context.getApplicationContext().createPackageContext(packageName,
                    Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        webView1 = (WebView) findViewById(R.id.webView1);
        // включаем поддержку JavaScript
        webView1.getSettings().setJavaScriptEnabled(true);
        // указываем страницу загрузки
        webView1.loadUrl("http://www.lkks.ru/k/index.php?err5");//TODO Адрес?
        webView1.setWebViewClient(new WebViewClient());

        webView2 = (WebView) findViewById(R.id.webView2);
        // включаем поддержку JavaScript
        webView2.getSettings().setJavaScriptEnabled(true);
        // указываем страницу загрузки
        webView2.loadUrl("http://my.taxi-pilot.ru:8082/my/space");//TODO Такси Пилот 333
        webView2.setWebViewClient(new WebViewClient());

        //TODO OPTEUM TAXI
/*
        PackageManager pm = getApplicationContext().getPackageManager();
        // Фильтруем по ACTION_MAIN и CATEGORY_PLUGIN
        Intent queryIntent = new Intent(Intent.ACTION_MAIN);
        queryIntent.addCategory(CATEGORY_PLUGIN);
        // Получаем все активити, сервисы и ресиверы с заданным критерием отбора
        List<ResolveInfo> infos = pm.queryIntentActivities(queryIntent, 0);
        // Отбираем только активити, взяв сразу информацию о приложении (ApplicationInfo)
        final List<ApplicationInfo> pluginApps = new ArrayList<>();
        for (ResolveInfo resolveInfo : infos) {
            if (resolveInfo.activityInfo != null) {
                pluginApps.add(resolveInfo.activityInfo.applicationInfo);
            }
        }

        //PackageManager pm = getApplicationContext().getPackageManager();
        ApplicationInfo appInfo = pluginApps.get(0);
// Имя пакета
        String packageName = appInfo.packageName;
// Версия SDK
        int targetSdk = appInfo.targetSdkVersion;

//TODO ПОЛУЧЕНИЕ РЕСУРСОВ ПРИЛОЖЕНИЯ
        try {
            Resources res = pm.getResourcesForApplication(appInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //TODO Получение интента для запуска и запуск
        //Intent activityIntent = pm.getLaunchIntentForPackage(appInfo.packageName);
        //startActivity(activityIntent);
*/
        myButton1 = (Button) findViewById(R.id.myButton1);
        opteum_text1 = (TextView) findViewById(R.id.opteum_text1);
        //TODO ДОБАВИТЬ КЛИКАБЕЛЬНОСТЬ
        // инициализируем виджет
        ImageButton imageView1 = (ImageButton) findViewById(R.id.voiti_v_slugbu);
// устанавливаем изображение из папки res/drawable/ в виджет
        imageView1.setImageResource(R.drawable.ic_drawer_taxi);

        ImageButton imageView2 = (ImageButton) findViewById(R.id.taxometr);
// устанавливаем изображение из папки res/drawable/ в виджет
        imageView2.setImageResource(R.drawable.ic_drawer_taximeter);

        // инициализируем виджет
        ImageButton imageView3 = (ImageButton) findViewById(R.id.moi_zakazy);
// устанавливаем изображение из папки res/drawable/ в виджет
        imageView3.setImageResource(R.drawable.ic_drawer_history);
        //TODO Обработка нажатия на кнопку
        myButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO Запуск activity стороннего приложения
                PackageManager pm = getApplicationContext().getPackageManager();
                Intent activityIntent = pm.getLaunchIntentForPackage("com.opteum.opteumTaxi");
                startActivity(activityIntent);

            }
        });
        //TODO  получить ClassLoader, передав в метод нужное имя пакета
        Context pluginContext = getPackageContext(this, "com.opteum.opteumTaxi");
        if (pluginContext == null) return;
        ClassLoader classLoader = pluginContext.getClassLoader();

        //TODO Получение значения полей и вызов функций стороннего приложения
        try {
            Class<?> pluginClass = classLoader.loadClass("com.opteum.opteumTaxi.ActivityStart");
            // Получаем поле по имени
            //Field orderList = pluginClass.getDeclaredField("ACTION_APDATE");
           //TODO !!! opteum_view1 = orderList;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }// catch (NoSuchFieldException e) {
         //   e.printStackTrace();
        //}




        myButton2 = (Button) findViewById(R.id.myButton2);
        taxiClient_text2 = (TextView) findViewById(R.id.taxiClient_text2);
        //TODO Обработка нажатия на кнопку
        myButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager pm = getApplicationContext().getPackageManager();
                Intent activityIntent = pm.getLaunchIntentForPackage("Infocraft.TaxiClientA");
                startActivity(activityIntent);
            }
        });

        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            } else if (type.startsWith("image/")) {
                handleSendImage(intent); // Handle single image being sent
            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleSendMultipleImages(intent); // Handle multiple images being sent
            }
        } else {
            // Handle other intents, such as being started from the home screen
        }

    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            opteum_text1.setText(sharedText);// Update UI to reflect text being shared
        }
    }

    void handleSendImage(Intent intent) {
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            // Update UI to reflect image being shared
        }
    }

    void handleSendMultipleImages(Intent intent) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageUris != null) {
            // Update UI to reflect multiple images being shared
        }
    }


/*
    // Получаем метод по имени и сигнатуре
    // sum(42, 280)
    Method sumMethod = pluginClass.getDeclaredMethod("sum", int.class, int.class);
    int sum = sumMethod.invoke(null, 42, 280);
    // reverse("abcdefgh");
    Method reverseMethod = pluginClass.getDeclaredMethod("reverse", String.class);
    String reverse = reverseMethod.invoke(null, "abcdefgh");
*/
}
