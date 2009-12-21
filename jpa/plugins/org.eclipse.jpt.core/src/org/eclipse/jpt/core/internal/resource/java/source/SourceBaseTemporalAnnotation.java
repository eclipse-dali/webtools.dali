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
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.TemporalAnnotation;
import org.eclipse.jpt.core.resource.java.TemporalType;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;

/**
 * Abstract implementation for JPA annotations
 * javax.persistence.Temporal
 * javax.persistence.MapKeyTemporal
 */
public abstract class SourceBaseTemporalAnnotation
	extends SourceAnnotation<Attribute>
	implements TemporalAnnotation
{
	protected final DeclarationAnnotationElementAdapter<String> valueDeclarationAdapter;
	private final AnnotationElementAdapter<String> valueAdapter;
	private TemporalType value;


	protected SourceBaseTemporalAnnotation(JavaResourceNode parent, Attribute attribute, DeclarationAnnotationAdapter daa) {
		super(parent, attribute, daa);
		this.valueDeclarationAdapter = new EnumDeclarationAnnotationElementAdapter(daa, getValueElementName());
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


	// ********** TemporalAnnotation implementation **********

	// ***** value
	public TemporalType getValue() {
		return this.value;
	}

	public void setValue(TemporalType value) {
		if (this.attributeValueHasNotChanged(this.value, value)) {
			return;
		}
		TemporalType old = this.value;
		this.value = value;
		this.valueAdapter.setValue(TemporalType.toJavaAnnotationValue(value));
		this.firePropertyChanged(VALUE_PROPERTY, old, value);
	}

	private TemporalType buildValue(CompilationUnit astRoot) {
		return TemporalType.fromJavaAnnotationValue(this.valueAdapter.getValue(astRoot));
	}

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.valueDeclarationAdapter, astRoot);
	}

	protected abstract String getValueElementName();

}
