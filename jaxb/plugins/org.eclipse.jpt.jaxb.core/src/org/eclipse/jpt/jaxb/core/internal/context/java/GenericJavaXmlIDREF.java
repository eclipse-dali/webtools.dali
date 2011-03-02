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

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.jaxb.core.context.XmlIDREF;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.XmlIDREFAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaXmlIDREF
	extends AbstractJavaContextNode
	implements XmlIDREF
{

	protected final XmlIDREFAnnotation resourceXmlIDREF;

	public GenericJavaXmlIDREF(JaxbAttributeMapping parent, XmlIDREFAnnotation resource) {
		super(parent);
		this.resourceXmlIDREF = resource;
	}

	@Override
	public JaxbAttributeMapping getParent() {
		return (JaxbAttributeMapping) super.getParent();
	}

	protected JaxbPersistentAttribute getPersistentAttribute() {
		return getParent().getParent();
	}


	//************* validation ****************
	/**
	 * From the JAXB spec section 8.9.12 XmlIDREF:
	 * <p>
	 * The following mapping constraints must be enforced:<ul>
	 * <li> If the type of the field or property is a collection type, then the collection
	 *      item type must contain a property or field annotated with @XmlID.
	 * <li> If the field or property is not a collection type, then the type of the
	 *     property or field must contain a property or field annotated with @XmlID.
	 * </ul>
	 * <p>
	 * Note: If the collection item type or the type of the property (for non collection type)
	 * is java.lang.Object, then the instance must contain a property/field annotated with @XmlID attribute.
	 */
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		if (getPersistentAttribute().getJavaResourceAttributeTypeName() == Object.class.getName()) {
			//The instance must contain a property/field annotated with @XmlID attribute, but we cannot validate the instance
			return;
		}
		String typeName = getPersistentAttribute().getJavaResourceAttributeTypeName();
		JaxbPersistentClass persistentClass = getContextRoot().getPersistentClass(typeName);
		if (persistentClass != null) {
			if (!persistentClass.containsXmlId()) {
				messages.add(
				DefaultValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JaxbValidationMessages.XML_IDREF_TYPE_DOES_NOT_CONTAIN_XML_ID,
					new String[] {getPersistentAttribute().getName(), typeName},
					this,
					getValidationTextRange(astRoot)));				
			}
		}
		else {
			//do we validate this case??
		}
	}

	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.resourceXmlIDREF.getTextRange(astRoot);
	}
}
