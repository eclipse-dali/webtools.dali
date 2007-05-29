/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Iterator;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.IJoinColumn;
import org.eclipse.jpt.core.internal.mappings.IJoinTable;
import org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;

public class InverseJoinColumnDialog extends JoinColumnInJoinTableDialog {

	InverseJoinColumnDialog(Shell parent, IJoinTable joinTable) {
		super(parent, joinTable);
	}

	InverseJoinColumnDialog(Shell parent, IJoinColumn joinColumn) {
		super(parent, joinColumn);
	}
	
	protected String getTitle() {
		return JptUiMappingsMessages.InverseJoinColumnDialog_editInverseJoinColumn;
	}
	
	protected void populateNameCombo() {
		if (getJoinTable() == null) {
			return;
		}
		if (getJoinColumn() != null) {
			getNameCombo().add(NLS.bind(JptUiMappingsMessages.InverseJoinColumnDialog_defaultWithOneParam, getJoinColumn().getDefaultName()));
		}
		Table joinDBTable = getJoinTable().dbTable();
		if (joinDBTable != null) {
			for (Iterator i = joinDBTable.columnNames(); i.hasNext(); ) {
				getNameCombo().add((String) i.next());
			}
		}
		if (getJoinColumn() != null && getJoinColumn().getSpecifiedReferencedColumnName() != null) {
			getNameCombo().setText(getJoinColumn().getSpecifiedName());
		}
		else {
			getNameCombo().select(0);
		}
	}

	protected void populateReferencedNameCombo() {
		if (getJoinTable() == null) {
			return;
		}
		if (getJoinColumn() != null) {
			getReferencedColumnNameCombo().add(NLS.bind(JptUiMappingsMessages.InverseJoinColumnDialog_defaultWithOneParam, getJoinColumn().getDefaultReferencedColumnName()));
		}
		IMultiRelationshipMapping multiRelationshipMapping = (IMultiRelationshipMapping) getJoinTable().eContainer();
		IEntity targetEntity = multiRelationshipMapping.getResolvedTargetEntity();
		if (targetEntity != null) {
			Table referencedDbTable = targetEntity.primaryDbTable();
			if (referencedDbTable != null) {
				for (Iterator i = referencedDbTable.columnNames(); i.hasNext(); ) {
					getReferencedColumnNameCombo().add((String) i.next());
				}
			}
		}
		if (getJoinColumn() != null && getJoinColumn().getSpecifiedReferencedColumnName() != null) {
			getReferencedColumnNameCombo().setText(getJoinColumn().getSpecifiedReferencedColumnName());
		}
		else {
			getReferencedColumnNameCombo().select(0);
		}
	}
}
