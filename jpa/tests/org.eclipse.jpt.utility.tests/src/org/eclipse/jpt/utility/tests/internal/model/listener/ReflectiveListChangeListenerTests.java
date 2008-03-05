/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.model.listener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.model.listener.ReflectiveChangeListener;

import junit.framework.TestCase;

public class ReflectiveListChangeListenerTests extends TestCase {
	
	public ReflectiveListChangeListenerTests(String name) {
		super(name);
	}

	private ListChangeListener buildZeroArgumentListener(Object target) {
		return ReflectiveChangeListener.buildListChangeListener(target, "itemAddedZeroArgument", "itemRemovedZeroArgument", "itemReplacedZeroArgument", "itemMovedZeroArgument", "listClearedZeroArgument", "listChangedZeroArgument");
	}

	private ListChangeListener buildSingleArgumentListener(Object target) {
		return ReflectiveChangeListener.buildListChangeListener(target, "itemAddedSingleArgument", "itemRemovedSingleArgument", "itemReplacedSingleArgument", "itemMovedSingleArgument", "listClearedSingleArgument", "listChangedSingleArgument");
	}

	public void testItemAddedZeroArgument() {
		TestModel testModel = new TestModel();
		String string = "foo";
		Target target = new Target(testModel, TestModel.STRINGS_LIST, string, 0);
		testModel.addListChangeListener(this.buildZeroArgumentListener(target));
		testModel.addString(string);
		assertTrue(target.itemAddedZeroArgumentFlag);
		assertFalse(target.itemAddedSingleArgumentFlag);
		assertFalse(target.itemRemovedZeroArgumentFlag);
		assertFalse(target.itemRemovedSingleArgumentFlag);
		assertFalse(target.itemReplacedZeroArgumentFlag);
		assertFalse(target.itemReplacedSingleArgumentFlag);
		assertFalse(target.itemMovedZeroArgumentFlag);
		assertFalse(target.itemMovedSingleArgumentFlag);
		assertFalse(target.listClearedZeroArgumentFlag);
		assertFalse(target.listClearedSingleArgumentFlag);
		assertFalse(target.listChangedZeroArgumentFlag);
		assertFalse(target.listChangedSingleArgumentFlag);
	}

	public void testItemAddedZeroArgumentNamedList() {
		TestModel testModel = new TestModel();
		String string = "foo";
		Target target = new Target(testModel, TestModel.STRINGS_LIST, string, 0);
		testModel.addListChangeListener(TestModel.STRINGS_LIST, this.buildZeroArgumentListener(target));
		testModel.addString(string);
		assertTrue(target.itemAddedZeroArgumentFlag);
		assertFalse(target.itemAddedSingleArgumentFlag);
		assertFalse(target.itemRemovedZeroArgumentFlag);
		assertFalse(target.itemRemovedSingleArgumentFlag);
		assertFalse(target.itemReplacedZeroArgumentFlag);
		assertFalse(target.itemReplacedSingleArgumentFlag);
		assertFalse(target.itemMovedZeroArgumentFlag);
		assertFalse(target.itemMovedSingleArgumentFlag);
		assertFalse(target.listClearedZeroArgumentFlag);
		assertFalse(target.listClearedSingleArgumentFlag);
		assertFalse(target.listChangedZeroArgumentFlag);
		assertFalse(target.listChangedSingleArgumentFlag);
	}

	public void testItemAddedSingleArgument() {
		TestModel testModel = new TestModel();
		String string = "foo";
		Target target = new Target(testModel, TestModel.STRINGS_LIST, string, 0);
		testModel.addListChangeListener(this.buildSingleArgumentListener(target));
		testModel.addString(string);
		assertFalse(target.itemAddedZeroArgumentFlag);
		assertTrue(target.itemAddedSingleArgumentFlag);
		assertFalse(target.itemRemovedZeroArgumentFlag);
		assertFalse(target.itemRemovedSingleArgumentFlag);
		assertFalse(target.itemReplacedZeroArgumentFlag);
		assertFalse(target.itemReplacedSingleArgumentFlag);
		assertFalse(target.itemMovedZeroArgumentFlag);
		assertFalse(target.itemMovedSingleArgumentFlag);
		assertFalse(target.listClearedZeroArgumentFlag);
		assertFalse(target.listClearedSingleArgumentFlag);
		assertFalse(target.listChangedZeroArgumentFlag);
		assertFalse(target.listChangedSingleArgumentFlag);
	}

