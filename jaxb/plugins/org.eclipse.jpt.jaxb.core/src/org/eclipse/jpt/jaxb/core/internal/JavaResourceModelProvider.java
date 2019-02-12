/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceTypeCompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JaxbResourceModelProvider;

/**
 * Java source code
 */
public class JavaResourceModelProvider
	implements JaxbResourceModelProvider
{
	// singleton
	private static final JaxbResourceModelProvider INSTANCE = new JavaResourceModelProvider();

	/**
	 * Return the singleton.
	 */
	public static JaxbResourceModelProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private JavaResourceModelProvider() {
		super();
	}

	public IContentType getContentType() {
		return JavaResourceCompilationUnit.CONTENT_TYPE;
	}

	public JavaResourceCompilationUnit buildResourceModel(JaxbProject jaxbProject, IFile file) {
		return new SourceTypeCompilationUnit(
					JavaCore.createCompilationUnitFrom(file),
					jaxbProject.getPlatform().getAnnotationProvider(),
					jaxbProject.getPlatform().getAnnotationEditFormatter(),
					jaxbProject.getModifySharedDocumentCommandContext()
				);
	}

}
