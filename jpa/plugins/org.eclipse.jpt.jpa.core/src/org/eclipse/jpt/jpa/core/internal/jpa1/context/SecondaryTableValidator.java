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
import org.eclipse.jpt.jpa.core.context.SecondaryTable;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;

public class SecondaryTableValidator
	extends AbstractTableValidator
{
	public SecondaryTableValidator(SecondaryTable table) {
		super(table);
	}

	@Override
	protected ValidationMessage getUnresolvedCatalogMessage() {
		return JptJpaCoreValidationMessages.SECONDARY_TABLE_UNRESOLVED_CATALOG;
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnresolvedCatalogMessage() {
		throw this.buildAttributeTableNotSupportedException();
	}

	@Override
	protected ValidationMessage getUnresolvedSchemaMessage() {
		return JptJpaCoreValidationMessages.SECONDARY_TABLE_UNRESOLVED_SCHEMA;
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnresolvedSchemaMessage() {
		throw this.buildAttributeTableNotSupportedException();
	}

	@Override
	protected ValidationMessage getUnresolvedNameMessage() {
		return JptJpaCoreValidationMessages.SECONDARY_TABLE_UNRESOLVED_NAME;
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnresolvedNameMessage() {
		throw this.buildAttributeTableNotSupportedException();
	}

	protected UnsupportedOperationException buildAttributeTableNotSupportedException() {
		return new UnsupportedOperationException("An attribute cannot specify a secondary table"); //$NON-NLS-1$
	}
}