	public void testItemAddedSingleArgumentNamedList() {
		TestModel testModel = new TestModel();
		String string = "foo";
		Target target = new Target(testModel, TestModel.STRINGS_LIST, string, 0);
		testModel.addListChangeListener(TestModel.STRINGS_LIST, this.buildSingleArgumentListener(target));
		testModel.addString(string);
		assertFalse(target.itemAddedZeroArgumentFlag);
		assertTrue(target.itemAddedSingleArgumentFlag);
		assertFalse(target.itemRemovedZeroArgumentFlag);
		assertFalse(target.itemRemovedSingleArgumentFlag);
		assertFalse(target.itemReplacedZeroArgumentFlag);
		assertFalse(target.itemReplacedSingleArgumentFlag);
		assertFalse(target.itemMovedZeroArgumentFlag);
		assertFalse(target.itemMovedSingleArgumentFlag);
		assertFalse(target.listClearedZeroArgumentFlag);
		assertFalse(target.listClearedSingleArgumentFlag);
		assertFalse(target.listChangedZeroArgumentFlag);
		assertFalse(target.listChangedSingleArgumentFlag);
	}

	public void testItemRemovedZeroArgument() {
		TestModel testModel = new TestModel();
		String string = "foo";
		testModel.addString(string);
		Target target = new Target(testModel, TestModel.STRINGS_LIST, string, 0);
		testModel.addListChangeListener(this.buildZeroArgumentListener(target));
		testModel.removeString(string);
		assertFalse(target.itemAddedZeroArgumentFlag);
		assertFalse(target.itemAddedSingleArgumentFlag);
		assertTrue(target.itemRemovedZeroArgumentFlag);
		assertFalse(target.itemRemovedSingleArgumentFlag);
		assertFalse(target.itemReplacedZeroArgumentFlag);
		assertFalse(target.itemReplacedSingleArgumentFlag);
		assertFalse(target.itemMovedZeroArgumentFlag);
		assertFalse(target.itemMovedSingleArgumentFlag);
		assertFalse(target.listClearedZeroArgumentFlag);
		assertFalse(target.listClearedSingleArgumentFlag);
		assertFalse(target.listChangedZeroArgumentFlag);
		assertFalse(target.listChangedSingleArgumentFlag);
	}

	public void testItemRemovedZeroArgumentNamedList() {
		TestModel testModel = new TestModel();
		String string = "foo";
		testModel.addString(string);
		Target target = new Target(testModel, TestModel.STRINGS_LIST, string, 0);
		testModel.addListChangeListener(TestModel.STRINGS_LIST, this.buildZeroArgumentListener(target));
		testModel.removeString(string);
		assertFalse(target.itemAddedZeroArgumentFlag);
		assertFalse(target.itemAddedSingleArgumentFlag);
		assertTrue(target.itemRemovedZeroArgumentFlag);
		assertFalse(target.itemRemovedSingleArgumentFlag);
		assertFalse(target.itemReplacedZeroArgumentFlag);
		assertFalse(target.itemReplacedSingleArgumentFlag);
		assertFalse(target.itemMovedZeroArgumentFlag);
		assertFalse(target.itemMovedSingleArgumentFlag);
		assertFalse(target.listClearedZeroArgumentFlag);
		assertFalse(target.listClearedSingleArgumentFlag);
		assertFalse(target.listChangedZeroArgumentFlag);
		assertFalse(target.listChangedSingleArgumentFlag);
	}

	public void testItemRemovedSingleArgument() {
		TestModel testModel = new TestModel();
		String string = "foo";
		testModel.addString(string);
		Target target = new Target(testModel, TestModel.STRINGS_LIST, string, 0);
		testModel.addListChangeListener(this.buildSingleArgumentListener(target));
		testModel.removeString(string);
		assertFalse(target.itemAddedZeroArgumentFlag);
		assertFalse(target.itemAddedSingleArgumentFlag);
		assertFalse(target.itemRemovedZeroArgumentFlag);
		assertTrue(target.itemRemovedSingleArgumentFlag);
		assertFalse(target.itemReplacedZeroArgumentFlag);
		assertFalse(target.itemReplacedSingleArgumentFlag);
		assertFalse(target.itemMovedZeroArgumentFlag);
		assertFalse(target.itemMovedSingleArgumentFlag);
		assertFalse(target.listClearedZeroArgumentFlag);
		assertFalse(target.listClearedSingleArgumentFlag);
		assertFalse(target.listChangedZeroArgumentFlag);
		assertFalse(target.listChangedSingleArgumentFlag);
	}

