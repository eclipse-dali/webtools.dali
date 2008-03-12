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

	protected Text buildText(Composite parent, int style) {
		return widgetFactory.createText(parent, null, SWT.FLAT | style);
	}

	/*
	 * (non-Javadoc)
	 */
	public Button createButton(Composite parent, String text) {
		return this.createButton(parent, text, SWT.NULL);
	}

	protected final Button createButton(Composite parent, String text, int style) {
		return widgetFactory.createButton(parent, text, SWT.FLAT | style);
	}

	/*
	 * (non-Javadoc)
	 */
	public CCombo createCCombo(Composite parent) {
		return createCCombo(parent, SWT.READ_ONLY);
	}

	protected CCombo createCCombo(Composite parent, int style) {
		parent = fixComboBorderNotPainted(parent);

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
		return this.createButton(parent, text, SWT.CHECK);
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
		return new Combo(parent, SWT.FLAT);
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
		return new List(container, SWT.FLAT | style);
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
		text.setText(labelText, false, false);
		text.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));

		return text;
	}

	/*
	 * (non-Javadoc)
	 */
	public Text createMultiLineText(Composite parent) {
		return buildText(parent, SWT.MULTI | SWT.V_SCROLL);
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
	public Table createTable(Composite parent, int style) {
		return this.widgetFactory.createTable(parent, SWT.BORDER | style);
	}

	/*
	 * (non-Javadoc)
	 */
	public Text createText(Composite parent) {
		return buildText(parent, SWT.NULL);
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
	protected final Composite fixComboBorderNotPainted(Composite container) {

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
	protected final Composite fixTextBorderNotPainted(Composite container) {

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
	 * Returns the actual factory responsible for creating the new widgets.
	 *
	 * @return The factory creating the widgets with the form style (flat-style)
	 */
	public FormToolkit getWidgetFactory() {
		return widgetFactory;
	}
}