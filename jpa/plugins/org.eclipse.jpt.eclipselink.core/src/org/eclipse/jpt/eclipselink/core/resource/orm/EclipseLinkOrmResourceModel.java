/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.orm;

import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.core.ResourceModel;
import org.eclipse.jpt.core.resource.common.JpaXmlResource;
import org.eclipse.jpt.core.resource.orm.OrmResourceModel;
import org.eclipse.jpt.eclipselink.core.EclipseLinkResourceModel;
import org.eclipse.jpt.eclipselink.core.internal.resource.orm.EclipseLinkOrmResourceModelProvider;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class EclipseLinkOrmResourceModel extends OrmResourceModel
{
	public EclipseLinkOrmResourceModel(IFile file) {
		super(file);
	}
	
	
	@Override
	protected JpaXmlResource buildResource(IFile file) {
		EclipseLinkOrmResourceModelProvider modelProvider =
			EclipseLinkOrmResourceModelProvider.getModelProvider(file.getProject(), file.getProjectRelativePath().toString());
		return modelProvider.getResource();
	}
	
	/**
	 * @see ResourceModel#getResourceType()
	 */
	public String getResourceType() {
		return EclipseLinkResourceModel.ECLIPSELINK_ORM_RESOURCE_TYPE;
	}
	
	@Override
	public EclipseLinkOrmResource getResource() {
		return (EclipseLinkOrmResource) super.getResource();
	}
}