	public void testItemRemovedSingleArgumentNamedList() {
		TestModel testModel = new TestModel();
		String string = "foo";
		testModel.addString(string);
		Target target = new Target(testModel, TestModel.STRINGS_LIST, string, 0);
		testModel.addListChangeListener(TestModel.STRINGS_LIST, this.buildSingleArgumentListener(target));
		testModel.removeString(string);
		assertFalse(target.itemAddedZeroArgumentFlag);
		assertFalse(target.itemAddedSingleArgumentFlag);
		assertFalse(target.itemRemovedZeroArgumentFlag);
		assertTrue(target.itemRemovedSingleArgumentFlag);
		assertFalse(target.itemReplacedZeroArgumentFlag);
		assertFalse(target.itemReplacedSingleArgumentFlag);
		assertFalse(target.itemMovedZeroArgumentFlag);
		assertFalse(target.itemMovedSingleArgumentFlag);
		assertFalse(target.listClearedZeroArgumentFlag);
		assertFalse(target.listClearedSingleArgumentFlag);
		assertFalse(target.listChangedZeroArgumentFlag);
		assertFalse(target.listChangedSingleArgumentFlag);
	}

	public void testItemReplacedZeroArgument() {
		TestModel testModel = new TestModel();
		String oldString = "foo";
		String newString = "bar";
		testModel.addString(oldString);
		Target target = new Target(testModel, TestModel.STRINGS_LIST, newString, 0, oldString);
		testModel.addListChangeListener(this.buildZeroArgumentListener(target));
		testModel.replaceString(oldString, newString);
		assertFalse(target.itemAddedZeroArgumentFlag);
		assertFalse(target.itemAddedSingleArgumentFlag);
		assertFalse(target.itemRemovedZeroArgumentFlag);
		assertFalse(target.itemRemovedSingleArgumentFlag);
		assertTrue(target.itemReplacedZeroArgumentFlag);
		assertFalse(target.itemReplacedSingleArgumentFlag);
		assertFalse(target.itemMovedZeroArgumentFlag);
		assertFalse(target.itemMovedSingleArgumentFlag);
		assertFalse(target.listClearedZeroArgumentFlag);
		assertFalse(target.listClearedSingleArgumentFlag);
		assertFalse(target.listChangedZeroArgumentFlag);
		assertFalse(target.listChangedSingleArgumentFlag);
	}

	public void testItemReplacedZeroArgumentNamedList() {
		TestModel testModel = new TestModel();
		String oldString = "foo";
		String newString = "bar";
		testModel.addString(oldString);
		Target target = new Target(testModel, TestModel.STRINGS_LIST, newString, 0, oldString);
		testModel.addListChangeListener(TestModel.STRINGS_LIST, this.buildZeroArgumentListener(target));
		testModel.replaceString(oldString, newString);
		assertFalse(target.itemAddedZeroArgumentFlag);
		assertFalse(target.itemAddedSingleArgumentFlag);
		assertFalse(target.itemRemovedZeroArgumentFlag);
		assertFalse(target.itemRemovedSingleArgumentFlag);
		assertTrue(target.itemReplacedZeroArgumentFlag);
		assertFalse(target.itemReplacedSingleArgumentFlag);
		assertFalse(target.itemMovedZeroArgumentFlag);
		assertFalse(target.itemMovedSingleArgumentFlag);
		assertFalse(target.listClearedZeroArgumentFlag);
		assertFalse(target.listClearedSingleArgumentFlag);
		assertFalse(target.listChangedZeroArgumentFlag);
		assertFalse(target.listChangedSingleArgumentFlag);
	}

	public void testItemReplacedSingleArgument() {
		TestModel testModel = new TestModel();
		String oldString = "foo";
		String newString = "bar";
		testModel.addString(oldString);
		Target target = new Target(testModel, TestModel.STRINGS_LIST, newString, 0, oldString);
		testModel.addListChangeListener(this.buildSingleArgumentListener(target));
		testModel.replaceString(oldString, newString);
		assertFalse(target.itemAddedZeroArgumentFlag);
		assertFalse(target.itemAddedSingleArgumentFlag);
		assertFalse(target.itemRemovedZeroArgumentFlag);
		assertFalse(target.itemRemovedSingleArgumentFlag);
		assertFalse(target.itemReplacedZeroArgumentFlag);
		assertTrue(target.itemReplacedSingleArgumentFlag);
		assertFalse(target.itemMovedZeroArgumentFlag);
		assertFalse(target.itemMovedSingleArgumentFlag);
		assertFalse(target.listClearedZeroArgumentFlag);
		assertFalse(target.listClearedSingleArgumentFlag);
		assertFalse(target.listChangedZeroArgumentFlag);
		assertFalse(target.listChangedSingleArgumentFlag);
	}

