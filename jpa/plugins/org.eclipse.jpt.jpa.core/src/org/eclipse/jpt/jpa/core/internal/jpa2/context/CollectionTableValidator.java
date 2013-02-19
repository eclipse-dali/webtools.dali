/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context;

import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AbstractTableValidator;
import org.eclipse.jpt.jpa.core.jpa2.context.CollectionTable2_0;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;

public class CollectionTableValidator
	extends AbstractTableValidator
{
	public CollectionTableValidator(ReadOnlyPersistentAttribute persistentAttribute, CollectionTable2_0 table) {
		super(persistentAttribute, table);
	}

	@Override
	protected ValidationMessage getUnresolvedCatalogMessage() {
		return JptJpaCoreValidationMessages.COLLECTION_TABLE_UNRESOLVED_CATALOG;
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnresolvedCatalogMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_COLLECTION_TABLE_UNRESOLVED_CATALOG;
	}

	@Override
	protected ValidationMessage getUnresolvedSchemaMessage() {
		return JptJpaCoreValidationMessages.COLLECTION_TABLE_UNRESOLVED_SCHEMA;
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnresolvedSchemaMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_COLLECTION_TABLE_UNRESOLVED_SCHEMA;
	}

	@Override
	protected ValidationMessage getUnresolvedNameMessage() {
		return JptJpaCoreValidationMessages.COLLECTION_TABLE_UNRESOLVED_NAME;
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnresolvedNameMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_COLLECTION_TABLE_UNRESOLVED_NAME;
	}
}
