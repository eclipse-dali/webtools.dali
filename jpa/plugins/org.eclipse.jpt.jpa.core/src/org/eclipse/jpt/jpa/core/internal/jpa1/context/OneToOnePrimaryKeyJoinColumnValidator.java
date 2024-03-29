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
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;

public class OneToOnePrimaryKeyJoinColumnValidator
	extends PrimaryKeyJoinColumnValidator
{
	public OneToOnePrimaryKeyJoinColumnValidator(
				PersistentAttribute persistentAttribute,
				BaseJoinColumn column,
				BaseJoinColumn.ParentAdapter parentAdapter) {
		super(persistentAttribute, column, parentAdapter);
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnresolvedNameMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnresolvedReferencedColumnNameMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME;
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_PRIMARY_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_PRIMARY_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}
}
