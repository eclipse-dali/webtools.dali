/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.orm;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jpt.jpa.core.internal.operations.OrmFileCreationDataModelProperties;
import org.eclipse.jpt.jpa.core.resource.AbstractXmlResourceProvider;
import org.eclipse.jpt.jpa.core.resource.orm.AccessType;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlPersistenceUnitDefaults;
import org.eclipse.jpt.jpa.core.resource.orm.XmlPersistenceUnitMetadata;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCorePlugin;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public class EclipseLinkOrmXmlResourceProvider
	extends AbstractXmlResourceProvider
	implements OrmFileCreationDataModelProperties
{
	/**
	 * (Convenience method) Returns an EclipseLink ORM resource model provider for 
	 * the given file.
	 */
	public static EclipseLinkOrmXmlResourceProvider getXmlResourceProvider(IFile file) {
		return getXmlResourceProvider_(file.getProject(), file.getFullPath());
	}
	
	/**
	 * (Convenience method) Returns an EclipseLink ORM resource model provider for
	 * the given project in the specified runtime location
	 */
	public static EclipseLinkOrmXmlResourceProvider getXmlResourceProvider(IProject project, IPath runtimePath) {
		return getXmlResourceProvider_(project, runtimePath);
	}
	
	/**
	 * (Convenience method) Returns an EclipseLink ORM resource model provider for 
	 * the given project in the default runtime location
	 */
	public static EclipseLinkOrmXmlResourceProvider getDefaultXmlResourceProvider(IProject project) {
		return getXmlResourceProvider(project, JptJpaEclipseLinkCorePlugin.DEFAULT_ECLIPSELINK_ORM_XML_RUNTIME_PATH);
	}
	
	private static EclipseLinkOrmXmlResourceProvider getXmlResourceProvider_(IProject project, IPath fullPath) {
		return new EclipseLinkOrmXmlResourceProvider(project, fullPath);
	}
	
	
	public EclipseLinkOrmXmlResourceProvider(IProject project) {
		this(project, JptJpaEclipseLinkCorePlugin.DEFAULT_ECLIPSELINK_ORM_XML_RUNTIME_PATH);
	}
	
	public EclipseLinkOrmXmlResourceProvider(IProject project, IPath filePath) {
		super(project, filePath, JptJpaEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE);
	}
	
	
	@Override
	protected void populateRoot(Object config) {
		IDataModel dataModel = (IDataModel) config;
		XmlEntityMappings entityMappings = EclipseLinkOrmFactory.eINSTANCE.createXmlEntityMappings();
		entityMappings.setVersion(dataModel.getStringProperty(VERSION));
		getResourceContents().add(entityMappings);
		
		AccessType defaultAccess = (AccessType) dataModel.getProperty(DEFAULT_ACCESS); 
		if (defaultAccess != null) {
			XmlPersistenceUnitMetadata puMetadata = EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata();
			entityMappings.setPersistenceUnitMetadata(puMetadata);
			XmlPersistenceUnitDefaults puDefaults = OrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults();
			puMetadata.setPersistenceUnitDefaults(puDefaults);
			puDefaults.setAccess(defaultAccess);
		}
	}
}
