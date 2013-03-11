/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.core.resource.ProjectResourceLocator;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResourceProvider;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.core.internal.resource.orm.OrmXmlResourceProvider;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public class OrmFileCreationOperation
	extends AbstractJpaFileCreationOperation
	implements OrmFileCreationDataModelProperties
{
	public OrmFileCreationOperation(IDataModel dataModel) {
		super(dataModel);
	}
	
	
	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		SubMonitor sm = SubMonitor.convert(monitor, 5);
		IStatus status = super.execute(sm.newChild(4), info);
		
		if (status.isOK()) {
			addMappingFileToPersistenceXml();
			sm.worked(1);
		}
		
		return OK_STATUS;
	}
	
	protected PersistenceUnit getPersistenceUnit() throws ExecutionException {
		String pUnitName = getDataModel().getStringProperty(PERSISTENCE_UNIT);
		JpaProject jpaProject = getJpaProject();
		PersistenceXml persistenceXml = jpaProject.getContextModelRoot().getPersistenceXml();
		if (persistenceXml == null) {
			throw new ExecutionException("Project does not have a persistence.xml file"); //$NON-NLS-1$
		}
		Persistence persistence = persistenceXml.getRoot();
		if (persistence == null) {
			throw new ExecutionException("persistence.xml does not have a persistence node."); //$NON-NLS-1$
		}
		for (PersistenceUnit pUnit : persistence.getPersistenceUnits()) {
			if (pUnitName.equals(pUnit.getName())) {
				return pUnit;
			}
		}
		throw new ExecutionException("persistence.xml does not have persistence unit named \'" + pUnitName + "\'"); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	protected void addMappingFileToPersistenceXml() throws ExecutionException {
		if (this.getDataModel().getBooleanProperty(ADD_TO_PERSISTENCE_UNIT)) {
			this.addMappingFileToPersistenceXml_();
		}
	}

	protected void addMappingFileToPersistenceXml_() throws ExecutionException {
		PersistenceUnit pUnit = getPersistenceUnit();
		
		IPath containerPath = (IPath) getDataModel().getProperty(CONTAINER_PATH);
		String fileName = getDataModel().getStringProperty(FILE_NAME);
		IContainer container = PlatformTools.getContainer(containerPath);
		IPath filePath = container.getFullPath().append(fileName);
		IProject project = container.getProject();
		ProjectResourceLocator locator = (ProjectResourceLocator) project.getAdapter(ProjectResourceLocator.class);
		IPath runtimePath = locator.getRuntimePath(filePath);
		boolean found = false;
		for (MappingFileRef ref : pUnit.getSpecifiedMappingFileRefs()) {
			if (runtimePath.equals(ref.getFileName())) {
				found = true;
				break;
			}
		}
		if ( ! found) {
			pUnit.addSpecifiedMappingFileRef(runtimePath.toString());
		}
		this.getJpaProject().getPersistenceXmlResource().save();
	}

	@Override
	protected JptXmlResourceProvider getXmlResourceProvider(IFile file) {
		return OrmXmlResourceProvider.getXmlResourceProvider(file);
	}
}
