/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value.swing;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class ObjectListSelectionModelTests extends TestCase {
	private DefaultListModel listModel;
	private ObjectListSelectionModel selectionModel;

	public ObjectListSelectionModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.listModel = this.buildListModel();
		this.selectionModel = this.buildSelectionModel(this.listModel);
	}

	private DefaultListModel buildListModel() {
		DefaultListModel lm = new DefaultListModel();
		lm.addElement("foo");
		lm.addElement("bar");
		lm.addElement("baz");
		return lm;
	}

	private ObjectListSelectionModel buildSelectionModel(ListModel lm) {
		return new ObjectListSelectionModel(lm);
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testListDataListener() {
		this.selectionModel.addListSelectionListener(this.buildListSelectionListener());
		this.selectionModel.setSelectionInterval(0, 0);
		assertEquals("foo", this.selectionModel.selectedValue());
		this.listModel.set(0, "jar");
		assertEquals("jar", this.selectionModel.selectedValue());
	}

	public void testGetSelectedValue() {
		this.selectionModel.setSelectionInterval(0, 0);
		assertEquals("foo", this.selectionModel.selectedValue());
	}

	public void testGetSelectedValues() {
		this.selectionModel.setSelectionInterval(0, 0);
		this.selectionModel.addSelectionInterval(2, 2);
		assertEquals(2, this.selectionModel.selectedValues().length);
		assertTrue(ArrayTools.contains(this.selectionModel.selectedValues(), "foo"));
		assertTrue(ArrayTools.contains(this.selectionModel.selectedValues(), "baz"));
	}

	public void testSetSelectedValue() {
		this.selectionModel.setSelectedValue("foo");
		assertEquals(0, this.selectionModel.getMinSelectionIndex());
		assertEquals(0, this.selectionModel.getMaxSelectionIndex());
	}

	public void testSetSelectedValues() {
		this.selectionModel.setSelectedValues(new Object[] {"foo", "baz"});
		assertEquals(0, this.selectionModel.getMinSelectionIndex());
		assertEquals(2, this.selectionModel.getMaxSelectionIndex());
	}

	public void testAddSelectedValue() {
		this.listModel.addElement("joo");
		this.listModel.addElement("jar");
		this.listModel.addElement("jaz");
		this.selectionModel.setSelectedValue("foo");
		this.selectionModel.addSelectedValue("jaz");
		assertEquals(0, this.selectionModel.getMinSelectionIndex());
		assertEquals(5, this.selectionModel.getMaxSelectionIndex());
		assertTrue(this.selectionModel.isSelectedIndex(0));
		assertFalse(this.selectionModel.isSelectedIndex(1));
		assertFalse(this.selectionModel.isSelectedIndex(2));
		assertFalse(this.selectionModel.isSelectedIndex(3));
		assertFalse(this.selectionModel.isSelectedIndex(4));
		assertTrue(this.selectionModel.isSelectedIndex(5));
	}

	public void testAddSelectedValues() {
		this.listModel.addElement("joo");
		this.listModel.addElement("jar");
		this.listModel.addElement("jaz");
		this.selectionModel.setSelectedValue("foo");
		this.selectionModel.addSelectedValues(new Object[] {"bar", "jar"});
		assertEquals(0, this.selectionModel.getMinSelectionIndex());
		assertEquals(4, this.selectionModel.getMaxSelectionIndex());
		assertTrue(this.selectionModel.isSelectedIndex(0));
		assertTrue(this.selectionModel.isSelectedIndex(1));
		assertFalse(this.selectionModel.isSelectedIndex(2));
		assertFalse(this.selectionModel.isSelectedIndex(3));
		assertTrue(this.selectionModel.isSelectedIndex(4));
		assertFalse(this.selectionModel.isSelectedIndex(5));
	}

	public void testRemoveSelectedValue() {
		this.listModel.addElement("joo");
		this.listModel.addElement("jar");
		this.listModel.addElement("jaz");
		this.selectionModel.setSelectedValues(new Object[] {"foo", "baz", "jar"});
		this.selectionModel.removeSelectedValue("jar");
		assertEquals(0, this.selectionModel.getMinSelectionIndex());
		assertEquals(2, this.selectionModel.getMaxSelectionIndex());
		assertTrue(this.selectionModel.isSelectedIndex(0));
		assertFalse(this.selectionModel.isSelectedIndex(1));
		assertTrue(this.selectionModel.isSelectedIndex(2));
		assertFalse(this.selectionModel.isSelectedIndex(3));
		assertFalse(this.selectionModel.isSelectedIndex(4));
		assertFalse(this.selectionModel.isSelectedIndex(5));
	}

	public void testRemoveSelectedValues() {
		this.listModel.addElement("joo");
		this.listModel.addElement("jar");
		this.listModel.addElement("jaz");
		this.selectionModel.setSelectedValues(new Object[] {"foo", "baz", "joo", "jar"});
		this.selectionModel.removeSelectedValues(new Object[] {"foo", "joo"});
		assertEquals(2, this.selectionModel.getMinSelectionIndex());
		assertEquals(4, this.selectionModel.getMaxSelectionIndex());
		assertFalse(this.selectionModel.isSelectedIndex(0));
		assertFalse(this.selectionModel.isSelectedIndex(1));
		assertTrue(this.selectionModel.isSelectedIndex(2));
		assertFalse(this.selectionModel.isSelectedIndex(3));
		assertTrue(this.selectionModel.isSelectedIndex(4));
		assertFalse(this.selectionModel.isSelectedIndex(5));
	}

	public void testGetAnchorSelectedValue() {
		this.selectionModel.setAnchorSelectionIndex(1);
		assertEquals("bar", this.selectionModel.getAnchorSelectedValue());
	}

	public void testGetLeadSelectedValue() {
		this.selectionModel.setSelectedValue("bar");
		assertEquals("bar", this.selectionModel.getLeadSelectedValue());
		this.selectionModel.setSelectedValues(new Object[] {"foo", "baz"});
		assertEquals("baz", this.selectionModel.getLeadSelectedValue());
	}

	public void testGetMinMaxSelectedValue() {
		this.listModel.addElement("joo");
		this.listModel.addElement("jar");
		this.listModel.addElement("jaz");
		this.selectionModel.setSelectedValue("foo");
		this.selectionModel.addSelectedValues(new Object[] {"bar", "jar"});
		assertEquals("foo", this.selectionModel.getMinSelectedValue());
		assertEquals("jar", this.selectionModel.getMaxSelectedValue());
	}

	public void testValueIsSelected() {
		this.listModel.addElement("joo");
		this.listModel.addElement("jar");
		this.listModel.addElement("jaz");
		this.selectionModel.setSelectedValue("foo");
		this.selectionModel.addSelectedValues(new Object[] {"bar", "jar"});
		assertTrue(this.selectionModel.valueIsSelected("foo"));
		assertTrue(this.selectionModel.valueIsSelected("bar"));
		assertTrue(this.selectionModel.valueIsSelected("jar"));
		assertFalse(this.selectionModel.valueIsSelected("baz"));
	}

	public void testHasListeners() throws Exception {
		ListSelectionListener listener = this.buildListSelectionListener();
		assertEquals(0, this.listModel.getListDataListeners().length);
		this.selectionModel.addListSelectionListener(listener);
		assertEquals(1, this.listModel.getListDataListeners().length);
		this.selectionModel.removeListSelectionListener(listener);
		assertEquals(0, this.listModel.getListDataListeners().length);
	}

	private ListSelectionListener buildListSelectionListener() {
		return new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				// do nothing for now...
			}
		};
	}

}
