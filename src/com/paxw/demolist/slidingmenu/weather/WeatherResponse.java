package com.paxw.demolist.slidingmenu.weather;

import java.util.List;

public class WeatherResponse {
	private String desc;
	private String status;
	private WeatherData data;
	
	
	
	
	
	
	public String getDesc() {
		return desc;
	}






	public void setDesc(String desc) {
		this.desc = desc;
	}






	public String getStatus() {
		return status;
	}






	public void setStatus(String status) {
		this.status = status;
	}






	public WeatherData getData() {
		return data;
	}






	public void setData(WeatherData data) {
		this.data = data;
	}






	public class WeatherData{
		private String wendu ;
		private String ganmao;
		private List<Future>  forecast;
		public String getWendu() {
			return wendu;
		}
		public void setWendu(String wendu) {
			this.wendu = wendu;
		}
		public String getGanmao() {
			return ganmao;
		}
		public void setGanmao(String ganmao) {
			this.ganmao = ganmao;
		}
		public List<Future> getForecast() {
			return forecast;
		}
		public void setForecast(List<Future> forecast) {
			this.forecast = forecast;
		}
		
		
		
		
		
		
	}
	public class Future{
		private String fengxiang;
		private String fengli;
		private String high;
		private String type;
		private String low;
		private String date;
		public String getFengxiang() {
			return fengxiang;
		}
		public void setFengxiang(String fengxiang) {
			this.fengxiang = fengxiang;
		}
		public String getFengli() {
			return fengli;
		}
		public void setFengli(String fengli) {
			this.fengli = fengli;
		}
		public String getHigh() {
			return high;
		}
		public void setHigh(String high) {
			this.high = high;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getLow() {
			return low;
		}
		public void setLow(String low) {
			this.low = low;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		
	}
}
