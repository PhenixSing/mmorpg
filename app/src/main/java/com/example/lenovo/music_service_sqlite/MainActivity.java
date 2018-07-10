package com.example.lenovo.music_service_sqlite;


import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

import static cn.bmob.v3.b.From.e;
import static rx.schedulers.Schedulers.test;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    String account,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //AudioManager music = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
        play_music();
        Bmob.initialize(this, "82cd86b179c0069026f419c4ecbb2540");
        mytest();
    }

    private void mytest() {
        User p2 = new User();
        p2.setAccount("lucky");
        p2.setPassword("123456");
        p2.save(new SaveListener<String>() {
            @Override
            public void done(String objectId,BmobException e) {
                if(e==null){
                    Toast.makeText(MainActivity.this,
                            "添加数据成功，返回objectId为："+objectId, Toast.LENGTH_SHORT).show();
                    //toast("添加数据成功，返回objectId为："+objectId);
                }else{
                    //toast("创建数据失败：" + e.getMessage());
                    Toast.makeText(MainActivity.this,
                            "创建数据失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void play_music(){
        mediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.heyige);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    public void button_start(View view) {
        mediaPlayer.stop();
        //Intent intent = new Intent(MainActivity.this,)
        login_logup(view);
    }

    public void login_logup(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(R.string.not_login);
        builder.setPositiveButton(R.string.signup, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Intent intent = new

            }
        });
        builder.setNegativeButton(R.string.login, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                login_view();
            }
        });
        //builder.setCancelable(false);
        builder.create().show();

    }
    public  void login_view(){
        final TableLayout login_view = (TableLayout)
                getLayoutInflater().inflate(R.layout.login,null);
        new AlertDialog.Builder(this)
                .setView(login_view)
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText e = (EditText) login_view.findViewById(R.id.editText_account);
                        if(e == null) Log.i("fm", "NULL");
                        account = e.getText().toString();
                        password = ((EditText) login_view.findViewById(R.id.editText_password)).getText().toString();
                        search_password(account,password);
                    }
                })
                .create().show();

    }
    public void search_password(String account, final String password){
        BmobQuery<User> query = new BmobQuery<User>();
        //查询playerName叫“比目”的数据
        query.addWhereEqualTo("account",account);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(2);
        //执行查询方法
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if(e==null){
                    //toast("查询成功：共"+object.size()+"条数据。");
                    Toast.makeText(MainActivity.this,
                            "查询成功：共"+object.size()+"条数据。", Toast.LENGTH_SHORT).show();
                    //for (User user : object) {}
                        //获得playerName的信息
                    if(object.get(0).getPassword().equals(password))
                        Toast.makeText(MainActivity.this,
                            "login成功", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this,
                                "login failed", Toast.LENGTH_SHORT).show();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    Toast.makeText(MainActivity.this,
                            "not account", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
