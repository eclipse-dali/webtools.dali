/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context;

import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.BaseJoinColumnValidator;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;

public class MapKeyJoinColumnValidator
	extends BaseJoinColumnValidator<ReadOnlyJoinColumn>
{
	protected MapKeyJoinColumnValidator(
				ReadOnlyJoinColumn column,
				ReadOnlyJoinColumn.Owner joinColumnOwner,
				TableDescriptionProvider provider) {
		super(column, joinColumnOwner, provider);
	}

	public MapKeyJoinColumnValidator(
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
		return JptJpaCoreValidationMessages.MAP_KEY_JOIN_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected String getVirtualAttributeUnresolvedNameMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_JOIN_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected String getUnresolvedReferencedColumnNameMessage() {
		return JptJpaCoreValidationMessages.MAP_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME;
	}

	@Override
	protected String getVirtualAttributeUnresolvedReferencedColumnNameMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME;
	}

	@Override
	protected String getUnspecifiedNameMultipleJoinColumnsMessage() {
		return JptJpaCoreValidationMessages.MAP_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}

	@Override
	protected String getVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_MAP_KEY_JOIN_COLUMNS;
	}

	@Override
	protected String getUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return JptJpaCoreValidationMessages.MAP_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}

	@Override
	protected String getVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_MAP_KEY_JOIN_COLUMNS;
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
			return JptJpaCoreValidationMessages.MAP_KEY_JOIN_COLUMN_TABLE_NOT_VALID;
		}

		@Override
		protected String getVirtualAttributeColumnTableNotValidMessage() {
			return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_JOIN_COLUMN_TABLE_NOT_VALID;
		}
	}
}
