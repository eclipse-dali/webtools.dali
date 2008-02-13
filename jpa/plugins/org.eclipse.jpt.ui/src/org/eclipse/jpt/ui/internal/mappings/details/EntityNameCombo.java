/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |              ------------------------------------------------------------ |
 * | Entity Name: | I                                                      |v| |
 * |              ------------------------------------------------------------ |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see IEntity
 * @see EntityComposite - The parent container
 *
 * @version 2.0
 * @since 1.0
 */
public class EntityNameCombo extends AbstractFormPane<IEntity>
{
	private CCombo combo;

	/**
	 * Creates a new <code>EntityNameCombo</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public EntityNameCombo(AbstractFormPane<? extends IEntity> parentPane,
	                       Composite parent) {

		super(parentPane, parent);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void addPropertyNames(java.util.Collection<String> propertyNames) {
		super.addPropertyNames(propertyNames);
		propertyNames.add(IEntity.DEFAULT_NAME_PROPERTY);
		propertyNames.add(IEntity.SPECIFIED_NAME_PROPERTY);
	}

	private ModifyListener buildComboModifyListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (!isPopulating()) {
					CCombo combo = (CCombo) e.widget;
					valueChanged(combo.getText());
				}
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {

		super.doPopulate();
		populateCombo();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {
		combo = buildEditableCCombo(container);
		combo.addModifyListener(buildComboModifyListener());
	}

	private void populateCombo() {

		combo.removeAll();
		populateDefaultValue();
		updateSelectedItem();
	}

	/**
	 * Adds the default value to the combo if one exists.
	 */
	private void populateDefaultValue() {

		IEntity entity = subject();
		String defaultValue = (entity != null) ? entity.getDefaultName() : null;

		if (defaultValue != null) {
			combo.add(NLS.bind(
				JptUiMappingsMessages.EntityGeneralSection_nameDefaultWithOneParam,
				defaultValue
			));
		}
		else {
			combo.add(JptUiMappingsMessages.EntityGeneralSection_nameDefaultEmpty);
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void propertyChanged(String propertyName) {
		super.propertyChanged(propertyName);

		if (propertyName == IEntity.DEFAULT_NAME_PROPERTY ||
		    propertyName == IEntity.SPECIFIED_NAME_PROPERTY) {

			updateSelectedItem();
		}
	}

	/**
	 * Updates the selected item by selected the current value, if not
	 * <code>null</code>, or select the default value if one is available,
	 * otherwise remove the selection.
	 * <p>
	 * <b>Note:</b> It seems the text can be shown as truncated, changing the
	 * selection to (0, 0) makes the entire text visible.
	 */
	private void updateSelectedItem() {

		IEntity subject = subject();

		String value         = (subject != null) ? subject.getSpecifiedName() : null;
		String defaultValue  = (subject != null) ? subject.getDefaultName()   : null;
		String displayString = JptUiMappingsMessages.ColumnComposite_defaultEmpty;

		if (defaultValue != null) {
			displayString = NLS.bind(
				JptUiMappingsMessages.ColumnComposite_defaultWithOneParam,
				defaultValue
			);
		}

		// Make sure the default value is up to date
		if (!combo.getItem(0).equals(displayString)) {
			combo.remove(0);
			combo.add(displayString, 0);
		}

		// Select the new value
		if (value != null) {
			combo.setText(value);
		}
		// Select the default value
		else {
			combo.select(0);
		}

		combo.setSelection(new Point(0, 0));
	}

	private void valueChanged(String value) {

		IEntity subject = subject();
		String oldValue = (subject != null) ? subject.getSpecifiedName() : null;

		// Check for null value
		if (StringTools.stringIsEmpty(value)) {
			value = null;

			if (StringTools.stringIsEmpty(oldValue)) {
				return;
			}
		}

		// The default value
		if (value != null &&
		    combo.getItemCount() > 0 &&
		    value.equals(combo.getItem(0)))
		{
			value = null;
		}

		// Nothing to change
		if ((oldValue == value) && value == null) {
			return;
		}

		// Set the new value
		if ((value != null) && (oldValue == null) ||
		   ((oldValue != null) && !oldValue.equals(value))) {

			setPopulating(true);

			try {
				subject.setSpecifiedName(value);
			}
			finally {
				setPopulating(false);
			}
		}
	}
}