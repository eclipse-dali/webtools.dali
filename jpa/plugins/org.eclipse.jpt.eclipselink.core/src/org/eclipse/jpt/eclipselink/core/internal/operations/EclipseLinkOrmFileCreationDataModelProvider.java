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

import java.util.Iterator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jem.util.emf.workbench.ProjectUtilities;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.operations.OrmFileCreationDataModelProvider;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaPlatform;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelOperation;
import org.eclipse.wst.common.project.facet.core.FacetedProjectFramework;

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
	protected Iterator<IProject> jpaIProjects() {
		return new FilteringIterator<IProject, IProject>(CollectionTools.iterator(ProjectUtilities.getAllProjects())) {
			@Override
			protected boolean accept(IProject project) {
				try {
					if (FacetedProjectFramework.hasProjectFacet(project, JptCorePlugin.FACET_ID)) {
						JpaProject jpaProject = JptCorePlugin.getJpaProject(project);
						return jpaProject != null && jpaProject.getJpaPlatform().getId().equals(EclipseLinkJpaPlatform.ID);
					}
					return false;
				}
				catch (CoreException ce) {
					return false;
				}
			}
		};
	}
}
