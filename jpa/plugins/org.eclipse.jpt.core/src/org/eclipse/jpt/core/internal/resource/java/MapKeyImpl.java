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
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.MapKeyAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;

public class MapKeyImpl extends AbstractResourceAnnotation<Attribute> implements MapKeyAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	
	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = buildNameAdapter();

	
	private final AnnotationElementAdapter<String> nameAdapter;

	private String name;
	
	protected MapKeyImpl(JavaResourceNode parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
		this.nameAdapter = new ShortCircuitAnnotationElementAdapter<String>(attribute, NAME_ADAPTER);
	}
	
	public void initialize(CompilationUnit astRoot) {
		this.name = name(astRoot);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public String getName() {
		return this.name;
	}
	
	public void setName(String newName) {
		if (attributeValueHasNotChanged(this.name, newName)) {
			return;
		}
		String oldName = this.name;
		this.name = newName;
		this.nameAdapter.setValue(newName);
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}
	
	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(NAME_ADAPTER, astRoot);
	}

	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(NAME_ADAPTER, pos, astRoot);
	}
	
	public void update(CompilationUnit astRoot) {
		this.setName(this.name(astRoot));
	}
	
	protected String name(CompilationUnit astRoot) {
		return this.nameAdapter.getValue(astRoot);
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
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
		 * Ensure single instance.
		 */
		private MapKeyAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new MapKeyImpl(parent, (Attribute) member);
		}
		
		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return null;
		}
		
		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}
}
