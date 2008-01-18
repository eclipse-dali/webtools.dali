/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.context.base.ISingleRelationshipMapping;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.swt.widgets.Shell;

public class JoinColumnInRelationshipMappingDialog extends JoinColumnDialog {

	private ISingleRelationshipMapping singleRelationshipMapping;

	JoinColumnInRelationshipMappingDialog(Shell parent, ISingleRelationshipMapping singleRelationshipMapping) {
		super(parent);
		this.singleRelationshipMapping = singleRelationshipMapping;
	}

	JoinColumnInRelationshipMappingDialog(Shell parent, IJoinColumn joinColumn) {
		super(parent, joinColumn);
		this.singleRelationshipMapping = (ISingleRelationshipMapping) joinColumn.eContainer();
	}

	@Override
	protected String defaultTableName() {
		if (getJoinColumn() != null) {
			return getJoinColumn().getDefaultTable();
		}
		return this.singleRelationshipMapping.typeMapping().getTableName();
	}

	@Override
	protected Schema getSchema() {
		return this.singleRelationshipMapping.typeMapping().dbSchema();
	}

	@Override
	protected Table getNameTable() {
		Schema schema = this.getSchema();
		return (schema == null) ? null : schema.tableNamed(tableName());
	}

	@Override
	protected Table getReferencedNameTable() {
		IEntity targetEntity = this.singleRelationshipMapping.getResolvedTargetEntity();
		if (targetEntity != null) {
			return targetEntity.primaryDbTable();
		}
		return null;
	}
}
