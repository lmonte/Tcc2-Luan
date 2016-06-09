package com.example.qr_readerexample.views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView.OnQRCodeReadListener;
import com.example.qr_readerexample.R;
import com.example.qr_readerexample.controllers.EquipamentoDAO;
import com.example.qr_readerexample.models.EquipamentoBO;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DecoderActivity extends Activity implements OnQRCodeReadListener {

    private EditText idView;
    private EditText nameBox;
    private TextView myTextView;
    private QRCodeReaderView mydecoderview;
    private ImageView line_image;

    @Override
    public LayoutInflater getLayoutInflater() {
        return super.getLayoutInflater();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            EquipamentoDAO dao = new EquipamentoDAO(this);
            dao.initData(this);
        } catch (Exception e) {
            Log.e("erro", "database", e);
            myTextView.setText("equipamento não encontrado! ");
        }
        // Config QrCode
        setContentView(R.layout.activity_decoder);
        mydecoderview = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        mydecoderview.setOnQRCodeReadListener(this);
        myTextView = (TextView) findViewById(R.id.exampleTextView);
        line_image = (ImageView) findViewById(R.id.red_line_image);
        TranslateAnimation mAnimation = new TranslateAnimation(
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0.5f);
        mAnimation.setDuration(1000);
        mAnimation.setRepeatCount(-1);
        mAnimation.setRepeatMode(Animation.REVERSE);
        mAnimation.setInterpolator(new LinearInterpolator());
        line_image.setAnimation(mAnimation);
    }
    // Called when a QR is decoded
    // "text" : the text encoded in QR
    // "points" : points where QR control points are placed
    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        final String encryptedText = encryptMD5(text);
        EquipamentoDAO dao = new EquipamentoDAO(this);
        EquipamentoBO object;
        try {
            object = dao.findById(Long.parseLong(text));
            myTextView.setText("Nome do equipamento: " + object.getName());

            if(text.equals("1")){
                Intent i = new Intent(this, VideoActivity.class);
                i.putExtra("video", object.getVideo());
                startActivity(i);
            }
            else if(text.equals("2") ){
                Intent i = new Intent(this, Videoleg.class);
                i.putExtra("video", object.getVideo());
                startActivity(i);
            }
            else if(text.equals("3") ){
                Intent i = new Intent(this, VideoPuxada.class);
                i.putExtra("video", object.getVideo());
                startActivity(i);
            }
            else if(text.equals("4") ){
                Intent i = new Intent(this, VideoSupino.class);
                i.putExtra("video", object.getVideo());
                startActivity(i);
            }
            else if(text.equals("5") ){
                Intent i = new Intent(this, VideoTriceps.class);
                i.putExtra("video", object.getVideo());
                startActivity(i);
            }
        } catch (Exception e) {
            Log.e("erro", "database", e);
            myTextView.setText("equipamento não encontrado! ");
        }
    }

    private static String encryptMD5(final String data) {
        final MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            final BigInteger hash = new BigInteger(1, md.digest(data.getBytes()));
            return hash.toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "error on ecrypt";
    }


    // Called when your device have no camera
    @Override
    public void cameraNotFound() {

    }

    // Called when there's no QR codes in the camera preview image
    @Override
    public void QRCodeNotFoundOnCamImage() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mydecoderview.getCameraManager().startPreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mydecoderview.getCameraManager().stopPreview();
    }
}
