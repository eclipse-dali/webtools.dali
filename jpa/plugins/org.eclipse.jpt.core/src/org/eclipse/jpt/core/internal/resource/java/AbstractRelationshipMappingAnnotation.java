/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleTypeStringExpressionConverter;


public abstract class AbstractRelationshipMappingAnnotation extends AbstractAnnotationResource<Attribute> implements RelationshipMappingAnnotation
{
	private final AnnotationElementAdapter<String> targetEntityAdapter;

	private final AnnotationElementAdapter<String> fetchAdapter;

	private String targetEntity;

	private String fullyQualfiedTargetEntity;

	private FetchType fetch;
	
	
	public AbstractRelationshipMappingAnnotation(JavaPersistentAttributeResource parent, Attribute attribute, DeclarationAnnotationAdapter daa) {
		super(parent, attribute, daa);
		this.targetEntityAdapter = buildAnnotationElementAdapter(targetEntityAdapter());
		this.fetchAdapter = buildAnnotationElementAdapter(fetchAdapter());
	}
	
	protected AnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new ShortCircuitAnnotationElementAdapter<String>(this.getMember(), daea);
	}

	/**
	 * return the Java adapter's 'targetEntity' element adapter config
	 */
	protected abstract DeclarationAnnotationElementAdapter<String> targetEntityAdapter();

	/**
	 * return the Java adapter's 'cascade' element adapter config
	 */
//	protected abstract DeclarationAnnotationElementAdapter<String[]> cascadeAdapter();

	/**
	 * return the Java adapter's 'fetch' element adapter config
	 */
	protected abstract DeclarationAnnotationElementAdapter<String> fetchAdapter();

	public String getTargetEntity() {
		return this.targetEntity;
	}
	
	public void setTargetEntity(String targetEntity) {
		this.targetEntity = targetEntity;
		this.targetEntityAdapter.setValue(targetEntity);
	}
	
	public String getFullyQualfiedTargetEntity() {
		return this.fullyQualfiedTargetEntity;
	}
	
	private void setFullyQualifiedTargetEntity(String targetEntity) {
		this.fullyQualfiedTargetEntity = targetEntity;
		//change notification
	}
	
	public FetchType getFetch() {
		return this.fetch;
	}
	
	public void setFetch(FetchType fetch) {
		this.fetch = fetch;
		this.fetchAdapter.setValue(FetchType.toJavaAnnotationValue(fetch));
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
		this.setFetch(FetchType.fromJavaAnnotationValue(this.fetchAdapter.getValue(astRoot)));
		this.setTargetEntity(this.targetEntityAdapter.getValue(astRoot));
		this.setFullyQualifiedTargetEntity(fullyQualifiedTargetEntity(astRoot));
	}
	
	private String fullyQualifiedTargetEntity(CompilationUnit astRoot) {
		if (getTargetEntity() == null) {
			return null;
		}
		Expression expression = this.targetEntityAdapter.expression(astRoot);
		if (expression.getNodeType() == ASTNode.TYPE_LITERAL) {
			ITypeBinding resolvedTypeBinding = ((TypeLiteral) expression).getType().resolveBinding();
			if (resolvedTypeBinding != null) {
				return resolvedTypeBinding.getQualifiedName();
			}
		}
		return null;
	}

	// ********** static methods **********
	
	protected static DeclarationAnnotationElementAdapter<String> buildTargetEntityAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		// TODO what about QualifiedType?
		return buildAnnotationElementAdapter(annotationAdapter, elementName, SimpleTypeStringExpressionConverter.instance());
	}
	
	protected static DeclarationAnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, ExpressionConverter<String> converter) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(annotationAdapter, elementName, false, converter);
	}
	
	protected static DeclarationAnnotationElementAdapter<String> buildFetchAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return new EnumDeclarationAnnotationElementAdapter(annotationAdapter, elementName, false);
	}

}
