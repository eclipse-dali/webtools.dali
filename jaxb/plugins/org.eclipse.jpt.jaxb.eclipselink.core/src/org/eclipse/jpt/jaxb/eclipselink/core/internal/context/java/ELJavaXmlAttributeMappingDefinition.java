/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java;

import java.util.StringTokenizer;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.core.internal.context.java.JavaXmlAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlPathAnnotation;


public class ELJavaXmlAttributeMappingDefinition
		extends JavaXmlAttributeMappingDefinition
		implements DefaultJavaAttributeMappingDefinition {
	
	// singleton
	private static final ELJavaXmlAttributeMappingDefinition INSTANCE = 
			new ELJavaXmlAttributeMappingDefinition();
	
	private static final String[] SUPPORTING_ANNOTATION_NAMES = 
			{
				ELJaxb.XML_KEY,
				ELJaxb.XML_PATH };
	
	/**
	 * Return the singleton.
	 */
	public static DefaultJavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	protected ELJavaXmlAttributeMappingDefinition() {
		super();
	}
	
	
	@Override
	public Iterable<String> getSupportingAnnotationNames() {
		return IterableTools.concatenate(
				super.getSupportingAnnotationNames(),
				IterableTools.iterable(SUPPORTING_ANNOTATION_NAMES));
	}
	
	public boolean isDefault(JaxbPersistentAttribute persistentAttribute) {
		// test whether annotated with @XmlPath, and if so, if last segment starts with "@"
		// (presence of "@" elsewhere may be a node select clause)
		XmlPathAnnotation xmlPathAnnotation = null;
		if (persistentAttribute.getJavaResourceAttribute().getAnnotationsSize(ELJaxb.XML_PATH) > 0) {
			xmlPathAnnotation = (XmlPathAnnotation) persistentAttribute.getJavaResourceAttribute().getAnnotation(0, ELJaxb.XML_PATH);
		}
		if (xmlPathAnnotation == null) {
			return false;
		}
		String value = xmlPathAnnotation.getValue();
		if (value == null) {
			return false;
		}
		StringTokenizer tokenizer = new StringTokenizer(value, "/");
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if (! tokenizer.hasMoreTokens()) {
				return token.startsWith("@");
			}
		}
		return false;
	}
}
