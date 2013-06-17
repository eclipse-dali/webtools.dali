/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementWrapperAnnotation;

/**
 * javax.xml.bind.annotation.XmlElementWrapper
 */
public final class SourceXmlElementWrapperAnnotation
		extends SourceAnnotation
		implements XmlElementWrapperAnnotation {
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JAXB.XML_ELEMENT_WRAPPER);

	private final DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;
	private final AnnotationElementAdapter<String> nameAdapter;
	private String name;
	private TextRange nameTextRange;
	private TextRange nameValidationTextRange;
	
	private final DeclarationAnnotationElementAdapter<String> namespaceDeclarationAdapter;
	private final AnnotationElementAdapter<String> namespaceAdapter;
	private String namespace;
	private TextRange namespaceTextRange;
	private TextRange namespaceValidationTextRange;
	
	private final DeclarationAnnotationElementAdapter<Boolean> nillableDeclarationAdapter;
	private final AnnotationElementAdapter<Boolean> nillableAdapter;
	private Boolean nillable;
	private TextRange nillableTextRange;
	
	private final DeclarationAnnotationElementAdapter<Boolean> requiredDeclarationAdapter;
	private final AnnotationElementAdapter<Boolean> requiredAdapter;
	private Boolean required;
	private TextRange requiredTextRange;
	
	
	// ********** constructors **********
	public SourceXmlElementWrapperAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		this(parent, annotatedElement, DECLARATION_ANNOTATION_ADAPTER, new ElementAnnotationAdapter(annotatedElement, DECLARATION_ANNOTATION_ADAPTER));
	}

	public SourceXmlElementWrapperAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, annotatedElement, daa, annotationAdapter);
		this.nameDeclarationAdapter = this.buildNameAdapter(daa);
		this.nameAdapter = this.buildAnnotationElementAdapter(this.nameDeclarationAdapter);
		this.namespaceDeclarationAdapter = this.buildNamespaceAdapter(daa);
		this.namespaceAdapter = this.buildAnnotationElementAdapter(this.namespaceDeclarationAdapter);
		this.nillableDeclarationAdapter = this.buildNillableAdapter(daa);
		this.nillableAdapter = this.buildShortCircuitBooleanElementAdapter(this.nillableDeclarationAdapter);
		this.requiredDeclarationAdapter = this.buildRequiredAdapter(daa);
		this.requiredAdapter = this.buildShortCircuitBooleanElementAdapter(this.requiredDeclarationAdapter);
	}

	private DeclarationAnnotationElementAdapter<String> buildNameAdapter(DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daa, JAXB.XML_ELEMENT_WRAPPER__NAME);
	}

	private DeclarationAnnotationElementAdapter<String> buildNamespaceAdapter(DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daa, JAXB.XML_ELEMENT_WRAPPER__NAMESPACE);
	}

	private DeclarationAnnotationElementAdapter<Boolean> buildNillableAdapter(DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forBooleans(daa, JAXB.XML_ELEMENT_WRAPPER__NILLABLE);
	}

	private DeclarationAnnotationElementAdapter<Boolean> buildRequiredAdapter(DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forBooleans(daa, JAXB.XML_ELEMENT_WRAPPER__REQUIRED);
	}

	private AnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, daea);
	}

	private AnnotationElementAdapter<Boolean> buildShortCircuitBooleanElementAdapter(DeclarationAnnotationElementAdapter<Boolean> daea) {
		return new AnnotatedElementAnnotationElementAdapter<Boolean>(this.annotatedElement, daea);
	}

	public String getAnnotationName() {
		return JAXB.XML_ELEMENT_WRAPPER;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.name = buildName(astAnnotation);
		this.nameTextRange = buildNameTextRange(astAnnotation);
		this.nameValidationTextRange = buildNameValidationTextRange(astAnnotation);
		this.namespace = buildNamespace(astAnnotation);
		this.namespaceTextRange = buildNamespaceTextRange(astAnnotation);
		this.namespaceValidationTextRange = buildNamespaceValidationTextRange(astAnnotation);
		this.nillable = buildNillable(astAnnotation);
		this.nillableTextRange = buildNillableTextRange(astAnnotation);
		this.required = buildRequired(astAnnotation);
		this.requiredTextRange = buildRequiredTextRange(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		syncName(buildName(astAnnotation));
		this.nameTextRange = buildNameTextRange(astAnnotation);
		this.nameValidationTextRange = buildNameValidationTextRange(astAnnotation);
		syncNamespace(buildNamespace(astAnnotation));
		this.namespaceTextRange = buildNamespaceTextRange(astAnnotation);
		this.namespaceValidationTextRange = buildNamespaceValidationTextRange(astAnnotation);
		syncNillable(buildNillable(astAnnotation));
		this.nillableTextRange = buildNillableTextRange(astAnnotation);
		syncRequired(buildRequired(astAnnotation));
		this.requiredTextRange = buildRequiredTextRange(astAnnotation);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** XmlElementWrapperAnnotation implementation **********

	// ***** name
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (ObjectTools.notEquals(this.name, name)) {
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
	
	private TextRange buildNameTextRange(Annotation astAnnotation) {
		return getAnnotationElementTextRange(this.nameDeclarationAdapter, astAnnotation);
	}
	
	private TextRange buildNameValidationTextRange(Annotation astAnnotation) {
		return getElementTextRange(this.nameDeclarationAdapter, astAnnotation);
	}
	
	public TextRange getNameTextRange() {
		return this.nameTextRange;
	}
	
	public TextRange getNameValidationTextRange() {
		return this.nameValidationTextRange;
	}
	
	public boolean nameTouches(int pos) {
		return this.textRangeTouches(this.nameTextRange, pos);
	}
		
	
	// ***** namespace
	public String getNamespace() {
		return this.namespace;
	}

	public void setNamespace(String namespace) {
		if (ObjectTools.notEquals(this.namespace, namespace)) {
			this.namespace = namespace;
			this.namespaceAdapter.setValue(namespace);
		}
	}

	private void syncNamespace(String astNamespace) {
		String old = this.namespace;
		this.namespace = astNamespace;
		this.firePropertyChanged(NAMESPACE_PROPERTY, old, astNamespace);
	}

	private String buildNamespace(Annotation astAnnotation) {
		return this.namespaceAdapter.getValue(astAnnotation);
	}
	
	private TextRange buildNamespaceTextRange(Annotation astAnnotation) {
		return getAnnotationElementTextRange(this.namespaceDeclarationAdapter, astAnnotation);
	}
	
	private TextRange buildNamespaceValidationTextRange(Annotation astAnnotation) {
		return getElementTextRange(this.namespaceDeclarationAdapter, astAnnotation);
	}
	
	public TextRange getNamespaceTextRange() {
		return this.namespaceTextRange;
	}
	
	public TextRange getNamespaceValidationTextRange() {
		return this.namespaceValidationTextRange;
	}
	
	public boolean namespaceTouches(int pos) {
		return this.textRangeTouches(this.namespaceTextRange, pos);
	}
	
	
	// ***** nillable
	public Boolean getNillable() {
		return this.nillable;
	}

	public void setNillable(Boolean nillable) {
		if (ObjectTools.notEquals(this.nillable, nillable)) {
			this.nillable = nillable;
			this.nillableAdapter.setValue(nillable);
		}
	}

	private void syncNillable(Boolean astNillable) {
		Boolean old = this.nillable;
		this.nillable = astNillable;
		this.firePropertyChanged(NILLABLE_PROPERTY, old, astNillable);
	}

	private Boolean buildNillable(Annotation astAnnotation) {
		return this.nillableAdapter.getValue(astAnnotation);
	}


	private TextRange buildNillableTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.nillableDeclarationAdapter, astAnnotation);
	}

	public TextRange getNillableTextRange() {
		return this.nillableTextRange;
	}

	// ***** required
	public Boolean getRequired() {
		return this.required;
	}

	public void setRequired(Boolean required) {
		if (ObjectTools.notEquals(this.required, required)) {
			this.required = required;
			this.requiredAdapter.setValue(required);
		}
	}

	private void syncRequired(Boolean astRequired) {
		Boolean old = this.required;
		this.required = astRequired;
		this.firePropertyChanged(REQUIRED_PROPERTY, old, astRequired);
	}

	private Boolean buildRequired(Annotation astAnnotation) {
		return this.requiredAdapter.getValue(astAnnotation);
	}

	private TextRange buildRequiredTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.requiredDeclarationAdapter, astAnnotation);
	}

	public TextRange getRequiredTextRange() {
		return this.requiredTextRange;
	}
}
