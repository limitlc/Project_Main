package com.paxw.demolist.slidingmenu;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.http.client.utils.URLEncodedUtils;
import org.json.JSONObject;

import com.paxw.demolist.R;
import com.paxw.demolist.slidingmenu.weather.GetCityCodeUtil;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v4.widget.ViewDragHelper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.BDNotifyListener;//假如用到位置提醒功能，需要import该类
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.location.Poi;
public class SlidemenuActivity extends Activity implements BDLocationListener {

	private DrawerLayout drawerLayout;
//	private LinearLayout force;
//	private LinearLayout back;
	public LocationClient mLocationClient = null;
//	public BDLocationListener myListener = new MyLocationListener();
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (msg.what==0x999) {
//				Log.i("BaiduLocationApiDem", GetCityCodeUtil.getInstance(context).cityMap.get(((String)msg.obj)));
//				getWeather(GetCityCodeUtil.getInstance(context).cityMap.get(("郑州市")));
			}

		};
	};
	private Context context;
	private Map<String, String> cityMap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context  = this;
		mRequestQueue =  Volley.newRequestQueue(this); 
		setContentView(R.layout.activity_slidemenu);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
		mLocationClient.registerLocationListener( this );    //注册监听函数
		GetCityCodeUtil.getInstance(this);
		initLocation();
		mLocationClient.start();
		getWeather("北京");
		
	}
//	time : 2015-10-30 11:31:09
//	error code : 161
//	latitude : 39.962957
//	lontitude : 116.480532
//	radius : 63.282173
//	addr : 中国北京市朝阳区安家楼村路
//	operationers : 0
//	describe : 网络定位成功
//	locationdescribe : 在燕莎50#附近
//	poilist size = : 4
//	poi= : 2423643486581417115 中赫技术投资控股公司 0.99
//	poi= : 8035432502523439802 安家楼 0.99
//	poi= : 17829740999090372607 安家楼38号院 0.99
//	poi= : 1266619304795701247 嘉禾 0.99
	@Override
	public void onReceiveLocation(BDLocation location) {
		// Receive Location
		StringBuffer sb = new StringBuffer(256);
		sb.append("time : ");
		sb.append(location.getTime());
		sb.append("\nerror code : ");
		sb.append(location.getLocType());
		sb.append("\nlatitude : ");
		sb.append(location.getLatitude());
		sb.append("\nlontitude : ");
		sb.append(location.getLongitude());
		sb.append("\nradius : ");
		sb.append(location.getRadius());
		if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
			sb.append("\nspeed : ");
			sb.append(location.getSpeed());// 单位：公里每小时
			sb.append("\nsatellite : ");
			sb.append(location.getSatelliteNumber());
			sb.append("\nheight : ");
			sb.append(location.getAltitude());// 单位：米
			sb.append("\ndirection : ");
			sb.append(location.getDirection());// 单位度
			sb.append("\naddr : ");
			sb.append(location.getAddrStr());
			sb.append("\ndescribe : ");
			sb.append("gps定位成功");
			mLocationClient.stop();

		} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
			sb.append("\naddr : ");
			sb.append(location.getAddrStr());
			// 运营商信息
			sb.append("\noperationers : ");
			sb.append(location.getOperators());
			sb.append("\ndescribe : ");
			sb.append("网络定位成功");
			mLocationClient.stop();
		} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
			sb.append("\ndescribe : ");
			sb.append("离线定位成功，离线定位结果也是有效的");
			mLocationClient.stop();
		} else if (location.getLocType() == BDLocation.TypeServerError) {
			sb.append("\ndescribe : ");
			sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
			mLocationClient.stop();
		} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
			sb.append("\ndescribe : ");
			sb.append("网络不同导致定位失败，请检查网络是否通畅");
			mLocationClient.stop();
		} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
			sb.append("\ndescribe : ");
			sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
			mLocationClient.stop();
		}
		sb.append("\nlocationdescribe : ");
		sb.append(location.getLocationDescribe());// 位置语义化信息
		List<Poi> list = location.getPoiList();// POI数据
		if (list != null) {
			sb.append("\npoilist size = : ");
			sb.append(list.size());
			for (Poi p : list) {
				sb.append("\npoi= : ");
				sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
			}
		}
//		Log.i("BaiduLocationApiDem", sb.toString());
//		Log.i("BaiduLocationApiDem", cityMap.get(location.getCity()));
		Message msg = handler.obtainMessage();
		msg.obj=location.getCity();
		msg.what=0x999;
		handler.sendMessage(msg);
	}
	/**
	 * 百度定位初始化参数
	 */
	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
		int span = 1000;
		option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(false);// 可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
		option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		mLocationClient.setLocOption(option);
	}
	private RequestQueue mRequestQueue;  
	private void getWeather(String cityCode){
		String str = "http://wthrcdn.etouch.cn/weather_mini?city=";
		String url ="";
		try {
			url = str+URLEncoder.encode(cityCode, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET, url,null,new Response.Listener<JSONObject>(){

			@Override
			public void onResponse(JSONObject response) {
				
				Log.e("-------------", response.toString());
			}
			
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
		});
		mRequestQueue.add(jr);
	}

	

}
