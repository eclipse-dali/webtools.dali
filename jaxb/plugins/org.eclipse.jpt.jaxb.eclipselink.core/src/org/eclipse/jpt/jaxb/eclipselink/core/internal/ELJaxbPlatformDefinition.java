/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.jpt.jaxb.core.internal.AbstractJaxbPlatformDefinition;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinition;


public abstract class ELJaxbPlatformDefinition
		extends AbstractJaxbPlatformDefinition {
	
	/**
	 * See <code>org.eclipse.jpt.jaxb.eclipselink.core/plugin.xml:org.eclipse.jpt.jaxb.core.jaxbPlatforms</code>.
	 */
	public static final String GROUP_ID = "eclipselink"; //$NON-NLS-1$


	protected ELJaxbPlatformDefinition() {
		super();
	}
	
	
	protected abstract JaxbPlatformDefinition getGenericJaxbPlatformDefinition();
	
	
	@Override
	public String getSchemaTypeMapping(String javaTypeName) {
		String mapping = getGenericJaxbPlatformDefinition().getSchemaTypeMapping(javaTypeName);
		return (mapping != null) ? mapping : super.getSchemaTypeMapping(javaTypeName);
	}
	
	@Override
	@SuppressWarnings("nls")
	protected Map<String, String> buildJavaToSchemaTypes() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("java.sql.Date", "date");
		map.put("java.sql.Time", "time");
		map.put("java.sql.Timestamp", "dateTime");
		return map;
	}
}
