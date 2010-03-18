/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.core.internal.utility.jdt.MemberAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.core.jpa2.resource.java.ElementCollection2_0Annotation;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.resource.java.FetchType;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.ExpressionConverter;

/**
 * org.eclipse.persistence.annotations.Transformation
 */
public final class SourceElementCollection2_0Annotation
	extends SourceAnnotation<Attribute>
	implements ElementCollection2_0Annotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> TARGET_CLASS_ADAPTER = buildTargetClassAdapter();
	private final AnnotationElementAdapter<String> targetClassAdapter;
	private String targetClass;

	String fullyQualifiedTargetClassName;

	private static final DeclarationAnnotationElementAdapter<String> FETCH_ADAPTER = buildFetchAdapter();
	private final AnnotationElementAdapter<String> fetchAdapter;
	private FetchType fetch;


	public SourceElementCollection2_0Annotation(JavaResourcePersistentAttribute parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
		this.targetClassAdapter = new MemberAnnotationElementAdapter<String>(attribute, TARGET_CLASS_ADAPTER);
		this.fetchAdapter = new MemberAnnotationElementAdapter<String>(attribute, FETCH_ADAPTER);
	}


	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public void initialize(CompilationUnit astRoot) {
		this.targetClass = this.buildTargetClass(astRoot);
		this.fullyQualifiedTargetClassName = this.buildFullyQualifiedTargetClassName(astRoot);
		this.fetch = this.buildFetch(astRoot);
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncTargetClass(this.buildTargetClass(astRoot));
		this.syncFullyQualifiedTargetClassName(this.buildFullyQualifiedTargetClassName(astRoot));
		this.syncFetch(this.buildFetch(astRoot));
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.targetClass);
	}


	// ********** ElementCollection2_0Annotation implementation **********
	
	// ***** target class
	public String getTargetClass() {
		return this.targetClass;
	}

	public void setTargetClass(String targetClass) {
		if (this.attributeValueHasChanged(this.targetClass, targetClass)) {
			this.targetClass = targetClass;
			this.targetClassAdapter.setValue(targetClass);
		}
	}

	private void syncTargetClass(String astTargetClass) {
		String old = this.targetClass;
		this.targetClass = astTargetClass;
		this.firePropertyChanged(TARGET_CLASS_PROPERTY, old, astTargetClass);
	}

	private String buildTargetClass(CompilationUnit astRoot) {
		return this.targetClassAdapter.getValue(astRoot);
	}

	public TextRange getTargetClassTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(TARGET_CLASS_ADAPTER, astRoot);
	}

	// ***** fully-qualified target entity class name
	public String getFullyQualifiedTargetClassName() {
		return this.fullyQualifiedTargetClassName;
	}

	private void syncFullyQualifiedTargetClassName(String name) {
		String old = this.fullyQualifiedTargetClassName;
		this.fullyQualifiedTargetClassName = name;
		this.firePropertyChanged(FULLY_QUALIFIED_TARGET_CLASS_NAME_PROPERTY, old, name);
	}

	private String buildFullyQualifiedTargetClassName(CompilationUnit astRoot) {
		return (this.targetClass == null) ? null : ASTTools.resolveFullyQualifiedName(this.targetClassAdapter.getExpression(astRoot));
	}

	// ***** fetch
	public FetchType getFetch() {
		return this.fetch;
	}

	public void setFetch(FetchType fetch) {
		if (this.attributeValueHasChanged(this.fetch, fetch)) {
			this.fetch = fetch;
			this.fetchAdapter.setValue(FetchType.toJavaAnnotationValue(fetch));
		}
	}

	private void syncFetch(FetchType astFetch) {
		FetchType old = this.fetch;
		this.fetch = astFetch;
		this.firePropertyChanged(FETCH_PROPERTY, old, astFetch);
	}

	private FetchType buildFetch(CompilationUnit astRoot) {
		return FetchType.fromJavaAnnotationValue(this.fetchAdapter.getValue(astRoot));
	}

	public TextRange getFetchTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(FETCH_ADAPTER, astRoot);
	}

	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildFetchAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA2_0.ELEMENT_COLLECTION__FETCH, false);
	}
	
	private static DeclarationAnnotationElementAdapter<String> buildTargetClassAdapter() {
		return buildTargetClassAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA2_0.ELEMENT_COLLECTION__TARGET_CLASS);
	}
	
	private static DeclarationAnnotationElementAdapter<String> buildTargetClassAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		// TODO what about QualifiedType?
		return buildAnnotationElementAdapter(annotationAdapter, elementName, SimpleTypeStringExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, ExpressionConverter<String> converter) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(annotationAdapter, elementName, false, converter);
	}

}
