/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.oxm;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jpt.common.core.internal.resource.xml.AbstractJptXmlResourceProvider;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EclipseLink;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmFactory;

public class OxmXmlResourceProvider
		extends AbstractJptXmlResourceProvider {
	
	/**
	 * (Convenience method) Returns an OXM resource model provider for 
	 * the given file.
	 */
	public static OxmXmlResourceProvider getXmlResourceProvider(IFile file) {
		return getXmlResourceProvider_(file.getProject(), file.getFullPath());
	}
	
	/**
	 * (Convenience method) Returns an OXM resource model provider for
	 * the given project in the specified runtime location
	 */
	public static OxmXmlResourceProvider getXmlResourceProvider(IProject project, IPath runtimePath) {
		return getXmlResourceProvider_(project, runtimePath);
	}
	
	
	private static OxmXmlResourceProvider getXmlResourceProvider_(IProject project, IPath fullPath) {
		return new OxmXmlResourceProvider(project, fullPath);
	}
	
	
	public OxmXmlResourceProvider(IProject project, IPath filePath) {
		super(project, filePath, EXmlBindings.CONTENT_TYPE);
	}
	
	@Override
	protected void populateRoot(Object config) {
		EXmlBindings xmlBindings = OxmFactory.eINSTANCE.createEXmlBindings();
		xmlBindings.setVersion(EclipseLink.SCHEMA_VERSION_2_4); // TODO - for now
		getResourceContents().add(xmlBindings);
	}
}
