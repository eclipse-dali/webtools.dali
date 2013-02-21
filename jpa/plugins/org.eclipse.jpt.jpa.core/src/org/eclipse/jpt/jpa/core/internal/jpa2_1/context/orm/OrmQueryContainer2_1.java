/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2_1.context.orm;

import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryContainer;
import org.eclipse.jpt.jpa.core.jpa2_1.context.QueryContainer2_1;

public interface OrmQueryContainer2_1
	extends QueryContainer2_1, OrmQueryContainer
{
	// ********** named stored procedure queries **********

	ListIterable<OrmNamedStoredProcedureQuery2_1> getNamedStoredProcedureQueries();

	OrmNamedStoredProcedureQuery2_1 addNamedStoredProcedureQuery();

	OrmNamedStoredProcedureQuery2_1 addNamedStoredProcedureQuery(int index);

}
