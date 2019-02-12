/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.widgets;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;

/**
 * This widget factory simply creates plain SWT widgets.
 * 
 * @see FormWidgetFactory
 */
public final class DefaultWidgetFactory
	implements WidgetFactory
{
	public static WidgetFactory instance() {
		return INSTANCE;
	}

	private static final WidgetFactory INSTANCE = new DefaultWidgetFactory();

	/**
	 * Ensure singleton.
	 */
	private DefaultWidgetFactory() {
		super();
	}

	public Button createButton(Composite parent, String text) {
		return this.createButton(parent, text, SWT.NONE);
	}

	public Button createCheckBox(Composite parent, String text) {
		return this.createButton(parent, text, SWT.CHECK);
	}

	public Combo createCombo(Composite parent) {
		return this.createCombo(parent, SWT.READ_ONLY);
	}

	public Composite createComposite(Composite parent) {
		return new Composite(parent, SWT.NONE);
	}
	
	public DateTime createDateTime(Composite parent, int style) {
		return new DateTime(parent, style);
	}

	public Combo createEditableCombo(Composite parent) {
		return this.createCombo(parent, SWT.NONE);
	}

	public Group createGroup(Composite parent, String title) {
		Group group = new Group(parent, SWT.NONE);
		group.setText(title);
		return group;
	}

	public Hyperlink createHyperlink(Composite parent, String text) {
		Hyperlink hyperlink = new Hyperlink(parent, SWT.NONE);
		hyperlink.setText(text);
		return hyperlink;
	}

	public Label createLabel(Composite parent, String labelText) {
		Label label = new Label(parent, SWT.WRAP);
		label.setText(labelText);
		return label;
	}

	public List createList(Composite parent, int style) {
		return new List(parent, SWT.BORDER | style);
	}

	public Text createMultiLineText(Composite parent) {
		return this.createText(parent, SWT.MULTI | SWT.V_SCROLL);
	}

	public Text createPasswordText(Composite parent) {
		return this.createText(parent, SWT.PASSWORD);
	}

	public Button createPushButton(Composite parent, String text) {
		return this.createButton(parent, text, SWT.PUSH);
	}

	public Button createRadioButton(Composite parent, String text) {
		return this.createButton(parent, text, SWT.RADIO);
	}

	public Section createSection(Composite parent, int style) {
		return new Section(parent, style);
	}

	public Spinner createSpinner(Composite parent) {
		return new Spinner(parent, SWT.NONE);
	}

	public Table createTable(Composite parent, int style) {
		return new Table(parent, SWT.BORDER | style);
	}

	public Text createText(Composite parent) {
		return this.createText(parent, SWT.NONE);
	}

	public Button createTriStateCheckBox(Composite parent, String text) {
		TriStateCheckBox checkBox = new TriStateCheckBox(parent, text, this);
		return checkBox.getCheckBox();
	}

	private Button createButton(Composite parent, String text, int style) {
		Button button = new Button(parent, style);
		button.setText(text);
		return button;
	}

	private Combo createCombo(Composite parent, int style) {
		return new Combo(parent, style | SWT.BORDER);
	}

	private Text createText(Composite parent, int style) {
		return new Text(parent, style | SWT.BORDER);
	}

	public void dispose() {
		// NOP
	}
}
