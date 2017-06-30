package com.kcb360.newkcb.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.kcb360.newkcb.R;
import com.kcb360.newkcb.diy.DiyDialog;
import com.kcb360.newkcb.base.BaseActivity;
import com.kcb360.newkcb.entity.LoginBean;
import com.kcb360.newkcb.net.JSONUtil;
import com.kcb360.newkcb.net.Request;
import com.kcb360.newkcb.net.SingletonInstance;
import com.kcb360.newkcb.net.UrlUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Created by xinshichao on 2017/4/28.
 * <p>
 * 添加银行卡
 */

public class AddBankCardActivity extends BaseActivity {

    // 布局相关
    // 返回
    private ImageView backIv;
    // 银行名称
    private Spinner bankNameSp;
    // 银行卡号, 户名, 银行预留手机号
    private EditText cardNumEt, realNameEt, stayPhoneEt;
    // 添加
    private Button subBtn;
    // spinner adapter
    private ArrayAdapter<String> arrayAdapter;

    // 数据获取
    // 线程池
    private ExecutorService es = SingletonInstance.getInstanceCachePool();
    // 线程
    private AddBankCardThread addBankCardThread;
    // 传参map
    private Map<String, String> map;
    // progessdialog
    private Dialog dg;
    // 存储数据
    private LoginBean bean;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (dg != null && dg.isShowing()) {
                dg.dismiss();
            }
            switch (msg.what) {
                case 0:
                    DiyDialog.loginDialog(AddBankCardActivity.this, msg.obj.toString());
                    break;
                case 1:
                    if (bean.getCode().equals("1")) {
                        Toast.makeText(AddBankCardActivity.this, bean.getContext(), Toast.LENGTH_SHORT).show();
                        setResult(1);
                        finish();
                    } else {
                        DiyDialog.loginDialog(AddBankCardActivity.this, bean.getContext());
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    public int setLayout() {
        return R.layout.activity_add_bank_card;
    }

    @Override
    protected void initData() {
        map = new HashMap<>();
        addBankCardThread = new AddBankCardThread();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        backIv = findView(R.id.add_bank_card_back_iv);
        bankNameSp = findView(R.id.add_bank_card_bank_name_sp);
        cardNumEt = findView(R.id.add_bank_card_card_num_et);
        realNameEt = findView(R.id.add_bank_card_real_name_et);
        stayPhoneEt = findView(R.id.add_bank_card_bank_stay_phone_et);
        subBtn = findView(R.id.add_bank_card_sub_btn);

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        arrayAdapter = new ArrayAdapter<String>(AddBankCardActivity.this, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.bank_name));
        bankNameSp.setAdapter(arrayAdapter);
        bankNameSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("The Spinner-->", " isRun");
                map.put("bankName", getResources().getStringArray(R.array.bank_name)[position]);
                map.put("recBankNo", getResources().getStringArray(R.array.bank_code)[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!doCheck()) {
                    return;
                }
                map.put("accountNumber", cardNumEt.getText().toString());
                map.put("bindName", realNameEt.getText().toString());
                map.put("bindPhone", stayPhoneEt.getText().toString());
                es.execute(addBankCardThread);
            }
        });
    }

    class AddBankCardThread implements Runnable {

        @Override
        public void run() {
            Message msg = new Message();
            try {
                String res = new Request().cookieRequest(map, UrlUtil.ADD_BANK_CARD);
                if (res.length() < 4) {
                    msg.obj = res;
                    msg.what = 0;
                    handler.sendMessage(msg);
                } else {
                    bean = JSONUtil.LoginJsonAnalytic(res);
                }
            } catch (IOException e) {
                e.printStackTrace();
                msg.obj = e.toString();
                msg.what = 0;
                handler.sendMessage(msg);
            }
            handler.sendEmptyMessage(1);
        }
    }

    public boolean doCheck() {
        if (!map.containsKey("bankName") || !map.containsKey("recBankNo") ||
                map.get("bankName").equals("请选择银行") || map.get("recBankNo").equals("0")) {
            DiyDialog.loginDialog(AddBankCardActivity.this, "请选择银行");
            return false;
        }
        if (!bankCardNumCheck()) {
            DiyDialog.loginDialog(AddBankCardActivity.this, "银行名称与卡号不对应");
            return false;
        }
        if (realNameEt.getText().length() == 0 || realNameEt.getText().toString().equals("")) {
            DiyDialog.loginDialog(AddBankCardActivity.this, "请输入开户人姓名");
            return false;
        }
        if (stayPhoneEt.getText().length() == 0 || stayPhoneEt.getText().toString().equals("") ||
                (stayPhoneEt.getText().length() != 11)) {
            DiyDialog.loginDialog(AddBankCardActivity.this, "请输入银行预留手机号");
            return false;
        }
        return true;
    }

    // 验证银行卡号是否正确
    public boolean bankCardNumCheck() {
        if (cardNumEt.getText().length() < 15 || cardNumEt.getText().length() > 19) {
            return false;
        }
        char bit = getBankCardCheckCode(cardNumEt.getText().toString().substring(0, cardNumEt.getText().length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardNumEt.getText().charAt(cardNumEt.getText().length() - 1) == bit;
    }

    public char getBankCardCheckCode(String nonCheckCodeBankCard) {
        if (nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0
                || !nonCheckCodeBankCard.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeBankCard.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }
}
