/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.List;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmTransientMapping;
import org.eclipse.jpt.core.resource.orm.AbstractTypeMapping;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlTransient;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class GenericOrmTransientMapping extends AbstractOrmAttributeMapping<XmlTransient> implements OrmTransientMapping
{
	
	public GenericOrmTransientMapping(OrmPersistentAttribute parent) {
		super(parent);
	}

	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmTransientMapping(this);
	}

	public int xmlSequence() {
		return 8;
	}

	public String getKey() {
		return MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY;
	}

	public XmlTransient addToResourceModel(AbstractTypeMapping typeMapping) {
		XmlTransient transientResource = OrmFactory.eINSTANCE.createTransientImpl();
		persistentAttribute().initialize(transientResource);
		typeMapping.getAttributes().getTransients().add(transientResource);
		return transientResource;
	}
	
	public void removeFromResourceModel(AbstractTypeMapping typeMapping) {
		typeMapping.getAttributes().getTransients().remove(this.attributeMapping());
		if (typeMapping.getAttributes().isAllFeaturesUnset()) {
			typeMapping.setAttributes(null);
		}
	}
	
	@Override
	public void initialize(XmlTransient transientResource) {
		super.initialize(transientResource);
	}
	
	@Override
	public void update(XmlTransient transientResource) {
		super.update(transientResource);
	}
	
	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
//		addModifierMessages(messages);
	}
	
//	protected void addModifierMessages(List<IMessage> messages) {
//		OrmPersistentAttribute attribute = persistentAttribute();
//		
//		if (attribute.getMapping().getKey() != MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY
//				&& attribute.getAttribute() != null 
//				&& attribute.getAttribute().isField()) {
//			int flags;
//			try {
//				flags = attribute.getAttribute().getJdtMember().getFlags();
//			} catch (JavaModelException jme) { 
//				/* no error to log, in that case */ 
//				return;
//			}
//			
//			if (Flags.isFinal(flags)) {
//				messages.add(
//					DefaultJpaValidationMessages.buildMessage(
//						IMessage.HIGH_SEVERITY,
//						JpaValidationMessages.PERSISTENT_ATTRIBUTE_FINAL_FIELD,
//						new String[] {attribute.getName()},
//						attribute, attribute.validationTextRange())
//				);
//			}
//			
//			if (Flags.isPublic(flags)) {
//				messages.add(
//					DefaultJpaValidationMessages.buildMessage(
//						IMessage.HIGH_SEVERITY,
//						JpaValidationMessages.PERSISTENT_ATTRIBUTE_PUBLIC_FIELD,
//						new String[] {attribute.getName()},
//						attribute, attribute.validationTextRange())
//				);
//				
//			}
//		}
//	}
}
