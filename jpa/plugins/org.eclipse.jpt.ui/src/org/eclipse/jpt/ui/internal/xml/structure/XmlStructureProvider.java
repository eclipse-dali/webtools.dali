/*******************************************************************************
 *  Copyright (c) 2006 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.xml.structure;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jpt.core.internal.JpaCorePlugin;
import org.eclipse.jpt.ui.internal.structure.IJpaStructureProvider;

public class XmlStructureProvider implements IJpaStructureProvider {

	public String fileContentType() {
		return JpaCorePlugin.ORM_XML_CONTENT_TYPE;
	}
	
	public ITreeContentProvider buildContentProvider() {
		return new AdapterFactoryContentProvider(new JpaCoreXmlItemProviderAdapterFactory());
	}
	
	public ILabelProvider buildLabelProvider() {
		return new AdapterFactoryLabelProvider(new JpaCoreXmlItemProviderAdapterFactory());
	}
	
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
