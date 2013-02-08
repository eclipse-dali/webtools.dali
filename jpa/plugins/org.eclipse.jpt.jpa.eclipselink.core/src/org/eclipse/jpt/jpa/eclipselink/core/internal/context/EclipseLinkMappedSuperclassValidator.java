/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context;

import java.util.List;
import org.eclipse.jpt.jpa.core.context.MappedSuperclass;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkMappedSuperclassValidator
	extends AbstractEclipseLinkTypeMappingValidator<MappedSuperclass>
{
	public EclipseLinkMappedSuperclassValidator(MappedSuperclass mappedSuperclass) {
		super(mappedSuperclass);
	}

	@Override
	protected void validateType(List<IMessage> messages) {
		if (this.getJavaResourceType().isFinal()) {
			messages.add(this.buildTypeMessage(JptJpaCoreValidationMessages.TYPE_MAPPING_FINAL_CLASS));
		}
		super.validateType(messages);
	}
}
