package com.drizzle.windownote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;

public class ContentActivity extends AppCompatActivity {

	@Bind(R.id.content_toolbar) Toolbar mToolbar;
	@Bind(R.id.content_fab) FloatingActionButton mFloatingActionButton;
	@Bind(R.id.title_edit) EditText titleEdit;
	@Bind(R.id.content_edit) EditText contentEdit;
	@Bind(R.id.content_date) TextView contentDate;
	private List<NoteBean> mNoteBeanList;
	private int noteId;
	private int EDIT_STATUS;
	private static final int EDIT_DISABLE = 1;
	private static final int EDIT_ENABLE = 2;
	private int noteType;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content);
		ButterKnife.bind(this);
		Intent intent = getIntent();
		noteType = intent.getIntExtra(Tag.NOTE_TYPE, Tag.NEW_NOTE_TYPE);
		if (noteType == Tag.NEW_NOTE_TYPE) {
			EDIT_STATUS = EDIT_ENABLE;
		} else {
			EDIT_STATUS = EDIT_DISABLE;
			noteId = intent.getIntExtra(Tag.NOTE_ID, 0);
		}
		initData();
		initViews();
	}

	private void initViews() {
		refreshEditStatus();
		setSupportActionBar(mToolbar);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		titleEdit.setText(mNoteBeanList.get(noteId).getTitle());
		contentDate.setText(mNoteBeanList.get(noteId).getDate());
		mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				if (EDIT_STATUS == EDIT_DISABLE) {
					EDIT_STATUS = EDIT_ENABLE;
					refreshEditStatus();
					Snackbar.make(view, R.string.start_edit, Snackbar.LENGTH_SHORT).show();
				} else {
					EDIT_STATUS = EDIT_DISABLE;
					refreshEditStatus();
					Snackbar.make(view, R.string.stop_edit, Snackbar.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void initData() {
		mNoteBeanList = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			NoteBean noteBean = new NoteBean();
			noteBean.setId(i);
			noteBean.setDate("2015年" + i + "月");
			noteBean.setTitle("2015年2015年2015年" + i + "条");
			mNoteBeanList.add(noteBean);
		}
	}

	private void refreshEditStatus() {
		if (EDIT_STATUS == EDIT_DISABLE) {
			contentEdit.setEnabled(false);
			titleEdit.setEnabled(false);
			mToolbar.setTitle(R.string.toolbar_note);
		} else {
			contentEdit.setEnabled(true);
			titleEdit.setEnabled(true);
			mToolbar.setTitle(R.string.toolbar_edit);
		}
	}

	private void onBackClicked() {
		if (EDIT_STATUS == EDIT_DISABLE) {
			finish();
		} else {
			if (TextUtils.isEmpty(titleEdit.getText().toString()) && TextUtils.isEmpty(
				contentEdit.getText().toString())) {
				new AlertDialog.Builder(this).setPositiveButton("确认", new DialogInterface.OnClickListener() {
					@Override public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						finish();
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).setTitle("确定?").setMessage(R.string.dialog_message).show();
			} else {
				//TODO:保存并退出
				finish();
			}
		}
	}

	@Override public void onBackPressed() {
		onBackClicked();
	}

	@Override public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_content, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			onBackClicked();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
