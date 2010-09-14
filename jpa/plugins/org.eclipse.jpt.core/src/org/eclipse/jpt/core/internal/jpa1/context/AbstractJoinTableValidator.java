/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context;

import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;

public abstract class AbstractJoinTableValidator extends AbstractTableValidator
{
	protected AbstractJoinTableValidator(JoinTable table, TableTextRangeResolver textRangeResolver) {
		super(table, textRangeResolver);
	}

	protected AbstractJoinTableValidator(PersistentAttribute persistentAttribute, JoinTable table, TableTextRangeResolver textRangeResolver) {
		super(persistentAttribute, table, textRangeResolver);
	}

	@Override
	protected String getUnresolvedCatalogMessage() {
		return JpaValidationMessages.JOIN_TABLE_UNRESOLVED_CATALOG;
	}

	@Override
	protected String getUnresolvedSchemaMessage() {
		return JpaValidationMessages.JOIN_TABLE_UNRESOLVED_SCHEMA;
	}

	@Override
	protected String getUnresolvedNameMessage() {
		return JpaValidationMessages.JOIN_TABLE_UNRESOLVED_NAME;
	}
}
