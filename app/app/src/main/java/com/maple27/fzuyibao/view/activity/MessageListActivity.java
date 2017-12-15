package com.maple27.fzuyibao.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.DefaultUser;
import com.maple27.fzuyibao.model.MyMessage;
import com.maple27.fzuyibao.model.entity.MessageReciverEntity;
import com.maple27.fzuyibao.view.custom_view.ChatView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.jiguang.imui.chatinput.ChatInputView;
import cn.jiguang.imui.chatinput.listener.OnCameraCallbackListener;
import cn.jiguang.imui.chatinput.listener.OnClickEditTextListener;
import cn.jiguang.imui.chatinput.listener.OnMenuClickListener;
import cn.jiguang.imui.chatinput.listener.RecordVoiceListener;
import cn.jiguang.imui.chatinput.model.FileItem;
import cn.jiguang.imui.chatinput.model.VideoItem;
import cn.jiguang.imui.commons.ImageLoader;
import cn.jiguang.imui.commons.models.IMessage;
import cn.jiguang.imui.messages.MsgListAdapter;
import cn.jiguang.imui.messages.ViewHolderController;
import cn.jiguang.imui.messages.ptr.PtrHandler;
import cn.jiguang.imui.messages.ptr.PullToRefreshLayout;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.options.MessageSendingOptions;
import cn.jpush.im.api.BasicCallback;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MessageListActivity extends Activity implements ChatView.OnKeyboardChangedListener,
        ChatView.OnSizeChangedListener, View.OnTouchListener, EasyPermissions.PermissionCallbacks, SensorEventListener {

    public static String KEY_SENDER = "KEY_SENDER";
    public static String KEY_RECIVER = "KEY_RECIVER";
    public static String APPKEY = "976bf293c72d49037901906a";

    public static String BASE_AVATAR_PATH = "";

    MessageReciverEntity mSender;
    MessageReciverEntity mTarget;

    Conversation mConversation;

    //实现右滑退出
    final static float TOUCHACTIONTIME = 300;
    float mLastTouchX = 0;
    long mLastTouchTime = 0;

    private final static String TAG = "MessageListActivity";
    private final int RC_RECORD_VOICE = 0x0001;
    private final int RC_CAMERA = 0x0002;
    private final int RC_PHOTO = 0x0003;

    private ChatView mChatView;
    private MsgListAdapter<MyMessage> mAdapter;
    private List<MyMessage> mData;

    private InputMethodManager mImm;
    private Window mWindow;
    private HeadsetDetectReceiver mReceiver;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private PowerManager mPowerManager;
    private PowerManager.WakeLock mWakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JMessageClient.registerEventReceiver(this);
        setContentView(R.layout.activity_message_chat);
        this.mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mWindow = getWindow();
        registerProximitySensorListener();
        //初始化数据
        initData();

        mChatView = (ChatView) findViewById(R.id.chat_view);
        mChatView.initModule();

        if(mTarget ==  null){
            mChatView.setTitle("Deadpool");
        }else{
            mChatView.setTitle(mTarget.getNickname());
        }

        mData = getMessages();
        initMsgAdapter();
        mReceiver = new HeadsetDetectReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(mReceiver, intentFilter);
        mChatView.setKeyboardChangedListener(this);
        mChatView.setOnSizeChangedListener(this);
        mChatView.setOnTouchListener(this);

        mChatView.setMenuClickListener(new OnMenuClickListener() {
            @Override
            public boolean onSendTextMessage(CharSequence input) {
                if (input.length() == 0) {
                    Log.i("MessageListActivity", "onSendTextMessage->input length = 0");
                    return false;
                }
                Log.i("MessageListActivity", "onSendTextMessage->start send");
                sendTexTMessage(input.toString());

                MyMessage message = new MyMessage(input.toString(), IMessage.MessageType.SEND_TEXT);
                message.setUserInfo(new DefaultUser(mSender.getAccount(), mSender.getNickname(), BASE_AVATAR_PATH + mSender.getAvatar()));
                message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                mData.add(message);

                mAdapter.addToStart(message, true);
                return true;
            }

            @Override
            public void onSendFiles(List<FileItem> list) {
                if (list == null || list.isEmpty()) {
                    return;
                }

                MyMessage message;
                for (FileItem item : list) {
                    if (item.getType() == FileItem.Type.Image) {
                        message = new MyMessage(null, IMessage.MessageType.SEND_IMAGE);

                    } else if (item.getType() == FileItem.Type.Video) {
                        message = new MyMessage(null, IMessage.MessageType.SEND_VIDEO);
                        message.setDuration(((VideoItem) item).getDuration());

                    } else {
                        throw new RuntimeException("Invalid FileItem type. Must be Type.Image or Type.Video");
                    }

                    message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                    message.setMediaFilePath(item.getFilePath());
                    message.setUserInfo(new DefaultUser("1", "Ironman", "R.drawable.ironman"));

                    final MyMessage fMsg = message;
                    MessageListActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.addToStart(fMsg, true);
                        }
                    });
                }
            }

            @Override
            public boolean switchToMicrophoneMode() {
                scrollToBottom();
                String[] perms = new String[]{
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                };

                if (!EasyPermissions.hasPermissions(MessageListActivity.this, perms)) {
                    EasyPermissions.requestPermissions(MessageListActivity.this,
                            getResources().getString(R.string.rationale_record_voice),
                            RC_RECORD_VOICE, perms);
                }
                return true;
            }

            @Override
            public boolean switchToGalleryMode() {
                scrollToBottom();
                String[] perms = new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE
                };

                if (!EasyPermissions.hasPermissions(MessageListActivity.this, perms)) {
                    EasyPermissions.requestPermissions(MessageListActivity.this,
                            getResources().getString(R.string.rationale_photo),
                            RC_PHOTO, perms);
                }
                return true;
            }

            @Override
            public boolean switchToCameraMode() {
                scrollToBottom();
                String[] perms = new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO
                };

                if (!EasyPermissions.hasPermissions(MessageListActivity.this, perms)) {
                    EasyPermissions.requestPermissions(MessageListActivity.this,
                            getResources().getString(R.string.rationale_camera),
                            RC_CAMERA, perms);
                } else {
                    File rootDir = getFilesDir();
                    String fileDir = rootDir.getAbsolutePath() + "/photo";
                    mChatView.setCameraCaptureFile(fileDir, new SimpleDateFormat("yyyy-MM-dd-hhmmss",
                            Locale.getDefault()).format(new Date()));
                }
                return true;
            }

            @Override
            public boolean switchToEmojiMode() {
                scrollToBottom();
                return true;
            }
        });

        mChatView.setRecordVoiceListener(new RecordVoiceListener() {
            @Override
            public void onStartRecord() {
                // set voice file path, after recording, audio file will save here
                String path = Environment.getExternalStorageDirectory().getPath() + "/voice";
                File destDir = new File(path);
                if (!destDir.exists()) {
                    destDir.mkdirs();
                }
                mChatView.setRecordVoiceFile(destDir.getPath(), DateFormat.format("yyyy-MM-dd-hhmmss",
                        Calendar.getInstance(Locale.CHINA)) + "");
            }

            @Override
            public void onFinishRecord(File voiceFile, int duration) {
                MyMessage message = new MyMessage(null, IMessage.MessageType.SEND_VOICE);
                message.setUserInfo(new DefaultUser(mSender.getAccount(), mSender.getNickname(), "R.drawable.ironman"));
                message.setMediaFilePath(voiceFile.getPath());
                message.setDuration(duration);
                message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                mAdapter.addToStart(message, true);

                sendVoiceMessage(voiceFile, duration);
            }

            @Override
            public void onCancelRecord() {

            }
        });

        mChatView.setOnCameraCallbackListener(new OnCameraCallbackListener() {
            @Override
            public void onTakePictureCompleted(String photoPath) {
                final MyMessage message = new MyMessage(null, IMessage.MessageType.SEND_IMAGE);
                message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                message.setMediaFilePath(photoPath);
                message.setUserInfo(new DefaultUser("1", "Ironman", "R.drawable.ironman"));
                MessageListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.addToStart(message, true);
                    }
                });
            }

            @Override
            public void onStartVideoRecord() {

            }

            @Override
            public void onFinishVideoRecord(String videoPath) {

            }

            @Override
            public void onCancelVideoRecord() {

            }
        });

        mChatView.setOnTouchEditTextListener(new OnClickEditTextListener() {
            @Override
            public void onTouchEditText() {
//                mAdapter.getLayoutManager().scrollToPosition(0);
            }
        });
    }

    private void sendVoiceMessage(File voiceFile, int duration) {
//        JMessageClient.createSingleVoiceMessage(String username, String appKey, File voiceFile, int duration)
    }

    private void sendTexTMessage(String text) {
            final String content = text;
            //for data
            TextContent textContent = new TextContent(content);
            Message message = mConversation.createSendMessage(textContent, mSender.getNickname());
            message.setOnSendCompleteCallback(new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    if(i==0){
                        //success
                        Log.i("MessageListActivity", "sendTexTMessage->sending success content:" + content);
                    }else{
                        //failed
                    }
                }
            });
            //设置消息发送时的一些控制参数
            MessageSendingOptions options = new MessageSendingOptions();
            options.setNeedReadReceipt(false);//是否需要对方用户发送消息已读回执
            options.setRetainOffline(true);//是否当对方用户不在线时让后台服务区保存这条消息的离线消息
            options.setShowNotification(true);//是否让对方展示sdk默认的通知栏通知
            options.setCustomNotificationEnabled(false);//是否需要自定义对方收到这条消息时sdk默认展示的通知栏中的文字
