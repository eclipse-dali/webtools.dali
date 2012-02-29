/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import java.util.List;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.AbstractOrmEmbeddedIdMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkAccessType;
import org.eclipse.jpt.jpa.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddedId;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * <code>orm.xml</code> embedded mapping
 */
public class OrmEclipseLinkEmbeddedIdMapping
	extends AbstractOrmEmbeddedIdMapping<XmlEmbeddedId>
{

	public OrmEclipseLinkEmbeddedIdMapping(OrmPersistentAttribute parent, XmlEmbeddedId xmlMapping) {
		super(parent, xmlMapping);
	}


	// ********** attribute type **********

	@Override
	protected String buildSpecifiedAttributeType() {
		return this.xmlAttributeMapping.getAttributeType();
	}

	@Override
	protected void setSpecifiedAttributeTypeInXml(String attributeType) {
		this.xmlAttributeMapping.setAttributeType(attributeType);
	}

	// ********** validation **********

	@Override
	protected boolean validateTargetEmbeddable(List<IMessage> messages) {
		if (this.isVirtualAccess()) {
			if (StringTools.stringIsEmpty(this.getAttributeType())) {
				messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						EclipseLinkJpaValidationMessages.VIRTUAL_ATTRIBUTE_NO_ATTRIBUTE_TYPE_SPECIFIED,
						new String[] {this.getName()},
						this,
						this.getAttributeTypeTextRange()
					)
				);
				return false;
			}
			if (this.getResolvedAttributeType() == null) {
				IType jdtType = JDTTools.findType(this.getJavaProject(), this.getFullyQualifiedAttributeType());
				if (jdtType == null) {
					messages.add(
						DefaultEclipseLinkJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							EclipseLinkJpaValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_TYPE_DOES_NOT_EXIST,
							new String[] {this.getFullyQualifiedAttributeType()},
							this,
							this.getAttributeTypeTextRange()
						)
					);
					return false;
				}
			}
		}
		return super.validateTargetEmbeddable(messages);
	}

	protected boolean isVirtualAccess() {
		return this.getPersistentAttribute().getAccess() == EclipseLinkAccessType.VIRTUAL;
	}

	@Override
	protected TextRange getAttributeTypeTextRange() {
		return this.getValidationTextRange(this.xmlAttributeMapping.getAttributeTypeTextRange());
	}
}