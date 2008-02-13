/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;

/**
 * This <code>IWidgetFactory</code> simply creates plain SWT widgets.
 *
 * @version 2.0
 * @since 2.0
 */
public final class DefaultWidgetFactory implements IWidgetFactory {

	/**
	 * The singleton instance of this <code>IWidgetFactory</code>
	 */
	private static final IWidgetFactory INSTANCE = new DefaultWidgetFactory();

	/**
	 * Creates a new <code>DefaultWidgetFactory</code>.
	 */
	private DefaultWidgetFactory() {
		super();
	}

	/**
	 * Returns the singleton instance of this <code>IWidgetFactory</code>.
	 *
	 * @return The singleton instance of this <code>IWidgetFactory</code>
	 */
	public static IWidgetFactory instance() {
		return INSTANCE;
	}

	/*
	 * (non-Javadoc)
	 */
	public Button createButton(Composite parent, String text) {
		return this.createButton(parent, text, SWT.NULL);
	}

	private Button createButton(Composite parent, String text, int style) {
		Button button = new Button(parent, style);
		button.setText(text);
		return button;
	}

	/*
	 * (non-Javadoc)
	 */
	public CCombo createCCombo(Composite parent) {
		return new CCombo(parent, SWT.BORDER | SWT.READ_ONLY);
	}

	/*
	 * (non-Javadoc)
	 */
	public Button createCheckBox(Composite parent, String text) {
		return this.createButton(parent, text, SWT.CHECK);
	}

	/*
	 * (non-Javadoc)
	 */
	public Combo createCombo(Composite parent) {
		return new Combo(parent, SWT.BORDER | SWT.READ_ONLY);
	}

	/*
	 * (non-Javadoc)
	 */
	public Composite createComposite(Composite parent) {
		return new Composite(parent, SWT.NULL);
	}

	/*
	 * (non-Javadoc)
	 */
	public CCombo createEditableCCombo(Composite parent) {
		return new CCombo(parent, SWT.BORDER);
	}

	/*
	 * (non-Javadoc)
	 */
	public Combo createEditableCombo(Composite parent) {
		return new Combo(parent, SWT.BORDER);
	}

	/*
	 * (non-Javadoc)
	 */
	public Group createGroup(Composite parent, String title) {
		Group group = new Group(parent, SWT.NULL);
		group.setText(title);
		return group;
	}

	/*
	 * (non-Javadoc)
	 */
	public Hyperlink createHyperlink(Composite parent, String text) {
		Hyperlink hyperlink = new Hyperlink(parent, SWT.NULL);
		hyperlink.setText(text);
		return hyperlink;
	}

	/*
	 * (non-Javadoc)
	 */
	public Label createLabel(Composite parent, String labelText) {
		Label label = new Label(parent, SWT.LEFT);
		label.setText(labelText);
		return label;
	}

	/*
	 * (non-Javadoc)
	 */
	public List createList(Composite parent, int style) {
		return new List(parent, SWT.BORDER | style);
	}

	/*
	 * (non-Javadoc)
	 */
	public Button createPushButton(Composite parent, String text) {
		return this.createButton(parent, text, SWT.PUSH);
	}

	/*
	 * (non-Javadoc)
	 */
	public Button createRadioButton(Composite parent, String text) {
		return this.createButton(parent, text, SWT.RADIO);
	}

	/*
	 * (non-Javadoc)
	 */
	public Section createSection(Composite parent, int style) {
		return new Section(parent, style);
	}

	/*
	 * (non-Javadoc)
	 */
	public Text createText(Composite parent) {
		return new Text(parent, SWT.BORDER);
	}

	/*
	 * (non-Javadoc)
	 */
	public Button createTriStateCheckBox(Composite parent, String text) {
		TriStateCheckBox checkBox = new TriStateCheckBox(parent, text, this);
		return checkBox.getCheckBox();
	}
}
