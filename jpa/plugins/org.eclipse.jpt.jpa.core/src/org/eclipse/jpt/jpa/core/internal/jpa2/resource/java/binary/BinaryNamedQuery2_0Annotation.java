/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.resource.java.binary.BinaryNamedQueryAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.LockModeType_2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.NamedQueryAnnotation2_0;

/**
 *  BinaryNamedQuery2_0Annotation
 */
public final class BinaryNamedQuery2_0Annotation
	extends BinaryNamedQueryAnnotation
	implements NamedQueryAnnotation2_0
{
	private LockModeType_2_0 lockMode;

	public BinaryNamedQuery2_0Annotation(JavaResourceModel parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.lockMode = this.buildLockMode();
	}

	@Override
	public void update() {
		super.update();
		this.setLockMode_(this.buildLockMode());
	}
	
	// ********** NamedQuery2_0Annotation implementation **********

	public LockModeType_2_0 getLockMode() {
		return this.lockMode;
	}

	public void setLockMode(LockModeType_2_0 lockMode) {
		throw new UnsupportedOperationException();
	}

	private void setLockMode_(LockModeType_2_0 lockMode) {
		LockModeType_2_0 old = this.lockMode;
		this.lockMode = lockMode;
		this.firePropertyChanged(LOCK_MODE_PROPERTY, old, lockMode);
	}

	public TextRange getLockModeTextRange() {
		throw new UnsupportedOperationException();
	}

	public boolean lockModeTouches(int pos) {
		throw new UnsupportedOperationException();
	}

	private LockModeType_2_0 buildLockMode() {
		return LockModeType_2_0.fromJavaAnnotationValue(this.getJdtMemberValue(JPA2_0.NAMED_QUERY__LOCK_MODE));
	}
	
}