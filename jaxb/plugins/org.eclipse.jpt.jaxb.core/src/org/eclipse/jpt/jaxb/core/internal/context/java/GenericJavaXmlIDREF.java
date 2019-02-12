/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlIDREF;
import org.eclipse.jpt.jaxb.core.resource.java.XmlIDREFAnnotation;
import org.eclipse.jpt.jaxb.core.validation.JptJaxbCoreValidationMessages;
import org.eclipse.jpt.jaxb.core.xsd.XsdFeature;
import org.eclipse.jpt.jaxb.core.xsd.XsdUtil;
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
	public TextRange getValidationTextRange() {
		return this.context.getAnnotation().getTextRange();
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		for (ValidatableReference ref : this.context.getReferences()) {
			String typeName = ref.getFullyQualifiedType();
			
			// Object may be used in some cases of a *single* type, but can't be validated
			if (! (Object.class.getName().equals(typeName) && IterableTools.size(this.context.getReferences()) == 1)) {
					
				JaxbClassMapping classMapping = getContextRoot().getClassMapping(typeName);
				if (classMapping == null || classMapping.getXmlIdMapping() == null) {
					messages.add(
					this.buildValidationMessage(
						ref.getTypeValidationTextRange(),
						JptJaxbCoreValidationMessages.XML_ID_REF__TYPE_DOES_NOT_CONTAIN_XML_ID,
						typeName));				
				}
			}
			
			XsdFeature xsdFeature = ref.getXsdFeature();
			if (xsdFeature == null) {
				return;
			}
			if (xsdFeature != null 
					&& ! xsdFeature.typeIsValid(XsdUtil.getSchemaForSchema().getTypeDefinition("IDREF"), this.context.isList())) {
				messages.add(
						this.buildValidationMessage(
							ref.getXsdFeatureValidationTextRange(),
							JptJaxbCoreValidationMessages.XML_ID_REF__SCHEMA_TYPE_NOT_IDREF,
							xsdFeature.getName()));
			}
		}
	}
	
	
	public interface Context {
		
		XmlIDREFAnnotation getAnnotation();
		
		Iterable<ValidatableReference> getReferences();
		
		boolean isList();
	}
	
	
	public interface ValidatableReference {
		
		String getFullyQualifiedType();
		
		TextRange getTypeValidationTextRange();
		
		XsdFeature getXsdFeature();
		
		TextRange getXsdFeatureValidationTextRange();
	}
}
