/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java;

import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.core.internal.IJpaFileContentProvider;
import org.eclipse.jpt.core.internal.IJpaRootContentNode;
import org.eclipse.jpt.core.internal.JptCorePlugin;

public class JavaJpaFileContentProvider implements IJpaFileContentProvider
{
	public static JavaJpaFileContentProvider INSTANCE = new JavaJpaFileContentProvider();
	
	/**
	 * Restrict access
	 */
	private JavaJpaFileContentProvider() {
		
	}
	
	public IJpaRootContentNode buildRootContent(IFile resourceFile) {
		JpaCompilationUnit content = JpaJavaFactory.eINSTANCE.createJpaCompilationUnit();
		content.setFile(resourceFile);
		return content;
	}
	
	public String contentType() {
		return JptCorePlugin.JAVA_CONTENT_TYPE;
	}
}
