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
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ConversionValueAnnotation;

/**
 * <code>org.eclipse.persistence.annotations.ConversionValue</code>
 */
final class EclipseLinkSourceConversionValueAnnotation
	extends SourceAnnotation
	implements ConversionValueAnnotation
{
	private DeclarationAnnotationElementAdapter<String> dataValueDeclarationAdapter;
	private AnnotationElementAdapter<String> dataValueAdapter;
	private String dataValue;
	private TextRange dataValueTextRange;

	private DeclarationAnnotationElementAdapter<String> objectValueDeclarationAdapter;
	private AnnotationElementAdapter<String> objectValueAdapter;
	private String objectValue;
	private TextRange objectValueTextRange;
	
	public static EclipseLinkSourceConversionValueAnnotation buildNestedSourceConversionValueAnnotation(
			JavaResourceModel parent, 
			AnnotatedElement element, 
			IndexedDeclarationAnnotationAdapter idaa) {
		
		return new EclipseLinkSourceConversionValueAnnotation(parent, element, idaa);
	}

	private EclipseLinkSourceConversionValueAnnotation(JavaResourceModel parent, AnnotatedElement element, IndexedDeclarationAnnotationAdapter idaa) {
		super(parent, element, idaa, new ElementIndexedAnnotationAdapter(element, idaa));
		this.dataValueDeclarationAdapter = this.buildDataValueDeclarationAdapter();
		this.dataValueAdapter = this.buildDataValueAdapter();
		this.objectValueDeclarationAdapter = this.buildObjectValueDeclarationAdapter();
		this.objectValueAdapter = this.buildObjectValueAdapter();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.dataValue = this.buildDataValue(astAnnotation);
		this.dataValueTextRange = this.buildDataValueTextRange(astAnnotation);

		this.objectValue = this.buildObjectValue(astAnnotation);
		this.objectValueTextRange = this.buildObjectValueTextRange(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncDataValue(this.buildDataValue(astAnnotation));
		this.dataValueTextRange = this.buildDataValueTextRange(astAnnotation);

		this.syncObjectValue(this.buildObjectValue(astAnnotation));
		this.objectValueTextRange = this.buildObjectValueTextRange(astAnnotation);
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

	private String buildDataValue(Annotation astAnnotation) {
		return this.dataValueAdapter.getValue(astAnnotation);
	}

	public TextRange getDataValueTextRange() {
		return this.dataValueTextRange;
	}

	private TextRange buildDataValueTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.dataValueDeclarationAdapter, astAnnotation);
	}

	private DeclarationAnnotationElementAdapter<String> buildDataValueDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(this.daa, EclipseLink.CONVERSION_VALUE__DATA_VALUE);
	}

	private AnnotationElementAdapter<String> buildDataValueAdapter() {
		return this.buildStringElementAdapter(this.dataValueDeclarationAdapter);
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

	private String buildObjectValue(Annotation astAnnotation) {
		return this.objectValueAdapter.getValue(astAnnotation);
	}

	public TextRange getObjectValueTextRange() {
		return this.objectValueTextRange;
	}

	private TextRange buildObjectValueTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.objectValueDeclarationAdapter, astAnnotation);
	}

	private DeclarationAnnotationElementAdapter<String> buildObjectValueDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(this.daa, EclipseLink.CONVERSION_VALUE__OBJECT_VALUE);
	}

	private AnnotationElementAdapter<String> buildObjectValueAdapter() {
		return this.buildStringElementAdapter(this.objectValueDeclarationAdapter);
	}


	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.dataValue == null) &&
				(this.objectValue == null);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.dataValue);
		sb.append("=>"); //$NON-NLS-1$
		sb.append(this.objectValue);
	}
}
