/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.jpa.core.context.BaseJoinColumn;

public class EntityPrimaryKeyJoinColumnValidator
	extends PrimaryKeyJoinColumnValidator
{
	public EntityPrimaryKeyJoinColumnValidator(
				BaseJoinColumn column,
				BaseJoinColumn.ParentAdapter parentAdapter) {
		super(column, parentAdapter);
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnresolvedNameMessage() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnresolvedReferencedColumnNameMessage() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		throw new UnsupportedOperationException();
	}
}
