/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
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
	String COMPOSITE_MEMBER = "composite-member";

	String MULTITENANT = "multitenant";
		String MULTITENANT__TYPE = "type";
	String MULTITENANT_TYPE = "multiltenant-type";
	String NAMED_STORED_FUNCTION_QUERY = "named-stored-function-query";
		String NAMED_STORED_FUNCTION_QUERY__NAME = "nname";
	String NAMED_PLSQL_STORED_PROCEDURE_QUERY = "named-plsql-stored-procedure-query";
		String NAMED_PLSQL_STORED_PROCEDURE_QUERY__NAME = "name";
	String NAMED_PLSQL_STORED_FUNCTION_QUERY = "named-plsql-stored-function-query";
		String NAMED_PLSQL_STORED_FUNCTION_QUERY__NAME = "name";
	String PLSQL_RECORD = "plsql-record";
		String PLSQL_RECORD__NAME = "name";
	String PLSQL_TABLE = "plsql-table";
		String PLSQL_TABLE__NAME = "name";
	String STRUCT = "struct";
		String STRUCT__NAME = "name";
	String STRUCTURE = "structure";
	String TENANT_DISCRIMINATOR_COLUMN = "tenant-discriminator-column";
		String TENANT_DISCRIMINATOR_COLUMN__NAME = "name";


}