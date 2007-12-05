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

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.BooleanStringExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;


public class ManyToOneImpl extends AbstractRelationshipMappingAnnotation implements ManyToOne
{	
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	
	private static final DeclarationAnnotationElementAdapter<String> TARGET_ENTITY_ADAPTER = buildTargetEntityAdapter();	

	private static final DeclarationAnnotationElementAdapter<String[]> CASCADE_ADAPTER = buildCascadeAdapter();

	private static final DeclarationAnnotationElementAdapter<String> FETCH_ADAPTER = buildFetchAdapter();
	
	private static final DeclarationAnnotationElementAdapter<String> OPTIONAL_ADAPTER = buildOptionalAdapter();
	
	private final AnnotationElementAdapter<String> optionalAdapter;

	private Boolean optional;

	protected ManyToOneImpl(JavaPersistentAttributeResource parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
		this.optionalAdapter = this.buildAnnotationElementAdapter(OPTIONAL_ADAPTER);
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.optional = this.optional(astRoot);
	}
	
	//**************** AbstractRelationshipMappingAnnotation implementation **************
	
	@Override
	protected DeclarationAnnotationElementAdapter<String> targetEntityAdapter() {
		return TARGET_ENTITY_ADAPTER;
	}
	
	@Override
	protected DeclarationAnnotationElementAdapter<String[]> cascadeAdapter() {
		return CASCADE_ADAPTER;
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> fetchAdapter() {
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
		Boolean oldOptional = this.optional;
		this.optional = newOptional;
		this.optionalAdapter.setValue(BooleanUtility.toJavaAnnotationValue(newOptional));
		firePropertyChanged(OPTIONAL_PROPERTY, oldOptional, newOptional);
	}

	public ITextRange optionalTextRange(CompilationUnit astRoot) {
		return elementTextRange(OPTIONAL_ADAPTER, astRoot);
	}

	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.setOptional(this.optional(astRoot));
	}
	
	protected Boolean optional(CompilationUnit astRoot) {
		return BooleanUtility.fromJavaAnnotationValue(this.optionalAdapter.getValue(astRoot));
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
	
	private static DeclarationAnnotationElementAdapter<String> buildOptionalAdapter() {
		return buildOptionalAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.MANY_TO_ONE__OPTIONAL);
	}
	
	private static DeclarationAnnotationElementAdapter<String> buildOptionalAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(annotationAdapter, elementName, false, BooleanStringExpressionConverter.instance());
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
		 * Ensure non-instantiability.
		 */
		private ManyToOneAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResource parent, Member member) {
			return new ManyToOneImpl((JavaPersistentAttributeResource) parent, (Attribute) member);
		}
		
		public Annotation buildNullAnnotation(JavaResource parent, Member member) {
			return null;
		}

		//TODO put this in the java context model when JavaOneToOneMapping exists
//		public Iterator<String> correspondingAnnotationNames() {
//			return new ArrayIterator<String>(
//				JPA.JOIN_COLUMN,
//				JPA.JOIN_COLUMNS,
//				JPA.JOIN_TABLE);
//		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
