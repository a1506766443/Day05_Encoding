package com.wl.mobail.day05_encoding;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * A simple {@link Fragment} subclass.
 */
public class Md5Fragment extends Fragment implements View.OnClickListener {


    private EditText edit;
    private TextView text;

    public Md5Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_md5, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        view.findViewById(R.id.md5_button).setOnClickListener(this);
        edit = ((EditText) view.findViewById(R.id.md5_edit));
        text = ((TextView) view.findViewById(R.id.md5_text));
    }

    @Override
    public void onClick(View v) {
        String s = edit.getText().toString();
        if (TextUtils.isEmpty(s)){
            Toast.makeText(getActivity(), "摘要数据不可为空", Toast.LENGTH_SHORT).show();
        } else {
            try {
                //获取一种摘要算法
                MessageDigest instance = MessageDigest.getInstance("MD5");
                byte[] digest = instance.digest(s.getBytes("UTF-8"));
                StringBuilder builder = new StringBuilder();
                for (byte b : digest) {
                    builder.append(String.format("%2x", b & 0xff));
                }
                String replace = builder.toString().replace(' ', '0').toUpperCase();
                text.setText(replace);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
}
