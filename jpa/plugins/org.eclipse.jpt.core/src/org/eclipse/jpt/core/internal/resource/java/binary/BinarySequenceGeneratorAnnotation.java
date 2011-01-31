/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.SequenceGeneratorAnnotation;

/**
 * javax.persistence.SequenceGenerator
 */
public abstract class BinarySequenceGeneratorAnnotation
	extends BinaryGeneratorAnnotation
	implements SequenceGeneratorAnnotation
{
	private String sequenceName;


	protected BinarySequenceGeneratorAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.sequenceName = this.buildSequenceName();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setSequenceName_(this.buildSequenceName());
	}


	// ********** BinaryGeneratorAnnotation implementation **********

	@Override
	String getNameElementName() {
		return JPA.SEQUENCE_GENERATOR__NAME;
	}

	@Override
	String getInitialValueElementName() {
		return JPA.SEQUENCE_GENERATOR__INITIAL_VALUE;
	}

	@Override
	String getAllocationSizeElementName() {
		return JPA.SEQUENCE_GENERATOR__ALLOCATION_SIZE;
	}


	// ********** SequenceGeneratorAnnotation implementation **********

	// ***** sequence name
	public String getSequenceName() {
		return this.sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		throw new UnsupportedOperationException();
	}

	private void setSequenceName_(String sequenceName) {
		String old = this.sequenceName;
		this.sequenceName = sequenceName;
		this.firePropertyChanged(SEQUENCE_NAME_PROPERTY, old, sequenceName);
	}

	private String buildSequenceName() {
		return (String) this.getJdtMemberValue(JPA.SEQUENCE_GENERATOR__SEQUENCE_NAME);
	}

	public TextRange getSequenceNameTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public boolean sequenceNameTouches(int pos, CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

}