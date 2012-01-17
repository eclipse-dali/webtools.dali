/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.orm;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.internal.operations.OrmFileCreationDataModelProperties;
import org.eclipse.jpt.jpa.core.resource.AbstractXmlResourceProvider;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.XmlPersistenceUnitDefaults;
import org.eclipse.jpt.jpa.core.resource.orm.XmlPersistenceUnitMetadata;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public class OrmXmlResourceProvider
	extends AbstractXmlResourceProvider
	implements OrmFileCreationDataModelProperties
{
	/**
	 * (Convenience method) Returns an ORM resource model provider for 
	 * the given file.
	 */
	public static OrmXmlResourceProvider getXmlResourceProvider(IFile file) {
		return getXmlResourceProvider_(file.getProject(), file.getFullPath());
	}
	
	/**
	 * (Convenience method) Returns an ORM resource model provider for
	 * the given project in the specified runtime location
	 */
	public static OrmXmlResourceProvider getXmlResourceProvider(IProject project, IPath runtimePath) {
		return getXmlResourceProvider_(project, runtimePath);
	}
	
	/**
	 * (Convenience method) Returns an ORM resource model provider for 
	 * the given project in the default runtime location
	 */
	public static OrmXmlResourceProvider getDefaultXmlResourceProvider(IProject project) {
		return getXmlResourceProvider(project, JptJpaCorePlugin.DEFAULT_ORM_XML_RUNTIME_PATH);
	}
	
	private static OrmXmlResourceProvider getXmlResourceProvider_(IProject project, IPath fullPath) {
		return new OrmXmlResourceProvider(project, fullPath);
	}
	
	
	public OrmXmlResourceProvider(IProject project) {
		this(project, JptJpaCorePlugin.DEFAULT_ORM_XML_RUNTIME_PATH);
	}
	
	public OrmXmlResourceProvider(IProject project, IPath filePath) {
		super(project, filePath, JptJpaCorePlugin.ORM_XML_CONTENT_TYPE);
	}
	
	@Override
	protected void populateRoot(Object config) {
		IDataModel dataModel = (IDataModel) config;
		XmlEntityMappings entityMappings = OrmFactory.eINSTANCE.createXmlEntityMappings();
		entityMappings.setVersion(dataModel.getStringProperty(VERSION));
		getResourceContents().add(entityMappings);
		
		String defaultAccess = (String) dataModel.getProperty(DEFAULT_ACCESS); 
		if (defaultAccess != null) {
			XmlPersistenceUnitMetadata puMetadata = OrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata();
			entityMappings.setPersistenceUnitMetadata(puMetadata);
			XmlPersistenceUnitDefaults puDefaults = OrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults();
			puMetadata.setPersistenceUnitDefaults(puDefaults);
			puDefaults.setAccess(defaultAccess);
		}
	}
}
