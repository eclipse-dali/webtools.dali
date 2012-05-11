/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource.persistence;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.operations.PersistenceFileCreationDataModelProperties;
import org.eclipse.jpt.core.resource.AbstractXmlResourceProvider;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public class PersistenceXmlResourceProvider
	extends AbstractXmlResourceProvider
	implements PersistenceFileCreationDataModelProperties
{
	/**
	 * (Convenience method) Returns a persistence resource model provider for 
	 * the given file.
	 */
	public static PersistenceXmlResourceProvider getXmlResourceProvider(IFile file) {
		return getXmlResourceProvider_(file.getProject(), file.getFullPath().toString());
	}
	
	/**
	 * (Convenience method) Returns an persistence resource model provider for
	 * the given project in the specified deploy location
	 */
	public static PersistenceXmlResourceProvider getXmlResourceProvider(IProject project, String deployLocation) {
		return getXmlResourceProvider_(project, JptCorePlugin.getDeploymentURI(project, deployLocation));
	}
	
	/**
	 * (Convenience method) Returns a persistence resource model provider for 
	 * the given project in the default deploy location
	 */
	public static PersistenceXmlResourceProvider getDefaultXmlResourceProvider(IProject project) {
		return getXmlResourceProvider(project, JptCorePlugin.DEFAULT_PERSISTENCE_XML_FILE_PATH);
	}
	
	private static PersistenceXmlResourceProvider getXmlResourceProvider_(IProject project, String location) {
		return new PersistenceXmlResourceProvider(project, new Path(location));
	}
	
	
	public PersistenceXmlResourceProvider(IProject project) {
		this(project, new Path(JptCorePlugin.DEFAULT_PERSISTENCE_XML_FILE_PATH));
	}
		
	public PersistenceXmlResourceProvider(IProject project, IPath filePath) {
		super(project, filePath, JptCorePlugin.PERSISTENCE_XML_CONTENT_TYPE);
	}
	
	
	@Override
	protected void populateRoot(Object config) {
		IDataModel dataModel = (IDataModel) config;
		XmlPersistence persistence = PersistenceFactory.eINSTANCE.createXmlPersistence();
		persistence.setVersion(dataModel.getStringProperty(VERSION));
		XmlPersistenceUnit persistenceUnit = PersistenceFactory.eINSTANCE.createXmlPersistenceUnit();
		persistenceUnit.setName(getProject().getName());
		persistence.getPersistenceUnits().add(persistenceUnit);
		getResourceContents().add(persistence);
	}
}
