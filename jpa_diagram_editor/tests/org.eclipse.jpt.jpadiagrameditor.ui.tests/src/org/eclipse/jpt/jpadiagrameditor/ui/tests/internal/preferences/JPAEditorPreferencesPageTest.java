/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2010 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Stefan Dimov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.preferences.JPAEditorPreferenceInitializer;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.preferences.JPAEditorPreferencesPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JPAEditorPreferencesPageTest {

	private IPreferenceStore store;
	private String oldValue;

	@Before
	public void setUp() {
		store = JPADiagramEditorPlugin.getDefault().getPreferenceStore();
		oldValue = store
				.getString(JPAEditorPreferenceInitializer.PROPERTY_ENTITY_PACKAGE);
	}

	@After
	public void tearDown() {
		store.setValue(JPAEditorPreferenceInitializer.PROPERTY_ENTITY_PACKAGE,
				oldValue);
	}

	/*
	@Test
	public void testCheckState() {
		JPAEditorPreferencesPage page = createControl();
		assertNull(page.getErrorMessage());
	}

	@Test
	public void testCheckStateErrorMessage() {
		store.setValue(JPAEditorPreferenceInitializer.DEFAULT_ENTITY_PACKAGE,
				"d omd");
		JPAEditorPreferencesPage page = createControl();
		assertNotNull(page.getErrorMessage());
	}

	@Test
	public void testCheckStateWarningMessage() {
		store.setValue(JPAEditorPreferenceInitializer.DEFAULT_ENTITY_PACKAGE,
				"CapitalLetter");
		JPAEditorPreferencesPage page = createControl();
		assertNull(page.getMessage());
		assertTrue(page.isValid());
	}
	*/

	@Test
	public void testInit() {
		JPAEditorPreferencesPage page = new JPAEditorPreferencesPage();
		page.init(null);
	}

	@SuppressWarnings("unused")
	private JPAEditorPreferencesPage createControl() {
		JPAEditorPreferencesPage page = new JPAEditorPreferencesPage();
		Composite composite = new Composite(Display.getDefault()
				.getActiveShell(), SWT.NONE);
		page.createControl(composite);
		return page;
	}
}
