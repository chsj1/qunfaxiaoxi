package com.hjd.qunfaxiaoxi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.database.Cursor;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private List<String> smsList;
	private List<Contact> contactList;
	private List<String> smsBegining;
	private String addSmsSuccessText;
	SmsManager smsManager;
	private String sendName = "黄俊东";

	Random r;
	int smsSize;
	int contactsSize;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		r = new Random();
		contactList = getContactList();
		initialSmsList();
		addSmsSuccessText = "添加DIY祝福语成功";
		smsManager = SmsManager.getDefault();

		initialNameNumbers();
	}

	public void initialNameNumbers() {
		TextView name_tv = (TextView) findViewById(R.id.nameNumbers);
		name_tv.setText("你的通讯录里面共有" + contactList.size() + "个联系人");
	}

	public void initialSmsBeginning() {
		smsBegining = new ArrayList<String>();
	}

	public void qunfaSms(View view){
    	System.out.println("------->开始群发消息");
    	
    	//用于处理姓名为空的情况
    	EditText editText = (EditText) findViewById(R.id.name);
    	String name = editText.getText().toString();
    	if(name.trim().equals("") == false){
    		sendName = name;
    	}
    	
    	int i;
    	int size = contactList.size();
    	for(i = 0 ; i < size ; ++i){
    		Contact contact = contactList.get(i);
    		String content = "哈喽~"+ sendName +"在这里给"+contact.getName() + "拜年了~" + smsList.get(r.nextInt(smsSize));
    		String number = contact.getNumber();
    		if(isMobileNO(number) == false){//如果手机号码不合法
    			continue;
    		}
    		
    		sendSms(number, content);
    	    
    		System.out.println("群发的内容是: " + content);
    	}
    	
    	Toast.makeText(getApplicationContext(), "这次将群发"+ size + "条祝福信息", 1).show();
    }

	/**
	 * 判断是否是一个手机号码
	 * @param mobiles
	 * @return
	 */
	public boolean isMobileNO(String mobiles){
    	Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
//        System.out.println(m.matches()+"---");
        return m.matches();
    }

	/**
	 * 发短信的逻辑
	 * 
	 * @param phone
	 *            接收短信的号码
	 * @param content
	 *            短信内容
	 */
	public void sendSms(String phone, String content) {

		List<String> texts = smsManager.divideMessage(content);

		System.out.println("--------->content: " + content);
		System.out.println("phone:" + phone);
		for (int i = 0; i < texts.size(); ++i) {
			smsManager.sendTextMessage(phone, null, content, null, null);

			System.out.println("短信已发送...");
		}
	}

	public void printSmsList() {
		int size = smsList.size();
		int i;
		for (i = 0; i < size; ++i) {
			System.out.println(smsList.get(i));
		}
	}

	public void addSms(View view) {
		System.out.println("---------->你单击了添加富裕的按钮");

		EditText et = (EditText) findViewById(R.id.smsContent);
		smsList.add(et.getText().toString());

		et.setText("");// 清空输入的内容
		smsSize = smsList.size();

		printSmsList();
		Toast.makeText(getApplicationContext(),
				addSmsSuccessText + ",俊东在这里给你拜年了~", 1).show();
	}

	public void initialSmsList() {
		smsList = new ArrayList<String>();
		smsList.add("羊年到，快乐把你逗，欢喜连连;美好把你宠，幸福无限;吉祥把你绕，好运无穷;财气把你熏，财运亨通;平安把你保，福寿绵绵;朋友则把你祝福，情深谊厚：祝羊年开怀，福寿安康!");
		smsList.add("鞭炮响，羊年到，喜气洋洋很重要：祖国搞定钓鱼岛，国足雄起不卧槽；房价不再节节高，股市人人得解套；涨工资不再是口号，没事宅在家里数钞票，幸福快乐直到老!");
		smsList.add("新年快乐！最近是否没时间，拜年、聚会、访亲忙碌碌。短信祝福又送到，朋友问候莫忘掉，愿你新年的每一天都快乐，每一秒都幸福！");

		smsSize = smsList.size();
	}

	/**
	 * 获取联系人列表
	 * 
	 * @return
	 */
	public List<Contact> getContactList() {
		List<Contact> contacts = new ArrayList<Contact>();

		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		Cursor cursor = getContentResolver().query(uri,
				new String[] { "display_name", "sort_key", "data1" }, null,
				null, "sort_key");
		if (cursor.moveToFirst()) {
			do {
				String name = cursor.getString(0);
				// String sortKey = getSortKey(cursor.getString(1));
				String number = cursor.getString(2);

				Contact contact = new Contact();
				contact.setName(name);
				// contact.setSortKey(sortKey);
				contact.setNumber(number);
				contacts.add(contact);
			} while (cursor.moveToNext());
		}

		return contacts;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
