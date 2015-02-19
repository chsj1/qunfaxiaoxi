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
	private String sendName = "�ƿ���";

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
		addSmsSuccessText = "���DIYף����ɹ�";
		smsManager = SmsManager.getDefault();

		initialNameNumbers();
	}

	public void initialNameNumbers() {
		TextView name_tv = (TextView) findViewById(R.id.nameNumbers);
		name_tv.setText("���ͨѶ¼���湲��" + contactList.size() + "����ϵ��");
	}

	public void initialSmsBeginning() {
		smsBegining = new ArrayList<String>();
	}

	public void qunfaSms(View view){
    	System.out.println("------->��ʼȺ����Ϣ");
    	
    	//���ڴ�������Ϊ�յ����
    	EditText editText = (EditText) findViewById(R.id.name);
    	String name = editText.getText().toString();
    	if(name.trim().equals("") == false){
    		sendName = name;
    	}
    	
    	int i;
    	int size = contactList.size();
    	for(i = 0 ; i < size ; ++i){
    		Contact contact = contactList.get(i);
    		String content = "���~"+ sendName +"�������"+contact.getName() + "������~" + smsList.get(r.nextInt(smsSize));
    		String number = contact.getNumber();
    		if(isMobileNO(number) == false){//����ֻ����벻�Ϸ�
    			continue;
    		}
    		
    		sendSms(number, content);
    	    
    		System.out.println("Ⱥ����������: " + content);
    	}
    	
    	Toast.makeText(getApplicationContext(), "��ν�Ⱥ��"+ size + "��ף����Ϣ", 1).show();
    }

	/**
	 * �ж��Ƿ���һ���ֻ�����
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
	 * �����ŵ��߼�
	 * 
	 * @param phone
	 *            ���ն��ŵĺ���
	 * @param content
	 *            ��������
	 */
	public void sendSms(String phone, String content) {

		List<String> texts = smsManager.divideMessage(content);

		System.out.println("--------->content: " + content);
		System.out.println("phone:" + phone);
		for (int i = 0; i < texts.size(); ++i) {
			smsManager.sendTextMessage(phone, null, content, null, null);

			System.out.println("�����ѷ���...");
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
		System.out.println("---------->�㵥������Ӹ�ԣ�İ�ť");

		EditText et = (EditText) findViewById(R.id.smsContent);
		smsList.add(et.getText().toString());

		et.setText("");// ������������
		smsSize = smsList.size();

		printSmsList();
		Toast.makeText(getApplicationContext(),
				addSmsSuccessText + ",������������������~", 1).show();
	}

	public void initialSmsList() {
		smsList = new ArrayList<String>();
		smsList.add("���굽�����ְ��㶺����ϲ����;���ð���裬�Ҹ�����;��������ƣ���������;��������Ѭ�����˺�ͨ;ƽ�����㱣����������;���������ף�����������ף���꿪�������ٰ���!");
		smsList.add("�����죬���굽��ϲ���������Ҫ������㶨���㵺�����������Բۣ����۲��ٽڽڸߣ��������˵ý��ף��ǹ��ʲ����ǿںţ�û��լ�ڼ�������Ʊ���Ҹ�����ֱ����!");
		smsList.add("������֣�����Ƿ�ûʱ�䣬���ꡢ�ۻᡢ����æµµ������ף�����͵��������ʺ�Ī������Ը�������ÿһ�춼���֣�ÿһ�붼�Ҹ���");

		smsSize = smsList.size();
	}

	/**
	 * ��ȡ��ϵ���б�
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
