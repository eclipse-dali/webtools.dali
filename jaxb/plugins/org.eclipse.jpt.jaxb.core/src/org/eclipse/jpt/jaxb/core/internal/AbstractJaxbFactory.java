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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaResourceModel;
import org.eclipse.jpt.jaxb.core.JaxbFactory;
import org.eclipse.jpt.jaxb.core.JaxbFile;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JaxbProject.Config;

/**
 * Central class that allows extenders to easily replace implementations of
 * various Dali interfaces.
 */
public abstract class AbstractJaxbFactory
	implements JaxbFactory
{
	protected AbstractJaxbFactory() {
		super();
	}
	
	
	// ********** Core Model **********
	
	public JaxbProject buildJaxbProject(Config config) {
		return new GenericJaxbProject(config);
	}

	public JaxbFile buildJaxbFile(JaxbProject jaxbProject, IFile file, IContentType contentType, JpaResourceModel resourceModel) {
		return new GenericJaxbFile(jaxbProject, file, contentType, resourceModel);
	}

//	// ********** Context Nodes **********
//	
//	public JpaRootContextNode buildRootContextNode(JpaProject parent) {
//		return new GenericRootContextNode(parent);
//	}
}
