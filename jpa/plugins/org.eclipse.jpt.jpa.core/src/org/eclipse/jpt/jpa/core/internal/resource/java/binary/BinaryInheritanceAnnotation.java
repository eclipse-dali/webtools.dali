/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.java.InheritanceAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.InheritanceType;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * <code>javax.persistence.Inheritance</code>
 */
public final class BinaryInheritanceAnnotation
	extends BinaryAnnotation
	implements InheritanceAnnotation
{
	private InheritanceType strategy;


	public BinaryInheritanceAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.strategy = this.buildStrategy();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setStrategy_(this.buildStrategy());
	}


	// ********** InheritanceAnnotation implementation **********

	// ***** strategy
	public InheritanceType getStrategy() {
		return this.strategy;
	}

	public void setStrategy(InheritanceType strategy) {
		throw new UnsupportedOperationException();
	}

	private void setStrategy_(InheritanceType strategy) {
		InheritanceType old = this.strategy;
		this.strategy = strategy;
		this.firePropertyChanged(STRATEGY_PROPERTY, old, strategy);
	}

	private InheritanceType buildStrategy() {
		return InheritanceType.fromJavaAnnotationValue(this.getJdtMemberValue(JPA.INHERITANCE__STRATEGY));
	}

	public TextRange getStrategyTextRange() {
		throw new UnsupportedOperationException();
	}
}
