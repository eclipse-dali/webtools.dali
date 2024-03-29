/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorTypeAnnotation;

/**
 * javax.xml.bind.annotation.XmlAccessorType
 */
public final class SourceXmlAccessorTypeAnnotation
	extends SourceAnnotation
	implements XmlAccessorTypeAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JAXB.XML_ACCESSOR_TYPE);

	private static final DeclarationAnnotationElementAdapter<String> VALUE_ADAPTER = buildValueAdapter();
	private final AnnotationElementAdapter<String> valueAdapter;
	private XmlAccessType value;
	private TextRange valueTextRange;


	public SourceXmlAccessorTypeAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		super(parent, annotatedElement, DECLARATION_ANNOTATION_ADAPTER);
		this.valueAdapter = new AnnotatedElementAnnotationElementAdapter<String>(annotatedElement, VALUE_ADAPTER);
	}

	public String getAnnotationName() {
		return JAXB.XML_ACCESSOR_TYPE;
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


	//*************** XmlAccessorTypeAnnotation implementation ****************

	// ***** value
	public XmlAccessType getValue() {
		return this.value;
	}

	public void setValue(XmlAccessType value) {
		if (ObjectTools.notEquals(this.value, value)) {
			this.value = value;
			this.valueAdapter.setValue(XmlAccessType.toJavaAnnotationValue(value));
		}
	}

	private void syncValue(XmlAccessType astValue) {
		XmlAccessType old = this.value;
		this.value = astValue;
		this.firePropertyChanged(VALUE_PROPERTY, old, astValue);
	}

	private XmlAccessType buildValue(Annotation astAnnotation) {
		return XmlAccessType.fromJavaAnnotationValue(this.valueAdapter.getValue(astAnnotation));
	}

	public TextRange getValueTextRange() {
		return this.valueTextRange;
	}

	private TextRange buildValueTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(VALUE_ADAPTER, astAnnotation);
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildValueAdapter() {
		//remove the XmlAccessorType annotation when the value element is removed.
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_ACCESSOR_TYPE__VALUE);
	}

}
