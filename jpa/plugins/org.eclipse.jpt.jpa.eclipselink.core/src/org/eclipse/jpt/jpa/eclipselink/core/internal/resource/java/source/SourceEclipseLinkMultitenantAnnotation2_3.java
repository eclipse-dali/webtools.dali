/*******************************************************************************
 *  Copyright (c) 2011, 2012  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.BooleanExpressionConverter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.MultitenantAnnotation2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.MultitenantType2_3;

/**
 * org.eclipse.persistence.annotations.Multitenant
 */
public class SourceEclipseLinkMultitenantAnnotation2_3
	extends SourceAnnotation
	implements MultitenantAnnotation2_3
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER 
			= new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> VALUE_ADAPTER = buildValueAdapter();
	private final AnnotationElementAdapter<String> valueAdapter;
	private MultitenantType2_3 value;
	private TextRange valueTextRange;

	private static final DeclarationAnnotationElementAdapter<Boolean> INCLUDE_CRITERIA_ADAPTER = buildIncludeCriteriaAdapter();
	private final AnnotationElementAdapter<Boolean> includeCriteriaAdapter;
	private Boolean includeCriteria;
	private TextRange includeCriteriaTextRange;

	public SourceEclipseLinkMultitenantAnnotation2_3(JavaResourceAnnotatedElement parent, AnnotatedElement element) {
		super(parent, element, DECLARATION_ANNOTATION_ADAPTER);
		this.valueAdapter = new AnnotatedElementAnnotationElementAdapter<String>(element, VALUE_ADAPTER);
		this.includeCriteriaAdapter = new AnnotatedElementAnnotationElementAdapter<Boolean>(element, INCLUDE_CRITERIA_ADAPTER);
	}


	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.value = this.buildValue(astAnnotation);
		this.valueTextRange = this.buildValueTextRange(astAnnotation);
		this.includeCriteria = this.buildIncludeCriteria(astAnnotation);
		this.includeCriteriaTextRange = this.buildIncludeCriteriaTextRange(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncValue(this.buildValue(astAnnotation));
		this.valueTextRange = this.buildValueTextRange(astAnnotation);
		this.syncIncludeCriteria(this.buildIncludeCriteria(astAnnotation));
		this.includeCriteriaTextRange = this.buildIncludeCriteriaTextRange(astAnnotation);
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.value == null) &&
				(this.includeCriteria == null);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}

	// ********** EclipseLinkMultitenantAnnotation implementation **********

	public boolean isSpecified() {
		return true;
	}

	// ***** value
	public MultitenantType2_3 getValue() {
		return this.value;
	}

	public void setValue(MultitenantType2_3 value) {
		if (this.attributeValueHasChanged(this.value, value)) {
			this.value = value;
			this.valueAdapter.setValue(MultitenantType2_3.toJavaAnnotationValue(value));
		}
	}

	private void syncValue(MultitenantType2_3 astValue) {
		MultitenantType2_3 old = this.value;
		this.value = astValue;
		this.firePropertyChanged(VALUE_PROPERTY, old, astValue);
	}

	private MultitenantType2_3 buildValue(Annotation astAnnotation) {
		return MultitenantType2_3.fromJavaAnnotationValue(this.valueAdapter.getValue(astAnnotation));
	}

	public TextRange getValueTextRange() {
		return this.valueTextRange;
	}

	private TextRange buildValueTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(VALUE_ADAPTER, astAnnotation);
	}


	// ***** include criteria
	public Boolean getIncludeCriteria() {
		return this.includeCriteria;
	}

	public void setIncludeCriteria(Boolean includeCriteria) {
		if (this.attributeValueHasChanged(this.includeCriteria, includeCriteria)) {
			this.includeCriteria = includeCriteria;
			this.includeCriteriaAdapter.setValue(includeCriteria);
		}
	}

	private void syncIncludeCriteria(Boolean astIncludeCriteria) {
		Boolean old = this.includeCriteria;
		this.includeCriteria = astIncludeCriteria;
		this.firePropertyChanged(INCLUDE_CRITERIA_PROPERTY, old, astIncludeCriteria);
	}

	private Boolean buildIncludeCriteria(Annotation astAnnotation) {
		return this.includeCriteriaAdapter.getValue(astAnnotation);
	}

	public TextRange getIncludeCriteriaTextRange() {
		return this.includeCriteriaTextRange;
	}

	private TextRange buildIncludeCriteriaTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(INCLUDE_CRITERIA_ADAPTER, astAnnotation);
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildValueAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, EclipseLink.MULTITENANT__VALUE);
	}

	private static DeclarationAnnotationElementAdapter<Boolean> buildIncludeCriteriaAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(DECLARATION_ANNOTATION_ADAPTER, EclipseLink.MULTITENANT__INCLUDE_CRITERIA, BooleanExpressionConverter.instance());
	}

}
