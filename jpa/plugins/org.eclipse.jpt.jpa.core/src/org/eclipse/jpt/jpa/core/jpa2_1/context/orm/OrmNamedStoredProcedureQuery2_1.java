/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2_1.context.orm;

import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.orm.OrmQuery;
import org.eclipse.jpt.jpa.core.jpa2_1.context.NamedStoredProcedureQuery2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.java.JavaNamedStoredProcedureQuery2_1;
import org.eclipse.jpt.jpa.core.resource.orm.XmlNamedStoredProcedureQuery;

/**
 * JPA 2.1
 * <code>orm.xml</code> named stored procedure query
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.3
 */
public interface OrmNamedStoredProcedureQuery2_1
	extends OrmQuery, NamedStoredProcedureQuery2_1
{
	XmlNamedStoredProcedureQuery getXmlQuery();
	
	// *********** parameters ************
	
	ListIterable<OrmStoredProcedureParameter2_1> getParameters();

	OrmStoredProcedureParameter2_1 addParameter();

	OrmStoredProcedureParameter2_1 addParameter(int index);

	// ********** metadata conversion *********
	
	/**
	 * Build up a mapping file query from
	 * the given Java query
	 */
	void convertFrom(JavaNamedStoredProcedureQuery2_1 javaQuery);
}
