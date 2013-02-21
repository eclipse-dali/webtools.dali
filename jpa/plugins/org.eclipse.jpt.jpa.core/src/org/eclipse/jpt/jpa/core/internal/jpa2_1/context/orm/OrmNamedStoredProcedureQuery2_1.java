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
import org.eclipse.jpt.jpa.core.context.orm.OrmQuery;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.NamedStoredProcedureQuery2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.java.JavaNamedStoredProcedureQuery2_1;
import org.eclipse.jpt.jpa.core.resource.orm.XmlNamedStoredProcedureQuery;

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
