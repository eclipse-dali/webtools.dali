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

import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;

public class JoinColumnValidator
	extends BaseJoinColumnValidator<ReadOnlyJoinColumn>
{
	protected JoinColumnValidator(
				ReadOnlyJoinColumn column,
				ReadOnlyJoinColumn.Owner joinColumnOwner,
				TableDescriptionProvider provider) {
		super(column, joinColumnOwner, provider);
	}

	public JoinColumnValidator(
				ReadOnlyPersistentAttribute persistentAttribute,
				ReadOnlyJoinColumn column,
				ReadOnlyJoinColumn.Owner joinColumnOwner,
				TableDescriptionProvider provider) {
		super(persistentAttribute, column, joinColumnOwner, provider);
	}

	@Override
	protected JptValidator buildTableValidator() {
		return new TableValidator();
	}

	@Override
	protected String getUnresolvedNameMessage() {
		return JpaValidationMessages.JOIN_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected String getVirtualAttributeUnresolvedNameMessage() {
		return JpaValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected String getUnresolvedReferencedColumnNameMessage() {
		return JpaValidationMessages.JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME;
	}

	@Override
	protected String getVirtualAttributeUnresolvedReferencedColumnNameMessage() {
		return JpaValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME;
	}

	@Override
	protected String getUnspecifiedNameMultipleJoinColumnsMessage() {
		return JpaValidationMessages.JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}

	@Override
	protected String getVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage() {
		return JpaValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}

	@Override
	protected String getUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return JpaValidationMessages.JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}

	@Override
	protected String getVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return JpaValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}


	// ********** table validator **********

	protected class TableValidator
		extends BaseColumnTableValidator
	{
		protected TableValidator() {
			super();
		}

		@Override
		protected String getColumnTableNotValidMessage() {
			return JpaValidationMessages.JOIN_COLUMN_TABLE_NOT_VALID;
		}

		@Override
		protected String getVirtualAttributeColumnTableNotValidMessage() {
			return JpaValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_TABLE_NOT_VALID;
		}
	}
}
