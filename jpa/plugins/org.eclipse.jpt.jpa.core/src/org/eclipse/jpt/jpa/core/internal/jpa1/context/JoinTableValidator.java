/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;

public class JoinTableValidator
	extends AbstractJoinTableValidator
{
	public JoinTableValidator(ReadOnlyPersistentAttribute persistentAttribute, ReadOnlyJoinTable table) {
		super(persistentAttribute, table);
	}

	@Override
	protected String getVirtualAttributeUnresolvedCatalogMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_TABLE_UNRESOLVED_CATALOG;
	}

	@Override
	protected String getVirtualAttributeUnresolvedSchemaMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_TABLE_UNRESOLVED_SCHEMA;
	}

	@Override
	protected String getVirtualAttributeUnresolvedNameMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_TABLE_UNRESOLVED_NAME;
	}
}
