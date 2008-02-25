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
package org.eclipse.jpt.core.resource.orm;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jpt.core.ResourceModel;
import org.eclipse.jpt.core.resource.common.JpaXmlResourceModel;

public class OrmResourceModel extends JpaXmlResourceModel
{
	public OrmResourceModel(IFile file) {
		super(file);
	}
	
	
	/**
	 * @see ResourceModel#getResourceType()
	 */
	public String getResourceType() {
		return ResourceModel.ORM_RESOURCE_TYPE;
	}
	
	@Override
	protected OrmArtifactEdit buildArtifactEdit(IProject project) {
		return OrmArtifactEdit.getArtifactEditForRead(project);
	}
	
	@Override
	public OrmResource resource() {
		return (OrmResource) super.resource();
	}
}
