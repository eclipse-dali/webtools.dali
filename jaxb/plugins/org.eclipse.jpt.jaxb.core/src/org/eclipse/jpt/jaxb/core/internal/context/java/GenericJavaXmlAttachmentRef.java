/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlAttachmentRef;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAttachmentRefAnnotation;

public class GenericJavaXmlAttachmentRef
		extends AbstractJavaContextNode
		implements XmlAttachmentRef {
	
	protected final XmlAttachmentRefAnnotation resourceXmlAttachmentRef;
	
	
	public GenericJavaXmlAttachmentRef(JaxbAttributeMapping parent, XmlAttachmentRefAnnotation resource) {
		super(parent);
		this.resourceXmlAttachmentRef = resource;
	}
	
	
	@Override
	public JaxbAttributeMapping getParent() {
		return (JaxbAttributeMapping) super.getParent();
	}

	protected JaxbPersistentAttribute getPersistentAttribute() {
		return getParent().getPersistentAttribute();
	}

	@Override
	public TextRange getValidationTextRange() {
		return this.resourceXmlAttachmentRef.getTextRange();
	}
}
