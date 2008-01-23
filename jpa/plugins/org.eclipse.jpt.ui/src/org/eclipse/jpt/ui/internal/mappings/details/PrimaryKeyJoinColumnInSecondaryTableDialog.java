/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.context.base.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.context.base.ISecondaryTable;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.swt.widgets.Shell;

public class PrimaryKeyJoinColumnInSecondaryTableDialog extends AbstractJoinColumnDialog<IPrimaryKeyJoinColumn> {

	private ISecondaryTable secondaryTable;

	PrimaryKeyJoinColumnInSecondaryTableDialog(Shell parent, ISecondaryTable secondaryTable) {
		super(parent);
		this.secondaryTable = secondaryTable;
	}

	PrimaryKeyJoinColumnInSecondaryTableDialog(Shell parent, IPrimaryKeyJoinColumn joinColumn) {
		super(parent, joinColumn);
		this.secondaryTable = (ISecondaryTable) joinColumn.eContainer();
	}

	@Override
	protected Table getNameTable() {
		return this.secondaryTable.dbTable();
	}

	@Override
	protected Table getReferencedNameTable() {
		return this.secondaryTable.typeMapping().primaryDbTable();
	}
}
