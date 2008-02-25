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
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * This <code>IWidgetFactory</code> is responsible to create the widgets
 * using the <code>TabbedPropertySheetWidgetFactory</code> in order use the
 * form style (flat-style) look and feel.
 *
 * @see TabbedPropertySheetWidgetFactory
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public final class FormWidgetFactory implements WidgetFactory {

	/**
	 * The actual factory responsible for creating the new widgets.
	 */
	private final TabbedPropertySheetWidgetFactory widgetFactory;

	/**
	 * Creates a new <code>FormWidgetFactory</code>.
	 *
	 * @param widgetFactory The actual factory responsible for creating the new
	 * widgets
	 */
	public FormWidgetFactory(TabbedPropertySheetWidgetFactory widgetFactory) {
		super();

		Assert.isNotNull(widgetFactory, "The widget factory cannot be null");
		this.widgetFactory = widgetFactory;
	}

	/*
	 * (non-Javadoc)
	 */
	public Button createButton(Composite parent, String text) {
		return this.createButton(parent, text, SWT.NULL);
	}

	private Button createButton(Composite parent, String text, int style) {
		return widgetFactory.createButton(parent, text, SWT.FLAT | style);
	}

	/*
	 * (non-Javadoc)
	 */
	public CCombo createCCombo(Composite parent) {
		return createCombo(parent, SWT.READ_ONLY);
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
		return new Combo(parent, SWT.READ_ONLY);
	}

	private CCombo createCombo(Composite parent, int style) {
		parent = fixComboBorderNotPainted(parent);
		return widgetFactory.createCCombo(parent, SWT.FLAT | style);
	}

	/*
	 * (non-Javadoc)
	 */
	public Composite createComposite(Composite parent) {
		return widgetFactory.createComposite(parent);
	}

	/*
	 * (non-Javadoc)
	 */
	public CCombo createEditableCCombo(Composite parent) {
		return createCombo(parent, SWT.NULL);
	}

	/*
	 * (non-Javadoc)
	 */
	public Combo createEditableCombo(Composite parent) {
		return new Combo(parent, SWT.FLAT);
	}

	/*
	 * (non-Javadoc)
	 */
	public Group createGroup(Composite parent, String title) {
		return widgetFactory.createGroup(parent, title);
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
		return widgetFactory.createLabel(container, labelText);
	}

	/*
	 * (non-Javadoc)
	 */
	public List createList(Composite container, int style) {
		return widgetFactory.createList(container, SWT.FLAT | style);
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
		return widgetFactory.createSection(parent, SWT.FLAT | style);
	}

	/*
	 * (non-Javadoc)
	 */
	public Text createText(Composite parent) {
		parent = fixTextBorderNotPainted(parent);

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;

		Text text = widgetFactory.createText(parent, null, SWT.FLAT);
		text.setLayoutData(gridData);

		return text;
	}

	/*
	 * (non-Javadoc)
	 */
	public Button createTriStateCheckBox(Composite parent, String text) {
		TriStateCheckBox checkBox = new TriStateCheckBox(parent, text, this);
		return checkBox.getCheckBox();
	}

	/**
	 * Wraps the given <code>Composite</code> into a new <code>Composite</code>
	 * in order to have the widgets' border painted. This must be a bug in the
	 * <code>GridLayout</code> used in a form.
	 *
	 * @param container The parent of the sub-pane with 1 pixel border
	 * @return A new <code>Composite</code> that has the necessary space to paint
	 * the border
	 */
	private Composite fixComboBorderNotPainted(Composite container) {

		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		layout.marginWidth  = 0;
		layout.marginTop    = 1;
		layout.marginLeft   = 1;
		layout.marginBottom = 1;
		layout.marginRight  = 1;

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;

		container = widgetFactory.createComposite(container);
		container.setLayoutData(gridData);
		container.setLayout(layout);

		return container;
	}

	/**
	 * Wraps the given <code>Composite</code> into a new <code>Composite</code>
	 * in order to have the widgets' border painted. This must be a bug in the
	 * <code>GridLayout</code> used in a form.
	 *
	 * @param container The parent of the sub-pane with 2 pixel border
	 * @return A new <code>Composite</code> that has the necessary space to paint
	 * the border
	 */
	private Composite fixTextBorderNotPainted(Composite container) {

		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		layout.marginWidth  = 0;
		layout.marginTop    = 2;
		layout.marginLeft   = 2;
		layout.marginBottom = 2;
		layout.marginRight  = 2;

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;

		container = widgetFactory.createComposite(container);
		container.setLayoutData(gridData);
		container.setLayout(layout);

		return container;
	}

	/**
	 * Returns the actual factory responsible for creating the new widgets.
	 *
	 * @return The factory creating the widgets with the form style (flat-style)
	 */
	public TabbedPropertySheetWidgetFactory getWidgetFactory() {
		return widgetFactory;
	}
}
