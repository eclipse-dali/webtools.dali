/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessOrder;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorOrderAnnotation;

/**
 * javax.xml.bind.annotation.XmlAccessorOrder
 */
public final class SourceXmlAccessorOrderAnnotation
	extends SourceAnnotation
	implements XmlAccessorOrderAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JAXB.XML_ACCESSOR_ORDER);

	private static final DeclarationAnnotationElementAdapter<String> VALUE_ADAPTER = buildValueAdapter();
	private final AnnotationElementAdapter<String> valueAdapter;
	private XmlAccessOrder value;
	private TextRange valueTextRange;


	public SourceXmlAccessorOrderAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		super(parent, annotatedElement, DECLARATION_ANNOTATION_ADAPTER);
		this.valueAdapter = new AnnotatedElementAnnotationElementAdapter<String>(annotatedElement, VALUE_ADAPTER);
	}

	public String getAnnotationName() {
		return JAXB.XML_ACCESSOR_ORDER;
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
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}


	//*************** XmlAccessorOrderAnnotation implementation ****************

	// ***** value
	public XmlAccessOrder getValue() {
		return this.value;
	}

	public void setValue(XmlAccessOrder value) {
		if (this.attributeValueHasChanged(this.value, value)) {
			this.value = value;
			this.valueAdapter.setValue(XmlAccessOrder.toJavaAnnotationValue(value));
		}
	}

	private void syncValue(XmlAccessOrder astValue) {
		XmlAccessOrder old = this.value;
		this.value = astValue;
		this.firePropertyChanged(VALUE_PROPERTY, old, astValue);
	}

	private XmlAccessOrder buildValue(Annotation astAnnotation) {
		return XmlAccessOrder.fromJavaAnnotationValue(this.valueAdapter.getValue(astAnnotation));
	}

	public TextRange getValueTextRange() {
		return this.valueTextRange;
	}

	private TextRange buildValueTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(VALUE_ADAPTER, astAnnotation);
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildValueAdapter() {
		//remove the XmlAccessorOrder annotation when the value element is removed.
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_ACCESSOR_ORDER__VALUE);
	}

}
