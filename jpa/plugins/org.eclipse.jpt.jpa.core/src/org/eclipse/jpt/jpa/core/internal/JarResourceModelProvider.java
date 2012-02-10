/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.JptResourceModel;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryPackageFragmentRoot;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaResourceModelProvider;

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
		return JptCommonCorePlugin.JAR_CONTENT_TYPE;
	}

	/**
	 * NB: Despite the check in GenericJpaProject.addJpaFile_(IFile),
	 * we can get here and
	 * the PFR will be null if the JAR is underneath a directory on the
	 * classpath but the JAR itself is not on the classpath.
	 * Returning null should be OK.
	 */
	public JptResourceModel buildResourceModel(JpaProject jpaProject, IFile file) {
		IPackageFragmentRoot pfr = JavaCore.createJarPackageFragmentRootFrom(file);
		return (pfr ==null) ? null : new BinaryPackageFragmentRoot(pfr, jpaProject.getJpaPlatform().getAnnotationProvider());
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
