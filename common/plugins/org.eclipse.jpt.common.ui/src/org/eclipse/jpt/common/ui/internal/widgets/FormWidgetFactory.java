/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.widgets;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
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
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

/**
 * This widget factory delegates its behavior to a {@link FormToolkit}.
 * in order use the <em>form</em> style (i.e. the flat style)
 * look and feel. Clients must call {@link #dispose()}
 * when they are finished using the widget factory.
 *
 * @see DefaultWidgetFactory
 */
public class FormWidgetFactory
	implements WidgetFactory
{
	private final FormToolkit formToolkit;


	/**
	 * Construct a widget factory that delegates to the specified
	 * <em>form</em> toolkit.
	 * <p>
	 * Any client that calls this constructor must call {@link #dispose()} when
	 * it is finished using the resulting widget factory.
	 */
	public FormWidgetFactory(FormToolkit formToolkit) {
		super();
		if (formToolkit == null) {
			throw new NullPointerException();
		}
		this.formToolkit = formToolkit;
	}

	public Button createButton(Composite parent, String text) {
		return this.createButton(parent, text, SWT.NULL);
	}

	public Button createCheckBox(Composite parent, String text) {
		return this.createButton(parent, text, SWT.CHECK);
	}

	public Combo createCombo(Composite parent) {
		return this.createCombo(parent, SWT.READ_ONLY);
	}

	public Composite createComposite(Composite parent) {
		return this.formToolkit.createComposite(parent);
	}

	public DateTime createDateTime(Composite parent, int style) {
		parent = this.createBorderContainer(parent);

		DateTime dateTime = new DateTime(parent, style | SWT.FLAT);
		dateTime.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		this.formToolkit.adapt(dateTime, true, false);

		return dateTime;
	}

	public Combo createEditableCombo(Composite parent) {
		return this.createCombo(parent, SWT.NONE);
	}

	public Group createGroup(Composite parent, String title) {
		Group group = new Group(parent, SWT.NULL);
		group.setText(title);
		return group;
	}

	public Hyperlink createHyperlink(Composite parent, String text) {
		return this.formToolkit.createHyperlink(parent, text, SWT.FLAT);
	}

	public Label createLabel(Composite container, String labelText) {
		return this.formToolkit.createLabel(container, labelText, SWT.WRAP);
	}

	public List createList(Composite container, int style) {
		List list = new List(container, SWT.FLAT | style);
		list.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		return list;
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
		return this.formToolkit.createSection(parent, SWT.FLAT | style);
	}

	public ScrolledForm createScrolledForm(Composite parent) {
		return this.formToolkit.createScrolledForm(parent);
	}

	public Spinner createSpinner(Composite parent) {
		parent = this.createBorderContainer(parent);

		Spinner spinner = new Spinner(parent, SWT.FLAT);
		spinner.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		this.formToolkit.adapt(spinner, true, false);

		return spinner;
	}

	public Table createTable(Composite parent, int style) {
		Table table = this.formToolkit.createTable(parent, SWT.BORDER | style);
		table.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		return table;
	}

	public Text createText(Composite parent) {
		return this.createText(parent, SWT.NONE);
	}

	private Text createText(Composite parent, int style) {
		return this.formToolkit.createText(parent, null, SWT.BORDER | SWT.FLAT | style);
	}

	public Button createTriStateCheckBox(Composite parent, String text) {
		TriStateCheckBox checkBox = new TriStateCheckBox(parent, text, this);
		return checkBox.getCheckBox();
	}

	/**
	 * Wraps the given <code>Composite</code> into a new <code>Composite</code>
	 * in order to have the widgets' border painted. Except for <code>CCombo</code>,
	 * the top and bottom margins have to be 2 pixel and the left and right
	 * margins have to be 1 pixel.
	 */
	private Composite createBorderContainer(Composite parent) {
		return this.createBorderContainer(parent, 2, 1);
	}

	private Composite createBorderContainer(Composite parent, int marginHeight, int marginWidth) {
		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = marginHeight;
		layout.marginWidth  = marginWidth;

		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;

		Composite composite = this.formToolkit.createComposite(parent);
		composite.setLayoutData(gridData);
		composite.setLayout(layout);

		return composite;
	}

	private Button createButton(Composite parent, String text, int style) {
		return this.formToolkit.createButton(parent, text, SWT.FLAT | style);
	}

	private Combo createCombo(Composite parent, int style) {
		return new Combo(parent, style | SWT.FLAT);
	}

	public void dispose() {
		this.formToolkit.dispose();
	}
}
