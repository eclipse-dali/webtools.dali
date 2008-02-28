/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.common;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.wst.common.componentcore.ArtifactEdit;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public abstract class JpaArtifactEdit extends ArtifactEdit
{
	public JpaArtifactEdit(IProject aProject, boolean toAccessAsReadOnly) 
			throws IllegalArgumentException {
		super(aProject, toAccessAsReadOnly);
	}
	
	
	/**
	 * @return a resource for the given file
	 */
	public abstract JpaXmlResource getResource(IFile file);
	
	/**
	 * @param deployURI - this must be in a deployment relevant form 
	 * 	(e.g "META-INF/persistence.xml" instead of "src/META-INF/persistence.xml")
	 * @return a resource for the given deployment file URI
	 */
	public abstract JpaXmlResource getResource(String deployURI);
}
