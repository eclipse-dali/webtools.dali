/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.List;
import org.eclipse.jpt.core.internal.IPersistentAttribute;
import org.eclipse.jpt.core.internal.content.orm.XmlOneToOne;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class XmlOneToOneContext
	extends XmlSingleRelationshipMappingContext
{
	public XmlOneToOneContext(IContext parentContext, XmlOneToOne mapping) {
		super(parentContext, mapping);
	}
	
	protected XmlOneToOne getMapping() {
		return (XmlOneToOne) super.attributeMapping();
	}
	
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		
		if (getMapping().getMappedBy() != null) {
			addMappedByMessages(messages);
		}
	}
	
	protected void addMappedByMessages(List<IMessage> messages) {
		XmlOneToOne mapping = getMapping();
		String mappedBy = mapping.getMappedBy();
		IEntity targetEntity = mapping.getResolvedTargetEntity();
		
		if (targetEntity == null) {
			// already have validation messages for that
			return;
		}
		
		IPersistentAttribute attribute = targetEntity.getPersistentType().resolveAttribute(mappedBy);
		
		if (attribute == null) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.MAPPING_UNRESOLVED_MAPPED_BY,
						new String[] {mappedBy}, 
						mapping, mapping.getMappedByTextRange())
				);
			return;
		}
		
		if (! mapping.mappedByIsValid(attribute.getMapping())) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.MAPPING_INVALID_MAPPED_BY,
						new String[] {mappedBy}, 
						mapping, mapping.getMappedByTextRange())
				);
		}
	}
}
