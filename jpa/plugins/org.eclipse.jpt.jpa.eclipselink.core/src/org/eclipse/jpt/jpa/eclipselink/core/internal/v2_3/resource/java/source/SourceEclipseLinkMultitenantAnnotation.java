/*******************************************************************************
 *  Copyright (c) 2011  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.v2_3.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
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
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java.EclipseLink2_3;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java.EclipseLinkMultitenantAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java.MultitenantType;
import org.eclipse.jpt.jpa.eclipselink.core.v2_4.resource.java.EclipseLink2_4;

/**
 * org.eclipse.persistence.annotations.Multitenant
 */
public class SourceEclipseLinkMultitenantAnnotation
	extends SourceAnnotation
	implements EclipseLinkMultitenantAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER 
			= new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> VALUE_ADAPTER = buildValueAdapter();
	private final AnnotationElementAdapter<String> valueAdapter;
	private MultitenantType value;

	private static final DeclarationAnnotationElementAdapter<Boolean> INCLUDE_CRITERIA_ADAPTER = buildIncludeCriteriaAdapter();
	private final AnnotationElementAdapter<Boolean> includeCriteriaAdapter;
	private Boolean includeCriteria;

	public SourceEclipseLinkMultitenantAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element) {
		super(parent, element, DECLARATION_ANNOTATION_ADAPTER);
		this.valueAdapter = new AnnotatedElementAnnotationElementAdapter<String>(element, VALUE_ADAPTER);
		this.includeCriteriaAdapter = new AnnotatedElementAnnotationElementAdapter<Boolean>(element, INCLUDE_CRITERIA_ADAPTER);
	}


	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public void initialize(CompilationUnit astRoot) {
		this.value = this.buildValue(astRoot);
		this.includeCriteria = this.buildIncludeCriteria(astRoot);
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncValue(this.buildValue(astRoot));
		this.syncIncludeCriteria(this.buildIncludeCriteria(astRoot));
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

	// ********** MultitenantAnnotation implementation **********

	// ***** value
	public MultitenantType getValue() {
		return this.value;
	}

	public void setValue(MultitenantType value) {
		if (this.attributeValueHasChanged(this.value, value)) {
			this.value = value;
			this.valueAdapter.setValue(MultitenantType.toJavaAnnotationValue(value));
		}
	}

	private void syncValue(MultitenantType astValue) {
		MultitenantType old = this.value;
		this.value = astValue;
		this.firePropertyChanged(VALUE_PROPERTY, old, astValue);
	}

	private MultitenantType buildValue(CompilationUnit astRoot) {
		return MultitenantType.fromJavaAnnotationValue(this.valueAdapter.getValue(astRoot));
	}

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(VALUE_ADAPTER, astRoot);
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

	private Boolean buildIncludeCriteria(CompilationUnit astRoot) {
		return this.includeCriteriaAdapter.getValue(astRoot);
	}

	public TextRange getIncludeCriteriaTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(INCLUDE_CRITERIA_ADAPTER, astRoot);
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildValueAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, EclipseLink2_3.MULTITENANT__VALUE);
	}

	private static DeclarationAnnotationElementAdapter<Boolean> buildIncludeCriteriaAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(DECLARATION_ANNOTATION_ADAPTER, EclipseLink2_4.MULTITENANT__INCLUDE_CRITERIA, BooleanExpressionConverter.instance());
	}

}
