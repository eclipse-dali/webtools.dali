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
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.CustomizerAnnotation;

/**
 * <code>org.eclipse.persistence.annotations.Customizer</code>
 */
public final class EclipseLinkSourceCustomizerAnnotation
	extends SourceAnnotation
	implements CustomizerAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> VALUE_ADAPTER = buildValueAdapter();
	private final AnnotationElementAdapter<String> valueAdapter;
	private String value;
	private TextRange valueTextRange;

	/**
	 * @see org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceIdClassAnnotation#fullyQualifiedClassName
	 */
	private String fullyQualifiedCustomizerClassName;
	// we need a flag since the f-q name can be null
	private boolean fqCustomizerClassNameStale = true;


	public EclipseLinkSourceCustomizerAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element) {
		super(parent, element, DECLARATION_ANNOTATION_ADAPTER);
		this.valueAdapter = new AnnotatedElementAnnotationElementAdapter<String>(element, VALUE_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
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


	// ********** CustomizerAnnotation implementation **********

	// ***** value
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		if (this.attributeValueHasChanged(this.value, value)) {
			this.value = value;
			this.fqCustomizerClassNameStale = true;
			this.valueAdapter.setValue(value);
		}
	}

	private void syncValue(String astValue) {
		if (this.attributeValueHasChanged(this.value, astValue)) {
			this.syncValue_(astValue);
		}
	}

	private void syncValue_(String astValue) {
		String old = this.value;
		this.value = astValue;
		this.fqCustomizerClassNameStale = true;
		this.firePropertyChanged(VALUE_PROPERTY, old, astValue);
	}

	private String buildValue(Annotation astAnnotation) {
		return this.valueAdapter.getValue(astAnnotation);
	}

	public TextRange getValueTextRange() {
		return this.valueTextRange;
	}

	private TextRange buildValueTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(VALUE_ADAPTER, astAnnotation);
	}


	// ***** fully-qualified customizer class name
	public String getFullyQualifiedCustomizerClassName() {
		if (this.fqCustomizerClassNameStale) {
			this.fullyQualifiedCustomizerClassName = this.buildFullyQualifiedCustomizerClassName();
			this.fqCustomizerClassNameStale = false;
		}
		return this.fullyQualifiedCustomizerClassName;
	}

	private String buildFullyQualifiedCustomizerClassName() {
		return (this.value == null) ? null : this.buildFullyQualifiedCustomizerClassName_();
	}

	private String buildFullyQualifiedCustomizerClassName_() {
		return ASTTools.resolveFullyQualifiedName(this.valueAdapter.getExpression(this.buildASTRoot()));
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildValueAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String>(DECLARATION_ANNOTATION_ADAPTER, EclipseLink.CUSTOMIZER__VALUE, SimpleTypeStringExpressionConverter.instance());
	}
}
