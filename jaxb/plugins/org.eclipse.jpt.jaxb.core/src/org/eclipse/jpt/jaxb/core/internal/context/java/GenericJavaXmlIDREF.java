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
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
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
		implements XmlIDREF {
	
	protected final Context context;
	
	
	public GenericJavaXmlIDREF(JaxbAttributeMapping parent, Context context) {
		super(parent);
		this.context = context;
	}
	
	
	@Override
	public JaxbAttributeMapping getParent() {
		return (JaxbAttributeMapping) super.getParent();
	}

	protected JaxbPersistentAttribute getPersistentAttribute() {
		return getParent().getPersistentAttribute();
	}


	// ***** validation *****
	
	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.context.getAnnotation().getTextRange(astRoot);
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		for (ValidatableType type : this.context.getReferencedTypes()) {
			String typeName = type.getFullyQualifiedName();
			
			// Object may be used in some cases of a *single* type, but can't be validated
			if ((Object.class.getName().equals(typeName) && CollectionTools.size(this.context.getReferencedTypes()) == 1)
					// Make sure class exists.  Nonexistent classes will already have an error.
					|| JDTTools.findType(getJaxbProject().getJavaProject(), typeName) == null) {
				continue;
			}
			
			JaxbPersistentClass persistentClass = getContextRoot().getPersistentClass(typeName);
			if (persistentClass == null || ! persistentClass.containsXmlId()) {
				messages.add(
				DefaultValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JaxbValidationMessages.XML_IDREF__TYPE_DOES_NOT_CONTAIN_XML_ID,
					new String[] { typeName },
					this,
					type.getValidationTextRange(astRoot)));				
			}
		}
	}
	
	
	public interface Context {
		
		XmlIDREFAnnotation getAnnotation();
		
		Iterable<ValidatableType> getReferencedTypes();
	}
	
	
	public interface ValidatableType {
		
		String getFullyQualifiedName();
		
		TextRange getValidationTextRange(CompilationUnit astRoot);
	}
}
