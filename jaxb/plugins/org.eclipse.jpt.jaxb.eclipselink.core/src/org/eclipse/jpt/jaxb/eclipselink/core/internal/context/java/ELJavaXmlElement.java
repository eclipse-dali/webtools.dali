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

import java.util.List;
import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlElement;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class ELJavaXmlElement
		extends GenericJavaXmlElement {
	
	public ELJavaXmlElement(JavaContextNode parent, Context context) {
		super(parent, context);
	}
	
	
	@Override
	protected Context getContext() {
		return (Context) super.getContext();
	}
	
	@Override
	protected void validateQName(List<IMessage> messages, IReporter reporter) {
		if (! getContext().hasXmlPath()) {
			super.validateQName(messages, reporter);
		}
	}
	
	
	public interface Context
			extends GenericJavaXmlElement.Context {
		
		boolean hasXmlPath();
	}
}
