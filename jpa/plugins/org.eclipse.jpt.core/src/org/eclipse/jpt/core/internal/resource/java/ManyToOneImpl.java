/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.BooleanExpressionConverter;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.ManyToOneAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;


public class ManyToOneImpl extends AbstractRelationshipMappingAnnotation implements ManyToOneAnnotation
{	
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	
	private static final DeclarationAnnotationElementAdapter<String> TARGET_ENTITY_ADAPTER = buildTargetEntityAdapter();	

	private static final DeclarationAnnotationElementAdapter<String[]> CASCADE_ADAPTER = buildCascadeAdapter();

	private static final DeclarationAnnotationElementAdapter<String> FETCH_ADAPTER = buildFetchAdapter();
	
	private static final DeclarationAnnotationElementAdapter<Boolean> OPTIONAL_ADAPTER = buildOptionalAdapter();
	
	private final AnnotationElementAdapter<Boolean> optionalAdapter;

	private Boolean optional;

	protected ManyToOneImpl(JavaResourcePersistentAttribute parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
		this.optionalAdapter = this.buildBooleanAnnotationElementAdapter(OPTIONAL_ADAPTER);
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.optional = this.optional(astRoot);
	}
	
	//**************** AbstractRelationshipMappingAnnotation implementation **************
	
	@Override
	protected DeclarationAnnotationElementAdapter<String> getTargetEntityAdapter() {
		return TARGET_ENTITY_ADAPTER;
	}
	
	@Override
	protected DeclarationAnnotationElementAdapter<String[]> getCascadeAdapter() {
		return CASCADE_ADAPTER;
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> getFetchAdapter() {
		return FETCH_ADAPTER;
	}
	
	//**************** Annotation implementation **************
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	
	public Boolean getOptional() {
		return this.optional;
	}
	
	public void setOptional(Boolean newOptional) {
		if (attributeValueHasNotChanged(this.optional, newOptional)) {
			return;
		}
		Boolean oldOptional = this.optional;
		this.optional = newOptional;
		this.optionalAdapter.setValue(newOptional);
		firePropertyChanged(OPTIONAL_PROPERTY, oldOptional, newOptional);
	}

	public TextRange getOptionalTextRange(CompilationUnit astRoot) {
		return getElementTextRange(OPTIONAL_ADAPTER, astRoot);
	}

	@Override
	public void update(CompilationUnit astRoot) {
		super.update(astRoot);
		this.setOptional(this.optional(astRoot));
	}
	
	protected Boolean optional(CompilationUnit astRoot) {
		return this.optionalAdapter.getValue(astRoot);
	}

	// ********** static methods **********
	
	private static DeclarationAnnotationElementAdapter<String> buildTargetEntityAdapter() {
		return buildTargetEntityAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.MANY_TO_ONE__TARGET_ENTITY);
	}

	private static DeclarationAnnotationElementAdapter<String[]> buildCascadeAdapter() {
		return buildEnumArrayAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.MANY_TO_ONE__CASCADE);
	}

	private static DeclarationAnnotationElementAdapter<String> buildFetchAdapter() {
		return buildFetchAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.MANY_TO_ONE__FETCH);
	}
	
	private static DeclarationAnnotationElementAdapter<Boolean> buildOptionalAdapter() {
		return buildOptionalAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.MANY_TO_ONE__OPTIONAL);
	}
	
	private static DeclarationAnnotationElementAdapter<Boolean> buildOptionalAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(annotationAdapter, elementName, false, BooleanExpressionConverter.instance());
	}

	
	public static class ManyToOneAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final ManyToOneAnnotationDefinition INSTANCE = new ManyToOneAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static ManyToOneAnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure single instance.
		 */
		private ManyToOneAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new ManyToOneImpl((JavaResourcePersistentAttribute) parent, (Attribute) member);
		}
		
		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return null;
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
