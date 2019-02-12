/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceTypeCompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaResourceModelProvider;

/**
 * Java source code
 */
public class JavaResourceModelProvider
	implements JpaResourceModelProvider
{
	// singleton
	private static final JpaResourceModelProvider INSTANCE = new JavaResourceModelProvider();

	/**
	 * Return the singleton.
	 */
	public static JpaResourceModelProvider instance() {
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

	public JavaResourceCompilationUnit buildResourceModel(JpaProject jpaProject, IFile file) {
		return new SourceTypeCompilationUnit(
					JavaCore.createCompilationUnitFrom(file),
					jpaProject.getJpaPlatform().getAnnotationProvider(),
					jpaProject.getJpaPlatform().getAnnotationEditFormatter(),
					jpaProject.getManager().getModifySharedDocumentCommandContext()
				);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
