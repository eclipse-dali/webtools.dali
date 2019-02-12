/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java;

import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jaxb.core.internal.context.java.JavaXmlElementsMappingDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;


public class ELJavaXmlElementsMappingDefinition
		extends JavaXmlElementsMappingDefinition {
	
	
	// singleton
	private static final ELJavaXmlElementsMappingDefinition INSTANCE = 
			new ELJavaXmlElementsMappingDefinition();
	
	
	private static final String[] SUPPORTING_ANNOTATION_NAMES = 
			{
				ELJaxb.XML_PATH,
				ELJaxb.XML_PATHS };
	
	/**
	 * Return the singleton.
	 */
	public static ELJavaXmlElementsMappingDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	protected ELJavaXmlElementsMappingDefinition() {
		super();
	}
	
	
	@Override
	public Iterable<String> getSupportingAnnotationNames() {
		return IterableTools.concatenate(
				super.getSupportingAnnotationNames(),
				IterableTools.iterable(SUPPORTING_ANNOTATION_NAMES));
	}
}
