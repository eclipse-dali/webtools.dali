/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;

public class TableValidator
	extends AbstractTableValidator
{
	public TableValidator(Table table) {
		super(table);
	}

	@Override
	protected ValidationMessage getUnresolvedCatalogMessage() {
		return JptJpaCoreValidationMessages.TABLE_UNRESOLVED_CATALOG;
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnresolvedCatalogMessage() {
		throw this.buildAttributeTableNotSupportedException();
	}

	@Override
	protected ValidationMessage getUnresolvedSchemaMessage() {
		return JptJpaCoreValidationMessages.TABLE_UNRESOLVED_SCHEMA;
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnresolvedSchemaMessage() {
		throw this.buildAttributeTableNotSupportedException();
	}

	@Override
	protected ValidationMessage getUnresolvedNameMessage() {
		return JptJpaCoreValidationMessages.TABLE_UNRESOLVED_NAME;
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnresolvedNameMessage() {
		throw this.buildAttributeTableNotSupportedException();
	}

	protected UnsupportedOperationException buildAttributeTableNotSupportedException() {
		return new UnsupportedOperationException("An attribute cannot specify a table"); //$NON-NLS-1$
	}
}
