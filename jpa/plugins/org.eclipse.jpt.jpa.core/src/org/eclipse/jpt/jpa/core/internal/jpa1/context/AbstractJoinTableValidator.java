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
import org.eclipse.jpt.jpa.core.context.JoinTable;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;

public abstract class AbstractJoinTableValidator
	extends AbstractTableValidator
{
	protected AbstractJoinTableValidator(JoinTable table) {
		super(table);
	}

	protected AbstractJoinTableValidator(PersistentAttribute persistentAttribute, JoinTable table) {
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
