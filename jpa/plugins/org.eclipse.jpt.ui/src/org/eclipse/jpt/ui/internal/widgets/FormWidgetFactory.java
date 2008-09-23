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
import org.eclipse.swt.widgets.DateTime;
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
		return createBorderContainer(container, 2, 1);
	}
	
	protected Composite createBorderContainer(Composite container, int marginHeight, int marginWidth) {

		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = marginHeight;
		layout.marginWidth  = marginWidth;

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;

		container = widgetFactory.createComposite(container);
		container.setLayoutData(gridData);
		container.setLayout(layout);

		return container;
	}
	
	/**
	 * {@inheritDoc}
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

	/**
	 * {@inheritDoc}
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
		parent = createBorderContainer(parent, 1, 1);

		CCombo combo = new CCombo(parent, style);
		widgetFactory.adapt(combo, true, false);

		// Bugzilla 145837 - workaround for no borders on Windows XP
		if (widgetFactory.getBorderStyle() == SWT.BORDER) {
			combo.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		}

		return combo;
	}

	/**
	 * {@inheritDoc}
	 */
	public Button createCheckBox(Composite parent, String text) {
		return createButton(parent, text, SWT.CHECK);
	}

	/**
	 * {@inheritDoc}
	 */
	public Combo createCombo(Composite parent) {
		return new Combo(parent, SWT.READ_ONLY);
	}

	/**
	 * {@inheritDoc}
	 */
	public Composite createComposite(Composite parent) {
		Composite composite = widgetFactory.createComposite(parent);
		widgetFactory.paintBordersFor(composite);
		return composite;
	}
	/**
	 * {@inheritDoc}
	 */
	public DateTime createDateTime(Composite parent, int style) {
		parent = createBorderContainer(parent);

		DateTime dateTime = new DateTime(parent, style | SWT.FLAT);
		dateTime.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		this.widgetFactory.adapt(dateTime, true, false);

		return dateTime;
	}

	/**
	 * {@inheritDoc}
	 */
	public CCombo createEditableCCombo(Composite parent) {
		return createCCombo(parent, SWT.NULL);
	}

	/**
	 * {@inheritDoc}
	 */
	public Combo createEditableCombo(Composite parent) {
		Combo combo = new Combo(parent, SWT.FLAT);
		combo.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		return combo;
	}

	/**
	 * {@inheritDoc}
	 */
	public Group createGroup(Composite parent, String title) {
		Group group = new Group(parent, SWT.SHADOW_NONE);
		group.setText(title);
		group.setBackground(widgetFactory.getColors().getBackground());
		group.setForeground(widgetFactory.getColors().getForeground());
		return group;
	}

	/**
	 * {@inheritDoc}
	 */
	public Hyperlink createHyperlink(Composite parent, String text) {
		return widgetFactory.createHyperlink(parent, text, SWT.FLAT);
	}

	/**
	 * {@inheritDoc}
	 */
	public Label createLabel(Composite container, String labelText) {
		return widgetFactory.createLabel(container, labelText, SWT.WRAP);
	}

	/**
	 * {@inheritDoc}
	 */
	public List createList(Composite container, int style) {
		List list = new List(container, SWT.FLAT | style);
		list.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		return list;
	}

	/**
	 * {@inheritDoc}
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

	/**
	 * {@inheritDoc}
	 */
	public Text createMultiLineText(Composite parent) {
		return createText(parent, SWT.MULTI | SWT.V_SCROLL);
	}

	/**
	 * {@inheritDoc}
	 */
	public Text createPasswordText(Composite parent) {
		return createText(parent, SWT.PASSWORD);
	}

	/**
	 * {@inheritDoc}
	 */
	public Button createPushButton(Composite parent, String text) {
		return createButton(parent, text, SWT.PUSH);
	}

	/**
	 * {@inheritDoc}
	 */
	public Button createRadioButton(Composite parent, String text) {
		return createButton(parent, text, SWT.RADIO);
	}

	/**
	 * {@inheritDoc}
	 */
	public Section createSection(Composite parent, int style) {
		return widgetFactory.createSection(parent, SWT.FLAT | style);
	}

	/**
	 * {@inheritDoc}
	 */
	public Spinner createSpinner(Composite parent) {
		parent = createBorderContainer(parent);

		Spinner spinner = new Spinner(parent, SWT.FLAT);
		spinner.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		widgetFactory.adapt(spinner, true, false);

		return spinner;
	}

	/**
	 * {@inheritDoc}
	 */
	public Table createTable(Composite parent, int style) {
		Table table = this.widgetFactory.createTable(parent, SWT.BORDER | style);
		table.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		return table;
	}

	/**
	 * {@inheritDoc}
	 */
	public Text createText(Composite parent) {
		return createText(parent, SWT.NULL);
	}

	protected Text createText(Composite parent, int style) {
		return widgetFactory.createText(parent, null, SWT.FLAT | style);
	}

	/**
	 * {@inheritDoc}
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