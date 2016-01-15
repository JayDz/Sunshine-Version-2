package com.example.android.sunshine.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * Author:      Jeffrey Diaz
 * Date:        1/15/2016
 * Description:
 */
public class Utils {

	public static void launchMapActivity(Context context) {
		String location = PreferenceManager.getDefaultSharedPreferences(context)
			.getString(context.getString(R.string.pref_location_key), context.getString(R.string.pref_location_default));

		Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
			.appendQueryParameter("q", location)
			.build();

		Intent intent = new Intent(Intent.ACTION_VIEW, geoLocation);
		if (intent.resolveActivity(context.getPackageManager()) != null) {
			context.startActivity(intent);
		} else {
			Toast.makeText(context, "Maps not installed", Toast.LENGTH_SHORT).show();
		}
	}
}