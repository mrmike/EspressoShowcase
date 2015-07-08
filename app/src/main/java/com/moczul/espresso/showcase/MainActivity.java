package com.moczul.espresso.showcase;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity implements Observer<String> {

    public static final String EXTRA_TITLE = "extra_title";

    private static final int SPEECH_REQUEST_CODE = 1337;

    private TextView mTitle;
    private EditText mInput;
    private TextView mOutput;

    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = (TextView) findViewById(R.id.title);
        mInput = (EditText) findViewById(R.id.text_input);
        mOutput = (TextView) findViewById(R.id.output);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_TITLE)) {
            mTitle.setText(intent.getStringExtra(EXTRA_TITLE));
        }

        findViewById(R.id.speech).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                startActivityForResult(intent, SPEECH_REQUEST_CODE);
            }
        });

        findViewById(R.id.action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeAction(mInput.getText().toString());
            }
        });
    }

    private void makeAction(final String input) {
        final Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onStart();
                /**
                 * Very complex logic
                 */
                sleep(4000);
                subscriber.onNext(input);
                subscriber.onCompleted();
            }
        });

        mSubscription = observable.subscribeOn(RxScheduler.get())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            mInput.setText(results.get(0));
        }
    }

    @Override
    public void onNext(String s) {
        mOutput.setText(s);
    }

    @Override
    public void onCompleted() {
        // ignore
    }

    @Override
    public void onError(Throwable e) {
        // ignore
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscription.unsubscribe();
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignore) {
        }
    }
}
