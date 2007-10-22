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
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;


public class EntityImpl extends AbstractAnnotationResource<Type> implements Entity
{
	private static final String ANNOTATION_NAME = JPA.ENTITY;

	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final AnnotationElementAdapter<String> nameAdapter;

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = buildNameAdapter();

	private String name;

	
	protected EntityImpl(JavaPersistentTypeResource parent, Type type) {
		super(parent, type, DECLARATION_ANNOTATION_ADAPTER);
		this.nameAdapter = new ShortCircuitAnnotationElementAdapter<String>(getMember(), NAME_ADAPTER);
	}
	
	//*********** Annotation implementation ****************
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	//*********** Entity implementation ****************
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
		this.nameAdapter.setValue(name);
	}

	public ITextRange nameTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(NAME_ADAPTER, astRoot);
	}
	
	//*********** JavaResource implementation ****************
	public void updateFromJava(CompilationUnit astRoot) {
		setName(this.nameAdapter.getValue(astRoot));
	}
	
	
	//*********** static methods ****************
	private static DeclarationAnnotationElementAdapter<String> buildNameAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.ENTITY__NAME, false); // false = do not remove annotation when empty
	}

	
	public static class EntityAnnotationDefinition implements MappingAnnotationDefinition
	{
		// singleton
		private static final EntityAnnotationDefinition INSTANCE = new EntityAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static MappingAnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private EntityAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResource parent, Member member) {
			return new EntityImpl((JavaPersistentTypeResource) parent, (Type) member);
		}

		public Iterator<String> correspondingAnnotationNames() {
			return new ArrayIterator<String>(
				JPA.TABLE,
				JPA.SECONDARY_TABLE,
				JPA.SECONDARY_TABLES,
				JPA.PRIMARY_KEY_JOIN_COLUMN,
				JPA.PRIMARY_KEY_JOIN_COLUMNS,
				JPA.ID_CLASS,
				JPA.INHERITANCE,
				JPA.DISCRIMINATOR_VALUE,
				JPA.DISCRIMINATOR_COLUMN,
				JPA.SEQUENCE_GENERATOR,
				JPA.TABLE_GENERATOR,
				JPA.NAMED_QUERY,
				JPA.NAMED_QUERIES,
				JPA.NAMED_NATIVE_QUERY,
				JPA.NAMED_NATIVE_QUERIES,
				JPA.SQL_RESULT_SET_MAPPING,
				JPA.EXCLUDE_DEFAULT_LISTENERS,
				JPA.EXCLUDE_SUPERCLASS_LISTENERS,
				JPA.ENTITY_LISTENERS,
				JPA.PRE_PERSIST,
				JPA.POST_PERSIST,
				JPA.PRE_REMOVE,
				JPA.POST_REMOVE,
				JPA.PRE_UPDATE,
				JPA.POST_UPDATE,
				JPA.POST_LOAD,
				JPA.ATTRIBUTE_OVERRIDE,
				JPA.ATTRIBUTE_OVERRIDES,
				JPA.ASSOCIATION_OVERRIDE,
				JPA.ASSOCIATION_OVERRIDES);
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}
}
