/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context;

import java.util.List;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextRoot;
import org.eclipse.jpt.jaxb.core.resource.jaxbprops.JaxbPropertiesResource;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.validation.ELJaxbValidationMessageBuilder;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.validation.ELJaxbValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class ELJaxbContextRoot
		extends AbstractJaxbContextRoot {
	
	
	public ELJaxbContextRoot(JaxbProject jaxbProject) {
		super(jaxbProject);
	}
	
	
	// **************** validation ********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		validateJaxbProperties(messages, reporter);
	}
	
	protected void validateJaxbProperties(List<IMessage> messages, IReporter reporter) {
		String factoryProp = "javax.xml.bind.context.factory";
		String factoryPropValue = "org.eclipse.persistence.jaxb.JAXBContextFactory";
			
		for (JaxbPackage jp : getPackages()) {
			String pn = jp.getName();
			JaxbPropertiesResource jpr = getJaxbProject().getJaxbPropertiesResource(pn);
			if (jpr != null && StringTools.stringsAreEqual(jpr.getProperty(factoryProp), factoryPropValue)) {
				return;
			}
		}
		
		messages.add(
				ELJaxbValidationMessageBuilder.buildMessage(
						IMessage.HIGH_SEVERITY,
						ELJaxbValidationMessages.PROJECT_MISSING_ECLIPSELINK_JAXB_CONTEXT_FACTORY,
						getJaxbProject()));
	}
}
