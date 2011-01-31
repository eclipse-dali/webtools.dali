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

import java.util.Map;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.Type;
import org.eclipse.jpt.core.internal.resource.java.source.SourceNamedQueryAnnotation;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.jpa2.resource.java.LockModeType_2_0;
import org.eclipse.jpt.core.jpa2.resource.java.NamedQuery2_0Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;

/**
 * <code>javax.persistence.NamedQuery</code>
 */
public final class SourceNamedQuery2_0Annotation
	extends SourceNamedQueryAnnotation
	implements NamedQuery2_0Annotation
{
	private DeclarationAnnotationElementAdapter<String> lockModeDeclarationAdapter;
	private AnnotationElementAdapter<String> lockModeAdapter;
	private LockModeType_2_0 lockMode;


	public SourceNamedQuery2_0Annotation(JavaResourceNode parent, Type type, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, type, daa, annotationAdapter);
		this.lockModeDeclarationAdapter = this.buildLockModeDeclarationAdapter();
		this.lockModeAdapter = this.buildLockModeAdapter();
	}

	public SourceNamedQuery2_0Annotation(JavaResourceNode parent, Type type) {
		this(parent, type, DECLARATION_ANNOTATION_ADAPTER, new ElementAnnotationAdapter(type, DECLARATION_ANNOTATION_ADAPTER));
	}

	private String getLockModeElementName() {
		return JPA2_0.NAMED_QUERY__LOCK_MODE;
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.lockMode = this.buildLockMode(astRoot);
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		this.syncLockMode(this.buildLockMode(astRoot));
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

	private DeclarationAnnotationElementAdapter<String> buildLockModeDeclarationAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(this.daa, this.getLockModeElementName());
	}

	private AnnotationElementAdapter<String> buildLockModeAdapter() {
		return new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, this.lockModeDeclarationAdapter);
	}
	

	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.lockMode == null);
	}

	@Override
	protected void rebuildAdapters() {
		super.rebuildAdapters();
		this.lockModeDeclarationAdapter = this.buildLockModeDeclarationAdapter();
		this.lockModeAdapter = this.buildLockModeAdapter();
	}

	@Override
	public void storeOn(Map<String, Object> map) {
		super.storeOn(map);
		map.put(LOCK_MODE_PROPERTY, this.lockMode);
		this.lockMode = null;
	}

	@Override
	public void restoreFrom(Map<String, Object> map) {
		super.restoreFrom(map);
		this.setLockMode((LockModeType_2_0) map.get(LOCK_MODE_PROPERTY));
	}


	// ********** static methods **********

	static SourceNamedQuery2_0Annotation createNestedNamedQuery(JavaResourceNode parent, Type type, int index, DeclarationAnnotationAdapter attributeOverridesAdapter) {
		IndexedDeclarationAnnotationAdapter idaa = buildNestedDeclarationAnnotationAdapter(index, attributeOverridesAdapter, ANNOTATION_NAME);
		IndexedAnnotationAdapter annotationAdapter = new ElementIndexedAnnotationAdapter(type, idaa);
		return new SourceNamedQuery2_0Annotation(parent, type, idaa, annotationAdapter);
	}
}
