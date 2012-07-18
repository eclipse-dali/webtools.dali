/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.StringExpressionConverter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkBaseConvertAnnotation;

/**
 * <code><ul>
 * <li>org.eclipse.persistence.annotations.Convert
 * <li>org.eclipse.persistence.annotations.MapKeyConvert
 * </ul></code>
 */
public abstract class SourceEclipseLinkBaseConvertAnnotation
	extends SourceAnnotation
	implements EclipseLinkBaseConvertAnnotation
{
	private final DeclarationAnnotationElementAdapter<String> valueDeclarationAdapter;
	private final AnnotationElementAdapter<String> valueAdapter;
	private String value;
	private TextRange valueTextRange;


	protected SourceEclipseLinkBaseConvertAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element, DeclarationAnnotationAdapter daa) {
		super(parent, element, daa);
		this.valueDeclarationAdapter = this.buildValueDeclarationAdapter();
		this.valueAdapter = new AnnotatedElementAnnotationElementAdapter<String>(element, this.valueDeclarationAdapter);
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.value = this.buildValue(astAnnotation);
		this.valueTextRange = this.buildValueTextRange(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncValue(this.buildValue(astAnnotation));
		this.valueTextRange = this.buildValueTextRange(astAnnotation);
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.value == null);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}


	// ********** ConvertAnnotation implementation **********

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

	private String buildValue(Annotation astAnnotation) {
		return this.valueAdapter.getValue(astAnnotation);
	}

	public TextRange getValueTextRange() {
		return this.valueTextRange;
	}

	private TextRange buildValueTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.valueDeclarationAdapter, astAnnotation);
	}

	public boolean valueTouches(int pos) {
		return this.textRangeTouches(this.valueTextRange, pos);
	}

	private DeclarationAnnotationElementAdapter<String> buildValueDeclarationAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String>(this.daa, this.getValueElementName(), StringExpressionConverter.instance());
	}

	protected abstract String getValueElementName();
}
