/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.MemberAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.OrderByAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;

/**
 * javax.persistence.OrderBy
 */
public final class SourceOrderByAnnotation
	extends SourceAnnotation<Attribute>
	implements OrderByAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> VALUE_ADAPTER = buildValueAdapter();
	private final AnnotationElementAdapter<String> valueAdapter;
	private String value;


	public SourceOrderByAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
		this.valueAdapter = new MemberAnnotationElementAdapter<String>(attribute, VALUE_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public void initialize(CompilationUnit astRoot) {
		this.value = this.buildValue(astRoot);
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncValue(this.buildValue(astRoot));
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}


	// ********** OrderByAnnotation implementation **********

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

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(VALUE_ADAPTER, astRoot);
	}

	private String buildValue(CompilationUnit astRoot) {
		return this.valueAdapter.getValue(astRoot);
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildValueAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.ORDER_BY__VALUE, false);
	}

}
