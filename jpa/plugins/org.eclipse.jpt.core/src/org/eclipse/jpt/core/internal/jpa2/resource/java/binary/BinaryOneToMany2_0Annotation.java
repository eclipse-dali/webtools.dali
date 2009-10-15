/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.binary.BinaryOneToManyAnnotation;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.jpa2.resource.java.OneToMany2_0Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;

/**
 *  BinaryOneToMany2_0Annotation
 */
public final class BinaryOneToMany2_0Annotation
	extends BinaryOneToManyAnnotation
	implements OneToMany2_0Annotation
{
	private Boolean orphanRemoval;

	// ********** constructor **********
	public BinaryOneToMany2_0Annotation(JavaResourcePersistentAttribute parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.orphanRemoval = this.buildOrphanRemoval();
	}

	@Override
	public void update() {
		super.update();
		this.setOrphanRemoval_(this.buildOrphanRemoval());
	}

	// ********** OneToMany2_0Annotation implementation **********

	public Boolean getOrphanRemoval() {
		return this.orphanRemoval;
	}

	public void setOrphanRemoval(Boolean orphanRemoval) {
		throw new UnsupportedOperationException();
	}

	public TextRange getOrphanRemovalTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	private Boolean buildOrphanRemoval() {
		return (Boolean) this.getJdtMemberValue(JPA2_0.ONE_TO_MANY__ORPHAN_REMOVAL);
	}

	private void setOrphanRemoval_(Boolean orphanRemoval) {
		Boolean old = this.orphanRemoval;
		this.orphanRemoval = orphanRemoval;
		this.firePropertyChanged(ORPHAN_REMOVAL_PROPERTY, old, orphanRemoval);
	}
}