/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JpaResourceModelProvider;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.resource.java.source.SourcePackageInfoCompilationUnit;
import org.eclipse.jpt.core.resource.java.JavaResourceCompilationUnit;

/**
 * Java package-info.java source code
 */
public class JavaPackageInfoResourceModelProvider
	implements JpaResourceModelProvider
{
	// singleton
	private static final JpaResourceModelProvider INSTANCE = new JavaPackageInfoResourceModelProvider();

	/**
	 * Return the singleton.
	 */
	public static JpaResourceModelProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private JavaPackageInfoResourceModelProvider() {
		super();
	}

	public IContentType getContentType() {
		return JptCorePlugin.JAVA_SOURCE_PACKAGE_INFO_CONTENT_TYPE;
	}

	public JavaResourceCompilationUnit buildResourceModel(JpaProject jpaProject, IFile file) {
		return new SourcePackageInfoCompilationUnit(
			JavaCore.createCompilationUnitFrom(file),
			jpaProject.getJpaPlatform().getAnnotationProvider(),
			jpaProject.getJpaPlatform().getAnnotationEditFormatter(),
			jpaProject.getModifySharedDocumentCommandExecutor()
		);
	}

}
