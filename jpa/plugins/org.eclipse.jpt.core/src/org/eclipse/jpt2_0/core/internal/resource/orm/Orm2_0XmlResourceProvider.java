/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt2_0.core.internal.resource.orm;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.resource.AbstractXmlResourceProvider;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt2_0.core.resource.orm.JPA;

public class Orm2_0XmlResourceProvider
	extends AbstractXmlResourceProvider
{
	/**
	 * (Convenience method) Returns an ORM resource model provider for 
	 * the given file.
	 */
	public static Orm2_0XmlResourceProvider getXmlResourceProvider(IFile file) {
		return getXmlResourceProvider_(file.getProject(), file.getFullPath().toString());
	}
	
	/**
	 * (Convenience method) Returns an ORM resource model provider for
	 * the given project in the specified deploy location
	 */
	public static Orm2_0XmlResourceProvider getXmlResourceProvider(IProject project, String deployLocation) {
		return getXmlResourceProvider_(project, JptCorePlugin.getDeploymentURI(project, deployLocation));
	}
	
	/**
	 * (Convenience method) Returns an ORM resource model provider for 
	 * the given project in the default deploy location
	 */
	public static Orm2_0XmlResourceProvider getDefaultXmlResourceProvider(IProject project) {
		return getXmlResourceProvider(project, JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
	}
	
	private static Orm2_0XmlResourceProvider getXmlResourceProvider_(IProject project, String location) {
		return new Orm2_0XmlResourceProvider(project, new Path(location));
	}
	
	
	public Orm2_0XmlResourceProvider(IProject project) {
		this(project, new Path(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH));
	}
	
	public Orm2_0XmlResourceProvider(IProject project, IPath filePath) {
		super(project, filePath, JptCorePlugin.ORM2_0_XML_CONTENT_TYPE);
	}
	
	@Override
	protected void populateRoot() {
		XmlEntityMappings entityMappings = OrmFactory.eINSTANCE.createXmlEntityMappings();
		entityMappings.setVersion(JPA.VERSION_2_0);
		getResourceContents().add(entityMappings);
	}
}
