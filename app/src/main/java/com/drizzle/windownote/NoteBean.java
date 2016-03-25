package com.drizzle.windownote;

/**
 * Created by drizzle on 16/3/24.
 */
public class NoteBean implements Comparable<NoteBean> {
	private long id;
	private String title;
	private String content;
	private String date;
	private long minSeconds;

	public long getMinSeconds() {
		return minSeconds;
	}

	public void setMinSeconds(long minSeconds) {
		this.minSeconds = minSeconds;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public NoteBean() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override public int hashCode() {
		return (int) id;
	}

	@Override public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (this == o) {
			return true;
		}
		if (o instanceof NoteBean) {
			NoteBean noteBean = (NoteBean) o;
			return (noteBean.getId() == this.id);
		}
		return false;
	}

	@Override public int compareTo(NoteBean another) {
		return (int) (another.getMinSeconds() - this.minSeconds);
	}
}
