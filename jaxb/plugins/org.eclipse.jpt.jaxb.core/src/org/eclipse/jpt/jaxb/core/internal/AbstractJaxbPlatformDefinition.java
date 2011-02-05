/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal;

import java.util.ArrayList;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jaxb.core.JaxbResourceModelProvider;
import org.eclipse.jpt.jaxb.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinition;
import org.eclipse.jpt.jaxb.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.jaxb.core.resource.java.NestableAnnotationDefinition;

/**
 * All the state in the JAXB platform definition should be "static" 
 * (i.e. unchanging once it is initialized).
 */
public abstract class AbstractJaxbPlatformDefinition
		implements JaxbPlatformDefinition {
	
	private AnnotationDefinition[] annotationDefinitions;

	private NestableAnnotationDefinition[] nestableAnnotationDefinitions;
	
	private JaxbResourceModelProvider[] resourceModelProviders;

	private ArrayList<DefaultJavaAttributeMappingDefinition> defaultJavaAttributeMappingDefinitions;

	private ArrayList<JavaAttributeMappingDefinition> specifiedJavaAttributeMappingDefinitions;

//	private ResourceDefinition[] resourceDefinitions;


	/**
	 * zero-argument constructor
	 */
	protected AbstractJaxbPlatformDefinition() {
		super();
	}
	
	
	// ********** annotation definitions **********
	
	public AnnotationDefinition[] getAnnotationDefinitions() {
		if (this.annotationDefinitions == null) {
			this.annotationDefinitions = this.buildAnnotationDefinitions();
		}
		return this.annotationDefinitions;
	}
	
	protected abstract AnnotationDefinition[] buildAnnotationDefinitions();


	// ********** nestable annotation definitions **********

	public NestableAnnotationDefinition[] getNestableAnnotationDefinitions() {
		if (this.nestableAnnotationDefinitions == null) {
			this.nestableAnnotationDefinitions = this.buildNestableAnnotationDefinitions();
		}
		return this.nestableAnnotationDefinitions;
	}

	protected abstract NestableAnnotationDefinition[] buildNestableAnnotationDefinitions();
	
	
	// ********** resource models **********
	
	public ListIterable<JaxbResourceModelProvider> getResourceModelProviders() {
		return new ArrayListIterable<JaxbResourceModelProvider>(getResourceModelProviders_());
	}
	
	protected synchronized JaxbResourceModelProvider[] getResourceModelProviders_() {
		if (this.resourceModelProviders == null) {
			this.resourceModelProviders = this.buildResourceModelProviders();
		}
		return this.resourceModelProviders;
	}
	
	protected abstract JaxbResourceModelProvider[] buildResourceModelProviders();


	// ********** Java attribute mappings **********

	public synchronized Iterable<DefaultJavaAttributeMappingDefinition> getDefaultJavaAttributeMappingDefinitions() {
		if (this.defaultJavaAttributeMappingDefinitions == null) {
			this.defaultJavaAttributeMappingDefinitions = this.buildDefaultJavaAttributeMappingDefinitions();
		}
		return this.defaultJavaAttributeMappingDefinitions;
	}

	protected ArrayList<DefaultJavaAttributeMappingDefinition> buildDefaultJavaAttributeMappingDefinitions() {
		ArrayList<DefaultJavaAttributeMappingDefinition> definitions = new ArrayList<DefaultJavaAttributeMappingDefinition>();
		this.addDefaultJavaAttributeMappingDefinitionsTo(definitions);
		return definitions;
	}

	/**
	 * To the specified list, add mapping definitions to use for analyzing the
	 * default mapping of an attribute. The order is important,
	 * as once a mapping definition tests positive for an attribute,
	 * all following mapping definitions are ignored.
	 */
	protected abstract void addDefaultJavaAttributeMappingDefinitionsTo(ArrayList<DefaultJavaAttributeMappingDefinition> definitions);

	public synchronized Iterable<JavaAttributeMappingDefinition> getSpecifiedJavaAttributeMappingDefinitions() {
		if (this.specifiedJavaAttributeMappingDefinitions == null) {
			this.specifiedJavaAttributeMappingDefinitions = this.buildSpecifiedJavaAttributeMappingDefinitions();
		}
		return this.specifiedJavaAttributeMappingDefinitions;
	}

	protected ArrayList<JavaAttributeMappingDefinition> buildSpecifiedJavaAttributeMappingDefinitions() {
		ArrayList<JavaAttributeMappingDefinition> definitions = new ArrayList<JavaAttributeMappingDefinition>();
		this.addSpecifiedJavaAttributeMappingDefinitionsTo(definitions);
		return definitions;
	}

	/**
	 * To the specified list, add mapping definitions to use for analyzing the
	 * specified mapping of an attribute given all annotations on it. The order
	 * is important, as once a mapping definition tests positive for an
	 * attribute, all following mapping definitions are ignored.
	 */
	protected abstract void addSpecifiedJavaAttributeMappingDefinitionsTo(ArrayList<JavaAttributeMappingDefinition> definitions);


//	// ********** Mapping Files **********
//	
//	public ListIterator<ResourceDefinition> resourceDefinitions() {
//		return new ArrayListIterator<ResourceDefinition>(getResourceDefinitions());
//	}
//	
//	protected synchronized ResourceDefinition[] getResourceDefinitions() {
//		if (this.resourceDefinitions == null) {
//			this.resourceDefinitions = this.buildResourceDefinitions();
//		}
//		return this.resourceDefinitions;
//	}
//	
//	protected abstract ResourceDefinition[] buildResourceDefinitions();
}
