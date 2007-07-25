/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java;

import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.IJpaFileContentProvider;
import org.eclipse.jpt.core.internal.IJpaRootContentNode;
import org.eclipse.jpt.core.internal.JptCorePlugin;

public class JavaJpaFileContentProvider implements IJpaFileContentProvider
{
	//singleton
	private static final JavaJpaFileContentProvider INSTANCE = new JavaJpaFileContentProvider();
	
	/**
	 * Return the singleton.
	 */
	public static IJpaFileContentProvider instance() {
		return INSTANCE;
	}

	/**
	 * Restrict access
	 */
	private JavaJpaFileContentProvider() {
		super();
	}
	
	public IJpaRootContentNode buildRootContent(IJpaFile jpaFile) {
		JpaCompilationUnit content = JpaJavaFactory.eINSTANCE.createJpaCompilationUnit();
		jpaFile.setContent(content);
		content.setFile(jpaFile.getFile());
		return content;
	}
	
	public String contentType() {
		return JptCorePlugin.JAVA_CONTENT_TYPE;
	}
}
