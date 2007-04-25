/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/        
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.swt.widgets.Shell;

public class PrimaryKeyJoinColumnDialog extends AbstractJoinColumnDialog<IPrimaryKeyJoinColumn> {

	private IEntity entity;
	
	PrimaryKeyJoinColumnDialog(Shell parent, IEntity entity) {
		super(parent);
		this.entity = entity;
	}

	PrimaryKeyJoinColumnDialog(Shell parent, IPrimaryKeyJoinColumn joinColumn) {
		super(parent, joinColumn);
		this.entity = (IEntity) joinColumn.eContainer();
	}

	protected Table getNameTable() {
		return this.entity.primaryDbTable();
	}
	
	protected Table getReferencedNameTable() {
		return this.entity.parentEntity().primaryDbTable();
	}
}