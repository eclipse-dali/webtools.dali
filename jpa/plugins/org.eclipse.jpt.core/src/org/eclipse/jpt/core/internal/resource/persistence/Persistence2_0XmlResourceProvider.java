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
import org.eclipse.jpt.core.resource.AbstractXmlResourceProvider;
import org.eclipse.jpt2_0.core.resource.orm.JPA;
import org.eclipse.jpt2_0.core.resource.persistence.Persistence2_0Factory;
import org.eclipse.jpt2_0.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt2_0.core.resource.persistence.XmlPersistenceUnit;

public class Persistence2_0XmlResourceProvider
	extends AbstractXmlResourceProvider
{
	/**
	 * (Convenience method) Returns a persistence resource model provider for 
	 * the given file.
	 */
	public static Persistence2_0XmlResourceProvider getXmlResourceProvider(IFile file) {
		return getXmlResourceProvider_(file.getProject(), file.getFullPath().toString());
	}
	
	/**
	 * (Convenience method) Returns an persistence resource model provider for
	 * the given project in the specified deploy location
	 */
	public static Persistence2_0XmlResourceProvider getXmlResourceProvider(IProject project, String deployLocation) {
		return getXmlResourceProvider_(project, JptCorePlugin.getDeploymentURI(project, deployLocation));
	}
	
	/**
	 * (Convenience method) Returns a persistence resource model provider for 
	 * the given project in the default deploy location
	 */
	public static Persistence2_0XmlResourceProvider getDefaultXmlResourceProvider(IProject project) {
		return getXmlResourceProvider(project, JptCorePlugin.DEFAULT_PERSISTENCE_XML_FILE_PATH);
	}
	
	private static Persistence2_0XmlResourceProvider getXmlResourceProvider_(IProject project, String location) {
		return new Persistence2_0XmlResourceProvider(project, new Path(location));
	}
	
	
	public Persistence2_0XmlResourceProvider(IProject project) {
		this(project, new Path(JptCorePlugin.DEFAULT_PERSISTENCE_XML_FILE_PATH));
	}
		
	public Persistence2_0XmlResourceProvider(IProject project, IPath filePath) {
		super(project, filePath, JptCorePlugin.PERSISTENCE_XML_CONTENT_TYPE);
	}
	
	@Override
	protected void populateRoot() {
		XmlPersistence persistence = Persistence2_0Factory.eINSTANCE.createXmlPersistence();
		persistence.setVersion(JPA.VERSION_2_0);
		XmlPersistenceUnit persistenceUnit = Persistence2_0Factory.eINSTANCE.createXmlPersistenceUnit();
		persistenceUnit.setName(getProject().getName());
		persistence.getPersistenceUnits().add(persistenceUnit);
		getResourceContents().add(persistence);
	}
}
