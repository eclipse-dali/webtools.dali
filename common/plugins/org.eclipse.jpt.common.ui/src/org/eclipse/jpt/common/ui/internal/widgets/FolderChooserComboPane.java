/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.common.ui.internal.widgets;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jpt.utility.internal.StringConverter;
import org.eclipse.jpt.utility.internal.model.value.SimpleListValueModel;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;


/**
 * This chooser allows the user to choose a folder when browsing.
 * <p>
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |        ---------------------------------------------------- ------------- |
 * | Label: | I                                            |v| | | Browse... | |
 * |        ---------------------------------------------------- ------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @version 3.0
 * @since 3.0
 */
public abstract class FolderChooserComboPane<T extends Model> extends FolderChooserPane<T>
{
	/**
	 * Creates a new <code>FolderChooserComboPane</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public FolderChooserComboPane(Pane<? extends T> parentPane,
	                         Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>FolderChooserComboPane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public FolderChooserComboPane(Pane<?> parentPane,
	                         PropertyValueModel<? extends T> subjectHolder,
	                         Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected Control addMainControl(Composite container) {
		
		return addEditableCombo(
			container,
			this.buildListHolder(),
			this.getTextHolder(),
			this.buildStringConverter()
		);
	}

	/**
	 * Creates the list holder of the combo box.
	 */
	protected ListValueModel<String> buildListHolder() {
		return new SimpleListValueModel<String>(
			this.buildDefaultList()
		);
	}

	/**
	 * Creates the default list of the combo box.
	 */
	protected List<String> buildDefaultList() {
		return Arrays.asList(this.getDefaultString());
	}

	/**
	 * Returns the default value of the combo box.
	 */
	protected abstract String getDefaultString();
	
	/**
	 * The converter responsible to transform each combo box item
	 * into a string representation
	 */
	protected StringConverter<String> buildStringConverter() {
		return StringConverter.Default.<String>instance();
	}

}
