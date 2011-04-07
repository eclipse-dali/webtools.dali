/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.Attribute;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceMember;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAttributeAnnotation;

/**
 * javax.xml.bind.annotation.XmlAttribute
 */
public final class SourceXmlAttributeAnnotation
	extends SourceAnnotation<Attribute>
	implements XmlAttributeAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;
	private final AnnotationElementAdapter<String> nameAdapter;
	private String name;

	private final DeclarationAnnotationElementAdapter<String> namespaceDeclarationAdapter;
	private final AnnotationElementAdapter<String> namespaceAdapter;
	private String namespace;

	private final DeclarationAnnotationElementAdapter<Boolean> requiredDeclarationAdapter;
	private final AnnotationElementAdapter<Boolean> requiredAdapter;
	private Boolean required;


	// ********** constructors **********
	public SourceXmlAttributeAnnotation(JavaResourceMember parent, Attribute attribute) {
		this(parent, attribute, DECLARATION_ANNOTATION_ADAPTER, new ElementAnnotationAdapter(attribute, DECLARATION_ANNOTATION_ADAPTER));
	}

	public SourceXmlAttributeAnnotation(JavaResourceMember parent, Attribute attribute, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, attribute, daa, annotationAdapter);
		this.nameDeclarationAdapter = this.buildNameAdapter(daa);
		this.nameAdapter = this.buildAnnotationElementAdapter(this.nameDeclarationAdapter);
		this.namespaceDeclarationAdapter = this.buildNamespaceAdapter(daa);
		this.namespaceAdapter = this.buildAnnotationElementAdapter(this.namespaceDeclarationAdapter);
		this.requiredDeclarationAdapter = this.buildRequiredAdapter(daa);
		this.requiredAdapter = this.buildShortCircuitBooleanElementAdapter(this.requiredDeclarationAdapter);
	}

	private DeclarationAnnotationElementAdapter<String> buildNameAdapter(DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daa, JAXB.XML_ATTRIBUTE__NAME);
	}

	private DeclarationAnnotationElementAdapter<String> buildNamespaceAdapter(DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daa, JAXB.XML_ATTRIBUTE__NAMESPACE);
	}

	private DeclarationAnnotationElementAdapter<Boolean> buildRequiredAdapter(DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forBooleans(daa, JAXB.XML_ATTRIBUTE__REQUIRED);
	}

	private AnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, daea);
	}

	private AnnotationElementAdapter<Boolean> buildShortCircuitBooleanElementAdapter(DeclarationAnnotationElementAdapter<Boolean> daea) {
		return new AnnotatedElementAnnotationElementAdapter<Boolean>(this.annotatedElement, daea);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public void initialize(CompilationUnit astRoot) {
		this.name = this.buildName(astRoot);
		this.namespace = this.buildNamespace(astRoot);
		this.required = this.buildRequired(astRoot);
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncName(this.buildName(astRoot));
		this.syncNamespace(this.buildNamespace(astRoot));
		this.syncRequired(this.buildRequired(astRoot));
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** XmlAttributeAnnotation implementation **********

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

	private String buildName(CompilationUnit astRoot) {
		return this.nameAdapter.getValue(astRoot);
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.nameDeclarationAdapter, astRoot);
	}
	
	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return elementTouches(this.nameDeclarationAdapter, pos, astRoot);
	}
	
	
	// ***** namespace
	public String getNamespace() {
		return this.namespace;
	}

	public void setNamespace(String namespace) {
		if (this.attributeValueHasChanged(this.namespace, namespace)) {
			this.namespace = namespace;
			this.namespaceAdapter.setValue(namespace);
		}
	}

	private void syncNamespace(String astNamespace) {
		String old = this.namespace;
		this.namespace = astNamespace;
		this.firePropertyChanged(NAMESPACE_PROPERTY, old, astNamespace);
	}

	private String buildNamespace(CompilationUnit astRoot) {
		return this.namespaceAdapter.getValue(astRoot);
	}

	public TextRange getNamespaceTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.namespaceDeclarationAdapter, astRoot);
	}
	
	public boolean namespaceTouches(int pos, CompilationUnit astRoot) {
		return elementTouches(this.namespaceDeclarationAdapter, pos, astRoot);
	}
	
	
	// ***** required
	public Boolean getRequired() {
		return this.required;
	}

	public void setRequired(Boolean required) {
		if (this.attributeValueHasChanged(this.required, required)) {
			this.required = required;
			this.requiredAdapter.setValue(required);
		}
	}

	private void syncRequired(Boolean astRequired) {
		Boolean old = this.required;
		this.required = astRequired;
		this.firePropertyChanged(REQUIRED_PROPERTY, old, astRequired);
	}

	private Boolean buildRequired(CompilationUnit astRoot) {
		return this.requiredAdapter.getValue(astRoot);
	}

	public TextRange getRequiredTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.requiredDeclarationAdapter, astRoot);
	}
}
