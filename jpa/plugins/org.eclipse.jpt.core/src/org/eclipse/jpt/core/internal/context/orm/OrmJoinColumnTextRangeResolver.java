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
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JoinColumnTextRangeResolver;

public class OrmJoinColumnTextRangeResolver
	extends OrmNamedColumnTextRangeResolver
	implements JoinColumnTextRangeResolver
{

	public OrmJoinColumnTextRangeResolver(OrmJoinColumn column) {
		super(column);
	}

	@Override
	protected OrmJoinColumn getColumn() {
		return (OrmJoinColumn) super.getColumn();
	}

	public TextRange getTableTextRange() {
		return this.getColumn().getTableTextRange();
	}

	public TextRange getReferencedColumnNameTextRange() {
		return this.getColumn().getReferencedColumnNameTextRange();
	}
}
