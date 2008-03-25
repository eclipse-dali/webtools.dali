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

import org.eclipse.core.runtime.Assert;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
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
 * This <code>WidgetFactory</code> is responsible to create the widgets
 * using the <code>FormToolkit</code> in order use the form style (flat-style)
 * look and feel.
 *
 * @see FormToolkit
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public class FormWidgetFactory implements WidgetFactory {

	/**
	 * The actual factory responsible for creating the new widgets.
	 */
	private final FormToolkit widgetFactory;

	/**
	 * Creates a new <code>FormWidgetFactory</code>.
	 *
	 * @param widgetFactory The actual factory responsible for creating the new
	 * widgets
	 */
	public FormWidgetFactory(FormToolkit widgetFactory) {
		super();

		Assert.isNotNull(widgetFactory, "The widget factory cannot be null");
		this.widgetFactory = widgetFactory;
	}

	/**
	 * Wraps the given <code>Composite</code> into a new <code>Composite</code>
	 * in order to have the widgets' border painted. Except for <code>CCombo</code>,
	 * the top and bottom margins have to be 2 pixel and the left and right
	 * margins have to be 1 pixel.
	 *
	 * @param container The parent of the sub-pane
	 * @return A new <code>Composite</code> that has the necessary space to paint
	 * the border
	 */
	protected Composite createBorderContainer(Composite container) {

		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		layout.marginWidth  = 0;
		layout.marginTop    = 2;
		layout.marginLeft   = 1;
		layout.marginBottom = 2;
		layout.marginRight  = 1;

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;

		container = widgetFactory.createComposite(container);
		container.setLayoutData(gridData);
		container.setLayout(layout);

		return container;
	}

	/*
	 * (non-Javadoc)
	 */
	public Button createButton(Composite parent, String text) {
		return createButton(parent, text, SWT.NULL);
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
	protected Button createButton(Composite parent, String text, int style) {
		return widgetFactory.createButton(parent, text, SWT.FLAT | style);
	}

	/*
	 * (non-Javadoc)
	 */
	public CCombo createCCombo(Composite parent) {
		return createCCombo(parent, SWT.READ_ONLY);
	}

	/**
	 * Creates a new combo.
	 *
	 * @param parent The parent container
	 * @param style The style to apply to the combo, usually read-only, flat
	 * @return The newly created <code>CCombo</code>
	 */
	protected CCombo createCCombo(Composite parent, int style) {
		parent = createBorderContainer(parent);

		CCombo combo = new CCombo(parent, style);
		widgetFactory.adapt(combo, true, false);

		// Bugzilla 145837 - workaround for no borders on Windows XP
		if (widgetFactory.getBorderStyle() == SWT.BORDER) {
			combo.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		}

		return combo;
	}

	/*
	 * (non-Javadoc)
	 */
	public Button createCheckBox(Composite parent, String text) {
		return createButton(parent, text, SWT.CHECK);
	}

	/*
	 * (non-Javadoc)
	 */
	public Combo createCombo(Composite parent) {
		return new Combo(parent, SWT.READ_ONLY);
	}

	/*
	 * (non-Javadoc)
	 */
	public Composite createComposite(Composite parent) {
		Composite composite = widgetFactory.createComposite(parent);
      widgetFactory.paintBordersFor(composite);
		return composite;
	}

	/*
	 * (non-Javadoc)
	 */
	public CCombo createEditableCCombo(Composite parent) {
		return createCCombo(parent, SWT.NULL);
	}

	/*
	 * (non-Javadoc)
	 */
	public Combo createEditableCombo(Composite parent) {
		Combo combo = new Combo(parent, SWT.FLAT);
		combo.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		return combo;
	}

	/*
	 * (non-Javadoc)
	 */
	public Group createGroup(Composite parent, String title) {
		Group group = new Group(parent, SWT.SHADOW_NONE);
		group.setText(title);
		group.setBackground(widgetFactory.getColors().getBackground());
		group.setForeground(widgetFactory.getColors().getForeground());
		return group;
	}

	/*
	 * (non-Javadoc)
	 */
	public Hyperlink createHyperlink(Composite parent, String text) {
		return widgetFactory.createHyperlink(parent, text, SWT.FLAT);
	}

	/*
	 * (non-Javadoc)
	 */
	public Label createLabel(Composite container, String labelText) {
		return widgetFactory.createLabel(container, labelText, SWT.WRAP);
	}

	/*
	 * (non-Javadoc)
	 */
	public List createList(Composite container, int style) {
		List list = new List(container, SWT.FLAT | style);
		list.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		return list;
	}

	/*
	 * (non-Javadoc)
	 */
	public FormText createMultiLineLabel(Composite parent, String labelText) {

		Composite container = widgetFactory.createComposite(parent, SWT.NONE);

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

		FormText text = widgetFactory.createFormText(container, true);
		text.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		text.setText(labelText, false, false);

		return text;
	}

	/*
	 * (non-Javadoc)
	 */
	public Text createMultiLineText(Composite parent) {
		return createText(parent, SWT.MULTI | SWT.V_SCROLL);
	}

	/*
	 * (non-Javadoc)
	 */
	public Button createPushButton(Composite parent, String text) {
		return createButton(parent, text, SWT.PUSH);
	}

	/*
	 * (non-Javadoc)
	 */
	public Button createRadioButton(Composite parent, String text) {
		return createButton(parent, text, SWT.RADIO);
	}

	/*
	 * (non-Javadoc)
	 */
	public Section createSection(Composite parent, int style) {
		return widgetFactory.createSection(parent, SWT.FLAT | style);
	}

	/*
	 * (non-Javadoc)
	 */
	public Spinner createSpinner(Composite parent) {
		parent = createBorderContainer(parent);

		Spinner spinner = new Spinner(parent, SWT.FLAT);
		spinner.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		widgetFactory.adapt(spinner, true, false);

		return spinner;
	}

	/*
	 * (non-Javadoc)
	 */
	public Table createTable(Composite parent, int style) {
		Table table = this.widgetFactory.createTable(parent, SWT.BORDER | style);
		table.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		return table;
	}

	/*
	 * (non-Javadoc)
	 */
	public Text createText(Composite parent) {
		return createText(parent, SWT.NULL);
	}

	protected Text createText(Composite parent, int style) {
		return widgetFactory.createText(parent, null, SWT.FLAT | style);
	}

	/*
	 * (non-Javadoc)
	 */
	public Button createTriStateCheckBox(Composite parent, String text) {
		TriStateCheckBox checkBox = new TriStateCheckBox(parent, text, this);
		return checkBox.getCheckBox();
	}

	/**
	 * Returns the actual factory responsible for creating the new widgets.
	 *
	 * @return The factory creating the widgets with the form style (flat-style)
	 */
	public FormToolkit getWidgetFactory() {
		return widgetFactory;
	}
}