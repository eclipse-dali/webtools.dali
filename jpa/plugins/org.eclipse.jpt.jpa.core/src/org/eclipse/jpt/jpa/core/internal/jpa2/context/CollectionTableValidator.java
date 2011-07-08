/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context;

import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AbstractTableValidator;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.jpa2.context.CollectionTable2_0;

public class CollectionTableValidator
	extends AbstractTableValidator
{
	public CollectionTableValidator(ReadOnlyPersistentAttribute persistentAttribute, CollectionTable2_0 table, TableTextRangeResolver textRangeResolver) {
		super(persistentAttribute, table, textRangeResolver);
	}

	@Override
	protected String getUnresolvedCatalogMessage() {
		return JpaValidationMessages.COLLECTION_TABLE_UNRESOLVED_CATALOG;
	}

	@Override
	protected String getVirtualAttributeUnresolvedCatalogMessage() {
		return JpaValidationMessages.VIRTUAL_ATTRIBUTE_COLLECTION_TABLE_UNRESOLVED_CATALOG;
	}

	@Override
	protected String getUnresolvedSchemaMessage() {
		return JpaValidationMessages.COLLECTION_TABLE_UNRESOLVED_SCHEMA;
	}

	@Override
	protected String getVirtualAttributeUnresolvedSchemaMessage() {
		return JpaValidationMessages.VIRTUAL_ATTRIBUTE_COLLECTION_TABLE_UNRESOLVED_SCHEMA;
	}

	@Override
	protected String getUnresolvedNameMessage() {
		return JpaValidationMessages.COLLECTION_TABLE_UNRESOLVED_NAME;
	}

	@Override
	protected String getVirtualAttributeUnresolvedNameMessage() {
		return JpaValidationMessages.VIRTUAL_ATTRIBUTE_COLLECTION_TABLE_UNRESOLVED_NAME;
	}
}
