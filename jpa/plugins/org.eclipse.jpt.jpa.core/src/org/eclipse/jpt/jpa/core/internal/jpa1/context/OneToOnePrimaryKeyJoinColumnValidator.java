/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;

public class OneToOnePrimaryKeyJoinColumnValidator
	extends PrimaryKeyJoinColumnValidator
{
	public OneToOnePrimaryKeyJoinColumnValidator(
				ReadOnlyPersistentAttribute persistentAttribute,
				ReadOnlyBaseJoinColumn column,
				ReadOnlyBaseJoinColumn.Owner owner) {
		super(persistentAttribute, column, owner);
	}

	@Override
	protected String getVirtualAttributeUnresolvedNameMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected String getVirtualAttributeUnresolvedReferencedColumnNameMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME;
	}

	@Override
	protected String getVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_PRIMARY_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}

	@Override
	protected String getVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_PRIMARY_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}
}
