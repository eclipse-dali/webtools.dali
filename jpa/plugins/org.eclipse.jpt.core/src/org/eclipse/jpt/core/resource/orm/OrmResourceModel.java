/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.orm;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jpt.core.ResourceModel;
import org.eclipse.jpt.core.resource.common.JpaXmlResourceModel;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
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
