/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.java.structure;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jpt.ui.internal.structure.IJpaStructureProvider;

public class JavaStructureProvider implements IJpaStructureProvider
{
	public String fileContentType() {
		return JavaCore.JAVA_SOURCE_CONTENT_TYPE;
	}

	public ITreeContentProvider buildContentProvider() {
		return new AdapterFactoryContentProvider(new JpaCoreJavaItemProviderAdapterFactory());
	}
	
	public ILabelProvider buildLabelProvider() {
		return new AdapterFactoryLabelProvider(new JpaCoreJavaItemProviderAdapterFactory());
	}
	
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
