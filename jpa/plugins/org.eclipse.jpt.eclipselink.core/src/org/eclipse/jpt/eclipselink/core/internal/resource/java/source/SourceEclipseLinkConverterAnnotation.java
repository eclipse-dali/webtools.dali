/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.core.internal.utility.jdt.MemberAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkConverterAnnotation;

/**
 * org.eclipse.persistence.annotations.Converter
 */
public final class SourceEclipseLinkConverterAnnotation
	extends SourceEclipseLinkNamedConverterAnnotation
	implements EclipseLinkConverterAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> CONVERTER_CLASS_ADAPTER = buildConverterClassAdapter();
	private final AnnotationElementAdapter<String> converterClassAdapter;
	private String converterClass;

	private String fullyQualifiedConverterClassName;

	public SourceEclipseLinkConverterAnnotation(JavaResourcePersistentMember parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER);
		this.converterClassAdapter = new MemberAnnotationElementAdapter<String>(member, CONVERTER_CLASS_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.converterClass = this.buildConverterClass(astRoot);
		this.fullyQualifiedConverterClassName = this.buildFullyQualifiedConverterClassName(astRoot);
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		this.syncConverterClass(this.buildConverterClass(astRoot));
		this.syncFullyQualifiedConverterClassName(this.buildFullyQualifiedConverterClassName(astRoot));
	}


	// ********** SourceNamedConverterAnnotation implementation **********

	@Override
	String getNameElementName() {
		return EclipseLink.CONVERTER__NAME;
	}


	// ********** ConverterAnnotation implementation **********

	// ***** converter class
	public String getConverterClass() {
		return this.converterClass;
	}

	public void setConverterClass(String converterClass) {
		if (this.attributeValueHasChanged(this.converterClass, converterClass)) {
			this.converterClass = converterClass;
			this.converterClassAdapter.setValue(converterClass);
		}
	}

	private void syncConverterClass(String astConverterClass) {
		String old = this.converterClass;
		this.converterClass = astConverterClass;
		this.firePropertyChanged(CONVERTER_CLASS_PROPERTY, old, astConverterClass);
	}

	private String buildConverterClass(CompilationUnit astRoot) {
		return this.converterClassAdapter.getValue(astRoot);
	}

	public TextRange getConverterClassTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(CONVERTER_CLASS_ADAPTER, astRoot);
	}

	public boolean converterClassImplementsInterface(String interfaceName, CompilationUnit astRoot) {
		return (this.converterClass != null)
				&& ASTTools.typeIsSubTypeOf(this.converterClassAdapter.getExpression(astRoot), interfaceName);
	}

	// ***** fully-qualified converter class name
	public String getFullyQualifiedConverterClassName() {
		return this.fullyQualifiedConverterClassName;
	}

	private void syncFullyQualifiedConverterClassName(String name) {
		String old = this.fullyQualifiedConverterClassName;
		this.fullyQualifiedConverterClassName = name;
		this.firePropertyChanged(FULLY_QUALIFIED_CONVERTER_CLASS_NAME_PROPERTY, old, name);
	}

	private String buildFullyQualifiedConverterClassName(CompilationUnit astRoot) {
		return (this.converterClass == null) ? null : ASTTools.resolveFullyQualifiedName(this.converterClassAdapter.getExpression(astRoot));
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildConverterClassAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String>(DECLARATION_ANNOTATION_ADAPTER, EclipseLink.CONVERTER__CONVERTER_CLASS, false, SimpleTypeStringExpressionConverter.instance());
	}

}
