/*******************************************************************************
 *  Copyright (c) 2010, 2012  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context;

import java.util.List;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jpa.core.context.MappedSuperclass;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkMappedSuperclassValidator
	extends AbstractEclipseLinkTypeMappingValidator<MappedSuperclass>
{
	public EclipseLinkMappedSuperclassValidator(MappedSuperclass mappedSuperclass, JavaResourceType jrt) {
		super(mappedSuperclass, jrt);
	}


	@Override
	protected void validateType(List<IMessage> messages) {
		if (this.isFinalType()) {
			messages.add(this.buildTypeMessage(JpaValidationMessages.TYPE_MAPPING_FINAL_CLASS));
		}
		super.validateType(messages);
	}
}
