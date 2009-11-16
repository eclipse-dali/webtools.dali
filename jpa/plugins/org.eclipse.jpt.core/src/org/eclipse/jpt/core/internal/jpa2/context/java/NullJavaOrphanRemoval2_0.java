/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.jpa2.context.java.JavaOrphanRemovable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOrphanRemovalHolder2_0;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;

/**
 *  NullJavaOrphanRemoval2_0
 */
public class NullJavaOrphanRemoval2_0
			extends AbstractJavaJpaContextNode
			implements JavaOrphanRemovable2_0
{
	// ********** constructor **********
	public NullJavaOrphanRemoval2_0(JavaOrphanRemovalHolder2_0 parent) {
		super(parent);
	}

	// ********** OrphanRemovable2_0 implementation **********
	
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

	// ********** JavaOrphanRemovable2_0 implementation **********
	
	public void initialize(JavaResourcePersistentAttribute jrpa) {
		// do nothing
	}
	
	public void update(JavaResourcePersistentAttribute jrpa) {
		// do nothing
	}

	// ********** validation **********
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return null;
	}
}
