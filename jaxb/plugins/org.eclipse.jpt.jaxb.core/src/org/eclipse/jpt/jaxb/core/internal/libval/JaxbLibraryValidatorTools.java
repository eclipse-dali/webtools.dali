/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.libval;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.IVMInstall2;
import org.eclipse.jdt.launching.IVMInstallType;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.LibraryLocation;
import org.eclipse.jpt.common.core.internal.libval.LibraryValidatorTools;
import org.eclipse.jpt.common.core.libprov.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jst.common.project.facet.core.JavaFacet;
import org.eclipse.jst.common.project.facet.core.StandardJreRuntimeComponent;
import org.eclipse.wst.common.project.facet.core.IFacetedProjectBase;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.runtime.IRuntime;
import org.eclipse.wst.common.project.facet.core.runtime.IRuntimeComponent;

/**
 * JAXB
 * {@link org.eclipse.jpt.common.core.libval.LibraryValidator} utility methods.
 */
public class JaxbLibraryValidatorTools {

	/**
	 * The JAXB facet version string 2.1.
	 * <p>
	 * Value: {@value}
	 */
	public static final String FACET_VERSION_STRING_2_1 = "2.1"; //$NON-NLS-1$

	/**
	 * The JAXB facet version 2.1.
	 */
	public static final IProjectFacetVersion FACET_VERSION_2_1 = JaxbProject.FACET.getVersion(FACET_VERSION_STRING_2_1);

	/**
	 * The JAXB facet version string 2.2.
	 * <p>
	 * Value: {@value}
	 */
	public static final String FACET_VERSION_STRING_2_2 = "2.2"; //$NON-NLS-1$

	/**
	 * The JAXB facet version 2.2.
	 */
	public static final IProjectFacetVersion FACET_VERSION_2_2 = JaxbProject.FACET.getVersion(FACET_VERSION_STRING_2_2);


	/** 
	 * Return the JAXB facet version corresponding to the specified config's
	 * Java facet version.
	 * Assume highest update of Java (e.g. Java 1.6 maps to JAXB 2.1)
	 */
	public static IProjectFacetVersion findJavaJaxbVersion(JptLibraryProviderInstallOperationConfig config) {
		IProjectFacetVersion javaVersion = LibraryValidatorTools.getJavaFacetVersion(config);
		
		if (javaVersion == JavaFacet.VERSION_1_6) {
			return FACET_VERSION_2_1;
		}
		if (javaVersion == JavaFacet.VERSION_1_7) {
			return FACET_VERSION_2_2;
		}
		if (javaVersion == JavaFacet.VERSION_1_8) {
			return FACET_VERSION_2_2;
		}
		return null;
	}

	/**
	 * Return the JAXB facet version corresponding to the specified config's JRE.
	 */
	public static IProjectFacetVersion findJreJaxbVersion(JptLibraryProviderInstallOperationConfig config) {
		IVMInstall vmInstall = findVMInstall(config.getFacetedProject());
		return (vmInstall == null) ? null : findJreJaxbVersion(vmInstall);
	}
	
	private static IVMInstall findVMInstall(IFacetedProjectBase facetedProject) {
		IVMInstall vm = null;
		IRuntime runtime = facetedProject.getPrimaryRuntime();
		if (runtime != null) {
			for (IRuntimeComponent rc : runtime.getRuntimeComponents()) {
				vm = findVMInstall(rc);
				if (vm != null) {
					return vm;
				}
			}
		}

		IJavaProject javaProject = JavaCore.create(facetedProject.getProject());
		if (javaProject != null) {	
			try {
				vm = JavaRuntime.getVMInstall(javaProject);
			} catch (CoreException ce) {
				// do nothing, just use the default install
			}
		}
		return (vm != null) ? vm : JavaRuntime.getDefaultVMInstall();
	}
	
	private static IVMInstall findVMInstall(IRuntimeComponent rc) {
		String vmInstallTypeId = rc.getProperty(StandardJreRuntimeComponent.PROP_VM_INSTALL_TYPE);
		if (vmInstallTypeId == null) {
			return null;
		}
		String vmInstallId = rc.getProperty(StandardJreRuntimeComponent.PROP_VM_INSTALL_ID);
		if (vmInstallId == null) {
			return null;
		}
		IVMInstallType vmInstallType = JavaRuntime.getVMInstallType(vmInstallTypeId);
		return (vmInstallType == null) ? null : vmInstallType.findVMInstall(vmInstallId);
	}
	
	private static IProjectFacetVersion findJreJaxbVersion(IVMInstall vm) {
		if (vm instanceof IVMInstall2) {
			String javaVersion = ((IVMInstall2) vm).getJavaVersion();
			if (javaVersion != null) {
				// all other versions except 1.7 and 1.6 have no corresponding version (as of yet)
				if (javaVersion.startsWith(JavaCore.VERSION_1_7)) {
					return FACET_VERSION_2_2;
				}
				// 1.6 must be further analyzed
				if (! javaVersion.startsWith(JavaCore.VERSION_1_6)) {
					return null;
				}
			}
		}
		
		boolean foundXmlSeeAlso = false;  // marker for jaxb 2.1 +
		for (IPath vmLibPath : buildLibraryPaths(vm)) {
			File file = vmLibPath.toFile();
			if (file.exists()) {
				ZipFile zipFile = null;
				try {
					zipFile = new ZipFile(file);
					if (zipFile.getEntry(JAXB_PERMISSION) != null) {
						// short circuit for JAXB 2.2
						return FACET_VERSION_2_2;
					}
					foundXmlSeeAlso |= (zipFile.getEntry(XML_SEE_ALSO) != null);
				} catch (IOException ex) {
					// ignore
				} finally {
					if (zipFile != null) {
						try {
							zipFile.close();
						} catch (IOException ex) {
							// ignore
						}
					}
				}
			}
		}
		return foundXmlSeeAlso ? FACET_VERSION_2_1 : null;
	}

	private static Iterable<IPath> buildLibraryPaths(IVMInstall vm) {
			return IterableTools.transform(IterableTools.iterable(JavaRuntime.getLibraryLocations(vm)), LIBRARY_LOCATION_TRANSFORMER);
	}

	private static final Transformer<LibraryLocation, IPath> LIBRARY_LOCATION_TRANSFORMER = new LibraryLocationTransformer();
	/* CU private */ static class LibraryLocationTransformer
		extends TransformerAdapter<LibraryLocation, IPath>
	{
		@Override
		public IPath transform(LibraryLocation libraryLocation) {
			return libraryLocation.getSystemLibraryPath();
		}
	}

	private static final String XML_SEE_ALSO = "javax/xml/bind/annotation/XmlSeeAlso.class"; //$NON-NLS-1$
	private static final String JAXB_PERMISSION = "javax/xml/bind/JAXBPermission.class"; //$NON-NLS-1$
}
