/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptResourceModel;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JaxbResourceModelProvider;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.oxm.OxmXmlResourceProvider;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.Oxm;

public class OxmResourceModelProvider
	implements JaxbResourceModelProvider {
	
	// singleton
	private static final OxmResourceModelProvider INSTANCE = new OxmResourceModelProvider();
	
	
	/**
	 * Return the singleton.
	 */
	public static OxmResourceModelProvider instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private OxmResourceModelProvider() {
		super();
	}
	
	
	public IContentType getContentType() {
		return Oxm.CONTENT_TYPE;
	}
	
	public JptResourceModel buildResourceModel(JaxbProject jaxbProject, IFile file) {
		return OxmXmlResourceProvider.getXmlResourceProvider(file).getXmlResource();
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
