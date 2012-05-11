/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JpaResourceModel;
import org.eclipse.jpt.core.JpaResourceModelProvider;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.resource.java.binary.BinaryPackageFragmentRoot;

/**
 * JAR files
 */
public class JarResourceModelProvider
	implements JpaResourceModelProvider
{
	// singleton
	private static final JpaResourceModelProvider INSTANCE = new JarResourceModelProvider();

	/**
	 * Return the singleton.
	 */
	public static JpaResourceModelProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private JarResourceModelProvider() {
		super();
	}

	public IContentType getContentType() {
		return JptCorePlugin.JAR_CONTENT_TYPE;
	}

	/**
	 * NB: Despite the check in GenericJpaProject.addJpaFile_(IFile),
	 * we can get here and
	 * the PFR will be null if the JAR is underneath a directory on the
	 * classpath but the JAR itself is not on the classpath.
	 * Returning null should be OK.
	 */
	public JpaResourceModel buildResourceModel(JpaProject jpaProject, IFile file) {
		IPackageFragmentRoot pfr = JavaCore.createJarPackageFragmentRootFrom(file);
		return (pfr ==null) ? null : new BinaryPackageFragmentRoot(pfr, jpaProject.getJpaPlatform().getAnnotationProvider());
	}

}
