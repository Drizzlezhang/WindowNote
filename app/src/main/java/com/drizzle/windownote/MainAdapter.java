package com.drizzle.windownote;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.List;

/**
 * Created by drizzle on 16/3/24.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

	private LayoutInflater mLayoutInflater;
	private Context mContext;
	private List<NoteBean> mNoteBeanList;

	public MainAdapter(List<NoteBean> noteBeanList, Context context) {
		mContext = context;
		mLayoutInflater = LayoutInflater.from(mContext);
		this.mNoteBeanList = noteBeanList;
	}

	private AdapterView.OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
		this.mOnItemClickListener = onItemClickListener;
	}

	@Override public void onBindViewHolder(final MainViewHolder holder, final int position) {
		holder.dateText.setText(getNoteBean(position).getDate());
		holder.titleText.setText(getNoteBean(position).getTitle());
		holder.itemCard.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				if (mOnItemClickListener != null) {
					mOnItemClickListener.onItemClick(null, holder.itemCard, position, getNoteBean(position).getId());
				}
			}
		});
	}

	private NoteBean getNoteBean(int position) {
		NoteBean noteBean = mNoteBeanList.get(position);
		if (noteBean == null) {
			return new NoteBean();
		}
		return noteBean;
	}

	@Override public int getItemCount() {
		return mNoteBeanList == null ? 0 : mNoteBeanList.size();
	}

	@Override public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new MainViewHolder(mLayoutInflater.inflate(R.layout.main_item, parent, false));
	}

	public class MainViewHolder extends RecyclerView.ViewHolder {
		@Bind(R.id.item_card) CardView itemCard;
		@Bind(R.id.item_title) TextView titleText;
		@Bind(R.id.item_date) TextView dateText;

		public MainViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}
