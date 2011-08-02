/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourcePackageInfoCompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JaxbResourceModelProvider;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;

/**
 * Java package-info.java source code
 */
public class JavaPackageInfoResourceModelProvider
	implements JaxbResourceModelProvider
{
	// singleton
	private static final JaxbResourceModelProvider INSTANCE = new JavaPackageInfoResourceModelProvider();

	/**
	 * Return the singleton.
	 */
	public static JaxbResourceModelProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private JavaPackageInfoResourceModelProvider() {
		super();
	}

	public IContentType getContentType() {
		return JptCommonCorePlugin.JAVA_SOURCE_PACKAGE_INFO_CONTENT_TYPE;
	}

	public JavaResourceCompilationUnit buildResourceModel(JaxbProject jaxbProject, IFile file) {
		ICompilationUnit compilationUnit = JavaCore.createCompilationUnitFrom(file);
		try {
			if (compilationUnit.getPackageDeclarations().length > 0){
				return new SourcePackageInfoCompilationUnit(
						compilationUnit,
						jaxbProject.getPlatform().getAnnotationProvider(),
						jaxbProject.getPlatform().getAnnotationEditFormatter(),
						jaxbProject.getModifySharedDocumentCommandExecutor());
			} 
			//ignore package-info placed in default package as
			//it doesn't have package declaration and can't hold annotations
			return null;
		} catch (JavaModelException e) {
			JptJaxbCorePlugin.log(e);
			// Ignore -- project is in a bad state. This will get recalled if necessary
			return null;
		}
	}


}
