/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import java.util.List;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.internal.context.TypeMappingTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class GenericTypeMappingValidator
	extends AbstractTypeMappingValidator<TypeMapping>
{
	public GenericTypeMappingValidator(TypeMapping typeMapping, JavaResourceType jrt, TypeMappingTextRangeResolver textRangeResolver) {
		super(typeMapping, jrt, textRangeResolver);
	}

	@Override
	protected void validateType(List<IMessage> messages) {
		if (this.isFinalType()) {
			messages.add(this.buildTypeMessage(JpaValidationMessages.TYPE_MAPPING_FINAL_CLASS));
		}
		if (this.isMemberType()) {
			messages.add(this.buildTypeMessage(JpaValidationMessages.TYPE_MAPPING_MEMBER_CLASS));
		}
		if (this.hasNoArgConstructor()) {
			if (this.hasPrivateNoArgConstructor()) {
				messages.add(this.buildTypeMessage(JpaValidationMessages.TYPE_MAPPING_CLASS_PRIVATE_NO_ARG_CONSTRUCTOR));
			}
		}
		else {
			messages.add(this.buildTypeMessage(JpaValidationMessages.TYPE_MAPPING_CLASS_MISSING_NO_ARG_CONSTRUCTOR));
		}
	}
}
