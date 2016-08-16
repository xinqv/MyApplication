package com.example.administrator.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private Button btn_insert;
    private Button btn_query;
    private Button btn_update;
    private Button btn_delete;
    private SQLiteDatabase db;
    private MyDBOpenHelper myDBHelper;
    private StringBuilder sb;
    private int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //aaa
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
//        myDBHelper = new MyDBOpenHelper(mContext, "my.db", null, 1);
        myDBHelper =  MyDBOpenHelper.newInstance(this);

        bindViews();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

        onTestBaidu2Click();
     }

    private void onTestBaidu2Click() {
//        RequestParams params = new RequestParams("http://news-at.zhihu.com/api/4/news/latest");
////        params.setSslSocketFactory(...); // 设置ssl
////        params.addQueryStringParameter("wd", "xUtils");
//
//        x.http().get(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//
//                DailyBean dd = JSON.parseObject(result,DailyBean.class);
//
//
//                Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//
//        });

//        RequestParams params = new RequestParams("http://192.168.0.118:8080/exj/removte");
        RequestParams params = new RequestParams("http://192.168.0.118:8080/springtest/userAPI/getAllUser.json");
//        params.addBodyParameter("e","daf");//普通post
//        params.setAsJsonContent(true);//json
//        params.setMultipart(true);//表单

        //表单post
        params.addBodyParameter("token","1");
        params.addBodyParameter("key","2");
        params.addBodyParameter("body","3");

        //json post
//        RequestParams params = new RequestParams("http://192.168.0.118:8080/springtest/userAPI/getUser.json");
//
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("token",1);
//            jsonObject.put("key",2);
//            jsonObject.put("body",3);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        params.setAsJsonContent(true);
//        params.setBodyContent(jsonObject.toString());

        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });


//        ImageView imageView = (ImageView) findViewById(R.id.img);
//        x.image().bind(imageView, "http://2.im.guokr.com/m3APF2liqGiifg3IcCL70jZ_vHQv7kYLSdOxOeDq-ytKAQAA6wAAAFBO.png", imageOptions);


    }

    private void bindViews() {
        btn_insert = (Button) findViewById(R.id.btn_insert);
        btn_query = (Button) findViewById(R.id.btn_query);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_delete = (Button) findViewById(R.id.btn_delete);

        btn_query.setOnClickListener(this);
        btn_insert.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        db = myDBHelper.getWritableDatabase();
        switch (v.getId()) {
            case R.id.btn_insert:
                ContentValues values1 = new ContentValues();
                values1.put("name", "呵呵~" + i);
                i++;
                //参数依次是：表名，强行插入null值得数据列的列名，一行记录的数据

                db.insert("person", null, values1);

                Toast.makeText(mContext, "插入完毕~", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_query:
                sb = new StringBuilder();
                //参数依次是:表名，列名，where约束条件，where中占位符提供具体的值，指定group by的列，进一步约束
                //指定查询结果的排序方式
                Cursor cursor = db.query("person", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        int pid = cursor.getInt(cursor.getColumnIndex("personid"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        sb.append("id：" + pid + "：" + name + "\n");
                    } while (cursor.moveToNext());


                }
                cursor.close();
                Toast.makeText(mContext, sb.toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_update:
                ContentValues values2 = new ContentValues();
                values2.put("name", "嘻嘻~");
                //参数依次是表名，修改后的值，where条件，以及约束，如果不指定三四两个参数，会更改所有行
                db.update("person", values2, "name = ?", new String[]{"呵呵~2"});

                break;
            case R.id.btn_delete:
                //参数依次是表名，以及where条件与约束
//                db.delete("person", "personid = ?", new String[]{"3"});
                db.execSQL("delete from person where 1=1");
                break;
        }
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
