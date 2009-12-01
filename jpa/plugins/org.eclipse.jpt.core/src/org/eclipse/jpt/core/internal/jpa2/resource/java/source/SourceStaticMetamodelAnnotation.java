/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.JDTTools;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.jpa2.resource.java.StaticMetamodelAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Type;

/**
 * javax.persistence.metamodel.StaticMetamodel
 */
public final class SourceStaticMetamodelAnnotation
	extends SourceAnnotation<Type>
	implements StaticMetamodelAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> VALUE_ADAPTER = buildValueAdapter();
	private final AnnotationElementAdapter<String> valueAdapter;
	private String value;

	private String fullyQualifiedClassName;


	public SourceStaticMetamodelAnnotation(JavaResourcePersistentType parent, Type type) {
		super(parent, type, DECLARATION_ANNOTATION_ADAPTER);
		this.valueAdapter = new ShortCircuitAnnotationElementAdapter<String>(type, VALUE_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public void initialize(CompilationUnit astRoot) {
		this.value = this.buildValue(astRoot);
		this.fullyQualifiedClassName = this.buildFullyQualifiedClassName(astRoot);
	}

	public void update(CompilationUnit astRoot) {
		this.setValue(this.buildValue(astRoot));
		this.setFullyQualifiedClassName(this.buildFullyQualifiedClassName(astRoot));
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}


	// ********** StaticMetamodelAnnotation implementation **********

	// ***** value
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		if (this.attributeValueHasNotChanged(this.value, value)) {
			return;
		}
		String old = this.value;
		this.value = value;
		this.valueAdapter.setValue(value);
		this.firePropertyChanged(VALUE_PROPERTY, old, value);
	}

	private String buildValue(CompilationUnit astRoot) {
		return this.valueAdapter.getValue(astRoot);
	}

	// ***** fully-qualified class name
	public String getFullyQualifiedClassName() {
		return this.fullyQualifiedClassName;
	}

	private void setFullyQualifiedClassName(String fullyQualifiedClassName) {
		String old = this.fullyQualifiedClassName;
		this.fullyQualifiedClassName = fullyQualifiedClassName;
		this.firePropertyChanged(FULLY_QUALIFIED_CLASS_NAME_PROPERTY, old, fullyQualifiedClassName);
	}

	private String buildFullyQualifiedClassName(CompilationUnit astRoot) {
		return (this.value == null) ? null : JDTTools.resolveFullyQualifiedName(this.valueAdapter.getExpression(astRoot));
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildValueAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String>(DECLARATION_ANNOTATION_ADAPTER, JPA2_0.STATIC_METAMODEL__VALUE, SimpleTypeStringExpressionConverter.instance());
	}

}
