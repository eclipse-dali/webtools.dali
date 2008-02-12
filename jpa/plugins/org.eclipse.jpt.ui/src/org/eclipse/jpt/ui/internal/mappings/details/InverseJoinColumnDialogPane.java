/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Iterator;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IJoinTable;
import org.eclipse.jpt.core.internal.context.base.IRelationshipMapping;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

/**
 * See <code>AbstractJoinColumnDialogPane</code> for the layout.
 *
 * @see JoinColumnInJoinTableStateObject
 *
 * @version 2.0
 * @since 2.0
 */
public class InverseJoinColumnDialogPane extends AbstractJoinColumnDialogPane<JoinColumnInJoinTableStateObject>
{
	/**
	 * Creates a new <code>InverseJoinColumnDialogPane</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public InverseJoinColumnDialogPane(PropertyValueModel<JoinColumnInJoinTableStateObject> subjectHolder,
	                                   Composite parent)
	{
		super(subjectHolder, parent);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void populateNameCombo() {

		JoinColumnInJoinTableStateObject subject = subject();

		Combo nameCombo = getNameCombo();
		nameCombo.removeAll();

		if (subject == null) {
			return;
		}

		IJoinColumn joinColumn = subject.getJoinColumn();

		// Add the default column name if one exists
		String defaultName = (joinColumn != null) ? joinColumn.getDefaultName() : null;

		if (defaultName != null) {
			nameCombo.add(NLS.bind(
				JptUiMappingsMessages.InverseJoinColumnDialog_defaultWithOneParam,
				defaultName
			));
		}

		// Populate the combo with the column names
		IJoinTable joinTable = subject.getJoinTable();

		if (joinTable != null) {
			Table joinDBTable = joinTable.dbTable();

			if (joinDBTable != null) {
				Iterator<String> columnNames = joinDBTable.columnNames();

				for (Iterator<String> iter = CollectionTools.sort(columnNames); iter.hasNext(); ) {
					nameCombo.add(iter.next());
				}
			}
		}

		// Set the selected name
		String name = (joinColumn != null) ? joinColumn.getSpecifiedName() : null;

		if ((name != null) && !name.equals(defaultName)) {
			getNameCombo().setText(name);
		}
		else if (defaultName != null) {
			nameCombo.select(0);
		}
		else {
			nameCombo.select(-1);
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void populateReferencedNameCombo() {

		JoinColumnInJoinTableStateObject subject = subject();

		Combo referencedColumnNameCombo = getReferencedColumnNameCombo();
		referencedColumnNameCombo.removeAll();

		if (subject == null) {
			return;
		}

		IJoinColumn joinColumn = subject.getJoinColumn();

		// Add the default column name if one exists
		String defaultReferencedColumnName = (joinColumn != null) ? joinColumn.getDefaultReferencedColumnName() : null;

		if (defaultReferencedColumnName != null) {
			referencedColumnNameCombo.add(NLS.bind(
				JptUiMappingsMessages.InverseJoinColumnDialog_defaultWithOneParam,
				defaultReferencedColumnName
			));
		}

		// Populate the combo with the column names
		IRelationshipMapping relationshipMapping = subject.relationshipMapping();
		IEntity targetEntity = relationshipMapping.getResolvedTargetEntity();

		if (targetEntity != null) {
			Table referencedDbTable = targetEntity.primaryDbTable();

			if (referencedDbTable != null) {
				Iterator<String> columnNames = referencedDbTable.columnNames();

				for (Iterator<String> iter = CollectionTools.sort(columnNames); iter.hasNext(); ) {
					referencedColumnNameCombo.add(iter.next());
				}
			}
		}

		// Set the selected name
		String referencedColumnName = (joinColumn != null) ? joinColumn.getSpecifiedReferencedColumnName() : null;

		if ((referencedColumnName != null) && !referencedColumnName.equals(defaultReferencedColumnName))
		{
			referencedColumnNameCombo.setText(referencedColumnName);
		}
		else if (defaultReferencedColumnName != null) {
			referencedColumnNameCombo.select(0);
		}
		else {
			referencedColumnNameCombo.select(-1);
		}
	}
}