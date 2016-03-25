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

public class ContentActivity extends AppCompatActivity {

	@Bind(R.id.content_toolbar) Toolbar mToolbar;
	@Bind(R.id.content_fab) FloatingActionButton mFloatingActionButton;
	@Bind(R.id.title_edit) EditText titleEdit;
	@Bind(R.id.content_edit) EditText contentEdit;
	@Bind(R.id.content_date) TextView contentDate;
	private int noteId;
	private int EDIT_STATUS;
	private static final int EDIT_DISABLE = 1;
	private static final int EDIT_ENABLE = 2;
	private int noteType;
	private NoteBean mNoteBean;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content);
		ButterKnife.bind(this);
		initData();
		initViews();
	}

	private void initData() {
		Intent intent = getIntent();
		noteType = intent.getIntExtra(Tag.NOTE_TYPE, Tag.NEW_NOTE_TYPE);
		if (noteType == Tag.NEW_NOTE_TYPE) {
			EDIT_STATUS = EDIT_DISABLE;
			mNoteBean = new NoteBean();
			mNoteBean.setId(System.currentTimeMillis());
			mNoteBean.setMinSeconds(System.currentTimeMillis());
			mNoteBean.setTitle("");
			mNoteBean.setContent("");
		} else {
			EDIT_STATUS = EDIT_DISABLE;
			noteId = intent.getIntExtra(Tag.NOTE_ID, 0);
			mNoteBean = NoteUtils.getInstance(this).getNote(noteId);
		}
	}

	private void initViews() {
		refreshEditStatus();
		setSupportActionBar(mToolbar);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		titleEdit.setText(mNoteBean.getTitle());
		contentDate.setText(mNoteBean.getDate());
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
			if (TextUtils.isEmpty(titleEdit.getText().toString()) || TextUtils.isEmpty(
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
		switch (id) {
			case android.R.id.home:
				onBackClicked();
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}
}
