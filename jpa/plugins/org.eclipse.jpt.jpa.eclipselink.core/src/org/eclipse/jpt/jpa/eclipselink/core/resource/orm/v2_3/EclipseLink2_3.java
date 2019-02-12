/*******************************************************************************
 *  Copyright (c) 2011, 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3;

import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLink2_2;


@SuppressWarnings("nls")
public interface EclipseLink2_3
	extends EclipseLink2_2
{
	String SCHEMA_NAMESPACE = EclipseLink.SCHEMA_NAMESPACE;
	String SCHEMA_LOCATION = "http://www.eclipse.org/eclipselink/xsds/eclipselink_orm_2_3.xsd";
	String SCHEMA_VERSION	= "2.3";

	// EclipseLink 2.3 specific nodes
	String ARRAY = "array";
		String ARRAY__DATABASE_TYPE = "database-type";
		String ARRAY__TARGET_CLASS = "target-class";
		String ARRAY__ATTRIBUTE_TYPE = "attribute-type";

	String COMPOSITE_MEMBER = "composite-member";

	String MULTITENANT = "multitenant";
		String MULTITENANT__TYPE = "type";
	String MULTITENANT_TYPE = "multiltenant-type";

	String NAMED_STORED_FUNCTION_QUERY = "named-stored-function-query";
	String NAMED_PLSQL_STORED_PROCEDURE_QUERY = "named-plsql-stored-procedure-query";
	String NAMED_PLSQL_STORED_FUNCTION_QUERY = "named-plsql-stored-function-query";
	String PLSQL_RECORD = "plsql-record";
	String PLSQL_TABLE = "plsql-table";
	String STRUCT = "struct";
	String STRUCTURE = "structure";
		String STRUCTURE__ATTRIBUTE_TYPE = "attribute-type";

	String TENANT_DISCRIMINATOR_COLUMN = "tenant-discriminator-column";
		String TENANT_DISCRIMINATOR_COLUMN__CONTEXT_PROPERTY = "context-property";
		String TENANT_DISCRIMINATOR_COLUMN__PRIMARY_KEY= "primary-key";
		String TENANT_DISCRIMINATOR_COLUMN__TABLE = "table";

	String CALL_BY_INDEX = "call-by-index"; //$NON-NLS-1$
	String MULTIPLE_RESULT_SETS = "multiple-result-sets"; //$NON-NLS-1$
	String OPTIONAL = "optional"; //$NON-NLS-1$

}