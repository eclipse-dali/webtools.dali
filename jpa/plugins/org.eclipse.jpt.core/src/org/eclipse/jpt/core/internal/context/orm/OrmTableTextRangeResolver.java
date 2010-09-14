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

import org.eclipse.jpt.core.context.orm.OrmBaseTable;
import org.eclipse.jpt.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.core.utility.TextRange;

public class OrmTableTextRangeResolver
	implements TableTextRangeResolver
{
	protected final OrmBaseTable ormTable;

	public OrmTableTextRangeResolver(OrmBaseTable ormTable) {
		this.ormTable = ormTable;
	}

	protected OrmBaseTable getTable() {
		return this.ormTable;
	}

	public TextRange getNameTextRange() {
		return this.ormTable.getNameTextRange();
	}

	public TextRange getCatalogTextRange() {
		return this.ormTable.getCatalogTextRange();
	}

	public TextRange getSchemaTextRange() {
		return this.ormTable.getSchemaTextRange();
	}
}
