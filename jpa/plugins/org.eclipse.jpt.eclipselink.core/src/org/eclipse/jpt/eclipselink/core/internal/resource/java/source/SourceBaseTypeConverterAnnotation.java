/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.TypeStringExpressionConverter;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.eclipselink.core.resource.java.BaseTypeConverterAnnotation;

/**
 * org.eclipse.persistence.annotations.TypeConverter
 * org.eclipse.persistence.annotations.ObjectTypeConverter
 */
abstract class SourceBaseTypeConverterAnnotation
	extends SourceNamedConverterAnnotation
	implements BaseTypeConverterAnnotation
{
	final DeclarationAnnotationElementAdapter<String> dataTypeDeclarationAdapter;
	final AnnotationElementAdapter<String> dataTypeAdapter;
	String dataType;

	final DeclarationAnnotationElementAdapter<String> objectTypeDeclarationAdapter;
	final AnnotationElementAdapter<String> objectTypeAdapter;
	String objectType;


	SourceBaseTypeConverterAnnotation(JavaResourcePersistentMember parent, Member member, DeclarationAnnotationAdapter daa) {
		super(parent, member, daa);
		this.dataTypeDeclarationAdapter = this.buildTypeAdapter(this.getDataTypeElementName());
		this.dataTypeAdapter = new ShortCircuitAnnotationElementAdapter<String>(this.member, this.dataTypeDeclarationAdapter);

		this.objectTypeDeclarationAdapter = this.buildTypeAdapter(this.getObjectTypeElementName());
		this.objectTypeAdapter = new ShortCircuitAnnotationElementAdapter<String>(this.member, this.objectTypeDeclarationAdapter);
	}

	private DeclarationAnnotationElementAdapter<String> buildTypeAdapter(String elementName) {
		 // false = do not remove annotation when empty
		return new ConversionDeclarationAnnotationElementAdapter<String>(this.daa, elementName, false, TypeStringExpressionConverter.instance());
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.dataType = this.buildDataType(astRoot);
		this.objectType = this.buildObjectType(astRoot);
	}

	@Override
	public void update(CompilationUnit astRoot) {
		super.update(astRoot);
		this.setDataType(this.buildDataType(astRoot));
		this.setObjectType(this.buildObjectType(astRoot));
	}


	// ********** BaseTypeConverterAnnotation implementation **********

	// ***** data type
	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		if (this.attributeValueHasNotChanged(this.dataType, dataType)) {
			return;
		}
		String old = this.dataType;
		this.dataType = dataType;
		this.dataTypeAdapter.setValue(dataType);
		this.firePropertyChanged(DATA_TYPE_PROPERTY, old, dataType);
	}

	private String buildDataType(CompilationUnit astRoot) {
		return this.dataTypeAdapter.getValue(astRoot);
	}

	public TextRange getDataTypeTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.dataTypeDeclarationAdapter, astRoot);
	}

	abstract String getDataTypeElementName();

	// ***** object type
	public String getObjectType() {
		return this.objectType;
	}

	public void setObjectType(String objectType) {
		if (this.attributeValueHasNotChanged(this.objectType, objectType)) {
			return;
		}
		String old = this.objectType;
		this.objectType = objectType;
		this.objectTypeAdapter.setValue(objectType);
		this.firePropertyChanged(OBJECT_TYPE_PROPERTY, old, objectType);
	}

	public TextRange getObjectTypeTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.objectTypeDeclarationAdapter, astRoot);
	}

	private String buildObjectType(CompilationUnit astRoot) {
		return this.objectTypeAdapter.getValue(astRoot);
	}

	abstract String getObjectTypeElementName();

}
