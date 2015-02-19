package com.hjd.qunfaxiaoxi;

/**
 * 鑱旂郴浜哄疄浣撶被
 * 
 * @author 黄俊东
 */
public class Contact {

	/**
	 * 联系人的名字
	 */
	private String name;

	/**
	 * 鎺掑簭瀛楁瘝
	 */
	private String sortKey;

	/**
	 * 联系人的号码
	 */
	private String number;
	
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSortKey() {
		return sortKey;
	}

	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}

	@Override
	public String toString() {
		return "Contact [name=" + name + ", sortKey=" + sortKey + ", number="
				+ number + "]";
	}

	
}
