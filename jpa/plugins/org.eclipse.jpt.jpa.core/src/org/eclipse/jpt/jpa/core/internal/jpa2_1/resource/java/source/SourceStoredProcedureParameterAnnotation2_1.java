/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2_1.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.TypeStringExpressionConverter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.JPA2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.ParameterMode_2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.StoredProcedureParameterAnnotation2_1;

/**
 * <code>javax.persistence.StoredProcedureParameter</code>
 */
public final class SourceStoredProcedureParameterAnnotation2_1
	extends SourceAnnotation
	implements StoredProcedureParameterAnnotation2_1
{
	private DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;
	private AnnotationElementAdapter<String> nameAdapter;
	private String name;
	private TextRange nameTextRange;

	private DeclarationAnnotationElementAdapter<String> modeDeclarationAdapter;
	private AnnotationElementAdapter<String> modeAdapter;
	private ParameterMode_2_1 mode;
	private TextRange modeTextRange;

	private DeclarationAnnotationElementAdapter<String> typeDeclarationAdapter;
	private AnnotationElementAdapter<String> typeAdapter;
	private String type;
	private TextRange typeTextRange;

	private String fullyQualifiedTypeName;
	// we need a flag since the f-q name can be null
	private boolean fqTypeNameStale = true;
	
	public static SourceStoredProcedureParameterAnnotation2_1 buildNamedStoredProcedureQuery2_1Parameter(
			JavaResourceModel parent,
			AnnotatedElement element,
			DeclarationAnnotationAdapter namedStoredProcedureQueryAdapter,
			int index)
	{
		return buildNestedSourceStoredProcedureParameter2_1Annotation(
				parent,
				element,
				buildStoredProcedureParameter2_1AnnotationAdapter(namedStoredProcedureQueryAdapter,
				index));
	}

	
	public static SourceStoredProcedureParameterAnnotation2_1 buildNestedSourceStoredProcedureParameter2_1Annotation(
			JavaResourceModel parent, 
			AnnotatedElement element, 
			IndexedDeclarationAnnotationAdapter idaa) 
	{
		return new SourceStoredProcedureParameterAnnotation2_1(
				parent,
				element,
				idaa);
	}

	private SourceStoredProcedureParameterAnnotation2_1(
			JavaResourceModel parent,
			AnnotatedElement element,
			IndexedDeclarationAnnotationAdapter idaa)
	{
		super(parent, element, idaa, new ElementIndexedAnnotationAdapter(element, idaa));
		this.nameDeclarationAdapter = this.buildNameDeclarationAdapter();
		this.nameAdapter = this.buildNameAdapter();
		this.modeDeclarationAdapter = this.buildModeDeclarationAdapter();
		this.modeAdapter = this.buildModeAdapter();
		this.typeDeclarationAdapter = this.buildTypeDeclarationAdapter();
		this.typeAdapter = this.buildTypeAdapter();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.name = this.buildName(astAnnotation);
		this.nameTextRange = this.buildNameTextRange(astAnnotation);

		this.mode = this.buildMode(astAnnotation);
		this.modeTextRange = this.buildModeTextRange(astAnnotation);

		this.type = this.buildType(astAnnotation);
		this.typeTextRange = this.buildTypeTextRange(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncName(this.buildName(astAnnotation));
		this.nameTextRange = this.buildNameTextRange(astAnnotation);

		this.syncMode(this.buildMode(astAnnotation));
		this.modeTextRange = this.buildModeTextRange(astAnnotation);

		this.syncType(this.buildType(astAnnotation));
		this.typeTextRange = this.buildTypeTextRange(astAnnotation);
	}


	// ********** StoredProcedureParameterAnnotation implementation **********

	// ***** name
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (this.attributeValueHasChanged(this.name, name)) {
			this.name = name;
			this.nameAdapter.setValue(name);
		}
	}

	private void syncName(String astName) {
		String old = this.name;
		this.name = astName;
		this.firePropertyChanged(NAME_PROPERTY, old, astName);
	}

	private String buildName(Annotation astAnnotation) {
		return this.nameAdapter.getValue(astAnnotation);
	}

	public TextRange getNameTextRange() {
		return this.nameTextRange;
	}

	private TextRange buildNameTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.nameDeclarationAdapter, astAnnotation);
	}

	private DeclarationAnnotationElementAdapter<String> buildNameDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(this.daa, JPA2_1.NAMED_STORED_PROCEDURE_PARAMETER__NAME);
	}

	private AnnotationElementAdapter<String> buildNameAdapter() {
		return this.buildStringElementAdapter(this.nameDeclarationAdapter);
	}

	// ***** mode
	public ParameterMode_2_1 getMode() {
		return this.mode;
	}

	public void setMode(ParameterMode_2_1 mode) {
		if (this.attributeValueHasChanged(this.mode, mode)) {
			this.mode = mode;
			this.modeAdapter.setValue(ParameterMode_2_1.toJavaAnnotationValue(mode));
		}
	}

	private void syncMode(ParameterMode_2_1 astValue) {
		ParameterMode_2_1 old = this.mode;
		this.mode = astValue;
		this.firePropertyChanged(MODE_PROPERTY, old, astValue);
	}

	private ParameterMode_2_1 buildMode(Annotation astAnnotation) {
		return ParameterMode_2_1.fromJavaAnnotationValue(this.modeAdapter.getValue(astAnnotation));
	}

	public TextRange getModeTextRange() {
		return this.modeTextRange;
	}

	private TextRange buildModeTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.modeDeclarationAdapter, astAnnotation);
	}

	private DeclarationAnnotationElementAdapter<String> buildModeDeclarationAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(this.daa, JPA2_1.NAMED_STORED_PROCEDURE_PARAMETER__MODE);
	}

	private AnnotationElementAdapter<String> buildModeAdapter() {
		return this.buildStringElementAdapter(this.modeDeclarationAdapter);
	}
	
	// ***** type
	public String getTypeName() {
		return this.type;
	}

	public void setTypeName(String type) {
		if (this.attributeValueHasChanged(this.type, type)) {
			this.type = type;
			this.fqTypeNameStale = true;
			this.typeAdapter.setValue(type);
		}
	}
	
	private void syncType(String astType) {
		if (this.attributeValueHasChanged(this.type, astType)) {
			this.syncType_(astType);
		}
	}

	private void syncType_(String astType) {
		String old = this.type;
		this.type = astType;
		this.fqTypeNameStale = true;
		this.firePropertyChanged(TYPE_PROPERTY, old, astType);
	}

	private String buildType(Annotation astAnnotation) {
		return this.typeAdapter.getValue(astAnnotation);
	}

	public TextRange getTypeTextRange() {
		return this.typeTextRange;
	}

	private TextRange buildTypeTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.typeDeclarationAdapter, astAnnotation);
	}

	private DeclarationAnnotationElementAdapter<String> buildTypeDeclarationAdapter() {
		return buildTypeDeclarationAdapter(this.daa, JPA2_1.NAMED_STORED_PROCEDURE_PARAMETER__TYPE);
	}

	private static DeclarationAnnotationElementAdapter<String> buildTypeDeclarationAdapter(
			DeclarationAnnotationAdapter annotationAdapter,
			String elementName)
	{
		return buildAnnotationElementAdapter(
				annotationAdapter, 
				elementName, 
				TypeStringExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<String> buildAnnotationElementAdapter(
			DeclarationAnnotationAdapter annotationAdapter,
			String elementName,
			ExpressionConverter<String> converter)
	{
		return new ConversionDeclarationAnnotationElementAdapter<String>(
				annotationAdapter,
				elementName,
				converter);
	}

	private AnnotationElementAdapter<String> buildTypeAdapter() {
		return this.buildStringElementAdapter(this.typeDeclarationAdapter);
	}
	
	// ***** fully-qualified type name
	public String getFullyQualifiedTypeName()  {
		if (this.fqTypeNameStale) {
			this.fullyQualifiedTypeName = this.buildFullyQualifiedTypeName();
			this.fqTypeNameStale = false;
		}
		return this.fullyQualifiedTypeName;
	}

	private String buildFullyQualifiedTypeName() {
		return (this.type == null) ? null : this.buildFullyQualifiedTypeName_();
	}

	private String buildFullyQualifiedTypeName_() {
		return ASTTools.resolveFullyQualifiedName(this.typeAdapter.getExpression(this.buildASTRoot()));
	}


	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.name == null) &&
				(this.mode == null) &&
				(this.type == null);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** static methods **********
	private static IndexedDeclarationAnnotationAdapter buildStoredProcedureParameter2_1AnnotationAdapter(
			DeclarationAnnotationAdapter namedStoredProcedureQuery2_1Adapter,
			int index)
	{
		return new NestedIndexedDeclarationAnnotationAdapter(
				namedStoredProcedureQuery2_1Adapter,
				JPA2_1.NAMED_STORED_PROCEDURE_QUERY__PARAMETERS,
				index,
				ANNOTATION_NAME);
	}
}
