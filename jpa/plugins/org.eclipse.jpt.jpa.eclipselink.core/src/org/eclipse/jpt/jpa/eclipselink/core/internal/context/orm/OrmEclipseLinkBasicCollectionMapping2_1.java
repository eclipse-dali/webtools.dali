/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import java.util.List;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasicCollection;
import org.eclipse.jpt.jpa.eclipselink.core.validation.JptJpaEclipseLinkCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OrmEclipseLinkBasicCollectionMapping2_1
	extends AbstractOrmEclipseLinkBasicCollectionMapping
{
	public OrmEclipseLinkBasicCollectionMapping2_1(OrmPersistentAttribute parent, XmlBasicCollection xmlMapping) {
		super(parent, xmlMapping);
	}

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		messages.add(
			this.buildValidationMessage(
				this.getValidationTextRange(),
				IMessage.NORMAL_SEVERITY,
				JptJpaEclipseLinkCoreValidationMessages.BASIC_COLLECTION_MAPPING_DEPRECATED,
				this.getName()
			)
		);
	}
}
