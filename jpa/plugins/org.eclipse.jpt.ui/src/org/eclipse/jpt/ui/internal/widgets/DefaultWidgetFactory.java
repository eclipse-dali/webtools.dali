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

import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

/**
 * This <code>WidgetFactory</code> simply creates plain SWT widgets.
 *
 * @version 2.0
 * @since 2.0
 */
public class DefaultWidgetFactory implements WidgetFactory {

	/**
	 * The singleton instance of this <code>IWidgetFactory</code>
	 */
	private static final WidgetFactory INSTANCE = new DefaultWidgetFactory();

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
	public static WidgetFactory instance() {
		return INSTANCE;
	}

	/*
	 * (non-Javadoc)
	 */
	public Button createButton(Composite parent, String text) {
		return this.createButton(parent, text, SWT.NULL);
	}

	/**
	 * Creates a new button.
	 *
	 * @param parent The parent container
	 * @param text The button's text
	 * @param style The style to apply to the button, which determines its type:
	 * toggle, push, check box, radio
	 * @return The newly created <code>Button</code>
	 */
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
		Label label = new Label(parent, SWT.WRAP);
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
	public FormText createMultiLineLabel(Composite parent, String labelText) {

		Composite container = new Composite(parent, SWT.NONE);

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		container.setLayoutData(gridData);

		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns   = 1;
		layout.bottomMargin = 0;
		layout.leftMargin   = 0;
		layout.rightMargin  = 0;
		layout.topMargin    = 0;
		container.setLayout(layout);

		FormToolkit widgetFactory = new FormToolkit(parent.getDisplay());
		FormText text = widgetFactory.createFormText(container, true);
		text.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		text.setText(labelText, false, false);

		return text;
	}

	/*
	 * (non-Javadoc)
	 */
	public Text createMultiLineText(Composite parent) {
		return new Text(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
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
	public Spinner createSpinner(Composite parent) {
		return new Spinner(parent, SWT.NULL);
	}

	/*
	 * (non-Javadoc)
	 */
	public Table createTable(Composite parent, int style) {
		return new Table(parent, SWT.BORDER | style);
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