	public void testItemReplacedSingleArgumentNamedList() {
		TestModel testModel = new TestModel();
		String oldString = "foo";
		String newString = "bar";
		testModel.addString(oldString);
		Target target = new Target(testModel, TestModel.STRINGS_LIST, newString, 0, oldString);
		testModel.addListChangeListener(TestModel.STRINGS_LIST, this.buildSingleArgumentListener(target));
		testModel.replaceString(oldString, newString);
		assertFalse(target.itemAddedZeroArgumentFlag);
		assertFalse(target.itemAddedSingleArgumentFlag);
		assertFalse(target.itemRemovedZeroArgumentFlag);
		assertFalse(target.itemRemovedSingleArgumentFlag);
		assertFalse(target.itemReplacedZeroArgumentFlag);
		assertTrue(target.itemReplacedSingleArgumentFlag);
		assertFalse(target.itemMovedZeroArgumentFlag);
		assertFalse(target.itemMovedSingleArgumentFlag);
		assertFalse(target.listClearedZeroArgumentFlag);
		assertFalse(target.listClearedSingleArgumentFlag);
		assertFalse(target.listChangedZeroArgumentFlag);
		assertFalse(target.listChangedSingleArgumentFlag);
	}

	public void testItemMovedZeroArgument() {
		TestModel testModel = new TestModel();
		testModel.addString("zero");
		testModel.addString("one");
		testModel.addString("two");
		testModel.addString("three");
		Target target = new Target(testModel, TestModel.STRINGS_LIST, 0, 2);
		testModel.addListChangeListener(this.buildZeroArgumentListener(target));
		testModel.moveString(0, 2);
		assertFalse(target.itemAddedZeroArgumentFlag);
		assertFalse(target.itemAddedSingleArgumentFlag);
		assertFalse(target.itemRemovedZeroArgumentFlag);
		assertFalse(target.itemRemovedSingleArgumentFlag);
		assertFalse(target.itemReplacedZeroArgumentFlag);
		assertFalse(target.itemReplacedSingleArgumentFlag);
		assertTrue(target.itemMovedZeroArgumentFlag);
		assertFalse(target.itemMovedSingleArgumentFlag);
		assertFalse(target.listClearedZeroArgumentFlag);
		assertFalse(target.listClearedSingleArgumentFlag);
		assertFalse(target.listChangedZeroArgumentFlag);
		assertFalse(target.listChangedSingleArgumentFlag);
	}

	public void testItemMovedZeroArgumentNamedList() {
		TestModel testModel = new TestModel();
		testModel.addString("zero");
		testModel.addString("one");
		testModel.addString("two");
		testModel.addString("three");
		Target target = new Target(testModel, TestModel.STRINGS_LIST, 0, 2);
		testModel.addListChangeListener(TestModel.STRINGS_LIST, this.buildZeroArgumentListener(target));
		testModel.moveString(0, 2);
		assertFalse(target.itemAddedZeroArgumentFlag);
		assertFalse(target.itemAddedSingleArgumentFlag);
		assertFalse(target.itemRemovedZeroArgumentFlag);
		assertFalse(target.itemRemovedSingleArgumentFlag);
		assertFalse(target.itemReplacedZeroArgumentFlag);
		assertFalse(target.itemReplacedSingleArgumentFlag);
		assertTrue(target.itemMovedZeroArgumentFlag);
		assertFalse(target.itemMovedSingleArgumentFlag);
		assertFalse(target.listClearedZeroArgumentFlag);
		assertFalse(target.listClearedSingleArgumentFlag);
		assertFalse(target.listChangedZeroArgumentFlag);
		assertFalse(target.listChangedSingleArgumentFlag);
	}

	public void testItemMovedSingleArgument() {
		TestModel testModel = new TestModel();
		testModel.addString("zero");
		testModel.addString("one");
		testModel.addString("two");
		testModel.addString("three");
		Target target = new Target(testModel, TestModel.STRINGS_LIST, 0, 2);
		testModel.addListChangeListener(this.buildSingleArgumentListener(target));
		testModel.moveString(0, 2);
		assertFalse(target.itemAddedZeroArgumentFlag);
		assertFalse(target.itemAddedSingleArgumentFlag);
		assertFalse(target.itemRemovedZeroArgumentFlag);
		assertFalse(target.itemRemovedSingleArgumentFlag);
		assertFalse(target.itemReplacedZeroArgumentFlag);
		assertFalse(target.itemReplacedSingleArgumentFlag);
		assertFalse(target.itemMovedZeroArgumentFlag);
		assertTrue(target.itemMovedSingleArgumentFlag);
		assertFalse(target.listClearedZeroArgumentFlag);
		assertFalse(target.listClearedSingleArgumentFlag);
		assertFalse(target.listChangedZeroArgumentFlag);
		assertFalse(target.listChangedSingleArgumentFlag);
	}

