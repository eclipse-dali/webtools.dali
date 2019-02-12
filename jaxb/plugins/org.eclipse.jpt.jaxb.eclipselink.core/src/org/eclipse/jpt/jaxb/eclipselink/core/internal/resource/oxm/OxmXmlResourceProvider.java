/*******************************************************************************
 *  Copyright (c) 2012, 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.oxm;

import static org.eclipse.jpt.jaxb.eclipselink.core.internal.operations.OxmFileCreationDataModelProperties.*;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jpt.common.core.internal.resource.xml.AbstractJptXmlResourceProvider;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public class OxmXmlResourceProvider
		extends AbstractJptXmlResourceProvider {
	
	/**
	 * (Convenience method) Returns an Oxm resource model provider for 
	 * the given file.
	 */
	public static OxmXmlResourceProvider getXmlResourceProvider(IFile file) {
		return getXmlResourceProvider_(file.getProject(), file.getFullPath());
	}
	
	/**
	 * (Convenience method) Returns an Oxm resource model provider for
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
		IDataModel dataModel = (IDataModel) config;
		EXmlBindings xmlBindings = OxmFactory.eINSTANCE.createEXmlBindings();
		xmlBindings.setDocumentVersion(dataModel.getStringProperty(VERSION));
		String packageName = dataModel.getStringProperty(PACKAGE_NAME); 
		if (! StringTools.isBlank(packageName)) {
			xmlBindings.setPackageName(packageName);
		}	
		getResourceContents().add(xmlBindings);
	}
}
