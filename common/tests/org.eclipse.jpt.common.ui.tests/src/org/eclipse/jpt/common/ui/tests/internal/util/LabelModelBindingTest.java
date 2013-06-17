/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.tests.internal.util;

import static org.junit.Assert.assertEquals;
import org.eclipse.jpt.common.ui.internal.swt.bindings.SWTBindingTools;
import org.eclipse.jpt.common.ui.internal.swt.widgets.DisplayTools;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("nls")
public final class LabelModelBindingTest {

	private Composite composite;

	@Before
	public void setUp() {
		this.composite = new Composite(DisplayTools.getShell(), SWT.NONE);
		this.composite.setLayout(new GridLayout());
	}

	@After
	public void tearDown() {
		if (this.composite != null) {
			this.composite.dispose();
			this.composite = null;
		}
	}

	@Test
	public void testSetImage() {
		Image expectedImage = new Image(this.composite.getDisplay(), 16, 16);
		try {
			Label label = new Label(this.composite, SWT.NULL);
			ModifiablePropertyValueModel<Image> imageModel = new SimplePropertyValueModel<Image>();
			SWTBindingTools.bindImageLabel(imageModel, label);
			imageModel.setValue(expectedImage);
			assertEquals(expectedImage, label.getImage());
		} finally {
			expectedImage.dispose();
		}
	}

	@Test
	public void testSetImageDispose() {
		Image expectedImage = new Image(this.composite.getDisplay(), 16, 16);
		try {
			Label label = new Label(this.composite, SWT.NULL);
			ModifiablePropertyValueModel<Image> imageModel = new SimplePropertyValueModel<Image>();
			SWTBindingTools.bindImageLabel(imageModel, label);
			label.dispose();
			imageModel.setValue(expectedImage);
		} finally {
			expectedImage.dispose();
		}
	}

	@Test
	public void testSetText() {
		Label label = new Label(this.composite, SWT.NULL);
		ModifiablePropertyValueModel<String> textModel = new SimplePropertyValueModel<String>();
		SWTBindingTools.bindTextLabel(textModel, label);
		String expectedText = "This is a test";
		textModel.setValue(expectedText);
		assertEquals(expectedText, label.getText());
	}

	@Test
	public void testSetTextDispose() {
		Label label = new Label(this.composite, SWT.NULL);
		ModifiablePropertyValueModel<String> textModel = new SimplePropertyValueModel<String>();
		SWTBindingTools.bindTextLabel(textModel, label);
		label.dispose();
		String expectedString = "This is a test";
		textModel.setValue(expectedString);
	}
}
