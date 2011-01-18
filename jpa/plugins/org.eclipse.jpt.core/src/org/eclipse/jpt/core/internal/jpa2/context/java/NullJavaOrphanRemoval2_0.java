/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.jpa2.context.java.JavaOrphanRemovable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOrphanRemovalHolder2_0;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * Null Java orphan removal
 */
public class NullJavaOrphanRemoval2_0
	extends AbstractJavaJpaContextNode
	implements JavaOrphanRemovable2_0
{
	public NullJavaOrphanRemoval2_0(JavaOrphanRemovalHolder2_0 parent) {
		super(parent);
	}


	// ********** orphan removal **********

	public boolean isOrphanRemoval() {
		return false;
	}

	public Boolean getSpecifiedOrphanRemoval() {
		return null;
	}

	public boolean isDefaultOrphanRemoval() {
		return false;
	}

	public void setSpecifiedOrphanRemoval(Boolean newSpecifiedOrphanRemoval) {
		throw new UnsupportedOperationException();
	}


	// ********** validation **********

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return null;
	}
}
