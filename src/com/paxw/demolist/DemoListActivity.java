package com.paxw.demolist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.paxw.demolist.anim.ShuXingDongActivity;
import com.paxw.demolist.progressbar.ProgressBarPai;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class DemoListActivity extends ListActivity {
	private List<String> mData = new ArrayList<String>(Arrays.asList(
			"属性动画","progress"));
	private ListAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mData);
		getListView().setAdapter(mAdapter);
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = null;
		switch (position)
		{
		case 0:
			intent = new Intent(this, ShuXingDongActivity.class);
			break;
		case 1:
			intent = new Intent(this, ProgressBarPai.class);
			break;
		}
		startActivity(intent);
	}
}
