/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal;

import org.eclipse.core.resources.IProject;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinition;

/**
 * Straightforward implementation of the JAXB project config.
 */
public class SimpleJaxbProjectConfig
		implements JaxbProject.Config {
	
	private IProject project;
	
	private JaxbPlatformDefinition platformDefinition;
	
	
	public SimpleJaxbProjectConfig() {
		super();
	}
	
	
	public IProject getProject() {
		return this.project;
	}
	
	public void setProject(IProject project) {
		this.project = project;
	}
	
	public JaxbPlatformDefinition getPlatformDefinition() {
		return this.platformDefinition;
	}
	
	public void setPlatformDefinition(JaxbPlatformDefinition platformDefinition) {
		this.platformDefinition = platformDefinition;
	}
	
	@Override
	public String toString() {
		return ObjectTools.toString(this, this.project.getName());
	}
}
