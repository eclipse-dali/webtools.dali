/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.source.SourceNamedQueryAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.MemberAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.jpa2.resource.java.LockModeType_2_0;
import org.eclipse.jpt.core.jpa2.resource.java.NamedQuery2_0Annotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Type;

/**
 * javax.persistence.NamedQuery
 */
public final class SourceNamedQuery2_0Annotation
	extends SourceNamedQueryAnnotation
	implements NamedQuery2_0Annotation
{
	private final DeclarationAnnotationElementAdapter<String> lockModeDeclarationAdapter;
	private final AnnotationElementAdapter<String> lockModeAdapter;
	private LockModeType_2_0 lockMode;

	// ********** constructors **********
	public SourceNamedQuery2_0Annotation(JavaResourceNode parent, Type type, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, type, daa, annotationAdapter);
		this.lockModeDeclarationAdapter = this.buildLockModeTypeAdapter(daa);
		this.lockModeAdapter = new MemberAnnotationElementAdapter<String>(type, this.lockModeDeclarationAdapter);
	}
	
	public SourceNamedQuery2_0Annotation(JavaResourceNode parent, Type type) {
		this(parent, type, DECLARATION_ANNOTATION_ADAPTER, new MemberAnnotationAdapter(type, DECLARATION_ANNOTATION_ADAPTER));
	}
	
	private DeclarationAnnotationElementAdapter<String> buildLockModeTypeAdapter(DeclarationAnnotationAdapter daAdapter) {
		return new EnumDeclarationAnnotationElementAdapter(daAdapter, this.getLockModeElementName());
	}

	private String getLockModeElementName() {
		return JPA2_0.NAMED_QUERY__LOCK_MODE;
	}

	// ********** initialize/update **********
	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.lockMode = this.buildLockMode(astRoot);
	}

	@Override
	public void initializeFrom(NestableAnnotation oldAnnotation) {
		super.initializeFrom(oldAnnotation);
		
		NamedQuery2_0Annotation old = (NamedQuery2_0Annotation) oldAnnotation;
		this.setLockMode(old.getLockMode());
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		this.syncLockMode(this.buildLockMode(astRoot));
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.lockMode);
	}

	// ********** NamedQuery2_0Annotation implementation **********

	public LockModeType_2_0 getLockMode() {
		return this.lockMode;
	}

	public void setLockMode(LockModeType_2_0 lockMode) {
		if (this.attributeValueHasChanged(this.lockMode, lockMode)) {
			this.lockMode = lockMode;
			this.lockModeAdapter.setValue(LockModeType_2_0.toJavaAnnotationValue(lockMode));
		}
	}

	private void syncLockMode(LockModeType_2_0 astLockMode) {
		LockModeType_2_0 old = this.lockMode;
		this.lockMode = astLockMode;
		this.firePropertyChanged(LOCK_MODE_PROPERTY, old, astLockMode);
	}

	private LockModeType_2_0 buildLockMode(CompilationUnit astRoot) {
		return LockModeType_2_0.fromJavaAnnotationValue(this.lockModeAdapter.getValue(astRoot));
	}

	public TextRange getLockModeTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.lockModeDeclarationAdapter, astRoot);
	}

	public boolean lockModeTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.lockModeDeclarationAdapter, pos, astRoot);
	}


	// ********** static methods **********

	static SourceNamedQuery2_0Annotation createNestedNamedQuery(JavaResourceNode parent, Type type, int index, DeclarationAnnotationAdapter attributeOverridesAdapter) {
		IndexedDeclarationAnnotationAdapter idaa = buildNestedDeclarationAnnotationAdapter(index, attributeOverridesAdapter);
		IndexedAnnotationAdapter annotationAdapter = new MemberIndexedAnnotationAdapter(type, idaa);

		return new SourceNamedQuery2_0Annotation(parent, type, idaa, annotationAdapter);
	}

	private static IndexedDeclarationAnnotationAdapter buildNestedDeclarationAnnotationAdapter(int index, DeclarationAnnotationAdapter namedQueriesAdapter) {
		return new NestedIndexedDeclarationAnnotationAdapter(namedQueriesAdapter, index, JPA.NAMED_QUERY);
	}

}
