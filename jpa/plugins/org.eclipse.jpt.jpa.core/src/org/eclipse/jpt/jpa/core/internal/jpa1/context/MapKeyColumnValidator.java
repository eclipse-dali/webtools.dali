/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.TableColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;

public class MapKeyColumnValidator
	extends AbstractNamedColumnValidator<ReadOnlyBaseColumn, TableColumnTextRangeResolver>
{
	public MapKeyColumnValidator(
				ReadOnlyPersistentAttribute persistentAttribute,
				ReadOnlyBaseColumn column,
				TableColumnTextRangeResolver textRangeResolver,
				TableDescriptionProvider provider) {
		super(persistentAttribute, column, textRangeResolver, provider);
	}

	@Override
	protected JptValidator buildTableValidator() {
		return new TableValidator();
	}

	@Override
	protected String getUnresolvedNameMessage() {
		return JpaValidationMessages.MAP_KEY_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected String getVirtualAttributeUnresolvedNameMessage() {
		return JpaValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_COLUMN_UNRESOLVED_NAME;
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
			return JpaValidationMessages.MAP_KEY_COLUMN_TABLE_NOT_VALID;
		}

		@Override
		protected String getVirtualAttributeColumnTableNotValidMessage() {
			return JpaValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_COLUMN_TABLE_NOT_VALID;
		}
	}
}
