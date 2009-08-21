/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.InheritanceAnnotation;
import org.eclipse.jpt.core.resource.java.InheritanceType;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * javax.persistence.Inheritance
 */
public final class NullInheritanceAnnotation
	extends NullAnnotation
	implements InheritanceAnnotation
{
	protected NullInheritanceAnnotation(JavaResourcePersistentType parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	protected InheritanceAnnotation addAnnotation() {
		return (InheritanceAnnotation) super.addAnnotation();
	}

	// ***** strategy
	public InheritanceType getStrategy() {
		return null;
	}

	public void setStrategy(InheritanceType strategy) {
		if (strategy != null) {
			this.addAnnotation().setStrategy(strategy);
		}
	}

	public TextRange getStrategyTextRange(CompilationUnit astRoot) {
		return null;
	}

}
