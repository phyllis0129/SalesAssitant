/**
 *@title SrxParameters.java 
 *@package com.srx.utility 
 *@author Johnbin Wang (code my life) 
 *@mail johnbin.wang@gmail.com 
 *@date 2012-8-14 
 *@version V1.0
 */
package net.basilwang.utils;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

public class SaParameters {

	private Bundle mParameters = new Bundle();
	private List<String> mKeys = new ArrayList<String>();

	public SaParameters() {

	}

	public void add(String key, String value) {
		SaLog.log("srxparam", "adding... key=" + key + ", value=" + value);
		if (this.mKeys.contains(key)) {
			this.mParameters.putString(key, value);
		} else {
			this.mKeys.add(key);
			this.mParameters.putString(key, value);
		}
	}

	public void remove(String key) {
		SaLog.log("srxparam", "remove key=" + key);
		mKeys.remove(key);
		this.mParameters.remove(key);
	}

	public void remove(int i) {
		SaLog.log("srxparam", "remove key, index =" + i);
		String key = this.mKeys.get(i);
		this.mParameters.remove(key);
		mKeys.remove(key);
	}

	public int getLocation(String key) {
		if (this.mKeys.contains(key)) {
			return this.mKeys.indexOf(key);
		}
		return -1;
	}

	public String getKey(int location) {
		if (location >= 0 && location < this.mKeys.size()) {
			return this.mKeys.get(location);
		}
		return "";
	}

	public String getValue(String key) {
		String rlt = this.mParameters.getString(key);
		return rlt;
	}

	public String getValue(int location) {
		String key = this.mKeys.get(location);
		String rlt = this.mParameters.getString(key);
		return rlt;
	}

	public int size() {
		return mKeys.size();
	}

	public void addAll(SaParameters parameters) {
		for (int i = 0; i < parameters.size(); i++) {
			SaLog.log("srxparam", "adding... key=" + parameters.getKey(i)
					+ ", value=" + parameters.getValue(i));
			this.add(parameters.getKey(i), parameters.getValue(i));
		}

	}

	public void clear() {
		SaLog.log("srxparam", "clear!!!!!!!!!!!!!!!!!!!");
		this.mKeys.clear();
		this.mParameters.clear();
	}

	public List<String> getKeys() {
		return this.mKeys;
	}

	public String toStr() {
		return mParameters.toString();
	}
}
