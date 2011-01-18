/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import java.util.ArrayList;
import org.eclipse.jpt.core.JpaAnnotationDefinitionProvider;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;

public abstract class AbstractJpaAnnotationDefinitionProvider
	implements JpaAnnotationDefinitionProvider
{
	protected ArrayList<AnnotationDefinition> typeAnnotationDefinitions;

	protected ArrayList<AnnotationDefinition> typeMappingAnnotationDefinitions;

	protected ArrayList<AnnotationDefinition> attributeAnnotationDefinitions;

	private ArrayList<AnnotationDefinition> packageAnnotationDefinitions;


	protected AbstractJpaAnnotationDefinitionProvider() {
		super();
	}


	// ********** type annotation definitions **********

	public synchronized Iterable<AnnotationDefinition> getTypeAnnotationDefinitions() {
		if (this.typeAnnotationDefinitions == null) {
			this.typeAnnotationDefinitions = this.buildTypeAnnotationDefinitions();
		}
		return this.typeAnnotationDefinitions;
	}

	protected ArrayList<AnnotationDefinition> buildTypeAnnotationDefinitions() {
		ArrayList<AnnotationDefinition> definitions = new ArrayList<AnnotationDefinition>();
		this.addTypeAnnotationDefinitionsTo(definitions);
		return definitions;
	}

	protected abstract void addTypeAnnotationDefinitionsTo(ArrayList<AnnotationDefinition> definitions);


	// ********** type mapping annotation definitions **********

	public synchronized Iterable<AnnotationDefinition> getTypeMappingAnnotationDefinitions() {
		if (this.typeMappingAnnotationDefinitions == null) {
			this.typeMappingAnnotationDefinitions = this.buildTypeMappingAnnotationDefinitions();
		}
		return this.typeMappingAnnotationDefinitions;
	}

	protected ArrayList<AnnotationDefinition> buildTypeMappingAnnotationDefinitions() {
		ArrayList<AnnotationDefinition> definitions = new ArrayList<AnnotationDefinition>();
		this.addTypeMappingAnnotationDefinitionsTo(definitions);
		return definitions;
	}

	protected abstract void addTypeMappingAnnotationDefinitionsTo(ArrayList<AnnotationDefinition> definitions);


	// ********** attribute annotation definitions **********

	public synchronized Iterable<AnnotationDefinition> getAttributeAnnotationDefinitions() {
		if (this.attributeAnnotationDefinitions == null) {
			this.attributeAnnotationDefinitions = this.buildAttributeAnnotationDefinitions();
		}
		return this.attributeAnnotationDefinitions;
	}

	protected ArrayList<AnnotationDefinition> buildAttributeAnnotationDefinitions() {
		ArrayList<AnnotationDefinition> definitions = new ArrayList<AnnotationDefinition>();
		this.addAttributeAnnotationDefinitionsTo(definitions);
		return definitions;
	}

	protected abstract void addAttributeAnnotationDefinitionsTo(ArrayList<AnnotationDefinition> definitions);


	// ********** package annotation definitions **********

	public synchronized Iterable<AnnotationDefinition> getPackageAnnotationDefinitions() {
		if (this.packageAnnotationDefinitions == null) {
			this.packageAnnotationDefinitions = this.buildPackageAnnotationDefinitions();
		}
		return this.packageAnnotationDefinitions;
	}

	protected ArrayList<AnnotationDefinition> buildPackageAnnotationDefinitions() {
		ArrayList<AnnotationDefinition> definitions = new ArrayList<AnnotationDefinition>();
		this.addPackageAnnotationDefinitionsTo(definitions);
		return definitions;
	}

	protected abstract void addPackageAnnotationDefinitionsTo(ArrayList<AnnotationDefinition> definitions);
}
