/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value.swing;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent.EventType;
import javax.swing.text.Document;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.ReflectionTools;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.swing.DocumentAdapter;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class DocumentAdapterTests extends TestCase {
	private WritablePropertyValueModel<String> stringHolder;
	Document documentAdapter;
	boolean eventFired;

	public DocumentAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.stringHolder = new SimplePropertyValueModel<String>("0123456789");
		this.documentAdapter = new DocumentAdapter(this.stringHolder) {
			@Override
			protected PropertyChangeListener buildStringListener() {
				return this.buildStringListener_();
			}
		};
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testRemove() throws Exception {
		this.eventFired = false;
		this.documentAdapter.addDocumentListener(new TestDocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				DocumentAdapterTests.this.eventFired = true;
				assertEquals(EventType.REMOVE, e.getType());
				assertEquals(DocumentAdapterTests.this.documentAdapter, e.getDocument());
				// this will be the removal of "23456"
				assertEquals(2, e.getOffset());
				assertEquals(5, e.getLength());
			}
		});
		this.documentAdapter.remove(2, 5);
		assertTrue(this.eventFired);
		assertEquals("01789", this.stringHolder.getValue());
	}

	public void testInsert() throws Exception {
		this.eventFired = false;
		this.documentAdapter.addDocumentListener(new TestDocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				DocumentAdapterTests.this.eventFired = true;
				assertEquals(EventType.INSERT, e.getType());
				assertEquals(DocumentAdapterTests.this.documentAdapter, e.getDocument());
				// this will be the insert of "xxxxxx"
				assertEquals(2, e.getOffset());
				assertEquals(5, e.getLength());
			}
		});
		this.documentAdapter.insertString(2, "xxxxx", null);
		assertTrue(this.eventFired);
		assertEquals("01xxxxx23456789", this.stringHolder.getValue());
	}

	public void testSetValue() throws Exception {
		this.eventFired = false;
		this.documentAdapter.addDocumentListener(new TestDocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				DocumentAdapterTests.this.eventFired = true;
				assertEquals(EventType.INSERT, e.getType());
				assertEquals(DocumentAdapterTests.this.documentAdapter, e.getDocument());
				// this will be the insert of "foo"
				assertEquals(0, e.getOffset());
				assertEquals(3, e.getLength());
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				assertEquals(EventType.REMOVE, e.getType());
				assertEquals(DocumentAdapterTests.this.documentAdapter, e.getDocument());
				// this will be the removal of "0123456789"
				assertEquals(0, e.getOffset());
				assertEquals(10, e.getLength());
			}
		});
		assertEquals("0123456789", this.documentAdapter.getText(0, this.documentAdapter.getLength()));
		this.stringHolder.setValue("foo");
		assertTrue(this.eventFired);
		assertEquals("foo", this.documentAdapter.getText(0, this.documentAdapter.getLength()));
	}

	public void testHasListeners() throws Exception {
		SimplePropertyValueModel<String> localStringHolder = (SimplePropertyValueModel<String>) this.stringHolder;
		assertFalse(localStringHolder.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.verifyHasNoListeners(this.documentAdapter);

		DocumentListener listener = new TestDocumentListener();
		this.documentAdapter.addDocumentListener(listener);
		assertTrue(localStringHolder.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.verifyHasListeners(this.documentAdapter);

		this.documentAdapter.removeDocumentListener(listener);
		assertFalse(localStringHolder.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.verifyHasNoListeners(this.documentAdapter);
	}

	private void verifyHasNoListeners(Object document) throws Exception {
		Object delegate = ReflectionTools.getFieldValue(document, "delegate");
		Object[] listeners = (Object[]) ReflectionTools.executeMethod(delegate, "getDocumentListeners");
		assertEquals(0, listeners.length);
	}

	private void verifyHasListeners(Object document) throws Exception {
		Object delegate = ReflectionTools.getFieldValue(document, "delegate");
		Object[] listeners = (Object[]) ReflectionTools.executeMethod(delegate, "getDocumentListeners");
		assertFalse(listeners.length == 0);
	}


private class TestDocumentListener implements DocumentListener {
	TestDocumentListener() {
		super();
	}
	public void changedUpdate(DocumentEvent e) {
		fail("unexpected event");
	}
	public void insertUpdate(DocumentEvent e) {
		fail("unexpected event");
	}
	public void removeUpdate(DocumentEvent e) {
		fail("unexpected event");
	}
}

}
