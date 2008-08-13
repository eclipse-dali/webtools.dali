/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.elorm;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jpt.core.resource.common.JpaXmlResourceModel;
import org.eclipse.jpt.eclipselink.core.EclipseLinkResourceModel;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class EclipseLinkOrmResourceModel extends JpaXmlResourceModel
{
	public EclipseLinkOrmResourceModel(IFile file) {
		super(file);
	}
	
	
	/**
	 * @see ResourceModel#getResourceType()
	 */
	public String getResourceType() {
		return EclipseLinkResourceModel.ECLIPSELINK_ORM_RESOURCE_TYPE;
	}
	
	@Override
	protected EclipseLinkOrmArtifactEdit buildArtifactEdit(IProject project) {
		return EclipseLinkOrmArtifactEdit.getArtifactEditForRead(project);
	}
	
	@Override
	public EclipseLinkOrmResource getResource() {
		return (EclipseLinkOrmResource) super.getResource();
	}
}
