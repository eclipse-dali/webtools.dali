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
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlMimeTypeAnnotation;

/**
 * javax.xml.bind.annotation.XmlMimeType
 */
public final class SourceXmlMimeTypeAnnotation
	extends SourceAnnotation
	implements XmlMimeTypeAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JAXB.XML_MIME_TYPE);

	private static final DeclarationAnnotationElementAdapter<String> VALUE_ADAPTER = buildValueAdapter();
	private final AnnotationElementAdapter<String> valueAdapter;
	private String value;
	private TextRange valueTextRange;

	public SourceXmlMimeTypeAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		super(parent, annotatedElement, DECLARATION_ANNOTATION_ADAPTER);
		this.valueAdapter = this.buildAnnotationElementAdapter(VALUE_ADAPTER);
	}

	protected AnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, daea);
	}

	public String getAnnotationName() {
		return JAXB.XML_MIME_TYPE;
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


	// ********** XmlMimeTypeAnnotation implementation **********
	// ***** value
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		if (ObjectTools.notEquals(this.value, value)) {
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
		return this.getElementTextRange(VALUE_ADAPTER, astAnnotation);
	}


	//*********** static methods ****************

	private static DeclarationAnnotationElementAdapter<String> buildValueAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_MIME_TYPE__VALUE);
	}
}
