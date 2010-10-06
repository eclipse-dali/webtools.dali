/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.gen;

/**
 *  ClassesGeneratorExtensionOptions
 */
public class ClassesGeneratorExtensionOptions
{
	private String classpath;
	private boolean allowsExtensions;
	
	private String additionalArgs;
	
	// ********** constructor **********
	
	public ClassesGeneratorExtensionOptions() {
		super();
	}

	// ********** getters/setters *********

	public boolean allowsExtensions() {
		return this.allowsExtensions;
	}

	public void setAllowsExtensions(boolean allowsExtensions) {
		this.allowsExtensions = allowsExtensions;
	}
	
	public String getClasspath() {
		return this.classpath;
	}
	
	public void setClasspath(String classpath){
		this.classpath = classpath;
	}

	public String getAdditionalArgs() {
		return this.additionalArgs;
	}
	
	public void setAdditionalArgs(String additionalArgs){
		this.additionalArgs = additionalArgs;
	}

}
