/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.AbstractResourceAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.StringExpressionConverter;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.StructConverterAnnotation;


public class StructConverterImpl extends AbstractResourceAnnotation<Member> implements StructConverterAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final AnnotationElementAdapter<String> nameAdapter;
	private final AnnotationElementAdapter<String> converterAdapter;

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = buildNameAdapter();
	private static final DeclarationAnnotationElementAdapter<String> CONVERTER_ADAPTER = buildConverterAdapter();

	
	private String name;
	private String converter;
	
	protected StructConverterImpl(JavaResourcePersistentMember parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER);
		this.nameAdapter = new ShortCircuitAnnotationElementAdapter<String>(member, NAME_ADAPTER);
		this.converterAdapter = new ShortCircuitAnnotationElementAdapter<String>(member, CONVERTER_ADAPTER);
	}
	
	public void initialize(CompilationUnit astRoot) {
		this.name = this.name(astRoot);
		this.converter = this.converter(astRoot);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	//*************** Converter implementation ****************
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

	public String getConverter() {
		return this.converter;
	}
	
	public void setConverter(String newConverter) {
		if (attributeValueHasNotChanged(this.converter, newConverter)) {
			return;
		}
		String oldConverter = this.converter;
		this.converter = newConverter;
		this.converterAdapter.setValue(newConverter);
		firePropertyChanged(CONVERTER_PROPERTY, oldConverter, newConverter);
	}
	
	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(NAME_ADAPTER, astRoot);
	}

	public TextRange getConverterTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(CONVERTER_ADAPTER, astRoot);
	}
	
	public void update(CompilationUnit astRoot) {
		this.setName(this.name(astRoot));
		this.setConverter(this.converter(astRoot));
	}
	
	protected String name(CompilationUnit astRoot) {
		return this.nameAdapter.getValue(astRoot);
	}
	
	protected String converter(CompilationUnit astRoot) {
		return this.converterAdapter.getValue(astRoot);
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}
	
	// ********** static methods **********
	private static DeclarationAnnotationElementAdapter<String> buildNameAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String>(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.STRUCT_CONVERTER__NAME, false, StringExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<String> buildConverterAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String>(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.STRUCT_CONVERTER__CONVERTER, false, StringExpressionConverter.instance());
	}
	
	public static class StructConverterAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final StructConverterAnnotationDefinition INSTANCE = new StructConverterAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static StructConverterAnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private StructConverterAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new StructConverterImpl(parent, member);
		}
		
		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return null;
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
