/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui;

import org.eclipse.swt.custom.CCombo;
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
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;

/**
 * A widget factory is responsible for creating an SWT widget based on the right
 * style. Some style shows the widgets differently, for instance, the flat style
 * shows the widgets with less borders.
 * <p>
 * Provisional API: This interface is part of an interim API that is still under
 * development and expected to change significantly before reaching stability.
 * It is available at this early stage to solicit feedback from pioneering
 * adopters on the understanding that any code that uses this API will almost
 * certainly be broken (repeatedly) as the API evolves.
 *
 * @version 2.0
 * @since 2.0
 */
public interface WidgetFactory {

	/**
	 * Creates a new regular button.
	 *
	 * @param parent The parent container
	 * @param text The button's text
	 * @return A new <code>Button</code>
	 */
	Button createButton(Composite parent, String text);

	/**
	 * Creates a new non-editable custom <code>Combo</code>.
	 *
	 * @deprecated
	 * @param parent The parent container
	 * @return A new <code>CCombo</code>
	 */
	@Deprecated
	CCombo createCCombo(Composite parent);

	/**
	 * Creates a new check box button.
	 *
	 * @param parent The parent container
	 * @param text The button's text
	 * @return A new <code>Button</code>
	 */
	Button createCheckBox(Composite parent, String text);

	/**
	 * Creates a new non-editable <code>Combo</code>.
	 *
	 * @param parent The parent container
	 * @return A new <code>Combo</code>
	 */
	Combo createCombo(Composite parent);

	/**
	 * Creates a new container.
	 *
	 * @param parent The parent container
	 * @return A new <code>Composite</code>
	 */
	Composite createComposite(Composite parent);

	/**
	 * Creates a new DateTime.
	 *
	 * @param container The parent container
	 * @param style The style is to tell the type of widget
	 * (<code>SWT.DATE</code> or <code>SWT.TIME</code> or <code>SWT.CALENDAR</code>)
	 * @return A new <code>DateTime</code>
	 */
	DateTime createDateTime(Composite parent, int style);
	
	/**
	 * Creates a new editable custom <code>CCombo</code>.
	 *
	 * @deprecated
	 * @param parent The parent container
	 * @return A new <code>CCombo</code>
	 */
	@Deprecated
	CCombo createEditableCCombo(Composite parent);

	/**
	 * Creates a new editable <code>Combo</code>.
	 *
	 * @param parent The parent container
	 * @return A new <code>Combo</code>
	 */
	Combo createEditableCombo(Composite parent);

	/**
	 * Creates a new titled pane (group box).
	 *
	 * @param parent The parent container
	 * @param title The group pane's title
	 * @return A new <code>Group</code>
	 */
	Group createGroup(Composite parent, String title);

	/**
	 * Creates a new label that is shown as a hyperlink.
	 *
	 * @param parent The parent container
	 * @param text The label's text
	 * @return A new <code>Hyperlink</code>
	 */
	Hyperlink createHyperlink(Composite parent, String text);

	/**
	 * Creates a new label.
	 *
	 * @param container The parent container
	 * @param labelText The label's text
	 * @return A new <code>Label</code>
	 */
	Label createLabel(Composite container, String labelText);

	/**
	 * Creates a new list.
	 *
	 * @param container The parent container
	 * @param style The style is usually to tell what type of selection
	 * (<code>SWT.MULTI</code> or <code>SWT.SINGLE</code>)
	 * @return A new <code>Label</code>
	 */
	List createList(Composite container, int style);

	/**
	 * Creates a new label that can be wrapped on multiple lines.
	 *
	 * @param container The parent container
	 * @param labelText The label's text
	 * @return A new <code>FormText</code>
	 */
	FormText createMultiLineLabel(Composite container, String labelText);

	/**
	 * Creates a new editable text area.
	 *
	 * @param parent The parent container
	 * @param parent The number of lines the text area should display
	 * @return A new <code>Text</code>
	 */
	Text createMultiLineText(Composite parent);

	/**
	 * Creates a new editable text field that handles password.
	 *
	 * @param container The parent container
	 * @return A new <code>Text</code>
	 */
	Text createPasswordText(Composite container);

	/**
	 * Creates a new push button (toggle between selected and unselected).
	 *
	 * @param parent The parent container
	 * @param text The button's text
	 * @return A new <code>Button</code>
	 */
	Button createPushButton(Composite parent, String text);

	/**
	 * Creates a new radio button.
	 *
	 * @param parent The parent container
	 * @param text The button's text
	 * @return A new <code>Button</code>
	 */
	Button createRadioButton(Composite parent, String text);

	/**
	 * Creates a new section, which is a collapsable pane with a title bar.
	 *
	 * @param parent The parent container
	 * @param style The style of the title bar, which can be
	 * <code>ExpandableComposite.TWISTIE</code> and
	 * <code>ExpandableComposite.TITLE_BAR</code>
	 * @return A new <code>Section</code>
	 */
	Section createSection(Composite parent, int style);

	/**
	 * Creates a new spinner.
	 *
	 * @param parent The parent container
	 * @return A new <code>Spinner</code>
	 */
	Spinner createSpinner(Composite parent);

	/**
	 * Creates a new table.
	 *
	 * @param container The parent container
	 * @param style The style to apply to the table
	 * @return A new <code>Table</code>
	 */
	Table createTable(Composite parent, int style);

	/**
	 * Creates a new editable text field.
	 *
	 * @param container The parent container
	 * @return A new <code>Text</code>
	 */
	Text createText(Composite parent);

	/**
	 * Creates a new tri-state check box.
	 *
	 * @param parent The parent container
	 * @param text The button's text
	 * @return A new <code>Button</code> that has 3 selection states
	 */
	Button createTriStateCheckBox(Composite parent, String text);
}