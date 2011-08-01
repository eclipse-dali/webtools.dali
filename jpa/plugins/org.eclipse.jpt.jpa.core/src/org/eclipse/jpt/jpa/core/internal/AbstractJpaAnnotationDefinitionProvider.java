/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal;

import java.util.ArrayList;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.jpa.core.JpaAnnotationDefinitionProvider;

public abstract class AbstractJpaAnnotationDefinitionProvider
	implements JpaAnnotationDefinitionProvider
{
	protected ArrayList<AnnotationDefinition> annotationDefinitions;

	protected ArrayList<NestableAnnotationDefinition> nestableAnnotationDefinitions;


	protected AbstractJpaAnnotationDefinitionProvider() {
		super();
	}


	// ********** annotation definitions **********

	public synchronized Iterable<AnnotationDefinition> getAnnotationDefinitions() {
		if (this.annotationDefinitions == null) {
			this.annotationDefinitions = this.buildAnnotationDefinitions();
		}
		return this.annotationDefinitions;
	}

	protected ArrayList<AnnotationDefinition> buildAnnotationDefinitions() {
		ArrayList<AnnotationDefinition> definitions = new ArrayList<AnnotationDefinition>();
		this.addAnnotationDefinitionsTo(definitions);
		return definitions;
	}

	protected abstract void addAnnotationDefinitionsTo(ArrayList<AnnotationDefinition> definitions);


	// ********** nestable annotation definitions **********

	public synchronized Iterable<NestableAnnotationDefinition> getNestableAnnotationDefinitions() {
		if (this.nestableAnnotationDefinitions == null) {
			this.nestableAnnotationDefinitions = this.buildNestableAnnotationDefinitions();
		}
		return this.nestableAnnotationDefinitions;
	}

	protected ArrayList<NestableAnnotationDefinition> buildNestableAnnotationDefinitions() {
		ArrayList<NestableAnnotationDefinition> definitions = new ArrayList<NestableAnnotationDefinition>();
		this.addNestableAnnotationDefinitionsTo(definitions);
		return definitions;
	}

	protected abstract void addNestableAnnotationDefinitionsTo(ArrayList<NestableAnnotationDefinition> definitions);

}

