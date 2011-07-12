/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.GenericPersistentAttributeValidator;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentAttribute;

/**
 * Generic Java persistent attribute
 */
public class GenericJavaPersistentAttribute
	extends AbstractJavaPersistentAttribute
{

	public GenericJavaPersistentAttribute(PersistentType parent, JavaResourcePersistentAttribute jrpa) {
		super(parent, jrpa);
	}

	// ********** validation **********

	@Override
	protected JptValidator buildAttibuteValidator(CompilationUnit astRoot) {
		return new GenericPersistentAttributeValidator(this, this, this.buildTextRangeResolver(astRoot));
	}
}