//        if (false) {
////            options.setNotificationTitle(mEt_customNotifyTitle.getText().toString());//自定义对方收到消息时通知栏展示的title
////            options.setNotificationAtPrefix(mEt_customNotifyAtPrefix.getText().toString());//自定义对方收到消息时通知栏展示的@信息的前缀
////            options.setNotificationText(mEt_customNotifyText.getText().toString());//自定义对方收到消息时通知栏展示的text
//        }
            //发送消息
            JMessageClient.sendMessage(message, options);
    }

    private void initData() {
        Log.i("MessageListActivity", "initData");
        //获得intent对象
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mSender = (MessageReciverEntity) bundle.getSerializable(MessageListActivity.KEY_SENDER);
        if(mSender == null){
            Log.i("MessageListActivity", "mSender == null");
        }
        mTarget = (MessageReciverEntity) bundle.getSerializable(MessageListActivity.KEY_RECIVER);
        if (mSender != null && mTarget != null) {
            Log.i("MessageListActivity", "initData->sender：" + mSender.getAccount());
            Log.i("MessageListActivity", "initData->reciver：" + mTarget.getAccount());

            Log.i("MessageListActivity", "initData->get Conversation：" + mTarget.getAccount());

            //获得mConversation
            mConversation = JMessageClient.getSingleConversation(mTarget.getAccount(), APPKEY);
            if (mConversation == null) {
                mConversation = Conversation.createSingleConversation(mTarget.getAccount(), APPKEY);
            }
        }

        //confirm avatar
        Log.i("MessageListActivity", "sender avatar:" + BASE_AVATAR_PATH + mSender.getAvatar());
        Log.i("MessageListActivity", "target avatar:" + BASE_AVATAR_PATH + mTarget.getAvatar());

    }

    private void registerProximitySensorListener() {
        try {
            mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
            mWakeLock = mPowerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, TAG);
            mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        try {
            if (audioManager.isBluetoothA2dpOn() || audioManager.isWiredHeadsetOn()) {
                return;
            }
            if (mAdapter.getMediaPlayer().isPlaying()) {
                float distance = event.values[0];
                if (distance >= mSensor.getMaximumRange()) {
                    mAdapter.setAudioPlayByEarPhone(0);
                    setScreenOn();
                } else {
                    mAdapter.setAudioPlayByEarPhone(2);
                    ViewHolderController.getInstance().replayVoice();
                    setScreenOff();
                }
            } else {
                if (mWakeLock != null && mWakeLock.isHeld()) {
                    mWakeLock.release();
                    mWakeLock = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setScreenOn() {
        if (mWakeLock != null) {
            mWakeLock.setReferenceCounted(false);
            mWakeLock.release();
            mWakeLock = null;
        }
    }

    private void setScreenOff() {
        if (mWakeLock == null) {
            mWakeLock = mPowerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, TAG);
        }
        mWakeLock.acquire();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private class HeadsetDetectReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                if (intent.hasExtra("state")) {
                    int state = intent.getIntExtra("state", 0);
                    mAdapter.setAudioPlayByEarPhone(state);
                }
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    private List<MyMessage> getMessages() {
        List<MyMessage> list = new ArrayList<>();
        if(mConversation == null){
            return list;
        }
        List<Message> messages = mConversation.getAllMessage();
        Log.i("MessageListActivity", "getMessages->length：" + messages.size());

        for (int i = 0; i < messages.size(); i++) {
            Message localMessage = messages.get(i);
            String who = localMessage.getFromUser().getUserName();

            TextContent textContent = (TextContent) localMessage.getContent();
            String content = textContent.getText();

            long time_num = localMessage.getCreateTime();
            SimpleDateFormat format=new SimpleDateFormat("hh:mm:ss");
            Date d1=new Date(time_num);
            String time = format.format(d1);

            MyMessage message = null;
            if (who.equals(mTarget.getAccount())) {
                Log.i("MessageListActivity", "mTarget->name:" + who);
                message = new MyMessage(content, IMessage.MessageType.RECEIVE_TEXT);
                message.setUserInfo(new DefaultUser(mTarget.getAccount(), mTarget.getNickname(),BASE_AVATAR_PATH +  mTarget.getAvatar()));
            } else if(who.equals(mSender.getAccount())){
                Log.i("MessageListActivity", "mSender->name:" + who);
                message = new MyMessage(content, IMessage.MessageType.SEND_TEXT);
                message.setUserInfo(new DefaultUser(mSender.getAccount(), mSender.getNickname(), BASE_AVATAR_PATH + mSender.getAvatar()));
            }else{
                //error
                Log.i("MessageListActivity", "error->no one:" + who);
            }

            message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
            list.add(message);
        }
        Collections.reverse(list);
        return list;
    }

    private void initMsgAdapter() {
        ImageLoader imageLoader = new ImageLoader() {
            @Override
            public void loadAvatarImage(ImageView avatarImageView, String string) {
                // You can use other image load libraries.
                if (string.contains("R.drawable")) {
                    Integer resId = getResources().getIdentifier(string.replace("R.drawable.", ""),
                            "drawable", getPackageName());

                    avatarImageView.setImageResource(resId);
                } else {
                    Glide.with(getApplicationContext())
                            .load(string)
                            .apply(new RequestOptions()
                                    .placeholder(R.drawable.aurora_headicon_default))
                            .into(avatarImageView);
                }
            }

            @Override
            public void loadImage(ImageView imageView, String string) {
                // You can use other image load libraries.
                Glide.with(getApplicationContext())
                        .load(string)
                        .apply(new RequestOptions()
                                .fitCenter())
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.aurora_picture_not_found))
                        .apply(new RequestOptions()
                                .override(400, Target.SIZE_ORIGINAL))
                        .into(imageView);
            }
        };

        // Use default layout
        MsgListAdapter.HoldersConfig holdersConfig = new MsgListAdapter.HoldersConfig();
        mAdapter = new MsgListAdapter<>("0", holdersConfig, imageLoader);
        // If you want to customise your layout, try to create custom ViewHolder:
        // holdersConfig.setSenderTxtMsg(CustomViewHolder.class, layoutRes);
        // holdersConfig.setReceiverTxtMsg(CustomViewHolder.class, layoutRes);
        // CustomViewHolder must extends ViewHolders defined in MsgListAdapter.
        // Current ViewHolders are TxtViewHolder, VoiceViewHolder.

        mAdapter.setOnMsgClickListener(new MsgListAdapter.OnMsgClickListener<MyMessage>() {
            @Override
            public void onMessageClick(MyMessage message) {
                // do something
                if (message.getType() == IMessage.MessageType.RECEIVE_VIDEO
                        || message.getType() == IMessage.MessageType.SEND_VIDEO) {
                    if (!TextUtils.isEmpty(message.getMediaFilePath())) {
                        Intent intent = new Intent(MessageListActivity.this, VideoActivity.class);
                        intent.putExtra(VideoActivity.VIDEO_PATH, message.getMediaFilePath());
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            getApplicationContext().getString(R.string.message_click_hint),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        mAdapter.setMsgLongClickListener(new MsgListAdapter.OnMsgLongClickListener<MyMessage>() {
            @Override
            public void onMessageLongClick(MyMessage message) {
                Toast.makeText(getApplicationContext(),
                        getApplicationContext().getString(R.string.message_long_click_hint),
                        Toast.LENGTH_SHORT).show();
                // do something
            }
        });

        mAdapter.setOnAvatarClickListener(new MsgListAdapter.OnAvatarClickListener<MyMessage>() {
            @Override
            public void onAvatarClick(MyMessage message) {
                DefaultUser userInfo = (DefaultUser) message.getFromUser();
                Toast.makeText(getApplicationContext(),
                        getApplicationContext().getString(R.string.avatar_click_hint),
                        Toast.LENGTH_SHORT).show();
                // do something
            }
        });

        mAdapter.setMsgStatusViewClickListener(new MsgListAdapter.OnMsgStatusViewClickListener<MyMessage>() {
            @Override
            public void onStatusViewClick(MyMessage message) {
                // message status view click, resend or download here
            }
        });

//        MyMessage message = new MyMessage("Hello World", IMessage.MessageType.RECEIVE_TEXT);
//        message.setUserInfo(new DefaultUser("0", "Deadpool", "R.drawable.deadpool"));
//        mAdapter.addToStart(message, true);
//        MyMessage eventMsg = new MyMessage("haha", IMessage.MessageType.EVENT);
//        mAdapter.addToStart(eventMsg, true);
        mAdapter.addToEnd(mData);
        PullToRefreshLayout layout = mChatView.getPtrLayout();
        layout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PullToRefreshLayout layout) {
                Log.i("MessageListActivity", "Loading next page");
                loadNextPage();
            }
        });
        // Deprecated, should use onRefreshBegin to load next page
        mAdapter.setOnLoadMoreListener(new MsgListAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore(int page, int totalCount) {
//                Log.i("MessageListActivity", "Loading next page");
//                loadNextPage();
            }
        });

        mChatView.setAdapter(mAdapter);
        mAdapter.getLayoutManager().scrollToPosition(0);
    }

    private void loadNextPage() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                List<MyMessage> list = new ArrayList<>();
//                Resources res = getResources();
//                String[] messages = res.getStringArray(R.array.conversation);
//                for (int i = 0; i < messages.length; i++) {
//                    MyMessage message;
//                    if (i % 2 == 0) {
//                        message = new MyMessage(messages[i], IMessage.MessageType.RECEIVE_TEXT);
//                        message.setUserInfo(new DefaultUser("0", "DeadPool", "R.drawable.deadpool"));
//                    } else {
//                        message = new MyMessage(messages[i], IMessage.MessageType.SEND_TEXT);
//                        message.setUserInfo(new DefaultUser("1", "IronMan", "R.drawable.ironman"));
//                    }
//                    message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
//                    list.add(message);
//                }
//                Collections.reverse(list);
//                mAdapter.addToEnd(list);
//                mChatView.getPtrLayout().refreshComplete();
//            }
//        }, 1500);
    }

    @Override
    public void onKeyBoardStateChanged(int state) {
        switch (state) {
            case ChatInputView.KEYBOARD_STATE_INIT:
                ChatInputView chatInputView = mChatView.getChatInputView();
                if (mImm != null) {
                    mImm.isActive();
                }
                if (chatInputView.getMenuState() == View.INVISIBLE
                        || (!chatInputView.getSoftInputState()
                        && chatInputView.getMenuState() == View.GONE)) {

                    mWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                            | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    chatInputView.dismissMenuLayout();
                }
                break;
        }
    }

    private void scrollToBottom() {
        mAdapter.getLayoutManager().scrollToPosition(0);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (oldh - h > 300) {
            mChatView.setMenuHeight(oldh - h);
        }
        scrollToBottom();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //右滑保存
                mLastTouchX = motionEvent.getX();
                mLastTouchTime = System.currentTimeMillis();

                //输入
                ChatInputView chatInputView = mChatView.getChatInputView();

                if (view.getId() == chatInputView.getInputView().getId()) {

                    if (chatInputView.getMenuState() == View.VISIBLE
                            && !chatInputView.getSoftInputState()) {
                        chatInputView.dismissMenuAndResetSoftMode();
                        return false;
                    } else {
                        return false;
                    }
                }
                if (chatInputView.getMenuState() == View.VISIBLE) {
                    chatInputView.dismissMenuLayout();
                }
                try {
                    View v = getCurrentFocus();
                    if (mImm != null && v != null) {
                        mImm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        mWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                        view.clearFocus();
                        chatInputView.setSoftInputState(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case MotionEvent.ACTION_UP:
                float nowX = motionEvent.getX();
                long nowTime = System.currentTimeMillis();
                WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                float TOUCHACTIONX = wm.getDefaultDisplay().getWidth()/4;
                if(nowTime - mLastTouchTime < TOUCHACTIONTIME){
                    if(nowX - mLastTouchX > TOUCHACTIONX){
                        //满足短时间，移动相应距离，就进行相应
                        quitActivity();
                    }
                }

                break;
        }
        return false;
    }

    private void quitActivity() {
        Log.i("MessageListActivity", "quitActivity->finish");
        finish();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JMessageClient.registerEventReceiver(this);
        unregisterReceiver(mReceiver);
        mSensorManager.unregisterListener(this);
    }

    public static void startMessageListActivity(Activity activity, MessageReciverEntity sender, MessageReciverEntity reciver){
        Log.i("MessageListActivity", "startMessageListActivity->sender:" + sender.getAccount() + "  reciver:" + reciver.getAccount());
        Intent intent = new Intent();
        intent.setClass(activity, MessageListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MessageListActivity.KEY_SENDER, sender);
        bundle.putSerializable(MessageListActivity.KEY_RECIVER, reciver);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    //接受信息
    public void onEvent(MessageEvent event){
        Message msg = event.getMessage();
        Log.i("MessageListActivity", "onEvent->message type:" + msg.getContentType());
        switch (msg.getContentType()){
            case text:
                String who = msg.getFromUser().getNickname();
                Log.i("MessageListActivity", "onEvent->message from:" + who);
                Log.i("MessageListActivity", "onEvent->message from:" + mTarget.getNickname());
                if(who.equals(mTarget.getNickname())){
                    //for data
                    TextContent textContent = (TextContent) msg.getContent();
                    String input = textContent.getText();

                    MyMessage message = new MyMessage(input, IMessage.MessageType.RECEIVE_TEXT);
                    message.setUserInfo(new DefaultUser(mTarget.getAccount(), mTarget.getNickname(), BASE_AVATAR_PATH + mTarget.getAvatar()));
                    message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                    mData.add(message);

                    //for view
                    mAdapter.addToStart(message, true);

                    Log.i("MessageListActivity", "onEvent->message content:" + input);
                }
                break;
        }
    }

}
