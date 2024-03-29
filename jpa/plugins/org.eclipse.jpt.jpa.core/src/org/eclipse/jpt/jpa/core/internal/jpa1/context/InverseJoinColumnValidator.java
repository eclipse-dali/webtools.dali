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
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;

public class InverseJoinColumnValidator
	extends BaseJoinColumnValidator<JoinColumn>
{
	protected InverseJoinColumnValidator(
				JoinColumn column,
				JoinColumn.ParentAdapter joinColumnParentAdapter,
				TableDescriptionProvider provider) {
		super(column, joinColumnParentAdapter, provider);
	}

	public InverseJoinColumnValidator(
				PersistentAttribute persistentAttribute,
				JoinColumn column,
				JoinColumn.ParentAdapter joinColumnParentAdapter,
				TableDescriptionProvider provider) {
		super(persistentAttribute, column, joinColumnParentAdapter, provider);
	}

	@Override
	protected JpaValidator buildTableValidator() {
		return new TableValidator();
	}

	@Override
	protected ValidationMessage getUnresolvedNameMessage() {
		return JptJpaCoreValidationMessages.INVERSE_JOIN_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnresolvedNameMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_INVERSE_JOIN_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected ValidationMessage getUnresolvedReferencedColumnNameMessage() {
		return JptJpaCoreValidationMessages.INVERSE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME;
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnresolvedReferencedColumnNameMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_INVERSE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME;
	}

	@Override
	protected ValidationMessage getUnspecifiedNameMultipleJoinColumnsMessage() {
		return JptJpaCoreValidationMessages.INVERSE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_INVERSE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}

	@Override
	protected ValidationMessage getUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return JptJpaCoreValidationMessages.INVERSE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_INVERSE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}


	// ********** table validator **********

	protected class TableValidator
		extends BaseColumnTableValidator
	{
		protected TableValidator() {
			super();
		}

		@Override
		protected ValidationMessage getColumnTableNotValidMessage() {
			return JptJpaCoreValidationMessages.INVERSE_JOIN_COLUMN_TABLE_NOT_VALID;
		}

		@Override
		protected ValidationMessage getVirtualAttributeColumnTableNotValidMessage() {
			return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_INVERSE_JOIN_COLUMN_TABLE_NOT_VALID;
		}
	}
}
