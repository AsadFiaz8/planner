package net.eagledev.planner;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import net.eagledev.planner.Activity.AddActionAtivity;
import net.eagledev.planner.Activity.AddAimActivity;
import net.eagledev.planner.Activity.AddReminder;
import net.eagledev.planner.Activity.AddRoutine;
import net.eagledev.planner.Activity.EditActionActivity;
import net.eagledev.planner.Activity.EditAimActivity;
import net.eagledev.planner.Activity.EditRoutineActivity;
import net.eagledev.planner.Adapter.AimAdapter;
import net.eagledev.planner.Adapter.TaskAdapter;
import net.eagledev.planner.Fragment.AccountFragment;
import net.eagledev.planner.Fragment.ActionsFragment;
import net.eagledev.planner.Fragment.AimsFragment;
import net.eagledev.planner.Fragment.ContactFragment;
import net.eagledev.planner.Fragment.RemindersFragment;
import net.eagledev.planner.Fragment.RoutinesFragment;
import net.eagledev.planner.Fragment.SettingsFragment;
import net.eagledev.planner.Fragment.TasksFragment;
import net.eagledev.planner.Interface.ItemClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public final class MainActivity extends AppCompatActivity implements View.OnClickListener, ActionsFragment.OnFragmentInteractionListener, RewardedVideoAdListener, NeedPremiumDialog.NeedPremiumDialogListener {

    public static final String TAG = "MainActivity";
    public static final int CODE_ROUTINES = 0;
    public static final int CODE_ACTIONS = 1;



    private DrawerLayout drawerLayout;
    Calendar data = Calendar.getInstance();
    List<Action> ac = new ArrayList<Action>();
    FloatingActionsMenu floatingActionsMenu;
    FloatingActionButton btnNewAction;
    Dialog taskInfoDialog;
    FloatingActionButton btnNewReminder;
    FloatingActionButton btnAims;
    public static FirestoreDatabase fDatabase = new FirestoreDatabase();
    public static Calendar planNextDayCal;
    Calendar notificationDate = Calendar.getInstance();
    Button button;
    ImageView menuBg;
    public  static boolean needShowMainPage = false;
    Checker checker =  new Checker();
    FloatingActionButton btnLeft;
    FloatingActionButton btnRight;
    public static int[] colors = new int[54];
    public  static int icons[] = {R.drawable.city18, R.drawable.clothes01, R.drawable.clothes17, R.drawable.finance06, R.drawable.finance26, R.drawable.food17, R.drawable.food13, R.drawable.home13, R.drawable.home16, R.drawable.home34, R.drawable.medical11, R.drawable.others18, R.drawable.sports08, R.drawable.sports20, R.drawable.tech02, R.drawable.transport04, R.drawable.transport05, R.drawable.transport09, R.drawable.transport19, R.drawable.ui34, R.drawable.animals02, R.drawable.animals11, R.drawable.city08, R.drawable.clothes23, R.drawable.clothes28, R.drawable.documents03, R.drawable.finance02, R.drawable.finance24, R.drawable.finance53, R.drawable.food01, R.drawable.food05, R.drawable.food06, R.drawable.food08, R.drawable.food10, R.drawable.food12, R.drawable.food15, R.drawable.home01, R.drawable.home05, R.drawable.home09, R.drawable.home10, R.drawable.home11, R.drawable.home20, R.drawable.home36, R.drawable.home38, R.drawable.mail14, R.drawable.medical01, R.drawable.medical03, R.drawable.medical04, R.drawable.medical06, R.drawable.nature01, R.drawable.nature05, R.drawable.nature09, R.drawable.nature12, R.drawable.nature18, R.drawable.others03, R.drawable.others11, R.drawable.others17, R.drawable.others19, R.drawable.others21, R.drawable.others40, R.drawable.phone15, R.drawable.space05, R.drawable.sports01, R.drawable.sports06, R.drawable.sports09, R.drawable.sports12, R.drawable.sports14, R.drawable.sports25, R.drawable.sports27, R.drawable.sports29, R.drawable.tech06, R.drawable.tech09, R.drawable.tech18, R.drawable.tech37, R.drawable.transport06, R.drawable.transport09, R.drawable.transport13, R.drawable.ui03, R.drawable.ui48, R.drawable.ui65};
    int colorGray;
    ImageView clockArrow;
    public String emptyLabel = "432534253425345435345342532453425";
    TextView actionInfo;
    Button btnEdit;
    Button btnEditRoutine;
    Button btn_new_action;
    TextView actionInfoText;
    TextView actionTimeText;
    public static boolean setMainPage = false;
    int selectedID;
    int notificationID = 1;
    float menuBgPos = 800;
    int menuAnimTime = 180;
    ViewGroup.LayoutParams params;
    int bgH;
    int bgW;
    public static RewardedVideoAd VideoAd;
    LinearLayout linearLayout;
    RelativeLayout clockLayout;
    Button btnActions;
    List<Integer> startRoutinesTime = new ArrayList<>();
    List<Integer> stopRoutinesTime = new ArrayList<>();
    List<Aim> aims = new ArrayList<>();
    List<Action> actionList = new ArrayList<>();
    String showDate;
    public static RelativeLayout rl;
    Formatter f = new Formatter();
    public static Toolbar toolbar;
    NavigationView navigationView;
    Intent serviceIntent;
    RecyclerView recyclerView;
    ItemClickListener itemClickListener;
    List<Action> todayActions = new ArrayList<>();
    public static List<Drawable> drawables = new ArrayList<>();
    public static ValueHolder valueHolder;
    public static Context context;
    Animation clockOut;
    Animation clockIn;
    public static SharedPreferences pref;
    private Calendar currentDate;
    private Calendar now = Calendar.getInstance();
    public static AppDatabase appDatabase;
    FragmentManager fragmentManager = getFragmentManager();
    public static FirebaseAnalytics mFirebaseAnalytics;

    PieChart chart;
    Button btn;
    public static boolean needRefresh = false;
    public static FirebaseAuth mAuth;
    public static FirebaseUser currentUser;
    public FirebaseFirestore fdb;


    private final static int REQUEST_CODE_1 = 1;
    private final static int REQUEST_CODE_2 = 2;
    private final static int REQUEST_CODE_TUT = 3;
    public final static int RESULT_CODE_TUT = 4355;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentDate = Calendar.getInstance();
        currentDate.setFirstDayOfWeek(Calendar.MONDAY);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        setupLanguage();
        rl = findViewById(R.id.relative_layout);
        pref = this.getPreferences(Context.MODE_PRIVATE);
        context = getApplicationContext();
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "planner").allowMainThreadQueries().addMigrations(MIGRATION_4_5).build();
        fDatabase = new FirestoreDatabase();
        valueHolder = new ValueHolder();
        if(!valueHolder.isTut()){
            Intent tutIntent = new Intent(context, TutorialActivity.class);
            startActivityForResult(tutIntent, REQUEST_CODE_TUT);
        }
        setupBars();
        ReadData(false);
        getDatabasePath("planner");
        setColors();
        setupPieChartBackground();
        setupPieChart();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        setupList();
        setDate();
        setupUpdate();
        setupMenuBackground();
        setDrawables();
        setNotifications();
        showDate = f.DateForClock(currentDate);
        actionInfo.setText(showDate);
        setButtons();
        setOthers();
        if(valueHolder.isMainNotification()) {
            startService();
        }

        loadAd();
        setNavText();
        aims = appDatabase.appDao().getAimsDateType(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 0);


        //------------------ Tutaj tymczasowo będę wrzucać nowy kod




        if(appDatabase.appDao().getMaxActionID() > 0 && !valueHolder.getFirstBackup()){
            fDatabase.AddActions(appDatabase.appDao().getActions());
            fDatabase.AddRoutines(appDatabase.appDao().getRoutines());
        }
        fDatabase.DownloadActions();
        fDatabase.DownloadRoutines();
        //--------------------------------------------------------
    }

    private void loadAd() {

        VideoAd = MobileAds.getRewardedVideoAdInstance(this);
        VideoAd.setRewardedVideoAdListener(this);
    }

    public void WatchAd(){
        if (VideoAd.isLoaded()) {
            VideoAd.show();

        } else {
            Toast.makeText(context, getString(R.string.sorry_no_ads), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onClick(View view) {
        Context cnt = findViewById(R.id.relative_layout).getContext();
        switch (view.getId()) {

            case R.id.action_1:

                Intent i1 = new Intent(cnt, AddRoutine.class);
                if(appDatabase.appDao().getRoutinesCount() < getResources().getInteger(R.integer.premium_max_routines) || valueHolder.canUsePremium()) {
                    //Spełnia warunki
                    startActivity(i1);
                    floatingActionsMenu.collapse();
                } else {
                    NeedPremiumDialog pd = new NeedPremiumDialog(context, CODE_ROUTINES);
                    pd.ShowDialog(getString(R.string.premium_reason1));
                }


                break;

            case R.id.action_2:
                cnt = findViewById(R.id.relative_layout).getContext();


                Intent i2 = new Intent(cnt, AddTaskActivity.class);
                //Intent i2 = new Intent(cnt, AddAimActivity.class);

                startActivity(i2);
                floatingActionsMenu.collapse();
                break;

            case R.id.action_3:
                cnt = findViewById(R.id.relative_layout).getContext();
                Intent i3 = new Intent(cnt, AddReminder.class);
                startActivity(i3);
                //Toast.makeText(getApplicationContext(), "Przypomnienia zostaną dodane w kolejnej aktualizacji", Toast.LENGTH_SHORT).show();
                floatingActionsMenu.collapse();
                break;

            case R.id.action_4:
                cnt = findViewById(R.id.relative_layout).getContext();
                Intent i4 = new Intent(cnt, AddActionAtivity.class);
                startActivity(i4);
                floatingActionsMenu.collapse();
                break;

            case R.id.btn_left:
                currentDate.add(Calendar.DATE, -1);
                chart.highlightValue(1, 1);
                clockLayout.startAnimation(clockOut);
                ReadData(false);
                setupPieChart();
                setDate();
                switchClockArrow();
                clockLayout.startAnimation(clockIn);
                showDate = f.DateForClock(currentDate);
                actionInfo.setText(showDate);
                chart.setSelected(false);
                break;

            case R.id.btn_right:
                currentDate.add(Calendar.DATE, 1);
                chart.highlightValue(1, 1);
                clockLayout.startAnimation(clockOut);
                ReadData(false);
                setupPieChart();
                setDate();
                switchClockArrow();
                clockLayout.startAnimation(clockIn);
                showDate = f.DateForClock(currentDate);
                actionInfo.setText(showDate);
                chart.setSelected(false);
                break;

            case R.id.btn_edit:
                EditAction(selectedID);
                break;

            case R.id.btn_edit_routine:
                //Edit routine
                Intent intentEditRoutine = new Intent(getApplicationContext(), EditRoutineActivity.class);
                intentEditRoutine.putExtra("ID", selectedID);
                startActivityForResult(intentEditRoutine, REQUEST_CODE_2);
                break;

            case R.id.btn_action_delete:
                appDatabase.appDao().nukeActionsTable();
                break;


            case R.id.btn_new_action:
                cnt = findViewById(R.id.relative_layout).getContext();
                Intent i5 = new Intent(cnt, AddActionAtivity.class);
                startActivity(i5);
                break;

            case R.id.header_layout:
                fragmentManager.beginTransaction().replace(R.id.contnet_frame, new AccountFragment()).commit();
                rl.setVisibility(View.INVISIBLE);
                floatingActionsMenu.setVisibility(View.INVISIBLE);
                toolbar.setTitle(R.string.account);
                drawerLayout.closeDrawers();
                break;

        }

    }

    public void openLogin(){

    }

    private void Update() {
        clockArrow.setRotation((Calendar.getInstance().get(Calendar.HOUR_OF_DAY)*60+Calendar.getInstance().get(Calendar.MINUTE))/4);
        //clockArrow.setRotation(Calendar.getInstance().get(Calendar.SECOND));

        if(!checker.DateEquals(notificationDate, Calendar.getInstance()))
        {
            ReadData(true);
        }
        if(valueHolder.isMainNotification()) {
            setServiceText();
        } else stopService();
        if(needRefresh) {
            Refresh();

        }
        try{
            if(needShowMainPage){
                showMainPage();
            }
        } catch (Exception e){
            Log.e(TAG, e.getMessage());
        }

        if(setMainPage){
            android.app.Fragment frag = new android.app.Fragment();
            fragmentManager.beginTransaction().replace(R.id.contnet_frame, frag).commit();
            Refresh();
            rl.setVisibility(View.VISIBLE);
            floatingActionsMenu.setVisibility(View.VISIBLE);
            toolbar.setTitle(R.string.main_page);
            setMainPage = false;
        }

    }

    private void setupBars() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar.getTitle() != getResources().getString(R.string.planned_actions) && toolbar.getTitle() != getResources().getString(R.string.routines)&& toolbar.getTitle() != getResources().getString(R.string.aims)) {
            toolbar.setTitle(R.string.main_page);
        }


        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.text09);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        drawerLayout.closeDrawers();

                        switch (menuItem.getItemId()) {
                            case R.id.nav_main:
                                android.app.Fragment frag = new android.app.Fragment();
                                fragmentManager.beginTransaction().replace(R.id.contnet_frame, frag).commit();
                                Refresh();
                                rl.setVisibility(View.VISIBLE);
                                floatingActionsMenu.setVisibility(View.VISIBLE);
                                toolbar.setTitle(R.string.main_page);
                                break;
                            case R.id.nav_actions:
                                fragmentManager.beginTransaction().replace(R.id.contnet_frame, new ActionsFragment()).commit();
                                rl.setVisibility(View.INVISIBLE);
                                floatingActionsMenu.setVisibility(View.VISIBLE);
                                toolbar.setTitle(R.string.scheduled_activities);

                                break;
                            case R.id.nav_routines:
                                fragmentManager.beginTransaction().replace(R.id.contnet_frame, new RoutinesFragment()).commit();
                                rl.setVisibility(View.INVISIBLE);
                                floatingActionsMenu.setVisibility(View.VISIBLE);
                                toolbar.setTitle(R.string.routines);

                                break;
                            case R.id.nav_tasks:
                                fragmentManager.beginTransaction().replace(R.id.contnet_frame, new TasksFragment()).commit();
                                rl.setVisibility(View.INVISIBLE);
                                floatingActionsMenu.setVisibility(View.VISIBLE);
                                toolbar.setTitle(R.string.tasks);

                                break;
                            case R.id.nav_reminders:
                                fragmentManager.beginTransaction().replace(R.id.contnet_frame, new RemindersFragment()).commit();
                                rl.setVisibility(View.INVISIBLE);
                                floatingActionsMenu.setVisibility(View.VISIBLE);
                                toolbar.setTitle(R.string.reminders);

                                break;

                            case R.id.nav_settings:
                                fragmentManager.beginTransaction().replace(R.id.contnet_frame, new SettingsFragment()).commit();
                                rl.setVisibility(View.INVISIBLE);
                                toolbar.setTitle(R.string.settings);
                                floatingActionsMenu.setVisibility(View.INVISIBLE);
                                break;

                            case R.id.nav_premium:
                                Intent premiumIntent = new Intent(context, BuyPremiumActivity.class);
                                startActivity(premiumIntent);
                                break;

                            case R.id.nav_contact:
                                fragmentManager.beginTransaction().replace(R.id.contnet_frame, new ContactFragment()).commit();
                                rl.setVisibility(View.INVISIBLE);
                                floatingActionsMenu.setVisibility(View.INVISIBLE);
                                toolbar.setTitle(R.string.contact);


                                break;

                        }


                        return true;
                    }
                });
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {




            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                floatingActionsMenu.collapse();
            }

            @Override
            public void onDrawerClosed(@NonNull View view) {

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    private void setNotifications() {

        Intent nIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        nIntent.putExtra("ID", -1);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(MainActivity.this, 0, nIntent, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);

        planNextDayCal = Calendar.getInstance();
        planNextDayCal.setTimeInMillis(System.currentTimeMillis());
        planNextDayCal.set(Calendar.HOUR_OF_DAY, 20);
        planNextDayCal.set(Calendar.MINUTE, 0);
        planNextDayCal.set(Calendar.SECOND, 0);

        alarm.setRepeating(AlarmManager.RTC_WAKEUP, planNextDayCal.getTimeInMillis(),
                1000 * 60 * 60 * 24, alarmIntent);

    }

    private void setButtons() {
        floatingActionsMenu = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        setFloatingButtonControls();
        btnNewAction = (FloatingActionButton) findViewById(R.id.action_4);
        btnNewAction.setOnClickListener(this);
        btnNewReminder = (FloatingActionButton) findViewById(R.id.action_3);
        btnNewReminder.setOnClickListener(this);
        btnAims = (FloatingActionButton) findViewById(R.id.action_2);
        btnAims.setOnClickListener(this);
        findViewById(R.id.action_1).setOnClickListener(this);
        btnLeft = (FloatingActionButton) findViewById(R.id.btn_left);
        btnLeft.setOnClickListener(this);
        btnRight = (FloatingActionButton) findViewById(R.id.btn_right);
        btnRight.setOnClickListener(this);
        btnEdit = findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(this);
        btnEditRoutine = findViewById(R.id.btn_edit_routine);
        btnEditRoutine.setOnClickListener(this);
        menuBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionsMenu.collapse();
            }
        });
        btnActions = findViewById(R.id.btn_action_delete);
        btnActions.setOnClickListener(this);
        btn = findViewById(R.id.my_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appDatabase.appDao().nukeRoutinesTable();
            }
        });
        btn_new_action = findViewById(R.id.btn_new_action);
        btn_new_action.setOnClickListener(this);
        actionInfoText = findViewById(R.id.action_name);
        actionTimeText = findViewById(R.id.action_time);
    }

    private void setColors() {
        int alp = 200;
        colors[0] = Color.argb(alp, 255, 0, 0);     //RED
        colors[1] = Color.argb(alp, 0, 255, 0);     //GREEN
        colors[2] = Color.argb(alp, 0, 0, 255);     //BLUE
        colors[3] = Color.argb(alp, 0, 255, 255);   // Błękitny
        colors[4] = Color.argb(alp, 255, 255, 0);   // YELLOW
        colors[5] = Color.argb(alp, 255, 0, 255);   // Różowy
//Green
        colors[6] = Color.argb(alp, 0, 58, 0);
        colors[7] = Color.argb(alp,1, 86, 0)  ;
        colors[8] = Color.argb(alp,1, 117, 0)  ;
        colors[9] = Color.argb(alp,1, 153, 0)  ;
        colors[10] = Color.argb(alp, 1, 200, 0) ;
        colors[11] = Color.argb(alp,13, 255, 12)  ;
//Yellow
        colors[12] = Color.argb(alp,67, 68, 0)  ;
        colors[13] = Color.argb(alp, 90, 91, 0) ;
        colors[14] = Color.argb(alp, 121, 122, 0) ;
        colors[15] = Color.argb(alp, 159, 160, 0) ;
        colors[16] = Color.argb(alp, 205, 206, 0) ;
        colors[17] = Color.argb(alp, 255,255,0) ;
//Orange
        colors[18] = Color.argb(alp,76, 28, 0)  ;
        colors[19] = Color.argb(alp, 112, 41, 0) ;
        colors[20] = Color.argb(alp,140, 51, 0)  ;
        colors[21] = Color.argb(alp, 183, 66, 0) ;
        colors[22] = Color.argb(alp, 229, 82, 0)  ;
        colors[23] = Color.argb(alp, 255, 91, 0) ;
//Red
        colors[24] = Color.argb(alp, 61, 0, 0) ;
        colors[25] = Color.argb(alp, 96, 0, 0) ;
        colors[26] = Color.argb(alp, 132, 0, 0) ;
        colors[27] = Color.argb(alp,  175, 0, 0);
        colors[28] = Color.argb(alp,  211, 0, 0);
        colors[29] = Color.argb(alp,  255, 0, 0);
//Pink
        colors[30] = Color.argb(alp, 61, 0, 49) ;
        colors[31] = Color.argb(alp, 99, 0, 79) ;
        colors[32] = Color.argb(alp, 137, 0, 109) ;
        colors[33] = Color.argb(alp,173, 0, 137)  ;
        colors[34] = Color.argb(alp, 214, 0, 169) ;
        colors[35] = Color.argb(alp, 255, 0, 201) ;
//Violet
        colors[36] = Color.argb(alp, 60, 0, 68) ;
        colors[37] = Color.argb(alp, 90, 0, 102) ;
        colors[38] = Color.argb(alp, 119, 0, 135) ;
        colors[39] = Color.argb(alp, 155, 0, 175) ;
        colors[40] = Color.argb(alp, 202, 0, 229) ;
        colors[41] = Color.argb(alp,  230, 48, 255);
//Blue
        colors[42] = Color.argb(alp, 0, 31, 79) ;
        colors[43] = Color.argb(alp, 0, 45, 114) ;
        colors[44] = Color.argb(alp,0, 61, 155)  ;
        colors[45] = Color.argb(alp,  0, 74, 188);
        colors[46] = Color.argb(alp, 0, 89, 226) ;
        colors[47] = Color.argb(alp, 10, 106, 255) ;
//Navy
        colors[48] = Color.argb(alp, 0, 66, 73) ;
        colors[49] = Color.argb(alp, 0, 96, 107) ;
        colors[50] = Color.argb(alp, 0, 125, 140) ;
        colors[51] = Color.argb(alp, 0, 159, 178) ;
        colors[52] = Color.argb(alp, 0, 200, 224) ;
        colors[53] = Color.argb(alp, 20, 229, 255) ;

        int grayValue = 100;
        colorGray = Color.argb(alp, grayValue, grayValue, grayValue);
    }

    private void setFloatingButtonControls() {
        this.floatingActionsMenu = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        this.floatingActionsMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                //backgroundDimmer.setVisibility(View.VISIBLE);
                params.height = bgH;
                params.width = bgW;
                linearLayout.setLayoutParams(params);
                ObjectAnimator animatorY = ObjectAnimator.ofFloat(menuBg, "y", 0);
                animatorY.setDuration(menuAnimTime);
                ObjectAnimator anim = ObjectAnimator.ofFloat(menuBg, "scaleY", 1);
                anim.setDuration(menuAnimTime);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(anim, animatorY);
                animatorSet.start();
                HideButtons();
            }

            @Override
            public void onMenuCollapsed() {
                //backgroundDimmer.setVisibility(View.GONE);
                params.height = 0;
                params.width = 0;
                linearLayout.setLayoutParams(params);
                ObjectAnimator animatorY = ObjectAnimator.ofFloat(menuBg, "y", menuBgPos);
                animatorY.setDuration(menuAnimTime);
                ObjectAnimator anim = ObjectAnimator.ofFloat(menuBg, "scaleY", 0);
                anim.setDuration(menuAnimTime);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(anim, animatorY);
                animatorSet.start();
            }
        });
    }

    private void setupPieChart() {
        final List<PieEntry> pieEntries = new ArrayList<>();
        List<Integer> pieColors = new ArrayList<>();
        int t = 0;
        boolean isRoutine = false;

        if(ac.size() > 0) {
            Action currentAction = ac.get(0);
            int currentActionNumber = 0;
            int actionTime = 24*60;
            //Powtarzaj dopki czas nie osiągie 24h
            while(t<24*60) {
                if(!ac.isEmpty()) {
                    for (int i = 0; i<ac.size(); i++) {
                        if (actionTime > ac.get(i).getStartMinutes()) {
                            actionTime = ac.get(i).getStartMinutes();
                            currentAction = ac.get(i);
                            currentActionNumber = i;
                        }
                    } ac.remove(currentActionNumber);
                    isRoutine = false;
                    //Sprawdzanie akcja czy rutyna
                    if(startRoutinesTime.size() > 0) {
                        for (int r = 0; startRoutinesTime.size()>r ; r++) {
                            if (startRoutinesTime.get(r) == currentAction.getStartMinutes() && stopRoutinesTime.get(r) == currentAction.getStopMinutes()){
                                isRoutine = true;
                            }
                        }
                    }
                    String acID = String.valueOf(currentAction.getId());
                    if(isRoutine) {
                        acID="r"+String.valueOf(currentAction.getId());
                    }
                    if(currentAction.getStartMinutes() > t) {
                        pieEntries.add(new PieEntry(currentAction.getStartMinutes()-t,emptyLabel));
                        pieColors.add(colorGray);
                        if(currentAction.getStopMinutes()-currentAction.getStartMinutes()>90){
                            pieEntries.add(new PieEntry(currentAction.getTime(), acID,getDrawable(currentAction.getIcon())));
                        } else {
                            //Czas trwania mniejszy niż 90 minut

                            //Drawable d = getDrawable(currentAction.getIcon());
                            if(currentAction.getStopMinutes()-currentAction.getStartMinutes()>45){
                                pieEntries.add(new PieEntry(currentAction.getTime(), acID, scaleDrawable(currentAction.getIcon(),50)));
                            } else {
                                pieEntries.add(new PieEntry(currentAction.getTime(), acID));
                            }
                        }



                        pieColors.add(currentAction.getColor());
                        t=currentAction.getStopMinutes();
                        actionTime = 24*60;
                    } else {
                        if(currentAction.getStopMinutes()-currentAction.getStartMinutes()>90){
                            pieEntries.add(new PieEntry(currentAction.getTime(), acID,getDrawable(currentAction.getIcon())));
                        } else {
                            //Czas trwania mniejszy niż 90 minut
                            if(currentAction.getStopMinutes()-currentAction.getStartMinutes()>45){
                                pieEntries.add(new PieEntry(currentAction.getTime(), acID, scaleDrawable(currentAction.getIcon(),50)));
                            } else {
                                pieEntries.add(new PieEntry(currentAction.getTime(), acID));
                            }
                        }
                        pieColors.add(currentAction.getColor());
                        t=currentAction.getStopMinutes();
                        actionTime = 24*60;
                    }
                } else {
                    pieEntries.add(new PieEntry(24*60-t, emptyLabel));
                    pieColors.add(colorGray);
                    t=24*60;
                }
            }
        }
        else {
            pieEntries.add(new PieEntry(24*60,emptyLabel));
            pieColors.add(colorGray);
            t=24*60;
        }
        PieDataSet dataSet = new PieDataSet(pieEntries, "Test");
        int color[] = new int[pieColors.size()];
        for (int i = 0; i<pieColors.size(); i++) {
            color[i] = pieColors.get(i);
        }
        dataSet.setColors(color);
        dataSet.setValueTextSize(0);
        dataSet.setSliceSpace(2);
        PieData data = new PieData(dataSet);
        //Get the chart
        chart = (PieChart) findViewById(R.id.chart);
        chart.setHighlightPerTapEnabled(true);
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // pobieranie actions z danego dnia
                //sprawdzanie czy nazwa oraz długość akcji w minutach są takie same jak pie entry
                PieEntry pe = (PieEntry) e;
                String s = pe.getLabel();
                HideButtons();
                if(!s.equals(emptyLabel)){
                    if(s.charAt(0)=='r') {
                        s = s.replaceFirst("r","");
                        Routine selectedRoutine = appDatabase.appDao().idRoutine(Integer.parseInt(s));
                        selectedID = Integer.parseInt(s);
                        actionTimeText.setText(f.Time(selectedRoutine.getStart())+"\n"+f.Time(selectedRoutine.getStop()));
                        actionTimeText.setVisibility(View.VISIBLE);
                        actionInfoText.setText(selectedRoutine.getName());
                        actionInfoText.setVisibility(View.VISIBLE);
                        btnEditRoutine.setVisibility(View.VISIBLE);
                        btnEdit.setVisibility(View.INVISIBLE);

                    } else {
                        Action selectedAction = appDatabase.appDao().idAction(Integer.parseInt(s));
                        selectedID = Integer.parseInt(s);
                        actionTimeText.setText(f.Time(selectedAction.getStart())+"\n"+f.Time(selectedAction.getStop()));
                        actionTimeText.setVisibility(View.VISIBLE);
                        actionInfoText.setText(selectedAction.getDesc());
                        actionInfoText.setVisibility(View.VISIBLE);
                        btnEdit.setVisibility(View.VISIBLE);
                        btnEditRoutine.setVisibility(View.INVISIBLE);
                    }
                } else {
                    actionInfo.setText(showDate);
                    HideButtons();
                    btn_new_action.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected() {
                actionInfo.setText(showDate);
                HideButtons();
            }
        });
        chart.setDragDecelerationEnabled(false);
        chart.setRotationEnabled(false);
        chart.setDrawSliceText(false);
        chart.setData(data);
        chart.animateY(0);
        chart.setTransparentCircleRadius(0f);
        Description desc = new Description();
        desc.setText("");
        chart.setDescription(desc);
        chart.invalidate();
        Legend legend = chart.getLegend();
        legend.setEnabled(false);

        //Setup clock
        clockArrow = findViewById(R.id.clock);
        actionInfo = findViewById(R.id.action_info);
        actionInfo.setText(showDate);
    }

    private void setupPieChartBackground() {
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            pieEntries.add(new PieEntry(60));
        }
        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        int c = 100;
        dataSet.setColor(Color.argb(255, c, c, c));
        dataSet.setValueTextSize(0);
        dataSet.setSliceSpace(2);
        PieData data = new PieData(dataSet);
        //Get the chart
        PieChart bgchart = (PieChart) findViewById(R.id.chartBg);
        bgchart.setDragDecelerationEnabled(false);
        bgchart.setDrawSliceText(false);
        bgchart.setData(data);
        bgchart.animateY(0);
        bgchart.setRotationEnabled(false);
        bgchart.setTransparentCircleRadius(0f);
        bgchart.setCenterText("Test");
        Description desc = new Description();
        desc.setText("");
        bgchart.setDescription(desc);
        bgchart.invalidate();
        Legend legend = bgchart.getLegend();
        legend.setEnabled(false);
    }

    private void ReadData(boolean notify) {
        if(appDatabase == null){
            appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "planner").allowMainThreadQueries().build();

        }
        startRoutinesTime.clear();
        stopRoutinesTime.clear();
        ac.clear();
        boolean today = false;
        //Log.e("date equals", String.valueOf(checker.DateEquals(currentDate, Calendar.getInstance())));

        Calendar c = Calendar.getInstance();
        if(checker.DateEquals(currentDate, Calendar.getInstance())){
            today = true;
            actionList.clear();
        }
        if(!notify){
            todayActions= appDatabase.appDao().getActionsFromDay(currentDay(), currentMonth(), currentYear());
        } else {
            actionList.clear();
            try {
                todayActions= appDatabase.appDao().getActionsFromDay(c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH), c.get(Calendar.YEAR));
            } catch (Exception e)
            {
                Log.e("MainActivity", e.getMessage());
                Toast.makeText(context, getString(R.string.error_loading_activities), Toast.LENGTH_LONG).show();
            }


        }

        for(int i = 0; i<todayActions.size(); i++) {
            ac.add(todayActions.get(i));
            if(today || notify){
                actionList.add(todayActions.get(i));
            }

        }
        List<Routine> routineAdapter = new ArrayList<>();
        currentDate.setFirstDayOfWeek(Calendar.MONDAY);
        switch (currentDate.get(Calendar.DAY_OF_WEEK)){
            case 2:
                routineAdapter = appDatabase.appDao().getMonday();
                break;
            case 3:
                routineAdapter = appDatabase.appDao().getTuesday();
                break;
            case 4:
                routineAdapter = appDatabase.appDao().getWednesday();
                break;
            case 5:
                routineAdapter = appDatabase.appDao().getThursday();
                break;
            case 6:
                routineAdapter = appDatabase.appDao().getFriday();
                break;
            case 7:
                routineAdapter = appDatabase.appDao().getSaturday();
                break;
            case 1:
                routineAdapter = appDatabase.appDao().getSunday();
                break;
        }
        for(int i = 0; i<routineAdapter.size(); i++) {
            Routine r = routineAdapter.get(i);
            Calendar start =  r.getStart();
            int startR = start.get(Calendar.HOUR_OF_DAY)*60+start.get(Calendar.MINUTE);
            Calendar stop = r.getStop();
            int stopR = stop.get(Calendar.HOUR_OF_DAY)*60+stop.get(Calendar.MINUTE);
            boolean createAction = true;
            int startM = 0;
            int startH = 0;
            int stopM = 0;
            int stopH = 0;
            for (int l = 0; l<ac.size(); l++) {

                //Funkcja sprawdz tylko kolizję z jedną akcją

                int startA = ac.get(l).getStart_hour()*60+ac.get(l).getStart_minute();
                int stopA = ac.get(l).getStop_hour()*60+ac.get(l).getStop_minute();
                //Nie koliduje
                if(stopA <= startR || startA >= stopR) {
                    //Log.e(r.getName(),"Nie koliduje z "+ac.get(l).getDesc());
                }
                //Koliduje na początku
                if(stopA > startR && stopA < stopR && startA < startR) {
                    //Log.e(r.getName(),"Koliduje na początku z "+ac.get(l).getDesc());
                    startR = stopA;
                }
                //Koliduje na końcu
                if(startA < stopR && startA > startR && stopA > stopR) {
                    //Log.e(r.getName(),"Koliduje na końcu z "+ac.get(l).getDesc());
                    stopR = startA;
                }
                //Kolidujee wewnątrz
                if(startA >= startR && stopA <= stopR) {
                    //Log.e(r.getName(),"Kolidujee wewnątrz z "+ac.get(l).getDesc());
                    if(startA == startR) {
                        startR = stopA;
                    }
                    if(stopA == stopR) {
                        stopR = startA;
                    }
                    if(startA == startR && stopA == stopR || startA > startR && stopA < stopR){
                        createAction = false;
                    }
                }
                //Całkowicie koliduje
                if(startA <= startR && stopA >= stopR) {
                    //Log.e(r.getName(),"Całkowicie koliduje z "+ac.get(l).getDesc());
                    createAction = false;
                }
            }
            boolean test = true;
            if (createAction && test) {

                while(startR > 0) {
                    if(startR>=60) {
                        startR -= 60;
                        startH++;
                    } else {
                        startM = startR;
                        startR = 0;
                    }
                }
                while (stopR > 0) {
                    if(stopR>=60) {
                        stopR -=60;
                        stopH++;
                    } else {
                        stopM = stopR;
                        stopR = 0;
                    }
                }
                Calendar startTimeR = Calendar.getInstance();
                startTimeR.set(Calendar.HOUR_OF_DAY, startH);
                startTimeR.set(Calendar.MINUTE, startM);
                Calendar stopTimeR = Calendar.getInstance();
                stopTimeR.set(Calendar.HOUR_OF_DAY, stopH);
                stopTimeR.set(Calendar.MINUTE, stopM);
                //Log.e(r.getName(), "Crating action from routine");
                Action a = new Action(r.getId(), r.getName(), startTimeR, stopTimeR, r.getIcon(), r.getColor());
                if(!notify){

                    startRoutinesTime.add(a.getStartMinutes());
                    stopRoutinesTime.add(a.getStopMinutes());
                    ac.add(a);
                }

                if (today || notify) {
                    actionList.add(a);
                }

            }
        }

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appDatabase.appDao().nukeActionsTable();

            }
        });
    }

    private void setDate(){

        data.set(getTime(Calendar.YEAR),getTime(Calendar.MONTH)+1, getTime(Calendar.DATE), getTime(Calendar.HOUR_OF_DAY), getTime(Calendar.MINUTE));
        TextView date = findViewById(R.id.date);
        date.setText(data.get(Calendar.YEAR) +" "+ data.get(Calendar.MONTH) + " " + data.get(Calendar.DATE));
    }

    private int getTime(int cal){
        return Calendar.getInstance().get(cal);
    }

    public int currentDay() {
        return currentDate.get(Calendar.DAY_OF_MONTH);
    }

    public int currentMonth() {
        return currentDate.get(Calendar.MONTH);
    }

    public int currentYear() {
        return currentDate.get(Calendar.YEAR);
    }

    public void switchClockArrow() {
        if(currentDate.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH) && currentDate.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH) && currentDate.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR))
        {
            clockArrow.setVisibility(View.VISIBLE);
        } else clockArrow.setVisibility(View.INVISIBLE);
    }

    private void setupUpdate() {
        Thread thread = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Update();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        thread.start();
    }

    private void setupMenuBackground() {
        linearLayout = findViewById(R.id.bg_layout);
        params = linearLayout.getLayoutParams();
        bgH = params.height;
        bgW = params.width;
        params.height = 0;
        params.width = 0;
        linearLayout.setLayoutParams(params);

        menuBg = findViewById(R.id.menu_background);
        menuBg.setVisibility(View.VISIBLE);
        menuBg.setMaxHeight(0);
        menuBg.setMaxWidth(0);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(menuBg, "y", menuBgPos);
        animatorY.setDuration(0);
        ObjectAnimator anim = ObjectAnimator.ofFloat(menuBg, "scaleY", 0);
        anim.setDuration(0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(anim, animatorY);
        animatorSet.start();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        android.app.Fragment frag = new android.app.Fragment();
        fragmentManager.beginTransaction().replace(R.id.contnet_frame, frag).commit();
        rl.setVisibility(View.VISIBLE);
        toolbar.setTitle(R.string.main_page);
    }

    public void CloseActionFragment() {
        fragmentManager.beginTransaction().replace(R.id.contnet_frame, new ActionsFragment()).commit();

        rl.setVisibility(View.INVISIBLE);
    }

    public void EditAction(int id) {
        Intent intentEdit = new Intent(getApplicationContext(), EditActionActivity.class);
        intentEdit.putExtra("ID", id);
        startActivityForResult(intentEdit, REQUEST_CODE_1);
    }

    public void HideButtons(){
        btnEdit.setVisibility(View.INVISIBLE);
        btnEditRoutine.setVisibility(View.INVISIBLE);
        btn_new_action.setVisibility(View.INVISIBLE);
        actionInfoText.setVisibility(View.INVISIBLE);
        actionTimeText.setVisibility(View.INVISIBLE);
    }

    private void setDrawables() {
        drawables.add(getDrawable(R.drawable.ui21));
        drawables.add(getDrawable(R.drawable.ui96));
    }

    private void setupList() {
        List<Task> dayTaskList = appDatabase.appDao().getTaskDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        List<Task> repeatTaskList = appDatabase.appDao().getTasksRepeatType(1);
        List<Task> todayRepeatTypDay = new ArrayList<Task>();
        now.setFirstDayOfWeek(Calendar.MONDAY);
        int dayOfWeek = now.get(Calendar.DAY_OF_WEEK);
        dayOfWeek -=1;
        if (dayOfWeek<1){
            dayOfWeek = 7;
        }
        for (int i = 0; i< repeatTaskList.size(); i++){
            if(repeatTaskList.get(i).getDays().charAt(dayOfWeek-1) == '1'){
                repeatTaskList.get(i).setUsingDate(now);
                todayRepeatTypDay.add(repeatTaskList.get(i));
            }
        }
        List<Task> todayRepeatTypInterval = appDatabase.appDao().getTasksRepeatType(2);
        List<Task> allTaskLists = new ArrayList<>();
        for (int i = 0 ; i<dayTaskList.size(); i++){
            boolean isExist = false;
            for (int l = 0; l<allTaskLists.size(); l++){
                if(dayTaskList.get(i).getId() == allTaskLists.get(l).getId()){
                    //Sparwdzanie czy zadanie o danym id już zostało dodane
                    isExist = true;
                }
            }
            if (!isExist){
                dayTaskList.get(i).setUsingDate(now);
                allTaskLists.add(dayTaskList.get(i));
            }
        }
        for (int i = 0 ; i<todayRepeatTypDay.size(); i++){
            boolean isExist = false;
            for (int l = 0; l<allTaskLists.size(); l++){
                if(dayTaskList.get(i).getId() == allTaskLists.get(l).getId()){
                    //Sparwdzanie czy zadanie o danym id już zostało dodane
                    isExist = true;
                }
            }
            if (!isExist){
                todayRepeatTypDay.get(i).setUsingDate(now);
                allTaskLists.add(todayRepeatTypDay.get(i));
            }


            //allTaskLists.add(todayRepeatTypDay.get(i));
        }
        for (int i = 0 ; i<todayRepeatTypInterval.size(); i++){
            Task task = todayRepeatTypInterval.get(i);
            Calendar tCal = Calendar.getInstance();
            tCal.setTimeInMillis(task.getTime());
            if(task.getTime_type() == 0){
                //Dni
                if(Math.abs((tCal.getTimeInMillis()-now.getTimeInMillis())/86400000) % task.getRepeat_gap() == 0){
                    boolean isExist = false;
                    for (int l = 0; l<allTaskLists.size(); l++){
                        if(task.getId() == allTaskLists.get(l).getId()){
                            //Sparwdzanie czy zadanie o danym id już zostało dodane
                            isExist = true;
                        }
                    }
                    if (!isExist){
                        task.setUsingDate(now);
                        allTaskLists.add(task);
                    }
                }
            }
            if(task.getTime_type() == 1){
                //Tygodnie
                if(Math.abs((tCal.getTimeInMillis()-now.getTimeInMillis())/604800000) % task.getRepeat_gap() == 0){
                    boolean isExist = false;
                    for (int l = 0; l<allTaskLists.size(); l++){
                        if(task.getId() == allTaskLists.get(l).getId()){
                            //Sparwdzanie czy zadanie o danym id już zostało dodane
                            isExist = true;
                        }
                    }
                    if (!isExist){
                        task.setUsingDate(now);
                        allTaskLists.add(task);
                    }
                }
            }
            if(task.getTime_type() == 2){
                //Miesiące
                if(((now.get(Calendar.YEAR)*12+now.get(Calendar.MONTH))-(tCal.get(Calendar.YEAR)*12+tCal.get(Calendar.MONTH))) % task.getRepeat_gap() == 0){
                    boolean isExist = false;
                    for (int l = 0; l<allTaskLists.size(); l++){
                        if(task.getId() == allTaskLists.get(l).getId()){
                            //Sparwdzanie czy zadanie o danym id już zostało dodane
                            isExist = true;
                        }
                    }
                    if (!isExist){
                        task.setUsingDate(now);
                        allTaskLists.add(task);
                    }
                }
            }
        }
        final List<Task> taskList = allTaskLists;
        //final List<Task> taskList = appDatabase.appDao().getTasks();
        TextView aimNullText = findViewById(R.id.aims_null_text);
        if(taskList.size() == 0) {
            aimNullText.setVisibility(View.VISIBLE);
        } else aimNullText.setVisibility(View.INVISIBLE);
        recyclerView = (RecyclerView) findViewById(R.id.aims_main_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ItemClickListener longListener;
        longListener = new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                final Task task = taskList.get(position);
                taskInfoDialog = new Dialog(MainActivity.this);
                taskInfoDialog.setTitle("Task info");
                taskInfoDialog.setContentView(R.layout.dialog_task_info);
                taskInfoDialog.show();
                TextView name = taskInfoDialog.findViewById(R.id.dialog_task_info_name);
                name.setText(task.getName());
                TextView date = taskInfoDialog.findViewById(R.id.dialog_task_info_date);
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(task.getTime());
                date.setText(f.Date(c));
                TextView repeating = taskInfoDialog.findViewById(R.id.dialog_task_info_repeating);
                switch (task.getRepeat_type()){
                    case 0:
                        repeating.setText("Brak");
                        break;
                    case 2:
                        String timeType="";
                        if(task.time_type==0) timeType = "dni";
                        if(task.time_type==1) timeType = "tygodnie";
                        if(task.time_type==2) timeType = "miesiące";
                        repeating.setText("Co "+task.getRepeat_gap()+" "+timeType);
                        break;
                    case 1:
                        String repeatDays = "";
                        if(task.getDays().charAt(0)=='1') repeatDays = repeatDays + " " +getString(R.string.mo);
                        if(task.getDays().charAt(1)=='1') repeatDays = repeatDays + " " +getString(R.string.tu);
                        if(task.getDays().charAt(2)=='1') repeatDays = repeatDays + " " +getString(R.string.we);
                        if(task.getDays().charAt(3)=='1') repeatDays = repeatDays + " " +getString(R.string.th);
                        if(task.getDays().charAt(4)=='1') repeatDays = repeatDays + " " +getString(R.string.fr);
                        if(task.getDays().charAt(5)=='1') repeatDays = repeatDays + " " +getString(R.string.sa);
                        if(task.getDays().charAt(6)=='1') repeatDays = repeatDays + " " +getString(R.string.su);


                        if (task.getDays().charAt(0)=='1' && task.getDays().charAt(1)=='1' && task.getDays().charAt(2)=='1' && task.getDays().charAt(3)=='1' && task.getDays().charAt(4)=='1'){
                            repeatDays = getString(R.string.work_days);
                        }
                        if (task.getDays().charAt(5)=='1' && task.getDays().charAt(6)=='1'){
                            repeatDays = getString(R.string.weekends);
                        }
                        if (task.getDays().charAt(0)=='1' && task.getDays().charAt(1)=='1' && task.getDays().charAt(2)=='1' && task.getDays().charAt(3)=='1' && task.getDays().charAt(4)=='1' && task.getDays().charAt(5)=='1' && task.getDays().charAt(6)=='1'){
                            repeatDays = getString(R.string.everyday);
                        }
                        repeating.setText(repeatDays);
                        break;
                }




                final TextView comment = taskInfoDialog.findViewById(R.id.dialog_task_info_comment);
                comment.setText(task.getComment());
                TextView label = taskInfoDialog.findViewById(R.id.dialog_task_info_label);
                label.setText(task.getLabel());
                TextView completed = taskInfoDialog.findViewById(R.id.dialog_task_info_completed);
                if (task.getRepeat_type()>0){
                    if (task.isCompleted()){
                        completed.setText("Ostatni ukończony z dnia "+f.Date(task.CompletedTime()));
                    } else {
                        completed.setText("Nie");
                    }
                } else {
                    if (task.isCompleted()){
                        completed.setText("Tak");
                    } else {
                        completed.setText("Nie");
                    }
                }

                Button button = taskInfoDialog.findViewById(R.id.dialog_task_info_button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent editTaskIntent = new Intent(MainActivity.this, AddTaskActivity.class);
                        editTaskIntent.putExtra("ID", task.getId());
                        editTaskIntent.putExtra("edit", true);
                        startActivity(editTaskIntent);
                        taskInfoDialog.dismiss();
                    }
                });
            }
        };
        itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {

                Task task = taskList.get(position);
                task.setCompleted(!task.isCompleted());
                ImageView imageButton = view.findViewById(R.id.task_list_button);
                if (task.getRepeat_type()>0){
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(task.getCompletedTime());
                    if (checker.Before(calendar, now)){
                        task.setCompletedTime(now.getTimeInMillis());
                        imageButton.setImageDrawable(getDrawable(R.drawable.ui21));
                    } else {
                        task.setCompletedTime(now.getTimeInMillis()-86400000);
                        imageButton.setImageDrawable(getDrawable(R.drawable.ui96));
                    }
                } else {
                    if(task.isCompleted()) {
                        imageButton.setImageDrawable(getDrawable(R.drawable.ui21));
                    }else {
                        imageButton.setImageDrawable(getDrawable(R.drawable.ui96));
                    }
                }

                appDatabase.appDao().updateTask(task);

            }
        };
        TaskAdapter adapter = new TaskAdapter(getApplicationContext(), taskList, itemClickListener, longListener);
        recyclerView.setAdapter(adapter);
    }

    public void Refresh(){
        Log.e(TAG,"Refresh");
        setupBars();
        ReadData(false);
        setupPieChart();
        setupList();
        showDate = f.DateForClock(currentDate);
        setNavText();
        needRefresh = false;
    }

    public void startService(){
        if(serviceIntent == null) {
            serviceIntent = new Intent(this, NotificationService.class);
        }
        serviceIntent.putExtra("tittle", getResources().getString(R.string.no_scheduled_activity));
        serviceIntent.putExtra("text",getResources().getString(R.string.add_now));
        try {
            startService(serviceIntent);
        } catch (Exception e){
            Log.e(TAG, "startservice()   "+e.getMessage());
        }



    }

    public void setServiceText() {
        String tittle = "";
        String text = "";
        int currentTimeMinutes = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)*60+Calendar.getInstance().get(Calendar.MINUTE);
        boolean isEmpty = true;
        for (int i = 0; i < actionList.size(); i++) {
            if(actionList.get(i).getStartMinutes() <= currentTimeMinutes && actionList.get(i).getStopMinutes() >= currentTimeMinutes) {
                isEmpty = false;
                tittle = actionList.get(i).getDesc();
                text = f.Time(actionList.get(i).getStart()) + " - " + f.Time(actionList.get(i).getStop());
            }
        }
        if (isEmpty) {
            tittle = getResources().getString(R.string.no_scheduled_activity);
            text = getResources().getString(R.string.add_now);
        }
        if(serviceIntent == null) {
            serviceIntent = new Intent(this, NotificationService.class);
        }
        serviceIntent.putExtra("tittle", tittle);
        serviceIntent.putExtra("text", text);
        try {
            startService(serviceIntent);
        } catch (Exception e){
            Log.e(TAG, "setServiceText()   "+e.getMessage());
        }



    }

    public void stopService() {
        Intent serviceIntent = new Intent(this, NotificationService.class);
        stopService(serviceIntent);
    }

    private void setupLanguage() {
        String lang;
        switch (Locale.getDefault().getLanguage()) {

            case "pl":
                lang = "pl";
                break;
            default:
                lang = "en";
                break;
        }
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    private Drawable scaleDrawable(int drawable, int scale){
        Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(drawable)).getBitmap();
        return new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, scale, scale, true));
    }

    public void showMainPage(){
        android.app.Fragment frag = new android.app.Fragment();
        fragmentManager.beginTransaction().replace(R.id.contnet_frame, frag).commit();
        rl.setVisibility(View.VISIBLE);
        toolbar.setTitle(R.string.main_page);
        needShowMainPage = false;
    }

    private void scheduleJob(){

        ComponentName componentName = new ComponentName(this,NotificationJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(1, componentName)
                .setRequiresCharging(false)
                .setPersisted(true)
                .setOverrideDeadline(1000)
                .build();
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = jobScheduler.schedule(jobInfo);
        if(resultCode == JobScheduler.RESULT_SUCCESS){
            Log.d("JobScheduler ", "Job Scheduled");
        } else {
            Log.d("JobScheduler ", "Job scheduling failed");
        }
    }

    private void cancelJob(View view){

        JobScheduler jobScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(1);
        Log.d("JobScheduler ", "Job cancelled");

    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static void setValueHolder(){
        valueHolder = new ValueHolder();
    }

    private void setNavText() {
        TextView navText = navigationView.getHeaderView(0).findViewById(R.id.nav_header);
        if (currentUser != null){
            navText.setText(currentUser.getDisplayName());
        }
        else {
            if(navText != null){
                navText.setText(getResources().getString(R.string.sign_in));
            }
        }
        navigationView.getHeaderView(0).findViewById(R.id.header_layout).setOnClickListener(this);
    }


    private void setOthers() {
        clockOut = AnimationUtils.loadAnimation(this, R.anim.clock_out);
        clockIn = AnimationUtils.loadAnimation(this, R.anim.clock_in);
        clockLayout = findViewById(R.id.clock_layout);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CODE_TUT){
            fragmentManager.beginTransaction().replace(R.id.contnet_frame, new AccountFragment()).commit();
            rl.setVisibility(View.INVISIBLE);
            floatingActionsMenu.setVisibility(View.INVISIBLE);
            toolbar.setTitle(R.string.account);
            drawerLayout.closeDrawers();
        }
    }


    static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

            // Create the new table
            database.execSQL(
                    "CREATE TABLE tasks (id INTEGER NOT NULL, time_type INTEGER NOT NULL, reminder INTEGER NOT NULL, year INTEGER NOT NULL, repeat_type INTEGER NOT NULL, completed INTEGER NOT NULL, priority INTEGER NOT NULL, minute INTEGER NOT NULL, repeat_gap INTEGER NOT NULL, month INTEGER NOT NULL, hour INTEGER NOT NULL, repeat INTEGER NOT NULL, name TEXT, label TEXT, days TEXT, comment TEXT, time INTEGER NOT NULL, day INTEGER NOT NULL,  cyear INTEGEAR NOT NULL, cmonth INTEGEAR NOT NULL,cday INTEGEAR NOT NULL, completed_time INTEGEAR NOT NULL, using_date INTEGEAR NOT NULL, PRIMARY KEY(id))");
        }
    };

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }

    @Override
    public void getPremiumDialogResultCode(int resultCode) {
        switch (resultCode){
            case CODE_ROUTINES:
                Intent i1 = new Intent(context, AddRoutine.class);
                    startActivity(i1);
                    floatingActionsMenu.collapse();
                break;
            case CODE_ACTIONS:

                break;
        }
    }
}

