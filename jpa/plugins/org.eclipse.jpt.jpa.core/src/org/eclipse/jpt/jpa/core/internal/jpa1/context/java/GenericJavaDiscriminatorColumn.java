/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License 2.0, which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.resource.java.DiscriminatorColumnAnnotation;

/**
 * Java discriminator column
 */
public class GenericJavaDiscriminatorColumn
	extends AbstractJavaNamedDiscriminatorColumn<JavaSpecifiedDiscriminatorColumn.ParentAdapter, DiscriminatorColumnAnnotation>
	implements JavaSpecifiedDiscriminatorColumn
{

	public GenericJavaDiscriminatorColumn(JavaSpecifiedDiscriminatorColumn.ParentAdapter parentAdapter) {
		super(parentAdapter);
	}


	// ********** column annotation **********

	@Override
	public DiscriminatorColumnAnnotation getColumnAnnotation() {
		return this.parentAdapter.getColumnAnnotation();
	}

	@Override
	protected void removeColumnAnnotation() {
		this.parentAdapter.removeColumnAnnotation();
	}
}
