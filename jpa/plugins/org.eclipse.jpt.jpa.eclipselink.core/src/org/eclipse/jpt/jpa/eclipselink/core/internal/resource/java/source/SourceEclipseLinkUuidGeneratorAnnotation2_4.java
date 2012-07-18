/*******************************************************************************
 *  Copyright (c) 2012  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkUuidGeneratorAnnotation2_4;

/**
 * org.eclipse.persistence.annotations.UuidGenerator
 */
public class SourceEclipseLinkUuidGeneratorAnnotation2_4
	extends SourceAnnotation
	implements EclipseLinkUuidGeneratorAnnotation2_4
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER 
			= new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = buildAdapter(JPA.SEQUENCE_GENERATOR__NAME);
	final AnnotationElementAdapter<String> nameAdapter;
	String name;
	TextRange nameTextRange;

	public SourceEclipseLinkUuidGeneratorAnnotation2_4(JavaResourceAnnotatedElement parent, AnnotatedElement element) {
		super(parent, element, DECLARATION_ANNOTATION_ADAPTER);
		this.nameAdapter = this.buildAdapter(NAME_ADAPTER);
	}

	protected AnnotationElementAdapter<String> buildAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, daea);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.name = this.buildName(astAnnotation);
		this.nameTextRange = this.buildNameTextRange(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncName(this.buildName(astAnnotation));
		this.nameTextRange = this.buildNameTextRange(astAnnotation);
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.name == null);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}

	// ***** name
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (this.attributeValueHasChanged(this.name, name)) {
			this.name = name;
			this.nameAdapter.setValue(name);
		}
	}

	private void syncName(String astName) {
		String old = this.name;
		this.name = astName;
		this.firePropertyChanged(NAME_PROPERTY, old, astName);
	}

	private String buildName(Annotation astAnnotation) {
		return this.nameAdapter.getValue(astAnnotation);
	}

	public TextRange getNameTextRange() {
		return this.nameTextRange;
	}

	private TextRange buildNameTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(NAME_ADAPTER, astAnnotation);
	}


	// ********** static methods **********

	protected static DeclarationAnnotationElementAdapter<String> buildAdapter(String elementName) {
		return buildAdapter(DECLARATION_ANNOTATION_ADAPTER, elementName);
	}

	static DeclarationAnnotationElementAdapter<String> buildAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(annotationAdapter, elementName);
	}


	public Integer getInitialValue() {
		return null;
	}
	public void setInitialValue(Integer initialValue) {
		throw new UnsupportedOperationException();
	}
	public TextRange getInitialValueTextRange() {
		throw new UnsupportedOperationException();
	}
	public Integer getAllocationSize() {
		return null;
	}
	public void setAllocationSize(Integer allocationSize) {
		throw new UnsupportedOperationException();
	}
	public TextRange getAllocationSizeTextRange() {
		throw new UnsupportedOperationException();
	}
}
