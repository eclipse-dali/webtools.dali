/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.context.base.IAssociationOverride;
import org.eclipse.jpt.core.internal.context.base.IAttributeMapping;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IRelationshipMapping;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.swt.widgets.Shell;

public class JoinColumnInAssociationOverrideDialog extends JoinColumnDialog {

	private IAssociationOverride associationOverride;

	JoinColumnInAssociationOverrideDialog(Shell parent, IAssociationOverride associationOverride) {
		super(parent);
		this.associationOverride = associationOverride;
	}

	JoinColumnInAssociationOverrideDialog(Shell parent, IJoinColumn joinColumn) {
		super(parent, joinColumn);
		this.associationOverride = (IAssociationOverride) joinColumn.eContainer();
	}

	@Override
	protected Schema getSchema() {
		return this.associationOverride.typeMapping().dbSchema();
	}

	@Override
	protected String defaultTableName() {
		if (getJoinColumn() != null) {
			return getJoinColumn().getDefaultTable();
		}
		return this.associationOverride.typeMapping().getTableName();
	}

	protected Table getNameTable() {
		return this.associationOverride.typeMapping().primaryDbTable();
	}

	protected Table getReferencedNameTable() {
		IAttributeMapping attributeMapping = this.associationOverride.getOwner().attributeMapping(this.associationOverride.getName());
		if (attributeMapping == null || !(attributeMapping instanceof IRelationshipMapping)) {
			return null;
		}
		IEntity targetEntity = ((IRelationshipMapping) attributeMapping).getResolvedTargetEntity();
		if (targetEntity != null) {
			return targetEntity.primaryDbTable();
		}
		return null;
	}

}
