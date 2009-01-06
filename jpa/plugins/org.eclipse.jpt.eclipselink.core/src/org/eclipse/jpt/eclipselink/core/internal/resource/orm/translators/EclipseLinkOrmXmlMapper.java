/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.orm.translators;

import org.eclipse.jpt.core.internal.resource.orm.translators.OrmXmlMapper;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkConstants;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage;

public interface EclipseLinkOrmXmlMapper
	extends OrmXmlMapper, EclipseLinkConstants
{
	EclipseLinkOrmPackage ECLIPSELINK_ORM_PKG = EclipseLinkOrmPackage.eINSTANCE;
	
	String ACCESS_METHODS = "access-methods"; //$NON-NLS-1$
	String ACCESS_METHODS__GET_METHOD = "get-method"; //$NON-NLS-1$
	String ACCESS_METHODS__SET_METHOD = "set-method"; //$NON-NLS-1$
	
	String BASIC_COLLECTION = "basic-collection";  //$NON-NLS-1$
	
	String BASIC_MAP = "basic-map";  //$NON-NLS-1$
	
	String CACHE = "cache";  //$NON-NLS-1$
	String CACHE__EXPIRY = "expiry";  //$NON-NLS-1$
	String CACHE__SIZE = "size";  //$NON-NLS-1$
	String CACHE__SHARED = "shared";  //$NON-NLS-1$
	String CACHE__TYPE = "type";  //$NON-NLS-1$
	String CACHE__ALWAYS_REFRESH = "always-refresh";  //$NON-NLS-1$
	String CACHE__REFRESH_ONLY_IF_NEWER = "refresh-only-if-newer";  //$NON-NLS-1$
	String CACHE__DISABLE_HITS = "disable-hits";  //$NON-NLS-1$
	String CACHE__COORDINATION_TYPE = "coordination-type";  //$NON-NLS-1$
	
	String CHANGE_TRACKING = "change-tracking";  //$NON-NLS-1$
		
	String CONVERSION_VALUE = "conversion-value"; //$NON-NLS-1$
	String CONVERSION_VALUE__DATA_VALUE = "data-value"; //$NON-NLS-1$
	String CONVERSION_VALUE__OBJECT_VALUE = "object-value"; //$NON-NLS-1$

	String CONVERT = "convert"; //$NON-NLS-1$
	
	String CONVERTER = "converter"; //$NON-NLS-1$
	String CONVERTER__CLASS = "class"; //$NON-NLS-1$
	String CONVERTER__NAME = "name"; //$NON-NLS-1$
	
	String CUSTOMIZER = "customizer";  //$NON-NLS-1$

	String EXISTENCE_CHECKING = "existence-checking";  //$NON-NLS-1$
	
	String EXPIRY_TIME_OF_DAY = "expiry-time-of-day";  //$NON-NLS-1$
	String EXPIRY_TIME_OF_DAY__HOUR = "hour";  //$NON-NLS-1$
	String EXPIRY_TIME_OF_DAY__MINUTE = "minute";  //$NON-NLS-1$
	String EXPIRY_TIME_OF_DAY__SECOND = "second";  //$NON-NLS-1$
	String EXPIRY_TIME_OF_DAY__MILLISECOND = "millisecond";  //$NON-NLS-1$
	
	String JOIN_FETCH = "join-fetch";  //$NON-NLS-1$
	
	String MUTABLE = "mutable";  //$NON-NLS-1$
	
	String NAMED_STORED_PROCEDURE_QUERY = "named-stored-procedure-query"; //$NON-NLS-1$
		String NAMED_STORED_PROCEDURE_QUERY__NAME = "name"; //$NON-NLS-1$
		String NAMED_STORED_PROCEDURE_QUERY__HINT = "hint"; //$NON-NLS-1$
		String NAMED_STORED_PROCEDURE_QUERY__RESULT_CLASS = "result-class"; //$NON-NLS-1$
		String NAMED_STORED_PROCEDURE_QUERY__RESULT_SET_MAPPING = "result-set-mapping"; //$NON-NLS-1$
		String NAMED_STORED_PROCEDURE_QUERY__PROCEDURE_NAME = "procedure-name"; //$NON-NLS-1$
		String NAMED_STORED_PROCEDURE_QUERY__RETURNS_RESULT_SET = "returns-result-set"; //$NON-NLS-1$
	String NAMED_STORED_PROCEDURE_QUERY__PARAMETER = "parameter"; //$NON-NLS-1$
		String PARAMETER__DIRECTION = "direction"; //$NON-NLS-1$
		String PARAMETER__NAME = "name"; //$NON-NLS-1$
		String PARAMETER__QUERY_PARAMETER = "query-parameter"; //$NON-NLS-1$
		String PARAMETER__TYPE = "type"; //$NON-NLS-1$
		String PARAMETER__JDBC_TYPE = "jdbc-type"; //$NON-NLS-1$
		String PARAMETER__JDBC_TYPE_NAME = "jdbc-type-name"; //$NON-NLS-1$
		
	String OBJECT_TYPE_CONVERTER = "object-type-converter"; //$NON-NLS-1$
	String OBJECT_TYPE_CONVERTER__NAME = "name"; //$NON-NLS-1$
	String OBJECT_TYPE_CONVERTER__DATA_TYPE = "data-type"; //$NON-NLS-1$
	String OBJECT_TYPE_CONVERTER__OBJECT_TYPE = "object-type"; //$NON-NLS-1$
	String OBJECT_TYPE_CONVERTER__DEFAULT_OBJECT_VALUE = "default-object-value"; //$NON-NLS-1$

	String PRIVATE_OWNED = "private-owned";  //$NON-NLS-1$
	
	String PROPERTY = "property"; //$NON-NLS-1$
	String PROPERTY__NAME = "name"; //$NON-NLS-1$
	String PROPERTY__VALUE = "value"; //$NON-NLS-1$
	String PROPERTY__VALUE_TYPE = "value-type"; //$NON-NLS-1$
	
	String READ_ONLY = "read-only";  //$NON-NLS-1$
	
	
	String STRUCT_CONVERTER = "struct-converter"; //$NON-NLS-1$
	String STRUCT_CONVERTER__NAME = "name"; //$NON-NLS-1$
	String STRUCT_CONVERTER__CONVERTER = "converter"; //$NON-NLS-1$
	
	String TRANSFORMATION = "transformation";  //$NON-NLS-1$

	String TYPE = "type";  //$NON-NLS-1$
	
	String TYPE_CONVERTER = "type-converter"; //$NON-NLS-1$
	String TYPE_CONVERTER__NAME = "name"; //$NON-NLS-1$
	String TYPE_CONVERTER__DATA_TYPE = "data-type"; //$NON-NLS-1$
	String TYPE_CONVERTER__OBJECT_TYPE = "object-type"; //$NON-NLS-1$
}
