/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource.persistence;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jpt.core.internal.IResourceModel;
import org.eclipse.jpt.core.internal.resource.common.JpaXmlResourceModel;

public class PersistenceResourceModel extends JpaXmlResourceModel
{
	public PersistenceResourceModel(IFile file) {
		super(file);
	}
	
	
	/**
	 * @see IResourceModel#getResourceType()
	 */
	public String getResourceType() {
		return IResourceModel.PERSISTENCE_RESOURCE_TYPE;
	}
	
	@Override
	protected PersistenceArtifactEdit buildArtifactEdit(IProject project) {
		return PersistenceArtifactEdit.getArtifactEditForRead(project);
	}
}
