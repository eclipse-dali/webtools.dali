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

import java.util.Arrays;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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
				comboModified(e);
			}
		};
	}

	private void comboModified(ModifyEvent e) {
		String text = ((CCombo) e.getSource()).getText();

		if (text.equals(combo.getItem(0))) {
			text = null;
		}

		subject().setSpecifiedName(text);

		// TODO Does this need to be done?
		//this.editingDomain.getCommandStack().execute(SetCommand.create(this.editingDomain, this.entity, MappingsPackage.eINSTANCE.getEntity_SpecifiedName(), text));
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
		combo = buildEditableCombo(container);
		combo.addModifyListener(buildComboModifyListener());
	}

	private void populateCombo() {
		if (subject() == null) {
			combo.clearSelection();
			combo.setItems(new String[0]);
		}
		else {
			String defaultItem = NLS.bind(JptUiMappingsMessages.EntityGeneralSection_nameDefaultWithOneParam, subject().getDefaultName());
			String specifiedName = subject().getSpecifiedName();

			if (specifiedName == null) {
				setComboData(defaultItem, new String[] { defaultItem });
			}
			else {
				setComboData(specifiedName, new String[] { defaultItem });
			}
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

			populateCombo();
		}
	}

	private void setComboData(String text, String[] items) {
		if (! Arrays.equals(items, combo.getItems())) {
			combo.setItems(items);
		}

		if (! text.equals(combo.getText())) {
			combo.setText(text);
		}
	}
}