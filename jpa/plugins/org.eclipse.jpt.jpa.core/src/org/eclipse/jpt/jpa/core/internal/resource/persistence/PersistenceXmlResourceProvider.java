/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.persistence;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.internal.operations.PersistenceFileCreationDataModelProperties;
import org.eclipse.jpt.jpa.core.resource.AbstractXmlResourceProvider;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit;
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
		return getXmlResourceProvider_(file.getProject(), file.getFullPath());
	}
	
	/**
	 * (Convenience method) Returns an persistence resource model provider for
	 * the given project in the specified runtime location
	 */
	public static PersistenceXmlResourceProvider getXmlResourceProvider(IProject project, IPath runtimePath) {
		return getXmlResourceProvider_(project, runtimePath);
	}
	
	/**
	 * (Convenience method) Returns a persistence resource model provider for 
	 * the given project in the default runtime location
	 */
	public static PersistenceXmlResourceProvider getDefaultXmlResourceProvider(IProject project) {
		return getXmlResourceProvider(project, JptJpaCorePlugin.DEFAULT_PERSISTENCE_XML_RUNTIME_PATH);
	}
	
	private static PersistenceXmlResourceProvider getXmlResourceProvider_(IProject project, IPath fullPath) {
		return new PersistenceXmlResourceProvider(project, fullPath);
	}
	
	
	public PersistenceXmlResourceProvider(IProject project) {
		this(project, JptJpaCorePlugin.DEFAULT_PERSISTENCE_XML_RUNTIME_PATH);
	}
		
	public PersistenceXmlResourceProvider(IProject project, IPath filePath) {
		super(project, filePath, JptJpaCorePlugin.PERSISTENCE_XML_CONTENT_TYPE);
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
