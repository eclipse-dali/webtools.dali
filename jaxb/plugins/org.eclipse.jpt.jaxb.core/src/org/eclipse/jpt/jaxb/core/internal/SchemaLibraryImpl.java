/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.core.SchemaLibrary;

public class SchemaLibraryImpl
		implements SchemaLibrary {
	
	private JaxbProject project;
	
	private final Map<String, String> schemaLocations; 
	
	
	SchemaLibraryImpl(JaxbProject project) {
		this.project = project;
		this.schemaLocations = new HashMap<String, String>();
		readProjectPreferences();
	}
	
	
	protected void readProjectPreferences() {
		Map<String, String> schemaMap = JptJaxbCorePlugin.getSchemaLocationMap(this.project.getProject());
		this.schemaLocations.putAll(schemaMap);
	}
	
	public Map<String, String> getSchemaLocations() {
		return Collections.unmodifiableMap(this.schemaLocations);
	}
	
	public void setSchemaLocations(Map<String, String> schemaLocations) {
		this.schemaLocations.clear();
		JptJaxbCorePlugin.setSchemaLocationMap(this.project.getProject(), schemaLocations);
		readProjectPreferences();
	}
}
