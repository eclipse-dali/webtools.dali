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

import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.jaxb.core.JaxbFactory;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JaxbProject.Config;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbElementFactoryMethod;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlAnyAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlAnyElementMapping;
import org.eclipse.jpt.jaxb.core.context.XmlAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlElementMapping;
import org.eclipse.jpt.jaxb.core.context.XmlElementRefMapping;
import org.eclipse.jpt.jaxb.core.context.XmlElementRefsMapping;
import org.eclipse.jpt.jaxb.core.context.XmlElementsMapping;
import org.eclipse.jpt.jaxb.core.context.XmlRegistry;
import org.eclipse.jpt.jaxb.core.context.XmlValueMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaClass;
import org.eclipse.jpt.jaxb.core.context.java.JavaClassMapping;
import org.eclipse.jpt.jaxb.core.internal.AbstractJaxbFactory;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELJaxbContextRoot;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.ELJaxbProjectImpl;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.ELJaxbContextRootImpl;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.ELJaxbPackageImpl;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaClassMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaElementFactoryMethod;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaXmlAnyAttributeMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaXmlAnyElementMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaXmlAttributeMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaXmlElementMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaXmlElementRefMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaXmlElementRefsMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaXmlElementsMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaXmlValueMapping;


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
	
	
	// ***** core model *****
	
	@Override
	public JaxbProject buildJaxbProject(Config config) {
		return new ELJaxbProjectImpl(config);
	}
	
	
	// ***** non-resource context nodes *****
	
	@Override
	public JaxbContextRoot buildContextRoot(JaxbProject parent) {
		return new ELJaxbContextRootImpl(parent);
	}
	
	@Override
	public JaxbPackage buildPackage(JaxbContextRoot parent, String packageName) {
		return new ELJaxbPackageImpl((ELJaxbContextRoot) parent, packageName);
	}
	
	
	// ***** java context nodes *****
	
	@Override
	public JaxbElementFactoryMethod buildJavaElementFactoryMethod(
			XmlRegistry parent, JavaResourceMethod resourceMethod) {
		return new ELJavaElementFactoryMethod(parent, resourceMethod);
	}
	
	@Override
	public JavaClassMapping buildJavaClassMapping(JavaClass parent) {
		return new ELJavaClassMapping(parent);
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
	public XmlElementRefMapping buildJavaXmlElementRefMapping(JaxbPersistentAttribute parent) {
		return new ELJavaXmlElementRefMapping(parent);
	}
	
	@Override
	public XmlElementRefsMapping buildJavaXmlElementRefsMapping(JaxbPersistentAttribute parent) {
		return new ELJavaXmlElementRefsMapping(parent);
	}
	
	@Override
	public XmlElementsMapping buildJavaXmlElementsMapping(JaxbPersistentAttribute parent) {
		return new ELJavaXmlElementsMapping(parent);
	}
	
	@Override
	public XmlValueMapping buildJavaXmlValueMapping(JaxbPersistentAttribute parent) {
		return new ELJavaXmlValueMapping(parent);
	}
}
