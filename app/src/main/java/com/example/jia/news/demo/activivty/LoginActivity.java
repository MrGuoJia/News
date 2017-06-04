package com.example.jia.news.demo.activivty;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jia.news.R;

public class LoginActivity extends AppCompatActivity {//登录界面没用到啊
    private EditText edt_phone;//编辑电话
    private EditText edt_code;//编辑验证码
    private TextView tv_sendCode;//发送验证码
    private Button btn_register;//注册
    private String login="";//用来假设客户要评论时，是否登录(没钱做短信验证)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        onClickEvent();
    }
    private void onClickEvent() {
        tv_sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);//让tv_sendCode处于不可点击状态
                timer.start();//开启倒计时
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNum=edt_phone.getText().toString();//获取编辑框的手机号码,记得用正则表达式验证是否符合手机格式
                String codePass=edt_code.getText().toString();//获取编辑框的验证码,记得证明验证码的正确性
                if(phoneNum.length()==0){
                    Toast.makeText(LoginActivity.this,"电话号码输入不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(codePass.length()==0){
                    Toast.makeText(LoginActivity.this,"验证码输入不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(isMobile(phoneNum)!=true){
                    Toast.makeText(LoginActivity.this,"电话号码不合法",Toast.LENGTH_SHORT).show();
                    return;
                }
                /*
                * 由于短信要收钱，没写，所以要看手机登录验证可去聚合数据开发文档看
                * 或者在自己的github 的PersonListWithBmob---LoginActivity去看看
                * 或看看自己收藏的博客
                * */
               login="login";
                Intent i=new Intent(LoginActivity.this,DetailNewsActivity.class);
                i.putExtra("login",login);
                startActivity(i);//这边假设登录成功
                finish();
            }
        });
    }

    private void initViews() {
        edt_phone= (EditText) findViewById(R.id.edt_phone);
        edt_code= (EditText) findViewById(R.id.edt_code);
        tv_sendCode= (TextView) findViewById(R.id.tv_sendCode);
        btn_register= (Button) findViewById(R.id.btn_register);

    }
    CountDownTimer timer=new CountDownTimer(10000, 1000) {// millisInFuture 总时长， countDownInterval 时间间隔
        @Override
        public void onTick(long millisUntilFinished) {
            tv_sendCode.setText(millisUntilFinished/1000+"秒后重新发送验证码");
        }

        @Override
        public void onFinish() {
            tv_sendCode.setEnabled(true);
            tv_sendCode.setText("发送验证码");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();//结束时，取消倒计时
    }
    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }

    }
}
