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
import org.eclipse.jpt.core.internal.context.base.IAbstractJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IMultiRelationshipMapping;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.osgi.util.NLS;
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
		IAbstractJoinColumn joinColumn = subject.getJoinColumn();

		if (subject.getJoinTable() == null) {
			return;
		}

		if (joinColumn != null) {
			getNameCombo().add(NLS.bind(JptUiMappingsMessages.InverseJoinColumnDialog_defaultWithOneParam, joinColumn.getDefaultName()));
		}

		Table joinDBTable = subject.getJoinTable().dbTable();

		if (joinDBTable != null) {
			for (Iterator<String> iter = joinDBTable.columnNames(); iter.hasNext(); ) {
				getNameCombo().add(iter.next());
			}
		}

		if (joinColumn != null &&
		    joinColumn.getSpecifiedName() != null)
		{
			getNameCombo().setText(joinColumn.getSpecifiedName());
		}
		else {
			getNameCombo().select(0);
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void populateReferencedNameCombo() {

		JoinColumnInJoinTableStateObject subject = subject();
		IAbstractJoinColumn joinColumn = subject.getJoinColumn();

		if (subject.getJoinTable() == null) {
			return;
		}

		if (joinColumn != null) {
			getReferencedColumnNameCombo().add(NLS.bind(JptUiMappingsMessages.InverseJoinColumnDialog_defaultWithOneParam, joinColumn.getDefaultReferencedColumnName()));
		}

		IMultiRelationshipMapping multiRelationshipMapping = subject.relationshipMapping();
		IEntity targetEntity = multiRelationshipMapping.getResolvedTargetEntity();

		if (targetEntity != null) {
			Table referencedDbTable = targetEntity.primaryDbTable();

			if (referencedDbTable != null) {
				for (Iterator<String> iter = referencedDbTable.columnNames(); iter.hasNext(); ) {
					getReferencedColumnNameCombo().add(iter.next());
				}
			}
		}

		if (joinColumn != null &&
		    joinColumn.getSpecifiedReferencedColumnName() != null)
		{
			getReferencedColumnNameCombo().setText(joinColumn.getSpecifiedReferencedColumnName());
		}
		else {
			getReferencedColumnNameCombo().select(0);
		}
	}
}
