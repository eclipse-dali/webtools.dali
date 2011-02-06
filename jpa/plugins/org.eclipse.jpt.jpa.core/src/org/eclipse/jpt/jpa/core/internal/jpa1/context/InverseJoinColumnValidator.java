/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.BaseColumnTableValidator.TableDescriptionProvider;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;

public class InverseJoinColumnValidator
	extends BaseJoinColumnValidator<JoinColumn, JoinColumnTextRangeResolver>
{
	public InverseJoinColumnValidator(
				JoinColumn column,
				JoinColumn.Owner joinColumnOwner,
				JoinColumnTextRangeResolver textRangeResolver,
				TableDescriptionProvider provider) {
		super(column, joinColumnOwner, textRangeResolver, provider);
	}

	public InverseJoinColumnValidator(
				PersistentAttribute persistentAttribute,
				JoinColumn column,
				JoinColumn.Owner joinColumnOwner,
				JoinColumnTextRangeResolver textRangeResolver,
				TableDescriptionProvider provider) {
		super(persistentAttribute, column, joinColumnOwner, textRangeResolver, provider);
	}

	@Override
	protected TableValidator buildTableValidator() {
		return new InverseJoinColumnTableValidator(this.persistentAttribute, this.column, this.textRangeResolver, this.tableDescriptionProvider);
	}

	@Override
	protected String getUnresolvedNameMessage() {
		return JpaValidationMessages.INVERSE_JOIN_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected String getVirtualAttributeUnresolvedNameMessage() {
		return JpaValidationMessages.VIRTUAL_ATTRIBUTE_INVERSE_JOIN_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected String getUnresolvedReferencedColumnNameMessage() {
		return JpaValidationMessages.INVERSE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME;
	}

	@Override
	protected String getVirtualAttributeUnresolvedReferencedColumnNameMessage() {
		return JpaValidationMessages.VIRTUAL_ATTRIBUTE_INVERSE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME;
	}

	@Override
	protected String getUnspecifiedNameMultipleJoinColumnsMessage() {
		return JpaValidationMessages.INVERSE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}

	@Override
	protected String getVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage() {
		return JpaValidationMessages.VIRTUAL_ATTRIBUTE_INVERSE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}

	@Override
	protected String getUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return JpaValidationMessages.INVERSE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}

	@Override
	protected String getVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return JpaValidationMessages.VIRTUAL_ATTRIBUTE_INVERSE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}

	public static class InverseJoinColumnTableValidator
		extends BaseColumnTableValidator
	{
		public InverseJoinColumnTableValidator(
				PersistentAttribute persistentAttribute,
				JoinColumn column,
				JoinColumnTextRangeResolver textRangeResolver,
				TableDescriptionProvider provider) {
			super(persistentAttribute, column, textRangeResolver, provider);
		}

		@Override
		protected String getColumnTableNotValidMessage() {
			return JpaValidationMessages.INVERSE_JOIN_COLUMN_TABLE_NOT_VALID;
		}

		@Override
		protected String getVirtualAttributeColumnTableNotValidMessage() {
			return JpaValidationMessages.VIRTUAL_ATTRIBUTE_INVERSE_JOIN_COLUMN_TABLE_NOT_VALID;
		}
	}
}
