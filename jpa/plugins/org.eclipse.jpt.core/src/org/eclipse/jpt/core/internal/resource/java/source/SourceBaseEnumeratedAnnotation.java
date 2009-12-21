/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.resource.java.EnumType;
import org.eclipse.jpt.core.resource.java.EnumeratedAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;

/**
 * javax.persistence.Enumerated
 * javax.persistence.MapKeyEnumerated
 */
public abstract class SourceBaseEnumeratedAnnotation
	extends SourceAnnotation<Attribute>
	implements EnumeratedAnnotation
{
	private final DeclarationAnnotationElementAdapter<String> valueDeclarationAdapter;
	private final AnnotationElementAdapter<String> valueAdapter;
	private EnumType value;
	

	protected SourceBaseEnumeratedAnnotation(JavaResourceNode parent, Attribute attribute, DeclarationAnnotationAdapter daa) {
		super(parent, attribute, daa);
		this.valueDeclarationAdapter = new EnumDeclarationAnnotationElementAdapter(daa, getValueElementName(), false);
		this.valueAdapter = new ShortCircuitAnnotationElementAdapter<String>(attribute, this.valueDeclarationAdapter);
	}
	
	public void initialize(CompilationUnit astRoot) {
		this.value = this.buildValue(astRoot);
	}

	public void update(CompilationUnit astRoot) {
		this.setValue(this.buildValue(astRoot));
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
		if (this.attributeValueHasNotChanged(this.value, value)) {
			return;
		}
		EnumType old = this.value;
		this.value = value;
		this.valueAdapter.setValue(EnumType.toJavaAnnotationValue(value));
		this.firePropertyChanged(VALUE_PROPERTY, old, value);
	}
	
	private EnumType buildValue(CompilationUnit astRoot) {
		return EnumType.fromJavaAnnotationValue(this.valueAdapter.getValue(astRoot));
	}

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.valueDeclarationAdapter, astRoot);
	}
	
	protected abstract String getValueElementName();

}
