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
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;

public abstract class AbstractJoinTableValidator
	extends AbstractTableValidator
{
	protected AbstractJoinTableValidator(ReadOnlyJoinTable table) {
		super(table);
	}

	protected AbstractJoinTableValidator(PersistentAttribute persistentAttribute, ReadOnlyJoinTable table) {
		super(persistentAttribute, table);
	}

	@Override
	protected ValidationMessage getUnresolvedCatalogMessage() {
		return JptJpaCoreValidationMessages.JOIN_TABLE_UNRESOLVED_CATALOG;
	}

	@Override
	protected ValidationMessage getUnresolvedSchemaMessage() {
		return JptJpaCoreValidationMessages.JOIN_TABLE_UNRESOLVED_SCHEMA;
	}

	@Override
	protected ValidationMessage getUnresolvedNameMessage() {
		return JptJpaCoreValidationMessages.JOIN_TABLE_UNRESOLVED_NAME;
	}
}
