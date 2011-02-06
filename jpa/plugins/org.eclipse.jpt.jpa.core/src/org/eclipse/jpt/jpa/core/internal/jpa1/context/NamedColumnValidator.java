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

import org.eclipse.jpt.jpa.core.context.BaseColumn;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.BaseColumnTableValidator.TableDescriptionProvider;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;

public class NamedColumnValidator
	extends AbstractNamedColumnValidator<BaseColumn, BaseColumnTextRangeResolver>
{

	public NamedColumnValidator(
				BaseColumn column,
				BaseColumnTextRangeResolver textRangeResolver,
				TableDescriptionProvider provider) {
		super(column, textRangeResolver, provider);
	}

	public NamedColumnValidator(
				PersistentAttribute persistentAttribute,
				BaseColumn column,
				BaseColumnTextRangeResolver textRangeResolver,
				TableDescriptionProvider provider) {
		super(persistentAttribute, column, textRangeResolver, provider);
	}

	@Override
	protected TableValidator buildTableValidator() {
		return new BaseColumnTableValidator(this.persistentAttribute, this.column, this.textRangeResolver, this.tableDescriptionProvider);
	}

	@Override
	protected String getUnresolvedNameMessage() {
		return JpaValidationMessages.COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected String getVirtualAttributeUnresolvedNameMessage() {
		return JpaValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_UNRESOLVED_NAME;
	}
}
