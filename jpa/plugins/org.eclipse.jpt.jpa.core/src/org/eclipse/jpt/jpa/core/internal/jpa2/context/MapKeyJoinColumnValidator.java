/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context;

import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.BaseJoinColumnValidator;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;

public class MapKeyJoinColumnValidator
	extends BaseJoinColumnValidator<JoinColumn>
{
	protected MapKeyJoinColumnValidator(
				JoinColumn column,
				JoinColumn.ParentAdapter joinColumnParentAdapter,
				TableDescriptionProvider provider) {
		super(column, joinColumnParentAdapter, provider);
	}

	public MapKeyJoinColumnValidator(
				PersistentAttribute persistentAttribute,
				JoinColumn column,
				JoinColumn.ParentAdapter joinColumnParentAdapter,
				TableDescriptionProvider provider) {
		super(persistentAttribute, column, joinColumnParentAdapter, provider);
	}

	@Override
	protected JptValidator buildTableValidator() {
		return new TableValidator();
	}

	@Override
	protected ValidationMessage getUnresolvedNameMessage() {
		return JptJpaCoreValidationMessages.MAP_KEY_JOIN_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnresolvedNameMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_JOIN_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected ValidationMessage getUnresolvedReferencedColumnNameMessage() {
		return JptJpaCoreValidationMessages.MAP_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME;
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnresolvedReferencedColumnNameMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME;
	}

	@Override
	protected ValidationMessage getUnspecifiedNameMultipleJoinColumnsMessage() {
		return JptJpaCoreValidationMessages.MAP_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_MAP_KEY_JOIN_COLUMNS;
	}

	@Override
	protected ValidationMessage getUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return JptJpaCoreValidationMessages.MAP_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
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
		protected ValidationMessage getColumnTableNotValidMessage() {
			return JptJpaCoreValidationMessages.MAP_KEY_JOIN_COLUMN_TABLE_NOT_VALID;
		}

		@Override
		protected ValidationMessage getVirtualAttributeColumnTableNotValidMessage() {
			return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_JOIN_COLUMN_TABLE_NOT_VALID;
		}
	}
}
