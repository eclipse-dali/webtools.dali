/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.jaxb.core.context.XmlNamedNodeMapping;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlID;
import org.eclipse.jpt.jaxb.core.resource.java.XmlIDAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class ELJavaXmlID
		extends GenericJavaXmlID {
	
	public ELJavaXmlID(XmlNamedNodeMapping parent, XmlIDAnnotation annotation) {
		super(parent, annotation);
	}
	
	
	@Override
	protected void validateAttributeType(List<IMessage> messages) {
		// no op - MOXy does not require String attribute types
	}
}
