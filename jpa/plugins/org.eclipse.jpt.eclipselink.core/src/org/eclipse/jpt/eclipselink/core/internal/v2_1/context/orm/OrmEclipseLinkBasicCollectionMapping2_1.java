/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_1.context.orm;

import java.util.List;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.AbstractOrmEclipseLinkBasicCollectionMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicCollection;
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
		if ( ! this.isVirtual()) { // if virtual the underlying java annotation will have a java deprecation warning
			messages.add(
				DefaultEclipseLinkJpaValidationMessages.buildMessage(
					IMessage.NORMAL_SEVERITY,
					EclipseLinkJpaValidationMessages.BASIC_COLLECTION_MAPPING_DEPRECATED,
					new String[] {this.getName()},
					this,
					this.getValidationTextRange()
				)
			);
		}
	}
}
