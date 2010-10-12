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
import org.eclipse.jpt.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkCustomizerAnnotation;

/**
 * org.eclipse.persistence.annotations.Customizer
 */
public final class SourceEclipseLinkCustomizerAnnotation
	extends SourceAnnotation<Type>
	implements EclipseLinkCustomizerAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> VALUE_ADAPTER = buildValueAdapter();
	private final AnnotationElementAdapter<String> valueAdapter;
	private String value;

	private String fullyQualifiedCustomizerClassName;

	public SourceEclipseLinkCustomizerAnnotation(JavaResourcePersistentType parent, Type type) {
		super(parent, type, DECLARATION_ANNOTATION_ADAPTER);
		this.valueAdapter = new AnnotatedElementAnnotationElementAdapter<String>(type, VALUE_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public void initialize(CompilationUnit astRoot) {
		this.value = this.buildValue(astRoot);
		this.fullyQualifiedCustomizerClassName = this.buildFullyQualifiedCustomizerClassName(astRoot);
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncValue(this.buildValue(astRoot));
		this.syncFullyQualifiedCustomizerClassName(this.buildFullyQualifiedCustomizerClassName(astRoot));
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}


	// ********** CustomizerAnnotation implementation **********

	// ***** value
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		if (this.attributeValueHasChanged(this.value, value)) {
			this.value = value;
			this.valueAdapter.setValue(value);
		}
	}

	private void syncValue(String astValue) {
		String old = this.value;
		this.value = astValue;
		this.firePropertyChanged(VALUE_PROPERTY, old, astValue);
	}

	private String buildValue(CompilationUnit astRoot) {
		return this.valueAdapter.getValue(astRoot);
	}

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(VALUE_ADAPTER, astRoot);
	}

	public boolean customizerClassImplementsInterface(String interfaceName, CompilationUnit astRoot) {
		return (this.value != null)
				&& ASTTools.typeIsSubTypeOf(this.valueAdapter.getExpression(astRoot), interfaceName);
	}


	// ***** fully-qualified customizer class name
	public String getFullyQualifiedCustomizerClassName() {
		return this.fullyQualifiedCustomizerClassName;
	}

	private void syncFullyQualifiedCustomizerClassName(String name) {
		String old = this.fullyQualifiedCustomizerClassName;
		this.fullyQualifiedCustomizerClassName = name;
		this.firePropertyChanged(FULLY_QUALIFIED_CUSTOMIZER_CLASS_NAME_PROPERTY, old, name);
	}

	private String buildFullyQualifiedCustomizerClassName(CompilationUnit astRoot) {
		return (this.value == null) ? null : ASTTools.resolveFullyQualifiedName(this.valueAdapter.getExpression(astRoot));
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildValueAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String>(DECLARATION_ANNOTATION_ADAPTER, EclipseLink.CUSTOMIZER__VALUE, false, SimpleTypeStringExpressionConverter.instance());
	}

}
