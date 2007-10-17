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
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;


public class ManyToManyImpl extends AbstractRelationshipMappingAnnotation implements ManyToMany
{
	private static final String ANNOTATION_NAME = JPA.MANY_TO_MANY;

	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> TARGET_ENTITY_ADAPTER = buildTargetEntityAdapter();	

	private static final DeclarationAnnotationElementAdapter<String[]> CASCADE_ADAPTER = buildCascadeAdapter();

	private static final DeclarationAnnotationElementAdapter<String> FETCH_ADAPTER = buildFetchAdapter();
	
	private static final DeclarationAnnotationElementAdapter<String> MAPPED_BY_ADAPTER = buildMappedByAdapter();	

	private final AnnotationElementAdapter<String> mappedByAdapter;

	private String mappedBy;
	
	protected ManyToManyImpl(JavaPersistentAttributeResource parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
		this.mappedByAdapter = buildAnnotationElementAdapter(MAPPED_BY_ADAPTER);
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

	
	public String getMappedBy() {
		return this.mappedBy;
	}
	
	public void setMappedBy(String mappedBy) {
		this.mappedBy = mappedBy;
		this.mappedByAdapter.setValue(mappedBy);
	}
	
	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.setMappedBy(this.mappedByAdapter.getValue(astRoot));
	}
	
	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildTargetEntityAdapter() {
		return buildTargetEntityAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.MANY_TO_MANY__TARGET_ENTITY);
	}

	private static DeclarationAnnotationElementAdapter<String[]> buildCascadeAdapter() {
		return buildEnumArrayAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.MANY_TO_MANY__CASCADE);
	}
	
	private static DeclarationAnnotationElementAdapter<String> buildFetchAdapter() {
		return buildFetchAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.MANY_TO_MANY__FETCH);
	}

	
	private static DeclarationAnnotationElementAdapter<String> buildMappedByAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.MANY_TO_MANY__MAPPED_BY, false); // false = do not remove annotation when empty
	}

	
	public static class ManyToManyAnnotationDefinition implements MappingAnnotationDefinition
	{
		// singleton
		private static final ManyToManyAnnotationDefinition INSTANCE = new ManyToManyAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static ManyToManyAnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private ManyToManyAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResource parent, Member member) {
			return new ManyToManyImpl((JavaPersistentAttributeResource) parent, (Attribute) member);
		}

		public Iterator<String> correspondingAnnotationNames() {
			return new ArrayIterator<String>(
				JPA.ORDER_BY,
				JPA.MAP_KEY,
				JPA.JOIN_TABLE);
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
