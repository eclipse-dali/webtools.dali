/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.libval;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.IVMInstall2;
import org.eclipse.jdt.launching.IVMInstallType;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.LibraryLocation;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jaxb.core.JaxbFacet;
import org.eclipse.jpt.jaxb.core.libprov.JaxbLibraryProviderInstallOperationConfig;
import org.eclipse.jst.common.project.facet.core.StandardJreRuntimeComponent;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.runtime.IRuntime;
import org.eclipse.wst.common.project.facet.core.runtime.IRuntimeComponent;


public class JaxbLibValUtil {
	
	static IProjectFacetVersion findJreJaxbVersion(JaxbLibraryProviderInstallOperationConfig config) {
		IRuntime runtime = config.getFacetedProject().getPrimaryRuntime();
		
		if (runtime != null) {
			for (IRuntimeComponent rc : runtime.getRuntimeComponents()) {
				IVMInstall vm = findVMInstall(rc);
				if (vm != null) {
					return findJreJaxbVersion(vm);
				}
			}
		}
		
		IVMInstall vm = JavaRuntime.getDefaultVMInstall();
		
		IJavaProject javaProject = JavaCore.create(config.getFacetedProject().getProject());
		if (javaProject != null) {	
			try {
				vm = JavaRuntime.getVMInstall(javaProject);
			}
			catch (CoreException ce) {
				// do nothing, just use the default install
			}
		}
		
		if (vm != null) {
			return findJreJaxbVersion(vm);
		}
		
		return null;
	}
	
	private static IVMInstall findVMInstall(IRuntimeComponent rc) {
		String vmInstallTypeId 
			= rc.getProperty(StandardJreRuntimeComponent.PROP_VM_INSTALL_TYPE);
		String vmInstallId
			= rc.getProperty(StandardJreRuntimeComponent.PROP_VM_INSTALL_ID);
		
		if (vmInstallTypeId == null || vmInstallId == null) {
			return null;
		}
		
		IVMInstallType vmInstallType = JavaRuntime.getVMInstallType(vmInstallTypeId);
		
		if( vmInstallType == null ) {
			return null;
		}
		
		return vmInstallType.findVMInstall(vmInstallId);
	}
	
	private static IProjectFacetVersion findJreJaxbVersion(IVMInstall vm) {
		if (vm instanceof IVMInstall2) {
			String javaVersion = ((IVMInstall2) vm).getJavaVersion();
			if (javaVersion != null) {
				if (javaVersion.startsWith(JavaCore.VERSION_1_7)) {
					return JaxbFacet.VERSION_2_2;
				}
				// all other versions except 1.6 have no corresponding version (as of yet)
				// 1.6 must be further analyzed
				if (! javaVersion.startsWith(JavaCore.VERSION_1_6)) {
					return null;
				}
			}
		}
		
		Iterable<IPath> vmLibPaths 
			= new TransformationIterable<LibraryLocation, IPath>(
					new ArrayIterable<LibraryLocation>(JavaRuntime.getLibraryLocations(vm))) {
				@Override
				protected IPath transform(LibraryLocation o) {
					return o.getSystemLibraryPath();
				}
			};
		
		boolean foundXmlSeeAlso = false;  // marker for jaxb 2.1 +
		boolean foundJAXBPermission = false;  // marker for jaxb 2.2 +
		
		for (IPath vmLibPath : vmLibPaths) {
			File file = vmLibPath.toFile();
			
			if (file.exists()) {
				ZipFile zip = null;
				
				try {
					zip = new ZipFile(file);
					
					foundXmlSeeAlso |= zip.getEntry("javax/xml/bind/annotation/XmlSeeAlso.class") != null;
					foundJAXBPermission |= zip.getEntry("javax/xml/bind/JAXBPermission.class") != null;
					
					// short circuit for JAXB 2.2
					if (foundJAXBPermission) {
						return JaxbFacet.VERSION_2_2;
					}
				}
				catch (IOException e) {}
				finally {
					if (zip != null) {
						try {
							zip.close();
						}
						catch (IOException e) {}
					}
				}
			}
		}
		
		if (foundXmlSeeAlso) {
			return JaxbFacet.VERSION_2_1;
		}
					
		return null;
	}
}
