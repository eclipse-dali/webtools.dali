/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.jpt.jaxb.core.internal.AbstractJaxbPlatformDefinition;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinition;


public abstract class AbstractELJaxbPlatformDefinition
		extends AbstractJaxbPlatformDefinition {
	
	protected AbstractELJaxbPlatformDefinition() {
		super();
	}
	
	
	protected abstract JaxbPlatformDefinition getGenericJaxbPlatformDefinition();
	
	
	@Override
	public String getSchemaTypeMapping(String javaTypeName) {
		String mapping = getGenericJaxbPlatformDefinition().getSchemaTypeMapping(javaTypeName);
		return (mapping != null) ? mapping : super.getSchemaTypeMapping(javaTypeName);
	}
	
	@Override
	protected Map<String, String> buildJavaToSchemaTypes() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("java.sql.Date", "date");
		map.put("java.sql.Time", "time");
		map.put("java.sql.Timestamp", "dateTime");
		return map;
	}
}
