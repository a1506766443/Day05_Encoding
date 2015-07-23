package com.wl.mobail.day05_encoding;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * A simple {@link Fragment} subclass.
 * DES 密钥长度 8 Bytes
 * 3DES  密钥长度  24 bytes----------算法名称 DESede
 * AES  密钥长度  32 bytes-----------          AES
 *
 *
 * 3DES 怎么来的  ：把原文进行加密时，把8为byte数组做为一个单元，末尾不足8位后面补0，正好8位，最后补8个0 表示结束符（自己没有结束符）
 * 是DES密钥长度的3倍，把24为的key 分为了3个key  其实是3个DES，先对原文经过des加密一次，通过第二组key解密一次，再加密一次
 *
 *
 */
public class DesFragment extends Fragment implements View.OnClickListener {


    private EditText key;
    private EditText src;
    private EditText rlt;


    public DesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_des, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        key = ((EditText) view.findViewById(R.id.des_key));
        src = ((EditText) view.findViewById(R.id.des_src));
        rlt = ((EditText) view.findViewById(R.id.des_rlt));
       view.findViewById(R.id.des_encode).setOnClickListener(this);
        view.findViewById(R.id.des_decode).setOnClickListener(this);

    }

    //先判断key石佛为空，只有在密钥有值得时候 才可以做接下来的操作
    public  void  onClick(View v)  {
        String key_str=key.getText().toString();
        if(TextUtils.isEmpty(key_str)){
            Toast.makeText(getActivity(),"密钥不可谓空",Toast.LENGTH_SHORT).show();

        }else{
            byte[] bytes = null;
            try {
                bytes = key_str.getBytes("UTF-8");
                //将
                byte[] keys = new byte[8];//===========24=======32
                System.arraycopy(bytes,0,keys,0,Math.min(bytes.length,keys.length));
                //加解密时，用到的key对象   参一：key[]  参二：什么样的key
                // 参一应该和参二的长度一样（即：第一个参数的byte数组的长度必须符合第二个参数算法的要求）
                //不同的算法要求的byte数组的长度是不同的 DES 要求8位（不能短 不能长），
                SecretKey secretKey = new SecretKeySpec(keys, "DES");//===========DESede=========AES
                //加解密时，用到的工具对象，工具的算法和key的算法
                Cipher cipher = Cipher.getInstance("DES");//=============DESede=========AES
                switch (v.getId()){
                    case R.id.des_encode:
                        String src_str = src.getText().toString();
                        if(TextUtils.isEmpty(src_str)){
                            Toast.makeText(getActivity(),"加密时，原文不可谓空",Toast.LENGTH_SHORT).show();

                        }else{
                            //初始化为加密模式
                            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                            byte[] aFinal = cipher.doFinal(src_str.getBytes("UTF-8"));
                            rlt.setText(Base64.encodeToString(aFinal,Base64.DEFAULT));
                        }
                        //byte数组不能显示在editText上，byte[] （不是任何编码格式的byte数组） --转化为String --（不能用new String ）

                        break;
                    case R.id.des_decode:
                        String rlt_str = rlt.getText().toString();
                        if(TextUtils.isEmpty(rlt_str)){
                            Toast.makeText(getActivity(),"解密时，密文不可谓空",Toast.LENGTH_SHORT).show();
                        }else{
                            cipher.init(Cipher.DECRYPT_MODE, secretKey);
                            byte[] aFinal = cipher.doFinal(Base64.decode(rlt_str,Base64.DEFAULT));
                            src.setText(new String(aFinal));
                        }
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }

        }
    }
}
