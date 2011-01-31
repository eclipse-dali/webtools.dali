/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.context.orm.OrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.context.BaseJoinColumnTextRangeResolver;

public class OrmPrimaryKeyJoinColumnTextRangeResolver
	extends OrmNamedColumnTextRangeResolver
	implements
		BaseJoinColumnTextRangeResolver
{

	public OrmPrimaryKeyJoinColumnTextRangeResolver(OrmPrimaryKeyJoinColumn column) {
		super(column);
	}

	@Override
	protected OrmPrimaryKeyJoinColumn getColumn() {
		return (OrmPrimaryKeyJoinColumn) super.getColumn();
	}

	public TextRange getReferencedColumnNameTextRange() {
		return this.getColumn().getReferencedColumnNameTextRange();
	}
}