	public void testItemMovedSingleArgumentNamedList() {
		TestModel testModel = new TestModel();
		testModel.addString("zero");
		testModel.addString("one");
		testModel.addString("two");
		testModel.addString("three");
		Target target = new Target(testModel, TestModel.STRINGS_LIST, 0, 2);
		testModel.addListChangeListener(TestModel.STRINGS_LIST, this.buildSingleArgumentListener(target));
		testModel.moveString(0, 2);
		assertFalse(target.itemAddedZeroArgumentFlag);
		assertFalse(target.itemAddedSingleArgumentFlag);
		assertFalse(target.itemRemovedZeroArgumentFlag);
		assertFalse(target.itemRemovedSingleArgumentFlag);
		assertFalse(target.itemReplacedZeroArgumentFlag);
		assertFalse(target.itemReplacedSingleArgumentFlag);
		assertFalse(target.itemMovedZeroArgumentFlag);
		assertTrue(target.itemMovedSingleArgumentFlag);
		assertFalse(target.listClearedZeroArgumentFlag);
		assertFalse(target.listClearedSingleArgumentFlag);
		assertFalse(target.listChangedZeroArgumentFlag);
		assertFalse(target.listChangedSingleArgumentFlag);
	}

	public void testListClearedZeroArgument() {
		TestModel testModel = new TestModel();
		String string = "foo";
		testModel.addString(string);
		Target target = new Target(testModel, TestModel.STRINGS_LIST, null, -1);
		testModel.addListChangeListener(this.buildZeroArgumentListener(target));
		testModel.clearStrings();
		assertFalse(target.itemAddedZeroArgumentFlag);
		assertFalse(target.itemAddedSingleArgumentFlag);
		assertFalse(target.itemRemovedZeroArgumentFlag);
		assertFalse(target.itemRemovedSingleArgumentFlag);
		assertFalse(target.itemReplacedZeroArgumentFlag);
		assertFalse(target.itemReplacedSingleArgumentFlag);
		assertFalse(target.itemMovedZeroArgumentFlag);
		assertFalse(target.itemMovedSingleArgumentFlag);
		assertTrue(target.listClearedZeroArgumentFlag);
		assertFalse(target.listClearedSingleArgumentFlag);
		assertFalse(target.listChangedZeroArgumentFlag);
		assertFalse(target.listChangedSingleArgumentFlag);
	}

	public void testListClearedZeroArgumentNamedList() {
		TestModel testModel = new TestModel();
		String string = "foo";
		testModel.addString(string);
		Target target = new Target(testModel, TestModel.STRINGS_LIST, null, -1);
		testModel.addListChangeListener(TestModel.STRINGS_LIST, this.buildZeroArgumentListener(target));
		testModel.clearStrings();
		assertFalse(target.itemAddedZeroArgumentFlag);
		assertFalse(target.itemAddedSingleArgumentFlag);
		assertFalse(target.itemRemovedZeroArgumentFlag);
		assertFalse(target.itemRemovedSingleArgumentFlag);
		assertFalse(target.itemReplacedZeroArgumentFlag);
		assertFalse(target.itemReplacedSingleArgumentFlag);
		assertFalse(target.itemMovedZeroArgumentFlag);
		assertFalse(target.itemMovedSingleArgumentFlag);
		assertTrue(target.listClearedZeroArgumentFlag);
		assertFalse(target.listClearedSingleArgumentFlag);
		assertFalse(target.listChangedZeroArgumentFlag);
		assertFalse(target.listChangedSingleArgumentFlag);
	}

