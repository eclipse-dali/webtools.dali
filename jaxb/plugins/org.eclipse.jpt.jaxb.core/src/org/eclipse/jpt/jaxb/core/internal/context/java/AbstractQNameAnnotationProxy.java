/*******************************************************************************
 *  Copyright (c) 2012, 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.context.AbstractQName.ResourceProxy;
import org.eclipse.jpt.jaxb.core.resource.java.QNameAnnotation;

/**
 * represents a {@link QNameAnnotation}
 */
public abstract class AbstractQNameAnnotationProxy
		implements ResourceProxy {
	
	protected abstract QNameAnnotation getAnnotation(boolean createIfNull);
	
	public String getNamespace() {
		QNameAnnotation annotation = getAnnotation(false);
		return annotation == null ? null : annotation.getNamespace();
	}
	
	public void setNamespace(String newSpecifiedNamespace) {
		getAnnotation(true).setNamespace(newSpecifiedNamespace);
	}
	
	public boolean namespaceTouches(int pos) {
		QNameAnnotation annotation = getAnnotation(false);
		return (annotation == null) ? false : annotation.namespaceTouches(pos);
	}
	
	public TextRange getNamespaceValidationTextRange() {
		QNameAnnotation annotation = getAnnotation(false);
		return (annotation == null) ? null : annotation.getNamespaceValidationTextRange();
	}
		
	public String getName() {
		QNameAnnotation annotation = getAnnotation(false);
		return annotation == null ? null : annotation.getName();
	}
	
	public void setName(String newSpecifiedName) {
		getAnnotation(true).setName(newSpecifiedName);
	}
	
	public boolean nameTouches(int pos) {
		QNameAnnotation annotation = getAnnotation(false);
		return (annotation == null) ? false : annotation.nameTouches(pos);
	}
	
	public TextRange getNameValidationTextRange() {
		QNameAnnotation annotation = getAnnotation(false);
		return (annotation == null) ? null : annotation.getNameValidationTextRange();
	}
}