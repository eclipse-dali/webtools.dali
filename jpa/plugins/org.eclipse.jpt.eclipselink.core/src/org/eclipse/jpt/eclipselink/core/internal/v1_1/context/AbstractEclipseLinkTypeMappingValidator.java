/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v1_1.context;

import java.util.List;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.internal.context.TypeMappingTextRangeResolver;
import org.eclipse.jpt.core.internal.jpa1.context.AbstractTypeMappingValidator;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public abstract class AbstractEclipseLinkTypeMappingValidator<T extends TypeMapping>
	extends AbstractTypeMappingValidator<T>
{
	public AbstractEclipseLinkTypeMappingValidator(T typeMapping, JavaResourcePersistentType jrpt, TypeMappingTextRangeResolver textRangeResolver) {
		super(typeMapping, jrpt, textRangeResolver);
	}


	@Override
	protected void validateType(List<IMessage> messages) {
		if (this.isMemberType() && !this.isStaticType()) {
			messages.add(this.buildEclipseLinkTypeMessage(EclipseLinkJpaValidationMessages.TYPE_MAPPING_MEMBER_CLASS_NOT_STATIC));
		}
		if (!this.hasNoArgConstructor()) {
			messages.add(this.buildTypeMessage(JpaValidationMessages.TYPE_MAPPING_CLASS_MISSING_NO_ARG_CONSTRUCTOR));
		}
	}

	protected IMessage buildEclipseLinkTypeMessage(String msgID) {
		return DefaultEclipseLinkJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				msgID,
				new String[] {this.typeMapping.getName()},
				this.typeMapping,
				this.textRangeResolver.getTypeMappingTextRange()
			);
	}
}