	public void testListClearedSingleArgument() {
		TestModel testModel = new TestModel();
		String string = "foo";
		testModel.addString(string);
		Target target = new Target(testModel, TestModel.STRINGS_LIST, null, -1);
		testModel.addListChangeListener(this.buildSingleArgumentListener(target));
		testModel.clearStrings();
		assertFalse(target.itemAddedZeroArgumentFlag);
		assertFalse(target.itemAddedSingleArgumentFlag);
		assertFalse(target.itemRemovedZeroArgumentFlag);
		assertFalse(target.itemRemovedSingleArgumentFlag);
		assertFalse(target.itemReplacedZeroArgumentFlag);
		assertFalse(target.itemReplacedSingleArgumentFlag);
		assertFalse(target.itemMovedZeroArgumentFlag);
		assertFalse(target.itemMovedSingleArgumentFlag);
		assertFalse(target.listClearedZeroArgumentFlag);
		assertTrue(target.listClearedSingleArgumentFlag);
		assertFalse(target.listChangedZeroArgumentFlag);
		assertFalse(target.listChangedSingleArgumentFlag);
	}

	public void testListClearedSingleArgumentNamedList() {
		TestModel testModel = new TestModel();
		String string = "foo";
		testModel.addString(string);
		Target target = new Target(testModel, TestModel.STRINGS_LIST, null, -1);
		testModel.addListChangeListener(TestModel.STRINGS_LIST, this.buildSingleArgumentListener(target));
		testModel.clearStrings();
		assertFalse(target.itemAddedZeroArgumentFlag);
		assertFalse(target.itemAddedSingleArgumentFlag);
		assertFalse(target.itemRemovedZeroArgumentFlag);
		assertFalse(target.itemRemovedSingleArgumentFlag);
		assertFalse(target.itemReplacedZeroArgumentFlag);
		assertFalse(target.itemReplacedSingleArgumentFlag);
		assertFalse(target.itemMovedZeroArgumentFlag);
		assertFalse(target.itemMovedSingleArgumentFlag);
		assertFalse(target.listClearedZeroArgumentFlag);
		assertTrue(target.listClearedSingleArgumentFlag);
		assertFalse(target.listChangedZeroArgumentFlag);
		assertFalse(target.listChangedSingleArgumentFlag);
	}

	public void testListChangedZeroArgument() {
		TestModel testModel = new TestModel();
		String string = "foo";
		testModel.addString(string);
		Target target = new Target(testModel, TestModel.STRINGS_LIST, null, -1);
		testModel.addListChangeListener(this.buildZeroArgumentListener(target));
		testModel.replaceAllStrings(new String[] {"bar", "baz"});
		assertFalse(target.itemAddedZeroArgumentFlag);
		assertFalse(target.itemAddedSingleArgumentFlag);
		assertFalse(target.itemRemovedZeroArgumentFlag);
		assertFalse(target.itemRemovedSingleArgumentFlag);
		assertFalse(target.itemReplacedZeroArgumentFlag);
		assertFalse(target.itemReplacedSingleArgumentFlag);
		assertFalse(target.itemMovedZeroArgumentFlag);
		assertFalse(target.itemMovedSingleArgumentFlag);
		assertFalse(target.listClearedZeroArgumentFlag);
		assertFalse(target.listClearedSingleArgumentFlag);
		assertTrue(target.listChangedZeroArgumentFlag);
		assertFalse(target.listChangedSingleArgumentFlag);
	}

	public void testListChangedZeroArgumentNamedList() {
		TestModel testModel = new TestModel();
		String string = "foo";
		testModel.addString(string);
		Target target = new Target(testModel, TestModel.STRINGS_LIST, null, -1);
		testModel.addListChangeListener(TestModel.STRINGS_LIST, this.buildZeroArgumentListener(target));
		testModel.replaceAllStrings(new String[] {"bar", "baz"});
		assertFalse(target.itemAddedZeroArgumentFlag);
		assertFalse(target.itemAddedSingleArgumentFlag);
		assertFalse(target.itemRemovedZeroArgumentFlag);
		assertFalse(target.itemRemovedSingleArgumentFlag);
		assertFalse(target.itemReplacedZeroArgumentFlag);
		assertFalse(target.itemReplacedSingleArgumentFlag);
		assertFalse(target.itemMovedZeroArgumentFlag);
		assertFalse(target.itemMovedSingleArgumentFlag);
		assertFalse(target.listClearedZeroArgumentFlag);
		assertFalse(target.listClearedSingleArgumentFlag);
		assertTrue(target.listChangedZeroArgumentFlag);
		assertFalse(target.listChangedSingleArgumentFlag);
	}

