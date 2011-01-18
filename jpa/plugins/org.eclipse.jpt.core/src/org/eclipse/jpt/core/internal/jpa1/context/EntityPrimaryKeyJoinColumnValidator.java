/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context;

import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.internal.context.BaseJoinColumnTextRangeResolver;

public class EntityPrimaryKeyJoinColumnValidator
	extends PrimaryKeyJoinColumnValidator
{
	public EntityPrimaryKeyJoinColumnValidator(
				BaseJoinColumn column,
				BaseJoinColumn.Owner owner,
				BaseJoinColumnTextRangeResolver textRangeResolver) {
		super(column, owner, textRangeResolver);
	}

	@Override
	protected String getVirtualAttributeUnresolvedNameMessage() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected String getVirtualAttributeUnresolvedReferencedColumnNameMessage() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected String getVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected String getVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		throw new UnsupportedOperationException();
	}
}
