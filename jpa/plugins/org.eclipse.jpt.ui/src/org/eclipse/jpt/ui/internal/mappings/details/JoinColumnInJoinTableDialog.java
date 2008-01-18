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

import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IJoinTable;
import org.eclipse.jpt.core.internal.context.base.IMultiRelationshipMapping;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.swt.widgets.Shell;

//if there is only 1 joinColumn and the user is editing it, they should be
//able to define defaults.  otherwise, we probably shouldn't allow it.
public class JoinColumnInJoinTableDialog extends AbstractJoinColumnDialog<IJoinColumn> {

	private IJoinTable joinTable;

	JoinColumnInJoinTableDialog(Shell parent, IJoinTable joinTable) {
		super(parent);
		this.joinTable = joinTable;
	}

	JoinColumnInJoinTableDialog(Shell parent, IJoinColumn joinColumn) {
		super(parent, joinColumn);
		this.joinTable = (IJoinTable) joinColumn.eContainer();
	}
	protected IJoinTable getJoinTable() {
		return this.joinTable;
	}

	@Override
	protected Table getNameTable() {
		return getJoinTable().dbTable();
	}

	@Override
	protected Table getReferencedNameTable() {
		IMultiRelationshipMapping multiRelationshipMapping = (IMultiRelationshipMapping) getJoinTable().eContainer();
		return multiRelationshipMapping.typeMapping().primaryDbTable();
	}
}