	public void testListChangedSingleArgument() {
		TestModel testModel = new TestModel();
		String string = "foo";
		testModel.addString(string);
		Target target = new Target(testModel, TestModel.STRINGS_LIST, null, -1);
		testModel.addListChangeListener(this.buildSingleArgumentListener(target));
		testModel.replaceAllStrings(new String[] {"bar", "baz"});
		assertFalse(target.itemAddedZeroArgumentFlag);
		assertFalse(target.itemAddedSingleArgumentFlag);
		assertFalse(target.itemRemovedZeroArgumentFlag);
		assertFalse(target.itemRemovedSingleArgumentFlag);
		assertFalse(target.itemReplacedZeroArgumentFlag);
		assertFalse(target.itemReplacedSingleArgumentFlag);
		assertFalse(target.itemMovedZeroArgumentFlag);
		assertFalse(target.itemMovedSingleArgumentFlag);
		assertFalse(target.listClearedZeroArgumentFlag);
		assertFalse(target.listClearedSingleArgumentFlag);
		assertFalse(target.listChangedZeroArgumentFlag);
		assertTrue(target.listChangedSingleArgumentFlag);
	}

	public void testListChangedSingleArgumentNamedList() {
		TestModel testModel = new TestModel();
		String string = "foo";
		testModel.addString(string);
		Target target = new Target(testModel, TestModel.STRINGS_LIST, null, -1);
		testModel.addListChangeListener(TestModel.STRINGS_LIST, this.buildSingleArgumentListener(target));
		testModel.replaceAllStrings(new String[] {"bar", "baz"});
		assertFalse(target.itemAddedZeroArgumentFlag);
		assertFalse(target.itemAddedSingleArgumentFlag);
		assertFalse(target.itemRemovedZeroArgumentFlag);
		assertFalse(target.itemRemovedSingleArgumentFlag);
		assertFalse(target.itemReplacedZeroArgumentFlag);
		assertFalse(target.itemReplacedSingleArgumentFlag);
		assertFalse(target.itemMovedZeroArgumentFlag);
		assertFalse(target.itemMovedSingleArgumentFlag);
		assertFalse(target.listClearedZeroArgumentFlag);
		assertFalse(target.listClearedSingleArgumentFlag);
		assertFalse(target.listChangedZeroArgumentFlag);
		assertTrue(target.listChangedSingleArgumentFlag);
	}

