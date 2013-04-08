/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context;

import java.util.Iterator;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jpt.common.core.internal.utility.JavaProjectTools;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.context.java.JavaType;

public class JavaTypeAdapterFactory
		implements IAdapterFactory {
	
	private static final Class<?>[] ADAPTER_LIST = new Class[] { IPackageFragment.class };
	
	
	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST;
	}
	
	public Object getAdapter(Object adaptableObject, @SuppressWarnings("rawtypes") Class adapterType) {
		if (adaptableObject instanceof JavaType) {
			return getAdapter((JavaType) adaptableObject, adapterType);
		}
		return null;
	}
	
	private Object getAdapter(JavaType javaType, Class<?> adapterType) {
		if (adapterType == IPackageFragment.class) {
			return getPackageFragment(javaType);
		}
		return null;
	}
	
	private IPackageFragment getPackageFragment(JavaType javaType) {
		JaxbProject jaxbProject = javaType.getJaxbProject();
		if (jaxbProject != null) {
			 Iterator<IPackageFragment> packageFragments 
			 		= JavaProjectTools.getPackageFragments(jaxbProject.getJavaProject(), javaType.getJaxbPackage().getName()).iterator();
			 if (packageFragments.hasNext()) {
				 return packageFragments.next();
			 }
		}
		return null;
	}
}
