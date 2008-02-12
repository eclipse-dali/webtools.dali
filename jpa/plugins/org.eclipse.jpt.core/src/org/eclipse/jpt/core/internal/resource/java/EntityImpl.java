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
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Type;


public class EntityImpl extends AbstractAnnotationResource<Type> implements Entity
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final AnnotationElementAdapter<String> nameAdapter;

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = buildNameAdapter();

	private String name;

	protected EntityImpl(JavaPersistentTypeResource parent, Type type) {
		super(parent, type, DECLARATION_ANNOTATION_ADAPTER);
		this.nameAdapter = new ShortCircuitAnnotationElementAdapter<String>(getMember(), NAME_ADAPTER);
	}
	
	public void initialize(CompilationUnit astRoot) {
		this.name = this.name(astRoot);
	}
	
	//*********** Annotation implementation ****************
	public String getAnnotationName() {
		return Entity.ANNOTATION_NAME;
	}
	
	//*********** Entity implementation ****************
	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		this.nameAdapter.setValue(newName);
		firePropertyChanged(Entity.NAME_PROPERTY, oldName, newName);
	}

	public ITextRange nameTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(NAME_ADAPTER, astRoot);
	}
	
	//*********** JavaResource implementation ****************
	public void updateFromJava(CompilationUnit astRoot) {
		this.setName(this.name(astRoot));
	}

	protected String name(CompilationUnit astRoot) {
		return this.nameAdapter.getValue(astRoot);
	}

	//*********** static methods ****************
	private static DeclarationAnnotationElementAdapter<String> buildNameAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.ENTITY__NAME, false); // false = do not remove annotation when empty
	}

	
	public static class EntityAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final EntityAnnotationDefinition INSTANCE = new EntityAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static EntityAnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private EntityAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaPersistentResource parent, Member member) {
			return new EntityImpl((JavaPersistentTypeResource) parent, (Type) member);
		}
		
		public Annotation buildNullAnnotation(JavaPersistentResource parent, Member member) {
			return null;
		}


		public String getAnnotationName() {
			return Entity.ANNOTATION_NAME;
		}
	}
}
