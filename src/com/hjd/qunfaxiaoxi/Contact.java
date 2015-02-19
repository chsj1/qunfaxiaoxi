package com.hjd.qunfaxiaoxi;

/**
 * 联系人实体类
 * 
 * @author �ƿ���
 */
public class Contact {

	/**
	 * ��ϵ�˵�����
	 */
	private String name;

	/**
	 * 排序字母
	 */
	private String sortKey;

	/**
	 * ��ϵ�˵ĺ���
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
