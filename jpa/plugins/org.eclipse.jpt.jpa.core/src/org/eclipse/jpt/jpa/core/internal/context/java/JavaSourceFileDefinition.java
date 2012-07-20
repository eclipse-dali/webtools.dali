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
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.jpa.core.ResourceDefinition;

public class JavaSourceFileDefinition
	implements ResourceDefinition
{
	// singleton
	private static final ResourceDefinition INSTANCE = new JavaSourceFileDefinition();

	/**
	 * Return the singleton
	 */
	public static ResourceDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private JavaSourceFileDefinition() {
		super();
	}

	public JptResourceType getResourceType() {
		return this.getResourceType(JavaResourceCompilationUnit.CONTENT_TYPE);
	}


	// ********** misc **********

	protected JptResourceType getResourceType(IContentType contentType) {
		return PlatformTools.getResourceType(contentType);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
