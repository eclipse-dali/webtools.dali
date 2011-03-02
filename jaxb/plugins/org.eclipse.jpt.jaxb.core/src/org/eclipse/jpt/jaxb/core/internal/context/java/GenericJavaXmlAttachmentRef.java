/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlAttachmentRef;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAttachmentRefAnnotation;

public class GenericJavaXmlAttachmentRef
	extends AbstractJavaContextNode
	implements XmlAttachmentRef
{

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
		return getParent().getParent();
	}

	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.resourceXmlAttachmentRef.getTextRange(astRoot);
	}
}
