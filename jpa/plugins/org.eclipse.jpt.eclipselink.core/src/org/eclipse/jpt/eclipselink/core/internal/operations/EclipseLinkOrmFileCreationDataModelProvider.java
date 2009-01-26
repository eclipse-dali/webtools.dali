/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.operations;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.operations.OrmFileCreationDataModelProvider;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaPlatformProvider;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelOperation;

public class EclipseLinkOrmFileCreationDataModelProvider extends OrmFileCreationDataModelProvider
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
	public Object getDefaultProperty(String propertyName) {
		if (propertyName.equals(FILE_PATH)) {
			return new Path(JptEclipseLinkCorePlugin.DEFAULT_ECLIPSELINK_ORM_XML_FILE_PATH).toPortableString();
		}
		return super.getDefaultProperty(propertyName);
	}
	
	@Override
	protected Filter<IProject> buildJpaIProjectsFilter() {
		return new EclipseLinkJpaIProjectsFilter();
	}

	protected class EclipseLinkJpaIProjectsFilter extends JpaIProjectsFilter {
		@Override
		protected boolean accept_(IProject project) throws CoreException {
			if (super.accept_(project)) {
				JpaProject jpaProject = JptCorePlugin.getJpaProject(project);
				return (jpaProject != null) && jpaProject.getJpaPlatform().getId().equals(EclipseLinkJpaPlatformProvider.ID);
			}
			return false;
		}
	}
	
}
