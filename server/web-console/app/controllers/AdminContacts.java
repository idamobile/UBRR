package controllers;

import java.util.List;

import models.core.Contact;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import controllers.CRUD;
import controllers.CRUD.ObjectType;

import play.mvc.With;

@With(Secure.class)
@CRUD.For(models.core.Contact.class)
public class AdminContacts extends CRUD{

	public static void moveUp(Contact contact) {
		contact.moveUp();
		redirect("Contacts.list");
		
	}
	
}
