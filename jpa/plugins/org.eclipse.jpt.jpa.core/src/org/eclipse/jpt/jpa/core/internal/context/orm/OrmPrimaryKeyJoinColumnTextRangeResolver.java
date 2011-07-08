/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyBaseJoinColumn;
import org.eclipse.jpt.jpa.core.internal.context.BaseJoinColumnTextRangeResolver;

public class OrmPrimaryKeyJoinColumnTextRangeResolver
	extends AbstractOrmNamedColumnTextRangeResolver<OrmReadOnlyBaseJoinColumn>
	implements BaseJoinColumnTextRangeResolver
{

	public OrmPrimaryKeyJoinColumnTextRangeResolver(OrmReadOnlyBaseJoinColumn column) {
		super(column);
	}

	public TextRange getReferencedColumnNameTextRange() {
		return this.column.getReferencedColumnNameTextRange();
	}
}
