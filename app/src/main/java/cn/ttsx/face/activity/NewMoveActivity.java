package cn.ttsx.face.activity;

import android.app.DatePickerDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ttsx.face.Application;
import cn.ttsx.face.R;
import cn.ttsx.face.RegisterActivity;
import cn.ttsx.face.base.BaseActivity;
import cn.ttsx.face.dao.DaoSession;
import cn.ttsx.face.dao.Move;
import cn.ttsx.face.dao.MoveDao;
import cn.ttsx.face.utils.ToastUtils;
import cn.ttsx.face.utils.Utils;
import cn.ttsx.face.view.TopBar;
import cn.ttsx.face.view.timepicker.SublimePickerFragment;
import cn.ttsx.face.view.timepicker.TimePickerUtils;

import static cn.ttsx.face.utils.ToastUtils.showShortToast;

/**
 * 新建活动
 *
 * @author zhangbing
 * @Description: (用一句话描述该类作用)
 * @CreateDate: 2018/1/3 14:21
 * @email 314835006@qq.com
 * @UpdateUser: zhangbing
 * @UpdateDate: 2018/1/3 14:21
 * @UpdateRemark:
 */
public class NewMoveActivity extends BaseActivity {
    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.tv_moveName)
    TextView tvMoveName;
    @BindView(R.id.et_moveName)
    EditText etMoveName;
    @BindView(R.id.iv_clear_moveName)
    ImageView ivClearMoveName;
    @BindView(R.id.ll_userName)
    RelativeLayout llUserName;
    @BindView(R.id.et_move_address)
    EditText etMoveAddress;
    @BindView(R.id.iv_clear_password)
    ImageView ivClearPassword;
    @BindView(R.id.ll_)
    RelativeLayout ll;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.ll_start_time)
    RelativeLayout llStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.ll_end_time)
    RelativeLayout llEndTime;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.et_description)
    EditText etDescription;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.ll_description)
    LinearLayout llDescription;

    private MoveDao moveDao;

    // 获取日期格式器对象x
    DateFormat fmtDate = new java.text.SimpleDateFormat("yyyy-MM-dd");

    // 获取一个日历对象
    Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);

    // 当点击DatePickerDialog控件的设置按钮时，调用该方法
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            // 修改日历控件的年，月，日
            // 这里的year,monthOfYear,dayOfMonth的值与DatePickerDialog控件设置的最新值一致
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        }
    };

    @Override
    protected int setContentViewId() {
        return R.layout.activity_new_move;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        DaoSession daoSession = ((Application) NewMoveActivity.this.getApplicationContext()).getDaoSession();
        moveDao = daoSession.getMoveDao();

        topbar.setTopBarTitle("新建活动");
        topbar.setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @OnClick({R.id.ll_start_time, R.id.ll_end_time, R.id.tv_next, R.id.ll_description})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_start_time:
                String time;
                try {
                    time = tvStartTime.getText().toString().trim().substring(0, 10);
                } catch (Exception e) {
                    time = Utils.getCurrentDayStr();
                }

                int year = 0;
                int month = 0;
                int day = 0;

                try {
                    String yearString = time.substring(0, 4);
                    year = Integer.parseInt(yearString);
                } catch (Exception e) {
                    year = -1;
                }
                try {
                    String monthString = time.substring(5, 7);
                    month = Integer.parseInt(monthString) - 1;
                } catch (Exception e) {
                    month = -1;
                }

                try {
                    String dayString = time.substring(8, 10);
                    day = Integer.parseInt(dayString);
                } catch (Exception e) {
                    day = -1;
                }


                TimePickerUtils.showDayPacker((NewMoveActivity.this).getSupportFragmentManager(), new SublimePickerFragment.Callback() {
                    @Override
                    public void onCancelled() {

                    }

                    @Override
                    public void onDateTimeRecurrenceSet(SelectedDate selectedDate, int hourOfDay, int minute, SublimeRecurrencePicker.RecurrenceOption recurrenceOption, String recurrenceRule) {

                        Date d = new Date(selectedDate.getFirstDate().getTimeInMillis());
                        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                        tvStartTime.setText(sf.format(d));

//                        long dayStr = Utils.stringToLong(fmtDate.format(d), "yyyy-MM-dd");
//                        long curTime = Utils.stringToLong(Utils.getCurrentDayStr(), "yyyy-MM-dd");
//                        if (dayStr <= curTime) {
//                            // 将页面TextView的显示更新为最新时间
//                            etBirthday.setText(sf.format(d));
//                        } else {
////                            if (etBirthday != null) {
////                                try {
////                                    etBirthday.setText(etBirthday.substring(0, 10));
////                                } catch (Exception e) {
////                                    etBirthday.setText("");
////                                }
////
////                            }
//                            ToastUtils.showShortToast("生日不能超过今天");
//                        }


                    }
                }, year, month, day);

                break;

            case R.id.ll_end_time:

                String time1;
                try {
                    time = tvEndTime.getText().toString().trim().substring(0, 10);
                } catch (Exception e) {
                    time = Utils.getCurrentDayStr();
                }

                int year1 = 0;
                int month1 = 0;
                int day1 = 0;

                try {
                    String yearString = time.substring(0, 4);
                    year1 = Integer.parseInt(yearString);
                } catch (Exception e) {
                    year1 = -1;
                }
                try {
                    String monthString = time.substring(5, 7);
                    month1 = Integer.parseInt(monthString) - 1;
                } catch (Exception e) {
                    month1 = -1;
                }

                try {
                    String dayString = time.substring(8, 10);
                    day1 = Integer.parseInt(dayString);
                } catch (Exception e) {
                    day1 = -1;
                }


                TimePickerUtils.showDayPacker((NewMoveActivity.this).getSupportFragmentManager(), new SublimePickerFragment.Callback() {
                    @Override
                    public void onCancelled() {

                    }

                    @Override
                    public void onDateTimeRecurrenceSet(SelectedDate selectedDate, int hourOfDay, int minute, SublimeRecurrencePicker.RecurrenceOption recurrenceOption, String recurrenceRule) {

                        Date d = new Date(selectedDate.getFirstDate().getTimeInMillis());
                        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                        tvEndTime.setText(sf.format(d));

//                        long dayStr = Utils.stringToLong(fmtDate.format(d), "yyyy-MM-dd");
//                        long curTime = Utils.stringToLong(Utils.getCurrentDayStr(), "yyyy-MM-dd");
//                        if (dayStr <= curTime) {
//                            // 将页面TextView的显示更新为最新时间
//                            etBirthday.setText(sf.format(d));
//                        } else {
////                            if (etBirthday != null) {
////                                try {
////                                    etBirthday.setText(etBirthday.substring(0, 10));
////                                } catch (Exception e) {
////                                    etBirthday.setText("");
////                                }
////
////                            }
//                            ToastUtils.showShortToast("生日不能超过今天");
//                        }


                    }
                }, year1, month1, day1);

                break;

            case R.id.ll_description:

                break;

            case R.id.tv_next:

                next();
                break;
        }
    }

    /**
     * 下一步
     */
    private void next() {
        String mMoveName = etMoveName.getText().toString().trim();
        String mMoveAddress = etMoveAddress.getText().toString().trim();
        String mStatrTime = tvStartTime.getText().toString().trim();
        String mEndTime = tvEndTime.getText().toString().trim();
        String mDescription = etDescription.getText().toString().trim();

        //活动名称的非空判断
        if (TextUtils.isEmpty(mMoveName)) {
            showShortToast("活动名称不能为空");
            return;
        }

        //活动地址的非空判断
        if (TextUtils.isEmpty(mMoveAddress)) {
            showShortToast("活动地址不能为空");
            return;
        }

        //开始时间的非空判断
        if (mStatrTime.equals("请选择开始时间")) {
            showShortToast("请选择活动开始时间");
            return;
        }

        //开始时间的非空判断
        if (mEndTime.equals("请选择结束时间")) {
            showShortToast("请选择活动结束时间");
            return;
        }

        Move move = new Move();
        move.setMoveName(mMoveName);
        move.setMoveAddress(mMoveAddress);
        move.setMoveStartTime(mStatrTime);
        move.setMoveEndTime(mEndTime);
        move.setMoveDescription(mDescription);
        move.setImage(null);
        moveDao.insertOrReplace(move);

        finish();

    }
}
