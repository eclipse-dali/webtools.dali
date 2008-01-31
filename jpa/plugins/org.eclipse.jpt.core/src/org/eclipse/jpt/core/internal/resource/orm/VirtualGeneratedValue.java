/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm;

import org.eclipse.jpt.core.internal.context.java.IJavaGeneratedValue;
import org.eclipse.jpt.core.internal.resource.common.JpaEObject;

public class VirtualGeneratedValue extends JpaEObject implements GeneratedValue
{
	IJavaGeneratedValue javaGeneratedValue;

	protected boolean metadataComplete;
	
		
	public VirtualGeneratedValue(IJavaGeneratedValue javaGeneratedValue, boolean metadataComplete) {
		super();
		this.javaGeneratedValue = javaGeneratedValue;
		this.metadataComplete = metadataComplete;
	}


	public String getGenerator() {
		if (this.metadataComplete) {
			return null;
		}
		return this.javaGeneratedValue.getGenerator();
	}

	public GenerationType getStrategy() {
		if (this.metadataComplete) {
			return null;
		}
		return org.eclipse.jpt.core.internal.context.base.GenerationType.toOrmResourceModel(this.javaGeneratedValue.getStrategy());
	}

	public void setGenerator(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public void setStrategy(GenerationType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	

	public void update(IJavaGeneratedValue javaGeneratedValue) {
		this.javaGeneratedValue = javaGeneratedValue;
	}


}
