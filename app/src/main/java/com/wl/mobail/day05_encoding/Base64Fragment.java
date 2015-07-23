package com.wl.mobail.day05_encoding;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;


/**
 * A simple {@link Fragment} subclass.
 */
public class Base64Fragment extends Fragment implements View.OnClickListener {


    private EditText src;
    private EditText rlt;

    public Base64Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base64, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        view.findViewById(R.id.base64_encode).setOnClickListener(this);
        view.findViewById(R.id.base64_decode).setOnClickListener(this);
        src = ((EditText) view.findViewById(R.id.base64_src));
        rlt = ((EditText) view.findViewById(R.id.base64_rlt));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.base64_encode:
                String src_s = src.getText().toString();
                if (TextUtils.isEmpty(src_s)){
                    Toast.makeText(getActivity(), "原码不可为空", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String s = Base64.encodeToString(src_s.getBytes("UTF-8"), Base64.DEFAULT);
                        rlt.setText(s);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.base64_decode:
                String rlt_s = rlt.getText().toString();
                if (TextUtils.isEmpty(rlt_s)){
                    Toast.makeText(getActivity(), "编码不可为空", Toast.LENGTH_SHORT).show();
                } else {
                    byte[] decode = Base64.decode(rlt_s, Base64.DEFAULT);
                    try {
                        src.setText(new String(decode, "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
