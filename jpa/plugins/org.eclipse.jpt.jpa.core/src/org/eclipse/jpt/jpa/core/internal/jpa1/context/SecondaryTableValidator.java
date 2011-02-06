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

import org.eclipse.jpt.jpa.core.context.SecondaryTable;
import org.eclipse.jpt.jpa.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;

public class SecondaryTableValidator extends AbstractTableValidator
{
	public SecondaryTableValidator(SecondaryTable table, TableTextRangeResolver textRangeResolver) {
		super(table, textRangeResolver);
	}

	@Override
	public SecondaryTable getTable() {
		return (SecondaryTable) super.getTable();
	}

	@Override
	protected String getUnresolvedCatalogMessage() {
		return JpaValidationMessages.SECONDARY_TABLE_UNRESOLVED_CATALOG;
	}

	@Override
	protected String getVirtualAttributeUnresolvedCatalogMessage() {
		throw new UnsupportedOperationException("No SecondaryTable annotations exist on attributes"); //$NON-NLS-1$
	}

	@Override
	protected String getUnresolvedSchemaMessage() {
		return JpaValidationMessages.SECONDARY_TABLE_UNRESOLVED_SCHEMA;
	}

	@Override
	protected String getVirtualAttributeUnresolvedSchemaMessage() {
		throw new UnsupportedOperationException("No SecondaryTable annotations exist on attributes"); //$NON-NLS-1$
	}

	@Override
	protected String getUnresolvedNameMessage() {
		return JpaValidationMessages.SECONDARY_TABLE_UNRESOLVED_NAME;
	}

	@Override
	protected String getVirtualAttributeUnresolvedNameMessage() {
		throw new UnsupportedOperationException("No SecondaryTable annotations exist on attributes"); //$NON-NLS-1$
	}
}
