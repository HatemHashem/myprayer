package com.example.myprayer;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("date")
	private Date date;

	@SerializedName("meta")
	private Meta meta;

	@SerializedName("timings")
	private Timings timings;

	public void setDate(Date date){
		this.date = date;
	}

	public Date getDate(){
		return date;
	}

	public void setMeta(Meta meta){
		this.meta = meta;
	}

	public Meta getMeta(){
		return meta;
	}

	public void setTimings(Timings timings){
		this.timings = timings;
	}

	public Timings getTimings(){
		return timings;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"date = '" + date + '\'' + 
			",meta = '" + meta + '\'' + 
			",timings = '" + timings + '\'' + 
			"}";
		}
}