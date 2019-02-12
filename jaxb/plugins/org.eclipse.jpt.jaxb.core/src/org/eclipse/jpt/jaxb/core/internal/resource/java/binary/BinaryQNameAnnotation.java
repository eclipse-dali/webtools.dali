/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.resource.java.QNameAnnotation;

public abstract class BinaryQNameAnnotation 
		extends BinaryAnnotation
		implements QNameAnnotation {
	
	public BinaryQNameAnnotation(JavaResourceModel parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
	}
	
	
	// ***** name
	
	public void setName(String name) {
		throw new UnsupportedOperationException();
	}
	
	public TextRange getNameTextRange() {
		throw new UnsupportedOperationException();
	}
	
	public TextRange getNameValidationTextRange() {
		throw new UnsupportedOperationException();
	}
	
	public boolean nameTouches(int pos) {
		throw new UnsupportedOperationException();
	}
	
	
	// ***** namespace
	
	public void setNamespace(String namespace) {
		throw new UnsupportedOperationException();
	}
	
	public TextRange getNamespaceTextRange() {
		throw new UnsupportedOperationException();
	}
	
	public TextRange getNamespaceValidationTextRange() {
		throw new UnsupportedOperationException();
	}
	
	public boolean namespaceTouches(int pos) {
		throw new UnsupportedOperationException();
	}
}
