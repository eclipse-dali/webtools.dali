/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;
import org.eclipse.jpt.jaxb.core.internal.context.java.AbstractJavaContextNode;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlKey;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlKeyAnnotation;


public class ELJavaXmlKey
		extends AbstractJavaContextNode
		implements ELXmlKey {
	
	protected final Context context;
	
	
	public ELJavaXmlKey(JavaContextNode parent, Context context) {
		super(parent);
		this.context = context;
	}
	
	
	@Override
	public TextRange getValidationTextRange() {
		return this.context.getAnnotation().getTextRange();
	}
	
	
	public interface Context {
		
		XmlKeyAnnotation getAnnotation();
	}
}
