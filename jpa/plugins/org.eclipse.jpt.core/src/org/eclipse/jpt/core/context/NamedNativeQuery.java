/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;



public interface NamedNativeQuery extends Query
{
	String getResultClass();
	void setResultClass(String value);
		String RESULT_CLASS_PROPERTY = "resultClassProperty";

	String getResultSetMapping();
	void setResultSetMapping(String value);
		String RESULT_SET_MAPPING_PROPERTY = "resultSetMappingProperty";

}
