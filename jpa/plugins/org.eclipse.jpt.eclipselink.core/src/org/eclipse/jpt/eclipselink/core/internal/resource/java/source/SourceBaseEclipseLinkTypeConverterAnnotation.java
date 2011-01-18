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
import org.eclipse.jpt.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.TypeStringExpressionConverter;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.eclipselink.core.resource.java.BaseEclipseLinkTypeConverterAnnotation;

/**
 * org.eclipse.persistence.annotations.TypeConverter
 * org.eclipse.persistence.annotations.ObjectTypeConverter
 */
abstract class SourceBaseEclipseLinkTypeConverterAnnotation
	extends SourceEclipseLinkNamedConverterAnnotation
	implements BaseEclipseLinkTypeConverterAnnotation
{
	final DeclarationAnnotationElementAdapter<String> dataTypeDeclarationAdapter;
	final AnnotationElementAdapter<String> dataTypeAdapter;
	String dataType;

	/**
	 * @see org.eclipse.jpt.core.internal.resource.java.source.SourceIdClassAnnotation#fullyQualifiedClassName
	 */
	String fullyQualifiedDataType;
	// we need a flag since the f-q name can be null
	boolean fqDataTypeStale = true;

	final DeclarationAnnotationElementAdapter<String> objectTypeDeclarationAdapter;
	final AnnotationElementAdapter<String> objectTypeAdapter;
	String objectType;

	/**
	 * @see org.eclipse.jpt.core.internal.resource.java.source.SourceIdClassAnnotation#fullyQualifiedClassName
	 */
	String fullyQualifiedObjectType;
	// we need a flag since the f-q name can be null
	boolean fqObjectTypeStale = true;


	SourceBaseEclipseLinkTypeConverterAnnotation(JavaResourcePersistentMember parent, Member member, DeclarationAnnotationAdapter daa) {
		super(parent, member, daa);
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
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.dataType = this.buildDataType(astRoot);
		this.objectType = this.buildObjectType(astRoot);
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		this.syncDataType(this.buildDataType(astRoot));
		this.syncObjectType(this.buildObjectType(astRoot));
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
		if (this.attributeValueHasChanged(this.dataType, dataType)) {
			this.dataType = dataType;
			this.fqDataTypeStale = true;
			this.dataTypeAdapter.setValue(dataType);
		}
	}

	private void syncDataType(String astDataType) {
		if (this.attributeValueHasChanged(this.dataType, astDataType)) {
			this.syncDataType_(astDataType);
		}
	}

	private void syncDataType_(String astDataType) {
		String old = this.dataType;
		this.dataType = astDataType;
		this.fqDataTypeStale = true;
		this.firePropertyChanged(DATA_TYPE_PROPERTY, old, astDataType);
	}

	private String buildDataType(CompilationUnit astRoot) {
		return this.dataTypeAdapter.getValue(astRoot);
	}

	public TextRange getDataTypeTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.dataTypeDeclarationAdapter, astRoot);
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
		if (this.attributeValueHasChanged(this.objectType, objectType)) {
			this.objectType = objectType;
			this.fqObjectTypeStale = true;
			this.objectTypeAdapter.setValue(objectType);
		}
	}

	private void syncObjectType(String astObjectType) {
		if (this.attributeValueHasChanged(this.objectType, astObjectType)) {
			this.syncObjectType_(astObjectType);
		}
	}

	private void syncObjectType_(String astObjectType) {
		String old = this.objectType;
		this.objectType = astObjectType;
		this.fqObjectTypeStale = true;
		this.firePropertyChanged(OBJECT_TYPE_PROPERTY, old, astObjectType);
	}

	private String buildObjectType(CompilationUnit astRoot) {
		return this.objectTypeAdapter.getValue(astRoot);
	}

	public TextRange getObjectTypeTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.objectTypeDeclarationAdapter, astRoot);
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
