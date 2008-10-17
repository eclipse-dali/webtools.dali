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
import org.eclipse.jpt.core.internal.utility.jdt.JDTTools;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.core.internal.utility.jdt.StringExpressionConverter;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.eclipselink.core.resource.java.ConverterAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;


public class ConverterImpl extends AbstractResourceAnnotation<Member> implements ConverterAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final AnnotationElementAdapter<String> nameAdapter;
	private final AnnotationElementAdapter<String> converterClassAdapter;

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = buildNameAdapter();
	private static final DeclarationAnnotationElementAdapter<String> CONVERTER_CLASS_ADAPTER = buildConverterClassAdapter();

	
	private String name;
	private String converterClass;
	private boolean implementsConverter;
	
	protected ConverterImpl(JavaResourcePersistentMember parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER);
		this.nameAdapter = new ShortCircuitAnnotationElementAdapter<String>(member, NAME_ADAPTER);
		this.converterClassAdapter = new ShortCircuitAnnotationElementAdapter<String>(member, CONVERTER_CLASS_ADAPTER);
	}
	
	public void initialize(CompilationUnit astRoot) {
		this.name = this.name(astRoot);
		this.converterClass = this.converterClass(astRoot);
		this.implementsConverter = this.implementsConverter(astRoot);
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

	public String getConverterClass() {
		return this.converterClass;
	}
	
	public void setConverterClass(String newConverterClass) {
		if (attributeValueHasNotChanged(this.converterClass, newConverterClass)) {
			return;
		}
		String oldConverterClass = this.converterClass;
		this.converterClass = newConverterClass;
		this.converterClassAdapter.setValue(newConverterClass);
		firePropertyChanged(CONVERTER_CLASS_PROPERTY, oldConverterClass, newConverterClass);
	}
	
	public boolean implementsConverter() {
		return this.implementsConverter;
	}
	
	protected void setImplementsConverter(boolean newImplementsConverter) {
		boolean oldImplementsConverter = this.implementsConverter;
		this.implementsConverter = newImplementsConverter;
		firePropertyChanged(IMPLEMENTS_CONVERTER_PROPERTY, oldImplementsConverter, newImplementsConverter);
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(NAME_ADAPTER, astRoot);
	}

	public TextRange getConverterClassTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(CONVERTER_CLASS_ADAPTER, astRoot);
	}
	
	public void update(CompilationUnit astRoot) {
		this.setName(this.name(astRoot));
		this.setConverterClass(this.converterClass(astRoot));
		this.setImplementsConverter(this.implementsConverter(astRoot));
	}
	
	protected String name(CompilationUnit astRoot) {
		return this.nameAdapter.getValue(astRoot);
	}
	
	protected String converterClass(CompilationUnit astRoot) {
		return this.converterClassAdapter.getValue(astRoot);
	}

	private boolean implementsConverter(CompilationUnit astRoot) {
		if (this.converterClass == null) {
			return false;
		}
		return JDTTools.findTypeInHierarchy(this.converterClassAdapter.getExpression(astRoot), ECLIPSELINK_CONVERTER_CLASS_NAME) != null;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}
	
	// ********** static methods **********
	private static DeclarationAnnotationElementAdapter<String> buildNameAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String>(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.CONVERTER__NAME, false, StringExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<String> buildConverterClassAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String>(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.CONVERTER__CONVERTER_CLASS, false, SimpleTypeStringExpressionConverter.instance());
	}
	
	public static class ConverterAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final ConverterAnnotationDefinition INSTANCE = new ConverterAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static ConverterAnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private ConverterAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new ConverterImpl(parent, member);
		}
		
		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return null;
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
