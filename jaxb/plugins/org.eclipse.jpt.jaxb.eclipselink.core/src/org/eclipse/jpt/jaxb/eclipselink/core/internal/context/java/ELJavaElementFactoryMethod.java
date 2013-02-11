/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.jaxb.core.context.XmlRegistry;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaElementFactoryMethod;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.validation.ELJaxbValidationMessageBuilder;
import org.eclipse.jpt.jaxb.eclipselink.core.validation.JptJaxbEclipseLinkCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class ELJavaElementFactoryMethod
		extends GenericJavaElementFactoryMethod {
	
	public ELJavaElementFactoryMethod(XmlRegistry parent, JavaResourceMethod resourceMethod) {
		super(parent, resourceMethod);
	}
	
	
	@Override
	protected void validateMethodReturnType(
			JavaResourceMethod resourceMethod, List<IMessage> messages) {
		
		if (! resourceMethod.getTypeBinding().isSubTypeOf(JAXB.JAXB_ELEMENT)) {
			messages.add(
					ELJaxbValidationMessageBuilder.buildMessage(
							IMessage.HIGH_SEVERITY,
							JptJaxbEclipseLinkCoreValidationMessages.XML_ELEMENT_DECL__INVALID_METHOD_SIGNATURE_RETURN_TYPE,
							this,
							getValidationTextRange()));
		}
	}
}
