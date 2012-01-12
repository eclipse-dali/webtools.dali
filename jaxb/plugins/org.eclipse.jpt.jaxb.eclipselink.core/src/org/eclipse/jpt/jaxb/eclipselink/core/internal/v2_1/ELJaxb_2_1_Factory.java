/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_1;

import org.eclipse.jpt.jaxb.core.JaxbFactory;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlAnyAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlAnyElementMapping;
import org.eclipse.jpt.jaxb.core.context.XmlAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlElementMapping;
import org.eclipse.jpt.jaxb.core.context.XmlElementsMapping;
import org.eclipse.jpt.jaxb.core.internal.AbstractJaxbFactory;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.ELJaxbContextRoot;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaXmlAnyAttributeMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaXmlAnyElementMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaXmlAttributeMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaXmlElementMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaXmlElementsMapping;


public class ELJaxb_2_1_Factory
		extends AbstractJaxbFactory {
	
	// singleton
	private static final JaxbFactory INSTANCE = new ELJaxb_2_1_Factory();
	
	
	/**
	 * Return the singleton.
	 */
	public static JaxbFactory instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private ELJaxb_2_1_Factory() {
		super();
	}
	
	
	@Override
	public JaxbContextRoot buildContextRoot(JaxbProject parent) {
		return new ELJaxbContextRoot(parent);
	}
	
	@Override
	public XmlAnyAttributeMapping buildJavaXmlAnyAttributeMapping(JaxbPersistentAttribute parent) {
		return new ELJavaXmlAnyAttributeMapping(parent);
	}
	
	@Override
	public XmlAnyElementMapping buildJavaXmlAnyElementMapping(JaxbPersistentAttribute parent) {
		return new ELJavaXmlAnyElementMapping(parent);
	}
	
	@Override
	public XmlAttributeMapping buildJavaXmlAttributeMapping(JaxbPersistentAttribute parent) {
		return new ELJavaXmlAttributeMapping(parent);
	}
	
	@Override
	public XmlElementMapping buildJavaXmlElementMapping(JaxbPersistentAttribute parent) {
		return new ELJavaXmlElementMapping(parent);
	}
	
	@Override
	public XmlElementsMapping buildJavaXmlElementsMapping(JaxbPersistentAttribute parent) {
		return new ELJavaXmlElementsMapping(parent);
	}
}
