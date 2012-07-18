/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.jpa.core.resource.java.BaseEnumeratedAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EnumType;

/**
 * <code><ul>
 * <li>javax.persistence.Enumerated
 * <li>javax.persistence.MapKeyEnumerated
 * </ul></code>
 */
public abstract class SourceBaseEnumeratedAnnotation
	extends SourceAnnotation
	implements BaseEnumeratedAnnotation
{
	private final DeclarationAnnotationElementAdapter<String> valueDeclarationAdapter;
	private final AnnotationElementAdapter<String> valueAdapter;
	private EnumType value;
	private TextRange valueTextRange;
	

	protected SourceBaseEnumeratedAnnotation(JavaResourceNode parent, AnnotatedElement element, DeclarationAnnotationAdapter daa) {
		super(parent, element, daa);
		this.valueDeclarationAdapter = new EnumDeclarationAnnotationElementAdapter(daa, getValueElementName());
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


	// ********** EnumeratedAnnotation implementation **********

	// ***** value
	public EnumType getValue() {
		return this.value;
	}
	
	public void setValue(EnumType value) {
		if (this.attributeValueHasChanged(this.value, value)) {
			this.value = value;
			this.valueAdapter.setValue(EnumType.toJavaAnnotationValue(value));
		}
	}
	
	private void syncValue(EnumType astValue) {
		EnumType old = this.value;
		this.value = astValue;
		this.firePropertyChanged(VALUE_PROPERTY, old, astValue);
	}
	
	private EnumType buildValue(Annotation astAnnotation) {
		return EnumType.fromJavaAnnotationValue(this.valueAdapter.getValue(astAnnotation));
	}

	public TextRange getValueTextRange() {
		return this.valueTextRange;
	}
	
	private TextRange buildValueTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.valueDeclarationAdapter, astAnnotation);
	}
	
	protected abstract String getValueElementName();
}
