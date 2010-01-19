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
import org.eclipse.jpt.core.internal.utility.jdt.MemberAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkConversionValueAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkObjectTypeConverterAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.NestableEclipseLinkConversionValueAnnotation;

/**
 * org.eclipse.persistence.annotations.ConversionValue
 */
final class SourceEclipseLinkConversionValueAnnotation
	extends SourceAnnotation<Member>
	implements NestableEclipseLinkConversionValueAnnotation
{
	private final DeclarationAnnotationElementAdapter<String> dataValueDeclarationAdapter;
	private final AnnotationElementAdapter<String> dataValueAdapter;
	private String dataValue;

	private final DeclarationAnnotationElementAdapter<String> objectValueDeclarationAdapter;
	private final AnnotationElementAdapter<String> objectValueAdapter;
	private String objectValue;


	SourceEclipseLinkConversionValueAnnotation(EclipseLinkObjectTypeConverterAnnotation parent, Member member, IndexedDeclarationAnnotationAdapter idaa) {
		super(parent, member, idaa, new MemberIndexedAnnotationAdapter(member, idaa));
		this.dataValueDeclarationAdapter = this.buildDataValueAdapter(idaa);
		this.dataValueAdapter = this.buildAdapter(this.dataValueDeclarationAdapter);
		this.objectValueDeclarationAdapter = this.buildObjectValueAdapter(idaa);
		this.objectValueAdapter = this.buildAdapter(this.objectValueDeclarationAdapter);
	}

	private AnnotationElementAdapter<String> buildAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new MemberAnnotationElementAdapter<String>(this.member, daea);
	}

	private DeclarationAnnotationElementAdapter<String> buildDataValueAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(declarationAnnotationAdapter, EclipseLink.CONVERSION_VALUE__DATA_VALUE, false);
	}

	private DeclarationAnnotationElementAdapter<String> buildObjectValueAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(declarationAnnotationAdapter, EclipseLink.CONVERSION_VALUE__OBJECT_VALUE, false);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public void initialize(CompilationUnit astRoot) {
		this.dataValue = this.buildDataValue(astRoot);
		this.objectValue = this.buildObjectValue(astRoot);
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncDataValue(this.buildDataValue(astRoot));
		this.syncObjectValue(this.buildObjectValue(astRoot));
	}

	public IndexedAnnotationAdapter getIndexedAnnotationAdapter() {
		return (IndexedAnnotationAdapter) this.annotationAdapter;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.dataValue);
		sb.append("=>"); //$NON-NLS-1$
		sb.append(this.objectValue);
	}


	// ********** ConversionValueAnnotation implementation **********

	// ***** data value
	public String getDataValue() {
		return this.dataValue;
	}

	public void setDataValue(String dataValue) {
		if (this.attributeValueHasChanged(this.dataValue, dataValue)) {
			this.dataValue = dataValue;
			this.dataValueAdapter.setValue(dataValue);
		}
	}

	private void syncDataValue(String astDataValue) {
		String old = this.dataValue;
		this.dataValue = astDataValue;
		this.firePropertyChanged(DATA_VALUE_PROPERTY, old, astDataValue);
	}

	private String buildDataValue(CompilationUnit astRoot) {
		return this.dataValueAdapter.getValue(astRoot);
	}

	public TextRange getDataValueTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.dataValueDeclarationAdapter, astRoot);
	}

	// ***** object value
	public String getObjectValue() {
		return this.objectValue;
	}

	public void setObjectValue(String objectValue) {
		if (this.attributeValueHasChanged(this.objectValue, objectValue)) {
			this.objectValue = objectValue;
			this.objectValueAdapter.setValue(objectValue);
		}
	}

	private void syncObjectValue(String astObjectValue) {
		String old = this.objectValue;
		this.objectValue = astObjectValue;
		this.firePropertyChanged(OBJECT_VALUE_PROPERTY, old, astObjectValue);
	}

	private String buildObjectValue(CompilationUnit astRoot) {
		return this.objectValueAdapter.getValue(astRoot);
	}

	public TextRange getObjectValueTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.objectValueDeclarationAdapter, astRoot);
	}


	//************ NestableAnnotation implementation

	public void initializeFrom(NestableAnnotation oldAnnotation) {
		EclipseLinkConversionValueAnnotation oldConversionValue = (EclipseLinkConversionValueAnnotation) oldAnnotation;
		this.setDataValue(oldConversionValue.getDataValue());
		this.setObjectValue(oldConversionValue.getObjectValue());
	}

	public void moveAnnotation(int newIndex) {
		this.getIndexedAnnotationAdapter().moveAnnotation(newIndex);
	}


	// ********** static methods **********

	static NestableEclipseLinkConversionValueAnnotation createConversionValue(EclipseLinkObjectTypeConverterAnnotation parent, Member member, DeclarationAnnotationAdapter daa, int index) {
		return new SourceEclipseLinkConversionValueAnnotation(parent, member, buildConversionValueAnnotationAdapter(daa, index));
	}

	private static IndexedDeclarationAnnotationAdapter buildConversionValueAnnotationAdapter(DeclarationAnnotationAdapter daa, int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(daa, EclipseLink.OBJECT_TYPE_CONVERTER__CONVERSION_VALUES, index, EclipseLink.CONVERSION_VALUE, false);
	}

}
