/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.TypeStringExpressionConverter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.BaseTypeConverterAnnotation;

/**
 * <code>
 * <ul>
 * <li>org.eclipse.persistence.annotations.TypeConverter
 * <li>org.eclipse.persistence.annotations.ObjectTypeConverter
 * </ul>
 * </code>
 */
abstract class EclipseLinkSourceBaseTypeConverterAnnotation
	extends EclipseLinkSourceNamedConverterAnnotation
	implements BaseTypeConverterAnnotation
{
	final DeclarationAnnotationElementAdapter<String> dataTypeDeclarationAdapter;
	final AnnotationElementAdapter<String> dataTypeAdapter;
	String dataType;
	TextRange dataTypeTextRange;

	/**
	 * @see org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceIdClassAnnotation#fullyQualifiedClassName
	 */
	String fullyQualifiedDataType;
	// we need a flag since the f-q name can be null
	boolean fqDataTypeStale = true;

	final DeclarationAnnotationElementAdapter<String> objectTypeDeclarationAdapter;
	final AnnotationElementAdapter<String> objectTypeAdapter;
	String objectType;
	TextRange objectTypeTextRange;

	/**
	 * @see org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceIdClassAnnotation#fullyQualifiedClassName
	 */
	String fullyQualifiedObjectType;
	// we need a flag since the f-q name can be null
	boolean fqObjectTypeStale = true;


	EclipseLinkSourceBaseTypeConverterAnnotation(
			JavaResourceAnnotatedElement parent,
			AnnotatedElement element,
			IndexedDeclarationAnnotationAdapter daa,
			IndexedAnnotationAdapter annotationAdapter) {
		super(parent, element, daa, annotationAdapter);
		this.dataTypeDeclarationAdapter = this.buildTypeAdapter(this.getDataTypeElementName());
		this.dataTypeAdapter = new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, this.dataTypeDeclarationAdapter);

		this.objectTypeDeclarationAdapter = this.buildTypeAdapter(this.getObjectTypeElementName());
		this.objectTypeAdapter = new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, this.objectTypeDeclarationAdapter);
	}

	private DeclarationAnnotationElementAdapter<String> buildTypeAdapter(String elementName) {
		 // false = do not remove annotation when empty
		return new ConversionDeclarationAnnotationElementAdapter<String>(this.daa, elementName, TypeStringExpressionConverter.instance());
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);

		this.dataType = this.buildDataType(astAnnotation);
		this.dataTypeTextRange = this.buildDataTypeTextRange(astAnnotation);

		this.objectType = this.buildObjectType(astAnnotation);
		this.objectTypeTextRange = this.buildObjectTypeTextRange(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);

		this.syncDataType(this.buildDataType(astAnnotation));
		this.dataTypeTextRange = this.buildDataTypeTextRange(astAnnotation);

		this.syncObjectType(this.buildObjectType(astAnnotation));
		this.objectTypeTextRange = this.buildObjectTypeTextRange(astAnnotation);
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.dataType == null) &&
				(this.objectType == null);
	}


	// ********** BaseTypeConverterAnnotation implementation **********

	// ***** data type
	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		if (ObjectTools.notEquals(this.dataType, dataType)) {
			this.dataType = dataType;
			this.fqDataTypeStale = true;
			this.dataTypeAdapter.setValue(dataType);
		}
	}

	private void syncDataType(String astDataType) {
		if (ObjectTools.notEquals(this.dataType, astDataType)) {
			this.syncDataType_(astDataType);
		}
	}

	private void syncDataType_(String astDataType) {
		String old = this.dataType;
		this.dataType = astDataType;
		this.fqDataTypeStale = true;
		this.firePropertyChanged(DATA_TYPE_PROPERTY, old, astDataType);
	}

	private String buildDataType(Annotation astAnnotation) {
		return this.dataTypeAdapter.getValue(astAnnotation);
	}

	public TextRange getDataTypeTextRange() {
		return this.dataTypeTextRange;
	}

	private TextRange buildDataTypeTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.dataTypeDeclarationAdapter, astAnnotation);
	}

	abstract String getDataTypeElementName();

	// ***** fully-qualified data type
	public String getFullyQualifiedDataType() {
		if (this.fqDataTypeStale) {
			this.fullyQualifiedDataType = this.buildFullyQualifiedDataType();
			this.fqDataTypeStale = false;
		}
		return this.fullyQualifiedDataType;
	}

	private String buildFullyQualifiedDataType() {
		return (this.dataType == null) ? null : this.buildFullyQualifiedDataType_();
	}

	private String buildFullyQualifiedDataType_() {
		return ASTTools.resolveFullyQualifiedName(this.dataTypeAdapter.getExpression(this.buildASTRoot()));
	}

	// ***** object type
	public String getObjectType() {
		return this.objectType;
	}

	public void setObjectType(String objectType) {
		if (ObjectTools.notEquals(this.objectType, objectType)) {
			this.objectType = objectType;
			this.fqObjectTypeStale = true;
			this.objectTypeAdapter.setValue(objectType);
		}
	}

	private void syncObjectType(String astObjectType) {
		if (ObjectTools.notEquals(this.objectType, astObjectType)) {
			this.syncObjectType_(astObjectType);
		}
	}

	private void syncObjectType_(String astObjectType) {
		String old = this.objectType;
		this.objectType = astObjectType;
		this.fqObjectTypeStale = true;
		this.firePropertyChanged(OBJECT_TYPE_PROPERTY, old, astObjectType);
	}

	private String buildObjectType(Annotation astAnnotation) {
		return this.objectTypeAdapter.getValue(astAnnotation);
	}

	public TextRange getObjectTypeTextRange() {
		return this.objectTypeTextRange;
	}

	private TextRange buildObjectTypeTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.objectTypeDeclarationAdapter, astAnnotation);
	}

	abstract String getObjectTypeElementName();

	// ***** fully-qualified object type
	public String getFullyQualifiedObjectType() {
		if (this.fqObjectTypeStale) {
			this.fullyQualifiedObjectType = this.buildFullyQualifiedObjectType();
			this.fqObjectTypeStale = false;
		}
		return this.fullyQualifiedObjectType;
	}

	private String buildFullyQualifiedObjectType() {
		return (this.objectType == null) ? null : this.buildFullyQualifiedObjectType_();
	}

	private String buildFullyQualifiedObjectType_() {
		return ASTTools.resolveFullyQualifiedName(this.objectTypeAdapter.getExpression(this.buildASTRoot()));
	}
}
