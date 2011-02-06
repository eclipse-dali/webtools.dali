/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkConverterAnnotation;

/**
 * org.eclipse.persistence.annotations.Converter
 */
public final class BinaryEclipseLinkConverterAnnotation
	extends BinaryEclipseLinkNamedConverterAnnotation
	implements EclipseLinkConverterAnnotation
{
	private String converterClass;


	public BinaryEclipseLinkConverterAnnotation(JavaResourcePersistentMember parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.converterClass = this.buildConverterClass();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setConverterClass_(this.buildConverterClass());
	}


	// ********** BinaryNamedConverterAnnotation implementation **********

	@Override
	String getNameElementName() {
		return EclipseLink.CONVERTER__NAME;
	}

	// ********** ConverterAnnotation implementation **********

	// ***** converter class
	public String getConverterClass() {
		return this.converterClass;
	}

	public void setConverterClass(String converterClass) {
		throw new UnsupportedOperationException();
	}

	private void setConverterClass_(String converterClass) {
		String old = this.converterClass;
		this.converterClass = converterClass;
		this.firePropertyChanged(CONVERTER_CLASS_PROPERTY, old, converterClass);
	}

	public String getFullyQualifiedConverterClassName() {
		return this.converterClass;
	}

	private String buildConverterClass() {
		return (String) this.getJdtMemberValue(EclipseLink.CONVERTER__CONVERTER_CLASS);
	}

	public TextRange getConverterClassTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public boolean converterClassImplementsInterface(String interfaceName, CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

}
