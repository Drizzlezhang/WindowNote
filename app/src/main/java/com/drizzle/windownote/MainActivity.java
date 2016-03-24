package com.drizzle.windownote;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
	@Bind(R.id.main_toolbar) Toolbar mToolbar;

	@Bind(R.id.main_recyclerview) RecyclerView mRecyclerView;

	@Bind(R.id.main_fab) FloatingActionButton mFloatingActionButton;

	private MainAdapter mMainAdapter;
	private List<NoteBean> mNoteBeanList;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		initData();
		initViews();
	}

	private void initViews() {
		mToolbar.setTitle(R.string.toolbar_chart);
		mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mMainAdapter = new MainAdapter(mNoteBeanList, this);
		mRecyclerView.setAdapter(mMainAdapter);
		mMainAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(MainActivity.this, ContentActivity.class);
				intent.putExtra(Tag.NOTE_ID, id);
				intent.putExtra(Tag.NOTE_TYPE, Tag.EXIST_NOTE_TYPE);
				ActivityOptionsCompat optionsCompat =
					ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,
						new Pair<View, String>(view.findViewById(R.id.item_title),
							getString(R.string.transition_title)),
						new Pair<View, String>(view.findViewById(R.id.item_date), getString(R.string.transition_date)));
				ActivityCompat.startActivity(MainActivity.this, intent, optionsCompat.toBundle());
			}
		});
		mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ContentActivity.class);
				intent.putExtra(Tag.NOTE_TYPE, Tag.NEW_NOTE_TYPE);
				startActivity(intent);
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
}
