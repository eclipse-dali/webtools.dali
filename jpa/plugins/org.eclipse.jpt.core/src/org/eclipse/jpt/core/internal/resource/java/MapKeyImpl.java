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
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;

public class MapKeyImpl extends AbstractAnnotationResource<Attribute> implements MapKey
{
	private static final String ANNOTATION_NAME = JPA.MAP_KEY;

	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = buildNameAdapter();

	
	private final AnnotationElementAdapter<String> nameAdapter;

	private String name;
	
	protected MapKeyImpl(JavaResource parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
		this.nameAdapter = new ShortCircuitAnnotationElementAdapter<String>(attribute, NAME_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
		this.nameAdapter.setValue(name);
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
		setName(nameAdapter.getValue(astRoot));
	}
	
	// ********** static methods **********
	private static DeclarationAnnotationElementAdapter<String> buildNameAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.MAP_KEY__NAME, false);
	}


	public static class MapKeyAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final MapKeyAnnotationDefinition INSTANCE = new MapKeyAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static AnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private MapKeyAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResource parent, Member member) {
			return new MapKeyImpl(parent, (Attribute) member);
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}
}
