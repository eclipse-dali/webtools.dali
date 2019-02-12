/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.operations;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.internal.operations.OrmFileCreationDataModelProvider;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelOperation;

public class EclipseLinkOrmFileCreationDataModelProvider
	extends OrmFileCreationDataModelProvider
{
	/**
	 * required default constructor
	 */
	public EclipseLinkOrmFileCreationDataModelProvider() {
		super();
	}
	
	
	@Override
	public IDataModelOperation getDefaultOperation() {
		return new EclipseLinkOrmFileCreationOperation(getDataModel());
	}
	
	@Override
	protected String getDefaultFileName() {
		return XmlEntityMappings.DEFAULT_RUNTIME_PATH.lastSegment();
	}
	
	@Override
	protected IContentType getContentType() {
		return XmlEntityMappings.CONTENT_TYPE;
	}
	
	@Override
	protected boolean platformIsSupported(JpaPlatform jpaPlatform) {
		return super.platformIsSupported(jpaPlatform) &&
				jpaPlatform.getConfig().getGroupConfig().getId().equals(EclipseLinkJpaPlatformFactory.GROUP_ID);
	}
}
