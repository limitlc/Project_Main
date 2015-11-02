package com.paxw.demolist.slidingmenu.weather;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import android.content.Context;
import android.util.Log;
public class GetCityCodeUtil {
	public  Map<String, String> cityMap = new HashMap<String, String>();
	private static GetCityCodeUtil instance;
	
	public  static synchronized GetCityCodeUtil getInstance(Context context){
		if (null == instance) {
			instance = new GetCityCodeUtil(context);
		}
		return instance;
	}
	private  GetCityCodeUtil(final Context context){
		new Thread(){
			@Override
			public void run() {
				readAssets(context);
			}
		}.start();
		
	}
	private void readAssets(Context context) {
		InputStream in = null;
		ByteArrayOutputStream out = null;

		try {
			in = context.getAssets().open("citycode.json");

			out = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.flush();
			Gson json = new Gson();
			WeatherBean weatherBean = json.fromJson(new String(out.toByteArray()), WeatherBean.class);
			for (CityofProvince province : weatherBean.get城市代码()) {
				for (CityCode city : province.get市()) {
					cityMap.put(city.get市名()+"市", city.get编码());
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
