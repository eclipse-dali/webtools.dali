/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.Attribute;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.ElementCollection2_0Annotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.java.FetchType;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentAttribute;

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

	/**
	 * @see org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceIdClassAnnotation#fullyQualifiedClassName
	 */
	private String fullyQualifiedTargetClassName;
	// we need a flag since the f-q name can be null
	private boolean fqTargetClassNameStale = true;

	private static final DeclarationAnnotationElementAdapter<String> FETCH_ADAPTER = buildFetchAdapter();
	private final AnnotationElementAdapter<String> fetchAdapter;
	private FetchType fetch;


	public SourceElementCollection2_0Annotation(JavaResourcePersistentAttribute parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
		this.targetClassAdapter = new AnnotatedElementAnnotationElementAdapter<String>(attribute, TARGET_CLASS_ADAPTER);
		this.fetchAdapter = new AnnotatedElementAnnotationElementAdapter<String>(attribute, FETCH_ADAPTER);
	}


	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public void initialize(CompilationUnit astRoot) {
		this.targetClass = this.buildTargetClass(astRoot);
		this.fetch = this.buildFetch(astRoot);
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncTargetClass(this.buildTargetClass(astRoot));
		this.syncFetch(this.buildFetch(astRoot));
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.targetClass == null) &&
				(this.fetch == null);
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
			this.fqTargetClassNameStale = true;
			this.targetClassAdapter.setValue(targetClass);
		}
	}

	private void syncTargetClass(String astTargetClass) {
		if (this.attributeValueHasChanged(this.targetClass, astTargetClass)) {
			this.syncTargetClass_(astTargetClass);
		}
	}

	private void syncTargetClass_(String astTargetClass) {
		String old = this.targetClass;
		this.targetClass = astTargetClass;
		this.fqTargetClassNameStale = true;
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
		if (this.fqTargetClassNameStale) {
			this.fullyQualifiedTargetClassName = this.buildFullyQualifiedTargetClassName();
			this.fqTargetClassNameStale = false;
		}
		return this.fullyQualifiedTargetClassName;
	}

	private String buildFullyQualifiedTargetClassName() {
		return (this.targetClass == null) ? null : this.buildFullyQualifiedTargetClassName_();
	}

	private String buildFullyQualifiedTargetClassName_() {
		return ASTTools.resolveFullyQualifiedName(this.targetClassAdapter.getExpression(this.buildASTRoot()));
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
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA2_0.ELEMENT_COLLECTION__FETCH);
	}
	
	private static DeclarationAnnotationElementAdapter<String> buildTargetClassAdapter() {
		return buildTargetClassAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA2_0.ELEMENT_COLLECTION__TARGET_CLASS);
	}
	
	private static DeclarationAnnotationElementAdapter<String> buildTargetClassAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		// TODO what about QualifiedType?
		return buildAnnotationElementAdapter(annotationAdapter, elementName, SimpleTypeStringExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, ExpressionConverter<String> converter) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(annotationAdapter, elementName, converter);
	}

}
