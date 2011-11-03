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
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlIDREF;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.XmlIDREFAnnotation;
import org.eclipse.jpt.jaxb.core.xsd.XsdFeature;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.eclipse.xsd.util.XSDUtil;

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
		
		for (ValidatableReference ref : this.context.getReferences()) {
			String typeName = ref.getFullyQualifiedType();
			
			// Object may be used in some cases of a *single* type, but can't be validated
			if (! (Object.class.getName().equals(typeName) && CollectionTools.size(this.context.getReferences()) == 1)) {
					
				JaxbClassMapping classMapping = getContextRoot().getClassMapping(typeName);
				if (classMapping == null || classMapping.getXmlIdMapping() == null) {
					messages.add(
					DefaultValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JaxbValidationMessages.XML_ID_REF__TYPE_DOES_NOT_CONTAIN_XML_ID,
						new String[] { typeName },
						this,
						ref.getTypeTextRange(astRoot)));				
				}
			}
			
			XsdFeature xsdFeature = ref.getXsdFeature();
			XsdTypeDefinition xsdType = (xsdFeature == null) ? null : xsdFeature.getType();
			if (xsdType != null && 
					! xsdType.matches(XSDUtil.SCHEMA_FOR_SCHEMA_URI_2001, "IDREF")) {
				messages.add(
						DefaultValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JaxbValidationMessages.XML_ID_REF__SCHEMA_TYPE_NOT_IDREF,
							new String [] { xsdFeature.getName() },
							this,
							ref.getXsdFeatureTextRange(astRoot)));
			}
		}
	}
	
	
	public interface Context {
		
		XmlIDREFAnnotation getAnnotation();
		
		Iterable<ValidatableReference> getReferences();
	}
	
	
	public interface ValidatableReference {
		
		String getFullyQualifiedType();
		
		TextRange getTypeTextRange(CompilationUnit astRoot);
		
		XsdFeature getXsdFeature();
		
		TextRange getXsdFeatureTextRange(CompilationUnit astRoot);
	}
}
