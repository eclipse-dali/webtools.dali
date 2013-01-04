/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
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
	
	public TextRange getNamespaceTextRange() {
		QNameAnnotation annotation = getAnnotation(false);
		return (annotation == null) ? null : annotation.getNamespaceTextRange();
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
	
	public TextRange getNameTextRange() {
		QNameAnnotation annotation = getAnnotation(false);
		return (annotation == null) ? null : annotation.getNameTextRange();
	}
}