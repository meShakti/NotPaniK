package com.meshakti.notpanic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingsShow extends Activity {
	Spinner addableContacts, contacts;
	Button saveSettings, activateButon, closeButon;
	List<String> addableContactsList, contactsList;
	HashMap<String, String> contactsNumber;
	DBHelper dbManager;
	EditText messageBox;

	public final static int MAX_CONTACTS = 6;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);

		// Initializing every object
		dbManager = new DBHelper(getApplicationContext());
		addableContacts = (Spinner) findViewById(R.id.app_contact_list_added);
		contacts = (Spinner) findViewById(R.id.app_contact_list);
		saveSettings = (Button) findViewById(R.id.app_settings_save);
		activateButon = (Button) findViewById(R.id.app_settings_activate);
		closeButon = (Button) findViewById(R.id.app_settings_close);
		messageBox = (EditText) findViewById(R.id.app_user_message);
		messageBox.setFocusable(false); // remove placing of cursor in
										// messageBox
		messageBox.setText(dbManager.getMessage());
		contactsNumber = new HashMap<String, String>();
		addableContactsList = new ArrayList<String>();
		addableContactsList.add("Add Contacts");
		contactsList = new ArrayList<String>();
		contactsList.add("See Contacts");

		// setting up Initial Views (Experimental)

		try {
			Cursor c = dbManager.getData("Select id,name from Contacts");

			while (c.moveToNext()) {
				contactsList.add(c.getString(1));
			}
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Applicaton Started",
					Toast.LENGTH_SHORT).show();
		}
		ArrayAdapter<String> cAdapter = new ArrayAdapter<String>(
				getApplicationContext(), R.layout.spintext, contactsList);
		contacts.setAdapter(cAdapter);
		getContactList();

		addableContacts
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> adp, View v,
							int option, long id) {
						String contact = addableContacts.getSelectedItem()
								.toString();
						for (String item : contactsList) {
							if (contact.equals("Add Contacts")) {
								return;
							}
							if (contact.equals(item)) {
								Toast.makeText(getApplicationContext(),
										"Contact is already in list",
										Toast.LENGTH_SHORT).show();
								return;
							}

							if (contacts.getAdapter().getCount() >= MAX_CONTACTS) {
								Toast.makeText(getApplicationContext(),
										"Only 5 Contacts Allowed",
										Toast.LENGTH_SHORT).show();
								return;
							}

						}
						contactsList.add(contact);
						ArrayAdapter<String> cAdapter = new ArrayAdapter<String>(
								getApplicationContext(), R.layout.spintext,
								contactsList);
						contacts.setAdapter(cAdapter);
						Toast.makeText(getApplicationContext(),
								"Contact Added", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onNothingSelected(AdapterView<?> adp) {

					}

				});
		contacts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adp, View v, int option,
					long id) {
				if (!contacts.getSelectedItem().toString()
						.equals("See Contacts")) {

					contactsList.remove(option);
					ArrayAdapter<String> cAdapter = new ArrayAdapter<String>(
							getApplicationContext(), R.layout.spintext,
							contactsList);
					contacts.setAdapter(cAdapter);
					Toast.makeText(getApplicationContext(),
							"Contact is removed", Toast.LENGTH_SHORT).show();
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> adp) {

			}
		});
		saveSettings.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// A potentially dangerous code to use it first delete all the
				// data then inserts it back;

				if (contacts.getAdapter().getCount() != 1) {
					dbManager.clearAll();
					ContentValues values = new ContentValues();
					for (String name : contactsList) {
						if (!name.equals("See Contacts")) {
							values.put("name", name);
							values.put("telno", contactsNumber.get(name));
							int ins = dbManager.insert(values);
							if (ins < 0) {
								Toast.makeText(
										getApplicationContext(),
										"Insert Error Please Reset all settings",
										Toast.LENGTH_SHORT).show();
								return;
							}
						}

					}
					// code to save message
					values = new ContentValues();
					values.put("message", messageBox.getText().toString());
					int ll = dbManager.setMessage(values);
					if (ll > 0) {
						Toast.makeText(getApplicationContext(),
								"Settings Saved", Toast.LENGTH_SHORT).show();

					} else {
						Toast.makeText(getApplicationContext(),
								"Insert Error Please Reset all settings",
								Toast.LENGTH_SHORT).show();
						return;
					}
				}

				else {
					Toast.makeText(getApplicationContext(),
							"Empty List.Please insert some contacts",
							Toast.LENGTH_SHORT).show();
				}

			}

		});

		activateButon.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				startActivity(new Intent(SettingsShow.this, PanicAction.class));

			}
		});
		closeButon.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();

			}
		});
		messageBox.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// In Touch mode,set focus back to messageBox
				messageBox.setFocusableInTouchMode(true);
				return false;
			}
		});

	}

	private void getContactList() {

		ContentResolver resolver = getContentResolver();
		Uri contacts = android.provider.ContactsContract.Contacts.CONTENT_URI;
		Cursor c = resolver.query(contacts, null,
				android.provider.ContactsContract.Contacts.HAS_PHONE_NUMBER,
				null, android.provider.ContactsContract.Contacts.DISPLAY_NAME);
		if (c != null && c.moveToFirst()) {
			int phoneNameIndex = c
					.getColumnIndex(android.provider.ContactsContract.Contacts.DISPLAY_NAME);
			do {

				String phoneName = c.getString(phoneNameIndex);
				addableContactsList.add(phoneName);

				String id = c
						.getString(c
								.getColumnIndex(android.provider.ContactsContract.Contacts._ID));
				Cursor phCur = resolver
						.query(android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
								null,
								android.provider.ContactsContract.CommonDataKinds.Phone.CONTACT_ID
										+ " = ?", new String[] { id }, null);

				// This code will only pick the first phone number
				phCur.moveToNext();
				String phoneNo = phCur
						.getString(phCur
								.getColumnIndex(android.provider.ContactsContract.CommonDataKinds.Phone.NUMBER));
				contactsNumber.put(phoneName, phoneNo);

			} while (c.moveToNext());

			ArrayAdapter<String> aclAdapter = new ArrayAdapter<String>(
					getApplicationContext(), R.layout.spintext,
					addableContactsList);
			addableContacts.setAdapter(aclAdapter);
		}

	}

}
