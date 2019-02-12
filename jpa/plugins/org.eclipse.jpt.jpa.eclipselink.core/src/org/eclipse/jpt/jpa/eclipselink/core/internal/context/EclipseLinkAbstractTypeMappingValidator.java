/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context;

import java.util.List;
import org.eclipse.jpt.common.core.internal.utility.ValidationMessageTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AbstractTypeMappingValidator;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.validation.JptJpaEclipseLinkCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public abstract class EclipseLinkAbstractTypeMappingValidator<T extends TypeMapping>
	extends AbstractTypeMappingValidator<T>
{
	protected EclipseLinkAbstractTypeMappingValidator(T typeMapping) {
		super(typeMapping);
	}


	@Override
	protected void validateType(List<IMessage> messages) {
		JavaResourceType jrt = this.getJavaResourceType();
		if (jrt.getTypeBinding().isMemberTypeDeclaration() && ! jrt.isStatic()) {
			messages.add(this.buildEclipseLinkTypeMessage(JptJpaEclipseLinkCoreValidationMessages.TYPE_MAPPING_MEMBER_CLASS_NOT_STATIC));
		}
		if ( ! jrt.hasNoArgConstructor()) {
			messages.add(this.buildTypeMessage(JptJpaCoreValidationMessages.TYPE_MAPPING_CLASS_MISSING_NO_ARG_CONSTRUCTOR));
		}
	}

	protected IMessage buildEclipseLinkTypeMessage(ValidationMessage validationMessage) {
		return ValidationMessageTools.buildValidationMessage(
				this.typeMapping.getResource(),
				this.typeMapping.getValidationTextRange(),
				validationMessage,
				this.typeMapping.getName()
			);
	}
}
