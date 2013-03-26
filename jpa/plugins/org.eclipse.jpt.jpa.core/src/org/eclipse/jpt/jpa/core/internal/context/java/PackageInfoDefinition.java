/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.internal.utility.ContentTypeTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.jpa.core.JpaResourceDefinition;

/**
 * <code>package-info</code> Java source code files.
 */
public class PackageInfoDefinition
	implements JpaResourceDefinition
{
	// singleton
	private static final JpaResourceDefinition INSTANCE = new PackageInfoDefinition();

	/**
	 * Return the singleton
	 */
	public static JpaResourceDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private PackageInfoDefinition() {
		super();
	}

	public JptResourceType getResourceType() {
		return this.getResourceType(JavaResourceCompilationUnit.PACKAGE_INFO_CONTENT_TYPE);
	}


	// ********** misc **********

	protected JptResourceType getResourceType(IContentType contentType) {
		return ContentTypeTools.getResourceType(contentType);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
