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
import org.eclipse.jpt.jaxb.core.context.XmlMixed;
import org.eclipse.jpt.jaxb.core.resource.java.XmlMixedAnnotation;

public class GenericJavaXmlMixed
	extends AbstractJavaContextNode
	implements XmlMixed
{

	protected final XmlMixedAnnotation resourceXmlMixed;

	public GenericJavaXmlMixed(JaxbAttributeMapping parent, XmlMixedAnnotation resource) {
		super(parent);
		this.resourceXmlMixed = resource;
	}

	@Override
	public JaxbAttributeMapping getParent() {
		return (JaxbAttributeMapping) super.getParent();
	}

	protected JaxbPersistentAttribute getPersistentAttribute() {
		return getParent().getParent();
	}

	// ********** synchronize/update **********

	public void synchronizeWithResourceModel() {
		//nothing yet
	}

	public void update() {
		//nothing yet
	}


	//************* validation ****************

	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.resourceXmlMixed.getTextRange(astRoot);
	}
}
