/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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

public class JavaSourceFileDefinition
	implements JpaResourceDefinition
{
	// singleton
	private static final JpaResourceDefinition INSTANCE = new JavaSourceFileDefinition();

	/**
	 * Return the singleton
	 */
	public static JpaResourceDefinition instance() {
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
		return ContentTypeTools.getResourceType(contentType);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
