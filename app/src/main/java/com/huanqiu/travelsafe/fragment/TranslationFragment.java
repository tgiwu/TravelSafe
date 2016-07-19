package com.huanqiu.travelsafe.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.huanqiu.travelsafe.App;
import com.huanqiu.travelsafe.R;
import com.huanqiu.travelsafe.controllers.TranslationController;
import com.huanqiu.travelsafe.event.RequestTranslationEvent;
import com.huanqiu.travelsafe.event.RxBus;
import com.huanqiu.travelsafe.model.TranslationModel;
import com.huanqiu.travelsafe.utils.Constants;
import com.nuance.speechkit.Audio;
import com.nuance.speechkit.AudioPlayer;
import com.nuance.speechkit.DetectionType;
import com.nuance.speechkit.Language;
import com.nuance.speechkit.Recognition;
import com.nuance.speechkit.RecognitionType;
import com.nuance.speechkit.Session;
import com.nuance.speechkit.Transaction;
import com.nuance.speechkit.TransactionException;

import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/19.
 */
public class TranslationFragment extends BaseTranslationFragment implements TranslationController.TranslationUi, AudioPlayer.Listener {

    private enum State {
        IDLE,
        LISTENING,
        PROCESSING
    }

    private Audio startEarcon;
    private Audio stopEarcon;
    private Audio errorEarcon;

    private Session speechSession;
    private Transaction transaction;
    private State state = State.IDLE;

    private Handler handler = new Handler();

