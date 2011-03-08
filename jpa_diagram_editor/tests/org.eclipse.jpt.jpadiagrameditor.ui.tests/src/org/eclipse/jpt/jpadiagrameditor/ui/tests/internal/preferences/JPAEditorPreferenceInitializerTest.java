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

import static org.junit.Assert.*;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.preferences.JPAEditorPreferenceInitializer;
import org.junit.Test;

public class JPAEditorPreferenceInitializerTest {
	@Test
	public void testInitializeDefaultPreferences() {
		IPreferenceStore store = new PreferenceStore();
		AbstractPreferenceInitializer fixture = new JPAEditorPreferenceInitializer(
				store);
		fixture.initializeDefaultPreferences();
		String pack = store
				.getDefaultString(JPAEditorPreferenceInitializer.PROPERTY_ENTITY_PACKAGE);
		assertEquals(JPAEditorPreferenceInitializer.PROPERTY_VAL_ENTITY_PACKAGE, pack);
	}

}
