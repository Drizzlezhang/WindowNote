package com.drizzle.windownote;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by drizzle on 16/3/28.
 */
public class EditView extends RelativeLayout implements View.OnTouchListener {
	public static int viewWidth;
	public static int viewHeight;
	private Button ok;
	private Button cancel;
	private View view;
	private Context mContext;
	private WindowManager mWindowManager;
	private WindowManager.LayoutParams mLayoutParams;

	public EditView(final Context context) {
		super(context);
		mContext = context;
		mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		LayoutInflater.from(context).inflate(R.layout.edit_layout, this);
		view = findViewById(R.id.edit_layout);
		setOnTouchListener(this);
		viewWidth = view.getLayoutParams().width;
		viewHeight = view.getLayoutParams().height;
		ok = (Button) view.findViewById(R.id.window_ok_btn);
		cancel = (Button) view.findViewById(R.id.window_cancel_btn);
		final EditText editText = (EditText) view.findViewById(R.id.window_edit);
		ok.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				if (TextUtils.isEmpty(editText.getText().toString())) {
					Toast.makeText(context, "内容为空", Toast.LENGTH_SHORT).show();
				} else {
					NoteBean noteBean = new NoteBean();
					noteBean.setId(System.currentTimeMillis());
					noteBean.setMinSeconds(System.currentTimeMillis());
					noteBean.setContent(editText.getText().toString());
					noteBean.setTitle("无标题");
					Date date = new Date();
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
					noteBean.setDate(simpleDateFormat.format(date));
					NoteUtils.getInstance(context).saveNote(noteBean);
				}
				hideThisView(context);
				Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				hideThisView(context);
			}
		});
	}

	@Override public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_DOWN) {
				hideThisView(mContext);
			}
		}
		return super.dispatchKeyEvent(event);
	}

	private void hideThisView(final Context context) {
		ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.0f);
		ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.0f);
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.playTogether(objectAnimator, objectAnimator1);
		animatorSet.setDuration(150).setInterpolator(new DecelerateInterpolator());
		animatorSet.addListener(new Animator.AnimatorListener() {
			@Override public void onAnimationStart(Animator animation) {

			}

			@Override public void onAnimationEnd(Animator animation) {
				EditWindowManager.removeEditView(context);
				EditWindowManager.createFabWindow(context);
			}

			@Override public void onAnimationCancel(Animator animation) {

			}

			@Override public void onAnimationRepeat(Animator animation) {

			}
		});
		animatorSet.start();
	}

	float dX, dY;

	@Override public boolean onTouch(View v, MotionEvent event) {
		switch (event.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:
				dX = view.getX() - event.getRawX();
				dY = view.getY() - event.getRawY();
				break;
			case MotionEvent.ACTION_MOVE:
				//	view.animate().x(event.getRawX() + dX).y(event.getRawY() + dY).setDuration(0).start();
				mLayoutParams.x = (int) (event.getRawX() + dX);
				mLayoutParams.y = (int) (event.getRawY() + dY);
				mWindowManager.updateViewLayout(this, mLayoutParams);
				break;
			default:
				return false;
		}
		return true;
	}
}
