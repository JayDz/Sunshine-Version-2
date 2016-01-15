package com.example.android.sunshine.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailsActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.forecast_details_container);
		if (fragment == null) {
			fragment = new DetailsFragment();
			fm.beginTransaction().add(R.id.forecast_details_container, fragment).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId()) {
		case R.id.action_details_settings:
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			return true;
		case R.id.action_preferred_location:
			Utils.launchMapActivity(this);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public static class DetailsFragment extends Fragment {

		private static final String SHARE_TAG = "#SunshineApp";
		private String forecast;

		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			setHasOptionsMenu(true);
		}

		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
		{
			inflater.inflate(R.menu.detailsfragment, menu);

			MenuItem menuItem = menu.findItem(R.id.action_share);

			ShareActionProvider provider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
			if (provider != null) {
				provider.setShareIntent(createSharedForecastIntent());
			}
		}

		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
		{
			View view = inflater.inflate(R.layout.fragment_details, container, false);

			forecast = getActivity().getIntent().getExtras().getString(Intent.EXTRA_TEXT);
			TextView forecastTextView = (TextView) view.findViewById(R.id.forecast_detail_textview);
			forecastTextView.setText(forecast);

			return view;
		}

		private Intent createSharedForecastIntent() {
			Intent shareIntent = new Intent(Intent.ACTION_SEND);
			shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			shareIntent.setType("text/plain");
			shareIntent.putExtra(Intent.EXTRA_TEXT, forecast + SHARE_TAG);

			return shareIntent;
		}
	}
}