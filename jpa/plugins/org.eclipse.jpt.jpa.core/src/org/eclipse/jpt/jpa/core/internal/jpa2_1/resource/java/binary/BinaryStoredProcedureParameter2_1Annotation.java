/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2_1.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.JPA2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.ParameterMode_2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.StoredProcedureParameter2_1Annotation;

/**
 * javax.persistence.StoredProcedureParameter
 */
public final class BinaryStoredProcedureParameter2_1Annotation 
	extends BinaryAnnotation
	implements StoredProcedureParameter2_1Annotation
{
	private String name;
	private ParameterMode_2_1 mode;
	private String type;


	public BinaryStoredProcedureParameter2_1Annotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.name = this.buildName();
		this.mode = this.buildMode();
		this.type = this.buildType();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setName_(this.buildName());
		this.setMode_(this.buildMode());
		this.setType_(this.buildType());
	}
	

	// ********** BinaryStoredProcedureParameterAnnotation implementation **********

	String getNameElementName() {
		return JPA2_1.NAMED_STORED_PROCEDURE_PARAMETER__NAME;
	}

	String getModeElementName() {
		return JPA2_1.NAMED_STORED_PROCEDURE_PARAMETER__MODE;
	}

	String getTypeElementName() {
		return JPA2_1.NAMED_STORED_PROCEDURE_PARAMETER__TYPE;
	}

	// ********** StoredProcedureParameterAnnotation implementation **********

	// ***** name
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		throw new UnsupportedOperationException();
	}

	private void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}

	private String buildName() {
		return (String) this.getJdtMemberValue(this.getNameElementName());
	}

	public TextRange getNameTextRange() {
		throw new UnsupportedOperationException();
	}
	
	// ***** mode
	public ParameterMode_2_1 getMode() {
		return this.mode;
	}

	public void setMode(ParameterMode_2_1 mode) {
		throw new UnsupportedOperationException();
	}

	private void setMode_(ParameterMode_2_1 mode) {
		ParameterMode_2_1 old = this.mode;
		this.mode = mode;
		this.firePropertyChanged(MODE_PROPERTY, old, mode);
	}

	private ParameterMode_2_1 buildMode() {
		return ParameterMode_2_1.fromJavaAnnotationValue(this.getJdtMemberValue(this.getModeElementName()));
	}

	public TextRange getModeTextRange() {
		throw new UnsupportedOperationException();
	}

	// ***** type
	public String getTypeName() {
		return this.type;
	}

	public void setTypeName(String type) {
		throw new UnsupportedOperationException();
	}

	private void setType_(String type) {
		String old = this.type;
		this.type = type;
		this.firePropertyChanged(TYPE_PROPERTY, old, type);
	}

	private String buildType() {
		return (String) this.getJdtMemberValue(this.getTypeElementName());
	}

	public TextRange getTypeTextRange() {
		throw new UnsupportedOperationException();
	}

	// ***** fully-qualified type name
	public String getFullyQualifiedTypeName()  {
		return this.type;
	}

}
