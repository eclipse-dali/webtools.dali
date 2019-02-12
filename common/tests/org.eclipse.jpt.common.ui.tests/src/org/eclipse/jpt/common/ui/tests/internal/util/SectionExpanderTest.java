/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.tests.internal.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.eclipse.jpt.common.ui.internal.swt.bindings.SWTBindingTools;
import org.eclipse.jpt.common.ui.internal.swt.widgets.DisplayTools;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("nls")
public final class SectionExpanderTest {

	private Composite parent;

	@Before
	public void setUp() {
		this.parent = new Composite(DisplayTools.getShell(), SWT.NONE);
		this.parent.setLayout(new GridLayout());
	}

	@After
	public void tearDown() {
		if (this.parent != null) {
			this.parent.dispose();
		}
	}

	@Test
	public void testSwitchState() {

		SimplePropertyValueModel<Boolean> booleanModel =
			new SimplePropertyValueModel<Boolean>(Boolean.FALSE);

		Section section = new Section(this.parent, ExpandableComposite.TWISTIE);

		SWTBindingTools.bindExpandedState(booleanModel, section);

		assertFalse(
			"The Section should not be expanded",
			section.isExpanded()
		);

		// Change state (null)
		booleanModel.setValue(null);

		assertFalse(
			"The Section should not be expanded",
			section.isExpanded()
		);

		// Change state (true)
		booleanModel.setValue(Boolean.TRUE);

		assertTrue(
			"The Combo should be enabled",
			section.isExpanded()
		);

		// Change state (false)
		booleanModel.setValue(Boolean.FALSE);

		assertFalse(
			"The Combo should not be enabled",
			section.isExpanded()
		);

		// Dispose
		section.dispose();
		booleanModel.setValue(Boolean.TRUE);
	}
}