    @Inject
    RxBus rxBus;
    @BindView(R.id.reco_type_label)
    TextView recoTypeLabel;
    @BindView(R.id.dictation)
    RadioButton dictation;
    @BindView(R.id.search)
    RadioButton search;
    @BindView(R.id.tv)
    RadioButton tv;
    @BindView(R.id.reco_type_picker)
    RadioGroup recoTypePicker;
    @BindView(R.id.detection_type_label)
    TextView detectionTypeLabel;
    @BindView(R.id.long_endpoint)
    RadioButton longEndpoint;
    @BindView(R.id.short_endpoint)
    RadioButton shortEndpoint;
    @BindView(R.id.none)
    RadioButton none;
    @BindView(R.id.detection_type_picker)
    RadioGroup detectionTypePicker;
    @BindView(R.id.language)
    EditText language;
    @BindView(R.id.language_container)
    LinearLayout languageContainer;
    @BindView(R.id.logs)
    TextView logs;
    @BindView(R.id.logs_container)
    ScrollView logsContainer;
    @BindView(R.id.volume_bar)
    ProgressBar volumeBar;
    @BindView(R.id.logs_label)
    TextView logsLabel;
    @BindView(R.id.clear_logs)
    Button clearLogs;
    @BindView(R.id.toggle_reco)
    Button toggleReco;
    @BindView(R.id.footer)
    FrameLayout footer;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        speechSession = Session.Factory.session(getActivity(), Constants.NUANCE_SERVER_URI, Constants.NUANCE_APP_KEY);
        speechSession.getAudioPlayer().setListener(this);
        loadEarcons();
        setState(State.IDLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.asr_fragment_layout, container, false);
        ButterKnife.bind(this, view);
        App.getInstance().getGraph().inject(this);
        return view;
    }

    @Override
    public void setCallbacks(TranslationController.TranslationUiCallback callbacks) {
        super.setCallbacks(callbacks);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void showError(int s) {
        super.showError(s);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void doNext(TranslationModel translationModel) {
        Log.i("zhy", "do next " + translationModel.getTrans_result().get(0).getDst());
        synthesize(translationModel.getTrans_result().get(0).getDst());
    }

    @Override
    public Map<String, String> getInputParam() {
        return null;
    }

    @OnClick({R.id.clear_logs, R.id.toggle_reco})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clear_logs:
                logs.setText("");

                break;
            case R.id.toggle_reco:
                toggleReco();
                break;
        }
    }

    public static TranslationFragment newInstance(String id) {
        TranslationFragment fragment = new TranslationFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    private void setState(State newState) {
        state = newState;
        switch (newState) {
            case IDLE:
                toggleReco.setText(getResources().getString(R.string.recognize));
                break;
            case LISTENING:
                toggleReco.setText(getResources().getString(R.string.listening));
                break;
            case PROCESSING:
                toggleReco.setText(getResources().getString(R.string.processing));
                break;
        }
    }

    private void toggleReco() {
        switch (state) {
            case IDLE:
                recognize();
                break;
            case LISTENING:
                stopRecording();
                break;
            case PROCESSING:
                cancel();
                break;
        }
    }

    private void recognize() {
        //Setup our Reco transaction options.
        Transaction.Options options = new Transaction.Options();
        options.setRecognitionType(resourceIDToRecoType(recoTypePicker.getCheckedRadioButtonId()));
        options.setDetection(resourceIDToDetectionType(detectionTypePicker.getCheckedRadioButtonId()));
        options.setLanguage(new Language("cmn-CHN"));
        options.setEarcons(startEarcon, stopEarcon, errorEarcon, null);

        //Start listening
        transaction = speechSession.recognize(options, recoListener);
    }

    private Transaction.Listener recoListener = new Transaction.Listener() {
        @Override
        public void onStartedRecording(Transaction transaction) {
            logs.append("\nonStartedRecording");

            //We have started recording the users voice.
            //We should update our state and start polling their volume.
            setState(State.LISTENING);
            startAudioLevelPoll();
        }

        @Override
        public void onFinishedRecording(Transaction transaction) {
            logs.append("\nonFinishedRecording");

            //We have finished recording the users voice.
            //We should update our state and stop polling their volume.
            setState(State.PROCESSING);
            stopAudioLevelPoll();
        }

        @Override
        public void onRecognition(Transaction transaction, Recognition recognition) {
            logs.append("\nonRecognition: " + recognition.getText());

            rxBus.send(new RequestTranslationEvent(recognition.getText(), "zh", "en", TranslationFragment.this.hashCode()));

            //We have received a transcription of the users voice from the server.
            setState(State.IDLE);
        }

        @Override
        public void onSuccess(Transaction transaction, String s) {
            logs.append("\nonSuccess");

            //Notification of a successful transaction. Nothing to do here.
        }

        @Override
        public void onError(Transaction transaction, String s, TransactionException e) {
            logs.append("\nonError: " + e.getMessage() + ". " + s);

            //Something went wrong. Check Configuration.java to ensure that your settings are correct.
            //The user could also be offline, so be sure to handle this case appropriately.
            //We will simply reset to the idle state.
            setState(State.IDLE);
        }
    };

    private void stopRecording() {
        transaction.stopRecording();
    }

    private void cancel() {
        transaction.cancel();
        setState(State.IDLE);
    }

    private Runnable audioPoller = new Runnable() {
        @Override
        public void run() {
            float level = transaction.getAudioLevel();
            volumeBar.setProgress((int)level);
            handler.postDelayed(audioPoller, 50);
        }
    };

    private void startAudioLevelPoll() {
        audioPoller.run();
    }

    private void stopAudioLevelPoll() {
        handler.removeCallbacks(audioPoller);
        volumeBar.setProgress(0);
    }

    private void loadEarcons() {
        //Load all the earcons from disk
        startEarcon = new Audio(getActivity(), R.raw.sk_start, Constants.PCM_FORMAT);
        stopEarcon = new Audio(getActivity(), R.raw.sk_stop, Constants.PCM_FORMAT);
        errorEarcon = new Audio(getActivity(), R.raw.sk_error, Constants.PCM_FORMAT);
    }

    private RecognitionType resourceIDToRecoType(int id) {
        if(id == R.id.dictation) {
            return RecognitionType.DICTATION;
        }
        if(id == R.id.search) {
            return RecognitionType.SEARCH;
        }
        if(id == R.id.tv) {
            return RecognitionType.TV;
        }
        return null;
    }

    private DetectionType resourceIDToDetectionType(int id) {
        if(id == R.id.long_endpoint) {
            return DetectionType.Long;
        }
        if(id == R.id.short_endpoint) {
            return DetectionType.Short;
        }
        if(id == R.id.none) {
            return DetectionType.None;
        }
        return null;
    }

    private void synthesize(String s) {
        //Setup our TTS transaction options.
        Transaction.Options options = new Transaction.Options();
        options.setLanguage(new Language(language.getText().toString()));
        //options.setVoice(new Voice(Voice.SAMANTHA)); //optionally change the Voice of the speaker, but will use the default if omitted.
        //Start a TTS transaction
        transaction = speechSession.speakString(s, options, new Transaction.Listener() {
            @Override
            public void onAudio(Transaction transaction, Audio audio) {
                logs.append("\nonAudio");

                //The TTS audio has returned from the server, and has begun auto-playing.
                transaction = null;
            }

            @Override
            public void onSuccess(Transaction transaction, String s) {
                logs.append("\nonSuccess");

                //Notification of a successful transaction. Nothing to do here.
            }

            @Override
            public void onError(Transaction transaction, String s, TransactionException e) {
                logs.append("\nonError: " + e.getMessage() + ". " + s);

                //Something went wrong. Check Configuration.java to ensure that your settings are correct.
                //The user could also be offline, so be sure to handle this case appropriately.

                transaction = null;
            }
        });
    }

    @Override
    public void onBeginPlaying(AudioPlayer audioPlayer, Audio audio) {

    }

    @Override
    public void onFinishedPlaying(AudioPlayer audioPlayer, Audio audio) {

    }
}
