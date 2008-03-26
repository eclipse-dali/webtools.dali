/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.java.JavaGeneratedValue;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.orm.GenerationType;
import org.eclipse.jpt.core.resource.orm.XmlGeneratedValue;
import org.eclipse.jpt.core.utility.TextRange;

public class VirtualXmlGeneratedValue extends AbstractJpaEObject implements XmlGeneratedValue
{
	JavaGeneratedValue javaGeneratedValue;

	protected boolean metadataComplete;
	
		
	public VirtualXmlGeneratedValue(JavaGeneratedValue javaGeneratedValue, boolean metadataComplete) {
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
		return org.eclipse.jpt.core.context.GenerationType.toOrmResourceModel(this.javaGeneratedValue.getStrategy());
	}

	public void setGenerator(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public void setStrategy(GenerationType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public TextRange getGeneratorTextRange() {
		return null;
	}

	public void update(JavaGeneratedValue javaGeneratedValue) {
		this.javaGeneratedValue = javaGeneratedValue;
	}
}
