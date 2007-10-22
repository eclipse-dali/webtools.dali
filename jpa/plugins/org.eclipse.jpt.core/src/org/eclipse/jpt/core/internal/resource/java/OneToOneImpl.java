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

import java.util.Iterator;
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
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class OneToOneImpl extends AbstractRelationshipMappingAnnotation implements OneToOne
{
	private static final String ANNOTATION_NAME = JPA.ONE_TO_ONE;

	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	static final DeclarationAnnotationElementAdapter<String> TARGET_ENTITY_ADAPTER = buildTargetEntityAdapter();	

	private static final DeclarationAnnotationElementAdapter<String[]> CASCADE_ADAPTER = buildCascadeAdapter();

	private static final DeclarationAnnotationElementAdapter<String> FETCH_ADAPTER = buildFetchAdapter();

	private static final DeclarationAnnotationElementAdapter<String> OPTIONAL_ADAPTER = buildOptionalAdapter();

	private static final DeclarationAnnotationElementAdapter<String> MAPPED_BY_ADAPTER = buildMappedByAdapter();	

	private final AnnotationElementAdapter<String> optionalAdapter;

	private final AnnotationElementAdapter<String> mappedByAdapter;


	private Boolean optional;
	
	private String mappedBy;

	public OneToOneImpl(JavaPersistentAttributeResource parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
		this.mappedByAdapter = buildAnnotationElementAdapter(MAPPED_BY_ADAPTER);
		this.optionalAdapter = this.buildAnnotationElementAdapter(OPTIONAL_ADAPTER);
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
	
	public void setOptional(Boolean optional) {
		this.optional = optional;
		this.optionalAdapter.setValue(BooleanUtility.toJavaAnnotationValue(optional));
	}

	public String getMappedBy() {
		return this.mappedBy;
	}
	
	public void setMappedBy(String mappedBy) {
		this.mappedBy = mappedBy;
		this.mappedByAdapter.setValue(mappedBy);
	}
	
	public ITextRange mappedByTextRange(CompilationUnit astRoot) {
		return elementTextRange(MAPPED_BY_ADAPTER, astRoot);
	}
	
	public ITextRange optionalTextRange(CompilationUnit astRoot) {
		return elementTextRange(OPTIONAL_ADAPTER, astRoot);
	}
	
	public boolean mappedByTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(MAPPED_BY_ADAPTER, pos, astRoot);
	}

	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.setOptional(BooleanUtility.fromJavaAnnotationValue(this.optionalAdapter.getValue(astRoot)));
		this.setMappedBy(this.mappedByAdapter.getValue(astRoot));
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildTargetEntityAdapter() {
		return buildTargetEntityAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.ONE_TO_ONE__TARGET_ENTITY);
	}

	private static DeclarationAnnotationElementAdapter<String[]> buildCascadeAdapter() {
		return buildEnumArrayAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.ONE_TO_ONE__CASCADE);
	}

	private static DeclarationAnnotationElementAdapter<String> buildFetchAdapter() {
		return buildFetchAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.ONE_TO_ONE__FETCH);
	}
	
	private static DeclarationAnnotationElementAdapter<String> buildOptionalAdapter() {
		return buildOptionalAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.ONE_TO_ONE__OPTIONAL);
	}
	
	private static DeclarationAnnotationElementAdapter<String> buildOptionalAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(annotationAdapter, elementName, false, BooleanStringExpressionConverter.instance());
	}
	
	private static DeclarationAnnotationElementAdapter<String> buildMappedByAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.ONE_TO_ONE__MAPPED_BY, false); // false = do not remove annotation when empty
	}
	
	public static class OneToOneAnnotationDefinition implements MappingAnnotationDefinition
	{

		// singleton
		private static final OneToOneAnnotationDefinition INSTANCE = new OneToOneAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static OneToOneAnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private OneToOneAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResource parent, Member member) {
			return new OneToOneImpl((JavaPersistentAttributeResource) parent, (Attribute) member);
		}

		public Iterator<String> correspondingAnnotationNames() {
			return new ArrayIterator<String>(
				JPA.PRIMARY_KEY_JOIN_COLUMN,
				JPA.PRIMARY_KEY_JOIN_COLUMNS,
				JPA.JOIN_COLUMN,
				JPA.JOIN_COLUMNS,
				JPA.JOIN_TABLE);
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}


}
