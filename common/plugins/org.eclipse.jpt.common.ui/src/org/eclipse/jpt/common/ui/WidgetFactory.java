/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui;

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
 * @version 3.3
 * @since 2.0
 */
public interface WidgetFactory {
	/**
	 * Create a new regular button with the specified parent and text.
	 * @see #createCheckBox(Composite, String)
	 * @see #createPushButton(Composite, String)
	 * @see #createRadioButton(Composite, String)
	 */
	Button createButton(Composite parent, String text);

	/**
	 * Create a new check box with the specified parent and text.
	 * @see #createButton(Composite, String)
	 * @see #createPushButton(Composite, String)
	 * @see #createRadioButton(Composite, String)
	 */
	Button createCheckBox(Composite parent, String text);

	/**
	 * Create a new drop-down list box with the specified parent.
	 */
	Combo createCombo(Composite parent);

	/**
	 * Create a new composite with the specified parent.
	 */
	Composite createComposite(Composite parent);

	/**
	 * Create a new date time widget with the specified parent and style.
	 * @see org.eclipse.swt.SWT#DATE
	 * @see org.eclipse.swt.SWT#TIME
	 * @see org.eclipse.swt.SWT#CALENDAR
	 */
	DateTime createDateTime(Composite parent, int style);

	/**
	 * Create a new combo-box with the specified parent.
	 */
	Combo createEditableCombo(Composite parent);

	/**
	 * Create a new group box with the specified parent and title.
	 */
	Group createGroup(Composite parent, String title);

	/**
	 * Create a new hyperlink label with the specified parent and
	 * text.
	 */
	Hyperlink createHyperlink(Composite parent, String text);

	/**
	 * Create a new label with the specified parent and text.
	 */
	Label createLabel(Composite parent, String text);

	/**
	 * Create a new list box with the specified parent and style.
	 * @see org.eclipse.swt.SWT#MULTI
	 * @see org.eclipse.swt.SWT#SINGLE
	 */
	List createList(Composite parent, int style);

	/**
	 * Create a new editable multi-line text field with the specified parent.
	 */
	Text createMultiLineText(Composite parent);

	/**
	 * Create a new editable password text field with the specified parent.
	 */
	Text createPasswordText(Composite parent);

	/**
	 * Create a new push button (i.e. a button that toggles between
	 * <em>selected</em> and <em>unselected</em>) with the specified parent and
	 * text.
	 * @see #createButton(Composite, String)
	 * @see #createCheckBox(Composite, String)
	 * @see #createRadioButton(Composite, String)
	 */
	Button createPushButton(Composite parent, String text);

	/**
	 * Create a new radio button with the specified parent and text.
	 * @see #createButton(Composite, String)
	 * @see #createCheckBox(Composite, String)
	 * @see #createPushButton(Composite, String)
	 */
	Button createRadioButton(Composite parent, String text);

	/**
	 * Create a new section (i.e. a collapsible group box) with the specified
	 * parent and expansion style.
	 * @see org.eclipse.ui.forms.widgets.ExpandableComposite#TWISTIE
	 * @see org.eclipse.ui.forms.widgets.ExpandableComposite#TREE_NODE
	 * @see org.eclipse.ui.forms.widgets.ExpandableComposite#FOCUS_TITLE
	 * @see org.eclipse.ui.forms.widgets.ExpandableComposite#CLIENT_INDENT
	 * @see org.eclipse.ui.forms.widgets.ExpandableComposite#COMPACT
	 * @see org.eclipse.ui.forms.widgets.ExpandableComposite#EXPANDED
	 * @see org.eclipse.ui.forms.widgets.ExpandableComposite#TITLE_BAR
	 * @see org.eclipse.ui.forms.widgets.ExpandableComposite#SHORT_TITLE_BAR
	 * @see org.eclipse.ui.forms.widgets.ExpandableComposite#NO_TITLE
	 * @see org.eclipse.ui.forms.widgets.ExpandableComposite#LEFT_TEXT_CLIENT_ALIGNMENT
	 * @see org.eclipse.ui.forms.widgets.ExpandableComposite#NO_TITLE_FOCUS_BOX
	 * @see org.eclipse.ui.forms.widgets.Section#DESCRIPTION
	 */
	Section createSection(Composite parent, int expansionStyle);

	/**
	 * Create a new spinner with the specified parent.
	 */
	Spinner createSpinner(Composite parent);

	/**
	 * Create a new table with the specified parent.
	 */
	Table createTable(Composite parent, int style);

	/**
	 * Create a new editable text field with the specified parent.
	 */
	Text createText(Composite parent);

	/**
	 * Create a new tri-state check box with the specified parent and text.
	 */
	Button createTriStateCheckBox(Composite parent, String text);

	/**
	 * Dispose the widget factory.
	 */
	void dispose();
}
