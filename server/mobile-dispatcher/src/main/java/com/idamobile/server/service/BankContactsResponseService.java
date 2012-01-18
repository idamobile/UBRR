package com.idamobile.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.idamobile.protocol.ubrr.Protocol.BankContactsRequest;
import com.idamobile.protocol.ubrr.Protocol.BankContactsResponse;
import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse.Builder;
import com.idamobile.server.dao.core.ContactDao;
import com.idamobile.server.dao.core.UpdatesDao;
import com.idamobile.server.model.Contact;
import com.idamobile.server.service.support.AbstractMessageService;

@Component
public class BankContactsResponseService extends AbstractMessageService<BankContactsRequest> {

	@Autowired
	private UpdatesDao updatesDao;

	@Autowired
	private ContactDao contactDao;

	@Override
	protected void processMessage(BankContactsRequest request, Builder responseBuilder) {

		BankContactsResponse.Builder contactsBuilder = BankContactsResponse.newBuilder();

		long storedLastUpdate = updatesDao.getUpdateTime(UpdatesDao.ENTITY_CONTACTS);

		if (request.getLastUpdateTime() < storedLastUpdate) {			
			List<Contact> all = contactDao.all();
			for (Contact contact: all) {
				contactsBuilder.addContacts(contact.createMessage());
			}			
		}

		contactsBuilder.setLastUpdateTime(storedLastUpdate);
		responseBuilder.setBankContactsResponse(contactsBuilder);
	}


	@Override
	protected BankContactsRequest extractRequestMessage(MBSRequest request) {
		return request.getBankContactsRequest();
	}


	@Override
	protected boolean isApplicable(MBSRequest request) {
		return request.hasBankContactsRequest();
	}

}
