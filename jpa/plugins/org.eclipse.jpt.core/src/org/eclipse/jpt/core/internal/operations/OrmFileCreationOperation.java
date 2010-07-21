/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.operations;

import java.util.Iterator;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.core.internal.resource.orm.OrmXmlResourceProvider;
import org.eclipse.jpt.core.resource.AbstractXmlResourceProvider;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
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
		PersistenceXml persistenceXml = jpaProject.getRootContextNode().getPersistenceXml();
		if (persistenceXml == null) {
			throw new ExecutionException("Project does not have a persistence.xml file"); //$NON-NLS-1$
		}
		Persistence persistence = persistenceXml.getPersistence();
		if (persistence == null) {
			throw new ExecutionException("persistence.xml does not have a persistence node."); //$NON-NLS-1$
		}
		for (Iterator<PersistenceUnit> stream = persistence.persistenceUnits(); stream.hasNext(); ) {
			PersistenceUnit pUnit = stream.next();
			if (pUnitName.equals(pUnit.getName())) {
				return pUnit;
			}
		}
		throw new ExecutionException("persistence.xml does not have persistence unit named \'" + pUnitName + "\'"); //$NON-NLS-1$
	}
	
	protected void addMappingFileToPersistenceXml() throws ExecutionException {
		if (! getDataModel().getBooleanProperty(ADD_TO_PERSISTENCE_UNIT)) {
			return;
		}
		JpaProject jpaProject = JptCorePlugin.getJpaProject(this.createdFile.getProject());
		JpaXmlResource resource = jpaProject.getPersistenceXmlResource();
	
		final PersistenceUnit pUnit = getPersistenceUnit();
		
		resource.modify(new Runnable() {
			public void run() {
				String filePath = getDataModel().getStringProperty(FILE_PATH);
				for (Iterator<MappingFileRef> stream = pUnit.specifiedMappingFileRefs(); stream.hasNext(); ) {
					if (filePath.equals(stream.next().getFileName())) {
						return;
					}
				}
				pUnit.addSpecifiedMappingFileRef(new Path(filePath).toPortableString());
			}
		});
	}
	
	@Override
	protected AbstractXmlResourceProvider getXmlResourceProvider(IFile file) {
		return OrmXmlResourceProvider.getXmlResourceProvider(file);
	}
}