	public void testBogusDoubleArgument1() {
		TestModel testModel = new TestModel();
		String string = "foo";
		Target target = new Target(testModel, TestModel.STRINGS_LIST, string, 0);
		boolean exCaught = false;
		try {
			ListChangeListener listener = ReflectiveChangeListener.buildListChangeListener(target, "listChangedDoubleArgument");
			fail("bogus listener: " + listener);
		} catch (RuntimeException ex) {
			if (ex.getCause().getClass() == NoSuchMethodException.class) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
	}

	public void testBogusDoubleArgument2() throws Exception {
		TestModel testModel = new TestModel();
		String string = "foo";
		Target target = new Target(testModel, TestModel.STRINGS_LIST, string, 0);
		Method method = ClassTools.method(target, "listChangedDoubleArgument", new Class[] {ListChangeEvent.class, Object.class});
		boolean exCaught = false;
		try {
			ListChangeListener listener = ReflectiveChangeListener.buildListChangeListener(target, method);
			fail("bogus listener: " + listener);
		} catch (RuntimeException ex) {
			if (ex.getMessage().equals(method.toString())) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
	}

	public void testListenerMismatch() {
		TestModel testModel = new TestModel();
		String string = "foo";
		Target target = new Target(testModel, TestModel.STRINGS_LIST, string, 0);
		// build a LIST change listener and hack it so we
		// can add it as a COLLECTION change listener
		Object listener = ReflectiveChangeListener.buildListChangeListener(target, "itemAddedSingleArgument");
		testModel.addCollectionChangeListener((CollectionChangeListener) listener);

		boolean exCaught = false;
		try {
			testModel.changeCollection();
			fail("listener mismatch: " + listener);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}


	private class TestModel extends AbstractModel {
		private List<String> strings = new ArrayList<String>();
			public static final String STRINGS_LIST = "strings";
		TestModel() {
			super();
		}
		ListIterator<String> strings() {
			return new CloneListIterator<String>(this.strings);
		}
		void addString(String string) {
			this.addItemToList(string, this.strings, STRINGS_LIST);
		}
		void removeString(String string) {
			this.removeItemFromList(this.strings.indexOf(string), this.strings, STRINGS_LIST);
		}
		void replaceString(String oldString, String newString) {
			this.setItemInList(this.strings.indexOf(oldString), newString, this.strings, STRINGS_LIST);
		}
		void moveString(int targetIndex, int sourceIndex) {
			this.moveItemInList(targetIndex, sourceIndex, this.strings, STRINGS_LIST);
		}
		void clearStrings() {
			this.clearList(this.strings, STRINGS_LIST);
		}
		void replaceAllStrings(String[] newStrings) {
			this.strings.clear();
			CollectionTools.addAll(this.strings, newStrings);
			this.fireListChanged(STRINGS_LIST);
		}
		void changeCollection() {
			this.fireCollectionChanged("bogus collection");
		}
	}

	private class Target {
		TestModel testModel;
		String listName;
		String string;
		int index;
		String replacedString;
		int sourceIndex;
		boolean itemAddedZeroArgumentFlag = false;
		boolean itemAddedSingleArgumentFlag = false;
		boolean itemRemovedZeroArgumentFlag = false;
		boolean itemRemovedSingleArgumentFlag = false;
		boolean itemReplacedZeroArgumentFlag = false;
		boolean itemReplacedSingleArgumentFlag = false;
		boolean itemMovedZeroArgumentFlag = false;
		boolean itemMovedSingleArgumentFlag = false;
		boolean listClearedZeroArgumentFlag = false;
		boolean listClearedSingleArgumentFlag = false;
		boolean listChangedZeroArgumentFlag = false;
		boolean listChangedSingleArgumentFlag = false;
		Target(TestModel testModel, String listName, String string, int index) {
			super();
			this.testModel = testModel;
			this.listName = listName;
			this.string = string;
			this.index = index;
		}
		Target(TestModel testModel, String listName, String string, int index, String replacedString) {
			this(testModel, listName, string, index);
			this.replacedString = replacedString;
		}
		Target(TestModel testModel, String listName, int targetIndex, int sourceIndex) {
			super();
			this.testModel = testModel;
			this.listName = listName;
			this.index = targetIndex;
			this.sourceIndex = sourceIndex;
		}
		void itemAddedZeroArgument() {
			this.itemAddedZeroArgumentFlag = true;
		}
		void itemAddedSingleArgument(ListChangeEvent e) {
			this.itemAddedSingleArgumentFlag = true;
			assertSame(this.testModel, e.getSource());
			assertEquals(this.listName, e.listName());
			assertEquals(this.string, e.items().next());
			assertEquals(this.index, e.index());
		}
		void itemRemovedZeroArgument() {
			this.itemRemovedZeroArgumentFlag = true;
		}
		void itemRemovedSingleArgument(ListChangeEvent e) {
			this.itemRemovedSingleArgumentFlag = true;
			assertSame(this.testModel, e.getSource());
			assertEquals(this.listName, e.listName());
			assertEquals(this.string, e.items().next());
			assertEquals(this.index, e.index());
		}
		void itemReplacedZeroArgument() {
			this.itemReplacedZeroArgumentFlag = true;
		}
		void itemReplacedSingleArgument(ListChangeEvent e) {
			this.itemReplacedSingleArgumentFlag = true;
			assertSame(this.testModel, e.getSource());
			assertEquals(this.listName, e.listName());
			assertEquals(this.string, e.items().next());
			assertEquals(this.replacedString, e.replacedItems().next());
			assertEquals(this.index, e.index());
		}
		void itemMovedZeroArgument() {
			this.itemMovedZeroArgumentFlag = true;
		}
		void itemMovedSingleArgument(ListChangeEvent e) {
			this.itemMovedSingleArgumentFlag = true;
			assertSame(this.testModel, e.getSource());
			assertEquals(this.listName, e.listName());
			assertEquals(this.index, e.targetIndex());
			assertEquals(this.sourceIndex, e.sourceIndex());
		}
		void listChangedZeroArgument() {
			this.listChangedZeroArgumentFlag = true;
		}
		void listClearedSingleArgument(ListChangeEvent e) {
			this.listClearedSingleArgumentFlag = true;
			assertSame(this.testModel, e.getSource());
			assertEquals(this.listName, e.listName());
			assertFalse(e.items().hasNext());
			assertEquals(this.index, e.index());
		}
		void listClearedZeroArgument() {
			this.listClearedZeroArgumentFlag = true;
		}
		void listChangedSingleArgument(ListChangeEvent e) {
			this.listChangedSingleArgumentFlag = true;
			assertSame(this.testModel, e.getSource());
			assertEquals(this.listName, e.listName());
			assertFalse(e.items().hasNext());
			assertEquals(this.index, e.index());
		}
		void listChangedDoubleArgument(ListChangeEvent e, Object o) {
			fail("bogus event: " + e);
		}
	}

}
