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
import org.eclipse.jpt.jaxb.core.internal.context.java.JavaXmlAnyAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;


public class ELJavaXmlAnyAttributeMappingDefinition
		extends JavaXmlAnyAttributeMappingDefinition {
	
	// singleton
	private static final ELJavaXmlAnyAttributeMappingDefinition INSTANCE = 
			new ELJavaXmlAnyAttributeMappingDefinition();
	
	private static final String[] SUPPORTING_ANNOTATION_NAMES = 
			{
				ELJaxb.XML_PATH };
	
	/**
	 * Return the singleton.
	 */
	public static ELJavaXmlAnyAttributeMappingDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	protected ELJavaXmlAnyAttributeMappingDefinition() {
		super();
	}
	
	
	@Override
	public Iterable<String> getSupportingAnnotationNames() {
		return IterableTools.concatenate(
				super.getSupportingAnnotationNames(),
				IterableTools.iterable(SUPPORTING_ANNOTATION_NAMES));
	}
}
