/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.NestableXmlJavaTypeAdapterAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;

/**
 * javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter
 */
public final class SourceXmlJavaTypeAdapterAnnotation
	extends SourceAnnotation<AnnotatedElement>
	implements NestableXmlJavaTypeAdapterAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final DeclarationAnnotationElementAdapter<String> valueDeclarationAdapter;
	private final AnnotationElementAdapter<String> valueAdapter;
	private String value;

	private String fullyQualifiedValueClassName;


	// ********** constructors **********
	public SourceXmlJavaTypeAdapterAnnotation(JavaResourceNode parent, AnnotatedElement type) {
		this(parent, type, DECLARATION_ANNOTATION_ADAPTER, new ElementAnnotationAdapter(type, DECLARATION_ANNOTATION_ADAPTER));
	}

	public SourceXmlJavaTypeAdapterAnnotation(JavaResourceNode parent, AnnotatedElement type, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, type, daa, annotationAdapter);
		this.valueDeclarationAdapter = buildValueAdapter(daa);
		this.valueAdapter = this.buildAnnotationElementAdapter(this.valueDeclarationAdapter);
	}

	private DeclarationAnnotationElementAdapter<String> buildValueAdapter(DeclarationAnnotationAdapter daa) {
		return buildAnnotationElementAdapter(daa, JAXB.XML_JAVA_TYPE_ADAPTER__VALUE, SimpleTypeStringExpressionConverter.instance());
	}

	static DeclarationAnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, ExpressionConverter<String> converter) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(annotationAdapter, elementName, false, converter);
	}

	protected AnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, daea);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public void initialize(CompilationUnit astRoot) {
		this.value = this.buildValue(astRoot);
		this.fullyQualifiedValueClassName = this.buildFullyQualifiedValueClassName(astRoot);
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncValue(this.buildValue(astRoot));
		this.syncFullyQualifiedValueClassName(this.buildFullyQualifiedValueClassName(astRoot));
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}


	// ********** XmlJavaTypeAdapterAnnotation implementation **********

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
		return this.getElementTextRange(this.valueDeclarationAdapter, astRoot);
	}

	// ***** fully-qualified value class name
	public String getFullyQualifiedValueClassName() {
		return this.fullyQualifiedValueClassName;
	}

	private void syncFullyQualifiedValueClassName(String name) {
		String old = this.fullyQualifiedValueClassName;
		this.fullyQualifiedValueClassName = name;
		this.firePropertyChanged(FULLY_QUALIFIED_VALUE_CLASS_NAME_PROPERTY, old, name);
	}

	private String buildFullyQualifiedValueClassName(CompilationUnit astRoot) {
		return (this.value == null) ? null : ASTTools.resolveFullyQualifiedName(this.valueAdapter.getExpression(astRoot));
	}


	//*********** NestableAnnotation implementation ****************


	/**
	 * convenience implementation of method from NestableAnnotation interface
	 * for subclasses
	 */
	public void initializeFrom(NestableAnnotation oldAnnotation) {
		XmlJavaTypeAdapterAnnotation oldJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) oldAnnotation;
		this.setValue(oldJavaTypeAdapterAnnotation.getValue());
	}

	/**
	 * convenience implementation of method from NestableAnnotation interface
	 * for subclasses
	 */
	public void moveAnnotation(int newIndex) {
		this.getIndexedAnnotationAdapter().moveAnnotation(newIndex);
	}

	private IndexedAnnotationAdapter getIndexedAnnotationAdapter() {
		return (IndexedAnnotationAdapter) this.annotationAdapter;
	}


	// ********** static methods **********

	static SourceXmlJavaTypeAdapterAnnotation createNestedXmlJavaTypeAdapterAnnotation(JavaResourceNode parent, AnnotatedElement annotatedElement, int index, DeclarationAnnotationAdapter javaTypeAdaptersAdapter) {
		IndexedDeclarationAnnotationAdapter idaa = buildNestedDeclarationAnnotationAdapter(index, javaTypeAdaptersAdapter);
		IndexedAnnotationAdapter annotationAdapter = new ElementIndexedAnnotationAdapter(annotatedElement, idaa);

		return new SourceXmlJavaTypeAdapterAnnotation(parent, annotatedElement, idaa, annotationAdapter);
	}

	private static IndexedDeclarationAnnotationAdapter buildNestedDeclarationAnnotationAdapter(int index, DeclarationAnnotationAdapter javaTypeAdaptersAdapter) {
		return new NestedIndexedDeclarationAnnotationAdapter(javaTypeAdaptersAdapter, index, JAXB.XML_JAVA_TYPE_ADAPTER);
	}

}
