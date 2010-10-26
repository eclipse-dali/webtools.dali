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
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatform;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * Straightforward implementation of the JAXB project config.
 */
public class SimpleJaxbProjectConfig implements JaxbProject.Config {
	protected IProject project;
	protected JaxbPlatform jaxbPlatform;

	public SimpleJaxbProjectConfig() {
		super();
	}

	public IProject getProject() {
		return this.project;
	}

	public void setProject(IProject project) {
		this.project = project;
	}

	public JaxbPlatform getJaxbPlatform() {
		return this.jaxbPlatform;
	}

	public void setJaxbPlatform(JaxbPlatform jaxbPlatform) {
		this.jaxbPlatform = jaxbPlatform;
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.project.getName());
	}

}
