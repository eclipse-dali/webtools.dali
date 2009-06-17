/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt2_0.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.AccessType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt2_0.core.resource.java.AccessAnnotation;

/**
 * javax.persistence.Access
 */
public final class SourceAccessAnnotation
	extends SourceAnnotation<Member>
	implements AccessAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> VALUE_ADAPTER = buildValueAdapter();
	private final AnnotationElementAdapter<String> valueAdapter;
	private AccessType value;


	public SourceAccessAnnotation(JavaResourcePersistentMember parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER);
		this.valueAdapter = new ShortCircuitAnnotationElementAdapter<String>(member, VALUE_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
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


	// ********** AccessAnnotation implementation **********

	// ***** value
	public AccessType getValue() {
		return this.value;
	}

	public void setValue(AccessType value) {
		if (this.attributeValueHasNotChanged(this.value, value)) {
			return;
		}
		AccessType old = this.value;
		this.value = value;
		this.valueAdapter.setValue(AccessType.toJavaAnnotationValue(value));
		this.firePropertyChanged(VALUE_PROPERTY, old, value);
	}

	private AccessType buildValue(CompilationUnit astRoot) {
		return AccessType.fromJavaAnnotationValue(this.valueAdapter.getValue(astRoot));
	}

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(VALUE_ADAPTER, astRoot);
	}

	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildValueAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.ACCESS__VALUE);
	}

}
