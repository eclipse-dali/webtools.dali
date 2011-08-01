/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.NamedNativeQueryAnnotation;

/**
 * javax.persistence.NamedNativeQuery
 */
public final class BinaryNamedNativeQueryAnnotation
	extends BinaryQueryAnnotation
	implements NamedNativeQueryAnnotation
{
	private String resultClass;
	private String resultSetMapping;


	public BinaryNamedNativeQueryAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.resultClass = this.buildResultClass();
		this.resultSetMapping = this.buildResultSetMapping();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setResultClass_(this.buildResultClass());
		this.setResultSetMapping_(this.buildResultSetMapping());
	}


	// ********** BinaryBaseNamedQueryAnnotation implementation **********

	@Override
	String getNameElementName() {
		return JPA.NAMED_NATIVE_QUERY__NAME;
	}

	@Override
	String getQueryElementName() {
		return JPA.NAMED_NATIVE_QUERY__QUERY;
	}

	@Override
	String getHintsElementName() {
		return JPA.NAMED_NATIVE_QUERY__HINTS;
	}


	// ********** NamedNativeQueryAnnotation implementation **********

	// ***** result class
	public String getResultClass() {
		return this.resultClass;
	}

	public void setResultClass(String resultClass) {
		throw new UnsupportedOperationException();
	}

	private void setResultClass_(String resultClass) {
		String old = this.resultClass;
		this.resultClass = resultClass;
		this.firePropertyChanged(RESULT_CLASS_PROPERTY, old, resultClass);
	}

	private String buildResultClass() {
		return (String) this.getJdtMemberValue(JPA.NAMED_NATIVE_QUERY__RESULT_CLASS);
	}

	public TextRange getResultClassTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** fully-qualified result class name
	public String getFullyQualifiedResultClassName()  {
		return this.resultClass;
	}

	// ***** result set mapping
	public String getResultSetMapping() {
		return this.resultSetMapping;
	}

	public void setResultSetMapping(String resultSetMapping) {
		throw new UnsupportedOperationException();
	}

	private void setResultSetMapping_(String resultSetMapping) {
		String old = this.resultSetMapping;
		this.resultSetMapping = resultSetMapping;
		this.firePropertyChanged(RESULT_SET_MAPPING_PROPERTY, old, resultSetMapping);
	}

	private String buildResultSetMapping() {
		return (String) this.getJdtMemberValue(JPA.NAMED_NATIVE_QUERY__RESULT_CLASS);
	}

	public TextRange getResultSetMappingTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

}
